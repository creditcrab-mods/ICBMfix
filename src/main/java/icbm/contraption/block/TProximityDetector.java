package icbm.contraption.block;

import java.util.List;

import calclavia.lib.TileEntityUniversalRunnable;
import icbm.contraption.ItemSignalDisruptor;
import icbm.contraption.ProximityDetectorModePacket;
import icbm.core.di.TileElectricICBM;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IRedstoneProvider;
import universalelectricity.prefab.tile.TileEntityRFUser;

public class TProximityDetector
    extends TileEntityRFUser implements IRedstoneProvider {

    public static final int ENERGY_USED = 5;

    public short frequency;
    public boolean isDetect;
    public Vector3 minCoord;
    public Vector3 maxCoord;
    public byte mode;
    public boolean isInverted;
    public double wattsForDisplay;

    public TProximityDetector() {
        super(32000, Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.frequency = 0;
        this.isDetect = false;
        this.minCoord = new Vector3(9.0, 9.0, 9.0);
        this.maxCoord = new Vector3(9.0, 9.0, 9.0);
        this.mode = 0;
        this.isInverted = false;
        this.wattsForDisplay = 0;
    }

    @Override
    public void initiate() {
        super.initiate();
        this.worldObj.notifyBlocksOfNeighborChange(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockType()
        );
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.worldObj.isRemote && super.ticks % 20L == 0L) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

            if (!this.isDisabled()) {
                boolean isDetectThisCheck = false;

                if (super.energyStorage.getEnergyStored() >= ENERGY_USED) {
                    final AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(
                        this.xCoord - this.minCoord.x,
                        this.yCoord - this.minCoord.y,
                        this.zCoord - this.minCoord.z,
                        this.xCoord + this.maxCoord.x + 1.0,
                        this.yCoord + this.maxCoord.y + 1.0,
                        this.zCoord + this.maxCoord.z + 1.0
                    );
                    final List<EntityLivingBase> entitiesNearby
                        = this.worldObj.getEntitiesWithinAABB(
                            EntityLivingBase.class, bounds
                        );

                    for (final EntityLivingBase entity : entitiesNearby) {
                        if (entity instanceof EntityPlayer
                            && (this.mode == 0 || this.mode == 1)) {
                            boolean gotDisrupter = false;

                            for (final ItemStack inventory :
                                 ((EntityPlayer) entity).inventory.mainInventory) {
                                if (inventory != null && inventory.getItem() instanceof ItemSignalDisruptor && ((ItemSignalDisruptor) inventory.getItem()).getFrequency(inventory) == this.frequency) {
                                    gotDisrupter = true;
                                    break;
                                }
                            }

                            if (gotDisrupter) {
                                if (this.isInverted) {
                                    isDetectThisCheck = true;
                                    break;
                                }

                                continue;
                            } else {
                                if (this.isInverted) {
                                    continue;
                                }

                                isDetectThisCheck = true;
                            }
                        } else {
                            if (!this.isInverted && !(entity instanceof EntityPlayer)
                                && (this.mode == 0 || this.mode == 2)) {
                                isDetectThisCheck = true;
                                break;
                            }

                            continue;
                        }
                    }

                    if (!this.worldObj.isRemote) {
                        energyStorage.extractEnergy(ENERGY_USED,false);
                    }
                }

                if (isDetectThisCheck != this.isDetect) {
                    this.isDetect = isDetectThisCheck;
                    this.worldObj.notifyBlocksOfNeighborChange(
                        this.xCoord, this.yCoord, this.zCoord, this.getBlockType()
                    );
                }
            }
        }
    }

    public void onModePacket(ProximityDetectorModePacket pkt) {
        pkt.mode.ifPresent(x -> this.mode = x);
        pkt.frequency.ifPresent(x -> this.frequency = x);
        pkt.minCoord.ifPresent(x -> this.minCoord = x);
        pkt.maxCoord.ifPresent(x -> this.maxCoord = x);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();

        energyStorage.writeToNBT(nbt);
        nbt.setShort("frequency", this.frequency);
        nbt.setByte("mode", this.mode);
        nbt.setBoolean("isInverted", this.isInverted);
        nbt.setTag("minCoord", this.minCoord.writeToNBT(new NBTTagCompound()));
        nbt.setTag("maxCoord", this.maxCoord.writeToNBT(new NBTTagCompound()));

        return new S35PacketUpdateTileEntity(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbt
        );
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();

        energyStorage.readFromNBT(nbt);
        this.frequency = nbt.getShort("frequency");
        this.mode = nbt.getByte("mode");
        this.isInverted = nbt.getBoolean("isInverted");
        this.minCoord = Vector3.readFromNBT(nbt.getCompoundTag("minCoord"));
        this.maxCoord = Vector3.readFromNBT(nbt.getCompoundTag("maxCoord"));
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.mode = nbt.getByte("mode");
        this.frequency = nbt.getShort("frequency");
        this.isInverted = nbt.getBoolean("isInverted");
        this.minCoord = Vector3.readFromNBT(nbt.getCompoundTag("minCoord"));
        this.maxCoord = Vector3.readFromNBT(nbt.getCompoundTag("maxCoord"));
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setShort("frequency", this.frequency);
        nbt.setByte("mode", this.mode);
        nbt.setBoolean("isInverted", this.isInverted);
        nbt.setTag("minCoord", this.minCoord.writeToNBT(new NBTTagCompound()));
        nbt.setTag("maxCoord", this.maxCoord.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public boolean isPoweringTo(final ForgeDirection side) {
        return this.isDetect;
    }

    @Override
    public boolean isIndirectlyPoweringTo(final ForgeDirection side) {
        return this.isDetect;
    }

}

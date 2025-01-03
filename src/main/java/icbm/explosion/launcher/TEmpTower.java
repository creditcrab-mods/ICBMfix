package icbm.explosion.launcher;

import icbm.api.RadarRegistry;
import icbm.core.MainBase;
import icbm.explosion.ICBMExplosion;
import icbm.explosion.zhapin.ZhaPin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IRedstoneReceptor;
import universalelectricity.prefab.multiblock.IMultiBlock;
import universalelectricity.prefab.tile.TileEntityRFUser;

public class TEmpTower
    extends TileEntityRFUser implements IMultiBlock, IRedstoneReceptor {

    public static final int MAX_RADIUS = 150;
    public float rotation;
    private float xuanZhuanLu;


    public byte mode;

    public int radius;

    //TODO:Add RF support to EMP tower

    public TEmpTower() {
        super(400000,Integer.MAX_VALUE,Integer.MAX_VALUE);
        this.rotation = 0.0f;
        this.mode = 0;
        this.radius = 60;
        RadarRegistry.register(this);
    }

    @Override
    public void invalidate() {
        RadarRegistry.unregister(this);
        super.invalidate();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.isDisabled()) {
            if (super.ticks % 20L == 0L && this.energyStorage.getEnergyStored() > 0) {
                this.worldObj.playSoundEffect(
                    (double) this.xCoord,
                    (double) this.yCoord,
                    (double) this.zCoord,
                    "icbm:machinehum",
                    0.5f,
                    (float) (0.8500000238418579 * this.energyStorage.getEnergyStored() / this.energyStorage.getMaxEnergyStored())
                );
            }

            this.xuanZhuanLu
                = (float) (Math.pow((float)this.energyStorage.getEnergyStored() / (float) this.energyStorage.getMaxEnergyStored(), 2.0) * 0.5);
            this.rotation += this.xuanZhuanLu;

            if (this.rotation > 360.0f) {
                this.rotation = 0.0f;
            }
        }

        if (!this.worldObj.isRemote) {
            if (super.ticks % 10L == 0L) {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();

        this.energyStorage.readFromNBT(nbt);
        super.disabledTicks = nbt.getInteger("disabledTicks");
        this.radius = nbt.getInteger("radius");
        this.mode = nbt.getByte("holzOhJa");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();

        this.energyStorage.writeToNBT(nbt);
        nbt.setInteger("disabledTicks", super.disabledTicks);
        nbt.setInteger("radius", this.radius);
        nbt.setByte("holzOhJa", this.mode);

        return new S35PacketUpdateTileEntity(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbt
        );
    }


    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.radius = nbt.getInteger("radius");
        this.mode = nbt.getByte("holzOhJa");
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("radius", this.radius);
        nbt.setByte("holzOhJa", this.mode);
    }

    @Override
    public void onPowerOn() {
        int dian = this.getEnergyCost();
        if (this.energyStorage.getEnergyStored() >= this.getEnergyCost()) {
            switch (this.mode){
                case 0:
                    ZhaPin.empSignal.doExplosion(
                        this.worldObj,
                        new Vector3(this.xCoord, this.yCoord, this.zCoord),
                        null,
                        this.radius,
                        -1
                    );
                    ZhaPin.empWave.doExplosion(
                        this.worldObj,
                        new Vector3(this.xCoord, this.yCoord, this.zCoord),
                        null,
                        this.radius,
                        -1
                    );
                    break;
                case 1:
                    ZhaPin.empSignal.doExplosion(
                        this.worldObj,
                        new Vector3(this.xCoord, this.yCoord, this.zCoord),
                        null,
                        this.radius,
                        1
                    );
                    break;
                case 2:
                    ZhaPin.empWave.doExplosion(
                        this.worldObj,
                        new Vector3(this.xCoord, this.yCoord, this.zCoord),
                        null,
                        this.radius,
                        -1
                    );
                    ZhaPin.empSignal.doExplosion(
                        this.worldObj,
                        new Vector3(this.xCoord, this.yCoord, this.zCoord),
                        null,
                        this.radius,
                        0
                    );
                    break;
            }

            this.energyStorage.extractEnergy(dian,false);
        }
    }

    @Override
    public void onPowerOff() {}

    @Override
    public void onDestroy(final TileEntity callingBlock) {
        this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air, 0, 3);
        this.worldObj.setBlock(
            this.xCoord, this.yCoord + 1, this.zCoord, Blocks.air, 0, 3
        );
    }

    @Override
    public boolean onActivated(final EntityPlayer entityPlayer) {
        entityPlayer.openGui(
            (Object) ICBMExplosion.instance,
            6,
            this.worldObj,
            this.xCoord,
            this.yCoord,
            this.zCoord
        );
        return true;
    }

    @Override
    public void onCreate(final Vector3 position) {
        MainBase.bJia.makeFakeBlock(
            this.worldObj,
            Vector3.add(position, new Vector3(0.0, 1.0, 0.0)),
            new Vector3(this)
        );
    }

    /*
    @Override
    public double getMaxJoules() {
        return Math.max(2000000.0f * (this.radius / 150.0f), 1000000.0f);
    }
     */

    public int getEnergyCost(){
        return (int)( 400000.0f * (this.radius / 150.0f));
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }
}

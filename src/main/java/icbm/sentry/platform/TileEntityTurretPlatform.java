package icbm.sentry.platform;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import icbm.sentry.IAmmunition;
import icbm.sentry.ITurretUpgrade;
import icbm.sentry.ProjectileType;
import icbm.sentry.damage.IHealthTile;
import icbm.sentry.terminal.TileEntityTerminal;
import icbm.sentry.turret.ItemAmmo;
import icbm.sentry.turret.TTurretBase;
import icbm.sentry.turret.upgrades.ItPaoTaiUpgrades;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.CustomDamageSource;

public class TileEntityTurretPlatform extends TileEntityTerminal implements IInventory, IEnergyReceiver {
    private TTurretBase turret;
    public ForgeDirection deployDirection;
    public static final int UPGRADE_START_INDEX = 12;

    public int prevRF = 0;


    public ItemStack[] containingItems;

    public TileEntityTurretPlatform() {
        this.turret = null;
        this.deployDirection = ForgeDirection.UP;
        this.containingItems = new ItemStack[16];
    }

    @Override
    public void updateEntity() {
        super.updateEntity();


        if (!this.worldObj.isRemote && super.ticks % 3L == 0L) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();

        }



        if (!this.worldObj.isRemote) {
            for (int i = 0; i < 12; ++i) {
                if(containingItems[i] != null && containingItems[i].getItem() instanceof IEnergyContainerItem containerItem){
                    int energyNeeded = energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored();
                    energyStorage.receiveEnergy( containerItem.extractEnergy(containingItems[i],energyNeeded,false),false);
                }


            }
        }
    }


    public int getRFRequest(){
        var turret = this.getTurret(false);
        if(turret != null){
            return (int) (turret.getFiringRequest() / UniversalElectricity.UE_RF_RATIO);
        }
        return 0;
    }



    public TTurretBase getTurret(final boolean getNew) {
        final Vector3 position = new Vector3(this);

        if (getNew || this.turret == null || this.turret.isInvalid()
            || !new Vector3(this.turret)
                    .equals(position.clone().modifyPositionFromSide(this.deployDirection)
                    )) {
            final TileEntity tileEntity
                = position.clone()
                      .modifyPositionFromSide(this.deployDirection)
                      .getTileEntity((IBlockAccess) this.worldObj);

            if (tileEntity instanceof TTurretBase) {
                this.turret = (TTurretBase) tileEntity;
            } else {
                this.turret = null;
            }
        }

        return this.turret;
    }

    public boolean destroyTurret() {
        final TileEntity ent = this.worldObj.getTileEntity(
            this.xCoord + this.deployDirection.offsetX,
            this.yCoord + this.deployDirection.offsetY,
            this.zCoord + this.deployDirection.offsetZ
        );

        if (ent instanceof TTurretBase) {
            this.turret = null;
            ((TTurretBase) ent).destroy(false);
            return true;
        }

        return false;
    }

    public boolean destroy(final boolean doExplosion) {
        if (doExplosion) {
            this.worldObj.createExplosion(
                (Entity) null,
                (double) this.xCoord,
                (double) this.yCoord,
                (double) this.zCoord,
                2.0f,
                true
            );
        }

        if (!this.worldObj.isRemote) {
            this.getBlockType().dropBlockAsItem(
                this.worldObj,
                this.xCoord,
                this.yCoord,
                this.zCoord,
                this.getBlockMetadata(),
                0
            );
        }

        return this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public String getInventoryName() {
        return this.getBlockType().getLocalizedName();
    }

    public boolean isRunning() {
        return this.getTurret(false) != null
            && energyStorage.getEnergyStored() >= getRFRequest();
    }

    public int getTotalRFStored(){
        int total = energyStorage.getEnergyStored();
        for (int i = 0; i < 12; i++) {
            final ItemStack itemStack = this.containingItems[i];

            if (itemStack != null) {
                final Item item = itemStack.getItem();

                if (item instanceof IEnergyContainerItem containerItem){
                    total += containerItem.getEnergyStored(itemStack);
                }
            }
        }
        return total;
    }

    public void drainTotalRF(int energy){

        if(energy <= energyStorage.getEnergyStored()){
            energyStorage.extractEnergy(energy,false);
            return;
        }

        int remainder = energy - energyStorage.getEnergyStored();
        energyStorage.setEnergyStored(0);

        for (int i = 0; i < 12; i++) {
            final ItemStack itemStack = this.containingItems[i];

            if (itemStack != null) {
                final Item item = itemStack.getItem();

                if (item instanceof IEnergyContainerItem containerItem){
                    int storedEnergy = containerItem.getEnergyStored(itemStack);
                    if(remainder <= storedEnergy){
                        containerItem.extractEnergy(itemStack,remainder,false);
                        return;
                    }
                    remainder -= storedEnergy;
                    containerItem.extractEnergy(itemStack,storedEnergy,false);
                }
            }
        }
    }

    public ItemStack hasAmmunition(final ProjectileType projectileType) {
        for (int i = 0; i < 12; ++i) {
            final ItemStack itemStack = this.containingItems[i];

            if (itemStack != null) {
                final Item item = itemStack.getItem();

                if (item instanceof IAmmunition
                    && ((IAmmunition) item).getType(itemStack) == projectileType) {
                    return itemStack;
                }
            }
        }

        return null;
    }

    public boolean useAmmunition(final ItemStack ammoStack) {
        if (ammoStack != null) {
            if (ammoStack.getItemDamage() == ItemAmmo.AmmoType.BULLETINF.ordinal()) {
                return true;
            }

            for (int i = 0; i < 12; ++i) {
                final ItemStack itemStack = this.containingItems[i];

                if (itemStack != null && itemStack.isItemEqual(ammoStack)) {
                    this.decrStackSize(i, 1);
                    return true;
                }
            }
        }

        return false;
    }

    public int getUpgradeCount(final ItPaoTaiUpgrades.TurretUpgradeType type) {
        int count = 0;

        for (int i = 12; i < 15; ++i) {
            final ItemStack itemStack = this.getStackInSlot(i);

            if (itemStack != null && itemStack.getItem() instanceof ITurretUpgrade
                && ((ITurretUpgrade) itemStack.getItem()).getType(itemStack) == type) {
                ++count;
            }
        }

        return count;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        final NBTTagList var2 = nbt.getTagList("Items", 10);
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound) var2.getCompoundTagAt(var3);
            final byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.containingItems.length) {
                this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        final NBTTagList itemTag = new NBTTagList();
        for (int slots = 0; slots < this.containingItems.length; ++slots) {
            if (this.containingItems[slots] != null) {
                final NBTTagCompound itemNbtData = new NBTTagCompound();
                itemNbtData.setByte("Slot", (byte) slots);
                this.containingItems[slots].writeToNBT(itemNbtData);
                itemTag.appendTag((NBTBase) itemNbtData);
            }
        }

        nbt.setTag("Items", (NBTBase) itemTag);
    }

    @Override
    public int getSizeInventory() {
        return this.containingItems.length;
    }

    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.containingItems[par1];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.containingItems[par1] != null) {
            final ItemStack var2 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var2;
        }

        return null;
    }

    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.containingItems[par1] == null) {
            return null;
        }

        if (this.containingItems[par1].stackSize <= par2) {
            final ItemStack var3 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var3;
        }

        final ItemStack var3 = this.containingItems[par1].splitStack(par2);

        if (this.containingItems[par1].stackSize == 0) {
            this.containingItems[par1] = null;
        }

        return var3;
    }

    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.containingItems[par1] = par2ItemStack;

        if (par2ItemStack != null
            && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(final int slotID, final ItemStack itemStack) {
        return slotID < 12 && itemStack.getItem() instanceof IAmmunition;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public String getChannel() {
        return "ICBM";
    }

    public boolean addStackToInventory(final ItemStack itemStack) {
        for (int i = 0; i < 12; ++i) {
            final ItemStack checkStack = this.getStackInSlot(i);

            if (itemStack.stackSize <= 0) {
                return true;
            }

            if (checkStack == null) {
                this.setInventorySlotContents(i, itemStack);
                return true;
            }

            if (checkStack.isItemEqual(itemStack)) {
                final int inputStack = Math.min(
                                           checkStack.stackSize + itemStack.stackSize,
                                           checkStack.getMaxStackSize()
                                       )
                    - checkStack.stackSize;
                itemStack.stackSize -= inputStack;
                final ItemStack itemStack2 = checkStack;
                itemStack2.stackSize += inputStack;
                this.setInventorySlotContents(i, checkStack);
            }
        }

        return false;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();


        super.writeToNBT(nbt);
        energyStorage.writeToNBT(nbt);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord,
            this.getBlockMetadata(), nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();


        super.readFromNBT(nbt);
        energyStorage.readFromNBT(nbt);
    }


}

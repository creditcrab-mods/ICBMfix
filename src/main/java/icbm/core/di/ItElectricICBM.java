package icbm.core.di;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.ItemHelper;
import icbm.core.ICBMTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import universalelectricity.core.item.ItemElectric;

import java.util.List;

public abstract class ItElectricICBM extends ItemElectric implements IEnergyContainerItem {

    public int CAPACITY = 32000;
    public int SEND = 160;
    public  int RECEIVE = 160;

    public ItElectricICBM(final String name) {
        super();
        this.setUnlocalizedName("icbm:" + name);
        this.setCreativeTab((CreativeTabs) ICBMTab.INSTANCE);
    }

    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        par3List.add(this.getEnergyStored(itemStack) + " RF");
    }

    @Override
    public int receiveEnergy(ItemStack itemStack, int i, boolean b) {
        if (itemStack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(itemStack, 0);
        }

        int energy = itemStack.stackTagCompound.getInteger("Energy");
        int delta = Math.min(i, Math.min(CAPACITY - energy, RECEIVE));

        energy += delta;
        itemStack.stackTagCompound.setInteger("Energy", energy);

        return delta;
    }

    public void drainEnergy(ItemStack itemStack, int i){
        if (itemStack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(itemStack, 0);
        }
        int energy = itemStack.stackTagCompound.getInteger("Energy");
        itemStack.stackTagCompound.setInteger("Energy",energy - i);
    }

    @Override
    public int extractEnergy(ItemStack itemStack, int i, boolean b) {
        if (itemStack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(itemStack, 0);
        }

        int energy = itemStack.stackTagCompound.getInteger("Energy");
        int delta = Math.min(i, Math.min(energy,SEND));

        energy -= delta;
        itemStack.stackTagCompound.setInteger("Energy", energy);

        return delta;
    }

    @Override
    public int getEnergyStored(ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(itemStack, 0);
        }

        return itemStack.stackTagCompound.getInteger("Energy");
    }

    @Override
    public int getMaxEnergyStored(ItemStack itemStack) {
        return CAPACITY;
    }
}

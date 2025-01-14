package icbm.core.di;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import icbm.explosion.launcher.LauncherManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.prefab.tile.TileEntityDisableable;

public class TileElectricICBM extends TileEntityDisableable implements IEnergyReceiver {

    public EnergyStorage energyStorage;
    public int prevRF;

    public TileElectricICBM(int capacity,int send,int recieve) {
        super();
        energyStorage = new EnergyStorage(capacity,send,recieve);
    }




    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        int prevEnergy = energyStorage.getEnergyStored();
        int receivedEnergy = energyStorage.receiveEnergy(i,b);
        if(energyStorage.getEnergyStored() != prevEnergy) this.markDirty();
        return receivedEnergy;

    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt){
        super.writeToNBT(nbt);
        energyStorage.writeToNBT(nbt);
    }
    @Override
    public void readFromNBT(final NBTTagCompound nbt){
        super.readFromNBT(nbt);
        energyStorage.readFromNBT(nbt);

    }
}

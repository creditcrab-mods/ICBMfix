package icbm.explosion.launcher;

import calclavia.lib.TileEntityUniversalStorable;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.core.util.energy.EnergyStorageAdv;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import icbm.api.ILauncherController;
import icbm.api.LauncherType;
import icbm.core.IICBMPeripheral;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;

public abstract class TLauncherController
    extends TileEntityUniversalStorable implements ILauncherController, IICBMPeripheral, IEnergyReceiver {
    protected Vector3 target;
    protected int frequency;
    public EnergyStorage energyStorage = new EnergyStorage(320000, Integer.MAX_VALUE,Integer.MAX_VALUE);

    public TLauncherController() {
        this.target = null;
        this.frequency = 0;
        LauncherManager.addLauncher(this);
    }

    @Override
    public boolean canConnect(final ForgeDirection direction) {
        return true;
    }

    @Override
    public Vector3 getTarget() {
        if (this.target == null) {
            if (this.getLauncherType() == LauncherType.CRUISE) {
                this.target = new Vector3(this.xCoord, this.yCoord, this.zCoord);
            } else {
                this.target = new Vector3(this.xCoord, 0.0, this.zCoord);
            }
        }

        return this.target;
    }

    @Override
    public void setTarget(final Vector3 target) {
        this.target = target.floor();
    }

    @Override
    public int getFrequency() {
        return this.frequency;
    }

    @Override
    public void setFrequency(final int frequency) {
        this.frequency = frequency;
    }

    //TODO: Change to RF

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
    public String getType() {
        return "ICBMLauncher";
    }

    @Override
    public String[] getMethodNames() {
        return new String[] { "launch",       "getTarget",    "setTarget", "canLaunch",
                              "setFrequency", "getFrequency", "getMissile" };
    }

    @Override
    public Object[] callMethod(
        final IComputerAccess computer,
        ILuaContext ctx,
        final int method,
        final Object[] arguments
    ) throws LuaException {
        switch (method) {
            case 0: {
                this.launch();
                return null;
            }

            case 1: {
                return new Object[] { this.getTarget().x,
                                      this.getTarget().y,
                                      this.getTarget().z };
            }

            case 2: {
                if (arguments[0] != null && arguments[1] != null
                    && arguments[2] != null) {
                    try {
                        this.setTarget(new Vector3(
                            (double) arguments[0],
                            (double) arguments[1],
                            (double) arguments[2]
                        ));
                    } catch (final Exception e) {
                        e.printStackTrace();
                        throw new LuaException("Target Parameter is Invalid.");
                    }
                }

                return null;
            }

            case 3: {
                return new Object[] { this.canLaunch() };
            }

            case 4: {
                if (arguments[0] != null) {
                    try {
                        double arg = (double) arguments[0];
                        arg = Math.max(Math.min(arg, 32767.0), -32768.0);
                        this.setFrequency((short) arg);
                    } catch (final Exception e) {
                        e.printStackTrace();
                        throw new LuaException("Frequency Parameter is Invalid.");
                    }
                }

                return null;
            }

            case 5: {
                return new Object[] { this.getFrequency() };
            }

            case 6: {
                if (this.getMissile() != null) {
                    return new Object[] {
                        this.getMissile().getExplosiveType().getMissileName()
                    };
                }

                return null;
            }

            default: {
                throw new LuaException("Invalid ICBM Launcher Function.");
            }
        }
    }

    @Override
    public void attach(final IComputerAccess computer) {}

    @Override
    public void detach(final IComputerAccess computer) {}

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.setFrequency(nbt.getInteger("frequency"));
        this.energyStorage.setEnergyStored(nbt.getInteger("rf"));
        this.target = Vector3.readFromNBT(nbt.getCompoundTag("target"));
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("frequency", this.getFrequency());
        nbt.setInteger("rf",this.energyStorage.getEnergyStored());
        if (this.target != null) {
            nbt.setTag("target", this.target.writeToNBT(new NBTTagCompound()));
        }
    }
}
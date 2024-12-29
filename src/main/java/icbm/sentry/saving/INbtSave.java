package icbm.sentry.saving;

import net.minecraft.nbt.NBTTagCompound;

public interface INbtSave {
    String saveFileName();

    NBTTagCompound getSaveData();

    boolean shouldSave(final boolean p0);
}

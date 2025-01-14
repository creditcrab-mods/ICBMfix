package icbm.api;

import net.minecraft.item.ItemStack;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IRedstoneReceptor;

public interface ILauncherController
    extends IRedstoneReceptor, IBlockFrequency {
    LauncherType getLauncherType();

    void launch();

    boolean canLaunch();

    String getStatus();

    Vector3 getTarget();

    void setTarget(Vector3 var1);

    void placeMissile(ItemStack var1);

    IMissile getMissile();
}

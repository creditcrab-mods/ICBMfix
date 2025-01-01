package icbm.api.explosion;

import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public interface IEMPBlock {
    void onEMP(World var1, Vector3 var2, IExplosive var3);
}

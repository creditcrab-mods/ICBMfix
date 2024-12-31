package icbm.explosion.zhapin.explosive;

import icbm.explosion.zhapin.EntityExplosion;
import icbm.explosion.zhapin.ZhaPin;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public abstract class ExThr extends ZhaPin {
    protected ExThr(final String mingZi, final int ID, final int tier) {
        super(mingZi, ID, tier);
    }

    @Override
    public boolean doExplosion(
        final World worldObj,
        final Vector3 position,
        final Entity explosionSource,
        final int callCount
    ) {
        final EntityExplosion source = (EntityExplosion) explosionSource;

        if (!worldObj.isRemote && source.dataList1.size() > 0
            && source.dataList1.get(0) instanceof ThrSheXian) {
            final ThrSheXian thread = (ThrSheXian) source.dataList1.get(0);

            if (thread.isComplete) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected int proceduralInterval() {
        return 1;
    }
}

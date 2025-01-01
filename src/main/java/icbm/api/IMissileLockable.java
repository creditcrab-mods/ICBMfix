package icbm.api;

import universalelectricity.core.vector.Vector3;

public interface IMissileLockable {
    boolean canLock(IMissile var1);

    Vector3 getPredictedPosition(int var1);
}

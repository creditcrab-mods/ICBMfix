package icbm.sentry.task;

public class TaskKillTarget extends TaskSearchTarget {
    @Override
    protected boolean onUpdateTask() {
        super.onUpdateTask();

        if (super.tileEntity != null) {

            if (!super.tileEntity.isValidTarget(super.tileEntity.getTarget())) {
                //System.out.println("Targeting Target");
                super.tileEntity.setTarget(null);
                super.tileEntity.cancelRotation();
                return false;
            }

            if (super.tileEntity.canActivateWeapon()) {
                super.tileEntity.onWeaponActivated();
            } else {
                final float[] rotations = super.tileEntity.lookHelper.getDeltaRotations(
                    super.tileEntity.getTargetPosition()
                );
                super.tileEntity.rotateTo(rotations[0], rotations[1]);
            }
        }

        return true;
    }
}

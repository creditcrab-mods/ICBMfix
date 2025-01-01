package icbm.api;

public interface ILauncherContainer {
    IMissile getContainingMissile();

    void setContainingMissile(IMissile var1);

    ILauncherController getController();
}

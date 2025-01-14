package icbm.explosion;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import icbm.core.di.TileElectricICBM;
import icbm.explosion.missile.EMissile;
import icbm.explosion.launcher.TCruiseLauncher;
import icbm.explosion.launcher.TEmpTower;
import icbm.explosion.launcher.TLauncher;
import icbm.explosion.launcher.TLauncherControlPanel;
import icbm.explosion.launcher.TLauncherPlatform;
import icbm.explosion.launcher.TMissileCoordinator;
import icbm.explosion.launcher.TRadarTower;
import icbm.explosion.container.ContainerCruiseLauncher;
import icbm.explosion.container.ContainerLauncherPlatform;
import icbm.explosion.zhapin.TileEntityExplosive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public class CommonProxy implements IGuiHandler {
    public void preInit() {}

    public void init() {
        GameRegistry.registerTileEntity(TCruiseLauncher.class, "ICBMCruiseLauncher");
        GameRegistry.registerTileEntity(TLauncherPlatform.class, "ICBMLauncherPlatform");
        GameRegistry.registerTileEntity(
            TLauncherControlPanel.class, "ICBMMissileLauncher"
        );
        GameRegistry.registerTileEntity(TLauncher.class, "ICBMLauncher");
        GameRegistry.registerTileEntity(TRadarTower.class, "ICBMRadarTower");
        GameRegistry.registerTileEntity(TEmpTower.class, "ICBMEmpTower");
        GameRegistry.registerTileEntity(
            TMissileCoordinator.class, "ICBMMissileCoordinator"
        );
        GameRegistry.registerTileEntity(TileEntityExplosive.class, "ICBMExplosive");
    }

    public Object getClientGuiElement(
        final int ID,
        final EntityPlayer player,
        final World world,
        final int x,
        final int y,
        final int z
    ) {
        return null;
    }

    public Object getServerGuiElement(
        final int ID,
        final EntityPlayer player,
        final World world,
        final int x,
        final int y,
        final int z
    ) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            switch (ID) {
                case 1: {
                    return new ContainerCruiseLauncher(
                        player.inventory, (TCruiseLauncher) tileEntity
                    );
                }

                case 7: {
                    return new ContainerLauncherPlatform(player.inventory, (TLauncherPlatform) tileEntity);
                }
            }
        }

        return null;
    }

    public boolean isGaoQing() {
        return false;
    }

    public void spawnParticle(
        final String name,
        final World world,
        final Vector3 position,
        final float scale,
        final double distance
    ) {
        this.spawnParticle(name, world, position, 0.0, 0.0, 0.0, scale, distance);
    }

    public void spawnParticle(
        final String name,
        final World world,
        final Vector3 position,
        final double motionX,
        final double motionY,
        final double motionZ,
        final float scale,
        final double distance
    ) {
        this.spawnParticle(
            name,
            world,
            position,
            motionX,
            motionY,
            motionZ,
            1.0f,
            1.0f,
            1.0f,
            scale,
            distance
        );
    }

    public void spawnParticle(
        final String name,
        final World world,
        final Vector3 position,
        final double motionX,
        final double motionY,
        final double motionZ,
        final float red,
        final float green,
        final float blue,
        final float scale,
        final double distance
    ) {}

    public IUpdatePlayerListBox getDaoDanShengYin(final EMissile eDaoDan) {
        return null;
    }

    public int getParticleSetting() {
        return -1;
    }
}

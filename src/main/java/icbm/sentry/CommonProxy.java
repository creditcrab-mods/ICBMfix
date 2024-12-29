package icbm.sentry;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import icbm.sentry.container.ContainerTurretPlatform;
import icbm.sentry.platform.TileEntityTurretPlatform;
import icbm.sentry.turret.mount.TRailgunTurret;
import icbm.sentry.turret.sentries.TAATurret;
import icbm.sentry.turret.sentries.TLaserTurret;
import icbm.sentry.turret.sentries.TMachineGunTurret;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.multiblock.TileEntityMulti;

public class CommonProxy implements IGuiHandler {
    public static final int GUI_PLATFORM_ID = 0;
    public static final int GUI_PLATFORM_TERMINAL_ID = 1;
    public static final int GUI_PLATFORM_ACCESS_ID = 2;

    public void init() {
        GameRegistry.registerTileEntity(TMachineGunTurret.class, "ICBMGunTurret");
        GameRegistry.registerTileEntity(TAATurret.class, "ICBMAATurret");
        GameRegistry.registerTileEntity(TRailgunTurret.class, "ICBMRailgun");
        GameRegistry.registerTileEntity(TLaserTurret.class, "ICBMLeiSheF");
        GameRegistry.registerTileEntity(TileEntityTurretPlatform.class, "ICBMPlatform");
        GameRegistry.registerTileEntity(TileEntityMulti.class, "ICBMMultiblock");
    }

    public void preInit() {}

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
                case 0: {
                    return new ContainerTurretPlatform(
                        player.inventory, (TileEntityTurretPlatform) tileEntity
                    );
                }
            }
        }

        return null;
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

    public void
    renderTracer(final World world, final Vector3 position, final Vector3 target) {}

    public void renderBeam(
        final World world,
        final Vector3 position,
        final Vector3 target,
        final float red,
        final float green,
        final float blue,
        final int age
    ) {}
}

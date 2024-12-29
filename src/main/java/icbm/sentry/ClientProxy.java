package icbm.sentry;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import icbm.sentry.platform.TileEntityTurretPlatform;
import icbm.sentry.render.BlockRenderingHandler;
import icbm.sentry.render.FXBeam;
import icbm.sentry.render.RAATurret;
import icbm.sentry.render.RESeat;
import icbm.sentry.render.RGunTurret;
import icbm.sentry.render.RLaserTurret;
import icbm.sentry.render.RRailgun;
import icbm.sentry.gui.GuiPlatformAccess;
import icbm.sentry.gui.GuiPlatformSlots;
import icbm.sentry.gui.GuiPlatformTerminal;
import icbm.sentry.turret.mount.ESeat;
import icbm.sentry.turret.mount.TRailgunTurret;
import icbm.sentry.turret.sentries.TAATurret;
import icbm.sentry.turret.sentries.TLaserTurret;
import icbm.sentry.turret.sentries.TMachineGunTurret;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        ClientRegistry.bindTileEntitySpecialRenderer(
            TMachineGunTurret.class, (TileEntitySpecialRenderer) new RGunTurret()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TAATurret.class, (TileEntitySpecialRenderer) new RAATurret()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TRailgunTurret.class, (TileEntitySpecialRenderer) new RRailgun()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TLaserTurret.class, (TileEntitySpecialRenderer) new RLaserTurret()
        );
        RenderingRegistry.registerEntityRenderingHandler(
            ESeat.class, (Render) new RESeat()
        );
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler
        ) new BlockRenderingHandler());
    }

    @Override
    public Object getClientGuiElement(
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
                    return new GuiPlatformSlots(player.inventory, (TileEntityTurretPlatform) tileEntity);
                }

                case 1: {
                    return new GuiPlatformTerminal(player, (TileEntityTurretPlatform) tileEntity);
                }

                case 2: {
                    return new GuiPlatformAccess(player, (TileEntityTurretPlatform) tileEntity);
                }
            }
        }

        return null;
    }

    @Override
    public void renderBeam(
        final World world,
        final Vector3 position,
        final Vector3 target,
        final float red,
        final float green,
        final float blue,
        final int age
    ) {
        FMLClientHandler.instance().getClient().effectRenderer.addEffect((EntityFX
        ) new FXBeam(world, position, target, red, green, blue, age));
    }
}

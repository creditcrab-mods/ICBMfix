package icbm.explosion;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import icbm.explosion.cart.EntityCart;
import icbm.explosion.missile.EMissile;
import icbm.explosion.fx.FXAntimatter;
import icbm.explosion.fx.FXPortal;
import icbm.explosion.fx.FXShockwave;
import icbm.explosion.fx.FXSmoke;
import icbm.explosion.gui.GEmpTower;
import icbm.explosion.gui.GLauncherPlatform;
import icbm.explosion.gui.GCruiseLauncher;
import icbm.explosion.gui.GMissileLauncher;
import icbm.explosion.gui.GRadarTower;
import icbm.explosion.launcher.TCruiseLauncher;
import icbm.explosion.launcher.TEmpTower;
import icbm.explosion.launcher.TLauncher;
import icbm.explosion.launcher.TLauncherControlPanel;
import icbm.explosion.launcher.TLauncherPlatform;
import icbm.explosion.launcher.TMissileCoordinator;
import icbm.explosion.launcher.TRadarTower;
import icbm.explosion.render.REExplosives;
import icbm.explosion.render.REmpTower;
import icbm.explosion.render.RFaSheDi;
import icbm.explosion.render.RFaSheJia;
import icbm.explosion.render.RFeiBlock;
import icbm.explosion.render.RGuangBang;
import icbm.explosion.render.RHJiQi;
import icbm.explosion.render.RHZhaPin;
import icbm.explosion.render.RItDaoDan;
import icbm.explosion.render.RItRocketLauncher;
import icbm.explosion.render.RLauncherControlPanel;
import icbm.explosion.render.RMissile;
import icbm.explosion.render.RRadarTower;
import icbm.explosion.render.RSMine;
import icbm.explosion.render.RShouLiuDan;
import icbm.explosion.render.RShrapnel;
import icbm.explosion.render.RXiaoFaSheQi;
import icbm.explosion.render.RYinDaoQi;
import icbm.explosion.render.RZhaPin;
import icbm.explosion.zhapin.EntityExplosion;
import icbm.explosion.zhapin.EntityExplosive;
import icbm.explosion.zhapin.EntityGrenade;
import icbm.explosion.zhapin.TileEntityExplosive;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import universalelectricity.core.vector.Vector3;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        FMLCommonHandler.instance().bus().register(new TickHandler());
    }

    @Override
    public void init() {
        super.init();
        MinecraftForgeClient.registerItemRenderer(
            ICBMExplosion.itemRocketLauncher, (IItemRenderer) new RItRocketLauncher()
        );
        MinecraftForgeClient.registerItemRenderer(
            ICBMExplosion.itDaoDan, (IItemRenderer) new RItDaoDan()
        );
        MinecraftForgeClient.registerItemRenderer(
            ICBMExplosion.itTeBieDaoDan, (IItemRenderer) new RItDaoDan()
        );
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler
        ) new RHZhaPin());
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler) new RHJiQi()
        );
        RenderingRegistry.registerEntityRenderingHandler(
            EntityExplosive.class, (Render) new REExplosives()
        );
        RenderingRegistry.registerEntityRenderingHandler(
            EMissile.class, (Render) new RMissile(0.5f)
        );
        RenderingRegistry.registerEntityRenderingHandler(
            EntityExplosion.class, (Render) new RZhaPin()
        );
        RenderingRegistry.registerEntityRenderingHandler(
            EGravityBlock.class, (Render) new RFeiBlock()
        );
        RenderingRegistry.registerEntityRenderingHandler(
            ELightBeam.class, (Render) new RGuangBang()
        );
        RenderingRegistry.registerEntityRenderingHandler(
            ESuiPian.class, (Render) new RShrapnel()
        );
        RenderingRegistry.registerEntityRenderingHandler(
            EntityGrenade.class, (Render) new RShouLiuDan()
        );
        RenderingRegistry.registerEntityRenderingHandler(
            EntityCart.class, (Render) new RenderMinecart()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TCruiseLauncher.class, (TileEntitySpecialRenderer) new RXiaoFaSheQi()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TLauncherPlatform.class, (TileEntitySpecialRenderer) new RFaSheDi()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TLauncherControlPanel.class,
            (TileEntitySpecialRenderer) new RLauncherControlPanel()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TLauncher.class, (TileEntitySpecialRenderer) new RFaSheJia()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TRadarTower.class, (TileEntitySpecialRenderer) new RRadarTower()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TEmpTower.class, (TileEntitySpecialRenderer) new REmpTower()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityExplosive.class, (TileEntitySpecialRenderer) new RSMine()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TMissileCoordinator.class, (TileEntitySpecialRenderer) new RYinDaoQi()
        );
    }

    @Override
    public Object getClientGuiElement(
        final int ID,
        final EntityPlayer entityPlayer,
        final World world,
        final int x,
        final int y,
        final int z
    ) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity != null || ID == 5) {
            switch (ID) {
                case 1: {
                    return new GCruiseLauncher(
                        entityPlayer.inventory, (TCruiseLauncher) tileEntity
                    );
                }

                case 2: {
                    return new GMissileLauncher((TLauncherControlPanel) tileEntity);
                }

                case 3: {
                    return new GRadarTower((TRadarTower) tileEntity);
                }

                case 6: {
                    return new GEmpTower((TEmpTower) tileEntity);
                }

                case 7: {
                    return new GLauncherPlatform(
                        entityPlayer.inventory, (TLauncherPlatform) tileEntity
                    );
                }
            }
        }

        return null;
    }

    @Override
    public boolean isGaoQing() {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }

    @Override
    public int getParticleSetting() {
        return Minecraft.getMinecraft().gameSettings.particleSetting;
    }

    @Override
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
    ) {
        EntityFX fx = null;

        if (name == "smoke") {
            fx = (EntityFX
            ) new FXSmoke(world, position, red, green, blue, scale, distance);
        } else if (name == "missile_smoke") {
            fx = (EntityFX
            ) new FXSmoke(world, position, red, green, blue, scale, distance)
                     .setAge(100);
        } else if (name == "portal") {
            fx = (EntityFX
            ) new FXPortal(world, position, red, green, blue, scale, distance);
        } else if (name == "antimatter") {
            fx = new FXAntimatter(world, position, red, green, blue, scale, distance);
        } else if (name == "digging") {
            fx = (EntityFX) new EntityDiggingFX(
                world,
                position.x,
                position.y,
                position.z,
                motionX,
                motionY,
                motionZ,
                Block.getBlockById((int) red),
                0,
                (int) green
            );
            fx.multipleParticleScaleBy(blue);
        } else if (name == "shockwave") {
            fx = new FXShockwave(world, position, red, green, blue, scale, distance);
        }

        if (fx != null) {
            ((Entity) fx).motionX = motionX;
            ((Entity) fx).motionY = motionY;
            ((Entity) fx).motionZ = motionZ;
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
        }
    }

    @Override
    public IUpdatePlayerListBox getDaoDanShengYin(final EMissile eDaoDan) {
        // return (IUpdatePlayerListBox) new MissileSound(
        // Minecraft.getMinecraft().sndManager, eDaoDan,
        // (EntityPlayerSP)Minecraft.getMinecraft().thePlayer);

        return null;
    }
}

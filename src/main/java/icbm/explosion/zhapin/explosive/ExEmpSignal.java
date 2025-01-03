package icbm.explosion.zhapin.explosive;

import java.util.List;

import icbm.api.IMissile;
import icbm.api.RadarRegistry;
import icbm.api.explosion.IEMPItem;
import icbm.explosion.zhapin.EntityExplosive;
import icbm.explosion.zhapin.ZhaPin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;
import universalelectricity.api.item.ItemRF;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.core.item.RFItemHelper;
import universalelectricity.core.vector.Vector3;

public class ExEmpSignal extends ZhaPin {
    public ExEmpSignal(final String name, final int ID, final int tier) {
        super(name, ID, tier);
    }

    /**
     * @author      creditcrab
     <p>
     use callCount to differentiate explosion types as it is unused by the explosive.
    <ul>
    <li>-1 to affect all.</li>
    <li>0 for affecting players only.</li>
    <li>1 for affecting missles only.</li>
    </ul>

     <p>
     */
    @Override
    public boolean doExplosion(
        final World worldObj,
        final Vector3 position,
        final Entity explosionSource,
        final int radius,
        final int callCount
    ) {

        if (callCount != 0) {
            final List<Entity> entitiesNearby
                = RadarRegistry.getEntitiesWithinRadius(position.toVector2(), radius);

            for (final Entity entity : entitiesNearby) {
                if (entity instanceof IMissile && !entity.isEntityEqual(explosionSource)
                    && ((IMissile) entity).getTicksInAir() > -1) {
                    ((IMissile) entity).dropMissileAsItem();
                }
            }
        }

        if(callCount != 1){
            final AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(
                position.x - radius,
                position.y - radius,
                position.z - radius,
                position.x + radius,
                position.y + radius,
                position.z + radius
            );
            final List<Entity> entities
                = worldObj.getEntitiesWithinAABB(Entity.class, bounds);

            for (final Entity entity2 : entities) {
                if (entity2 instanceof EntityPlayer) {
                    final IInventory inventory
                        = ((EntityPlayer) entity2).inventory;

                    for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                        final ItemStack itemStack = inventory.getStackInSlot(i);

                        if (itemStack != null) {
                            if (itemStack.getItem() instanceof IEMPItem) {
                                ((IEMPItem) itemStack.getItem())
                                    .onEMP(itemStack, entity2, ZhaPin.emp);
                            }
                            if (RFItemHelper.isEnergyContainerItem(itemStack)) {
                                RFItemHelper.setDefaultEnergyTag(itemStack,0);
                                RFItemHelper.extractEnergyFromContainer(itemStack,1,false);
                            }
                        }
                    }
                } else {
                    if (entity2 instanceof EntityExplosive) entity2.setDead();
                }
            }
        }


        worldObj.playSoundEffect(
            position.x,
            position.y,
            position.z,
            "icbm:emp",
            4.0f,
            (1.0f + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2f) * 0.7f
        );
        return false;
    }

    @Override
    public float getRadius() {
        return 50.0f;
    }

    @Override
    public double getEnergy() {
        return 0.0;
    }
}

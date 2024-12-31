package icbm.explosion.item;

import icbm.core.di.ItElectricICBM;
import icbm.explosion.ICBMExplosion;
import icbm.explosion.missile.EMissile;
import icbm.explosion.missile.ItMissile;
import icbm.explosion.missile.ItModuleMissile;
import icbm.explosion.missile.MissileBase;
import icbm.explosion.zhapin.ZhaPin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.vector.Vector3;

public class ItemRocketLauncher extends ItElectricICBM {

    public static final int USED_ENERGY = 4000;
    public ItemRocketLauncher() {
        super("rocketLauncher");
        CAPACITY = 64000;
    }

    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    public ItemStack onItemRightClick(
        final ItemStack itemStack, final World world, final EntityPlayer player
    ) {
        if (!world.isRemote && this.getEnergyStored(itemStack) >= USED_ENERGY) {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                final ItemStack inventoryStack = player.inventory.getStackInSlot(i);

                if (inventoryStack != null
                    && inventoryStack.getItem() instanceof ItMissile) {
                    int haoMa = inventoryStack.getItemDamage();

                    if (inventoryStack.getItem() instanceof ItModuleMissile) {
                        haoMa += 100;
                    }

                    final MissileBase daoDan = MissileBase.list[haoMa];

                    if (daoDan != null
                        && !ICBMExplosion.shiBaoHu(
                            world,
                            new Vector3((Entity) player),
                            ZhaPin.ZhaPinType.DAO_DAN,
                            haoMa
                        )) {
                        if (daoDan.getTier() <= 2 && daoDan.isCruise()) {
                            final Vector3 diDian = Vector3.add(
                                new Vector3((Entity) player), new Vector3(0.0, 0.5, 0.0)
                            );
                            final Vector3 kan = new Vector3(player.getLook(1.0f));
                            final Vector3 kaiShiDiDian
                                = Vector3.add(diDian, Vector3.multiply(kan, 1.1));
                            final Vector3 muBiao
                                = Vector3.add(diDian, Vector3.multiply(kan, 100.0));
                            final EMissile eDaoDan = new EMissile(
                                world,
                                kaiShiDiDian,
                                daoDan.getID(),
                                ((Entity) player).rotationYaw,
                                ((Entity) player).rotationPitch
                            );
                            world.spawnEntityInWorld((Entity) eDaoDan);
                            eDaoDan.launch(muBiao);

                            if (!player.capabilities.isCreativeMode) {
                                player.inventory.setInventorySlotContents(
                                    i, (ItemStack) null
                                );
                            }

                            drainEnergy(itemStack,USED_ENERGY);
                            return itemStack;
                        }
                    } else {
                        player.addChatMessage(
                            new ChatComponentText("Region being is protected.")
                        );
                    }
                }
            }
        }

        return itemStack;
    }

    public double getVoltage(final ItemStack itemStack) {
        return 25.0;
    }

    public double getMaxJoules(final ItemStack itemStack) {
        return 100000.0;
    }
}

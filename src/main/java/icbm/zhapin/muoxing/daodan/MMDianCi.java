package icbm.zhapin.muoxing.daodan;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import icbm.core.di.MICBM;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class MMDianCi extends MICBM {
    ModelRenderer MAIN_MODULE;
    ModelRenderer MOTOR_MODULE_1;
    ModelRenderer MOTOR_MODULE_2;
    ModelRenderer MOTOR_MODULE_3;
    ModelRenderer MOTOR_MODULE_4;
    ModelRenderer C1;
    ModelRenderer C2;
    ModelRenderer C3;
    ModelRenderer C4;
    ModelRenderer T1;
    ModelRenderer T2;
    ModelRenderer T3;
    ModelRenderer T4;
    ModelRenderer WING_1A;
    ModelRenderer WING_2A;
    ModelRenderer WING_1B;
    ModelRenderer WING_2B;
    ModelRenderer WING_3A;
    ModelRenderer WING_3B;
    ModelRenderer WING_4B;
    ModelRenderer WING_4A;
    ModelRenderer TOP;
    ModelRenderer BASE;
    ModelRenderer MAIN_COIL;
    ModelRenderer COIL_1;
    ModelRenderer COIL_2;
    ModelRenderer COIL_3;
    ModelRenderer COIL_4;
    ModelRenderer WARHEAD;

    public MMDianCi() {
        super.textureWidth = 128;
        super.textureHeight = 128;
        (this.MAIN_MODULE = new ModelRenderer((ModelBase) this, 0, 0))
            .addBox(0.0f, 0.0f, 0.0f, 8, 70, 8);
        this.MAIN_MODULE.setRotationPoint(-4.0f, -63.0f, -4.0f);
        this.MAIN_MODULE.setTextureSize(128, 128);
        this.MAIN_MODULE.mirror = true;
        this.setRotation(this.MAIN_MODULE, 0.0f, 0.0f, 0.0f);
        (this.MOTOR_MODULE_1 = new ModelRenderer((ModelBase) this, 0, 79))
            .addBox(0.0f, 0.0f, 0.0f, 6, 20, 6);
        this.MOTOR_MODULE_1.setRotationPoint(-8.0f, 0.0f, -8.0f);
        this.MOTOR_MODULE_1.setTextureSize(128, 128);
        this.MOTOR_MODULE_1.mirror = true;
        this.setRotation(this.MOTOR_MODULE_1, 0.0f, 0.0f, 0.0f);
        (this.MOTOR_MODULE_2 = new ModelRenderer((ModelBase) this, 0, 79))
            .addBox(0.0f, 0.0f, 0.0f, 6, 20, 6);
        this.MOTOR_MODULE_2.setRotationPoint(-8.0f, 0.0f, 2.0f);
        this.MOTOR_MODULE_2.setTextureSize(128, 128);
        this.MOTOR_MODULE_2.mirror = true;
        this.setRotation(this.MOTOR_MODULE_2, 0.0f, 0.0f, 0.0f);
        (this.MOTOR_MODULE_3 = new ModelRenderer((ModelBase) this, 0, 79))
            .addBox(0.0f, 0.0f, 0.0f, 6, 20, 6);
        this.MOTOR_MODULE_3.setRotationPoint(2.0f, 0.0f, -8.0f);
        this.MOTOR_MODULE_3.setTextureSize(128, 128);
        this.MOTOR_MODULE_3.mirror = true;
        this.setRotation(this.MOTOR_MODULE_3, 0.0f, 0.0f, 0.0f);
        (this.MOTOR_MODULE_4 = new ModelRenderer((ModelBase) this, 0, 79))
            .addBox(0.0f, 0.0f, 0.0f, 6, 20, 6);
        this.MOTOR_MODULE_4.setRotationPoint(2.0f, 0.0f, 2.0f);
        this.MOTOR_MODULE_4.setTextureSize(128, 128);
        this.MOTOR_MODULE_4.mirror = true;
        this.setRotation(this.MOTOR_MODULE_4, 0.0f, 0.0f, 0.0f);
        (this.C1 = new ModelRenderer((ModelBase) this, 0, 106))
            .addBox(0.0f, 0.0f, 0.0f, 2, 1, 2);
        this.C1.setRotationPoint(-6.0f, 20.0f, -6.0f);
        this.C1.setTextureSize(128, 128);
        this.C1.mirror = true;
        this.setRotation(this.C1, 0.0f, 0.0f, 0.0f);
        (this.C2 = new ModelRenderer((ModelBase) this, 0, 106))
            .addBox(0.0f, 0.0f, 0.0f, 2, 1, 2);
        this.C2.setRotationPoint(-6.0f, 20.0f, 4.0f);
        this.C2.setTextureSize(128, 128);
        this.C2.mirror = true;
        this.setRotation(this.C2, 0.0f, 0.0f, 0.0f);
        (this.C3 = new ModelRenderer((ModelBase) this, 0, 106))
            .addBox(0.0f, 0.0f, 0.0f, 2, 1, 2);
        this.C3.setRotationPoint(4.0f, 20.0f, -6.0f);
        this.C3.setTextureSize(128, 128);
        this.C3.mirror = true;
        this.setRotation(this.C3, 0.0f, 0.0f, 0.0f);
        (this.C4 = new ModelRenderer((ModelBase) this, 0, 106))
            .addBox(0.0f, 0.0f, 0.0f, 2, 1, 2);
        this.C4.setRotationPoint(4.0f, 20.0f, 4.0f);
        this.C4.setTextureSize(128, 128);
        this.C4.mirror = true;
        this.setRotation(this.C4, 0.0f, 0.0f, 0.0f);
        (this.T1 = new ModelRenderer((ModelBase) this, 0, 111))
            .addBox(0.0f, 0.0f, 0.0f, 4, 3, 4);
        this.T1.setRotationPoint(-7.0f, 21.0f, -7.0f);
        this.T1.setTextureSize(128, 128);
        this.T1.mirror = true;
        this.setRotation(this.T1, 0.0f, 0.0f, 0.0f);
        (this.T2 = new ModelRenderer((ModelBase) this, 0, 111))
            .addBox(0.0f, 0.0f, 0.0f, 4, 3, 4);
        this.T2.setRotationPoint(-7.0f, 21.0f, 3.0f);
        this.T2.setTextureSize(128, 128);
        this.T2.mirror = true;
        this.setRotation(this.T2, 0.0f, 0.0f, 0.0f);
        (this.T3 = new ModelRenderer((ModelBase) this, 0, 111))
            .addBox(0.0f, 0.0f, 0.0f, 4, 3, 4);
        this.T3.setRotationPoint(3.0f, 21.0f, -7.0f);
        this.T3.setTextureSize(128, 128);
        this.T3.mirror = true;
        this.setRotation(this.T3, 0.0f, 0.0f, 0.0f);
        (this.T4 = new ModelRenderer((ModelBase) this, 0, 111))
            .addBox(0.0f, 0.0f, 0.0f, 4, 3, 4);
        this.T4.setRotationPoint(3.0f, 21.0f, 3.0f);
        this.T4.setTextureSize(128, 128);
        this.T4.mirror = true;
        this.setRotation(this.T4, 0.0f, 0.0f, 0.0f);
        (this.WING_1A = new ModelRenderer((ModelBase) this, 43, 15))
            .addBox(-1.0f, 0.0f, 0.0f, 2, 12, 12);
        this.WING_1A.setRotationPoint(0.0f, -10.0f, 0.0f);
        this.WING_1A.setTextureSize(128, 128);
        this.WING_1A.mirror = true;
        this.setRotation(this.WING_1A, -0.7853982f, 0.0f, 0.0f);
        (this.WING_2A = new ModelRenderer((ModelBase) this, 43, 0))
            .addBox(0.0f, 0.0f, -1.0f, 12, 12, 2);
        this.WING_2A.setRotationPoint(0.0f, -10.0f, 0.0f);
        this.WING_2A.setTextureSize(128, 128);
        this.WING_2A.mirror = true;
        this.setRotation(this.WING_2A, 0.0f, 0.0f, 0.7853982f);
        (this.WING_1B = new ModelRenderer((ModelBase) this, 72, 28))
            .addBox(-1.0f, 0.0f, -8.0f, 2, 25, 16);
        this.WING_1B.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.WING_1B.setTextureSize(128, 128);
        this.WING_1B.mirror = true;
        this.setRotation(this.WING_1B, 0.0f, 0.0f, 0.0f);
        (this.WING_2B = new ModelRenderer((ModelBase) this, 72, 0))
            .addBox(-8.0f, 0.0f, -1.0f, 16, 25, 2);
        this.WING_2B.setRotationPoint(0.0f, -2.0f, 0.0f);
        this.WING_2B.setTextureSize(128, 128);
        this.WING_2B.mirror = true;
        this.setRotation(this.WING_2B, 0.0f, 0.0f, 0.0f);
        (this.WING_3A = new ModelRenderer((ModelBase) this, 34, 55))
            .addBox(-1.0f, 0.0f, -8.0f, 2, 10, 16);
        this.WING_3A.setRotationPoint(0.0f, -41.0f, 0.0f);
        this.WING_3A.setTextureSize(128, 128);
        this.WING_3A.mirror = true;
        this.setRotation(this.WING_3A, 0.0f, 0.0f, 0.0f);
        (this.WING_3B = new ModelRenderer((ModelBase) this, 34, 82))
            .addBox(-1.0f, -6.0f, -6.0f, 2, 12, 12);
        this.WING_3B.setRotationPoint(0.0f, -41.0f, 0.0f);
        this.WING_3B.setTextureSize(128, 128);
        this.WING_3B.mirror = true;
        this.setRotation(this.WING_3B, 0.7853982f, 0.0f, 0.0f);
        (this.WING_4B = new ModelRenderer((ModelBase) this, 34, 41))
            .addBox(-8.0f, 0.0f, -1.0f, 16, 10, 2);
        this.WING_4B.setRotationPoint(0.0f, -41.0f, 0.0f);
        this.WING_4B.setTextureSize(128, 128);
        this.WING_4B.mirror = true;
        this.setRotation(this.WING_4B, 0.0f, 0.0f, 0.0f);
        (this.WING_4A = new ModelRenderer((ModelBase) this, 34, 107))
            .addBox(-6.0f, -6.0f, -1.0f, 12, 12, 2);
        this.WING_4A.setRotationPoint(0.0f, -41.0f, 0.0f);
        this.WING_4A.setTextureSize(128, 128);
        this.WING_4A.mirror = true;
        this.setRotation(this.WING_4A, 0.0f, 0.0f, -0.7853982f);
        (this.TOP = new ModelRenderer((ModelBase) this, 72, 70))
            .addBox(0.0f, 0.0f, 0.0f, 10, 10, 10);
        this.TOP.setRotationPoint(-5.0f, -64.0f, -5.0f);
        this.TOP.setTextureSize(128, 128);
        this.TOP.mirror = true;
        this.setRotation(this.TOP, 0.0f, 0.0f, 0.0f);
        (this.BASE = new ModelRenderer((ModelBase) this, 64, 92))
            .addBox(-6.0f, 0.0f, -6.0f, 12, 1, 12);
        this.BASE.setRotationPoint(0.0f, -65.0f, 0.0f);
        this.BASE.setTextureSize(128, 128);
        this.BASE.mirror = true;
        this.setRotation(this.BASE, 0.0f, 0.0f, 0.0f);
        (this.MAIN_COIL = new ModelRenderer((ModelBase) this, 64, 107))
            .addBox(-3.0f, 0.0f, -3.0f, 6, 10, 6);
        this.MAIN_COIL.setRotationPoint(0.0f, -75.0f, 0.0f);
        this.MAIN_COIL.setTextureSize(128, 128);
        this.MAIN_COIL.mirror = true;
        this.setRotation(this.MAIN_COIL, 0.0f, 0.0f, 0.0f);
        (this.COIL_1 = new ModelRenderer((ModelBase) this, 90, 107))
            .addBox(3.0f, 0.0f, -5.0f, 2, 8, 2);
        this.COIL_1.setRotationPoint(0.0f, -73.0f, 0.0f);
        this.COIL_1.setTextureSize(128, 128);
        this.COIL_1.mirror = true;
        this.setRotation(this.COIL_1, 0.0f, 0.0f, 0.0f);
        (this.COIL_2 = new ModelRenderer((ModelBase) this, 90, 107))
            .addBox(-5.0f, 0.0f, 3.0f, 2, 8, 2);
        this.COIL_2.setRotationPoint(0.0f, -73.0f, 0.0f);
        this.COIL_2.setTextureSize(128, 128);
        this.COIL_2.mirror = true;
        this.setRotation(this.COIL_2, 0.0f, 0.0f, 0.0f);
        (this.COIL_3 = new ModelRenderer((ModelBase) this, 90, 107))
            .addBox(-5.0f, 0.0f, -5.0f, 2, 8, 2);
        this.COIL_3.setRotationPoint(0.0f, -73.0f, 0.0f);
        this.COIL_3.setTextureSize(128, 128);
        this.COIL_3.mirror = true;
        this.setRotation(this.COIL_3, 0.0f, 0.0f, 0.0f);
        (this.COIL_4 = new ModelRenderer((ModelBase) this, 90, 107))
            .addBox(3.0f, 0.0f, 3.0f, 2, 8, 2);
        this.COIL_4.setRotationPoint(0.0f, -73.0f, 0.0f);
        this.COIL_4.setTextureSize(128, 128);
        this.COIL_4.mirror = true;
        this.setRotation(this.COIL_4, 0.0f, 0.0f, 0.0f);
        (this.WARHEAD = new ModelRenderer((ModelBase) this, 100, 107))
            .addBox(-1.0f, -4.0f, -1.0f, 2, 4, 2);
        this.WARHEAD.setRotationPoint(0.0f, -75.0f, 0.0f);
        this.WARHEAD.setTextureSize(128, 128);
        this.WARHEAD.mirror = true;
        this.setRotation(this.WARHEAD, 0.0f, 0.0f, 0.0f);
    }

    public void render(
        final Entity entity,
        final float f,
        final float f1,
        final float f2,
        final float f3,
        final float f4,
        final float f5
    ) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.render(f5);
    }

    @Override
    public void render(final float f5) {
        this.MAIN_MODULE.render(f5);
        this.MOTOR_MODULE_1.render(f5);
        this.MOTOR_MODULE_2.render(f5);
        this.MOTOR_MODULE_3.render(f5);
        this.MOTOR_MODULE_4.render(f5);
        this.C1.render(f5);
        this.C2.render(f5);
        this.C3.render(f5);
        this.C4.render(f5);
        this.T1.render(f5);
        this.T2.render(f5);
        this.T3.render(f5);
        this.T4.render(f5);
        this.WING_1A.render(f5);
        this.WING_2A.render(f5);
        this.WING_1B.render(f5);
        this.WING_2B.render(f5);
        this.WING_3A.render(f5);
        this.WING_3B.render(f5);
        this.WING_4B.render(f5);
        this.WING_4A.render(f5);
        this.TOP.render(f5);
        this.BASE.render(f5);
        this.MAIN_COIL.render(f5);
        this.COIL_1.render(f5);
        this.COIL_2.render(f5);
        this.COIL_3.render(f5);
        this.COIL_4.render(f5);
        this.WARHEAD.render(f5);
    }

    private void
    setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

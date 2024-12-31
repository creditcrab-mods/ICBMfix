package icbm.explosion.potion;

import icbm.core.MainBase;
import universalelectricity.prefab.potion.CustomPotion;

public abstract class PotionICBM extends CustomPotion {
    public PotionICBM(
        final int id, final boolean isBadEffect, final int color, final String name
    ) {
        super(
            MainBase.CONFIGURATION.get("Potion", name, id).getInt(id),
            isBadEffect,
            color,
            name
        );
    }
}

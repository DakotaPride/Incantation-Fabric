package net.dakotapride.incantation.common.soulsComeAlive.entity.source;

import net.minecraft.entity.damage.DamageSource;

public class IncantationDamageSource extends DamageSource {
    public final String name;
    private boolean bypassesArmor;
    private boolean unblockable;
    private boolean bypassesProtection;
    private float exhaustion = 0.1F;

    public static final IncantationDamageSource SIREN_BREATHING =
            (new IncantationDamageSource("siren_breathing")).setBypassesArmor().setUnblockable();

    protected IncantationDamageSource setBypassesArmor() {
        this.bypassesArmor = true;
        this.exhaustion = 0.0F;
        return this;
    }

    protected IncantationDamageSource setUnblockable() {
        this.unblockable = true;
        this.exhaustion = 0.0F;
        return this;
    }

    protected IncantationDamageSource(String name) {
        super(name);
        this.name = name;
    }

}

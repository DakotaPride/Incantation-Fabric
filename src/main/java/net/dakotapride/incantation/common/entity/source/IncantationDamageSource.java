package net.dakotapride.incantation.common.entity.source;

import net.minecraft.entity.damage.DamageSource;

public class IncantationDamageSource extends DamageSource {
    public final String name;
    private boolean bypassesArmor;
    private boolean unblockable;
    private float exhaustion = 0.1F;
    private boolean magic;

    public static final IncantationDamageSource SIREN_BREATHING =
            (new IncantationDamageSource("siren_breathing")).setBypassesArmor().setUnblockable();
    public static final IncantationDamageSource RADIANCE_PLAGUE =
            (new IncantationDamageSource("radiance_plague")).setBypassesArmor().setUsesMagic();

    protected IncantationDamageSource setBypassesArmor() {
        this.bypassesArmor = true;
        this.exhaustion = 0.0F;
        return this;
    }

    public IncantationDamageSource setUsesMagic() {
        this.magic = true;
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

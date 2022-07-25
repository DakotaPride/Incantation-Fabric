package net.dakotapride.incantation.common.effect;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.entity.source.IncantationDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class RadianceStatusEffect extends EmptyStatusEffect {
    public RadianceStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.hasStatusEffect(IncantationMod.RADIANCE)) {
            entity.damage(IncantationDamageSource.RADIANCE_PLAGUE, 0.5F);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i;
        if (this == IncantationMod.RADIANCE) {
            i = 25 >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            } else {
                return true;
            }
        } else {
            return this == StatusEffects.HUNGER;
        }
    }
}

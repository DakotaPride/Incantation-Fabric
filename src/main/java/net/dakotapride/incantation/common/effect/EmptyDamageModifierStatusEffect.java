package net.dakotapride.incantation.common.effect;

import net.minecraft.entity.effect.DamageModifierStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class EmptyDamageModifierStatusEffect extends DamageModifierStatusEffect {
    public EmptyDamageModifierStatusEffect(StatusEffectCategory category, int color, double modifier) {
        super(category, color, modifier);
    }
}

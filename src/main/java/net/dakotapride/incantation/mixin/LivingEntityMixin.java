package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.dakotapride.incantation.compat.pickyourpoison.PickYourPoisonCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean clearStatusEffects();

    private final LivingEntity livingEntity = (LivingEntity) (Object) this;
    private final LivingEntity attacker = livingEntity.getAttacker();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (livingEntity.hasStatusEffect(IncantationMod.TOLERANCE)) {
            livingEntity.clearStatusEffects();
        }

        if (livingEntity.hasStatusEffect(PickYourPoisonCompat.ECHOING)) {
            if (!(livingEntity.getAttacker() == null)) {
                livingEntity.getActiveStatusEffects().forEach((statusEffect, statusEffectInstance) -> {
                    if (!attacker.hasStatusEffect(statusEffect) || (attacker.getStatusEffect(statusEffect) != null
                            && Objects.requireNonNull(attacker.getStatusEffect(statusEffect)).getAmplifier() <= statusEffectInstance.getAmplifier())) {
                        attacker.addStatusEffect(new StatusEffectInstance(statusEffect, 60, statusEffectInstance.getAmplifier()));
                    }
                });
            }
        }

        if (livingEntity.hasStatusEffect(MoreWeaponryCompat.UNWOUNDED)) {
            livingEntity.removeStatusEffect(StatusEffects.POISON);
            livingEntity.removeStatusEffect(StatusEffects.WITHER);
            livingEntity.removeStatusEffect(StatusEffects.INSTANT_DAMAGE);
        }

        if (livingEntity.hasStatusEffect(IncantationMod.RA_WRATH)) {
            livingEntity.setFireTicks(40);
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 300, 2));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 1));
            livingEntity.removeStatusEffect(StatusEffects.HUNGER);
        }

        if (livingEntity.hasStatusEffect(IncantationMod.BLEAK)) {
            livingEntity.setFrozenTicks(0);
        }

    }

}

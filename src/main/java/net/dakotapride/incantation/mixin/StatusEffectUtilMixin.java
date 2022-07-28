package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.util.update_classes.SoulsComeAlive;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectUtil.class)
public class StatusEffectUtilMixin {

    @Inject(method = "hasWaterBreathing", at = @At("TAIL"), cancellable = true)
    private static void hasWaterBreathing(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(entity.hasStatusEffect(StatusEffects.WATER_BREATHING)
                || entity.hasStatusEffect(StatusEffects.CONDUIT_POWER)
                || entity.hasStatusEffect(SoulsComeAlive.SIREN_WARNING));
    }


}

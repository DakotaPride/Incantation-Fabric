package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin<O> extends Entity {
    private final LivingEntity livingEntity = (LivingEntity) (Object) this;
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "hasStatusEffect", at = @At("HEAD"))
    private void tick(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity.hasStatusEffect(IncantationMod.MILKY_RESISTANCE)) {
            livingEntity.getActiveStatusEffects() = !effect.isBeneficial();
        }
    }

}

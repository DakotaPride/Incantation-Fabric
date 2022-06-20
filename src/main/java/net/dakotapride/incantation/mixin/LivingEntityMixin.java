package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin<O> extends Entity {
    private final LivingEntity livingEntity = (LivingEntity) (Object) this;
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (livingEntity.hasStatusEffect(IncantationMod.MILKY_RESISTANCE)) {
            livingEntity.clearStatusEffects();
        }

        if (livingEntity.hasStatusEffect(MoreWeaponryCompat.HARMING_RESISTANCE)) {
            livingEntity.removeStatusEffect(StatusEffects.POISON);
            livingEntity.removeStatusEffect(StatusEffects.WITHER);
        }

    }

}

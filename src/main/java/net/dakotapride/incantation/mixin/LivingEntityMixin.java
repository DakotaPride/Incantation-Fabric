package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.util.IncantationTags;
import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.dakotapride.incantation.compat.pickyourpoison.PickYourPoisonCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
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

        if (livingEntity.hasStatusEffect(IncantationMod.FLESHY_PUNISHMENT)) {
            livingEntity.setFireTicks(40);
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 300, 2));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 1));
            livingEntity.removeStatusEffect(StatusEffects.HUNGER);
        }

        if (livingEntity.hasStatusEffect(PickYourPoisonCompat.FOREIGN_POISON_RESISTANCE)) {
            livingEntity.removeStatusEffect(IncantationTags.PICK_YOUR_POISON_EFFECTS);
        }

    }

}
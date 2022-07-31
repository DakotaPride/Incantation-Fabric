package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.util.update_classes.SoulsComeAlive;
import net.dakotapride.incantation.common.util.IncantationTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "onSteppedOn", at = @At("HEAD"))
    private void incantation$onGoldenBlockSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(SoulsComeAlive.MIDAS_FAVOUR)) {
                if (state.isIn(IncantationTags.ACCEPTABLE_MIDAS_FAVOUR)) {
                    if (Objects.requireNonNull(livingEntity.getStatusEffect(SoulsComeAlive.MIDAS_FAVOUR)).getAmplifier() == 0) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 0));
                    } else if (Objects.requireNonNull(livingEntity.getStatusEffect(SoulsComeAlive.MIDAS_FAVOUR)).getAmplifier() == 1) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 1));
                    } else if (Objects.requireNonNull(livingEntity.getStatusEffect(SoulsComeAlive.MIDAS_FAVOUR)).getAmplifier() == 2) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 2));
                    } else if (Objects.requireNonNull(livingEntity.getStatusEffect(SoulsComeAlive.MIDAS_FAVOUR)).getAmplifier() == 3) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 3));
                    } else if (Objects.requireNonNull(livingEntity.getStatusEffect(SoulsComeAlive.MIDAS_FAVOUR)).getAmplifier() == 4) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 4));
                    } else {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 5));
                    }
                }
            }
        }
    }
}

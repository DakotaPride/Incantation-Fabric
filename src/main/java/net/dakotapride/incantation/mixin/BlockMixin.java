package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.util.SoulsComeAlive;
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

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "onSteppedOn", at = @At("HEAD"))
    private void incantation$onGoldenBlockSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity) {
            BlockState getBlockBelowEntity = livingEntity.getSteppingBlockState();
            if (livingEntity.hasStatusEffect(SoulsComeAlive.MIDAS_FAVOUR)) {
                if (getBlockBelowEntity.isIn(IncantationTags.ACCEPTABLE_MIDAS_FAVOUR)) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 1));
                }
            }
        }
    }
}

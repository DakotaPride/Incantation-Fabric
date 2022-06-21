package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.util.IncantationTags;
import net.dakotapride.incantation.compat.enhancedcelestials.EnhancedCelestialsCompat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void canHaveStatusEffect(World world, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if (entity.getActiveItem().isIn(IncantationTags.HARVESTABLE_FOODS)
                || entity.getActiveItem().isIn(IncantationTags.CROPTOPIA_HARVESTABLE_FOODS)
                || entity.getActiveItem().isIn(IncantationTags.MOREWEAPONRY_HARVESTABLE_FOODS)) {
            entity.addStatusEffect(new StatusEffectInstance(EnhancedCelestialsCompat.HARVEST_HEALING, 300, 0));
        }
    }

}

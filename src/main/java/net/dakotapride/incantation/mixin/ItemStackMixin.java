package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.util.IncantationTags;
import net.dakotapride.incantation.compat.enhancedcelestials.EnhancedCelestialsCompat;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void canHaveStatusEffect(World world, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if (FabricLoader.getInstance().isModLoaded("enhancedcelestials")) {
            if (entity.getActiveItem().isIn(IncantationTags.HARVESTABLE_FOODS)
                    && entity.hasStatusEffect(EnhancedCelestialsCompat.HARVEST_HEALING)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 0));
            }
        }

        if (entity.getActiveItem().isOf(Items.HONEY_BOTTLE)
                || entity.getActiveItem().isOf(Items.POTION)
                || entity.getActiveItem().isOf(Items.SPLASH_POTION)
                || entity.getActiveItem().isOf(Items.MELON_SLICE)
                || entity.getActiveItem().isOf(EnhancedCelestialsCompat.MENDING_MOON_CREST_FRUIT)
                || entity.getActiveItem().isOf(EnhancedCelestialsCompat.MOON_CREST_FRUIT)) {
            entity.removeStatusEffect(IncantationMod.FLESHY_PUNISHMENT);
            entity.setFireTicks(0);
        }
    }

}

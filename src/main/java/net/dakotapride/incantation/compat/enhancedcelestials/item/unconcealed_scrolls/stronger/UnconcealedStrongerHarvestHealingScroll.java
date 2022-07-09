package net.dakotapride.incantation.compat.enhancedcelestials.item.unconcealed_scrolls.stronger;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.item.FreezingResistanceScrollItem;
import net.dakotapride.incantation.compat.enhancedcelestials.EnhancedCelestialsCompat;
import net.dakotapride.incantation.compat.enhancedcelestials.item.HarvestHealingScrollItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnconcealedStrongerHarvestHealingScroll extends HarvestHealingScrollItem {
    public UnconcealedStrongerHarvestHealingScroll(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(EnhancedCelestialsCompat.HARVEST_HEALING, 460, 1));
        itemStack.decrement(1);
        user.getItemCooldownManager().set(this, 500);
        return TypedActionResult.success(itemStack);
    }
}

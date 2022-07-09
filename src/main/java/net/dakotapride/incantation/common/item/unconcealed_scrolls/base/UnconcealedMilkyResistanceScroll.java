package net.dakotapride.incantation.common.item.unconcealed_scrolls.base;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.item.MilkyResistanceScrollItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnconcealedMilkyResistanceScroll extends MilkyResistanceScrollItem {
    public UnconcealedMilkyResistanceScroll(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(IncantationMod.MILKY_RESISTANCE, 460, 0));
        itemStack.decrement(1);
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(itemStack);
    }
}

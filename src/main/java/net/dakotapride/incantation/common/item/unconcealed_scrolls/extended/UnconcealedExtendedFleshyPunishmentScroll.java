package net.dakotapride.incantation.common.item.unconcealed_scrolls.extended;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.item.FreezingResistanceScrollItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnconcealedExtendedFleshyPunishmentScroll extends FreezingResistanceScrollItem {
    public UnconcealedExtendedFleshyPunishmentScroll(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(IncantationMod.FLESHY_PUNISHMENT, 740, 0));
        itemStack.decrement(1);
        user.getItemCooldownManager().set(this, 800);
        return TypedActionResult.success(itemStack);
    }
}

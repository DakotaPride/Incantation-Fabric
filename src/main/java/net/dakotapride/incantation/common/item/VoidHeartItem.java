package net.dakotapride.incantation.common.item;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VoidHeartItem extends KingsoulItem {
    public VoidHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.getOffHandStack().isOf(this)) {
            user.addStatusEffect(new StatusEffectInstance(IncantationMod.DUSK, 100, 1));
            stack.decrement(1);
        }
        return super.use(world, user, hand);
    }
}

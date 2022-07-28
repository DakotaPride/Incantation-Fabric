package net.dakotapride.incantation.common.item.hollow_knight;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class KingsoulItem extends Item {
    public KingsoulItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.getMainHandStack().isOf(Items.OBSIDIAN)) {
            stack.decrement(1);
            user.giveItemStack(new ItemStack(IncantationMod.VOID_HEART, 1));
            user.getItemCooldownManager().set(this, 300);
        }
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}

package net.dakotapride.incantation.common.item.unconcealed_scrolls.extended;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.item.FreezingResistanceScrollItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnconcealedExtendedFreezingResistanceScroll extends FreezingResistanceScrollItem {
    public UnconcealedExtendedFreezingResistanceScroll(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(IncantationMod.FREEZING_RESISTANCE, 740, 0));
        itemStack.damage(1, user, (player) -> player.sendToolBreakStatus(player.getActiveHand()));
        user.getItemCooldownManager().set(this, 800);
        return TypedActionResult.success(itemStack);
    }
}

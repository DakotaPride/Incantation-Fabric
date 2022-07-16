package net.dakotapride.incantation.compat.enhancedcelestials.item.unconcealed_scrolls.base;

import net.dakotapride.incantation.compat.enhancedcelestials.EnhancedCelestialsCompat;
import net.dakotapride.incantation.compat.enhancedcelestials.item.HarvestHealingScrollItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnconcealedHarvestHealingScroll extends HarvestHealingScrollItem {
    public UnconcealedHarvestHealingScroll(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(EnhancedCelestialsCompat.HARVEST_HEALING, 460, 0));
        itemStack.decrement(1);
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(itemStack);
    }
}

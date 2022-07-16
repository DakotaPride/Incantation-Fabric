package net.dakotapride.incantation.compat.moreweaponry.item.unconcealed_scrolls.frosted;

import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.dakotapride.incantation.compat.moreweaponry.item.HarmingResistanceScrollItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnconcealedFrostedHarmingResistanceScroll extends HarmingResistanceScrollItem {
    public UnconcealedFrostedHarmingResistanceScroll(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(MoreWeaponryCompat.HARMING_RESISTANCE, 460, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 520, 1));
        itemStack.damage(1, user, (player) -> player.sendToolBreakStatus(player.getActiveHand()));
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(itemStack);
    }
}

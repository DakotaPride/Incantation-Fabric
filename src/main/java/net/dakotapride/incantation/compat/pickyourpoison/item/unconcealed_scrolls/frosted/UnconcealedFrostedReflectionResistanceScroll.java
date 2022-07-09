package net.dakotapride.incantation.compat.pickyourpoison.item.unconcealed_scrolls.frosted;

import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.dakotapride.incantation.compat.moreweaponry.item.HarmingResistanceScrollItem;
import net.dakotapride.incantation.compat.pickyourpoison.PickYourPoisonCompat;
import net.dakotapride.incantation.compat.pickyourpoison.item.ReflectionResistanceScrollItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnconcealedFrostedReflectionResistanceScroll extends ReflectionResistanceScrollItem {
    public UnconcealedFrostedReflectionResistanceScroll(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(PickYourPoisonCompat.REFLECTION_RESISTANCE, 460, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 520, 1));
        itemStack.decrement(1);
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(itemStack);
    }
}

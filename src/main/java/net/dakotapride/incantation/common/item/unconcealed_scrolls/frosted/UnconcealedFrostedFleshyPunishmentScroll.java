package net.dakotapride.incantation.common.item.unconcealed_scrolls.frosted;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.item.FleshyPunishmentScrollItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnconcealedFrostedFleshyPunishmentScroll extends FleshyPunishmentScrollItem {
    public UnconcealedFrostedFleshyPunishmentScroll(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(IncantationMod.FLESHY_PUNISHMENT, 460, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 520, 1));
        itemStack.damage(1, user, (player) -> player.sendToolBreakStatus(player.getActiveHand()));
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(itemStack);
    }
}

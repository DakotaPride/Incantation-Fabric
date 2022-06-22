package net.dakotapride.incantation.common.item;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FleshyPunishmentScrollItem extends EffectScrollItem {
    public FleshyPunishmentScrollItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            if (!user.hasStatusEffect(IncantationMod.INCANTATION_LVL_ONE)
                    || !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_ONE) && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_TWO)
                    || !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_ONE) && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_TWO)
                    && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_THREE)
                    || !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_ONE) && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_TWO)
                    && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_THREE) && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_FOUR)
                    || !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_ONE) && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_TWO)
                    && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_THREE) && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_FOUR)
                    && !user.hasStatusEffect(IncantationMod.INCANTATION_LVL_FIVE)) {
                deniedLevelOneScrolls(user);
            }
        }

        user.addStatusEffect(new StatusEffectInstance(IncantationMod.FLESHY_PUNISHMENT, 200, 0));

        return super.use(world, user, hand);
    }

    private void deniedLevelOneScrolls(PlayerEntity player) {
        player.sendMessage(Text.translatable("text.incantation.level_one_scrolls.denied", "text.incantation.level_one_scrolls.punishment"));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.incantation.punishment_parchment.fleshy").formatted(Formatting.BLUE));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.incantation.parchment.starting_desc").formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.translatable("item.incantation.punishment_parchment.fleshy.description").formatted(Formatting.BLUE));
    }

}

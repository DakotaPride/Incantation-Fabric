package net.dakotapride.incantation.common.item.unconcealed_scrolls.extended;

import net.dakotapride.incantation.common.util.update_classes.SoulsComeAlive;
import net.dakotapride.incantation.common.item.scrolls.MidasFavourScrollItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnconcealedExtendedMidasFavourScroll extends MidasFavourScrollItem {
    public UnconcealedExtendedMidasFavourScroll(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(SoulsComeAlive.MIDAS_FAVOUR, 740, 0));
        itemStack.damage(1, user, (player) -> player.sendToolBreakStatus(player.getActiveHand()));
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.soulsComeAlive.unconcealed.midas_favour.extended").formatted(Formatting.BLUE));
    }
}

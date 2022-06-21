package net.dakotapride.incantation.compat.enhancedcelestials.item;

import net.dakotapride.incantation.common.item.EffectScrollItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarvestHealingScrollItem extends EffectScrollItem {
    public HarvestHealingScrollItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.enhancedcelestials.healing_parchment.harvest").formatted(Formatting.BLUE));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.incantation.parchment.starting_desc").formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.translatable("item.enhancedcelestials.healing_parchment.harvest.description").formatted(Formatting.BLUE));
    }

}

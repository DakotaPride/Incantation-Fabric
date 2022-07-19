package net.dakotapride.incantation.compat.enhancedcelestials.item;

import net.dakotapride.incantation.common.item.EffectScrollItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RemedyScrollItem extends EffectScrollItem {
    public RemedyScrollItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.incantation.cannot_use.remedy").formatted(Formatting.BLUE));
    }

}

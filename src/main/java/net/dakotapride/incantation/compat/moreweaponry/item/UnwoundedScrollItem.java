package net.dakotapride.incantation.compat.moreweaponry.item;

import net.dakotapride.incantation.common.item.scrolls.EffectScrollItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnwoundedScrollItem extends EffectScrollItem {
    public UnwoundedScrollItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.incantation.cannot_use.unwounded").formatted(Formatting.BLUE));
    }

}

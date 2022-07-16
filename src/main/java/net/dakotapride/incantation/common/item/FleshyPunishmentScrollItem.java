package net.dakotapride.incantation.common.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FleshyPunishmentScrollItem extends EffectScrollItem {
    public FleshyPunishmentScrollItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.incantation.punishment_parchment.fleshy").formatted(Formatting.BLUE));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.incantation.parchment.starting_desc").formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.translatable("item.incantation.punishment_parchment.fleshy.description").formatted(Formatting.BLUE));
    }

}

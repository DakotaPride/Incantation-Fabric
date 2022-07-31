package net.dakotapride.incantation.compat.enhancedcelestials.item.items_modified;

import net.dakotapride.incantation.common.util.update_classes.SoulsComeAlive;
import net.dakotapride.incantation.compat.enhancedcelestials.EnhancedCelestialsCompat;
import net.dakotapride.incantation.compat.enhancedcelestials.item.RemedyScrollItem;
import net.minecraft.client.gui.screen.Screen;
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

public class GTUnconcealedRemedyScroll extends RemedyScrollItem {
    public GTUnconcealedRemedyScroll(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.addStatusEffect(new StatusEffectInstance(EnhancedCelestialsCompat.REMEDY, 460, 0));
        user.addStatusEffect(new StatusEffectInstance(SoulsComeAlive.MIDAS_FAVOUR, 540, 1));
        itemStack.damage(1, user, (player) -> player.sendToolBreakStatus(player.getActiveHand()));
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.incantation.unconcealed.remedy").formatted(Formatting.BLUE));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("text.minecraft.modifiers.list_of"));
        tooltip.add(Text.translatable("item.randomModifiersGo.golden_touch.modifier").formatted(Formatting.BLUE));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("text.incantation.modifier.description.golden_touch").formatted(Formatting.GRAY));
        }
    }
}

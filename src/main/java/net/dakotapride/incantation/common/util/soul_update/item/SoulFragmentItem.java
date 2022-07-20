package net.dakotapride.incantation.common.util.soul_update.item;

import net.dakotapride.incantation.common.util.soul_update.SoulsComeAliveAddon;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulFragmentItem extends Item {
    public SoulFragmentItem(Settings settings) {
        super(settings);
    }

    public void fragmentToolTipType(fragmentTypeList type, List<Text> tooltip, TooltipContext context) {
        switch (type) {
            case PLAYER -> {
                tooltip.add(Text.translatable("soulsComeAlive.fragment_type.player").formatted(Formatting.BLUE));
            }
            case HOSTILE -> {
                tooltip.add(Text.translatable("soulsComeAlive.fragment_type.hostile").formatted(Formatting.RED));
            }
            case PASSIVE -> {
                tooltip.add(Text.translatable("soulsComeAlive.fragment_type.passive").formatted(Formatting.GREEN));
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        fragmentToolTipType(getSoulShard(stack.getItem()), tooltip, context);
    }

    public enum fragmentTypeList {
        HOSTILE,
        PASSIVE,
        PLAYER
    }

    public fragmentTypeList getSoulShard(Item item) {
        fragmentTypeList type = null;

        if (item == SoulsComeAliveAddon.HOSTILE_SOUL_FRAGMENT) {
            type = fragmentTypeList.HOSTILE;
        } else if (item == SoulsComeAliveAddon.PASSIVE_SOUL_FRAGMENT) {
            type = fragmentTypeList.PASSIVE;
        } else if (item == SoulsComeAliveAddon.PLAYER_SOUL_FRAGMENT) {
            type = fragmentTypeList.PLAYER;
        }

        return type;
    }

}

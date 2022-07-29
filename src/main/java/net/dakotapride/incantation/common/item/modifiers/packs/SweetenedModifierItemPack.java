package net.dakotapride.incantation.common.item.modifiers.packs;

import net.dakotapride.incantation.common.util.update_classes.RandomModifiersGo;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SweetenedModifierItemPack extends Item {
    public SweetenedModifierItemPack(Settings settings) {
        super(settings);
    }
    private enum SweetenedModifierItems {
        cinnamon_bun,
        cookie,
        sugar,
        cake,
        honey_bottle,
        pumpkin_pie,
        sweet_berries,
        enchanted_cinnamon_bun,
        golden_cinnamon_bun,
        enchanted_golden_cinnamon_bun
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Reference For Potential Items From Item Pack
        SweetenedModifierItems itemsList;

        ItemStack itemStack = user.getStackInHand(hand);
        BlockPos pos = user.getBlockPos();
        if (user.isSneaking()) {
            ItemStack stackToDrop = null;
            float randomValue = world.random.nextFloat() * 145;
            if (randomValue < 145 && randomValue > 130) {
                stackToDrop = new ItemStack(RandomModifiersGo.GOLDEN_CINNAMON_BUN);
            } else if (randomValue < 130 && randomValue > 115) {
                stackToDrop = new ItemStack(RandomModifiersGo.ENCHANTED_CINNAMON_BUN);
            } else if (randomValue < 115 && randomValue > 100) {
                stackToDrop = new ItemStack(RandomModifiersGo.ENCHANTED_GOLDEN_CINNAMON_BUN);
            } else if (randomValue < 100 && randomValue > 85) {
                stackToDrop = new ItemStack(RandomModifiersGo.CINNAMON_BUN, 4);
            } else if (randomValue < 85 && randomValue > 71) {
                stackToDrop = new ItemStack(Items.COOKIE, 6);
            } else if (randomValue < 71 && randomValue > 57) {
                stackToDrop = new ItemStack(Items.SUGAR, 5);
            } else if (randomValue < 57 && randomValue > 43) {
                stackToDrop = new ItemStack(Items.CAKE, 1);
            } else if (randomValue < 43 && randomValue > 29) {
                stackToDrop = new ItemStack(Items.HONEY_BOTTLE, 4);
            } else if (randomValue < 29 && randomValue > 15) {
                stackToDrop = new ItemStack(Items.PUMPKIN_PIE, 6);
            } else if (randomValue < 15 && randomValue > 0) {
                stackToDrop = new ItemStack(Items.SWEET_BERRIES, 3);
            }

            if (stackToDrop != null) {
                user.dropItem(stackToDrop, false, false);
                user.getStackInHand(hand).decrement(1);
                spawnSweetenedModifierParticles(world, pos);
            }
        }

        return TypedActionResult.success(itemStack);
    }

    private static void spawnSweetenedModifierParticles(World world, BlockPos pos) {
        for(int i = 0; i < 360; i++) {
            if(i % 20 == 0) {
                world.addParticle(ParticleTypes.SOUL,
                        pos.getX() + 0.5d, pos.getY() + 1, pos.getZ() + 0.5d,
                        Math.cos(i) * 0.25d, 0.15d, Math.sin(i) * 0.25d);
                world.addParticle(ParticleTypes.END_ROD,
                        pos.getX() + 0.5d, pos.getY() + 1, pos.getZ() + 0.5d,
                        Math.cos(i) * 0.25d, 0.15d, Math.sin(i) * 0.25d);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("randomModifiersGo.modifier_type.sweetened").formatted(Formatting.LIGHT_PURPLE));
        tooltip.add(Text.literal(" "));
        if (!Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("text.minecraft.hasShiftDown.modifiers"));
        } else if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("text.minecraft.modifiers.outcome"));
            tooltip.add(Text.translatable(RandomModifiersGo.CINNAMON_BUN.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(RandomModifiersGo.ENCHANTED_CINNAMON_BUN.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(RandomModifiersGo.GOLDEN_CINNAMON_BUN.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(RandomModifiersGo.ENCHANTED_GOLDEN_CINNAMON_BUN.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(Items.COOKIE.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(Items.SUGAR.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(Items.CAKE.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(Items.HONEY_BOTTLE.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(Items.PUMPKIN_PIE.getTranslationKey(stack)).formatted(Formatting.AQUA));
            tooltip.add(Text.translatable(Items.SWEET_BERRIES.getTranslationKey(stack)).formatted(Formatting.AQUA));
        }
    }
}

package net.dakotapride.incantation.common.soulsComeAlive.item;

import net.dakotapride.incantation.common.soulsComeAlive.SoulsComeAlive;
import net.dakotapride.incantation.config.IncantationConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TotemOfSalvationItem extends Item {
    public TotemOfSalvationItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        user.world.sendEntityStatus(user, (byte)40);

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 600, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 2));

        if (user.getOffHandStack().isOf(SoulsComeAlive.HOSTILE_SOUL_FRAGMENT)) {
            user.addStatusEffect(new StatusEffectInstance(SoulsComeAlive.DEVILISH_BARGAIN, 3600, 1));
        } else if (user.getOffHandStack().isOf(SoulsComeAlive.SOUL_FIRE_FRAGMENT)) {
            user.addStatusEffect(new StatusEffectInstance(SoulsComeAlive.SOUL_BLESSING, 3600, 1));
        }

        stack.damage(1, user, (playerEntity -> playerEntity.sendToolBreakStatus(hand)));
        user.getItemCooldownManager().set(this, 400);

        return super.use(world, user, hand);
    }

    private void getDurabilityTooltip(List<Text> tooltip, ItemStack stack) {
        int maxDamage = this.getMaxDamage();
        int currentItemDamage = this.getMaxDamage() - stack.getDamage();
        tooltip.add(Text.translatable("soulsComeAlive.totemOfSalvation.max_uses", currentItemDamage, maxDamage));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (IncantationConfig.showTotemOfSalvationRemainingUses == true) {
            getDurabilityTooltip(tooltip, stack);
        }
    }
}

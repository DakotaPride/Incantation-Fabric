package net.dakotapride.incantation.common.item;

import net.dakotapride.incantation.common.util.update_classes.SoulsComeAlive;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulFragmentItem extends Item {
    public SoulFragmentItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        Item item = user.getActiveItem().getItem();
        if (entity instanceof AllayEntity && getSoulShard(item) == fragmentTypeList.PLAYER
                || entity instanceof AllayEntity && getSoulShard(item) == fragmentTypeList.PASSIVE
                || entity instanceof AllayEntity && getSoulShard(item) == fragmentTypeList.HOSTILE
                || entity instanceof AllayEntity && getSoulShard(item) == fragmentTypeList.DARKENED) {
            stack.decrement(1);

            user.giveItemStack(new ItemStack(SoulsComeAlive.DISRUPTIVE_SOUL_FRAGMENT, 1));
        } else if (entity instanceof DrownedEntity && getSoulShard(item) == fragmentTypeList.PLAYER
                || entity instanceof DrownedEntity && getSoulShard(item) == fragmentTypeList.PASSIVE
                || entity instanceof DrownedEntity && getSoulShard(item) == fragmentTypeList.HOSTILE
                || entity instanceof DrownedEntity && getSoulShard(item) == fragmentTypeList.LIGHTENED) {
            stack.decrement(1);

            user.giveItemStack(new ItemStack(SoulsComeAlive.ANGELIC_SOUL_FRAGMENT, 1));
        } else if (entity instanceof PhantomEntity && getSoulShard(item) == fragmentTypeList.PLAYER
                || entity instanceof PhantomEntity && getSoulShard(item) == fragmentTypeList.PASSIVE
                || entity instanceof PhantomEntity && getSoulShard(item) == fragmentTypeList.HOSTILE
                || entity instanceof PhantomEntity && getSoulShard(item) == fragmentTypeList.LIGHTENED) {
            stack.decrement(1);

            user.giveItemStack(new ItemStack(SoulsComeAlive.DARKENED_SOUL_FRAGMENT, 1));
        } else if (entity instanceof MerchantEntity && getSoulShard(item) == fragmentTypeList.PLAYER
                || entity instanceof PhantomEntity && getSoulShard(item) == fragmentTypeList.PASSIVE
                || entity instanceof PhantomEntity && getSoulShard(item) == fragmentTypeList.HOSTILE) {
            stack.decrement(1);

            user.giveItemStack(new ItemStack(SoulsComeAlive.LIGHTENED_SOUL_FRAGMENT, 1));
        }

        return super.useOnEntity(stack, user, entity, hand);
    }

    public void fragmentToolTipType(fragmentTypeList type, List<Text> tooltip) {
        switch (type) {
            case PLAYER ->
                    tooltip.add(Text.translatable("soulsComeAlive.fragment_type.player").formatted(Formatting.BLUE));
            case HOSTILE ->
                    tooltip.add(Text.translatable("soulsComeAlive.fragment_type.hostile").formatted(Formatting.RED));
            case PASSIVE ->
                    tooltip.add(Text.translatable("soulsComeAlive.fragment_type.passive").formatted(Formatting.GREEN));
            case DARKENED ->
                    tooltip.add(Text.translatable("soulsComeAlive.fragment_type.darkened").formatted(Formatting.DARK_GRAY));
            case LIGHTENED ->
                    tooltip.add(Text.translatable("soulsComeAlive.fragment_type.lightened").formatted(Formatting.YELLOW));
            case SOUL_FIRE ->
                    tooltip.add(Text.translatable("soulsComeAlive.fragment_type.soul_fire").formatted(Formatting.AQUA));
            case SIREN_WARNING ->
                    tooltip.add(Text.translatable("soulsComeAlive.fragment_type.siren_warning"));
            case ANGEL_STIFFNESS ->
                    tooltip.add(Text.translatable("soulsComeAlive.fragment_type.angel_stiffness"));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        fragmentToolTipType(getSoulShard(stack.getItem()), tooltip);
    }

    public enum fragmentTypeList {
        HOSTILE,
        PASSIVE,
        PLAYER,
        DARKENED,
        LIGHTENED,
        SOUL_FIRE,
        ANGEL_STIFFNESS,
        SIREN_WARNING
    }

    public fragmentTypeList getSoulShard(Item item) {
        fragmentTypeList type = null;

        if (item == SoulsComeAlive.HOSTILE_SOUL_FRAGMENT) {
            type = fragmentTypeList.HOSTILE;
        } else if (item == SoulsComeAlive.PASSIVE_SOUL_FRAGMENT) {
            type = fragmentTypeList.PASSIVE;
        } else if (item == SoulsComeAlive.PLAYER_SOUL_FRAGMENT) {
            type = fragmentTypeList.PLAYER;
        } else if (item == SoulsComeAlive.DARKENED_SOUL_FRAGMENT) {
            type = fragmentTypeList.DARKENED;
        } else if (item == SoulsComeAlive.LIGHTENED_SOUL_FRAGMENT) {
            type = fragmentTypeList.LIGHTENED;
        } else if (item == SoulsComeAlive.SOUL_FIRE_FRAGMENT) {
            type = fragmentTypeList.SOUL_FIRE;
        } else if (item == SoulsComeAlive.ANGELIC_SOUL_FRAGMENT) {
            type = fragmentTypeList.ANGEL_STIFFNESS;
        } else if (item == SoulsComeAlive.DISRUPTIVE_SOUL_FRAGMENT) {
            type = fragmentTypeList.SIREN_WARNING;
        }

        return type;
    }

}

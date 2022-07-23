package net.dakotapride.incantation.common.soulsComeAlive;

import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.common.soulsComeAlive.item.SoulFragmentItem;
import net.dakotapride.incantation.common.soulsComeAlive.item.TotemOfSalvationItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.dakotapride.incantation.common.IncantationMod.INCANTATION_GROUP;
import static net.dakotapride.incantation.common.IncantationMod.INCANTATION_ID;

public class SoulsComeAlive {

    public static SoulFragmentItem PLAYER_SOUL_FRAGMENT;
    public static SoulFragmentItem HOSTILE_SOUL_FRAGMENT;
    public static SoulFragmentItem PASSIVE_SOUL_FRAGMENT;
    public static SoulFragmentItem DARKENED_SOUL_FRAGMENT;
    public static SoulFragmentItem LIGHTENED_SOUL_FRAGMENT;
    public static SoulFragmentItem SOUL_FIRE_FRAGMENT;
    public static TotemOfSalvationItem TOTEM_OF_SALVATION;
    public static StatusEffect ANGEL_STIFFNESS = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0xFFFFE5)
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890",
                    -0.45000001788D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E",
                    2.0D, EntityAttributeModifier.Operation.ADDITION);
    public static SoulFragmentItem ANGELIC_SOUL_FRAGMENT;
    public static StatusEffect SIREN_WARNING = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0x16DDB2);
    public static SoulFragmentItem DISRUPTIVE_SOUL_FRAGMENT;
    public static StatusEffect SOUL_BLESSING = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0x16DDB2);
    public static StatusEffect DEVILISH_BARGAIN = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0x16DDB2);

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, name), item);
        return item;
    }


    public static void registerSoulsComeAliveAddon() {

        Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "soul_blessing"), SOUL_BLESSING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "devilish_bargain"), DEVILISH_BARGAIN);

        Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "angel_stiffness"), ANGEL_STIFFNESS);
        ANGELIC_SOUL_FRAGMENT = registerItem("angelic_soul_shard",
                new SoulFragmentItem(new FabricItemSettings().group(INCANTATION_GROUP)));
        Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "siren_warning"), SIREN_WARNING);
        DISRUPTIVE_SOUL_FRAGMENT = registerItem("disruptive_soul_shard",
                new SoulFragmentItem(new FabricItemSettings().group(INCANTATION_GROUP)));

        TOTEM_OF_SALVATION = registerItem("totem_of_salvation",
                new TotemOfSalvationItem(new FabricItemSettings().maxCount(1).maxDamage(5).group(INCANTATION_GROUP)));

        PLAYER_SOUL_FRAGMENT = registerItem("player_soul_shard",
                new SoulFragmentItem(new FabricItemSettings().group(INCANTATION_GROUP)));
        HOSTILE_SOUL_FRAGMENT = registerItem("hostile_soul_shard",
                new SoulFragmentItem(new FabricItemSettings().group(INCANTATION_GROUP)));
        PASSIVE_SOUL_FRAGMENT = registerItem("passive_soul_shard",
                new SoulFragmentItem(new FabricItemSettings().group(INCANTATION_GROUP)));

        DARKENED_SOUL_FRAGMENT = registerItem("darkened_soul_shard",
                new SoulFragmentItem(new FabricItemSettings().group(INCANTATION_GROUP)));
        LIGHTENED_SOUL_FRAGMENT = registerItem("lightened_soul_shard",
                new SoulFragmentItem(new FabricItemSettings().group(INCANTATION_GROUP)));

        SOUL_FIRE_FRAGMENT = registerItem("soul_fire_shard",
                new SoulFragmentItem(new FabricItemSettings().group(INCANTATION_GROUP)));
    }

}

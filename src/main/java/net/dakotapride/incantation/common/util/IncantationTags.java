package net.dakotapride.incantation.common.util;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.Collection;

public class IncantationTags {

    public static final TagKey<Item> HARVESTABLE_FOODS = createCommonItemTag("harvestable_healing_foods");
    public static final TagKey<Biome> HAS_JADE_GEODE = createIncantationBiomeTag("has_jade_geode");
    public static final TagKey<Biome> HAS_ENCHANTED_BERRY_BUSHES = createIncantationBiomeTag("has_enchanted_berry_bushes");

    private static TagKey<Item> createCommonItemTag(String name) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
    }

    private static TagKey<Biome> createIncantationBiomeTag(String name) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier(IncantationMod.INCANTATION_ID, name));
    }


}

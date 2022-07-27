package net.dakotapride.incantation.common.util;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

import java.util.Collection;

public class IncantationTags {

    public static final TagKey<Item> HARVESTABLE_FOODS = createCommonItemTag("harvestable_healing_foods");
    public static final TagKey<Biome> HAS_JADE_GEODE = createIncantationBiomeTag("has_jade_geode");
    public static final TagKey<Biome> HAS_ENCHANTED_BERRY_BUSHES = createIncantationBiomeTag("has_enchanted_berry_bushes");
    public static final TagKey<Block> ACCEPTABLE_MIDAS_FAVOUR = createCommonBlockTag("acceptable_midas_favour");
    public static final TagKey<Item> BEWITCHMENT_CENTER_LOCATABLE = createIncantationItemTag("bewitchment_center_locatable");
    public static final TagKey<Structure> SOUL_SHARD_LOCATED = createIncantationStructureTag("soul_shard_located");

    private static TagKey<Item> createCommonItemTag(String name) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
    }

    private static TagKey<Structure> createIncantationStructureTag(String name) {
        return TagKey.of(Registry.STRUCTURE_KEY, new Identifier(IncantationMod.INCANTATION_ID, name));
    }

    private static TagKey<Block> createCommonBlockTag(String name) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier("c", name));
    }

    private static TagKey<Biome> createIncantationBiomeTag(String name) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier(IncantationMod.INCANTATION_ID, name));
    }

    private static TagKey<Item> createIncantationItemTag(String name) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(IncantationMod.INCANTATION_ID, name));
    }


}

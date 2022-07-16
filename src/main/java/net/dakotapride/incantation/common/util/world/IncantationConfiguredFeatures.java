package net.dakotapride.incantation.common.util.world;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class IncantationConfiguredFeatures {

    public static final List<OreFeatureConfig.Target> OVERWORLD_AMMOLITE_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, IncantationMod.AMMOLITE_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, IncantationMod.DEEPSLATE_AMMOLITE_ORE.getDefaultState()));

    public static final List<OreFeatureConfig.Target> END_ALEXANDRITE_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, IncantationMod.ALEXANDRITE_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, IncantationMod.DEEPSLATE_ALEXANDRITE_ORE.getDefaultState()));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> AMMOLITE_ORE = ConfiguredFeatures.register("ammolite_ore",
            Feature.ORE, new OreFeatureConfig(OVERWORLD_AMMOLITE_ORES, 10));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ALEXANDRITE_ORE = ConfiguredFeatures.register("alexandrite_ore",
            Feature.ORE, new OreFeatureConfig(END_ALEXANDRITE_ORES, 4));

}

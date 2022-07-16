package net.dakotapride.incantation.common.util.world;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class IncantationPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> AMMOLITE_ORE_PLACED = PlacedFeatures.register("ammolite_ore",
            IncantationConfiguredFeatures.AMMOLITE_ORE, IncantationOreFeatures.modifiersWithCount(8,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(100))));

    public static final RegistryEntry<PlacedFeature> ALEXANDRITE_ORE_PLACED = PlacedFeatures.register("alexandrite_ore",
            IncantationConfiguredFeatures.ALEXANDRITE_ORE, IncantationOreFeatures.modifiersWithCount(5,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(30))));
}

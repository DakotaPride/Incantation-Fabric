package net.dakotapride.incantation.common;

import net.dakotapride.incantation.common.block.*;
import net.dakotapride.incantation.common.block.entity.BewitchmentTableBlock;
import net.dakotapride.incantation.common.block.entity.BewitchmentTableEntity;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.common.item.*;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.base.UnconcealedFleshyPunishmentScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.base.UnconcealedFreezingResistanceScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.base.UnconcealedMilkyResistanceScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.extended.UnconcealedExtendedFleshyPunishmentScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.extended.UnconcealedExtendedFreezingResistanceScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.frosted.UnconcealedFrostedFleshyPunishmentScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.frosted.UnconcealedFrostedFreezingResistanceScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.stronger.UnconcealedStrongerFleshyPunishmentScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.stronger.UnconcealedStrongerFreezingResistanceScroll;
import net.dakotapride.incantation.common.recipe.BewitchmentTableRecipe;
import net.dakotapride.incantation.common.screen.BewitchmentTableScreenHandler;
import net.dakotapride.incantation.common.util.IncantationTags;
import net.dakotapride.incantation.common.util.world.IncantationPlacedFeatures;
import net.dakotapride.incantation.compat.enhancedcelestials.EnhancedCelestialsCompat;
import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.dakotapride.incantation.compat.pickyourpoison.PickYourPoisonCompat;
import net.dakotapride.incantation.mixin.BrewingRecipeRegistryMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class IncantationMod implements ModInitializer {
	public static final String INCANTATION_ID = ("incantation");
	public static final Logger LOGGER = LoggerFactory.getLogger("incantation");

	public static StatusEffect MILKY_RESISTANCE = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0xF9F7F7);
	public static MilkyResistanceScrollItem MILKY_RESISTANCE_SCROLL;

	public static StatusEffect FREEZING_RESISTANCE = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xCEFFF2);
	public static FreezingResistanceScrollItem FREEZING_RESISTANCE_SCROLL;

	public static StatusEffect FLESHY_PUNISHMENT = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0xC89661);
	public static FleshyPunishmentScrollItem FLESHY_PUNISHMENT_SCROLL;

	public static ScreenHandlerType<BewitchmentTableScreenHandler> BEWITCHMENT_TABLE_SCREEN_HANDLER;
	public static BewitchmentTableBlock BEWITCHMENT_TABLE;
	public static BlockEntityType<BewitchmentTableEntity> BEWITCHMENT_TABLE_BLOCK_ENTITY;

	public static Potion MILKY_RESISTANCE_POTION;
	public static Potion FREEZING_RESISTANCE_POTION;
	public static Potion FLESHY_PUNISHMENT_POTION;


	public static Item PLAINS_CHERRIES;
	public static Item FROSTED_PLAINS_CHERRIES;
	public static Item SILVER_NUGGET_APPLE;
	public static Item MYSTIC_LEATHER;
	public static EnchantedBerryItem ENCHANTED_BERRIES;
	public static EnchantedBerryJamItem ENCHANTED_BERRY_JAM;

	public static UnconcealedMilkyResistanceScroll UNCONCEALED_MILKY_RESISTANCE_SCROLL;

	public static UnconcealedFreezingResistanceScroll UNCONCEALED_FREEZING_RESISTANCE_SCROLL;
	public static UnconcealedExtendedFreezingResistanceScroll UNCONCEALED_LONG_FREEZING_RESISTANCE_SCROLL;
	public static UnconcealedStrongerFreezingResistanceScroll UNCONCEALED_STRONG_FREEZING_RESISTANCE_SCROLL;
	public static UnconcealedFrostedFreezingResistanceScroll UNCONCEALED_FROSTED_FREEZING_RESISTANCE_SCROLL;

	public static UnconcealedFleshyPunishmentScroll UNCONCEALED_FLESHY_PUNISHMENT_SCROLL;
	public static UnconcealedExtendedFleshyPunishmentScroll UNCONCEALED_LONG_FLESHY_PUNISHMENT_SCROLL;
	public static UnconcealedStrongerFleshyPunishmentScroll UNCONCEALED_STRONG_FLESHY_PUNISHMENT_SCROLL;
	public static UnconcealedFrostedFleshyPunishmentScroll UNCONCEALED_FROSTED_FLESHY_PUNISHMENT_SCROLL;

	// Registration
	public static <T extends Block> T registerBlock(String name, T block) {
		Registry.register(Registry.BLOCK, new Identifier(INCANTATION_ID, name), block);
		return block;
	}

	public static <T extends Item> T registerItem(String name, T item) {
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, name), item);
		return item;
	}

	public static <T extends Potion> T registerPotion(String name, T potion) {
		Registry.register(Registry.POTION, new Identifier(INCANTATION_ID, name), potion);
		return potion;
	}

	public static BuddingGreenJadeBlock BUDDING_GREEN_JADE;
	public static GreenJadeBlock GREEN_JADE_BLOCK;
	public static GreenJadeClusterBlock GREEN_JADE_CLUSTER;
	public static GreenJadeClusterBlock SMALL_GREEN_JADE_BUD;
	public static GreenJadeClusterBlock MEDIUM_GREEN_JADE_BUD;
	public static GreenJadeClusterBlock LARGE_GREEN_JADE_BUD;
	public static Item GREEN_JADE_SHARD;

	public static BuddingJadeBlock BUDDING_JADE;
	public static JadeBlock JADE_BLOCK;
	public static JadeClusterBlock JADE_CLUSTER;
	public static JadeClusterBlock SMALL_JADE_BUD;
	public static JadeClusterBlock MEDIUM_JADE_BUD;
	public static JadeClusterBlock LARGE_JADE_BUD;
	public static Item JADE_SHARD;

	public static OreBlock AMMOLITE_ORE;
	public static OreBlock DEEPSLATE_AMMOLITE_ORE;
	public static Item RAW_AMMOLITE;
	public static Item AMMOLITE_GEM;

	public static OreBlock ALEXANDRITE_ORE;
	public static OreBlock DEEPSLATE_ALEXANDRITE_ORE;
	public static Item RAW_ALEXANDRITE;
	public static Item ALEXANDRITE_GEM;

	public static CastingJadeItem CASTING_JADE;
	public static CastingGreenJadeItem CASTING_GREEN_JADE;
	public static CastingAmethystItem CASTING_AMETHYST;
	public static Item AMMOLITE_LENS;
	public static Item IRON_CAST_MOLD;
	public static Item EMPTY_IRON_CASTING;

	public static final ItemGroup INCANTATION_GROUP = FabricItemGroupBuilder.create(
					new Identifier(INCANTATION_ID, "incantation"))
			.icon(() -> new ItemStack(Items.SPLASH_POTION))
			.appendItems(itemStacks -> {
				itemStacks.add(new ItemStack(AMMOLITE_ORE));
				itemStacks.add(new ItemStack(DEEPSLATE_AMMOLITE_ORE));
				itemStacks.add(new ItemStack(BEWITCHMENT_TABLE));
				itemStacks.add(new ItemStack(BUDDING_GREEN_JADE));
				itemStacks.add(new ItemStack(GREEN_JADE_BLOCK));
				itemStacks.add(new ItemStack(GREEN_JADE_CLUSTER));
				itemStacks.add(new ItemStack(SMALL_GREEN_JADE_BUD));
				itemStacks.add(new ItemStack(MEDIUM_GREEN_JADE_BUD));
				itemStacks.add(new ItemStack(LARGE_GREEN_JADE_BUD));
				itemStacks.add(new ItemStack(BUDDING_JADE));
				itemStacks.add(new ItemStack(JADE_BLOCK));
				itemStacks.add(new ItemStack(JADE_CLUSTER));
				itemStacks.add(new ItemStack(SMALL_JADE_BUD));
				itemStacks.add(new ItemStack(MEDIUM_JADE_BUD));
				itemStacks.add(new ItemStack(LARGE_JADE_BUD));
				itemStacks.add(new ItemStack(ALEXANDRITE_ORE));
				itemStacks.add(new ItemStack(DEEPSLATE_ALEXANDRITE_ORE));
				itemStacks.add(new ItemStack(PLAINS_CHERRIES));
				itemStacks.add(new ItemStack(FROSTED_PLAINS_CHERRIES));
				itemStacks.add(new ItemStack(SILVER_NUGGET_APPLE));
				itemStacks.add(new ItemStack(ENCHANTED_BERRIES));
				itemStacks.add(new ItemStack(ENCHANTED_BERRY_JAM));
				itemStacks.add(new ItemStack(MYSTIC_LEATHER));
				itemStacks.add(new ItemStack(RAW_AMMOLITE));
				itemStacks.add(new ItemStack(AMMOLITE_GEM));
				itemStacks.add(new ItemStack(AMMOLITE_LENS));
				itemStacks.add(new ItemStack(IRON_CAST_MOLD));
				itemStacks.add(new ItemStack(EMPTY_IRON_CASTING));
				itemStacks.add(new ItemStack(GREEN_JADE_SHARD));
				itemStacks.add(new ItemStack(JADE_SHARD));
				itemStacks.add(new ItemStack(RAW_ALEXANDRITE));
				itemStacks.add(new ItemStack(ALEXANDRITE_GEM));
				itemStacks.add(new ItemStack(FLESHY_PUNISHMENT_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_FLESHY_PUNISHMENT_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_LONG_FLESHY_PUNISHMENT_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_STRONG_FLESHY_PUNISHMENT_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_FROSTED_FLESHY_PUNISHMENT_SCROLL));
				itemStacks.add(new ItemStack(MILKY_RESISTANCE_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_MILKY_RESISTANCE_SCROLL));
				itemStacks.add(new ItemStack(FREEZING_RESISTANCE_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_FREEZING_RESISTANCE_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_LONG_FREEZING_RESISTANCE_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_STRONG_FREEZING_RESISTANCE_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_FROSTED_FREEZING_RESISTANCE_SCROLL));
				if (FabricLoader.getInstance().isModLoaded("pickyourpoison")) {
					itemStacks.add(new ItemStack(PickYourPoisonCompat.REFLECTION_RESISTANCE_SCROLL));
					itemStacks.add(new ItemStack(PickYourPoisonCompat.UNCONCEALED_REFLECTION_RESISTANCE_SCROLL));
					itemStacks.add(new ItemStack(PickYourPoisonCompat.UNCONCEALED_LONG_REFLECTION_RESISTANCE_SCROLL));
					itemStacks.add(new ItemStack(PickYourPoisonCompat.UNCONCEALED_STRONG_REFLECTION_RESISTANCE_SCROLL));
					itemStacks.add(new ItemStack(PickYourPoisonCompat.UNCONCEALED_FROSTED_REFLECTION_RESISTANCE_SCROLL));
				}
				if (FabricLoader.getInstance().isModLoaded("moreweaponry")) {
					itemStacks.add(new ItemStack(MoreWeaponryCompat.HARMING_RESISTANCE_SCROLL));
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNCONCEALED_HARMING_RESISTANCE_SCROLL));
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNCONCEALED_LONG_HARMING_RESISTANCE_SCROLL));
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNCONCEALED_STRONG_HARMING_RESISTANCE_SCROLL));
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNCONCEALED_FROSTED_HARMING_RESISTANCE_SCROLL));
				}
				if (FabricLoader.getInstance().isModLoaded("enhancedcelestials")) {
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.HARVEST_HEALING_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.UNCONCEALED_HARVEST_HEALING_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.UNCONCEALED_LONG_HARVEST_HEALING_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.UNCONCEALED_STRONG_HARVEST_HEALING_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.UNCONCEALED_FROSTED_HARVEST_HEALING_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.MOON_CREST_FRUIT));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.MENDING_MOON_CREST_FRUIT));
				}
			})
			.build();

	public static RegistryEntry<ConfiguredFeature<GeodeFeatureConfig, ?>> GREEN_JADE_GEODE;
	public static RegistryEntry<PlacedFeature> GREEN_JADE_GEODE_PLACED;

	public static RegistryEntry<ConfiguredFeature<GeodeFeatureConfig, ?>> JADE_GEODE;
	public static RegistryEntry<PlacedFeature> JADE_GEODE_PLACED;

    private static final Identifier BIRCH_LEAVES_BLOCK_ID
            = new Identifier("minecraft", "blocks/birch_leaves");

	@Override
	public void onInitialize() {

        LootTableEvents.MODIFY.register(((resourceManager, manager, id, supplier, setter) -> {
            if (BIRCH_LEAVES_BLOCK_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.30f))
                        .with(ItemEntry.builder(ENCHANTED_BERRIES))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)).build());
                supplier.pool(poolBuilder.build());
            }

        }));

		ALEXANDRITE_ORE = registerBlock("alexandrite_ore",
				new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE)));
		DEEPSLATE_ALEXANDRITE_ORE = registerBlock("deepslate_alexandrite_ore",
				new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "alexandrite_ore"), new BlockItem(ALEXANDRITE_ORE,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "deepslate_alexandrite_ore"), new BlockItem(DEEPSLATE_ALEXANDRITE_ORE,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		RAW_ALEXANDRITE = registerItem("raw_alexandrite",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		ALEXANDRITE_GEM = registerItem("alexandrite_gem",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		CASTING_JADE = registerItem("casting_jade",
				new CastingJadeItem(new FabricItemSettings().group(INCANTATION_GROUP)));
		CASTING_GREEN_JADE = registerItem("casting_green_jade",
				new CastingGreenJadeItem(new FabricItemSettings().group(INCANTATION_GROUP)));
		CASTING_AMETHYST = registerItem("casting_amethyst",
				new CastingAmethystItem(new FabricItemSettings().group(INCANTATION_GROUP)));
		AMMOLITE_LENS = registerItem("ammolite_lens",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		IRON_CAST_MOLD = registerItem("iron_casting_mold",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		EMPTY_IRON_CASTING = registerItem("empty_iron_casting",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		AMMOLITE_ORE = registerBlock("ammolite_ore",
				new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)));
		DEEPSLATE_AMMOLITE_ORE = registerBlock("deepslate_ammolite_ore",
				new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "ammolite_ore"), new BlockItem(AMMOLITE_ORE,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "deepslate_ammolite_ore"), new BlockItem(DEEPSLATE_AMMOLITE_ORE,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		RAW_AMMOLITE = registerItem("raw_ammolite",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		AMMOLITE_GEM = registerItem("ammolite_gem",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.UNDERGROUND_ORES, Objects.requireNonNull(IncantationPlacedFeatures.AMMOLITE_ORE_PLACED.getKey().orElse(null)));

		BUDDING_GREEN_JADE = registerBlock("budding_green_jade",
				new BuddingGreenJadeBlock(FabricBlockSettings.copy(Blocks.BUDDING_AMETHYST)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "budding_green_jade"), new BlockItem(BUDDING_GREEN_JADE,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		GREEN_JADE_BLOCK = registerBlock("green_jade_block",
				new GreenJadeBlock(FabricBlockSettings.copy(Blocks.AMETHYST_BLOCK)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "green_jade_block"), new BlockItem(GREEN_JADE_BLOCK,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		GREEN_JADE_SHARD = registerItem("green_jade_shard",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		GREEN_JADE_CLUSTER = registerBlock("green_jade_bud",
				new GreenJadeClusterBlock(7, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER)));
		SMALL_GREEN_JADE_BUD = registerBlock("small_green_jade_bud",
				new GreenJadeClusterBlock(3, 4, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER)));
		MEDIUM_GREEN_JADE_BUD = registerBlock("medium_green_jade_bud",
				new GreenJadeClusterBlock(4, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER)));
		LARGE_GREEN_JADE_BUD = registerBlock("large_green_jade_bud",
				new GreenJadeClusterBlock(5, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "green_jade_cluster"), new BlockItem(GREEN_JADE_CLUSTER,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "small_green_jade_cluster"), new BlockItem(SMALL_GREEN_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "medium_green_jade_cluster"), new BlockItem(MEDIUM_GREEN_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "large_green_jade_cluster"), new BlockItem(LARGE_GREEN_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		GREEN_JADE_GEODE = ConfiguredFeatures.register("green_jade_geode", Feature.GEODE ,
						new GeodeFeatureConfig(new GeodeLayerConfig(BlockStateProvider.of(Blocks.AIR),
								BlockStateProvider.of(GREEN_JADE_BLOCK),
								BlockStateProvider.of(BUDDING_GREEN_JADE),
								BlockStateProvider.of(Blocks.CALCITE),
								BlockStateProvider.of(Blocks.SMOOTH_BASALT),
								List.of(BUDDING_GREEN_JADE.getDefaultState()),
								BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),
								new GeodeLayerThicknessConfig(1.7D, 2.2D, 3.2D, 4.2D),
								new GeodeCrackConfig(0.95D, 2.0D, 2),
								0.35D, 0.083D, true,
								UniformIntProvider.create(4, 6),
								UniformIntProvider.create(3, 4),
								UniformIntProvider.create(1, 2),
								-16, 16, 0.05D, 1));

		GREEN_JADE_GEODE_PLACED = PlacedFeatures.register("green_jade_geode_placed",
				GREEN_JADE_GEODE, RarityFilterPlacementModifier.of(42),
				SquarePlacementModifier.of(),
				HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.aboveBottom(50)),
				BiomePlacementModifier.of());

		BiomeModifications.addFeature(BiomeSelectors.tag(IncantationTags.HAS_JADE_GEODE),
						GenerationStep.Feature.UNDERGROUND_DECORATION, Objects.requireNonNull(GREEN_JADE_GEODE_PLACED.getKey().orElse(null)));

		BUDDING_JADE = registerBlock("budding_jade",
				new BuddingJadeBlock(FabricBlockSettings.copy(Blocks.BUDDING_AMETHYST)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "budding_jade"), new BlockItem(BUDDING_JADE,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		JADE_BLOCK = registerBlock("jade_block",
				new JadeBlock(FabricBlockSettings.copy(Blocks.AMETHYST_BLOCK)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "jade_block"), new BlockItem(JADE_BLOCK,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		JADE_SHARD = registerItem("jade_shard",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		JADE_CLUSTER = registerBlock("jade_bud",
				new JadeClusterBlock(7, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER)));
		SMALL_JADE_BUD = registerBlock("small_jade_bud",
				new JadeClusterBlock(3, 4, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER)));
		MEDIUM_JADE_BUD = registerBlock("medium_jade_bud",
				new JadeClusterBlock(4, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER)));
		LARGE_JADE_BUD = registerBlock("large_jade_bud",
				new JadeClusterBlock(5, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "jade_cluster"), new BlockItem(JADE_CLUSTER,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "small_jade_cluster"), new BlockItem(SMALL_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "medium_jade_cluster"), new BlockItem(MEDIUM_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "large_jade_cluster"), new BlockItem(LARGE_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		JADE_GEODE = ConfiguredFeatures.register("jade_geode", Feature.GEODE ,
				new GeodeFeatureConfig(new GeodeLayerConfig(BlockStateProvider.of(Blocks.AIR),
						BlockStateProvider.of(JADE_BLOCK),
						BlockStateProvider.of(BUDDING_JADE),
						BlockStateProvider.of(Blocks.CALCITE),
						BlockStateProvider.of(Blocks.SMOOTH_BASALT),
						List.of(BUDDING_JADE.getDefaultState()),
						BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),
						new GeodeLayerThicknessConfig(1.7D, 2.2D, 3.2D, 4.2D),
						new GeodeCrackConfig(0.95D, 2.0D, 2),
						0.35D, 0.083D, true,
						UniformIntProvider.create(4, 6),
						UniformIntProvider.create(3, 4),
						UniformIntProvider.create(1, 2),
						-16, 16, 0.05D, 1));

		JADE_GEODE_PLACED = PlacedFeatures.register("jade_geode_placed",
				JADE_GEODE, RarityFilterPlacementModifier.of(42),
				SquarePlacementModifier.of(),
				HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.aboveBottom(50)),
				BiomePlacementModifier.of());

		BiomeModifications.addFeature(BiomeSelectors.tag(IncantationTags.HAS_JADE_GEODE),
				GenerationStep.Feature.UNDERGROUND_DECORATION, Objects.requireNonNull(JADE_GEODE_PLACED.getKey().orElse(null)));

		UNCONCEALED_FLESHY_PUNISHMENT_SCROLL = registerItem("unconcealed_fleshy_punishment_scroll",
				new UnconcealedFleshyPunishmentScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));
		UNCONCEALED_FROSTED_FLESHY_PUNISHMENT_SCROLL = registerItem("frosted_unconcealed_fleshy_punishment_scroll",
				new UnconcealedFrostedFleshyPunishmentScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));
		UNCONCEALED_LONG_FLESHY_PUNISHMENT_SCROLL = registerItem("long_unconcealed_fleshy_punishment_scroll",
				new UnconcealedExtendedFleshyPunishmentScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));
		UNCONCEALED_STRONG_FLESHY_PUNISHMENT_SCROLL = registerItem("strong_unconcealed_fleshy_punishment_scroll",
				new UnconcealedStrongerFleshyPunishmentScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));

		UNCONCEALED_MILKY_RESISTANCE_SCROLL = registerItem("unconcealed_milky_resistance_scroll",
				new UnconcealedMilkyResistanceScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));

		UNCONCEALED_FREEZING_RESISTANCE_SCROLL = registerItem("unconcealed_freezing_resistance_scroll",
				new UnconcealedFreezingResistanceScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));
		UNCONCEALED_FROSTED_FREEZING_RESISTANCE_SCROLL = registerItem("frosted_unconcealed_freezing_resistance_scroll",
				new UnconcealedFrostedFreezingResistanceScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));
		UNCONCEALED_LONG_FREEZING_RESISTANCE_SCROLL = registerItem("long_unconcealed_freezing_resistance_scroll",
				new UnconcealedExtendedFreezingResistanceScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));
		UNCONCEALED_STRONG_FREEZING_RESISTANCE_SCROLL = registerItem("strong_unconcealed_freezing_resistance_scroll",
				new UnconcealedStrongerFreezingResistanceScroll(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));

		PLAINS_CHERRIES = registerItem("plains_cherries",
				new Item(new FabricItemSettings().food(new FoodComponent.Builder()
						.saturationModifier(3.0f).hunger(3).snack().build()).group(INCANTATION_GROUP)));
		FROSTED_PLAINS_CHERRIES = registerItem("frosted_plains_cherries",
				new Item(new FabricItemSettings().food(new FoodComponent.Builder()
						.saturationModifier(1.0f).hunger(5)
						.statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1), 1.0f)
						.build()).group(INCANTATION_GROUP)));
		SILVER_NUGGET_APPLE = registerItem("silver_nugget_apple",
				new Item(new FabricItemSettings().food(new FoodComponent.Builder()
						.saturationModifier(6.0f).hunger(6)
						.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 1), 1.0f)
						.build()).group(INCANTATION_GROUP)));
		ENCHANTED_BERRIES = registerItem("enchanted_berries",
				new EnchantedBerryItem(new FabricItemSettings().food(new FoodComponent.Builder()
						.saturationModifier(4.0f).hunger(6).snack().build()).group(INCANTATION_GROUP)));
		ENCHANTED_BERRY_JAM = registerItem("enchanted_berry_jam",
				new EnchantedBerryJamItem(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));
		MYSTIC_LEATHER = registerItem("mystic_leather",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		MILKY_RESISTANCE_POTION  = registerPotion("milky_resistance",
				new Potion(new StatusEffectInstance(MILKY_RESISTANCE, 340, 0)));
		FLESHY_PUNISHMENT_POTION  = registerPotion("fleshy_punishment",
				new Potion(new StatusEffectInstance(FLESHY_PUNISHMENT, 180, 0)));
		FREEZING_RESISTANCE_POTION  = registerPotion("freezing_resistance",
				new Potion(new StatusEffectInstance(FREEZING_RESISTANCE, 260, 0)));

		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
				Items.MILK_BUCKET, MILKY_RESISTANCE_POTION);
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
				Items.ICE, FREEZING_RESISTANCE_POTION);
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
				Items.ROTTEN_FLESH, FLESHY_PUNISHMENT_POTION);

		BEWITCHMENT_TABLE_SCREEN_HANDLER =
				ScreenHandlerRegistry.registerSimple(new Identifier(INCANTATION_ID, "bewitchment_table"),
						BewitchmentTableScreenHandler::new);

		Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(INCANTATION_ID, BewitchmentTableRecipe.Serializer.ID),
				BewitchmentTableRecipe.Serializer.INSTANCE);
		Registry.register(Registry.RECIPE_TYPE, new Identifier(INCANTATION_ID, BewitchmentTableRecipe.Type.ID),
				BewitchmentTableRecipe.Type.INSTANCE);

		BEWITCHMENT_TABLE = registerBlock("bewitchment_table",
				new BewitchmentTableBlock(FabricBlockSettings.copy(Blocks.LECTERN)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "bewitchment_table"), new BlockItem(BEWITCHMENT_TABLE,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "milky_resistance"), MILKY_RESISTANCE);
		MILKY_RESISTANCE_SCROLL = registerItem("milky_resistance_scroll",
				new MilkyResistanceScrollItem(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));

		Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "freezing_resistance"), FREEZING_RESISTANCE);
		FREEZING_RESISTANCE_SCROLL = registerItem("freezing_resistance_scroll",
				new FreezingResistanceScrollItem(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));

		Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "fleshy_punishment"), FLESHY_PUNISHMENT);
		FLESHY_PUNISHMENT_SCROLL = registerItem("fleshy_punishment_scroll",
				new FleshyPunishmentScrollItem(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));

		if (FabricLoader.getInstance().isModLoaded("moreweaponry")) {
			MoreWeaponryCompat.moreWeaponryCompatRegistry();
		}

		if (FabricLoader.getInstance().isModLoaded("pickyourpoison")) {
			PickYourPoisonCompat.pickYourPoisonCompatRegistry();
		}

		if (FabricLoader.getInstance().isModLoaded("enhancedcelestials")) {
			EnhancedCelestialsCompat.enhancedCelestialsCompatRegistry();
		}

		BEWITCHMENT_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
				new Identifier(INCANTATION_ID, "bewitchment_table"),
				FabricBlockEntityTypeBuilder.create(BewitchmentTableEntity::new,
						BEWITCHMENT_TABLE).build(null));

		LOGGER.info("Incantation Awaits You!");
	}
}

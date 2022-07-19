package net.dakotapride.incantation.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.dakotapride.incantation.common.block.*;
import net.dakotapride.incantation.common.block.entity.BewitchmentTableBlock;
import net.dakotapride.incantation.common.block.entity.BewitchmentTableEntity;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.frosted.UnconcealedFrostedToleranceScroll;
import net.dakotapride.incantation.config.IncantationConfig;
import net.dakotapride.incantation.common.effect.EmptyDamageModifierStatusEffect;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.common.item.*;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.base.UnconcealedRaWrathScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.base.UnconcealedBleakScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.base.UnconcealedToleranceScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.extended.UnconcealedExtendedRaWrathScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.extended.UnconcealedExtendedBleakScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.frosted.UnconcealedFrostedRaWrathScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.frosted.UnconcealedFrostedBleakScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.stronger.UnconcealedStrongerRaWrathScroll;
import net.dakotapride.incantation.common.item.unconcealed_scrolls.stronger.UnconcealedStrongerBleakScroll;
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
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class IncantationMod implements ModInitializer {
	public static final String INCANTATION_ID = ("incantation");
	public static final Logger LOGGER = LoggerFactory.getLogger("incantation");


	public static StatusEffect TOLERANCE = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0xF9F7F7);
	public static ToleranceScrollItem TOLERANCE_SCROLL;
	public static StatusEffect BLEAK = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xCEFFF2);
	public static BleakScrollItem BLEAK_SCROLL;
	public static StatusEffect RA_WRATH = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0xC89661);
	public static RaWrathScrollItem RA_WRATH_SCROLL;
	public static StatusEffect ENCHANTED_BERRY_STRENGTH = new EmptyDamageModifierStatusEffect(StatusEffectCategory.NEUTRAL, 0x4F3F54, 6.0D)
			.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D,EntityAttributeModifier.Operation.ADDITION);
	public static ScreenHandlerType<BewitchmentTableScreenHandler> BEWITCHMENT_TABLE_SCREEN_HANDLER;
	public static BewitchmentTableBlock BEWITCHMENT_TABLE;
	public static BlockEntityType<BewitchmentTableEntity> BEWITCHMENT_TABLE_BLOCK_ENTITY;
	public static Potion TOLERANCE_POTION;
	public static Potion BLEAK_POTION;
	public static Potion RA_WRATH_POTION;
	public static Item PLAINS_CHERRIES;
	public static Item FROSTED_PLAINS_CHERRIES;
	public static Item SILVER_NUGGET_APPLE;
	public static Item MYSTIC_LEATHER;
	public static EnchantedBerryItem ENCHANTED_BERRIES;
	public static EnchantedBerryJamItem ENCHANTED_BERRY_JAM;
	public static UnconcealedToleranceScroll UNCONCEALED_TOLERANCE_SCROLL;
	public static UnconcealedFrostedToleranceScroll UNCONCEALED_FROSTED_TOLERANCE_SCROLL;
	public static UnconcealedBleakScroll UNCONCEALED_BLEAK_SCROLL;
	public static UnconcealedExtendedBleakScroll UNCONCEALED_LONG_BLEAK_SCROLL;
	public static UnconcealedStrongerBleakScroll UNCONCEALED_STRONG_BLEAK_SCROLL;
	public static UnconcealedFrostedBleakScroll UNCONCEALED_FROSTED_BLEAK_SCROLL;
	public static UnconcealedRaWrathScroll UNCONCEALED_RA_WRATH_SCROLL;
	public static UnconcealedExtendedRaWrathScroll UNCONCEALED_LONG_RA_WRATH_SCROLL;
	public static UnconcealedStrongerRaWrathScroll UNCONCEALED_STRONG_RA_WRATH_SCROLL;
	public static UnconcealedFrostedRaWrathScroll UNCONCEALED_FROSTED_RA_WRATH_SCROLL;
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
	public static Item FABRIC_PATCH;
	public static Block QUILTED_FABRIC_BLOCK;
	public static Item IRON_CAST_SHARD;
	public static RedstoneLampBlock JADE_CRYSTAL_LAMP;
	public static RedstoneLampBlock GREEN_JADE_CRYSTAL_LAMP;
	public static RedstoneLampBlock AMETHYST_CRYSTAL_LAMP;
	public static EnchantedBerryBushBlock ENCHANTED_BERRY_BUSH;
	public static NoEffectScrollItem BASE_SCROLL;

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

	public enum IncantationMaterials implements ToolMaterial {
		FABRIC_PATCHING(MiningLevels.HAND, 60, 1f,
				0f, 0,
				() -> Ingredient.ofItems(FABRIC_PATCH)),
		PACKED_FABRIC_PATCHING(MiningLevels.HAND, 80, 1f,
				0f, 0,
				() -> Ingredient.ofItems(QUILTED_FABRIC_BLOCK.asItem())),
		IRON_CASTING(MiningLevels.HAND, 20, 1f,
				0f, 0,
				() -> Ingredient.ofItems(IRON_CAST_SHARD));


		private final int miningLevel;
		private final int itemDurability;
		private final float miningSpeed;
		private final float attackDamage;
		private final int enchantability;
		private final Lazy<Ingredient> repairIngredient;

		IncantationMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
			this.miningLevel = miningLevel;
			this.itemDurability = itemDurability;
			this.miningSpeed = miningSpeed;
			this.attackDamage = attackDamage;
			this.enchantability = enchantability;
			this.repairIngredient = new Lazy<Ingredient>(repairIngredient);
		}

		@Override
		public int getDurability() {
			return this.itemDurability;
		}

		@Override
		public float getMiningSpeedMultiplier() {
			return this.miningSpeed;
		}

		@Override
		public float getAttackDamage() {
			return this.attackDamage;
		}

		@Override
		public int getMiningLevel() {
			return this.miningLevel;
		}

		@Override
		public int getEnchantability() {
			return this.enchantability;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return this.repairIngredient.get();
		}
	}

	public static final ItemGroup INCANTATION_GROUP = FabricItemGroupBuilder.create(
					new Identifier(INCANTATION_ID, "incantation"))
			.icon(() -> new ItemStack(Items.SPLASH_POTION))
			.appendItems(itemStacks -> {
				itemStacks.add(new ItemStack(BEWITCHMENT_TABLE));
				itemStacks.add(new ItemStack(GREEN_JADE_CRYSTAL_LAMP));
				itemStacks.add(new ItemStack(BUDDING_GREEN_JADE));
				itemStacks.add(new ItemStack(GREEN_JADE_BLOCK));
				itemStacks.add(new ItemStack(GREEN_JADE_CLUSTER));
				itemStacks.add(new ItemStack(SMALL_GREEN_JADE_BUD));
				itemStacks.add(new ItemStack(MEDIUM_GREEN_JADE_BUD));
				itemStacks.add(new ItemStack(LARGE_GREEN_JADE_BUD));
				itemStacks.add(new ItemStack(JADE_CRYSTAL_LAMP));
				itemStacks.add(new ItemStack(BUDDING_JADE));
				itemStacks.add(new ItemStack(JADE_BLOCK));
				itemStacks.add(new ItemStack(JADE_CLUSTER));
				itemStacks.add(new ItemStack(SMALL_JADE_BUD));
				itemStacks.add(new ItemStack(MEDIUM_JADE_BUD));
				itemStacks.add(new ItemStack(LARGE_JADE_BUD));
				itemStacks.add(new ItemStack(AMETHYST_CRYSTAL_LAMP));
				itemStacks.add(new ItemStack(AMMOLITE_ORE));
				itemStacks.add(new ItemStack(DEEPSLATE_AMMOLITE_ORE));
				itemStacks.add(new ItemStack(ALEXANDRITE_ORE));
				itemStacks.add(new ItemStack(DEEPSLATE_ALEXANDRITE_ORE));
				itemStacks.add(new ItemStack(PLAINS_CHERRIES));
				itemStacks.add(new ItemStack(FROSTED_PLAINS_CHERRIES));
				itemStacks.add(new ItemStack(SILVER_NUGGET_APPLE));
				itemStacks.add(new ItemStack(ENCHANTED_BERRIES));
				itemStacks.add(new ItemStack(ENCHANTED_BERRY_JAM));
				itemStacks.add(new ItemStack(MYSTIC_LEATHER));
				itemStacks.add(new ItemStack(FABRIC_PATCH));
				itemStacks.add(new ItemStack(RAW_AMMOLITE));
				itemStacks.add(new ItemStack(AMMOLITE_GEM));
				itemStacks.add(new ItemStack(AMMOLITE_LENS));
				itemStacks.add(new ItemStack(IRON_CAST_MOLD));
				itemStacks.add(new ItemStack(EMPTY_IRON_CASTING));
				itemStacks.add(new ItemStack(GREEN_JADE_SHARD));
				itemStacks.add(new ItemStack(JADE_SHARD));
				itemStacks.add(new ItemStack(RAW_ALEXANDRITE));
				itemStacks.add(new ItemStack(ALEXANDRITE_GEM));
				itemStacks.add(new ItemStack(BASE_SCROLL));
				itemStacks.add(new ItemStack(RA_WRATH_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_RA_WRATH_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_LONG_RA_WRATH_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_STRONG_RA_WRATH_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_FROSTED_RA_WRATH_SCROLL));
				itemStacks.add(new ItemStack(TOLERANCE_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_TOLERANCE_SCROLL));
				itemStacks.add(new ItemStack(BLEAK_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_BLEAK_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_LONG_BLEAK_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_STRONG_BLEAK_SCROLL));
				itemStacks.add(new ItemStack(UNCONCEALED_FROSTED_BLEAK_SCROLL));
				if (FabricLoader.getInstance().isModLoaded("pickyourpoison")) {
					itemStacks.add(new ItemStack(PickYourPoisonCompat.ECHOING_SCROLL));
					itemStacks.add(new ItemStack(PickYourPoisonCompat.UNCONCEALED_ECHOING_SCROLL));
					itemStacks.add(new ItemStack(PickYourPoisonCompat.UNCONCEALED_LONG_ECHOING_SCROLL));
					itemStacks.add(new ItemStack(PickYourPoisonCompat.UNCONCEALED_STRONG_ECHOING_SCROLL));
					itemStacks.add(new ItemStack(PickYourPoisonCompat.UNCONCEALED_FROSTED_ECHOING_SCROLL));
				}
				if (FabricLoader.getInstance().isModLoaded("moreweaponry")) {
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNWOUNDED_SCROLL));
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNCONCEALED_UNWOUNDED_SCROLL));
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNCONCEALED_LONG_UNWOUNDED_SCROLL));
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNCONCEALED_STRONG_UNWOUNDED_SCROLL));
					itemStacks.add(new ItemStack(MoreWeaponryCompat.UNCONCEALED_FROSTED_UNWOUNDED_SCROLL));
				}
				if (FabricLoader.getInstance().isModLoaded("enhancedcelestials")) {
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.REMEDY_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.UNCONCEALED_REMEDY_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.UNCONCEALED_LONG_REMEDY_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.UNCONCEALED_STRONG_REMEDY_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.UNCONCEALED_FROSTED_REMEDY_SCROLL));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.MOON_CREST_FRUIT));
					itemStacks.add(new ItemStack(EnhancedCelestialsCompat.MENDING_MOON_CREST_FRUIT));
				}
			})
			.build();

	public static RegistryEntry<ConfiguredFeature<GeodeFeatureConfig, ?>> GREEN_JADE_GEODE_FEATURE;
	public static RegistryEntry<PlacedFeature> GREEN_JADE_GEODE_PLACED;

	public static RegistryEntry<ConfiguredFeature<GeodeFeatureConfig, ?>> JADE_GEODE_FEATURE;
	public static RegistryEntry<PlacedFeature> JADE_GEODE_PLACED;

    private static final Identifier BIRCH_LEAVES_BLOCK_ID
            = new Identifier("minecraft", "blocks/birch_leaves");

	private void registerGeneration (String spawnBiomes, EnchantedBerryBushBlock bushBlock, int spawnChance, String name) {
		String[] biomes = spawnBiomes.replaceAll(" ", "").split(",");

		// Configure feature
		BlockPredicate blockPredicate = BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.wouldSurvive(bushBlock.getDefaultState(),
				BlockPos.ORIGIN), BlockPredicate.not(BlockPredicate.matchingBlocks(new Vec3i(0, -1, 0),
				List.of(bushBlock))));
		BlockStateProvider berryBushProvider = BlockStateProvider.of(bushBlock.getDefaultState().with(SweetBerryBushBlock.AGE, 3));
		RegistryEntry<PlacedFeature> placedFeatureEntry = PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
				berryBushProvider), blockPredicate);
		RandomPatchFeatureConfig randomPatchConfig = new RandomPatchFeatureConfig(32, 2, 3, placedFeatureEntry);
		ConfiguredFeature<RandomPatchFeatureConfig, ?> featureConfig = new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomPatchConfig);

		// Place feature
		List<PlacementModifier> placementModifiers = List.of(RarityFilterPlacementModifier.of(spawnChance), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
		RegistryEntry<PlacedFeature> placedFeature = PlacedFeatures.createEntry(Feature.RANDOM_PATCH, randomPatchConfig, placementModifiers.stream().toArray(PlacementModifier[]::new));

		// Register feature
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(INCANTATION_ID, "enchanted_berry_bush"), featureConfig);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(INCANTATION_ID,"enchanted_berry_bush"), placedFeature.getKeyOrValue().right().get());

		// Add to existing biome generation
		ArrayList<RegistryKey<Biome>> biomeKeys = new ArrayList<>();
		ArrayList<TagKey<Biome>> biomeTags = new ArrayList<>();

		for (String biome : biomes){
			// Category
			if (biome.charAt(0) == '#') {
				biomeTags.add(TagKey.of(Registry.BIOME_KEY, new Identifier(biome.substring(1))));
			} else {
				// Biome
				biomeKeys.add(RegistryKey.of(Registry.BIOME_KEY, new Identifier(biome)));
			}
		}

		registerBiomeGeneration(biomeKeys, biomeTags, placedFeature.getKeyOrValue().right().get());
	}

	private void registerBiomeGeneration(ArrayList<RegistryKey<Biome>> biomeKeys, ArrayList<TagKey<Biome>> biomeTags, PlacedFeature feature) {
		Predicate<BiomeSelectionContext> biomeSelector = BiomeSelectors.includeByKey(biomeKeys);

		if (!biomeTags.isEmpty()) {
			for (TagKey<Biome> biomeTag : biomeTags) {
				biomeSelector = biomeSelector.or(BiomeSelectors.tag(biomeTag));
			}
		}

		BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.VEGETAL_DECORATION, BuiltinRegistries.PLACED_FEATURE.getKey(feature).get());
	}

	public static IncantationConfig config;
	public String enchantedBerrySpawnBiomes = "#incantation:has_enchanted_berry_bushes";

	@Override
	public void onInitialize() {

		AutoConfig.register(IncantationConfig.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(IncantationConfig.class).getConfig();

        LootTableEvents.MODIFY.register(((resourceManager, manager, id, supplier, setter) -> {
            if (BIRCH_LEAVES_BLOCK_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .with(ItemEntry.builder(ENCHANTED_BERRIES))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)).build());
                supplier.pool(poolBuilder.build());
            }

        }));

		JADE_CRYSTAL_LAMP = registerBlock("jade_crystal_lamp",
				new RedstoneLampBlock(FabricBlockSettings.copy(Blocks.REDSTONE_LAMP).requiresTool()
						.luminance((state) -> state.get(RedstoneLampBlock.LIT) ? 13 : 0)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "jade_crystal_lamp"), new BlockItem(JADE_CRYSTAL_LAMP,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		GREEN_JADE_CRYSTAL_LAMP = registerBlock("green_jade_crystal_lamp",
				new RedstoneLampBlock(FabricBlockSettings.copy(Blocks.REDSTONE_LAMP).requiresTool()
						.luminance((state) -> state.get(RedstoneLampBlock.LIT) ? 13 : 0)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "green_jade_crystal_lamp"), new BlockItem(GREEN_JADE_CRYSTAL_LAMP,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		AMETHYST_CRYSTAL_LAMP = registerBlock("amethyst_crystal_lamp",
				new RedstoneLampBlock(FabricBlockSettings.copy(Blocks.REDSTONE_LAMP).requiresTool()
						.luminance((state) -> state.get(RedstoneLampBlock.LIT) ? 15 : 0)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "amethyst_crystal_lamp"), new BlockItem(AMETHYST_CRYSTAL_LAMP,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		ENCHANTED_BERRY_BUSH = registerBlock("enchanted_berry_bush",
				new EnchantedBerryBushBlock(FabricBlockSettings.copy(Blocks.SWEET_BERRY_BUSH)));
		registerGeneration(enchantedBerrySpawnBiomes, ENCHANTED_BERRY_BUSH, config.enchantedBerryBushSpawnChance, "enchanted_berry_bush");

		ALEXANDRITE_ORE = registerBlock("alexandrite_ore",
				new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE).requiresTool()));
		DEEPSLATE_ALEXANDRITE_ORE = registerBlock("deepslate_alexandrite_ore",
				new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE).requiresTool().requiresTool()));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "alexandrite_ore"), new BlockItem(ALEXANDRITE_ORE,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "deepslate_alexandrite_ore"), new BlockItem(DEEPSLATE_ALEXANDRITE_ORE,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		RAW_ALEXANDRITE = registerItem("raw_alexandrite",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		ALEXANDRITE_GEM = registerItem("alexandrite_gem",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		CASTING_JADE = registerItem("casting_jade",
				new CastingJadeItem(IncantationMaterials.IRON_CASTING,
						new FabricItemSettings().maxDamage(150).group(INCANTATION_GROUP)));
		CASTING_GREEN_JADE = registerItem("casting_green_jade",
				new CastingGreenJadeItem(IncantationMaterials.IRON_CASTING,
						new FabricItemSettings().maxDamage(150).group(INCANTATION_GROUP)));
		CASTING_AMETHYST = registerItem("casting_amethyst",
				new CastingAmethystItem(IncantationMaterials.IRON_CASTING,
						new FabricItemSettings().maxDamage(150).group(INCANTATION_GROUP)));
		AMMOLITE_LENS = registerItem("ammolite_lens",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		IRON_CAST_MOLD = registerItem("iron_casting_mold",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		EMPTY_IRON_CASTING = registerItem("empty_iron_casting",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		AMMOLITE_ORE = registerBlock("ammolite_ore",
				new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE).requiresTool()));
		DEEPSLATE_AMMOLITE_ORE = registerBlock("deepslate_ammolite_ore",
				new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE).requiresTool()));
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
		BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(),
				GenerationStep.Feature.UNDERGROUND_ORES, Objects.requireNonNull(IncantationPlacedFeatures.ALEXANDRITE_ORE_PLACED.getKey().orElse(null)));

		BUDDING_GREEN_JADE = registerBlock("budding_green_jade",
				new BuddingGreenJadeBlock(FabricBlockSettings.copy(Blocks.BUDDING_AMETHYST).requiresTool()));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "budding_green_jade"), new BlockItem(BUDDING_GREEN_JADE,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		GREEN_JADE_BLOCK = registerBlock("green_jade_block",
				new GreenJadeBlock(FabricBlockSettings.copy(Blocks.AMETHYST_BLOCK).requiresTool()));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "green_jade_block"), new BlockItem(GREEN_JADE_BLOCK,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		GREEN_JADE_SHARD = registerItem("green_jade_shard",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		FABRIC_PATCH = registerItem("fabric_patching",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		IRON_CAST_SHARD = registerItem("iron_casting_shard",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));
		QUILTED_FABRIC_BLOCK = registerBlock("quilted_fabric_block",
				new GreenJadeBlock(FabricBlockSettings.copy(Blocks.WHITE_WOOL).strength(2.3f)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "quilted_fabric_block"), new BlockItem(QUILTED_FABRIC_BLOCK,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		GREEN_JADE_CLUSTER = registerBlock("green_jade_bud",
				new GreenJadeClusterBlock(7, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER).requiresTool()));
		SMALL_GREEN_JADE_BUD = registerBlock("small_green_jade_bud",
				new GreenJadeClusterBlock(3, 4, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER).requiresTool()));
		MEDIUM_GREEN_JADE_BUD = registerBlock("medium_green_jade_bud",
				new GreenJadeClusterBlock(4, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER).requiresTool()));
		LARGE_GREEN_JADE_BUD = registerBlock("large_green_jade_bud",
				new GreenJadeClusterBlock(5, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER).requiresTool()));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "green_jade_bud"), new BlockItem(GREEN_JADE_CLUSTER,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "small_green_jade_bud"), new BlockItem(SMALL_GREEN_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "medium_green_jade_bud"), new BlockItem(MEDIUM_GREEN_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "large_green_jade_bud"), new BlockItem(LARGE_GREEN_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		GREEN_JADE_GEODE_FEATURE = ConfiguredFeatures.register("green_jade_geode", Feature.GEODE ,
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
				GREEN_JADE_GEODE_FEATURE, RarityFilterPlacementModifier.of(42),
				SquarePlacementModifier.of(),
				HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.aboveBottom(50)),
				BiomePlacementModifier.of());

		BiomeModifications.addFeature(BiomeSelectors.tag(IncantationTags.HAS_JADE_GEODE),
						GenerationStep.Feature.UNDERGROUND_DECORATION, Objects.requireNonNull(GREEN_JADE_GEODE_PLACED.getKey().orElse(null)));

		BUDDING_JADE = registerBlock("budding_jade",
				new BuddingJadeBlock(FabricBlockSettings.copy(Blocks.BUDDING_AMETHYST).requiresTool()));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "budding_jade"), new BlockItem(BUDDING_JADE,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		JADE_BLOCK = registerBlock("jade_block",
				new JadeBlock(FabricBlockSettings.copy(Blocks.AMETHYST_BLOCK).requiresTool()));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "jade_block"), new BlockItem(JADE_BLOCK,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		JADE_SHARD = registerItem("jade_shard",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		JADE_CLUSTER = registerBlock("jade_bud",
				new JadeClusterBlock(7, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER).requiresTool()));
		SMALL_JADE_BUD = registerBlock("small_jade_bud",
				new JadeClusterBlock(3, 4, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER).requiresTool()));
		MEDIUM_JADE_BUD = registerBlock("medium_jade_bud",
				new JadeClusterBlock(4, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER).requiresTool()));
		LARGE_JADE_BUD = registerBlock("large_jade_bud",
				new JadeClusterBlock(5, 3, FabricBlockSettings.copy(Blocks.AMETHYST_CLUSTER).requiresTool()));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "jade_bud"), new BlockItem(JADE_CLUSTER,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "small_jade_bud"), new BlockItem(SMALL_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "medium_jade_bud"), new BlockItem(MEDIUM_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, "large_jade_bud"), new BlockItem(LARGE_JADE_BUD,
				new FabricItemSettings().group(INCANTATION_GROUP)));

		JADE_GEODE_FEATURE = ConfiguredFeatures.register("jade_geode", Feature.GEODE ,
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
				JADE_GEODE_FEATURE, RarityFilterPlacementModifier.of(42),
				SquarePlacementModifier.of(),
				HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.aboveBottom(50)),
				BiomePlacementModifier.of());

		BiomeModifications.addFeature(BiomeSelectors.tag(IncantationTags.HAS_JADE_GEODE),
				GenerationStep.Feature.UNDERGROUND_DECORATION, Objects.requireNonNull(JADE_GEODE_PLACED.getKey().orElse(null)));

		BASE_SCROLL = registerItem("scroll",
				new NoEffectScrollItem(new FabricItemSettings().group(INCANTATION_GROUP)));

		UNCONCEALED_RA_WRATH_SCROLL = registerItem("unconcealed_ra_wrath_scroll",
				new UnconcealedRaWrathScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));
		UNCONCEALED_FROSTED_RA_WRATH_SCROLL = registerItem("frosted_unconcealed_ra_wrath_scroll",
				new UnconcealedFrostedRaWrathScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));
		UNCONCEALED_LONG_RA_WRATH_SCROLL = registerItem("long_unconcealed_ra_wrath_scroll",
				new UnconcealedExtendedRaWrathScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));
		UNCONCEALED_STRONG_RA_WRATH_SCROLL = registerItem("strong_unconcealed_ra_wrath_scroll",
				new UnconcealedStrongerRaWrathScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));

		UNCONCEALED_TOLERANCE_SCROLL = registerItem("unconcealed_tolerance_scroll",
				new UnconcealedToleranceScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));
		UNCONCEALED_FROSTED_TOLERANCE_SCROLL = registerItem("unconcealed_frosted_tolerance_scroll",
				new UnconcealedFrostedToleranceScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));

		UNCONCEALED_BLEAK_SCROLL = registerItem("unconcealed_bleak_scroll",
				new UnconcealedBleakScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));
		UNCONCEALED_FROSTED_BLEAK_SCROLL = registerItem("frosted_unconcealed_bleak_scroll",
				new UnconcealedFrostedBleakScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));
		UNCONCEALED_LONG_BLEAK_SCROLL = registerItem("long_unconcealed_bleak_scroll",
				new UnconcealedExtendedBleakScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));
		UNCONCEALED_STRONG_BLEAK_SCROLL = registerItem("strong_unconcealed_bleak_scroll",
				new UnconcealedStrongerBleakScroll(IncantationMaterials.PACKED_FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(80).group(INCANTATION_GROUP)));

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
				new EnchantedBerryItem(ENCHANTED_BERRY_BUSH, new FabricItemSettings().food(new FoodComponent.Builder()
						.saturationModifier(4.0f).hunger(6).snack().build()).group(INCANTATION_GROUP)));
		ENCHANTED_BERRY_JAM = registerItem("enchanted_berry_jam",
				new EnchantedBerryJamItem(new FabricItemSettings().maxCount(16).group(INCANTATION_GROUP)));
		MYSTIC_LEATHER = registerItem("mystic_leather",
				new Item(new FabricItemSettings().group(INCANTATION_GROUP)));

		TOLERANCE_POTION = registerPotion("tolerance",
				new Potion(new StatusEffectInstance(TOLERANCE, 500, 0)));
		RA_WRATH_POTION = registerPotion("ra_wrath",
				new Potion(new StatusEffectInstance(RA_WRATH, 520, 0)));
		BLEAK_POTION = registerPotion("bleak",
				new Potion(new StatusEffectInstance(BLEAK, 480, 0)));

		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
				Items.MILK_BUCKET, TOLERANCE_POTION);
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
				Items.ICE, BLEAK_POTION);
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
				Items.ROTTEN_FLESH, RA_WRATH_POTION);

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

		Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "enchanted_berry_strength"), ENCHANTED_BERRY_STRENGTH);

		Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "tolerance"), TOLERANCE);
		TOLERANCE_SCROLL = registerItem("tolerance_scroll",
				new ToleranceScrollItem(IncantationMaterials.FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(60).group(INCANTATION_GROUP)));

		Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "bleak"), BLEAK);
		BLEAK_SCROLL = registerItem("bleak_scroll",
				new BleakScrollItem(IncantationMaterials.FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(60).group(INCANTATION_GROUP)));

		Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "ra_wrath"), RA_WRATH);
		RA_WRATH_SCROLL = registerItem("ra_wrath_scroll",
				new RaWrathScrollItem(IncantationMaterials.FABRIC_PATCHING,
						new FabricItemSettings().maxCount(1).maxDamage(60).group(INCANTATION_GROUP)));

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

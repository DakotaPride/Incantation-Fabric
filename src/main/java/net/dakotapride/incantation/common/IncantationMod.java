package net.dakotapride.incantation.common;

import net.dakotapride.incantation.common.block.entity.BewitchmentTableBlock;
import net.dakotapride.incantation.common.block.entity.BewitchmentTableEntity;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.common.item.*;
import net.dakotapride.incantation.common.recipe.BewitchmentTableRecipe;
import net.dakotapride.incantation.common.screen.BewitchmentTableScreenHandler;
import net.dakotapride.incantation.compat.enhancedcelestials.EnhancedCelestialsCompat;
import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.dakotapride.incantation.compat.pickyourpoison.PickYourPoisonCompat;
import net.dakotapride.incantation.mixin.BrewingRecipeRegistryMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public static EnchantedBerryItem ENCHANTED_BERRIES;
	public static EnchantedBerryJamItem ENCHANTED_BERRY_JAM;


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

	public static final ItemGroup INCANTATION_GROUP = FabricItemGroupBuilder.create(
					new Identifier(INCANTATION_ID, "incantation"))
			.icon(() -> new ItemStack(Items.SPLASH_POTION)).build();

	@Override
	public void onInitialize() {

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
				new BewitchmentTableBlock(FabricBlockSettings.copy(Blocks.CAULDRON)));
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

		if (FabricLoader.getInstance().isModLoaded("enhancedcelestials")) {
			EnhancedCelestialsCompat.enhancedCelestialsCompatRegistry();
		}

		if (FabricLoader.getInstance().isModLoaded("pickyourpoison")) {
			PickYourPoisonCompat.pickYourPoisonCompatRegistry();
		}

		BEWITCHMENT_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
				new Identifier(INCANTATION_ID, "bewitchment_table"),
				FabricBlockEntityTypeBuilder.create(BewitchmentTableEntity::new,
						BEWITCHMENT_TABLE).build(null));

		LOGGER.info("Incantation Awaits You!");
	}
}

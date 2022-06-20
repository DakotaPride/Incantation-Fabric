package net.dakotapride.incantation.common;

import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.common.item.EffectScrollItem;
import net.dakotapride.incantation.common.item.MilkyResistanceScrollItem;
import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncantationMod implements ModInitializer {
	public static final String INCANTATION_ID = ("incantation");
	public static final Logger LOGGER = LoggerFactory.getLogger("incantation");

	public static StatusEffect MILKY_RESISTANCE = new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0xF9F7F7);
	public static MilkyResistanceScrollItem MILKY_RESISTANCE_SCROLL;

	public static EffectScrollItem FREEZING_RESISTANCE_SCROLL;


	// Registration
	public static <T extends Block> T registerBlock(String name, T block) {
		Registry.register(Registry.BLOCK, new Identifier(INCANTATION_ID, name), block);
		return block;
	}

	public static <T extends Item> T registerItem(String name, T item) {
		Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, name), item);
		return item;
	}

	public static final ItemGroup INCANTATION_GROUP = FabricItemGroupBuilder.create(
					new Identifier(INCANTATION_ID, "incantation"))
			.icon(() -> new ItemStack(Items.SPLASH_POTION)).build();

	@Override
	public void onInitialize() {

		if (FabricLoader.getInstance().isModLoaded("moreweaponry")) {
			MoreWeaponryCompat.moreWeaponryCompatRegistry();
		}

		Registry.register(Registry.STATUS_EFFECT, new Identifier(INCANTATION_ID, "milky_resistance"), MILKY_RESISTANCE);
		MILKY_RESISTANCE_SCROLL = registerItem("milky_resistance_scroll",
				new MilkyResistanceScrollItem(new FabricItemSettings().group(INCANTATION_GROUP)));

		FREEZING_RESISTANCE_SCROLL = registerItem("freezing_resistance_scroll",
				new EffectScrollItem(new FabricItemSettings().group(INCANTATION_GROUP)));

		LOGGER.info("Incantation Awaits You!");
	}
}

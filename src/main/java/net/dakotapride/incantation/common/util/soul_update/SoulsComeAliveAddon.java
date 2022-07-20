package net.dakotapride.incantation.common.util.soul_update;

import net.dakotapride.incantation.common.IncantationMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoulsComeAliveAddon {

    public static Item PLAYER_SOUL_FRAGMENT;
    public static Item HOSTILE_SOUL_FRAGMENT;
    public static Item PASSIVE_SOUL_FRAGMENT;

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(IncantationMod.INCANTATION_ID, name), item);
        return item;
    }


    public static void registerSoulsComeAliveAddon() {
        PLAYER_SOUL_FRAGMENT = registerItem("player_soul_shard",
                new Item(new FabricItemSettings()));
        HOSTILE_SOUL_FRAGMENT = registerItem("hostile_soul_shard",
                new Item(new FabricItemSettings()));
        PASSIVE_SOUL_FRAGMENT = registerItem("passive_soul_shard",
                new Item(new FabricItemSettings()));
    }

}

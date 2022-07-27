package net.dakotapride.incantation.compat.createFabric;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CreateFabricCompat {
    // 1.18.2 Exclusively Currently

    public static final String CREATE_FABRIC_ID = ("create");



    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(CREATE_FABRIC_ID, name), item);
        return item;
    }

    public static void createCompatRegistry() {

    }

}

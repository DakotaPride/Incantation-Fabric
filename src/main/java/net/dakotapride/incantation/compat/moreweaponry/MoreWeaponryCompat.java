package net.dakotapride.incantation.compat.moreweaponry;


import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.item.EffectScrollItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MoreWeaponryCompat {
    public static final String MORE_WEAPONRY_ID = ("moreweaponry");

    // Items

    public static EffectScrollItem COLD_BLOODED_SCROLL;
    public static Item COLD_BLOODED_IMBUED_ITEM;
    public static Item COLD_BLOODED_TWIST_ITEM;


    // Registration

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(MORE_WEAPONRY_ID, name), item);
        return item;
    }


    public static void moreWeaponryCompatRegistry() {

        // Items

        COLD_BLOODED_SCROLL = registerItem("cold_blooded_scroll",
                new EffectScrollItem(new FabricItemSettings().group(IncantationMod.INCANTATION_GROUP)));

    }

}

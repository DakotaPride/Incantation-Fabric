package net.dakotapride.incantation.compat.patchouli.items;

import net.dakotapride.incantation.common.IncantationMod;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

public class BewitchmentBookItem extends Item {
    public BewitchmentBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (user instanceof ServerPlayerEntity && FabricLoader.getInstance().isModLoaded("patchouli")) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            PatchouliAPI.get().openBookGUI(player, new Identifier(IncantationMod.INCANTATION_ID, "bewitchment_book"));
        }

        return TypedActionResult.success(itemStack);
    }

}

package net.dakotapride.incantation.client;

import de.guntram.mcmod.crowdintranslate.CrowdinTranslate;
import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.screen.BewitchmentTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.lwjgl.glfw.GLFW;

public class IncantationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        CrowdinTranslate.downloadTranslations("incantation", "incantation");

        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.GREEN_JADE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.SMALL_GREEN_JADE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.MEDIUM_GREEN_JADE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.LARGE_GREEN_JADE_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.JADE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.SMALL_JADE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.MEDIUM_JADE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.LARGE_JADE_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.ENCHANTED_BERRY_BUSH, RenderLayer.getCutout());

        ScreenRegistry.register(IncantationMod.BEWITCHMENT_TABLE_SCREEN_HANDLER, BewitchmentTableScreen::new);
    }
}

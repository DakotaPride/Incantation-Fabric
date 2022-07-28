package net.dakotapride.incantation.client;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.screen.BewitchmentTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class IncantationClient implements ClientModInitializer {
    public static final String KEY_CATEGORY_INCANTATION = "key.category.incantation.leveling";
    public static final String KEY_SHOW_ARTEFACT_LEVEL = "key.incantation.artefact_leveling";
    public static KeyBinding showArtefactLeveling;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (showArtefactLeveling.wasPressed()) {
            }});
        showArtefactLeveling = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SHOW_ARTEFACT_LEVEL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                KEY_CATEGORY_INCANTATION
        ));

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

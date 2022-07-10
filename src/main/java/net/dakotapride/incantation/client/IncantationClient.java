package net.dakotapride.incantation.client;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.screen.BewitchmentTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

public class IncantationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.GREEN_JADE_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.SMALL_GREEN_JADE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.MEDIUM_GREEN_JADE_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IncantationMod.LARGE_GREEN_JADE_BUD, RenderLayer.getCutout());

        ScreenRegistry.register(IncantationMod.BEWITCHMENT_TABLE_SCREEN_HANDLER, BewitchmentTableScreen::new);
    }
}

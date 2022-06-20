package net.dakotapride.incantation.client;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.screen.BewitchmentTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class IncantationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(IncantationMod.BEWITCHMENT_TABLE_SCREEN_HANDLER, BewitchmentTableScreen::new);
    }
}

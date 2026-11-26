package com.herobrinecoop.client;

import com.herobrinecoop.HerobrineCoop;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HerobrineCoop.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class CoopMenuEvents {
    private CoopMenuEvents() {}

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof TitleScreen screen) {
            int x = screen.width / 2 - 100;
            int y = screen.height / 4 + 120;
            event.addListener(Button.builder(Component.literal("Herobrine Co-op"), button ->
                    screen.getMinecraft().setScreen(new CoopScreen(screen)))
                .bounds(x, y, 200, 20)
                .build());
        } else if (event.getScreen() instanceof PauseScreen screen) {
            int x = screen.width / 2 - 100;
            int y = screen.height / 4 + 96;
            event.addListener(Button.builder(Component.literal("Herobrine Co-op / LAN"), button ->
                    screen.getMinecraft().setScreen(new CoopScreen(screen)))
                .bounds(x, y, 200, 20)
                .build());
        }
    }
}

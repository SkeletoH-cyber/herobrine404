package com.herobrinecoop.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.ConnectScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameType;

public class CoopScreen extends Screen {
    private final Screen parent;
    private String status = "Откройте мир для LAN или подключитесь по адресу друга.";

    public CoopScreen(Screen parent) {
        super(Component.literal("Herobrine Co-op"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        int top = this.height / 4;

        this.addRenderableWidget(Button.builder(Component.literal("Создать LAN-сервер"), b -> openLan())
            .bounds(center - 100, top, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Подключиться к другу"), b ->
                this.minecraft.setScreen(new JoinLanScreen(this)))
            .bounds(center - 100, top + 28, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Назад"), b -> this.minecraft.setScreen(parent))
            .bounds(center - 100, top + 64, 200, 20).build());
    }

    private void openLan() {
        if (this.minecraft == null || this.minecraft.getSingleplayerServer() == null) {
            status = "Сначала загрузи одиночный мир, затем открой меню паузы.";
            return;
        }
        String port = this.minecraft.getSingleplayerServer().shareToLAN(GameType.SURVIVAL, false);
        status = port == null
            ? "Не удалось открыть LAN. Попробуй ещё раз."
            : "LAN открыт! Сообщи другу свой локальный IP и порт " + port;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, this.height / 4 - 24, 0xFFFFFF);
        graphics.drawCenteredString(this.font, this.status, this.width / 2, this.height / 4 + 100, 0xE0E0E0);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }

    public void connect(String address) {
        ServerAddress parsed = ServerAddress.parseString(address.trim());
        ServerData data = new ServerData("Herobrine Co-op", address.trim(), ServerData.Type.OTHER);
        ConnectScreen.startConnecting(this, Minecraft.getInstance(), parsed, data, false);
    }
}

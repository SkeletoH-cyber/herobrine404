package com.herobrinecoop.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class JoinLanScreen extends Screen {
    private final CoopScreen parent;
    private EditBox address;

    public JoinLanScreen(CoopScreen parent) {
        super(Component.literal("Подключиться к другу"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        int top = this.height / 2 - 40;

        address = new EditBox(this.font, center - 100, top, 200, 20, Component.literal("IP:порт"));
        address.setMaxLength(128);
        address.setValue("192.168.1.2:25565");
        address.setHint(Component.literal("например 192.168.1.10:54321"));
        this.addRenderableWidget(address);

        this.addRenderableWidget(Button.builder(Component.literal("Подключиться"), b -> {
            if (!address.getValue().isBlank()) parent.connect(address.getValue());
        }).bounds(center - 100, top + 30, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Назад"), b ->
                this.minecraft.setScreen(parent))
            .bounds(center - 100, top + 58, 200, 20).build());

        this.setInitialFocus(address);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - 72, 0xFFFFFF);
        graphics.drawCenteredString(this.font, "Введите IP-адрес и порт, показанные у друга", this.width / 2,
            this.height / 2 - 54, 0xCFCFCF);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }
}

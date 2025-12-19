package com.goatchez.iron_bloomery.blocks.bloomery;

import com.goatchez.iron_bloomery.IronBloomery;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BloomeryScreen extends AbstractContainerScreen<BloomeryMenu> {

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, "textures/gui/bloomery/bloomery_gui.png");
    private static final ResourceLocation STEAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, "textures/gui/bloomery/bloomery_arrow_1_progress.png");
    private static final ResourceLocation ARROW_TEXTURE = ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, "textures/gui/bloomery/bloomery_arrow_2_progress.png");
    private static final ResourceLocation LIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, "textures/gui/bloomery/bloomery_lit_progress.png");

    public BloomeryScreen(BloomeryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        renderLitProgress(guiGraphics, x, y);

        if (menu.getProgress() >= 200) {
            guiGraphics.blit(STEAM_TEXTURE, x + 67, y + 35, 0, 0, 22, 17, 22, 17);
            renderProgressArrow(guiGraphics, x, y);
        } else {
            renderProgressSteam(guiGraphics, x, y);
        }
    }

    private void renderProgressSteam(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(STEAM_TEXTURE, x + 67, y + 35, 0, 0, menu.getScaledSteamProgress(), 17, 22, 17);
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(ARROW_TEXTURE, x + 67, y + 36, 0, 0, menu.getScaledArrowProgress(), 16, 33, 16);
        }
    }

    private void renderLitProgress(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isLit()) {
            guiGraphics.blit(LIT_TEXTURE, x + 42, y + 36 + (15 - menu.getScaledLitProgress()), 0, 15 - menu.getScaledLitProgress(), 15, menu.getScaledLitProgress(), 15, 15);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

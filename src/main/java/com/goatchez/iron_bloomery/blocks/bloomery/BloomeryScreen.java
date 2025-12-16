package com.goatchez.iron_bloomery.blocks.bloomery;

import com.goatchez.iron_bloomery.IronBloomery;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientActivePlayersTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.List;

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

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        renderLitProgress(guiGraphics, x, y);

        if (menu.getProgress() >= 200) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, STEAM_TEXTURE, x + 67, y + 35, 0, 0, 22, 17, 22, 17);
            renderProgressArrow(guiGraphics, x, y);
        } else {
            renderProgressSteam(guiGraphics, x, y);
        }
    }

    private void renderProgressSteam(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, STEAM_TEXTURE, x + 67, y + 35, 0, 0, menu.getScaledSteamProgress(), 17, 22, 17);
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 67, y + 36, 0, 0, menu.getScaledArrowProgress(), 16, 33, 16);
        }
    }

    private void renderLitProgress(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isLit()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, LIT_TEXTURE, x + 42, y + 36 + (15 - menu.getScaledLitProgress()), 0, 15 - menu.getScaledLitProgress(), 15, menu.getScaledLitProgress(), 15, 15);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

//        if (isHovering(47, 14, 16, 16, mouseX, mouseY)) {
//            guiGraphics.renderTooltip(font, List.of(ClientTooltipComponent.create(), mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, );
//        }
    }
}

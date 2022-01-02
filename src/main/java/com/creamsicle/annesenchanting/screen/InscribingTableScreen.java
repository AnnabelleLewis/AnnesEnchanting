package com.creamsicle.annesenchanting.screen;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import com.creamsicle.annesenchanting.container.InscribingTableContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class InscribingTableScreen extends AbstractContainerScreen<InscribingTableContainer> {

    private ResourceLocation GUI = new ResourceLocation(AnnesEnchanting.MOD_ID,"textures/gui/inscribing_table_gui.png");

    public InscribingTableScreen(InscribingTableContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }





    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        drawString(pPoseStack, Minecraft.getInstance().font, "Inscribing", 10,10, 0xffffff);
    }


    @Override
    protected void renderBg(PoseStack matrixStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0,GUI);
        int relx = (this.width - this.imageWidth) / 2;
        int rely = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relx,rely,0,0,this.imageWidth,this.imageHeight);
    }
}

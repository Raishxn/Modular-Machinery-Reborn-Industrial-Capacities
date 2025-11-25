package com.raishxn.mmr_industrial_capacities.client.screen;

import com.raishxn.mmr_industrial_capacities.client.container.DeepStorageContainer;
import com.raishxn.mmr_industrial_capacities.common.tile.DeepStorageBusTile;
import es.degrassi.mmreborn.client.screen.BaseScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DeepStorageScreen extends BaseScreen<DeepStorageContainer, DeepStorageBusTile> {

    public DeepStorageScreen(DeepStorageContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, false);
        this.imageHeight = 222;
        this.imageWidth = 176;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        clearWidgets();
        renderBgWithSlotSize(guiGraphics, 9, 54);
        renderSlots(guiGraphics);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);

        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, 140, 4210752, false);
    }
}
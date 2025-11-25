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
        // Ajusta o tamanho da janela para caber 9 linhas + inventário do jogador
        this.imageHeight = 256; // Altura máxima padrão, ajustável se necessário
        this.imageWidth = 176;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        clearWidgets();
        // Forçamos o desenho de um background de 9 colunas e 81 slots
        // O BaseScreen deve tentar adaptar o tamanho da textura
        renderBgWithSlotSize(guiGraphics, 9, 81);

        // Desenha os slots (itens)
        renderSlots(guiGraphics);
    }

    // Renderiza as etiquetas (Inventory, Title) nas posições corretas
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        // Empurra o label do inventário do jogador para baixo
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, 18 + (9 * 18) + 4, 4210752, false);
    }
}
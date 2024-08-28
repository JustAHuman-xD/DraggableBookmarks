package me.justahuman.draggablebookmarks.api;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public interface BookmarkExtension {
    void draggablebookmarks$renderForeground(Minecraft minecraft, PoseStack poseStack, AbstractContainerScreen<?> screen, int mouseX, int mouseY);
}

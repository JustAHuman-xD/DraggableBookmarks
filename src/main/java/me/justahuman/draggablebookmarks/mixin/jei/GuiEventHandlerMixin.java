package me.justahuman.draggablebookmarks.mixin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import me.justahuman.draggablebookmarks.api.BookmarkExtension;
import mezz.jei.common.gui.GuiEventHandler;
import mezz.jei.common.gui.overlay.bookmarks.LeftAreaDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiEventHandler.class)
public class GuiEventHandlerMixin {
    @Shadow(remap = false) @Final private LeftAreaDispatcher leftAreaDispatcher;

    @Inject(at = @At("TAIL"), method = "onDrawForeground", remap = false)
    public void onRenderForeground(AbstractContainerScreen<?> screen, PoseStack poseStack, int mouseX, int mouseY, CallbackInfo ci) {
        if (((LeftAreaDispatcherAccessor) this.leftAreaDispatcher).getContents() instanceof BookmarkExtension extension) {
            Minecraft minecraft = screen.getMinecraft();
            extension.draggablebookmarks$renderForeground(minecraft, poseStack, screen, mouseX, mouseY);
        }
    }
}

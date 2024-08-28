package me.justahuman.draggablebookmarks.mixin.jei;

import me.justahuman.draggablebookmarks.api.BookmarkExtension;
import mezz.jei.forge.events.GuiEventHandler;
import mezz.jei.gui.overlay.bookmarks.LeftAreaDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.client.event.ContainerScreenEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiEventHandler.class)
public class GuiEventHandlerMixin {
    @Shadow(remap = false) @Final private LeftAreaDispatcher leftAreaDispatcher;

    @Inject(at = @At("TAIL"), method = "onDrawForegroundEvent", remap = false)
    public void onRenderForeground(ContainerScreenEvent.DrawForeground event, CallbackInfo ci) {
        if (((LeftAreaDispatcherAccessor) this.leftAreaDispatcher).getContents() instanceof BookmarkExtension extension) {
            AbstractContainerScreen<?> screen = event.getContainerScreen();
            Minecraft minecraft = screen.getMinecraft();
            extension.draggablebookmarks$renderForeground(minecraft, event.getPoseStack(), screen, event.getMouseX(), event.getMouseY());
        }
    }
}

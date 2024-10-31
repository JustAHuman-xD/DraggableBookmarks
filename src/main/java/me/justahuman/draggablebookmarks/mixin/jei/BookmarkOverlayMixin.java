package me.justahuman.draggablebookmarks.mixin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import me.justahuman.draggablebookmarks.api.BookmarkExtension;
import mezz.jei.common.Internal;
import mezz.jei.common.bookmarks.BookmarkList;
import mezz.jei.common.gui.GuiScreenHelper;
import mezz.jei.common.gui.ghost.GhostIngredientDragManager;
import mezz.jei.common.gui.overlay.IngredientGridWithNavigation;
import mezz.jei.common.gui.overlay.bookmarks.BookmarkOverlay;
import mezz.jei.common.gui.textures.Textures;
import mezz.jei.common.input.IKeyBindings;
import mezz.jei.common.input.IUserInputHandler;
import mezz.jei.common.input.handlers.CombinedInputHandler;
import mezz.jei.common.network.IConnectionToServer;
import mezz.jei.core.config.IClientConfig;
import mezz.jei.core.config.IWorldConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookmarkOverlay.class)
public abstract class BookmarkOverlayMixin implements BookmarkExtension {
    @Unique private GhostIngredientDragManager draggablebookmarks$ghostIngredientDragManager;

    @Shadow(remap = false) @Final private IngredientGridWithNavigation contents;
    @Shadow(remap = false) @Final private IWorldConfig worldConfig;

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(BookmarkList bookmarkList, Textures textures, IngredientGridWithNavigation contents, IClientConfig clientConfig, IWorldConfig worldConfig, GuiScreenHelper guiScreenHelper, IConnectionToServer serverConnection, IKeyBindings keyBindings, CallbackInfo ci) {
        this.draggablebookmarks$ghostIngredientDragManager = new GhostIngredientDragManager(this.contents, guiScreenHelper, Internal.getRegisteredIngredients(), this.worldConfig);
    }

    @Override
    public void draggablebookmarks$renderForeground(Minecraft minecraft, PoseStack poseStack, AbstractContainerScreen<?> gui, int mouseX, int mouseY) {
        if (this.isListDisplayed()) {
            poseStack.pushPose();
            poseStack.translate(-gui.getGuiLeft(), -gui.getGuiTop(), 0.0);
            this.draggablebookmarks$ghostIngredientDragManager.drawOnForeground(minecraft, poseStack, mouseX, mouseY);
            poseStack.popPose();
        }
    }

    @Inject(at = @At("TAIL"), method = "drawTooltips", remap = false)
    public void drawTooltips(Minecraft minecraft, PoseStack poseStack, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.isListDisplayed()) {
            this.draggablebookmarks$ghostIngredientDragManager.drawTooltips(minecraft, poseStack, mouseX, mouseY);
        }
    }

    @ModifyVariable(method = "createInputHandler", name = "displayedInputHandler", at = @At(value = "STORE", ordinal = 0), remap = false)
    public IUserInputHandler createInputHandler(IUserInputHandler value) {
        return new CombinedInputHandler(this.draggablebookmarks$ghostIngredientDragManager.createInputHandler(), value);
    }

    @Shadow(remap = false) public abstract boolean isListDisplayed();
}

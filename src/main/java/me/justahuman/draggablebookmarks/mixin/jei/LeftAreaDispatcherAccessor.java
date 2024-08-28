package me.justahuman.draggablebookmarks.mixin.jei;

import mezz.jei.gui.overlay.bookmarks.ILeftAreaContent;
import mezz.jei.gui.overlay.bookmarks.LeftAreaDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LeftAreaDispatcher.class)
public interface LeftAreaDispatcherAccessor {
    @Accessor(remap = false)
    ILeftAreaContent getContents();
}

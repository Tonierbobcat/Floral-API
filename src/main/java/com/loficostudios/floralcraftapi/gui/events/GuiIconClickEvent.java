package com.loficostudios.floralcraftapi.gui.events;

import com.loficostudios.floralcraftapi.gui.events.base.GuiEvent;
import com.loficostudios.floralcraftapi.gui.guiicon.GuiIcon;
import com.loficostudios.floralcraftapi.gui.interfaces.FloralGui;
import org.bukkit.entity.Player;


public class GuiIconClickEvent extends GuiEvent {
    private final GuiIcon icon;
    public GuiIconClickEvent(Player player, FloralGui gui, GuiIcon icon) {
        super(player, gui);
        this.icon = icon;
    }

    public GuiIcon getIcon() {
        return icon;
    }
}

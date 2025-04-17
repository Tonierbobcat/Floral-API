package com.loficostudios.floralcraftapi.gui.events;

import com.loficostudios.floralcraftapi.gui.events.base.GuiEvent;
import com.loficostudios.floralcraftapi.gui.interfaces.IGui;
import org.bukkit.entity.Player;

public class GuiCloseEvent extends GuiEvent {
    public GuiCloseEvent(Player player, IGui gui) {
        super(player, gui);
    }
}

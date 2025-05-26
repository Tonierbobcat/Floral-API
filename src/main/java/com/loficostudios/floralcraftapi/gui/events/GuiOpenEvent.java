package com.loficostudios.floralcraftapi.gui.events;

import com.loficostudios.floralcraftapi.gui.events.base.GuiEvent;
import com.loficostudios.floralcraftapi.gui.interfaces.FloralGui;
import org.bukkit.entity.Player;

public class GuiOpenEvent extends GuiEvent {
    public GuiOpenEvent(Player player, FloralGui gui) {
        super(player, gui);
    }
}

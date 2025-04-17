package com.loficostudios.floralcraftapi.gui;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.gui.guiicon.GuiIcon;
import com.loficostudios.floralcraftapi.utils.bukkit.MinecraftText;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class ConfirmationGui extends PopOutGui {

    private final Consumer<Player> onYes;
    private final Consumer<Player> onNo;

    public ConfirmationGui(String message, Consumer<Player> onClose, Consumer<Player> onYes, Consumer<Player> onNo) {
        super(9, Component.text(message), onClose);
        this.onNo = onNo;
        this.onYes = onYes;

        setSlot(2, getYesButton());
        setSlot(6, getNoButton());
    }
    public ConfirmationGui(String message, Consumer<Player> onClose, Consumer<Player> onYes) {
        this(message, onClose, onYes, null);
    }

    private GuiIcon getYesButton() {
        return new GuiIcon(Material.LIME_STAINED_GLASS, MinecraftText.MiniMessage.parse("<green><bold>Confirm"), (p, c) -> {
            close(p);
            FloralCraftAPI.runTaskLater(new BukkitRunnable() {
                @Override
                public void run() {
                    if (onYes != null) {
                        onYes.accept(p);
                    }
                }
            }, 1);
        });
    }

    private GuiIcon getNoButton() {
        return new GuiIcon(Material.RED_STAINED_GLASS, MinecraftText.MiniMessage.parse("<red><bold>Cancel"), (p,c) -> {
            close(p);
            FloralCraftAPI.runTaskLater(new BukkitRunnable() {
                @Override
                public void run() {
                    if (onNo != null) {
                        onNo.accept(p);
                    }
                }
            }, 1);
        });
    }
}

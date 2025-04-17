package com.loficostudios.floralcraftapi.gui;

import com.loficostudios.floralcraftapi.gui.guiicon.GuiIcon;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PlayerGui extends PopOutGui implements Paginated {
    private BiConsumer<Player, Player> onClick;

    private int page;

    public PlayerGui(Consumer<Player> onClose, BiConsumer<Player, Player> onClick) {
        super(44, "Players", onClose);
        this.onClick = onClick;
        create(0);
    }

    public void create(int page) {
        clear();
        getInventory().clear();

        var players = Bukkit.getOnlinePlayers();

        var paginated = Paginated.paginate(new ArrayList<>(players), page, 27);

        var slot = 0;
        for (Player player : paginated) {
            setSlot(slot, getIcon(player));
            slot++;
        }
    }

    private GuiIcon getIcon(Player player) {
        var item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(player.getPlayerProfile());
        meta.displayName(Component.text("§f" + player.getName()));
        item.setItemMeta(meta);
        return new GuiIcon(item, "", (p, a) -> {
            onClick.accept(p, player);
        });
    }

    @Override
    public void nextPage(Player player) {
        create(page + 1);
    }

    @Override
    public void previousPage(Player player) {
        create(Math.max(0, page - 1));
    }
}

package com.loficostudios.floralcraftapi;

import com.loficostudios.floralcraftapi.character.entity.CharacterEntityManager;
import com.loficostudios.floralcraftapi.floor.FloorManager;
import com.loficostudios.floralcraftapi.gui.GuiManager;
import com.loficostudios.floralcraftapi.nms.NMSProvider;
import com.loficostudios.floralcraftapi.party.PartyManager;
import com.loficostudios.floralcraftapi.player.CombatManager;
import com.loficostudios.floralcraftapi.registry.impl.StatRegistry;
import com.loficostudios.floralcraftapi.scoreboard.ScoreboardManager;
import com.loficostudios.floralcraftapi.travel.TravelManager;
import com.loficostudios.floralcraftapi.utils.bukkit.FloralScheduler;
import com.loficostudios.floralcraftapi.world.WorldManager;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface FloralCraftAPI {

    @ApiStatus.ScheduledForRemoval
    @ApiStatus.Internal UnnamedClass unnamed = new UnnamedClass(); //this is temp for now will be removed in the future

    NMSProvider getNMS(); // todo why does this need to be in here?

    JavaPlugin getPlugin(); // todo this is not necessary

    StatRegistry getStatRegistry();

    GuiManager getGuiManager(); //todo this is not necessary

    CharacterEntityManager getCharacterEntityManager();

    ScoreboardManager getScoreboardManager();

    WorldManager getWorldManager();

    PartyManager getPartyManager();

    CombatManager getCombatManager();

    TravelManager getTravelManager();

    FloorManager getFloorManager();

    static FloralCraftAPI inst() {  //todo remove this
        return unnamed.getApi();
    }

    static NamespacedKey getNamespaceKey(@NotNull String key) { //todo remove this
        return new NamespacedKey(unnamed.getApi().getPlugin().getName().toLowerCase(Locale.ROOT), key.toLowerCase(Locale.ROOT));
    }

    static FloralCraftAPIConfig getConfig() {  //todo remove this
        return unnamed.getConfig();
    }

    static BukkitTask runTaskLater(Runnable runnable, long delay) {  //todo remove this
        return unnamed.getScheduler().runTaskLater(runnable, delay);
    }
    static BukkitTask runTaskTimer(Runnable runnable, long delay, long period) { //todo remove this
        return unnamed.getScheduler().runTaskTimer(runnable, delay, period);
    }

}

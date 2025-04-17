package com.loficostudios.floralcraftapi;

import com.loficostudios.floralcraftapi.character.entity.CharacterEntityManager;
import com.loficostudios.floralcraftapi.gui.GuiManager;
import com.loficostudios.floralcraftapi.items.pickup.ResourcePickupListener;
import com.loficostudios.floralcraftapi.mechanics.cooking.CookingPotListener;
import com.loficostudios.floralcraftapi.mechanics.dash.DashListener;
import com.loficostudios.floralcraftapi.nms.NMSProvider;
import com.loficostudios.floralcraftapi.party.PartyManager;
import com.loficostudios.floralcraftapi.player.CombatManager;
import com.loficostudios.floralcraftapi.profile.AbstractProfileManager;
import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import com.loficostudios.floralcraftapi.registry.impl.NPCRegistry;
import com.loficostudios.floralcraftapi.registry.impl.StatRegistry;
import com.loficostudios.floralcraftapi.scoreboard.ScoreboardManager;
import com.loficostudios.floralcraftapi.travel.TravelManager;
import com.loficostudios.floralcraftapi.utils.bukkit.FloralScheduler;
import com.loficostudios.floralcraftapi.world.WorldManager;
import com.loficostudios.floralcraftapi.world.WorldProvider;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;


public class FloralCraftAPI {
    private static FloralScheduler scheduler;
    private static FloralCraftAPI instance;
    private final JavaPlugin plugin;
    private final AbstractProfileManager<ProfileData> profileManager;

    //todo remove this
    private final WorldProvider worldProvider;

    private CharacterEntityManager characterEntityManager;
    private PartyManager partyManager;
    private ScoreboardManager scoreboardManager;
    private WorldManager worldManager;
    private CombatManager combatManager;
    private StatRegistry stats;
    private GuiManager guiManager;
    private TravelManager travelManager;

    private NPCRegistry npcRegistry;

    private static FloralCraftAPIConfig config;

    public FloralCraftAPI(JavaPlugin plugin, FloralCraftAPIConfig config) {
        this.plugin = plugin;
        Validate.isTrue(instance == null, "FloralCraftAPI already initialized");
        FloralCraftAPI.instance = this;
        FloralCraftAPI.config = config;

        FloralCraftAPI.scheduler = new FloralScheduler(plugin);

        this.worldProvider = config.getWorldProvider();

        this.profileManager = config.getProfileManager();
    }

    public static NMSProvider getNMS() {
        return config.getNMSProvider();
    }

    public static FloralCraftAPI inst() {
        return FloralCraftAPI.instance;
    }

    public static FloralCraftAPIConfig getConfig() {
        return config;
    }

    public void onEnable() {
        this.stats = new StatRegistry(config.stats());

        this.profileManager.initialize(profiles -> { });

        this.partyManager = new PartyManager();

        this.worldManager = new WorldManager(this.plugin, this.worldProvider);

        this.scoreboardManager = new ScoreboardManager();

        this.npcRegistry = new NPCRegistry(); //todo remove this

        this.guiManager = new GuiManager();

        this.combatManager = new CombatManager();

        this.travelManager = new TravelManager();

        this.characterEntityManager = new CharacterEntityManager();

        Arrays.asList(
                new ResourcePickupListener(),
                new DashListener(),
                new CookingPotListener(),
                this.combatManager,
                this.guiManager,
                this.worldManager
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this.plugin));
    }

    public void onDisable() {
    }

    @Deprecated
    public NPCRegistry getNpcRegistry() {
        return npcRegistry;
    }

    public AbstractProfileManager<ProfileData> getProfileManager() {
        return profileManager;
    }

    public StatRegistry getStatRegistry() {
        return stats;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public CharacterEntityManager getCharacterEntityManager() {
        return characterEntityManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }

    public CombatManager getCombatManager() {
        return combatManager;
    }

    public TravelManager getTravelManager() {
        return travelManager;
    }

    public static NamespacedKey getNamespaceKey(@NotNull String key) {
        return new NamespacedKey(instance.plugin.getName().toLowerCase(Locale.ROOT), key.toLowerCase(Locale.ROOT));
    }

    public static BukkitTask runTask(Runnable runnable) {
        return scheduler.runTask(runnable);
    }

    public static BukkitTask runTaskAsynchronously(Runnable runnable) {
        return scheduler.runTaskAsynchronously(runnable);
    }

    public static BukkitTask runTaskLater(Runnable runnable, long delay) {
        return scheduler.runTaskLater(runnable, delay);
    }

    public static BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        return scheduler.runTaskLaterAsynchronously(runnable, delay);
    }

    public static BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        return scheduler.runTaskTimer(runnable, delay, period);
    }

    public static BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        return scheduler.runTaskTimerAsynchronously(runnable, delay, period);
    }
}

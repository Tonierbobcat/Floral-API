package com.loficostudios.floralcraftapi.scoreboard;

import com.loficostudios.floralcraftapi.utils.bukkit.ColorUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class FloralScoreboard {
    private final @Nullable List<String> lines;
    private final String title;
    private final String id;

    @Deprecated
    public FloralScoreboard(String id, String title, String... lines) {
        this.title = title;
        this.id = id;
        this.lines = Arrays.stream(lines).toList();
        this.linesFunction = null;
    }

    public FloralScoreboard(String id, String title, @NotNull List<String> lines) {
        this.lines = lines;
        this.id = id;
        this.title = title;
        this.linesFunction = null;
    }

    private final @Nullable Function<Player, List<String>> linesFunction;

    public FloralScoreboard(String id, String title, @NotNull Function<Player, List<String>> lines) {
        this.title = title;
        this.id = id;
        this.lines = null;
        this.linesFunction = lines;
    }

    public void create(Player player) {
        var sb = bukkit(player);
        player.setScoreboard(sb);
    }

    @SuppressWarnings("deprecation")
    public Scoreboard bukkit(Player player) {
        var sb = Bukkit.getScoreboardManager().getNewScoreboard();
        var objective = sb.registerNewObjective("floral_sb:" + id, Criteria.DUMMY, ColorUtils.parse(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        List<String> lines = getLines(player);

        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);

            var team = sb.registerNewTeam("line_" + i);
            team.addEntry(ChatColor.values()[i] + "");
            team.prefix(Component.text(line));

            objective.getScore(ChatColor.values()[i] + "").setScore(lines.size() - i);
        }

        var dummy = sb.registerNewTeam("dummy");
        dummy.prefix(Component.text(""));
        return sb;
    }

    public List<String> getLines(Player player) {
        List<String> lines = List.of();
        if (this.lines != null) {
            lines = this.lines;
        } else if (linesFunction != null) {
            try {
                lines = linesFunction.apply(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public void update(Player player) {
        var sb = player.getScoreboard();
        var objective = sb.getObjective("floral_sb:" + id);
        if (objective == null)
            return;

        List<String> lines = getLines(player);


        for (int i = 0; i < lines.size(); i++) {
            String entry = ChatColor.values()[i] + "";
            String teamName = "line_" + i;
            Team team = sb.getTeam(teamName);

            if (team == null) {
                team = sb.registerNewTeam(teamName);
                team.addEntry(entry);
            }

            b(team, lines.get(i));

            objective.getScore(entry).setScore(lines.size() - i);
        }

        for (int i = lines.size(); ; i++) {
            Team team = sb.getTeam("line_" + i);

            if (team == null)
                break;

            sb.resetScores(ChatColor.values()[i] + "");
            team.unregister();
        }
    }

    private void b(Team team, String line) {
        if (team == null)
            return;
        Component parsed;
        try {
            parsed = ColorUtils.parse(line);
        } catch (Exception ignore) {
            parsed = Component.text(line);
        }

        team.prefix(parsed);
    }

    public static String line(String text) {
        return text;
    }

    public String getId() {
        return id;
    }

}

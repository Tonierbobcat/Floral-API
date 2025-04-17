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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloralScoreboard {
    private final List<Line> lines = new ArrayList<>();
    private final String title;

    private final String id;

    @Deprecated
    public FloralScoreboard(String id, String title, String... lines) {
        this.title = title;
        this.id = id;
        this.lines.addAll(Arrays.stream(lines).map(Line::new).toList());
    }

    public FloralScoreboard(String id, String title, List<Line> lines) {
        this.lines.addAll(lines);
        this.id = id;
        this.title = title;
    }


    public void create(Player player) {
        var sb = bukkit();
        player.setScoreboard(sb);
    }

    @SuppressWarnings("deprecation")
    public Scoreboard bukkit() {
        var sb = Bukkit.getScoreboardManager().getNewScoreboard();
        var objective = sb.registerNewObjective("floral_sb:" + id, Criteria.DUMMY, ColorUtils.parse(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i = 0; i < lines.size(); i++) {
            var team = sb.registerNewTeam("line_" + i);
            team.addEntry(ChatColor.values()[i] + "");
            team.prefix(ColorUtils.parse(lines.get(i).getFirst()));

            objective.getScore(ChatColor.values()[i] + "").setScore(lines.size() - i);
        }

        var dummy = sb.registerNewTeam("dummy");
        dummy.prefix(Component.text(""));
        return sb;
    }

    public void update(Player player) {
        var sb = player.getScoreboard();
        var objective = sb.getObjective("floral_sb:" + id);
        if (objective == null)
            return;
        for (int i = 0; i < lines.size(); i++) {
            var team = sb.getTeam("line_" + i);
            if (team != null) {
                var line = lines.get(i);

                var text = line.getFirst();

                Component parsed;
                try {
                    parsed = ColorUtils.parse(PlaceholderAPI.setPlaceholders(player, text));  //todo create parser provider
                } catch (Exception ignore) {
                    continue;
                }

                team.prefix(parsed);
            }
        }
    }

    public static Line line(String... frames) {
        return new Line(frames);
    }
    public static Line line(String text) {
        return new Line(text);
    }

    public String getId() {
        return id;
    }

    public static class Line {
        private final String[] frames;
        private int currentIndex = 0;
        public Line(String text) {
            this.frames = new String[] { text };
        }

        private Line(String... frames) {
            this.frames = frames;
        }

        private String getFirst() {
            return frames[0];
        }

        public String next() {
            var text = frames[currentIndex];
            currentIndex = (currentIndex + 1) % frames.length;
            return text;
        }
    }

}

package com.loficostudios.floralcraftapi.utils.bukkit;

import com.loficostudios.floralcraftapi.utils.Debug;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.HashMap;
import java.util.Map;

public class MinecraftText {
    private static final Map<String, String> formatMap = new HashMap<>();
    static {
        formatMap.put("<black>", "§0");
        formatMap.put("<dark_blue>", "§1");
        formatMap.put("<dark_green>", "§2");
        formatMap.put("<dark_aqua>", "§3");
        formatMap.put("<dark_red>", "§4");
        formatMap.put("<dark_purple>", "§5");
        formatMap.put("<gold>", "§6");
        formatMap.put("<gray>", "§7");
        formatMap.put("<dark_gray>", "§8");
        formatMap.put("<blue>", "§9");
        formatMap.put("<green>", "§a");
        formatMap.put("<aqua>", "§b");
        formatMap.put("<red>", "§c");
        formatMap.put("<light_purple>", "§d");
        formatMap.put("<yellow>", "§e");
        formatMap.put("<white>", "§f");
        formatMap.put("<bold>", "§l");
        formatMap.put("<italic>", "§o");
        formatMap.put("<underline>", "§n");
        formatMap.put("<strikethrough>", "§m");
        formatMap.put("<obfuscated>", "§k");
        formatMap.put("<reset>", "§r");
    }

    public static String parse(String input) {
        for (Map.Entry<String, String> entry : formatMap.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue());
        }
        input = gradient(input);
        input = hex(input);
        return input;
    }

    public static String gradient(String input) {
        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < input.length()) {
            // Check if we're at the start of a gradient tag
            if (input.startsWith("<gradient:", index)) {
                int gradientStart = index + "<gradient:".length();
                int gradientEnd = input.indexOf(">", gradientStart);
                int gradientClose = input.indexOf("</gradient>", gradientEnd);

                if (gradientEnd != -1 && gradientClose != -1) {
                    // Extract the start and end colors
                    String gradientColors = input.substring(gradientStart, gradientEnd);
                    String[] colors = gradientColors.split(":");
                    String startColor = colors[0];
                    String endColor = colors[1];
                    String gradientText = input.substring(gradientEnd + 1, gradientClose);

                    // Interpolate colors and build the gradient text
                    StringBuilder gradientTextBuilder = new StringBuilder();
                    for (int i = 0; i < gradientText.length(); i++) {
                        String color = interpolateColor(startColor, endColor, i, gradientText.length());

                        var chars = color.toCharArray();
                        var builder = new StringBuilder();
                        for (char aChar : chars) {
                            builder.append("§").append(aChar);
                        }

                        gradientTextBuilder.append("§x").append(builder).append(gradientText.charAt(i));
                    }

                    // Append the processed gradient text to the result
                    result.append(gradientTextBuilder);

                    // Move the index past the closing gradient tag
                    index = gradientClose + "</gradient>".length();
                } else {
                    // If the gradient tags are malformed, just append the current character and continue
                    result.append(input.charAt(index));
                    index++;
                }
            } else {
                // If no gradient tag is found, just append the current character
                result.append(input.charAt(index));
                index++;
            }
        }

        // Return the fully processed string

        return result.toString();
    }

//    private static String formatHexColor(String hex) {
//        return "§x" + hex.replaceAll("(..)", "§$1");
//    }

    //input <color:#ff0000>Hello</color> //expected §x§f§f§0§0§0§0Hello§r //actual <color:ff0000>Hello
    public static String hex(String input) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            char currentChar = input.charAt(i);

            // Check for opening <color:#xxxxxx> tag
            if (currentChar == '<' && i + 14 < input.length() && input.charAt(i + 1) == 'c' && input.charAt(i + 2) == 'o'
                    && input.charAt(i + 3) == 'l' && input.charAt(i + 4) == 'o' && input.charAt(i + 5) == 'r'
                    && input.charAt(i + 6) == ':' && input.charAt(i + 7) == '#') {

                // Extract the 6-character hex color code after <color:# and before the closing >
                String hexCode = input.substring(i + 8, i + 14); // Only capture the 6 digits after '#'
                result.append("§x");

                // Add each character of the hex code as §x§R§R§G§G§B§B format
                for (int j = 0; j < 6; j++) {
                    result.append("§").append(hexCode.charAt(j));
                }

                // Move past the <color:#xxxxxx> tag, including the '>' character
                i += 15; // Skip the '>' character
            } else if (currentChar == '<' && i + 8 < input.length() && input.charAt(i + 1) == '/' && input.charAt(i + 2) == 'c'
                    && input.charAt(i + 3) == 'o' && input.charAt(i + 4) == 'l' && input.charAt(i + 5) == 'o' && input.charAt(i + 6) == 'r'
                    && input.charAt(i + 7) == '>') {

                // If we encounter a closing </color> tag, append the reset color §r
                result.append("§r");

                // Move past the </color> tag
                i += 8;
            } else {
                // Copy the regular characters as they are, including special characters like '>' and '|'
                result.append(currentChar);
                i++;
            }
        }
//        Debug.log("color: " + result);
        return result.toString();
    }

    private static String interpolateColor(String startColor, String endColor, int step, int maxSteps) {
        // Remove the '#' symbol if present
        if (startColor.startsWith("#")) {
            startColor = startColor.substring(1);
        }
        if (endColor.startsWith("#")) {
            endColor = endColor.substring(1);
        }

        // Ensure the color strings are 6 characters long
        if (startColor.length() != 6 || endColor.length() != 6) {
            throw new IllegalArgumentException("Invalid color format. Color codes must be 6 characters long.");
        }

        // Extract RGB components of start and end colors
        int r1 = Integer.parseInt(startColor.substring(0, 2), 16);
        int g1 = Integer.parseInt(startColor.substring(2, 4), 16);
        int b1 = Integer.parseInt(startColor.substring(4, 6), 16);

        int r2 = Integer.parseInt(endColor.substring(0, 2), 16);
        int g2 = Integer.parseInt(endColor.substring(2, 4), 16);
        int b2 = Integer.parseInt(endColor.substring(4, 6), 16);

        // Interpolate between the start and end colors
        int r = r1 + (r2 - r1) * step / maxSteps;
        int g = g1 + (g2 - g1) * step / maxSteps;
        int b = b1 + (b2 - b1) * step / maxSteps;

        // Return the interpolated color as a hex string
        return String.format("%02X%02X%02X", r, g, b);
    }

    public static class MiniMessage {
        private final static net.kyori.adventure.text.minimessage.MiniMessage mm;
        public static Component parse(String input) {
            try {
                return mm.deserialize(input).decoration(TextDecoration.ITALIC, false);
            } catch (Exception e) {
                Debug.logError("Failed to deserialize text. " + e.getMessage());
                e.printStackTrace();
                return Component.text(input);
            }
        }

        static {
            mm = net.kyori.adventure.text.minimessage.MiniMessage.miniMessage();
        }
    }

    public static class Adventure {
        public static Component parse(String input) {
            return LegacyComponentSerializer.legacySection().deserialize(
                    MinecraftText.parse(input)).style(Style.empty().decoration(TextDecoration.ITALIC, false));
        }
    }
}

package dev.gether.getastronauta.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorFixer {


    static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static List<String> addColors(List<String> input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        for (int i = 0; i < input.size(); i++) {
            input.set(i, addColors(input.get(i)));
        }
        return input;
    }
    public static String addColors(String text)
    {
        for (Matcher matcher = pattern.matcher(text); matcher.find(); matcher = pattern.matcher(text)) {
            String color = text.substring(matcher.start(), matcher.end());
            text = text.replace(color, ChatColor.of(color) + "");
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}

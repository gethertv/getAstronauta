package dev.gether.getaustronauta.user;

import dev.gether.getaustronauta.GetAustronauta;
import dev.gether.getaustronauta.config.RuneConfig;
import dev.gether.getaustronauta.rune.RuneType;

import java.util.HashMap;

public class User {

    private HashMap<RuneType, Integer> levelOfRunes = new HashMap<>();

    public User(RuneConfig runeConfig) {
        // set default value for all runes
        for (RuneType runeType : runeConfig.runes.keySet()) {
            levelOfRunes.put(runeType, 0);
        }
    }

    public User(HashMap<RuneType, Integer> levelOfRunes) {
        this.levelOfRunes = levelOfRunes;
    }

    public int getActuallyLevel(RuneType runeType) {
        return levelOfRunes.get(runeType);
    }

    // upgrade 1 level rune
    public void increaseRune(RuneType runeType) {
        Integer integer = levelOfRunes.get(runeType);
        levelOfRunes.put(runeType, integer+1);
    }


    // reset runes to default value = 0
    public void resetRunes() {
        for (RuneType runeType : GetAustronauta.getInstance().getRuneConfig().runes.keySet()) {
            levelOfRunes.put(runeType, 0);
        }
    }

    public HashMap<RuneType, Integer> getLevelOfRunes() {
        return levelOfRunes;
    }
}

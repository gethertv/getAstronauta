package dev.gether.getastronauta.hook;

import dev.gether.getastronauta.rune.Rune;
import dev.gether.getastronauta.rune.RuneLevel;
import dev.gether.getastronauta.rune.RuneManager;
import dev.gether.getastronauta.rune.RuneType;
import dev.gether.getastronauta.user.User;
import dev.gether.getastronauta.user.UserManager;
import dev.gether.getclan.event.PointsChangeUserEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class ClanChangePointsListener implements Listener {

    private UserManager userManager;
    private RuneManager runeManager;


    public ClanChangePointsListener(UserManager userManager, RuneManager runeManager) {
        this.userManager = userManager;
        this.runeManager = runeManager;
    }

    @EventHandler
    public void onChangePoints(PointsChangeUserEvent event) {
        Player killer = event.getKiller();
        Optional<User> userTemp = userManager.getUser(killer.getUniqueId());
        if(userTemp.isEmpty())
            return;

        User user = userTemp.get();
        // get level perk of boost points
        int actuallyLevel = user.getActuallyLevel(RuneType.BOOST_POINTS);
        // if actually level is 0 that mean is default so ignore/cancel
        if(actuallyLevel==0) return;

        Optional<Rune> runeByType = runeManager.findRuneByType(RuneType.BOOST_POINTS);
        if(runeByType.isEmpty())
            return;

        Rune rune = runeByType.get();
        // get rune stats for this level
        Optional<RuneLevel> runeByLevel = rune.getRuneByLevel(actuallyLevel);
        if(runeByLevel.isEmpty())
            return;


        RuneLevel runeLevel = runeByLevel.get();
        // get multiply value
        double multiplyValuePoints = runeLevel.getValue()/100;

        // actually (old) points, what he should get
        int pointKiller = event.getPointKiller();
        // there is a new value what he should get
        double newPoints = pointKiller * (1+multiplyValuePoints);
        event.setPointKiller((int) newPoints);

    }
}

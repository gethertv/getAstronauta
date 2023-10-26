package dev.gether.getaustronauta.cmd;

import dev.gether.getaustronauta.GetAustronauta;
import dev.gether.getaustronauta.rune.RuneManager;
import dev.gether.getaustronauta.rune.RuneType;
import dev.gether.getaustronauta.user.UserManager;
import dev.gether.getaustronauta.utils.MessageUtil;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.entity.Player;


@Route(name = "getastronauta", aliases = "astronauta")
@Permission("getastronauta.use")
public class GetAstronautaCmd {

    private final UserManager userManager;
    private final RuneManager runeManager;

    public GetAstronautaCmd(UserManager userManager, RuneManager runeManager) {
        this.userManager = userManager;
        this.runeManager = runeManager;
    }

    @Execute()
    public void openInv(Player player) {
        userManager.openInv(player);
    }

    @Execute()
    @Permission("getastronauta.admin")
    public void giveRune(Player player, @Arg @Name("nazwa runy") RuneType runeType, @Arg @Name("ilosc") int amount) {
        // give item of rune
        runeManager.giveRune(player, runeType, amount);
    }

    @Execute(route = "reload")
    @Permission("getastronauta.admin")
    public void reloadPlugin(LiteSender liteSender) {
        GetAustronauta.getInstance().reloadPlugin(liteSender);
    }

    @Execute(route = "reset all")
    @Permission("getastronauta.admin")
    public void resetAllRunes(LiteSender liteSender, @Arg @Name("gracz") Player player) {
        userManager.resetRunes(player);
        MessageUtil.sendMessage(liteSender, "&aPomyslnie zresetowany runy dla "+player.getName());
    }
    @Execute(route = "setodlamek")
    @Permission("getastronauta.admin")
    public void setNewCoinItem(Player player) {
        runeManager.setNewCoinItem(player);
    }

    @Execute(route = "odlamek")
    @Permission("getastronauta.admin")
    public void giveCoin(LiteSender liteSender, @Arg @Name("gracz") Player target, @Arg @Name("ilosc") int amount) {
        userManager.giveCoin(liteSender, target, amount);
    }
    @Execute(route = "info")
    @Permission("getastronauta.admin")
    public void debugUser(LiteSender liteSender, @Arg @Name("gracz") Player player) {
        // get all level runes
        userManager.debugPlayer(liteSender, player);
    }

}

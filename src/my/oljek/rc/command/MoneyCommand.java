package my.oljek.rc.command;

import com.oljek.spigot.command.custom.Command;
import com.oljek.spigot.command.custom.ConsoleExecute;
import com.oljek.spigot.command.custom.PlayerExecute;
import my.oljek.rc.RealcraftPlugin;
import my.oljek.rc.manager.MysqlResult;
import my.oljek.rc.manager.Result;
import my.oljek.rc.manager.SQLiteConnectionManager;
import my.oljek.rc.util.MysqlUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@Command("money")
public class MoneyCommand {

    @PlayerExecute
    public void onPlayer(Player p, String[] args) {
        SQLiteConnectionManager connectionManager = RealcraftPlugin.getInstance().getSQLiteConnectionManager();

        if (args.length == 0) {
            int money = getMoney(p.getUniqueId(), connectionManager);

            p.sendMessage("У вас: " + money + " денег");
        } else {
            int moneyAdd = Integer.parseInt(args[0]);

            connectionManager.update("money", false, MysqlUtil.toCollection(new Object[] {"money", getMoney(p.getUniqueId(), connectionManager) + moneyAdd}), new Object[] {"UUID", p.getUniqueId()});
            p.sendMessage("успешно");
        }
    }

    private int getMoney(UUID uuid, SQLiteConnectionManager connectionManager) {
        MysqlResult result = connectionManager.selectResult("money", 1, new Object[]{"UUID", uuid});

        if (!result.hasNext()) {
            return 0;
        }

        Result resultNext = result.getNext();
        return resultNext.getInt("money");
    }

    @ConsoleExecute
    public void onConsole(CommandSender sender, String[] args) {

    }

}

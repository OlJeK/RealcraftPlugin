package my.oljek.rc.object;

import org.bukkit.Bukkit;

import java.util.LinkedList;
import java.util.Queue;

public class QueryQueue {

    private Queue<String> commands;

    public QueryQueue() {
        this.commands = new LinkedList<>();
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public synchronized void run() {
        for (int i = 0; i < commands.size(); i++) {
            String command = commands.poll();

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

}

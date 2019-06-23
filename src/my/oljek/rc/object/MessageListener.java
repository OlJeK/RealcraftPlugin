package my.oljek.rc.object;

import my.oljek.rc.RealcraftPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageListener implements Runnable {

    private static ServerSocket serverSocket;
    private static Socket socket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(3365);

            while (true) {
                if (serverSocket.isClosed())
                    return;

                socket = serverSocket.accept();

                System.out.println("test");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    RealcraftPlugin.getInstance().getQueryQueue().addCommand(line);
                }

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stop() {
        try {
            if (!socket.isClosed())
                socket.close();

            if (!serverSocket.isClosed())
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package dev.omochi.terminal;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CommandExec implements Callable<List<String>> {
    private String command;
    private CommandSender sender;

    public CommandExec(String command, CommandSender sender) {
        this.command = command;
        this.sender = sender;
    }

    @Override
    public List<String> call() throws Exception {
        List<String> result = new ArrayList<String>();
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor(Terminal.TimeOut, TimeUnit.SECONDS);
        if (process.isAlive()) {
            process.destroy();
            result.add("'" + command + "'の処理はタイムアウトしました。");
        } else {
            try (InputStreamReader is = new InputStreamReader(process.getInputStream(), Terminal.charset);
                 BufferedReader br = new BufferedReader(is)) {

                String result_single;
                while ((result_single = br.readLine()) != null) {
                    result.add(result_single);
                }
            }
            try (InputStreamReader is = new InputStreamReader(process.getErrorStream(), Terminal.charset);
                 BufferedReader br = new BufferedReader(is)) {

                String result_single;
                while ((result_single = br.readLine()) != null) {
                    result.add(ChatColor.RED + result_single);
                }
            }
            if (result.isEmpty()) {
                result.add("返り値がありません。");
            }
        }
        return result;
    }
}

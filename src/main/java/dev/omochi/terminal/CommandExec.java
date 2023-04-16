package dev.omochi.terminal;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CommandExec implements Callable<List<String>> {
    private String command;
    private CommandSender sender;
    private String OptimizationCommand;

    public CommandExec(String command, CommandSender sender) {
        this.command = command;
        this.sender = sender;
    }

    @Override
    public List<String> call() throws Exception {
        List<String> result = new ArrayList<String>();
        Process process=null;
        if(Terminal.isOptimizationCommand) {
            if (Terminal.platformUtils.isLinux()) {
                String filePath = Terminal.plugin.getDataFolder() + File.separator + "temp.sh";
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                file.setExecutable(true);
                file.setReadable(true);
                file.setWritable(true);


                try (FileWriter filewriter = new FileWriter(file);) {
                    filewriter.write(command);
                }
                OptimizationCommand = filePath;

            } else if (Terminal.platformUtils.isWindows()) {
                OptimizationCommand = "cmd /c " + command;
            } else {
                OptimizationCommand = command;
            }
        }else{
            OptimizationCommand = command;
        }
        process = Runtime.getRuntime().exec(OptimizationCommand);

        process.waitFor(Terminal.TimeOut, TimeUnit.SECONDS);
        if (process.isAlive()) {
            process.destroy();
            result.add("'" + OptimizationCommand + "'の処理はタイムアウトしました。");
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

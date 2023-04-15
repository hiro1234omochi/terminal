package dev.omochi.terminal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CommandCatch implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isAllow=false;
        if(sender instanceof Player){
            if(Terminal.isUseWhiteList){
                Player player = (Player) sender;
                for (String uuid : Terminal.WhiteList) {
                    Bukkit.getLogger().info(uuid);
                    if (player.getUniqueId().toString().equals(uuid)) {
                        isAllow = true;
                        break;
                    }
                }
            }else {
                isAllow=true;
            }
        }else{
            if(sender instanceof ConsoleCommandSender) {
                isAllow = Terminal.isAllowConsole;
            }
        }
        if(!isAllow){
            sender.sendMessage(ChatColor.RED+"config.ymlによってあなたは許可されていません。");
            return true;
        }
        if(args.length==0){
            sender.sendMessage(ChatColor.RED+"実行するコマンドを入力してください！");
            return true;
        }
        String command_send=String.join(" ",args);
        Long timestamp = System.currentTimeMillis() / 1000;

        CommandExec commandExec=new CommandExec(command_send,sender);
        ExecutorService ex = Executors.newSingleThreadExecutor();
        Future<List<String>> futureResult = ex.submit(commandExec);
        FutureAndMoreContain futureAndMoreContain=new FutureAndMoreContain(futureResult,sender,command_send,timestamp);
        Terminal.FutureAndMoreContainList.add(futureAndMoreContain);
        return true;
    }
}

package dev.omochi.terminal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ReturnCheck extends BukkitRunnable {

    @Override
    public void run() {
        // スケジュールで実行する処理の内容をここに書きます。
        for (int i=0;i<Terminal.FutureAndMoreContainList.size();i++) {
            FutureAndMoreContain f=Terminal.FutureAndMoreContainList.get(i);
            if (f.getFuture().isDone()) {
                try {
                    for (String line : f.getFuture().get()) {
                        f.getSender().sendMessage(line);
                    }
                } catch (ExecutionException e) {
                    f.getSender().sendMessage(ChatColor.RED + e.getCause().getMessage());
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                Terminal.FutureAndMoreContainList.remove(f);
            }
        }

    }
}

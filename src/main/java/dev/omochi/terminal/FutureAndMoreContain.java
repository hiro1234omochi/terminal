package dev.omochi.terminal;

import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.concurrent.Future;

public class FutureAndMoreContain {
    private final Future<List<String>> future;
    private final CommandSender sender;
    private final String command;
    private final long UNIXTime;
    public FutureAndMoreContain(Future<List<String>> future, CommandSender sender,String command,long UNIXTime){
        this.command=command;
        this.future=future;
        this.sender=sender;
        this.UNIXTime = UNIXTime;
    }

    public Future<List<String>> getFuture() {
        return future;
    }


    public CommandSender getSender() {
        return sender;
    }


    public String getCommand() {
        return command;
    }
}

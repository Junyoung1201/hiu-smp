package me.saehyeon.hiusmp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> playerNameList = Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();;

        if(label.equals("hiu-item")) {
            if(sender.isOp() || sender.equals(Bukkit.getConsoleSender())) {
                if(args.length == 1) {
                    return Arrays.asList("inv-save-paper");
                }
            }
        }

        if(label.equals("송금")) {
            if(args.length == 1) {
                return playerNameList;
            }
        }

        if(label.equals("돈")) {
            if(sender.isOp() || sender.equals(Bukkit.getConsoleSender())) {
                if(args.length == 1) {
                    return Arrays.asList("설정","차감","증가","확인");
                }

                if(args.length == 2) {
                    return playerNameList;
                }
            }
        }
        return List.of();
    }
}

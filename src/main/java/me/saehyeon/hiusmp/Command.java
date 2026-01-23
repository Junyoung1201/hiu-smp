package me.saehyeon.hiusmp;

import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.shop.BlockShop;
import me.saehyeon.hiusmp.shop.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(label.equals("상점")) {
            ShopManager.openSelectScreen((Player) sender);
        }

        else if(label.equals("돈")) {
            if(!sender.isOp() && !sender.equals(Bukkit.getConsoleSender())) {
                sender.sendMessage("당신의 현재 소지금은 §6§l"+Economy.getMoney((Player) sender)+" 히유§f입니다.");
                return false;
            }

            if(args.length == 0) {
                sender.sendMessage("당신의 현재 소지금은 §6§l"+Economy.getMoney((Player) sender)+" 히유§f입니다.");
                return false;
            }

            try {
                Player target = Bukkit.getPlayer(args[1]);

                if(target == null) {
                    sender.sendMessage("§c"+args[1]+"(이)라는 플레이어를 찾을 수 없음.");
                    return false;
                }

                if(args[0].equals("설정")) {

                    int value = Integer.parseInt(args[2]);

                    Economy.setMoney(target, value);
                    sender.sendMessage("§7"+args[1]+"§f의 돈을 §7"+value+" 히유§f로 설정함. (현재 소지금: "+Economy.getMoney(target)+" 히유)");
                }

                else if(args[0].equals("차감")) {

                    int value = Integer.parseInt(args[2]);

                    Economy.addMoney(target, -value);
                    sender.sendMessage("§7"+args[1]+"§f의 돈에서 §7"+value+" 히유§f를 차감함. (현재 소지금: "+Economy.getMoney(target)+" 히유)");
                }

                else if(args[0].equals("증가")) {
                    int value = Integer.parseInt(args[2]);
                    Economy.addMoney(target, value);
                    sender.sendMessage("§7"+args[1]+"§f의 돈에서 §7"+value+" 히유§f를 증가시킴. (현재 소지금: "+Economy.getMoney(target)+" 히유)");
                }

                else if(args[0].equals("확인")) {
                    sender.sendMessage("§7"+args[1]+"§f의 현재 소지금: "+Economy.getMoney(target)+" 히유)");
                }

            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("§c사용법: /돈 [설정/차감/증가/확인] [플레이어 이름] [값]");
            }
        }

        else if(label.equals("송금")) {

        }

        return false;
    }
}

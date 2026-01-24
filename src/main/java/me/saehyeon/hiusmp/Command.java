package me.saehyeon.hiusmp;

import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.features.Home;
import me.saehyeon.hiusmp.features.Teleport;
import me.saehyeon.hiusmp.shop.BlockShop;
import me.saehyeon.hiusmp.shop.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
                sender.sendMessage("당신의 현재 소지금은 §6§l"+Economy.getMoney((Player) sender)+" 히유코인§f입니다.");
                return false;
            }

            if(args.length == 0) {
                sender.sendMessage("당신의 현재 소지금은 §6§l"+Economy.getMoney((Player) sender)+" 히유코인§f입니다.");
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
                    sender.sendMessage("§7"+args[1]+"§f의 돈을 §7"+value+" 히유코인§f로 설정함. (현재 소지금: "+Economy.getMoney(target)+" 히유코인)");
                }

                else if(args[0].equals("차감")) {

                    int value = Integer.parseInt(args[2]);

                    Economy.addMoney(target, -value);
                    sender.sendMessage("§7"+args[1]+"§f의 돈에서 §7"+value+" 히유코인§f를 차감함. (현재 소지금: "+Economy.getMoney(target)+" 히유코인)");
                }

                else if(args[0].equals("증가")) {
                    int value = Integer.parseInt(args[2]);
                    Economy.addMoney(target, value);
                    sender.sendMessage("§7"+args[1]+"§f의 돈에서 §7"+value+" 히유코인§f를 증가시킴. (현재 소지금: "+Economy.getMoney(target)+" 히유코인)");
                }

                else if(args[0].equals("확인")) {
                    sender.sendMessage("§7"+args[1]+"§f의 현재 소지금: "+Economy.getMoney(target)+" 히유코인)");
                }

            } catch (Exception e) {
                sender.sendMessage("§c사용법: /돈 [설정/차감/증가/확인] [플레이어 이름] [값]");
            }
        }

        else if(label.equals("tpa-accept")) {
            try {
                Player target = Bukkit.getPlayer(args[0]);

                if(target == null) {
                    sender.sendMessage("§c해당 플레이어를 찾을 수 없습니다. 요청이 만료되었거나 해당 플레이어가 오프라인입니다.");
                } else {
                    Teleport.tpaAccept((Player) sender, target);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("§c올바르지 않은 접근입니다.");
            }
        }

        else if(label.equals("tpa-deny")) {
            try {
                Player target = Bukkit.getPlayer(args[0]);

                if(target == null) {
                    sender.sendMessage("§c해당 플레이어를 찾을 수 없습니다. 요청이 만료되었거나 해당 플레이어가 오프라인입니다.");
                } else {
                    Teleport.tpaDeny((Player) sender, target);
                }
            } catch (Exception e) {
                sender.sendMessage("§c올바르지 않은 요청입니다.");
            }
        }

        else if(label.equals("tpa") || label.equals("티피요청")) {
            try {
                Player target = Bukkit.getPlayer(args[0]);

                if(target == null) {
                    sender.sendMessage("§c"+args[0]+"(이)라는 플레이어를 찾을 수 없습니다.");
                } else {
                    Teleport.tpa((Player) sender, target);
                }

            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("§c사용법: /tpa [플레이어 이름] 또는 /티피요청 [플레이어 이름]");
            }
        }

        else if(label.equals("tpa-cancel")) {
            Teleport.teleportCancel((Player) sender);
        }

        else if(label.equals("로비")) {
            Teleport.teleportWait((Player) sender, new Location(Bukkit.getWorld("world"), 0, 0, 0,0,0));
        }

        else if(label.equals("집") || label.equals("홈")) {
            Home.openWarpGUI(((Player) sender));
        }

        else if(label.equals("집설정")) {
            Home.openSetHomeGUI(((Player) sender));
        }

        else if(label.equals("송금")) {
            try {
                if(args.length != 2) {
                    sender.sendMessage("§c사용법: /송금 [플레이어 이름] [금액]");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                int money = Integer.parseInt(args[1]);

                if(money <= 0) {
                    sender.sendMessage("§c송금할 금액은 자연수여야 합니다.");
                    return false;
                }

                if(target == null) {
                    sender.sendMessage("§c"+args[0]+"(이)라는 플레이어를 찾을 수 없습니다.");
                    return false;
                }

                if(Economy.getMoney((Player) sender) < money) {
                    sender.sendMessage("§c소지금이 부족합니다.");
                    return false;
                }

                Economy.addMoney((Player) sender, -money);
                Economy.addMoney(target, money);

                target.sendMessage("§7"+sender.getName()+"§f(으)로부터 §6"+money+" 히유코인§f이 입금되었습니다. (현재 소지금: "+Economy.getMoney(target)+" 히유코인)");
                sender.sendMessage("§7"+args[0]+"§f에게 §6"+money+" 히유코인§f을 송금했습니다. (현재 소지금: "+Economy.getMoney((Player) sender)+" 히유코인)");

            } catch (Exception e) {
                sender.sendMessage("§c사용법: /송금 [플레이어 이름] [금액]");
            }
        }

        return false;
    }
}

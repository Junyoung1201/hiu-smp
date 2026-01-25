package me.saehyeon.hiusmp;

import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.features.CustomName;
import me.saehyeon.hiusmp.features.Dice;
import me.saehyeon.hiusmp.features.Home;
import me.saehyeon.hiusmp.features.Teleport;
import me.saehyeon.hiusmp.items.InventorySavePaper;
import me.saehyeon.hiusmp.parkour.Parkour;
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

                    if(args[0].equalsIgnoreCase(sender.getName())) {
                        sender.sendMessage("§c왜 자기 자신에게 티피요청을 하려고 하나요?");
                    } else {
                        Teleport.tpa((Player) sender, target);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("§c사용법: /tpa [플레이어 이름] 또는 /티피요청 [플레이어 이름]");
            }
        }

        else if(label.equals("tpa-cancel")) {
            Teleport.teleportCancel((Player) sender);
        }

        else if(label.equals("이름")) {
            try {
                if(args.length == 0) {
                    sender.sendMessage("§c사용법: /이름 [한글 이름]");
                    return false;
                }

                CustomName.setName((Player) sender, args[0]);
                sender.sendMessage("당신의 이름이 "+args[0]+"§f(으)로 설정되었습니다.");

            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("§c사용법: /이름 [한글 이름]");
            }
        }

        else if(label.equals("로비")) {
            Teleport.teleportWait((Player) sender, new Location(Bukkit.getWorld("world"), 0, 0, 0,0,0));
        }

        else if(label.equals("집") || label.equals("홈")) {
            if(Parkour.isInParkour((Player) sender)) {
                sender.sendMessage("§c파쿠르 중에는 집 명령을 사용할 수 없습니다.");
                return false;
            }

            Home.openWarpGUI(((Player) sender));
        }

        else if(label.equals("집설정")) {
            if(Parkour.isInParkour((Player) sender)) {
                sender.sendMessage("§c파쿠르 중에는 집 설정 명령을 사용할 수 없습니다.");
                return false;
            }

            Home.openSetHomeGUI(((Player) sender));
        }

        else if(label.equals("parkour-clear")) {
            if(args[0].equals("hansol1025")) {
                sender.sendMessage("");
            } else {
                sender.sendMessage("§c올바르지 않은 접근입니다.");
            }
        }

        else if(label.equals("주사위")) {
            Dice.startBetting((Player) sender);
        }

        else if(label.equals("집터")) {
            Teleport.teleportWait((Player) sender, Constants.locations.TOWN_SPAWN);
        }

        else if(label.equals("도움말")) {
            sender.sendMessage("");
            sender.sendMessage("");
            sender.sendMessage("§6/상점: §f상점 화면을 엽니다.");
            sender.sendMessage("§6/주사위: §f주사위 도박을 시작합니다.");
            sender.sendMessage("§6/tpa 또는 /티피요청 [플레이어 이름]: §f특정 플레이어에게 텔레포트를 요청합니다.");
            sender.sendMessage("§6/돈: §f현재 소지금을 확인합니다.");
            sender.sendMessage("§6/송금 [플레이어 이름] [금액]: §f특정 플레이어에게 일정 금액을 송금합니다.");
            sender.sendMessage("§6/로비: §f로비로 이동합니다.");
            sender.sendMessage("§6/집터: §f집터로 이동합니다.");
            sender.sendMessage("§6/집 또는 /홈: §f집으로 설정된 위치로 텔레포트하기 위한 화면을 엽니다.");
            sender.sendMessage("§6/집설정: §f집 위치 설정을 위한 화면을 엽니다.");
            sender.sendMessage("§6/이름 [한글 이름]: §f한글 이름을 설정합니다.");
            sender.sendMessage("");
        }

        else if(label.equals("hiu-item")) {
            if(args[0].equals("inv-save-paper")) {
                ((Player) sender).getInventory().addItem(InventorySavePaper.getItem());
            }

            if(args[0].equals("test")) {
                sender.sendMessage("파쿠르 안에 있음?: "+Constants.locations.PARKOUR_POS1);
            }
        }

        else if(label.equals("나가기")) {
            if(Parkour.isInParkour((Player) sender)) {
                Parkour.exit((Player) sender);
            }
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

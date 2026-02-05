package me.saehyeon.hiusmp;

import me.saehyeon.hiusmp.economy.Economy;
import me.saehyeon.hiusmp.economy.Estate;
import me.saehyeon.hiusmp.features.CustomName;
import me.saehyeon.hiusmp.features.Dice;
import me.saehyeon.hiusmp.features.Home;
import me.saehyeon.hiusmp.features.Teleport;
import me.saehyeon.hiusmp.items.InstantLobbyBackPaper;
import me.saehyeon.hiusmp.items.InventorySavePaper;
import me.saehyeon.hiusmp.parkour.Parkour;
import me.saehyeon.hiusmp.shop.ShopManager;
import me.saehyeon.hiusmp.utils.DirUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static me.saehyeon.hiusmp.Constants.costs.CUSTOM_NAME_CHANGE_COST;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(label.equals("상점")) {
            ShopManager.openSelectScreen((Player) sender);
        }

        else if(label.equals("wild-clear")) {
            if(sender == Bukkit.getConsoleSender()) {

                String rootDir = Paths.get(Main.ins.getDataFolder().getAbsolutePath(),"..","..").toString();

                for(String worldName : Arrays.asList("wild","wild_nether","town_nether","wild_the_end")) {
                    Bukkit.dispatchCommand(sender, "mv remove "+worldName);
                    Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                        DirUtil.deleteDirectory(new File(rootDir, worldName));
                    },50);
                }

                Bukkit.getScheduler().runTaskLater(Main.ins, () -> {
                    Bukkit.dispatchCommand(sender, "mv create wild normal --world-type large_biomes");
                    Bukkit.dispatchCommand(sender, "mv create wild_nether nether --world-type large_biomes");
                    Bukkit.dispatchCommand(sender, "mv create town_nether nether --world-type large_biomes");
                    Bukkit.dispatchCommand(sender, "mv create wild_the_end the_end --world-type large_biomes");
                },100);
            }
        }

        else if(label.equals("땅")) {
            if(sender instanceof Player player) {
                if(!player.getWorld().getName().equals("town")) {
                    player.sendMessage("§c이 월드에서는 땅을 구매할 수 없습니다.");
                    return false;
                }

                if(args[0].equals("구매")) {
                    Estate.buyCurrentChunk(player);
                }

                else if(args[0].equals("조회")) {
                    Estate.whois(player);
                }

                else if(args[0].equals("가격")) {
                    player.sendMessage("현재 청크의 가격은 §6"+Estate.getChunkCost(player)+" 히유코인§f입니다.");
                }

                else if(args[0].equals("도움말")) {
                    player.sendMessage("");
                    player.sendMessage("§6§l[ 땅 명령어 도움말 ]");
                    player.sendMessage("");
                    player.sendMessage("§6/땅 구매 §f- 현재 서 있는 청크를 구매합니다.");
                    player.sendMessage("§6/땅 가격 §f- 현재 서 있는 청크의 구매 가격을 확인합니다.");
                    player.sendMessage("§6/땅 조회 §f- 현재 서 있는 청크의 소유자를 확인합니다.");
                    player.sendMessage("§6/땅 권한 부여 [플레이어] §f- 현재 청크에 대한 권한을 플레이어에게 부여합니다.");
                    player.sendMessage("§6/땅 권한 삭제 [플레이어] §f- 플레이어의 현재 청크 권한을 제거합니다.");
                    player.sendMessage("§6/땅 권한 목록 §f- 현재 청크의 권한 목록을 확인합니다.");
                    player.sendMessage("§6/땅 도움말 §f- 이 도움말을 표시합니다.");
                    player.sendMessage("");
                    player.sendMessage("§f※ 청크 구분은 §7F3 + G§f를 동시에 눌러 확인할 수 있습니다.");
                    player.sendMessage("§f7※ 청크 1개 구매 비용: §6" + me.saehyeon.hiusmp.Constants.costs.ESTATE_CHUNK_COST + " 히유코인");
                    player.sendMessage("");
                }

                else if(args[0].equals("권한")) {
                    try {
                        if(args[1].equals("삭제")) {
                            Player target = Bukkit.getPlayer(args[2]);

                            if(target == null) {
                                player.sendMessage("§c해당 플레이어를 찾을 수 없습니다.");
                                return false;
                            }

                            if(!Estate.hasChunk(player.getUniqueId().toString(), player.getChunk().getX(), player.getChunk().getZ())) {
                                player.sendMessage("§c청크 소유자만 권한을 삭제할 수 있습니다.");
                                return false;
                            }

                            if(!Estate.hasPermission(target, player.getChunk().getX(), player.getChunk().getZ())) {
                                player.sendMessage("§c이미 "+CustomName.getName(target)+"(은)는 해당 청크에 대한 권한을 가지고 있지 않습니다.");
                                return false;
                            }

                            Estate.removePermission(target.getUniqueId().toString(), player.getChunk().getX(), player.getChunk().getZ());
                            player.sendMessage("§7"+CustomName.getName(target)+"§f의 §7"+player.getChunk().getX()+", "+player.getChunk().getZ()+" §f청크에 대한 권한을 제거했습니다.");
                        }

                        else if(args[1].equals("부여")) {
                            Player target = Bukkit.getPlayer(args[2]);

                            if(target == null) {
                                player.sendMessage("§c해당 플레이어를 찾을 수 없습니다.");
                                return false;
                            }

                            if(!Estate.hasChunk(player.getUniqueId().toString(), player.getChunk().getX(), player.getChunk().getZ())) {
                                player.sendMessage("§c청크 소유자만 권한을 부여할 수 있습니다.");
                                return false;
                            }

                            if(Estate.hasPermission(target, player.getChunk().getX(), player.getChunk().getZ())) {
                                player.sendMessage("§c"+CustomName.getName(target)+"(은)는 이미 해당 청크에 대한 권한을 가지고 있습니다.");
                                return false;
                            }

                            Estate.addPermission(target, player.getChunk().getX(), player.getChunk().getZ());
                            player.sendMessage("§7"+CustomName.getName(target)+"§f에게 §7"+player.getChunk().getX()+", "+player.getChunk().getZ()+" §f청크에 대한 권한을 부여했습니다.");
                            target.sendMessage("§7"+CustomName.getName(player)+"§f 소유의 §7"+player.getChunk().getX()+", "+player.getChunk().getZ()+" §f청크에 대한 권한을 부여받았습니다.");
                        }

                        else if(args[1].equals("목록")) {
                            int x = player.getChunk().getX();
                            int z = player.getChunk().getZ();

                            // 현재 청크의 소유자인지 확인
                            if(!Estate.hasChunk(player.getUniqueId().toString(), x, z)) {
                                player.sendMessage("§c청크 소유자만 권한 목록을 확인할 수 있습니다.");
                                return false;
                            }

                            // 현재 청크에 대한 권한을 가진 플레이어 목록 가져오기
                            ArrayList<String> playersWithPermission = Estate.getPlayersWithPermissionForChunk(x, z);

                            if(playersWithPermission.isEmpty()) {
                                player.sendMessage("§c현재 청크에 권한을 부여받은 플레이어가 없습니다.");
                                return false;
                            }

                            player.sendMessage("");
                            player.sendMessage("§f[ §7"+x+", "+z+"§f 청크 권한 목록 ]");
                            for(String uuid : playersWithPermission) {
                                Player target = Bukkit.getPlayer(java.util.UUID.fromString(uuid));
                                String playerName;

                                if(target != null) {
                                    playerName = CustomName.getName(target);
                                } else {
                                    playerName = Bukkit.getOfflinePlayer(java.util.UUID.fromString(uuid)).getName();
                                }

                                player.sendMessage("§f- " + playerName);
                            }
                            player.sendMessage("");
                        }
                    } catch (Exception e) {
                        player.sendMessage("§c사용법: /땅 권한 [삭제/부여/목록] [플레이어 이름]");
                    }
                }
            } else {
                sender.sendMessage("§c땅 명령어는 인게임에서만 가능합니다.");
            }
        }

        else if(label.equals("칭호") || label.equals("별명") || label.equals("호칭")) {
            if(!sender.isOp() && !sender.equals(Bukkit.getConsoleSender())) {
                sender.sendMessage("§c이 명령어를 사용할 권한이 없습니다.");
                return false;
            }

            try {
                if(args.length < 2) {
                    sender.sendMessage("§c사용법: /칭호 [추가/삭제] [플레이어 이름] [칭호 내용...]");
                    return false;
                }

                String action = args[0];
                Player target = Bukkit.getPlayer(args[1]);

                if(target == null) {
                    sender.sendMessage("§c"+args[1]+"(이)라는 플레이어를 찾을 수 없습니다.");
                    return false;
                }

                // args[2]부터 끝까지를 칭호 내용으로 합치기
                StringBuilder prefixBuilder = new StringBuilder();
                for(int i = 2; i < args.length; i++) {
                    if(i > 2) {
                        prefixBuilder.append(" ");
                    }
                    prefixBuilder.append(args[i]);
                }
                String prefix = prefixBuilder.toString();

                if(prefix.isEmpty()) {
                    sender.sendMessage("§c칭호 내용을 입력해주세요.");
                    return false;
                }

                if(action.equals("추가")) {
                    CustomName.addPrefix(target, prefix);
                    sender.sendMessage("§7"+target.getName()+"§f에게 칭호 '"+prefix+"§f'(을)를 추가했습니다.");
                    target.sendMessage("칭호 '"+prefix+"§f'(이)가 추가되었습니다.");
                }
                else if(action.equals("삭제")) {
                    CustomName.removePrefix(target, prefix);
                    sender.sendMessage("§7"+target.getName()+"§f의 칭호 '"+prefix+"§f'(을)를 삭제했습니다.");
                    target.sendMessage("칭호 '"+prefix+"§f'(이)가 삭제되었습니다.");
                }
                else {
                    sender.sendMessage("§c사용법: /칭호 [추가/삭제] [플레이어 이름] [칭호 내용...]");
                }

            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("§c사용법: /칭호 [추가/삭제] [플레이어 이름] [칭호 내용...]");
            }
        }

        else if(label.equals("돈") || label.equals("ehs")) {
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
            if( (sender.isOp() || sender == Bukkit.getConsoleSender()) && args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                String name = args[1];

                if(target != null) {
                    sender.sendMessage("§7"+target.getName()+"§f의 이름을 "+name+"(으)로 변경했습니다.");
                } else {
                    sender.sendMessage("§c해당 플레이어를 찾을 수 없습니다.");
                }

                return true;
            }

            try {
                if(args.length == 0) {
                    sender.sendMessage("§c사용법: /이름 [한글 이름]");
                    return false;
                }

                if(CustomName.getName((Player) sender).equals(args[0])) {
                    sender.sendMessage("§c이미 해당 이름으로 설정되어 있습니다.");
                    return false;
                }

                if(!args[0].equals(sender.getName())) {
                    // 최초 이름 변경만 무료
                    if(!CustomName.getName((Player) sender).equals((sender).getName())) {
                        if(Economy.getMoney(((Player) sender)) < CUSTOM_NAME_CHANGE_COST) {
                            sender.sendMessage("§c소지금이 부족합니다. 이름 변경을 위해서는 "+CUSTOM_NAME_CHANGE_COST+" 히유코인이 필요합니다.");
                            return false;
                        } else {
                            Economy.addMoney((Player) sender, -CUSTOM_NAME_CHANGE_COST);
                            sender.sendMessage("이름 변경을 위해 §6"+CUSTOM_NAME_CHANGE_COST+" 히유골드§f를 소비했습니다.");
                        }
                    }
                }

                CustomName.setName((Player) sender, args[0]);
                sender.sendMessage("당신의 이름이 "+args[0]+"§f(으)로 설정되었습니다.");

            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("§c사용법: /이름 [한글 이름]");
            }
        }

        else if(label.equals("로비") || label.equals("스폰") || label.equals("spawn") || label.equals("넴주") || label.equals("마을")) {
            Teleport.teleportWait((Player) sender, Constants.locations.LOBBY);
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
            sender.sendMessage("§c히유 SMP 시즌2 부터는 /집터 명령을 사용할 수 없습니다. /로비 를 통해 집터를 이용해주세요.");
            //Teleport.teleportWait((Player) sender, Constants.locations.TOWN_SPAWN);
        }

        else if(label.equals("도움말")) {
            sender.sendMessage("");
            sender.sendMessage("");
            sender.sendMessage("§6/상점: §f상점 화면을 엽니다.");
            sender.sendMessage("§6/주사위: §f주사위 도박을 시작합니다.");
            sender.sendMessage("§6/tpa 또는 /티피요청 [플레이어 이름]: §f특정 플레이어에게 텔레포트를 요청합니다.");
            sender.sendMessage("§6/돈: §f현재 소지금을 확인합니다.");
            sender.sendMessage("§6/송금 [플레이어 이름] [금액]: §f특정 플레이어에게 일정 금액을 송금합니다.");
            sender.sendMessage("§6/마을: §f로비로 이동합니다.");
            sender.sendMessage("§6/땅 도움말: §f땅 관련 기능 도움말을 확인합니다.");
            sender.sendMessage("§6/집 또는 /홈: §f집으로 설정된 위치로 텔레포트하기 위한 화면을 엽니다.");
            sender.sendMessage("§6/집설정: §f집 위치 설정을 위한 화면을 엽니다.");
            sender.sendMessage("§6/이름 [한글 이름]: §f한글 이름을 설정합니다. 최초 이름 변경 시를 제외하고 §6"+CUSTOM_NAME_CHANGE_COST+" 히유코인§f을 소비합니다.");
            sender.sendMessage("");
        }

        else if(label.equals("hiu-item")) {
            if(args[0].equals("inv-save-paper")) {
                ((Player) sender).getInventory().addItem(InventorySavePaper.getItem());
            }

            else if(args[0].equals("lobby-back-paper")) {
                ((Player) sender).getInventory().addItem(InstantLobbyBackPaper.getItem());
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

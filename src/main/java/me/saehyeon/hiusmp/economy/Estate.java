package me.saehyeon.hiusmp.economy;

import me.saehyeon.hiusmp.Main;
import me.saehyeon.hiusmp.features.CustomName;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static me.saehyeon.hiusmp.Constants.costs.ESTATE_CHUNK_COST;

public class Estate {

    private static YamlConfiguration estateData = new YamlConfiguration();
    private static File estateDataFile = new File(Main.ins.getDataFolder(), "estate.yml");

    public static ArrayList<String> getEstate(String uuid) {
        return (ArrayList<String>) estateData.get("ownEstate."+uuid, new ArrayList<>());
    }

    public static boolean hasCurrentChunk(Player player) {
        int x = player.getChunk().getX();
        int z = player.getChunk().getZ();

        return getEstate(player.getUniqueId().toString()).stream().anyMatch(coord -> {
            String[] parts = coord.split(",");
            return Integer.parseInt(parts[0]) == x && Integer.parseInt(parts[1]) == z;
        });
    }

    public static boolean hasChunk(String uuid, int x, int z) {
        return getEstate(uuid).stream().anyMatch(coord -> {
            String[] parts = coord.split(",");
            return Integer.parseInt(parts[0]) == x && Integer.parseInt(parts[1]) == z;
        });
    }

    public static boolean hasPermissionCurrentChunk(Player player) {
        return hasPermission(player, player.getChunk().getX(), player.getChunk().getZ());
    }

    public static void sendNoPermissionMessage(Player player) {
        player.sendMessage("§c해당 토지를 조작할 권한이 없습니다. 토지에 관련된 도움말은 \"/땅 도움말\"을 입력하여 열람할 수 있습니다.");
    }

    public static boolean hasPermission(Player player, int x, int z) {
        String uuid = player.getUniqueId().toString();

        // 1. 플레이어가 청크의 소유자인지 확인
        if(hasChunk(uuid, x, z)) {
            return true;
        }

        // 2. 플레이어가 해당 청크에 대한 권한을 부여받았는지 확인
        return getPermissionChunks(uuid).stream().anyMatch(coord -> {
            String[] parts = coord.split(",");
            return Integer.parseInt(parts[0]) == x && Integer.parseInt(parts[1]) == z;
        });
    }

    public static ArrayList<String> getPermissionChunks(String uuid) {
        return (ArrayList<String>) estateData.get("permission."+uuid, new ArrayList<>());
    }

    public static ArrayList<String> getPermissionChunks(Player player) {
        return getPermissionChunks(player.getUniqueId().toString());
    }

    public static void whois(Player player) {
        int x = player.getChunk().getX();
        int z = player.getChunk().getZ();

        if(player.getWorld().getName().equals("town")) {
            String ownerUUID = getChunkOwnerUUID(x, z);
            if(ownerUUID == null) {
                player.sendMessage("§c현재 위치의 청크 소유자를 찾을 수 없습니다.");
            } else {
                Player owner = Bukkit.getPlayer(UUID.fromString(ownerUUID));
                if(owner != null) {
                    player.sendMessage("현재 있는 청크의 소유자는 §7"+ CustomName.getName(owner)+"§f입니다.");
                } else {
                    OfflinePlayer offlineOwner = Bukkit.getOfflinePlayer(UUID.fromString(ownerUUID));
                    player.sendMessage("현재 있는 청크의 소유자는 §7"+ offlineOwner.getName()+"§f입니다.");
                }
            }
        } else {
            player.sendMessage("§c현재 위치의 청크 소유자가 없습니다.");
        }
    }

    public static @Nullable String getChunkOwnerUUID(int x, int z) {
        String[] finalUUID = new String[] { null };

        estateData.getKeys(false).forEach(key -> {
            if(key.equals("ownEstate")) {
                if(estateData.getConfigurationSection("ownEstate") != null) {
                    estateData.getConfigurationSection("ownEstate").getKeys(false).forEach(uuid -> {
                        if(hasChunk(uuid, x, z)) {
                            finalUUID[0] = uuid;
                        }
                    });
                }
            }
        });

        return finalUUID[0];
    }

    public static void buyCurrentChunk(Player player) {
        int x = player.getChunk().getX();
        int z = player.getChunk().getZ();

        if(getChunkOwnerUUID(x, z) != null) {
            UUID uuid = UUID.fromString(getChunkOwnerUUID(x, z));
            String ownerName = Bukkit.getOfflinePlayer(uuid).getName();

            if(ownerName == null) {
                player.sendMessage("§c해당 청크는 이미 누군가의 소유입니다.");
                return;
            }

            if(ownerName.equals(player.getName())) {
                player.sendMessage("§c해당 청크는 이미 당신의 소유입니다.");
            } else {
                player.sendMessage("§c해당 청크는 이미 "+ownerName+"의 소유입니다.");
            }
            return;
        }

        if(Economy.getMoney(player) < ESTATE_CHUNK_COST) {
            player.sendMessage("§c청크를 구매하기 위해서는 "+ESTATE_CHUNK_COST+" 히유코인이 필요합니다. (현재 소지금: "+Economy.getMoney(player)+" 히유코인)");
            return;
        }

        Economy.addMoney(player, -ESTATE_CHUNK_COST);

        // 청크 정보를 플레이어의 estate 목록에 추가
        String playerUUID = player.getUniqueId().toString();
        ArrayList<String> estates = getEstate(playerUUID);
        estates.add(x + "," + z);
        estateData.set("ownEstate." + playerUUID, estates);

        // 파일에 저장
        save();
        player.sendMessage("§7"+x+", "+z+"§f 청크를 §6"+ESTATE_CHUNK_COST+" 히유코인§f을 지불하여 구매했습니다.");
        player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_PLACE, SoundCategory.MASTER, 1,1.5f);
    }

    public static void addPermission(Player player, int x, int z) {
        String uuid = player.getUniqueId().toString();

        // 이미 권한이 있는지 확인
        ArrayList<String> permissions = getPermissionChunks(uuid);
        String chunkCoord = x + "," + z;

        if(permissions.contains(chunkCoord)) {
            return;
        }

        // 권한 추가
        permissions.add(chunkCoord);
        estateData.set("permission." + uuid, permissions);

        // 파일에 저장
        save();
    }

    public static void removePermission(String uuid, int x, int z) {
        // 권한 목록 가져오기
        ArrayList<String> permissions = getPermissionChunks(uuid);

        // 해당 청크 권한 제거
        String chunkCoord = x + "," + z;
        boolean removed = permissions.remove(chunkCoord);

        if(!removed) {
            return;
        }

        // 업데이트된 권한 목록 저장
        estateData.set("permission." + uuid, permissions);

        // 파일에 저장
        save();
    }

    // 특정 청크에 대해 권한을 가진 플레이어 UUID 목록을 반환
    public static ArrayList<String> getPlayersWithPermissionForChunk(int x, int z) {
        ArrayList<String> result = new ArrayList<>();

        if(estateData.getConfigurationSection("permission") == null) {
            return result;
        }

        String chunkCoord = x + "," + z;

        // 모든 플레이어의 권한 목록을 순회
        estateData.getConfigurationSection("permission").getKeys(false).forEach(uuid -> {
            ArrayList<String> permissions = getPermissionChunks(uuid);
            // 해당 플레이어가 이 청크에 대한 권한을 가지고 있는지 확인
            if(permissions.contains(chunkCoord)) {
                result.add(uuid);
            }
        });

        return result;
    }

    public static void load() {
        try {
            if(!estateDataFile.exists()) {
                estateDataFile.createNewFile();
            }

            estateData.load(estateDataFile);
            Main.ins.getLogger().info("부동산 데이터 로드함.");

        } catch (Exception e) {
            Main.ins.getLogger().warning("estate.yml 로드 실패");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Main.ins.getDataFolder().mkdir();
            estateData.save(estateDataFile);

        } catch (Exception e) {
            Main.ins.getLogger().warning("estate.yml 저장 실패");
            e.printStackTrace();
        }
    }
}




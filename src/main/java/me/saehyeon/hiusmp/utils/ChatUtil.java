package me.saehyeon.hiusmp.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

public class ChatUtil {
    public static void sendClickCommandMessage(Player player, String _message, String command) {
        ComponentBuilder message = new ComponentBuilder(_message);

        BaseComponent[] msg = message.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)).create();

        player.spigot().sendMessage(msg);
    }
}

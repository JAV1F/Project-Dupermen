package com.ja.dupermen.command;

import com.ja.dupermen.command.commands.Bind;
import com.ja.dupermen.command.commands.Toggle;
import com.ja.dupermen.event.PacketEvent;
import com.ja.dupermen.util.MessageUtils;
import me.bush.eventbus.annotation.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.ArrayList;

public class CommandManager {
    public Minecraft mc = Minecraft.getMinecraft();
    ArrayList<Command> commands = new ArrayList<>();
    char prefix;

    //TODO add a command prediction system
    public CommandManager() {
        this.prefix = ';';
        commands.add(new Toggle());
        commands.add(new Bind());
    }

    public char getPrefix() {return prefix;}

    public void setPrefix(char prefix) {
        this.prefix = prefix;
    }

    @EventListener
    public void onPacketSend(PacketEvent.Send event) {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketChatMessage) {
            String[] args = ((CPacketChatMessage) packet).message.substring(1).split("\\s+");
            if (((CPacketChatMessage) packet).message.startsWith(String.valueOf(prefix))) event.setCancelled(true);
            for (Command command : commands) if (event.isCancelled() && args[0].equalsIgnoreCase(command.command) || command.shorten && args[0].equalsIgnoreCase(String.valueOf(command.command.charAt(0)))) if (args.length > command.args) {
                    command.execute(args);
                } else {
                    MessageUtils.sendSuffixMessage(mc.player, "Usage : " + command.usage);
                }
        }
    }
}

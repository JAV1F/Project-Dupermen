package com.ja.dupermen.util;

import com.ja.dupermen.Dupermen;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;

public class MessageUtils {
    public static void sendSuffixMessage(EntityPlayerSP player, String message) {
        if (player != null) player.sendMessage(new TextComponentString("\247b" + "[" + Dupermen.TITLE + "]" + " " + "\2477" + message));
    }
}

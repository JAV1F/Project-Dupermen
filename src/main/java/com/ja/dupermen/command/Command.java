package com.ja.dupermen.command;

import net.minecraft.client.Minecraft;

public class Command {
    public Minecraft mc = Minecraft.getMinecraft();
    String command;
    String usage;
    public int args;
    boolean drawn, shorten;

    public Command(String command, String usage, int args, boolean drawn, boolean shorten) {
        this.command = command;
        this.usage = usage;
        this.args = args;
        this.drawn = drawn;
        this.shorten = shorten;
    }

    public void execute(String[] args) {
    }

    public boolean nullCheck() {
        return mc.player == null;
    }

    public boolean fullNullCheck() {
        return mc.player == null || mc.world == null;
    }
}

package com.ja.dupermen.command.commands;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.command.Command;
import com.ja.dupermen.module.Module;
import com.ja.dupermen.util.MessageUtils;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super("bind", "bind <Module Name> <Key>", 2, true, true);
    }

    public void execute(String[] args) {
        for (Module module : Dupermen.moduleManager.getModules()) if (args[1].replaceAll("-", " ").equalsIgnoreCase(module.getName().trim())) {
            module.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
            MessageUtils.sendSuffixMessage(mc.player, "Binded " + module.getName() + " To " + Keyboard.getKeyName(module.getKey()));
        }
    }
}

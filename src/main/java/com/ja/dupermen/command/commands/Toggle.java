package com.ja.dupermen.command.commands;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.command.Command;
import com.ja.dupermen.module.Module;
import com.ja.dupermen.util.MessageUtils;

public class Toggle extends Command {
    public Toggle() {
        super("toggle", "toggle <Module Name>", 1, true, true);
    }

    public void execute(String[] args) {
        for (Module module : Dupermen.moduleManager.getModules()) if (args[1].replaceAll("-", " ").equalsIgnoreCase(module.getName().trim())) {
            module.toggle();
            MessageUtils.sendSuffixMessage(mc.player, "Toggled " + module.getName());
        }
    }
}

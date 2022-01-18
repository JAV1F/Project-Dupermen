package com.ja.dupermen.command.commands;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.command.Command;
import com.ja.dupermen.module.Module;
import com.ja.dupermen.setting.Setting;

public class Set extends Command {
    public Set() {
        super("set", "set <module> <setting> <value>", 3, true, false);
    }

    public void execute(String[] args) {
        for (Module module : Dupermen.moduleManager.getModules()) if (args[1].replace('-', ' ').equalsIgnoreCase(module.getName().trim())) for (Setting setting : module.getSettings()) if (args[2].equalsIgnoreCase(setting.getName())) setSetting(setting, args[3]);
    }

    public void setSetting(Setting setting, String value) {
        if (setting.isMVal()) setting.setMode(value);
        if (setting.isBVal() && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) setting.setBVal(Boolean.parseBoolean(value));
        if (setting.isDVal() || setting.isIntVal()) setting.setDVal(Double.parseDouble(value));
    }
}

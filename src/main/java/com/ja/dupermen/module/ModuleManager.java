package com.ja.dupermen.module;

import com.ja.dupermen.event.TickEvent;
import com.ja.dupermen.module.client.AutoDuper;
import com.ja.dupermen.module.client.ClickGUI;
import me.bush.eventbus.annotation.EventListener;
import me.bush.eventbus.annotation.ListenerPriority;

import java.util.ArrayList;

public class ModuleManager {
    ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new ClickGUI());
        modules.add(new AutoDuper());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    @EventListener(priority = ListenerPriority.HIGHEST)
    public void onTick(TickEvent.Pre event) {
        for (Module module : modules) {
            module.tick();
        }
    }
}

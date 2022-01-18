package com.ja.dupermen;

import com.ja.dupermen.command.CommandManager;
import com.ja.dupermen.gui.click.ClickGui;
import com.ja.dupermen.module.ModuleManager;
import me.bush.eventbus.bus.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@net.minecraftforge.fml.common.Mod(modid = Dupermen.MOD_ID, name = Dupermen.NAME, version = Dupermen.VERSION)
public class Dupermen {
    public static Minecraft mc;
    public static final String NAME = "Dupermen Client";
    public static final String MOD_ID = "dupermen";
    public static final String VERSION = "1.0";
    public static final String TITLE = NAME + " " + VERSION;

    public static EventBus EVENT_BUS;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static ClickGui clickGui;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        mc = Minecraft.getMinecraft();
        EVENT_BUS = new EventBus();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        clickGui = new ClickGui();
        EVENT_BUS.subscribe(moduleManager);
        EVENT_BUS.subscribe(commandManager);
    }
}
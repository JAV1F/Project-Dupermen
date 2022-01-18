package com.ja.dupermen.module;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.setting.Setting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Module {
    public static Minecraft mc = Dupermen.mc;
    String name, description;
    boolean toggled, keyDown;
    Category category;
    ArrayList<Setting> settings = new ArrayList<>();
    int key;


    public Module(String name, String description, Category category, int key) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.key = key;
        settings.add(new Setting("Drawn", true));
    }

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        key = Keyboard.KEY_NONE;
        settings.add(new Setting("Drawn", true));
    }

    public void addSetting(Setting setting) {
        this.settings.add(setting);
    }

    public void enable() {
        Dupermen.EVENT_BUS.subscribe(this);
        onEnable();
    }

    public void disable() {
        Dupermen.EVENT_BUS.unsubscribe(this);
        onDisable();
    }

    public void onEnable() {}

    public void onDisable() {}


    public void tick() {
        if (key != -1) {
            boolean isDown = Keyboard.isKeyDown(key);
            if (isDown && !keyDown && mc.currentScreen == null) toggle();
            keyDown = isDown;
            if (isToggled()) onUpdate();
        }
        onTick();
    }

    public void onTick() {

    }

    public void onUpdate() {

    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) enable(); else disable();
    }

    public boolean isToggled() {
        return toggled;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public boolean fullNullCheck() {
        return mc.player == null || mc.world == null;
    }
}

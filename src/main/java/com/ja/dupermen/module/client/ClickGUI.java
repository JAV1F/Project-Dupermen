package com.ja.dupermen.module.client;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.module.Category;
import com.ja.dupermen.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", "Graphical User Interface for Configuring the client", Category.DUPERMEN, Keyboard.KEY_O);
    }

    public void onEnable() {
        mc.displayGuiScreen(Dupermen.clickGui);
        toggle();
    }
}

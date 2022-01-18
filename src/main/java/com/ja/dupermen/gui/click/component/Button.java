package com.ja.dupermen.gui.click.component;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.module.Module;
import com.ja.dupermen.setting.Setting;
import com.ja.dupermen.util.ColourUtils;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class Button {
    ArrayList<ModeButton> modeButtons;
    ArrayList<BooleanButton> booleanButtons;
    ArrayList<Slider> sliders;
    Frame frame;
    Module module;
    boolean open;
    int width, height, x, y;
    KeybindButton keybindButton;

    public Button(Module module, Frame frame, int x, int y, int width, int height) {
        modeButtons = new ArrayList<>();
        booleanButtons = new ArrayList<>();
        sliders = new ArrayList<>();
        this.frame = frame;
        this.module = module;
        this.open = false;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        for (Setting setting : module.getSettings()) {
            if (setting.isMVal())
                modeButtons.add(new ModeButton(this, setting, this.x + 1, this.y, this.width - 2, this.height));
            if (setting.isBVal())
                booleanButtons.add(new BooleanButton(this, setting, this.x + 1, this.y, this.width - 2, this.height));
            if (setting.isDVal() || setting.isIntVal())
                sliders.add(new Slider(setting, this, this.x + 1, this.y, this.width - 2, this.height));
        }
        keybindButton = new KeybindButton(this, this.x + 1, this.y, this.width - 2, this.height);
    }

    public void render(int mouseX, int mouseY, int x, int y) {
        if (frame.open) {
            this.x = x;
            this.y = y;
            if (module.isToggled()) {
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + this.height + Dupermen.clickGui.getYOffset() - 1, Dupermen.clickGui.getEnabledButtonColour());
                Dupermen.clickGui.drawString(Dupermen.clickGui.mc.fontRenderer, module.getName(), this.x + 3, (this.y + this.height / 2) + Dupermen.clickGui.getYOffset() - Dupermen.clickGui.getTextYOffset(), Dupermen.clickGui.getEnabledButtonTextColour());
            } else {
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + height + Dupermen.clickGui.getYOffset() - 1, Dupermen.clickGui.getButtonColour());
                Dupermen.clickGui.drawString(Dupermen.clickGui.mc.fontRenderer, module.getName(), this.x + 3, (y + this.height / 2) + Dupermen.clickGui.getYOffset() - Dupermen.clickGui.getTextYOffset(), Dupermen.clickGui.getButtonTextColour());
            }

            if (isOver(mouseX, mouseY)) {
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + this.height + Dupermen.clickGui.getYOffset() - 1, ColourUtils.ColorUtils.toRGBA(255, 255, 255, 50));
            } int offset = y + height;

            for (ModeButton modeButton : modeButtons) {
                modeButton.render(mouseX, mouseY, this.x + 1, offset);
                offset += height;
            }

            for (BooleanButton booleanButton : booleanButtons) {
                booleanButton.render(mouseX, mouseY, this.x + 1, offset);
                offset += height;
            }

            for (Slider slider : sliders) {
                slider.render(mouseX, mouseY, this.x + 1, offset);
                offset += height;
            }

            keybindButton.render(mouseX, mouseY, this.x + 1, offset);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (frame.open) {
            if (mouseButton == 0) {
                if (isOver(mouseX, mouseY)) module.toggle();
            } else if (mouseButton == 1 && isOver(mouseX, mouseY)) open = !open;

            for (ModeButton modeButton : modeButtons) modeButton.mouseClicked(mouseX, mouseY, mouseButton);
            for (BooleanButton booleanButton : booleanButtons) booleanButton.mouseClicked(mouseX, mouseY, mouseButton);
            for (Slider slider : sliders) slider.mouseClicked(mouseX, mouseY, mouseButton);
        }

        keybindButton.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int state) {
        for (Slider slider : sliders) slider.mouseReleased(state);
    }

    public void keyTyped(int keyCode) {
        keybindButton.keyTyped(keyCode);
    }

    public boolean isOver(int x, int y) {
        return x >= this.x && y > this.y + Dupermen.clickGui.getYOffset() && x <= this.x + this.width && y < this.y + this.height + Dupermen.clickGui.getYOffset();
    }
}

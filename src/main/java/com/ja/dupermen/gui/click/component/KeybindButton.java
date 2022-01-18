package com.ja.dupermen.gui.click.component;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.util.ColourUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class KeybindButton {
    Button button;
    int width, height, x, y;

    public KeybindButton(Button button, int x, int y, int width, int height) {
        this.button = button;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void render(int mouseX, int mouseY, int x, int y) {
        this.x = x;
        this.y = y;
        if (button.open) {
            if (Dupermen.clickGui.getSelectedKeybindButton() == this) {
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + this.height + Dupermen.clickGui.getYOffset() - 1, Dupermen.clickGui.getEnabledButtonColour());
                Dupermen.clickGui.drawString(Dupermen.clickGui.mc.fontRenderer, "Key - " + Keyboard.getKeyName(button.module.getKey()), x + 3, (y + height / 2) + Dupermen.clickGui.getYOffset() - Dupermen.clickGui.getTextYOffset(), Dupermen.clickGui.getEnabledButtonTextColour());
            } else {
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + this.height + Dupermen.clickGui.getYOffset() - 1, Dupermen.clickGui.getButtonColour());
                Dupermen.clickGui.drawString(Dupermen.clickGui.mc.fontRenderer, "Key - " + Keyboard.getKeyName(button.module.getKey()), x + 3, (y + height / 2) + Dupermen.clickGui.getYOffset() - Dupermen.clickGui.getTextYOffset(), Dupermen.clickGui.getButtonTextColour());
            }

            if (isOver(mouseX, mouseY))
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + this.height + Dupermen.clickGui.getYOffset() - 1, ColourUtils.ColorUtils.toRGBA(255, 255, 255, 50));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (button.open) {
            if (mouseButton == 0 && isOver(mouseX, mouseY)) {
                if (Dupermen.clickGui.getSelectedKeybindButton() == this) {
                    Dupermen.clickGui.setSelectedKeybindButton(null);
                } else {
                    Dupermen.clickGui.setSelectedKeybindButton(this);
                }
            }
        }
    }

    public void keyTyped(int keyCode) {
        if (Dupermen.clickGui.getSelectedKeybindButton() == this) {
            if (keyCode == Keyboard.KEY_DELETE || keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_ESCAPE) {
                this.button.module.setKey(Keyboard.KEY_NONE);
            } else {
                this.button.module.setKey(keyCode);
            }
        }
    }

    public boolean isOver(int x, int y) {
        return x >= this.x && y > this.y + Dupermen.clickGui.getYOffset() && x <= this.x + this.width && y <= this.y + this.height + Dupermen.clickGui.getYOffset();
    }
}

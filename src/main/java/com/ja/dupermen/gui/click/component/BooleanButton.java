package com.ja.dupermen.gui.click.component;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.setting.Setting;
import com.ja.dupermen.util.ColourUtils;
import net.minecraft.client.gui.Gui;

public class BooleanButton {
    Button button;
    Setting setting;
    int width, height, x, y;

    public BooleanButton(Button button, Setting setting, int x, int y, int width, int height) {
        this.button = button;
        this.setting = setting;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void render(int mouseX, int mouseY, int x, int y) {
        if (button.open) {
            this.x = x;
            this.y = y;
            if (setting.getBVal()) {
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + this.height + Dupermen.clickGui.getYOffset() - 1, Dupermen.clickGui.getEnabledButtonColour());
                Dupermen.clickGui.drawString(Dupermen.clickGui.mc.fontRenderer, setting.getName(), this.x + 3, (this.y + this.height / 2) + Dupermen.clickGui.getYOffset() - Dupermen.clickGui.getTextYOffset(), Dupermen.clickGui.getEnabledButtonTextColour());
            } else {
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + this.height + Dupermen.clickGui.getYOffset() - 1, Dupermen.clickGui.getButtonColour());
                Dupermen.clickGui.drawString(Dupermen.clickGui.mc.fontRenderer, setting.getName(), this.x + 3, (this.y + this.height / 2) + Dupermen.clickGui.getYOffset() - Dupermen.clickGui.getTextYOffset(), Dupermen.clickGui.getButtonTextColour());
            }

            if (isOver(mouseX, mouseY))
                Gui.drawRect(this.x, this.y + Dupermen.clickGui.getYOffset(), this.x + this.width, this.y + this.height + Dupermen.clickGui.getYOffset() - 1, ColourUtils.ColorUtils.toRGBA(255, 255, 255, 50));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (button.open && mouseButton == 0 && isOver(mouseX, mouseY) && button.open) setting.setBVal(!setting.getBVal());
    }

    public boolean isOver(int x, int y) {
        return x >= this.x && y > this.y + Dupermen.clickGui.getYOffset() && x <= this.x + this.width && y <= this.y + this.height + Dupermen.clickGui.getYOffset();
    }
}

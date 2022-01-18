package com.ja.dupermen.gui.click;

import com.ja.dupermen.gui.click.component.Frame;
import com.ja.dupermen.gui.click.component.KeybindButton;
import com.ja.dupermen.module.Category;
import com.ja.dupermen.util.ColourUtils;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public class ClickGui extends GuiScreen {
    ArrayList<Frame> frames;
    KeybindButton selectedKeybindButton;
    int frameColour, buttonColour, enabledButtonColour, frameTextColour, buttonTextColour, enabledButtonTextColour, bg, textYOffset, yOffset, frameWidth = 115, frameHeight = 20, x, y, yVel = 0;

    public ClickGui() {
        textYOffset = 5;
        yOffset = 0;
        frameColour = ColourUtils.ColorUtils.toRGBA(100, 50, 255, 200);
        frameTextColour = ColourUtils.ColorUtils.toRGBA(255, 255, 255, 255);
        enabledButtonColour = ColourUtils.ColorUtils.toRGBA(100, 100, 255, 200);
        enabledButtonTextColour = ColourUtils.ColorUtils.toRGBA(255, 255, 255, 255);
        buttonTextColour = ColourUtils.ColorUtils.toRGBA(200, 200, 200, 255);
        bg = ColourUtils.ColorUtils.toRGBA(155, 155, 155, 155);
        frames = new ArrayList<>();
        x = 3;
        y = 5;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, x, y, this.frameWidth, this.frameHeight));
            x += frameWidth + 3;
        }
    }

    public int getTextYOffset() {
        return textYOffset;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            yOffset -= 10;
        } else if (dWheel > 0) {
            yOffset += 10;
        } else
        if (yOffset > 0) this.yOffset = 0;


        for (int i = frames.size() - 1; i > -1; i--) frames.get(i).render(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
            if (frame.isOver(mouseX, mouseY)) {
                frame.select(mouseX, mouseY, mouseButton);
                frames.remove(frame);
                frames.add(0, frame);
                break;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame frame : frames) frame.mouseReleased(state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) mc.displayGuiScreen(null);
        for (Frame frame : frames) frame.keyTyped(keyCode);

        selectedKeybindButton = null;
    }

    public KeybindButton getSelectedKeybindButton() {
        return selectedKeybindButton;
    }

    public void setSelectedKeybindButton(KeybindButton selectedKeybindButton) {
        this.selectedKeybindButton = selectedKeybindButton;
    }

    public int getButtonColour() {
        return buttonColour;
    }

    public int getEnabledButtonColour() {
        return enabledButtonColour;
    }

    public int getButtonTextColour() {
        return buttonTextColour;
    }

    public int getFrameColour() {
        return frameColour;
    }

    public int getFrameTextColour() {
        return frameTextColour;
    }

    public void setButtonColour(int buttonColour) {
        this.buttonColour = buttonColour;
    }

    public void setEnabledButtonColour(int enabledButtonColour) {
        this.enabledButtonColour = enabledButtonColour;
    }

    public void setButtonTextColour(int buttonTextColour) {
        this.buttonTextColour = buttonTextColour;
    }

    public void setFrameColour(int frameColour) {
        this.frameColour = frameColour;
    }

    public void setFrameTextColour(int frameTextColour) {
        this.frameTextColour = frameTextColour;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void setYOffset(int yOffset) { this.yOffset = yOffset; }

    public int getEnabledButtonTextColour() {
        return enabledButtonTextColour;
    }

    public void setEnabledButtonTextColour(int enabledButtonTextColour) {
        this.enabledButtonTextColour = enabledButtonTextColour;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getBg() {
        return bg;
    }
}

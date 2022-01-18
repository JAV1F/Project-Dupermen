package com.ja.dupermen.setting;
import java.util.Objects;

public class Setting {
    String name;
    boolean integer;
    boolean bVal;
    double dVal;
    double min;
    double max;
    String[] modes;
    int mode;
    String type;

    public Setting(String name, boolean bVal) {
        this.name = name;
        this.bVal = bVal;
        type = "Boolean";
    }

    public Setting(String name, double dVal, double min, double max, boolean integer) {
        this.name = name;
        this.dVal = dVal;
        this.min = min;
        this.max = max;
        this.integer = integer;
        type = "Double";
    }

    public Setting(String name, String[] modes) {
        this.name = name;
        this.modes = modes;
        this.mode = 0;
        this.type = "Modes";
    }

    public String getName() {
        return name;
    }

    public void switchMode(boolean forward) {
        if (forward) {
            if (mode < modes.length - 1) {mode++;} else {mode = 0;}
        } else {
            if (mode > 0) {mode--;} else {mode = modes.length - 1;}
        }
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public boolean getBVal() {
        return bVal;
    }

    public double getDVal() {
        return dVal;
    }

    public int getMVal() {
        return mode;
    }

    public String getMode() {
        return modes[mode];
    }

    public void setMVal(int mode) {
        this.mode = mode;
    }

    public void setupMVal(int mode) {
        this.mode = mode;
    }

    public void setBVal(boolean bVal) {
        this.bVal = bVal;
    }

    public void setupBVal(boolean bVal) {
        this.bVal = bVal;
    }

    public void setDVal(double dVal) {
        this.dVal = dVal;
    }

    public void setMode(String mode) {
        for (int i = 0; i < modes.length; i++) if (modes[i].equalsIgnoreCase(mode)) this.mode = i;
    }

    public void setupDVal(double dVal) {
        this.dVal = dVal;
    }

    public boolean isBVal() {
        return Objects.equals(type, "Boolean");
    }

    public boolean isDVal() {
        return Objects.equals(type, "Double") && !integer;
    }

    public boolean isIntVal() {
        return Objects.equals(type, "Double") && integer;
    }

    public boolean isMVal() {
        return modes != null;
    }
}

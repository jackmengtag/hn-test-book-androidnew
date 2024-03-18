package com.wwb.laobiao.usmsg.model;

import java.io.Serializable;

public class XiaoxiLvModel implements Serializable {
    public static final String SER_KEY ="ser_str" ;
    public static final String CHAR_SEL ="char_sel";
    private int icon=0;
    private String name="";
    private String instructions="";

    public int getIcon() {
        return icon;
    }
    public XiaoxiLvModel()
    {
        icon=0;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}

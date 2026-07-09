package com.mygame.game.models.charms;

public class Charm {
    public Charm(Type type) {

    }
    public enum Type{SOUL_CATCHER,DASHMASTER,UNBREAKABLE_STRENGTH,QUICK_SLASH,QUICK_FOCUS,HEAVY_BLOW}
    Type type;
    String name;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

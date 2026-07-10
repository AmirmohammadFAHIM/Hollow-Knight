package com.mygame.game.models.charms;

public class Charm {
    public Charm(Type type) {
        this.type = type;
        name = type.toString();
    }

    public enum Type {
        SOUL_CATCHER, DASHMASTER, UNBREAKABLE_STRENGTH, QUICK_SLASH, QUICK_FOCUS, HEAVY_BLOW;

        @Override
        public String toString() {
            switch (this) {
                case SOUL_CATCHER:
                    return "Soul Catcher";
                case DASHMASTER:
                    return "Dashmaster";
                case UNBREAKABLE_STRENGTH:
                    return "Unbreakable Strength";
                case QUICK_SLASH:
                    return "Quick Slash";
                case QUICK_FOCUS:
                    return "Quick Focus";
            }
            return null;
        }
    }

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

    public Charm() {
    }


}

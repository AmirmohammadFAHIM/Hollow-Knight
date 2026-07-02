package com.mygame.game.models.skill;

public enum ShootableTypes {

    VENGFUL_SPIRIT,

    CRYSTAL_LASER;

    float range;
    float cooldown;
    float speed;

    public float getCooldown() {
        return cooldown;
    }

    public float getRange() {
        return range;
    }

    public float getSpeed() {
        return speed;
    }
}

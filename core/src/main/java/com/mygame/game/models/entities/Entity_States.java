package com.mygame.game.models.entities;

public enum Entity_States {

    IDLE(null),

    NORMAL(null),

    END_ATTACK(NORMAL),

    Attack(null),

    START_ATTACK(Attack),

    END_SKILL(Attack),

    Skill(END_SKILL),

    START_SKILL(Skill),

    DEAD_END(null),

    DEATH_LANDING(DEAD_END),

    DEAD(DEATH_LANDING),

    TURN(NORMAL);


    Entity_States nextState;


    Entity_States(Entity_States nextState ) {
        this.nextState = nextState;
    }

    public boolean hasNextState() {
        return nextState != null;
    }
    public Entity_States getNextState() {
        return nextState;
    }
}

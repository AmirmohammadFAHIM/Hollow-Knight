package com.mygame.game.models.entities;

public enum NPC_States {

    NORMAL(null),

    ANGRY(null),

    COMBAT(null),

    DEAD_END(null),

    DEATH_LANDING(DEAD_END),

    DEAD(DEATH_LANDING),

    TURN(NORMAL);


    NPC_States nextState;


    NPC_States(NPC_States nextState ) {
        this.nextState = nextState;
    }

    public boolean hasNextState() {
        return nextState != null;
    }
    public NPC_States getNextState() {
        return nextState;
    }
}

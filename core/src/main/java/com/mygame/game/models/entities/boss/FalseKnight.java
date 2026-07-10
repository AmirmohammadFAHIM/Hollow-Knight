package com.mygame.game.models.entities.boss;

import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.Game;
import com.mygame.game.models.States;
import com.mygame.game.models.Vessel;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.Entity_States;
import com.mygame.game.models.map.SolidBlock;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class FalseKnight extends Entity {

    public FalseKnight(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 330;
        this.hp = 500;
        this.bounds = new Rectangle(x, y, width, height);
        setAction(Action.IDLE);
    }

    private enum KnightPosition{
        NEAR,MID,FAR;
    }
    public enum Action {
        IDLE,
        RUN,
        JUMP_KNOCK,
        DEFENSIVE_JUMP,
        AGGRESSIVE_JUMP,
        KNOCK,
        DEAD;

    }
    int phase = 1;
    record phaseOneConstants(float velocityX , float cooldown){}
    record phaseTwoConstants(float velocityX , float cooldown){}
    phaseOneConstants one = new phaseOneConstants(300 , 5);
    phaseTwoConstants two = new phaseTwoConstants(450 , 3f);
    private final float nearRange = 100f;
    private final float farRange = 850f;
    public Action action = Action.IDLE;
    KnightPosition knightPosition;
    boolean knightDirection = false;
    Deque<Action> last2action = new LinkedList<>();
    int damageTakenRecently = 0;
    private float cooldown = one.cooldown();



    private void whereIsKnight(){
        float dx = Game.getVessel().getX() - (this.x + this.width / 2);
        if(Math.abs(dx) <= nearRange){
            knightPosition = KnightPosition.NEAR;
        }
        else if (Math.abs(dx) >= farRange){
            knightPosition = KnightPosition.FAR;
        }
        else {
            knightPosition = KnightPosition.MID;
        }

        knightDirection = Game.getVessel().getX() > this.x;
    }


    float stunTimer = 7;
    private boolean Death(float delta){
        if(!alive){
            return true;
        }

        if(action == Action.DEAD){
            if(stunTimer <= 0 || DeathHitTimes >= 4){
                phase = 2;
                stunTimer = 7;
                setAction(Action.IDLE);
                if(DeathHitTimes >= 4){
                    hp = 500;
                    phase = 1;
                }
                DeathHitTimes = 0;
                return false;
            }
            else{
                stunTimer -= delta;
                return  true;
            }
        }

        return false;

    }


    Rectangle HeatBox =  new Rectangle();
    private int DeathHitTimes;
    private void DeathHit(){

        HeatBox.set(right ? this.x - 120 : this.x + this.width , 0 , 120 , 120);
        if(Game.getVessel().getState() == States.SLASH && this.action == Action.DEAD && alive){
            /// the hitbox
            if(Game.getVessel().getSlashBounds().overlaps(HeatBox)){
                setHurt(true);
            }

        }
    }



    public boolean turnPalse = false;
    public boolean turned = false;
    @Override
    public boolean update(float delta , Game game){
        System.out.println(hp);
        update_physics(delta, game);
        if(!alive){
            return false;
        }

        if(Death(delta)){
            return  false;
        }

        boolean done = true;
        resetRecentDamages(delta);
        switch(action){
            case RUN :
                done = run(delta, game);
                break;
            case  JUMP_KNOCK :
               done = knock(delta, game);
                break;
            case  DEFENSIVE_JUMP :
                done = defensive_jump(delta, game);
                break;
            case  AGGRESSIVE_JUMP :
                done = aggressive_jump(delta, game);
                break;
            case  KNOCK :
                done = knock(delta, game);
                break;
        }
        if(!done && action != Action.IDLE){
            setAction(Action.IDLE);
            cooldown = phase == 1 ? one.cooldown() : two.cooldown();
        }

        if(cooldown <= 0 && action == Action.IDLE){
            turned = false;
            decide(delta, game);
        }
        else if(action == Action.IDLE){
            cooldown -= delta;
            if(cooldown <= 0.6 && !turned){
                whereIsKnight();
                if(right != knightDirection){
                    turnPalse = true;
                    right = knightDirection;
                }
                turned = true;
                System.out.println("turn? " + turned);
            }

        }

        return true;
    }



    private void decide(float delta , Game game){
        System.out.println("LeBron James");
        whereIsKnight(); /// look where is the knight
        boolean underIntenseAttack = damageTakenRecently >= 3; /// am I taking a lot of damage?
        Action spam = checkLast2Action();
        boolean near = knightPosition == KnightPosition.NEAR;
        boolean far = knightPosition == KnightPosition.FAR;


        if(underIntenseAttack && spam != Action.DEFENSIVE_JUMP){
            defensive_jump(delta, game);
        }
        else if(near && phase == 2 && spam != Action.JUMP_KNOCK){
            /// jump knock function
        knock(delta, game);
        }
        else if(near && spam != Action.KNOCK) {
            knock(delta, game);
            }
        else if(!far && !near && spam != Action.AGGRESSIVE_JUMP){
            aggressive_jump(delta, game);
        }
        else if(far && spam != Action.RUN){
            run(delta, game);
        }
        else{
            randomDecide(delta , game, underIntenseAttack , near , far);
        }
        //System.out.println("decision : " + action);
        }

        private void randomDecide(float delta,Game game,
                                  boolean underIntenseAttack, boolean near , boolean far){

            System.out.println("We about to make a random decision :  " + cooldown +" " + action);
            if(underIntenseAttack  && phase == 2){
                /// jump knock function
            knock(delta, game);
            }
            else if(near){
                run(delta, game);
            }
            else if(far){
                aggressive_jump(delta, game);
            }
            else {
                Random rand = new Random();
                int random = rand.nextInt(5);
                switch (random){
                    case 0:
                        if(phase == 2) setAction(Action.JUMP_KNOCK);
                        else knock(delta, game);
                        break;
                    case 1:
                        run(delta, game);
                        break;
                    case 2:
                        aggressive_jump(delta, game);
                        break;
                    case 3:
                        defensive_jump(delta, game);
                        break;
                    case 4:
                        knock(delta, game);
                        break;
                }
            }




        }

    private Action checkLast2Action() {
        if (last2action.size() >= 2) {
            Action[] history = last2action.toArray(new Action[0]);
            if (history[0] == history[1]) return history[0];
        }
        return null;
    }








    float destinationX;
    float destinationY;
    /// we let the function to change the state
    private boolean run(float delta , Game game){
        if(action == Action.IDLE){
            System.out.println("run");
            setAction(Action.RUN);
            destinationX = Game.getVessel().getX();
            destinationY = 0;
            velocityX = (phase == 1 ? one.velocityX() : two.velocityX()) * (knightDirection ? 1 : -1);
            velocityY = 0;

            return true;
        }

        System.out.println("boss direction = " + right);



        if(Math.abs(this.x + this.width / 2 - destinationX) <= 25){
            System.out.println("We've reached here motherfucker");
            return false;
        }
        return true;
    }

    private boolean aggressive_jump(float delta , Game game){
        if(action == Action.IDLE){
            System.out.println("aggressive_jump");
            setAction(Action.AGGRESSIVE_JUMP);
            destinationX = Game.getVessel().getX() + this.width / 2;
            destinationY = 0;
            velocityX = (phase == 1 ? one.velocityX() : two.velocityX()) * (knightDirection ? 1 : -1);
            float dx = destinationX - this.x;
            //velocityY = Math.abs((7f * dx) / (2f * (phase == 1 ? one.velocityX() : two.velocityX())));
            velocityY = 750;
            is_grounded = false;
            return  true;
        }

        if(is_grounded){
            System.out.println("over : AJ");
            return false;
        }
        return true;
    }


    private boolean defensive_jump(float delta , Game game){
        System.out.println("defensive_jump");
        if(action == Action.IDLE){
            setAction(Action.DEFENSIVE_JUMP);
            velocityX = (phase == 1 ? one.velocityX() : two.velocityX()) * (right ? -1 : 1) * 0.5f;
            float dx = phase == 1 ? 250  : 450;
            velocityY = Math.abs(7f * dx / velocityX * 2);
            is_grounded = false;
            return true;
        }

        if(is_grounded){
            System.out.println("over : DJ");
            return false;
        }
        return true;
    }

    Rectangle hammer;
    float knockTimer = 1.3f;
    private boolean knock(float delta , Game game){
        if(action == Action.IDLE){
            System.out.println("knock");
            setAction(Action.KNOCK);
            float x = this.x + (right ? this.width : -165);
            hammer = new Rectangle(x , 0 , 200 , 165);
            return true;
        }

        if(knockTimer <= 0){
            knockTimer = 1.26f;
            System.out.println("over : K");
            return false;
        }

        knockTimer -= delta;

        if(Game.getVessel().getBounds().overlaps(hammer)){
            Game.getVessel().hurt = true;
            Game.getVessel().setHp(Game.getVessel().getHp() - 1); /// take one of the masks
        }

        return true;
    }

    public void setAction(Action action){
       if(this.action != action) {
           if (action != Action.IDLE) last2action.addFirst(action);
           if (last2action.size() > 2) last2action.removeLast();
           this.action = action;
           stateTime = 0;
           if (action == Action.IDLE) {
               velocityY = 0;
               velocityX = 0;
           }
       }
    }





    @Override
    public void setHurt(boolean hurt) {
        this.hurt = hurt;
       damageTakenRecently += 1;
    }

    float RDtimer = 1.5f;
    private void resetRecentDamages(float delta){
        if(RDtimer <= 0){
            RDtimer = 1.5f;
            damageTakenRecently = 0;
        }
        else{
            RDtimer -= delta;
        }
    }



    public void update_physics(float delta, Game game) {
        if (!is_grounded) {
            velocityY -= 7f;
        }

        x += velocityX * delta;
        bounds.x = x;

        bounds.y += 5;
        for (SolidBlock sb : Game.getCurrent_room().getBlocks()){
            if(bounds.overlaps(sb.getBlock())){
                if(velocityX < 0 ) x = sb.getBlock().x + sb.getBlock().getWidth();
                else x = sb.getBlock().x;
                break;
            }
        }
        bounds.y -= 5;
        bounds.x = x;



        y += velocityY * delta;
        bounds.y = y;

        is_grounded = false;

        for (SolidBlock sb : Game.getCurrent_room().getBlocks()) {
            Rectangle blockRect = sb.getBlock();
            if (blockRect.overlaps(bounds)) {
                if (velocityY <= 0) {
                    y = blockRect.y + blockRect.height;
                    velocityY = 0;
                    is_grounded = true;
                }
                bounds.y = y;
                break;
            }
        }

    }
}

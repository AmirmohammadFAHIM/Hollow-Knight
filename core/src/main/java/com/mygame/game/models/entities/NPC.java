package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.map.SolidBlock;

import java.util.ArrayList;

public class NPC {
    public static float hurtAccerlation = 50f;
    //public static float hurtCoolDown = 0.3f;
    protected float hurtRemaining = 0;
    protected float stateTime = 0f;
    protected String name;
    protected float  x;
    protected float y;
    protected float width = 50;
    protected float height = 40;
    protected Rectangle bounds;
    protected float velocityX;
    protected float velocityY;
    protected float hp;
    protected boolean right = false;
    protected boolean is_grounded = true;
    protected boolean alive = true;
    protected boolean hurt = false;
    protected NPC_States state = NPC_States.NORMAL;
    protected Animation<TextureAtlas.AtlasRegion> currentAnimation;
    public NPC(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public NPC(){

    }


    public float getHurtRemaining() {
        return hurtRemaining;
    }

    public void setHurtRemaining(float hurtRemaining) {
        this.hurtRemaining = hurtRemaining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
        if(hp <= 0){
            state = NPC_States.DEAD;
            alive = false;
        }
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isIs_grounded() {
        return is_grounded;
    }

    public void setIs_grounded(boolean is_grounded) {
        this.is_grounded = is_grounded;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isHurt() {
        return hurt;
    }

    public void setHurt(boolean hurt) {
        this.hurt = hurt;
        if(hurt){
            velocityY = LinearEnemy.getvY();
            velocityX = 230 * (right ? -1 : 1);
            is_grounded = false;
        }

    }

    public NPC_States getState() {
        return state;
    }

    public void setState(NPC_States state) {
        if(state != this.state){
            this.state = state;
            stateTime = 0;
            if(state == NPC_States.NORMAL){
                velocityX = LinearEnemy.getvX() * (right ? 1 : -1) ;
            }
        }

    }

    public boolean Hurt(float delta){
        if(hurt){

           if(is_grounded){
               setState(NPC_States.NORMAL);
           }
           else {
               velocityX += 8 * (right ? 1 : -1);
           }
            return true;
        }
        return false;
    }






    public void update(float delta , ArrayList <SolidBlock> blocks){
        stateTime += delta;
        if (!is_grounded) {
            velocityY -= 5;
        }
        update_physics(delta, blocks);

        if(Hurt(delta)){
            System.out.println(velocityX + " + " + velocityY);
            return;
        }

        System.out.println(velocityX + " " + velocityY);
        if(currentAnimation.isAnimationFinished(stateTime)){
            ;//
        }
    }



    public void update_physics(float delta, ArrayList<SolidBlock> blocks) {
        if (state == NPC_States.DEAD) {
            velocityX = 0;

        }

        // --- محور X ---
        x += velocityX * delta;
        bounds.x = x;

        // ترفند Skin Width: هیت‌باکس رو ۲ پیکسل از بالا و پایین جمع می‌کنیم
        // تا موقع حرکت افقی، پاهای انمی به زمین گیر نکنه
        /*bounds.y += 2f;
        bounds.height -= 4f;*/

        for (SolidBlock sb : blocks) {
            Rectangle blockRect = sb.getBlock();
            if (blockRect.overlaps(bounds)) {
                Rectangle block = sb.getBlock();
                if(right && block.x + block.width - bounds.x + bounds.width <= 3){
                    right = false;
                    setState(NPC_States.TURN);
                    System.out.println("collision x 1");
                }
                else if(!right && block.x - bounds.x >= 3){
                    right = true;
                    setState(NPC_States.TURN);
                    System.out.println("collision x 2");
                }
            }
        }

        // برگرداندن هیت‌باکس به سایز واقعی
        /*bounds.y -= 2f;
        bounds.height += 4f;*/

        // --- محور Y ---
        y += velocityY * delta;
        bounds.y = y;
        is_grounded = false;

        for (SolidBlock sb : blocks) {
            Rectangle blockRect = sb.getBlock();
            if (blockRect.overlaps(bounds)) {
                if (this.y > sb.getBlock().y + sb.getBlock().height - 3) { // فرود روی زمین
                    y = blockRect.y + blockRect.height;
                    velocityY = 0;
                    is_grounded = true;
                    if(hurt){
                        setHurt(false);
                        setState(NPC_States.NORMAL);
                    }
                }

            }
        }
    }
}

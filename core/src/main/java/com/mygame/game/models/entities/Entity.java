package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.linearEnemies.LinearEnemy;
import com.mygame.game.models.map.SolidBlock;

import java.util.ArrayList;

public class Entity {
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
    public float defaultSpeed ;
    protected float velocityX;
    protected float velocityY = 0;
    protected float hp;
    protected boolean flying = false;
    protected boolean right = false;
    protected boolean is_grounded = true;
    protected boolean alive = true;
    protected boolean hurt = false;
    protected Entity_States state = Entity_States.NORMAL;
    protected Animation<TextureAtlas.AtlasRegion> currentAnimation;
    public Entity(String name, float x, float y , float defaultSpeed) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.defaultSpeed = defaultSpeed;
    }

    public Entity(){

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
            state = Entity_States.DEAD;
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

    public Entity_States getState() {
        return state;
    }

    public void setState(Entity_States state) {
        if(state != this.state){
            this.state = state;
            stateTime = 0;
            if(state == Entity_States.NORMAL){
                velocityX = defaultSpeed * (right ? 1 : -1) ;
                velocityY = 0;
            }
            else if(state == Entity_States.IDLE){
                velocityX = 0;
                velocityY = 0;
            }
        }

    }

    public boolean Hurt(float delta){
        if(hurt){
           if(is_grounded){
               setState(Entity_States.NORMAL);
           }
           else {
               velocityX += 5 * (right ? 1 : -1);
           }
            return true;
        }
        return false;
    }






    public void update(float delta , Game game){
        stateTime += delta;
        ArrayList<SolidBlock> blocks = Game.getCurrent_room().getBlocks();

        update_physics(delta, blocks);


        if(Hurt(delta)){
            return;
        }

        if(state == Entity_States.DEAD || state == Entity_States.DEATH_LANDING) return;


        if(currentAnimation.isAnimationFinished(stateTime) &&
        state.hasNextState()){
            setState(state.nextState);
        }
    }



    public void update_physics(float delta, ArrayList<SolidBlock> blocks) {
        if (state == Entity_States.DEAD) {
            velocityX = 0;
            // اگر انمی در هوا مرد، جاذبه همچنان روی آن اثر کند تا بیفتد زمین
            if (!is_grounded) velocityY -= 7f;
        } else {
            // اعمال جاذبه معمولی برای انمی در صورتی که روی زمین نباشد
            if (!is_grounded && !flying) velocityY -= 7f;
            else if(!flying) velocityY = 0;
        }

        // -----------------------------------------------------------------
        // ۱. محور X - حرکت و برخورد با دیوارها
        // -----------------------------------------------------------------
        x += velocityX * delta;
        bounds.x = x;

        // حاشیه امن کف پا (Skin Width): کف پا را ۲ پیکسل بالا می‌آوریم تا به زمین گیر نکند
        bounds.y = y + 2f;
        bounds.height = height - 2f;

        boolean hitWall = false;
        for (SolidBlock sb : blocks) {
            Rectangle blockRect = sb.getBlock();
            if (blockRect.overlaps(bounds)) {
                // چون محور X جداست، هر برخوردی یعنی دیوار!
                if (velocityX > 0) {
                    // برخورد با دیوار سمت راست -> هل دادن به عقب و تغییر جهت به چپ
                    x = blockRect.x - width;
                    right = false;
                    velocityX = -Math.abs(velocityX); // سرعت منفی می‌شود
                    setState(Entity_States.TURN);
                } else if (velocityX < 0) {
                    // برخورد با دیوار سمت چپ -> هل دادن به جلو و تغییر جهت به راست
                    x = blockRect.x + blockRect.width;
                    right = true;
                    velocityX = Math.abs(velocityX); // سرعت مثبت می‌شود
                   if(state != Entity_States.Attack) setState(Entity_States.TURN);
                }


                hitWall = true;
                bounds.x = x; // بروزرسانی موقعیت هیت‌باکس بعد از اصلاح جابجایی
                break;
            }
        }

        // برگرداندن هیت‌باکس به ابعاد واقعی برای فاز Y
        bounds.y = y;
        bounds.height = height;

        // -----------------------------------------------------------------
        // ۲. محور Y - حرکت و فرود آمدن روی زمین
        // -----------------------------------------------------------------
        y += velocityY * delta;
        bounds.y = y;
        is_grounded = false;

        for (SolidBlock sb : blocks) {
            Rectangle blockRect = sb.getBlock();
            if (blockRect.overlaps(bounds)) {
                if (velocityY <= 0) { // در حال سقوط یا ایستاده
                    y = blockRect.y + blockRect.height;
                    velocityY = 0;
                    is_grounded = true;

                    if (hurt) {
                        setHurt(false);
                       if(state != Entity_States.DEATH_LANDING) setState(Entity_States.NORMAL);
                    }
                }
                bounds.y = y;
                break;
            }
        }

        // -----------------------------------------------------------------
        // ۳. رادار تشخیص دره و لبه‌ها (Ledge Detection)
        // -----------------------------------------------------------------
        // فقط زمانی لبه‌ها را چک می‌کنیم که انمی روی زمین باشد و به دیوار هم نخورده باشد
        // -----------------------------------------------------------------
// ۳. رادار تشخیص دره و لبه‌ها (Ledge Detection)
// -----------------------------------------------------------------
// رادار فقط و فقط در حالت حرکت عادی (NORMAL) باید روشن باشد
        if (is_grounded && !hitWall && state == Entity_States.NORMAL) {
            // ایجاد یک رادار کوچک (مستطیل فرضی) کمی جلوتر از بدن انمی و کمی پایین‌تر از کف پایش
            float radarX = right ? (x + width + 4f) : (x - 8f);
            float radarY = y - 5f; // ۵ پیکسل پایین‌تر از سطح زمین

            Rectangle ledgeRadar = new Rectangle(radarX, radarY, 4f, 8f);
            boolean groundAhead = false;

            // بررسی اینکه آیا این رادار با هیچ بلاکی تداخل دارد؟
            for (SolidBlock sb : blocks) {
                if (sb.getBlock().overlaps(ledgeRadar)) {
                    groundAhead = true; // زمین پیدا شد!
                    break;
                }
            }

            // اگر رادار با هیچ بلاکی تداخل نداشت، یعنی جلوش خالیه و دره است!
            if (!groundAhead) {
                right = !right; // تغییر جهت جهت منطقی
                velocityX = -velocityX; // معکوس کردن سرعت برای فریم‌های بعدی
                setState(Entity_States.TURN); // تغییر وضعیت به چرخش (که رادار را در فریم بعدی خاموش میکند)
            }
        }
    }
}

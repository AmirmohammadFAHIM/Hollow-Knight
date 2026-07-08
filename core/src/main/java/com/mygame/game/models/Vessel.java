package com.mygame.game.models;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.map.SolidBlock;
import com.mygame.game.models.map.Spike;
import com.mygame.game.view.VesselRender;

import java.util.ArrayList;

public class Vessel{
    /// ---------------STATICS-----------------
    private static float vertical_speed = 580f;
    private static float horizontal_speed = 450f;
    private static float dash_speed = 550f;
    private static float dash_cooldown = 0.4f;
    public static float gravity = 7f;
    private float stateTime = 0f;
    private float slashWidth = 80;
    private float slashHeight = 100;
    /// ---------------FIELDS------------------------
   private float x;
   private float y;
   private float velocityX;
   private float velocityY;
   private Rectangle bounds;
   /// ---------SAFETY RECORD--------------
    public record safeLoc(float x , float y){};
   private safeLoc safeLoc;
   /// ---------------------------------------
    private Rectangle slashBounds = new Rectangle();
    private float width = 70;
    private float height = 115;
    private float remaining_dash_time;
    private float hp = 5;
    private float damage = 40;
    /// -----------BOOLEANS--------------------
    private boolean is_ground = true;
    private boolean right = false;
    private boolean double_jump = true;
    public boolean hurt = false;
    /// -------------STATES--------------------
    private States state = States.IDLE;
    private States previous_state = States.IDLE;



    public Vessel(){


    }

    public static float getDash_speed() {
        return dash_speed;
    }

    public static void setDash_speed(float dash_speed) {
        Vessel.dash_speed = dash_speed;
    }

    public boolean isIs_ground() {
        return is_ground;
    }

    public void setIs_ground(boolean is_ground) {
        this.is_ground = is_ground;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
        if(hp <= 0){
            setState(States.Death);
        }
    }

    public boolean isDouble_jump() {
        return double_jump;
    }

    public void setDouble_jump(boolean double_jump) {
        this.double_jump = double_jump;
    }

    public static float getVertical_speed() {
        return vertical_speed;
    }

    public static void setVertical_speed(float vertical_speed) {
        Vessel.vertical_speed = vertical_speed;
    }

    public static float getHorizontal_speed() {
        return horizontal_speed;
    }

    public static void setHorizontal_speed(float horizontal_speed) {
        Vessel.horizontal_speed = horizontal_speed;
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

    public float getSlashWidth() {
        return slashWidth;
    }

    public void setSlashWidth(float slashWidth) {
        this.slashWidth = slashWidth;
    }

    public float getSlashHeight() {
        return slashHeight;
    }

    public void setSlashHeight(float slashHeight) {
        this.slashHeight = slashHeight;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
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

    public float getStateTime() {
        return stateTime;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
       if(state != this.state){
           previous_state = this.state;
           this.state = state;
           this.stateTime = 0f; ///resetting the state time so animations don't glitch
            if(state == States.IDLE){
                velocityX = 0f;
                velocityY = 0f;
            }
       }


    }


    public static float getDash_cooldown() {
        return dash_cooldown;
    }

    public static void setDash_cooldown(float dash_cooldown) {
        Vessel.dash_cooldown = dash_cooldown;
    }

    public float getRemaining_dash_time() {
        return remaining_dash_time;
    }

    public void setRemaining_dash_time(float remaining_dash_time) {
        this.remaining_dash_time = remaining_dash_time;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public States getPrevious_state() {
        return previous_state;
    }

    public void setPrevious_state(States previous_state) {
        this.previous_state = previous_state;
    }

    public void update(float delta , Game game) {
        stateTime += Gdx.graphics.getDeltaTime();
        death();
        if(state == States.Death) return;
        if(vengfull(delta)) return;
      if(freeze <= 0 && !hurt)  update_physics(delta , Game.getCurrent_room().getBlocks());
        updateSlashBounds();

        /// ------------------DASH STATE , MOST PARTICULAR ONE----------------------

        if(hurt){
            hurt(delta);
            return;
        }
       if(Dash(delta)) return;
       //else if(vengfull(delta)) return;

        /// --------------------OTHER STATES-------------------------

            heal(delta);
            if(this.state.shouldGoNext(stateTime )){
                setState(state.nextState);
            }

        fall();
        updateSafety();
        spike();

        if(state == States.WALL_SIDE){
            velocityY = -100;
        }
        if(state == States.DOUBLE_JUMP){
            if(VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)){
                setState(previous_state);
            }
        }







        /// -----------------SKILLS : SLASH , VENGFUL SPIRIT----------------

        slash(game);



    }


    private void update_physics(float delta, ArrayList<SolidBlock> blocks) {
        // -----------------------------------------------------------------
        // ۱. حرکت محور X و بررسی دیوارها
        // -----------------------------------------------------------------
        if(is_ground) velocityY = 0;
        else if(state != States.DASH && state != States.FIREBALL) velocityY -= gravity;

        x += velocityX * delta;
        bounds.x = x;

        // رفع باگ تلپورت (باگ شماره ۳):
        // فقط کف پا را ۲ پیکسل بالا می‌آوریم تا با زمین درگیر نشود.
        // سقفِ سر (Top) را دست‌نخورده رها می‌کنیم تا بالاتنه کاملاً دیوارها را تشخیص دهد.
        bounds.y = y + 2f;
        bounds.height = height - 2f; // اینگونه بالای هیت‌باکس دقیقاً سر جایش می‌ماند (y + height)

        for (SolidBlock sb : blocks) {
            Rectangle blockRect = sb.getBlock();
            if (blockRect.overlaps(bounds)) {
                // برخورد افقی با دیوار
                if (velocityX > 0) {
                    x = blockRect.x - width;
                } else if (velocityX < 0) {
                    x = blockRect.x + blockRect.width;
                }

                // ورود به حالت وال‌اسلاید (فقط در هوا و در حال سقوط)
                if (!is_ground && velocityY < 0) {
                    setState(States.WALL_SIDE);
                    double_jump = true;
                }

                velocityX = 0;
                bounds.x = x;
                break;
            }
        }

        // برگرداندن هیت‌باکس به ابعاد واقعی برای محاسبات محور Y
        bounds.y = y;
        bounds.height = height;

        // -----------------------------------------------------------------
        // ۲. حرکت محور Y و بررسی زمین/سقف
        // -----------------------------------------------------------------
        y += velocityY * delta;
        bounds.y = y;

        for (SolidBlock sb : blocks) {
            Rectangle blockRect = sb.getBlock();
            if (blockRect.overlaps(bounds)) {
                if (velocityY < 0) {
                    // فرود موفقیت‌آمیز روی زمین
                    y = blockRect.y + blockRect.height;

                    if (state == States.FALLING || state == States.WALL_SIDE) {
                        setState(States.LANDING);
                    }
                    is_ground = true;
                    double_jump = true;
                } else if (velocityY > 0) {
                    // اصابت سر به سقف
                    y = blockRect.y - height;
                }

                velocityY = 0;
                bounds.y = y;
                break;
            }
        }

        // -----------------------------------------------------------------
        // ۳. رادار تشخیص زمین و رفع باگ وال‌اسلاید
        // -----------------------------------------------------------------
        bounds.y = y - 1f;
        boolean grounded = false;
        for (SolidBlock sb : blocks) {
            if (sb.getBlock().overlaps(bounds)) {
                grounded = true;
                break;
            }
        }
        bounds.y = y;
        is_ground = grounded;

        // رفع باگ وال‌اسلاید (باگ شماره ۱):
        // اگر در حالت وال‌اسلاید هستیم، چک می‌کنیم آیا هنوز دیواری در نزدیکی هست؟
        if (state == States.WALL_SIDE) {
            boolean wallPresent = false;

            // چک کردن دیوار در سمت چپ (با فاصله ناچیز ۱ پیکسلی)
            bounds.x = x - 1f;
            for (SolidBlock sb : blocks) {
                if (sb.getBlock().overlaps(bounds)) { wallPresent = true; break; }
            }

            // چک کردن دیوار در سمت راست (با فاصله ناچیز ۱ پیکسلی)
            bounds.x = x + 1f;
            for (SolidBlock sb : blocks) {
                if (sb.getBlock().overlaps(bounds)) { wallPresent = true; break; }
            }

            bounds.x = x; // برگرداندن موقعیت اصلی هیت‌باکس

            // اگر دیواری نبود یا بازیکن به زمین رسید، از حالت وال‌اسلاید خارج شو
            if (!wallPresent || is_ground) {
                setState(States.FALLING);
            }
        }
    }



    private void slash(Game game){


        ArrayList<Entity> enemies = Game.getCurrent_room().getEnemies();
        for (Entity n : enemies) {
            if(n.getBounds().overlaps(slashBounds) && n.isAlive()){
               /// To Do:Declare that enemy is hit
                n.setHurt(true);
                n.setHp(n.getHp() - damage);

            }
        }

        for (Spike x : Game.getCurrent_room().getSpikes()){
            if(state == States.DOWN_SLASH &&
            slashBounds.overlaps(x.getBounds())){
                velocityY = 200;
                break;
            }
        }
    }


    private void spike(){
        for (Spike x : Game.getCurrent_room().getSpikes()){
            if(x.getBounds().overlaps(bounds)){
               if(state != States.Death){
                   setHurt(true);
                   setHp(hp - 1);
               }
            }
        }

    }


    private boolean Dash(float delta){
        if(state == States.DASH){
            remaining_dash_time -= delta;
            if(remaining_dash_time <= 0){
                remaining_dash_time = dash_cooldown;
                if(is_ground){
                    setState(States.IDLE); ///dash ended on ground
                }
                else {
                    setState(States.FALLING); ///dash ended on air
                }
                velocityX = 0;
            }
            return true;
        }
        return false;
    }


    private boolean vengfull(float delta){
        if(state == States.FIREBALL){
            if(VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)){
                state = previous_state;
            }

            return true;
        }
        return false;
    }

    private void fall(){
        if(state != States.SLASH && state != States.UP_SLASH && state != States.DOWN_SLASH
        && state != States.JUMPING && state != States.WALL_JUMP && state != States.WALL_SIDE && state !=
        States.DOUBLE_JUMP ){
           if(!is_ground) setState(States.FALLING);
        }
    }



    public Rectangle getBounds() {
        return bounds;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void updateRect() {
        bounds  = new Rectangle(x, y, width, height);
    }

    public static float getGravity() {
        return gravity;
    }

    public static void setGravity(float gravity) {
        Vessel.gravity = gravity;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Rectangle getSlashBounds() {
        return slashBounds;
    }

    public void setSlashBounds(Rectangle slashBounds) {
        this.slashBounds = slashBounds;
    }

    private void updateSlashBounds() {
        // فقط وقتی در حالت اسلش هستیم نیازه که هیت‌باکس آپدیت بشه
        if (state == States.SLASH) {
            float sx;
            float sy;

            // محاسبه X بر اساس جهت نگاه کردن
            if (isRight()) {
                // اگر راست می‌بینه: هیت‌باکس رو بنداز سمت راستِ هیت‌باکس شوالیه
                // (میتونی یه مقدار کمی هم ببریش داخل هیت باکس خود کاراکتر که دشمنانی که خیلی چسبیدن هم دمیج بخورن)
                sx = this.x + this.width/2;
            } else {
                // اگر چپ می‌بینه: هیت‌باکس رو بنداز سمت چپ
                sx = this.x - slashWidth;
            }

            // محاسبه Y (وسط چین کردن هیت‌باکس شمشیر نسبت به قد شوالیه)
            sy = this.y + (this.height / 2f) - (slashHeight / 2f);

            // اعمال مختصات به مستطیل
            slashBounds.set(sx, sy, slashWidth, slashHeight);
        }
        else if(state == States.UP_SLASH){
            slashBounds.set(x , y + height , slashWidth ,  slashHeight);
        }
        else if(state == States.DOWN_SLASH){
            slashBounds.set(x , y - slashHeight , slashWidth ,  slashHeight);
        }
        else {
            // وقتی اسلش نمی‌زنیم، هیت‌باکس رو می‌فرستیم یه جای دور که الکی با چیزی برخورد نکنه
            slashBounds.set(-1000, -1000, 0, 0);
        }
    }

    private final float spawnPoint = 6;
    private float timer = 0;
    private void updateSafety(){
        if(timer <= 0){
            if(is_ground && state != States.Death){
                safeLoc = new safeLoc(x , y);
            }
            timer = spawnPoint;
        }
        else{
            timer -= Gdx.graphics.getDeltaTime();
        }
    }

    private void death(){
        if(state == States.Death && VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)){
            hp = 5;
            x = safeLoc.x();
            y = safeLoc.y();
            setState(States.IDLE);
        }
    }


    float freeze = 0;
    float knockBackTime = 1.2f;
    private void hurt(float delta){
        if(freeze <= 0){
            velocityY = 700;
            velocityX = 450 * (right? -1 : 1);
            hurt = false;
        }
        else{
            freeze -= delta;
        }
    }

    public void setHurt(boolean hurt) {
        freeze = 0.4f;
        knockBackTime = 1.2f;
        this.hurt = hurt;
    }

    public void heal(float delta){
        if(state == States.FOCUS_GET && VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)){
            setHp(hp + 1);
        }
    }
}

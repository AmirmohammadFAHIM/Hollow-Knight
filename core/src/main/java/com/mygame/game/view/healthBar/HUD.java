package com.mygame.game.view.healthBar;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygame.game.models.Game;

import java.util.ArrayList;

public class HUD extends Table {
    private ArrayList<Mask> masks;
    private int lastKnownHp = 5;
    public HUD(){
        masks = new ArrayList<>();
      int fulls = (int) Game.getVessel().getHp() - 1;
      for ( int i = 0; i < 5; i++ ) {
          if(i <= fulls){
              masks.add(new Mask(i , Mask.State.SHINE));
          }
          else{
              masks.add(new Mask(i , Mask.State.BREAK));
          }
      }
      this.setFillParent(true);
      this.top().left();
      this.defaults().padRight(15);
      for (Mask mask : masks) {
          this.add(mask).size(120 , 120);
      }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        int actualHp = (int) Game.getVessel().getHp();

        if (actualHp < lastKnownHp) {
            int damageTaken = lastKnownHp - actualHp;
            for (int i = 0; i < damageTaken; i++) {
                breakLastFullMask();
            }
            lastKnownHp = actualHp;
        }
        else if (actualHp > lastKnownHp) {
            int healAmount = actualHp - lastKnownHp;
            for (int i = 0; i < healAmount; i++) {
                fillNextEmptyMask();
            }
            lastKnownHp = actualHp;
        }
    }

    private void breakLastFullMask(){
        for (int i = masks.size() - 1; i >= 0; i--) {
            Mask mask = masks.get(i);
            if (mask.isFull()) {
                mask.BreakMask();
                break;
            }
        }
    }

    private void fillNextEmptyMask(){
        for (Mask mask : masks) {
            if (!mask.isFull()) {
                mask.FillMask();
                break;
            }
        }
    }

}

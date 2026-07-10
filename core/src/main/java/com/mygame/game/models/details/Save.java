package com.mygame.game.models.details;

import com.mygame.game.models.Vessel;

public class Save {
    Vessel vesel;
    int roomIndex;
    String chunkName;
    int slotIndex;
    float progress;

    public Vessel getVesel() {
        return vesel;
    }

    public void setVesel(Vessel vesel) {
        this.vesel = vesel;
    }

    public int getRoomIndex() {
        return roomIndex;
    }

    public void setRoomIndex(int roomIndex) {
        this.roomIndex = roomIndex;
    }

    public String getChunkName() {
        return chunkName;
    }

    public void setChunkName(String chunkName) {
        this.chunkName = chunkName;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }


}

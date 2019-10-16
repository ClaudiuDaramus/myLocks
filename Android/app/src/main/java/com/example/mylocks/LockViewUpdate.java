package com.example.mylocks;

public interface LockViewUpdate {
    int STATE_LOCKED = 0;
    int STATE_UNLOCKED = 0;
    int STATE_UNAVAILABLE = 0;

    void onStateChanged(int state);
}

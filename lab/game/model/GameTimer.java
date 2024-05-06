package lab.game.model;

import lab.game.view.Observer;

import java.util.*;
import java.util.Timer;

public class GameTimer extends Observable{
    private int seconds;
    private final Timer timer;


    public GameTimer() {
        timer = new Timer();
        // startTime = new Date();
    }

    public void start() {
        seconds = 0;
        notifyObservers();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                notifyObservers();
            }
        }, 0, 1000);
    }


    public String getTime() {
        int minutes = seconds / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateTimer(getTime());
        }
    }

    public void stop() {
        timer.cancel();
    }
}

package lab.game.model;

import lab.game.view.Observer;

import java.util.ArrayList;

public abstract class Observable {
    ArrayList<Observer> observers = new ArrayList<>();

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public abstract void notifyObservers();
}

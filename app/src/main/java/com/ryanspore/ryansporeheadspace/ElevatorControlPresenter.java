package com.ryanspore.ryansporeheadspace;

import android.support.v7.widget.RecyclerView;

import java.util.HashSet;
import java.util.Set;

public class ElevatorControlPresenter implements ElevatorButtonsAdapter.Delegate {
    private static final int DIRECTION_STOPPED = 0;
    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = 2;

    private Delegate delegate;
    private Set<Integer> selectedFloors;
    private Integer movingDirection;
    private Integer currentFloor;
    private ExecutionManager handler;

    public ElevatorControlPresenter(Delegate delegate, ExecutionManager handler) {
        this.delegate = delegate;
        selectedFloors = new HashSet<>();
        this.handler = handler;
        setMovingDirection(DIRECTION_STOPPED);
        currentFloor = 1;
        delegate.setCurrentFloor(currentFloor);
    }

    @Override
    public void addSelection(int floor) {
        if(floor == currentFloor) {
            delegate.clearSelection(floor);
            return;
        }
        selectedFloors.add(floor);
        if (movingDirection == DIRECTION_STOPPED) {
            setMovingDirection(currentFloor < floor ? DIRECTION_UP : DIRECTION_DOWN);
            startMoving(1500);
        }
    }

    private void startMoving(final long delayInMillis) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (movingDirection == DIRECTION_UP) {
                    currentFloor++;
                } else if (movingDirection == DIRECTION_DOWN) {
                    currentFloor--;
                }
                delegate.setCurrentFloor(currentFloor);
                if (selectedFloors.contains(currentFloor)) {
                    selectedFloors.remove(currentFloor);
                    delegate.clearSelection(currentFloor);
                }
                if (anySelectedFloorsInDirection(movingDirection)) {
                    startMoving(500);
                } else if (!selectedFloors.isEmpty()) {
                    setMovingDirection(movingDirection == DIRECTION_UP ? DIRECTION_DOWN : DIRECTION_UP);
                    startMoving(1500);
                } else {
                    setMovingDirection(DIRECTION_STOPPED);
                }
            }
        }, delayInMillis);
    }

    private boolean anySelectedFloorsInDirection(int direction) {
        Set<Integer> floorsInDirection = new HashSet<>();
        for (Integer selectedFloor : selectedFloors) {
            if (direction == DIRECTION_DOWN && selectedFloor < currentFloor) {
                floorsInDirection.add(selectedFloor);
            }
            if (direction == DIRECTION_UP && selectedFloor > currentFloor) {
                floorsInDirection.add(selectedFloor);
            }
        }
        return !floorsInDirection.isEmpty();
    }

    private void setMovingDirection(int movingDirection) {
        this.movingDirection = movingDirection;
        switch (movingDirection) {
            case DIRECTION_DOWN:
                delegate.showMovingDown();
                break;
            case DIRECTION_UP:
                delegate.showMovingUp();
                break;
            case DIRECTION_STOPPED:
                delegate.showNotMoving();
                break;
        }
    }

    public interface ExecutionManager {
        boolean postDelayed(Runnable r, long delayMillis);
    }

    public interface Delegate {
        void setFloorButtonsAdapter(RecyclerView.Adapter floorButtonsAdapter);

        void setCurrentFloor(int currentFloor);

        void showMovingUp();

        void showMovingDown();

        void showNotMoving();

        void clearSelection(int floor);
    }
}

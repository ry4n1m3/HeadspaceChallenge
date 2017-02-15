package com.ryanspore.ryansporeheadspace;

import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ElevatorControlPresenterTest {

    private TestDelegate testDelegate;
    private TestExecutionManager testExecutionManager;
    private ElevatorControlPresenter subject;

    @Before
    public void setUp() throws Exception {
        testDelegate = new TestDelegate();
        testExecutionManager = new TestExecutionManager();
        subject = new ElevatorControlPresenter(testDelegate, testExecutionManager);
        testDelegate.shownFloors.clear();
    }

    @Test
    public void addSelection_disallowsCurrentFloor() throws Exception {
        subject.addSelection(1);
        testExecutionManager.runOff();
        assertEquals(true, testDelegate.shownFloors.isEmpty());
    }

    @Test
    public void addSelection_traversesFloorsToSelection() throws Exception {
        subject.addSelection(5);
        testExecutionManager.runOff();
        assertArrayEquals(new Integer[] {2, 3, 4, 5}, testDelegate.shownFloors.toArray());
    }

    @Test
    public void addSelection_traversesFloorsToSelectionInBothDirections() throws Exception {
        subject.addSelection(5);
        testExecutionManager.runOff();
        testDelegate.shownFloors.clear();
        subject.addSelection(10);
        subject.addSelection(1);
        testExecutionManager.runOff();
        assertArrayEquals(new Integer[] {6,7,8,9,10,9,8,7,6,5,4,3,2,1}, testDelegate.shownFloors.toArray());
    }

    private static class TestDelegate implements ElevatorControlPresenter.Delegate {
        public List<Integer> shownFloors = new ArrayList<>();

        @Override
        public void setFloorButtonsAdapter(RecyclerView.Adapter floorButtonsAdapter) {

        }

        @Override
        public void setCurrentFloor(int currentFloor) {
            shownFloors.add(currentFloor);
        }

        @Override
        public void showMovingUp() {

        }

        @Override
        public void showMovingDown() {

        }

        @Override
        public void showNotMoving() {

        }

        @Override
        public void clearSelection(int floor) {

        }
    }

    private static class TestExecutionManager implements ElevatorControlPresenter.ExecutionManager {
        private List<Runnable> runnableList = new ArrayList<>();

        @Override
        public boolean postDelayed(Runnable r, long delayMillis) {
            runnableList.add(r);
            return false;
        }

        public void runOff() {
            while(!runnableList.isEmpty()) {
                Runnable runnable = runnableList.get(0);
                runnableList.remove(0);
                runnable.run();
            }
        }
    }
}
package com.ryanspore.ryansporeheadspace;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class ElevatorControlView extends RelativeLayout
        implements ElevatorControlPresenter.Delegate {

    private TextView currentFloor;
    private View upArrow;
    private View downArrow;
    private RecyclerView floorButtons;
    private ElevatorControlPresenter presenter;
    private ElevatorButtonsAdapter floorButtonsAdapter;
    private int floorCount;

    public ElevatorControlView(Context context) {
        super(context);
    }

    public ElevatorControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ElevatorControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ElevatorControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        currentFloor = (TextView) findViewById(R.id.current_floor);
        upArrow = findViewById(R.id.up_arrow);
        downArrow = findViewById(R.id.down_arrow);
        floorButtons = (RecyclerView) findViewById(R.id.floor_buttons);
        floorButtons.setLayoutManager(new GridLayoutManager(getContext(), 3, VERTICAL, false));
        presenter = new ElevatorControlPresenter(this, new ElevatorHandler());
    }


    public void setFloorCount(int floorCount) {
        this.floorCount = floorCount;
        floorButtonsAdapter = new ElevatorButtonsAdapter(floorCount, presenter);
        setFloorButtonsAdapter(floorButtonsAdapter);
    }

    @Override
    public void setFloorButtonsAdapter(RecyclerView.Adapter floorButtonsAdapter) {
        floorButtons.setAdapter(floorButtonsAdapter);
    }

    @Override
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor.setText(String.valueOf(currentFloor));
    }

    @Override
    public void showMovingUp() {
        upArrow.setVisibility(View.VISIBLE);
        downArrow.setVisibility(View.GONE);
    }

    @Override
    public void showMovingDown() {
        downArrow.setVisibility(View.VISIBLE);
        upArrow.setVisibility(View.GONE);
    }

    @Override
    public void showNotMoving() {
        upArrow.setVisibility(View.GONE);
        downArrow.setVisibility(View.GONE);
    }

    @Override
    public void clearSelection(int floor) {
        floorButtonsAdapter.clearSelection(floor);
    }

    public static class ElevatorHandler extends Handler
            implements ElevatorControlPresenter.ExecutionManager {

    }
}

package com.ryanspore.ryansporeheadspace;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class ElevatorActivity extends AppCompatActivity implements FloorConfigurationView.Delegate {

    public static final String CONFIGURED_FLOORS = "configured_floors";
    private ViewGroup root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elevator_control);
        root = (ViewGroup) findViewById(R.id.elevator_control_root);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int floorCount = sharedPreferences.getInt(CONFIGURED_FLOORS, -1);
        if (floorCount > 0) {
            showElevatorControl(floorCount);
        } else {
            showFloorSelection();
        }
    }

    private void showFloorSelection() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        FloorConfigurationView inflated = (FloorConfigurationView) layoutInflater.inflate(R.layout.floor_configuration, root, false);
        inflated.setDelegate(this);
        root.removeAllViews();
        root.addView(inflated);
    }

    private void showElevatorControl(int floorCount) {
        // todo
    }

    @Override
    public void floorConfigurationComplete(int floorCount) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit()
                .putInt(CONFIGURED_FLOORS, floorCount)
                .apply();
        showElevatorControl(floorCount);
    }
}

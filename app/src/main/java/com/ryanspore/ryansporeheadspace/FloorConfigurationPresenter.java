package com.ryanspore.ryansporeheadspace;

public class FloorConfigurationPresenter {
    private Delegate delegate;
    private FloorConfigurationView.Delegate floorConfigurationDelegate;

    public FloorConfigurationPresenter(Delegate delegate) {
        this.delegate = delegate;
    }

    public void onSubmitClicked(CharSequence currentValue) {
        int floorCount = 0;
        try {
            floorCount = Integer.parseInt(currentValue.toString());
        } catch (NumberFormatException e) {
            delegate.setSubmitButtonEnabled(false);
        }
        if(floorCount > 0) {
            floorConfigurationDelegate.floorConfigurationComplete(floorCount);
        }
    }

    public void onFloorCountChanged(CharSequence currentValue) {
        try {
            Integer.parseInt(currentValue.toString());
            delegate.setSubmitButtonEnabled(true);
        } catch (NumberFormatException e) {
            delegate.setSubmitButtonEnabled(false);
        }
    }

    public void setFloorConfigurationDelegate(FloorConfigurationView.Delegate floorConfigurationDelegate) {
        this.floorConfigurationDelegate = floorConfigurationDelegate;
    }

    public interface Delegate {
        void setSubmitButtonEnabled(boolean enabled);
    }
}

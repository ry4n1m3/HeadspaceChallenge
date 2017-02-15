package com.ryanspore.ryansporeheadspace;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class FloorConfigurationView extends LinearLayout
        implements FloorConfigurationPresenter.Delegate {

    private EditText floorCount;
    private Button submitButton;
    private FloorConfigurationPresenter presenter;

    public FloorConfigurationView(Context context) {
        super(context);
    }

    public FloorConfigurationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloorConfigurationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FloorConfigurationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        floorCount = (EditText) findViewById(R.id.floor_count);
        submitButton = (Button) findViewById(R.id.submit_configuration);
        presenter = new FloorConfigurationPresenter(this);

        floorCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onFloorCountChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSubmitClicked(floorCount.getText());
            }
        });
    }

    public void setDelegate(Delegate delegate) {
        presenter.setFloorConfigurationDelegate(delegate);
    }

    @Override
    public void setSubmitButtonEnabled(boolean enabled) {
        submitButton.setEnabled(enabled);
    }

    public interface Delegate {
        void floorConfigurationComplete(int floorCount);
    }
}

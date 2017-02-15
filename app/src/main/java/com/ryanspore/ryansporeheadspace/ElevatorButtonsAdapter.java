package com.ryanspore.ryansporeheadspace;

import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ElevatorButtonsAdapter extends RecyclerView.Adapter {

    private static final int FLOOR_BUTTON = 1;
    private static final int CLOSE_DOORS_BUTTON = 2;

    private final int floorCount;
    private Delegate delegate;

    public ElevatorButtonsAdapter(int floorCount, Delegate delegate) {
        this.floorCount = floorCount;
        this.delegate = delegate;
    }

    @Override
    public int getItemViewType(int position) {
        if(position < floorCount) {
            return FLOOR_BUTTON;
        } else {
            return CLOSE_DOORS_BUTTON;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TextView itemView = (TextView) layoutInflater.inflate(R.layout.floor_control_cell, parent, false);
        if(viewType == FLOOR_BUTTON) {
            return new FloorNumberHolder(itemView);
        } else {
            return new CloseDoorsHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FloorNumberHolder) {
            final int floor = floorCount - position;
            final FloorNumberHolder floorNumberHolder = (FloorNumberHolder) holder;
            floorNumberHolder.setDisplayInt(floor);
            floorNumberHolder.itemView.getBackground().setColorFilter(null);
            floorNumberHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.addSelection(floor);
                    int lightTransparentBlue = 0x889999ff;
                    floorNumberHolder.itemView.getBackground().setColorFilter(lightTransparentBlue, PorterDuff.Mode.MULTIPLY);
                    floorNumberHolder.itemView.setOnClickListener(null);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return floorCount + 1;
    }

    public void clearSelection(int floor) {
        notifyItemChanged(floorCount - floor);
    }

    public static class FloorNumberHolder extends RecyclerView.ViewHolder {
        private TextView itemView;

        public FloorNumberHolder(TextView itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void setDisplayInt(int displayInt) {
            itemView.setText(String.valueOf(displayInt));
        }
    }

    public static class CloseDoorsHolder extends RecyclerView.ViewHolder {
        public CloseDoorsHolder(TextView itemView) {
            super(itemView);
            itemView.setText("> <");
        }
    }

    public interface Delegate {
        void addSelection(int floor);
    }
}

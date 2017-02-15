package com.ryanspore.ryansporeheadspace;

import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ElevatorButtonsAdapter extends RecyclerView.Adapter<ElevatorButtonsAdapter.FloorNumberHolder> {

    private final int floorCount;
    private Delegate delegate;

    public ElevatorButtonsAdapter(int floorCount, Delegate delegate) {
        this.floorCount = floorCount;
        this.delegate = delegate;
    }

    @Override
    public ElevatorButtonsAdapter.FloorNumberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TextView itemView = (TextView) layoutInflater.inflate(R.layout.floor_control_cell, parent, false);
        return new FloorNumberHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ElevatorButtonsAdapter.FloorNumberHolder holder, int position) {
        final int floor = floorCount - position;
        holder.setDisplayInt(floor);
        holder.itemView.getBackground().setColorFilter(null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.addSelection(floor);
                int lightTransparentBlue = 0x889999ff;
                holder.itemView.getBackground().setColorFilter(lightTransparentBlue, PorterDuff.Mode.MULTIPLY);
                holder.itemView.setOnClickListener(null);
            }
        });
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

    public interface Delegate {
        void addSelection(int floor);
    }
}

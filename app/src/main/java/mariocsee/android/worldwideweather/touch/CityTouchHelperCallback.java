package mariocsee.android.worldwideweather.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import mariocsee.android.worldwideweather.adapter.CityRecyclerAdapter;

/**
 * Created by mariocsee on 12/1/16.
 */

public class CityTouchHelperCallback extends ItemTouchHelper.Callback {

    private CityRecyclerAdapter cityRecyclerAdapter;

    public CityTouchHelperCallback(CityRecyclerAdapter cityRecyclerAdapter) {
        this.cityRecyclerAdapter = cityRecyclerAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        cityRecyclerAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition()
        );
        return true;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        cityRecyclerAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}

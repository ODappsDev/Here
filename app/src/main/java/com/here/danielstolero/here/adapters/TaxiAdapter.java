package com.here.danielstolero.here.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.here.danielstolero.here.R;
import com.here.danielstolero.here.databinding.ItemTaxiBinding;
import com.here.danielstolero.here.db.entities.TaxiEntity;
import com.here.danielstolero.here.models.Taxi;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TaxiAdapter extends RecyclerView.Adapter<TaxiAdapter.TaxiViewHolder> {

    private final String TAG = TaxiAdapter.class.getSimpleName();

    private List<TaxiEntity> mTaxiList;

    static class TaxiViewHolder extends RecyclerView.ViewHolder {

        private ItemTaxiBinding binding;

        TaxiViewHolder(ItemTaxiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public TaxiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaxiBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_taxi, parent, false);
        return new TaxiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaxiViewHolder holder, int position) {
        holder.binding.setTaxi(mTaxiList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mTaxiList == null ? 0 : mTaxiList.size();
    }

    public void setTaxis(@Nullable final List<TaxiEntity> taxiList) {
        if(taxiList != null) {
            if (mTaxiList == null) {
                mTaxiList = taxiList;
                Collections.sort(mTaxiList);
                notifyItemRangeInserted(0, taxiList.size());
            } else {
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                    @Override
                    public int getOldListSize() {
                        return mTaxiList.size();
                    }

                    @Override
                    public int getNewListSize() {
                        return taxiList.size();
                    }

                    @Override
                    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return taxiList.get(oldItemPosition).getId() ==
                                taxiList.get(newItemPosition).getId();
                    }

                    @Override
                    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        Taxi newTaxi = taxiList.get(newItemPosition);
                        Taxi oldTaxi = mTaxiList.get(oldItemPosition);
                        return newTaxi.getId() == oldTaxi.getId()
                                && Objects.equals(newTaxi.getName(), oldTaxi.getName())
                                && newTaxi.getEta() == oldTaxi.getEta();
                    }
                });
                mTaxiList = taxiList;
                Collections.sort(mTaxiList);
                result.dispatchUpdatesTo(this);
            }
        } else {
            Log.d(TAG, "Empty list.");
        }
    }


}

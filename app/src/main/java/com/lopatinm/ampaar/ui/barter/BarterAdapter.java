package com.lopatinm.ampaar.ui.barter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lopatinm.ampaar.R;
import com.lopatinm.ampaar.ui.ampaar.Product;
import com.lopatinm.ampaar.ui.ampaar.ProductAdapter;

import java.util.ArrayList;

public class BarterAdapter extends RecyclerView.Adapter<BarterAdapter.ViewHolder>{

    private ArrayList localDataSet;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textViewCount;
        private final TextView textViewName;
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.barterComment);
            textViewCount = (TextView) view.findViewById(R.id.productCount);
            textViewName = (TextView) view.findViewById(R.id.productName);
        }
        public TextView getTextView() {
            return textView;
        }

        public TextView getTextViewCount() {
            return textViewCount;
        }

        public TextView getTextViewName() {
            return textViewName;
        }
    }

    public BarterAdapter(Context context, ArrayList dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public BarterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_barter, viewGroup, false);

        return new BarterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BarterAdapter.ViewHolder viewHolder, final int position) {
        Barter barter = (Barter) localDataSet.get(position);
        viewHolder.getTextView().setText(barter.comment);
        viewHolder.getTextViewCount().setText(barter.count.toString());
        viewHolder.getTextViewName().setText(barter.name);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
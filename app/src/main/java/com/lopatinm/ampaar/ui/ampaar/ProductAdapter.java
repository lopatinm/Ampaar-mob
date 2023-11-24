package com.lopatinm.ampaar.ui.ampaar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lopatinm.ampaar.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private ArrayList localDataSet;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textViewCount;

        ImageButton plusButton;
        ImageButton minusButton;

        ImageButton barterAddButton;
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.nameTextView);
            textViewCount = (TextView) view.findViewById(R.id.countTextView);
            plusButton = (ImageButton) view.findViewById(R.id.plusButton);
            minusButton = (ImageButton) view.findViewById(R.id.minusButton);
            barterAddButton = (ImageButton) view.findViewById(R.id.barterAddButton);
        }
        public TextView getTextView() {
            return textView;
        }

        public TextView getTextViewCount() {
            return textViewCount;
        }
    }

    public ProductAdapter(Context context, ArrayList dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Product product = (Product) localDataSet.get(position);
        viewHolder.getTextView().setText(product.name);
        viewHolder.getTextViewCount().setText(product.count.toString());

        viewHolder.barterAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmpaarFragment.productId = product.id;
                AmpaarFragment.productCount = product.count;
                AmpaarFragment.productName = product.name;
                AmpaarFragment.productComment = "Обменяю";
                new AmpaarFragment.SendProductBarterData().execute();
            }
        });

        viewHolder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmpaarFragment.productId = product.id;
                AmpaarFragment.productCount = product.count + 1;
               new AmpaarFragment.PutProductData().execute();
            }
        });


        viewHolder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmpaarFragment.productId = product.id;
                AmpaarFragment.productCount = product.count - 1;
                new AmpaarFragment.PutProductData().execute();
            }
        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }



}

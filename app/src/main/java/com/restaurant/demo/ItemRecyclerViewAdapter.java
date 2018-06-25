package com.restaurant.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sonu on 24/07/17.
 */

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {
	private int itemQuantityResult = 0;
	class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private TextView itemLabel;
		private LinearLayout addButtonLayout;
		private LinearLayout quantityLayout;
		private Button itemQuantityAdd;
		private TextView itemQuantityMinus;
		private TextView itemQuantityPlus;
		private TextView itemQuantityValue;

		public ItemViewHolder(View itemView) {
			super(itemView);
			itemLabel = (TextView) itemView.findViewById(R.id.item_label);
			quantityLayout = (LinearLayout) itemView.findViewById(R.id.quantityLayout);
			addButtonLayout = (LinearLayout) itemView.findViewById(R.id.addButtonLayout);
			itemQuantityAdd = itemView.findViewById(R.id.itemQuantityAdd);
			itemQuantityMinus = itemView.findViewById(R.id.itemQuantityMinus);
			itemQuantityPlus = itemView.findViewById(R.id.itemQuantityPlus);
			itemQuantityValue = itemView.findViewById(R.id.itemQuantityValue);

			//add call backs
			itemQuantityAdd.setOnClickListener(this);
			itemQuantityMinus.setOnClickListener(this);
			itemQuantityPlus.setOnClickListener(this);

		}

		@Override
		public void onClick(View view) {
			if (view == itemQuantityAdd) {
				addButtonLayout.setVisibility(View.GONE);
				quantityLayout.setVisibility(View.VISIBLE);
			} else if (view == itemQuantityPlus) {
				itemQuantityResult++;
				if(itemQuantityResult == 0){
					addButtonLayout.setVisibility(View.VISIBLE);
					quantityLayout.setVisibility(View.GONE);
				}else{
					addButtonLayout.setVisibility(View.GONE);
					quantityLayout.setVisibility(View.VISIBLE);
				}
			} else if (view == itemQuantityMinus) {
				itemQuantityResult--;
				if(itemQuantityResult == 0){
					addButtonLayout.setVisibility(View.VISIBLE);
					quantityLayout.setVisibility(View.GONE);
				}else{
					addButtonLayout.setVisibility(View.GONE);
					quantityLayout.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	private Context context;
	private ArrayList<String> arrayList;

	public ItemRecyclerViewAdapter(Context context, ArrayList<String> arrayList) {
		this.context = context;
		this.arrayList = arrayList;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_row_layout, parent, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ItemViewHolder holder, int position) {
		holder.itemLabel.setText(arrayList.get(position));
	}

	@Override
	public int getItemCount() {
		return arrayList.size();
	}


}

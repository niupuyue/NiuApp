package com.niupule.niuapp.mvp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/24
 * Time: 17:19
 * Desc:
 * Version:
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>{


    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public FavoritesViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

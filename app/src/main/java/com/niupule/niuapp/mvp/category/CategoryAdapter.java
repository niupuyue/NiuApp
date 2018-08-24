package com.niupule.niuapp.mvp.category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 22:52
 * Desc:
 * Version:
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView text;


        public CategoryViewHolder(View itemView) {
            super(itemView);

        }
    }
}

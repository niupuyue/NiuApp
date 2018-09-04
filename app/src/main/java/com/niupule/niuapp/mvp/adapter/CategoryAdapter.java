package com.niupule.niuapp.mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niupule.niuapp.R;
import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.interfaze.OnRecycleViewItemClick;
import com.niupule.niuapp.util.StringUtil;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/4
 * Time: 11:57
 * Desc:
 * Version:
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ArticleDetailData> list;
    private OnRecycleViewItemClick listener;

    public CategoryAdapter(Context context,List<ArticleDetailData> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnRecycleViewItemClick listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category_article,parent,false);
        return new CategoryViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryViewHolder viewHolder = holder;
        ArticleDetailData data = list.get(position);
        viewHolder.item_view_title.setText(StringUtil.replaceInvalidChar(data.getTitle()));
        viewHolder.item_view_author.setText(data.getAuthor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<ArticleDetailData> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
        notifyItemChanged(list.size());
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private OnRecycleViewItemClick click;
        TextView item_view_title;
        AppCompatTextView item_view_author;

        public CategoryViewHolder(View itemView,OnRecycleViewItemClick listener) {
            super(itemView);
            item_view_author = itemView.findViewById(R.id.text_view_author);
            item_view_title = itemView.findViewById(R.id.text_view_title);
            click = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            click.onItemClick(v,getLayoutPosition());
        }
    }

}

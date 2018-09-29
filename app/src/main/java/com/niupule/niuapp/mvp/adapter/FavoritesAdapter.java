package com.niupule.niuapp.mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupule.niuapp.R;
import com.niupule.niuapp.data.detail.FavoriteArticleDetailData;
import com.niupule.niuapp.interfaze.OnCategoryOnClickListener;
import com.niupule.niuapp.interfaze.OnRecycleViewItemClick;
import com.niupule.niuapp.util.StringUtil;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/24
 * Time: 17:19
 * Desc:
 * Version:
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>{

    private List<FavoriteArticleDetailData> list;
    private Context context;
    private LayoutInflater inflater;
    private OnRecycleViewItemClick recycleViewItemClick;
    private OnCategoryOnClickListener categoryOnClickListener;

    public FavoritesAdapter(Context context,List<FavoriteArticleDetailData> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void updateData(List<FavoriteArticleDetailData> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
        notifyItemRemoved(list.size());
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_articles,parent,false);
        return new FavoritesViewHolder(view,recycleViewItemClick,categoryOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        FavoritesViewHolder viewHolder = holder;
        FavoriteArticleDetailData data = list.get(position);
        viewHolder.textAuthor.setText(data.getAuthor());
        viewHolder.textTitle.setText(StringUtil.replaceInvalidChar(data.getTitle()));
        //if the text is too long, the button can not show it correctly.The solution is adding " ".
        viewHolder.btnCategory.setText("  "+data.getChapterName()+"  ");
        viewHolder.textTime.setText(data.getNiceDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(OnRecycleViewItemClick listener){
        this.recycleViewItemClick = listener;
    }

    public void setCategoryListener(OnCategoryOnClickListener listener) {
        this.categoryOnClickListener = listener;
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnRecycleViewItemClick onRecycleViewItemClick;
        OnCategoryOnClickListener onCategoryOnClickListener;
        CardView cardView;
        AppCompatButton btnCategory;
        AppCompatTextView textTitle;
        AppCompatTextView textAuthor;
        AppCompatTextView textTime;

        public FavoritesViewHolder(View itemView,OnRecycleViewItemClick listener1,OnCategoryOnClickListener listener2) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_articles_card_view_layout);
            cardView.setOnClickListener(this);
            onRecycleViewItemClick = listener1;
            onCategoryOnClickListener = listener2;
            btnCategory = itemView.findViewById(R.id.item_articles_category);
            btnCategory.setOnClickListener(this);
            textTitle = itemView.findViewById(R.id.item_articles_title);
            textAuthor = itemView.findViewById(R.id.item_articles_author);
            textTime = itemView.findViewById(R.id.item_articles_time);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_articles_card_view_layout:
                    onRecycleViewItemClick.onItemClick(v,getAdapterPosition());
                    break;
                case R.id.item_articles_category:
                    onCategoryOnClickListener.onItemClick(v,getAdapterPosition());
                    break;
            }
        }
    }
}

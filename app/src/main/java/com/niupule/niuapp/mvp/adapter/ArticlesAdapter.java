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
import com.niupule.niuapp.data.detail.ArticleDetailData;
import com.niupule.niuapp.interfaze.OnCategoryOnClickListener;
import com.niupule.niuapp.interfaze.OnRecycleViewItemClick;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/10
 * Time: 22:59
 * Desc:
 * Version:
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private Context context;

    private LayoutInflater inflater;

    private List<ArticleDetailData> data;

    private OnRecycleViewItemClick recycleViewItemClick;

    private OnCategoryOnClickListener categoryOnClickListener;

    public static final int HEAD_VIEW = 0;
    public static final int NORMAL_VIEW = 1;

    private View headerView;

    public ArticlesAdapter(Context context, List<ArticleDetailData> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(this.context);
    }

    public void setArticlesItemClickListener(OnRecycleViewItemClick listener) {
        this.recycleViewItemClick = listener;
    }

    public void setCategoryOnClickListener(OnCategoryOnClickListener categoryOnClickListener) {
        this.categoryOnClickListener = categoryOnClickListener;
    }

    public void updateData(List<ArticleDetailData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
        notifyItemRemoved(data.size());
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEAD_VIEW) {
            return new ArticlesViewHolder(headerView, null, null);
        }
        View view = inflater.inflate(R.layout.item_articles, parent, false);
        return new ArticlesViewHolder(view, this.recycleViewItemClick, this.categoryOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {
        if (getItemViewType(position) == HEAD_VIEW) {
            return;
        }
        ArticlesViewHolder viewHolder = holder;
        ArticleDetailData data = this.data.get(getRealPosition(position));
        viewHolder.author.setText(data.getAuthor());
        viewHolder.title.setText(data.getTitle());
        viewHolder.time.setText(data.getNiceDate());
        viewHolder.btnCategory.setText(" " + data.getChapterName() + " ");
    }

    @Override
    public int getItemCount() {
        return this.data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView == null) return NORMAL_VIEW;
        if (position == 0) return HEAD_VIEW;
        return NORMAL_VIEW;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    protected int getRealPosition(int position) {
        if (headerView != null) {
            return position - 1;
        }
        return position;
    }

    class ArticlesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnRecycleViewItemClick recycleViewItemClick;
        OnCategoryOnClickListener categoryOnClickListener;
        CardView cardView;
        AppCompatButton btnCategory;
        AppCompatTextView author;
        AppCompatTextView title;
        AppCompatTextView time;

        public ArticlesViewHolder(View itemView, OnRecycleViewItemClick recycleViewItemClick, OnCategoryOnClickListener categoryOnClickListener) {
            super(itemView);
            if (itemView == headerView) {
                return;
            }
            this.recycleViewItemClick = recycleViewItemClick;
            this.categoryOnClickListener = categoryOnClickListener;
            btnCategory = itemView.findViewById(R.id.item_articles_category);
            btnCategory.setOnClickListener(this);
            author = itemView.findViewById(R.id.item_articles_author);
            title = itemView.findViewById(R.id.item_articles_title);
            time = itemView.findViewById(R.id.item_articles_time);
            cardView = itemView.findViewById(R.id.item_articles_card_view_layout);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_articles_card_view_layout:
                    recycleViewItemClick.onItemClick(v, getRealPosition(getAdapterPosition()));
                    break;
                case R.id.item_articles_category:
                    categoryOnClickListener.onItemClick(v, getRealPosition(getAdapterPosition()));
                    break;
                default:
                    break;
            }
        }
    }

}

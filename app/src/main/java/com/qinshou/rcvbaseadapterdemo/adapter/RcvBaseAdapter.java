package com.qinshou.rcvbaseadapterdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qinshou.rcvbaseadapterdemo.holder.BaseViewHolder;
import com.qinshou.rcvbaseadapterdemo.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:RecyclerView 的适配器的基类
 * Created by 禽兽先生
 * Created on 2017/11/22
 */

public abstract class RcvBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private int layoutId;
    private List<T> dataList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public RcvBaseAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (mOnItemClickListener != null) {
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder, dataList.get(position), position);
                }
            });
        }
        bindViewHolder(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract void bindViewHolder(BaseViewHolder holder, T itemData, int position);

    public Context getContext() {
        return context;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public List<T> getDataList() {
        return dataList;
    }

    /**
     * Description:设置数据,所有数据将被替换
     * Date:2018/3/9
     *
     * @param dataList 需要添加的数据
     */
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * Description:添加数据
     * Date:2018/3/9
     *
     * @param dataList  需要添加的数据
     * @param isRefresh 是否是刷新,如果为 true 则会先清空当前数据再添加,为 false 则会追加
     */
    public void addDataList(List<T> dataList, boolean isRefresh) {
        if (isRefresh) {
            this.dataList.clear();
        }
        this.dataList.addAll(dataList);
        if (isRefresh) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(this.dataList.size() - dataList.size(), this.dataList.size());
        }
    }

    /**
     * Description:设置 item 的点击监听器
     * Date:2018/3/9
     */
    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

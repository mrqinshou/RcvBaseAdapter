package com.qinshou.rcvbaseadapterdemo.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.qinshou.rcvbaseadapterdemo.holder.BaseViewHolder;
import com.qinshou.rcvbaseadapterdemo.itemview.BaseItemView;


/**
 * Description:item 为多布局类型的 RecyclerView 的适配器
 * 可以实现一对多（一个实体类对应多种布局）和多对多（多个实体类对应多个布局）
 * 使用时创建该适配器实例,创建不同的 ItemView 继承 BaseItemView,
 * 然后调用 addItemView() 方法传入 ItemView 即可
 * {@link com.qinshou.rcvbaseadapterdemo.itemview.BaseItemView}
 * BaseItemView 中有一个方法 isForViewType() 用来判断何时引入哪种子布局，多对多时不用覆盖该方法，内部会根据泛型的真实类型和
 * 该 position 的数据的类型判断，一对多时需覆盖该方法加入自己的逻辑判断，否则同一类型只会使用传入的第一个 ItemView
 * <p>
 * Created by 禽兽先生
 * Created on 2018/4/9
 */

public class RcvMultipleBaseAdapter extends RcvBaseAdapter {
    private SparseArray<BaseItemView> itemViewSparseArray;

    public RcvMultipleBaseAdapter(Context context) {
        super(context, 0);
        itemViewSparseArray = new SparseArray<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return itemViewSparseArray.get(viewType).onCreateViewHolder(parent);
    }

    @Override
    public int getItemViewType(int position) {
        for (int i = 0; i < itemViewSparseArray.size(); i++) {
            BaseItemView baseItemView = itemViewSparseArray.valueAt(i);
            if (baseItemView.isForViewType(getDataList().get(position), position)) {
                return itemViewSparseArray.keyAt(i);
            }
        }
        throw new IllegalArgumentException("No ItemView added that matches position=" + position + " in data source");
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, Object itemData, int position) {
        for (int i = 0; i < itemViewSparseArray.size(); i++) {
            BaseItemView baseItemView = itemViewSparseArray.valueAt(i);
            if (baseItemView.isForViewType(itemData, position)) {
                baseItemView.bindViewHolder(holder, itemData, position);
                return;
            }
        }
    }

    public void addItemView(BaseItemView baseItemView) {
        itemViewSparseArray.put(baseItemView.hashCode(), baseItemView);
    }

    public void removeItemView(BaseItemView baseItemView) {
        itemViewSparseArray.remove(baseItemView.hashCode());
    }
}

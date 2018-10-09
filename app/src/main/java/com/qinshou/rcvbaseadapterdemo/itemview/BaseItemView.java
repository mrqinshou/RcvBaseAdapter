package com.qinshou.rcvbaseadapterdemo.itemview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qinshou.rcvbaseadapterdemo.holder.BaseViewHolder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description:RecyclerView 单数据对多类型布局，多数据对多类型布局的适配器的 BaseItemView
 * Created by 禽兽先生
 * Created on 2018/4/9
 */

public abstract class BaseItemView<T> {
    private Context context;
    private int layoutId;

    public BaseItemView(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public BaseViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(context, itemView);
    }

    /**
     * Description:     子类可以覆蓋此方法决定引用该子布局的时机
     * Date:2018/8/6
     *
     * @param item     该position对应的数据
     * @param position position
     * @return 是否属于子布局
     */
    public boolean isForViewType(T item, int position) {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Class clazz = (Class) parameterizedType.getActualTypeArguments()[0];
        return item.getClass() == clazz;
    }

    /**
     * Description:绑定 UI 和数据
     * Date:2018/8/6
     */
    public abstract void bindViewHolder(BaseViewHolder holder, T itemData, int position);
}

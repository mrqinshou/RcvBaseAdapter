package com.qinshou.rcv.viewholder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: MrQinshou
 * Email:cqflqinhao@126.com
 * Date: 2021/4/14 10:37
 * Description:The basic of view holder,developers no longer need to care about the implementation
 * of the ViewHolder
 */
class BaseViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    private val viewSparseArray: SparseArray<View> = SparseArray()

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Return a view by id.
     */
    fun <T : View?> findViewById(viewId: Int): T? {
        var view: View? = viewSparseArray.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            viewSparseArray.put(viewId, view)
        }
        return view as T
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Return a text view by id.
     */
    fun getTextView(textViewId: Int): TextView? {
        var textView: TextView? = viewSparseArray.get(textViewId) as TextView
        if (textView == null) {
            textView = itemView.findViewById(textViewId)
            viewSparseArray.put(textViewId, textView)
        }
        return textView
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Return a image view by id.
     */
    fun getImageView(imageViewId: Int): ImageView? {
        var imageView: ImageView? = viewSparseArray.get(imageViewId) as ImageView
        if (imageView == null) {
            imageView = itemView.findViewById(imageViewId)
            viewSparseArray.put(imageViewId, imageView)
        }
        return imageView
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the visibility of view.
     */
    fun setVisibility(viewId: Int, visibility: Int): BaseViewHolder {
        val view = findViewById<View>(viewId)
        if (view != null) {
            view.visibility = visibility
        }
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the text of text view.
     */
    fun setTvText(viewId: Int, text: CharSequence?): BaseViewHolder {
        val textView = findViewById<TextView>(viewId)
        if (textView != null) {
            textView.text = text
        }
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the text of text view.
     */
    fun setTvText(viewId: Int, @StringRes resId: Int): BaseViewHolder {
        findViewById<TextView>(viewId)?.text = context.resources.getString(resId)
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the text of button.
     */
    fun setBtnText(viewId: Int, text: CharSequence?): BaseViewHolder {
        val button = findViewById<Button>(viewId)
        if (button != null) {
            button.text = text
        }
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the text of button.
     */
    fun setBtnText(viewId: Int, @StringRes resId: Int): BaseViewHolder {
        findViewById<Button>(viewId)?.text = context.resources.getString(resId)
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the img of image view.
     */
    fun setIvImage(viewId: Int, @DrawableRes resId: Int): BaseViewHolder {
        findViewById<ImageView>(viewId)?.setImageResource(resId)
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the img of image view.
     */
    fun setIvImage(viewId: Int, bitmap: Bitmap?): BaseViewHolder {
        findViewById<ImageView>(viewId)?.setImageBitmap(bitmap)
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the img of image view.
     */
    fun setIvImage(viewId: Int, drawable: Drawable?): BaseViewHolder {
        findViewById<ImageView>(viewId)?.setImageDrawable(drawable)
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the listener of click of view.
     */
    fun setOnClickListener(viewId: Int, onClickListener: View.OnClickListener?): BaseViewHolder {
        findViewById<View>(viewId)?.setOnClickListener(onClickListener)
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the state of selected of view.
     */
    fun setSelected(viewId: Int, selected: Boolean): BaseViewHolder {
        val view = findViewById<View>(viewId)
        if (view != null) {
            view.isSelected = selected
        }
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the background of view.
     */
    fun setBackgroundColor(viewId: Int, @ColorInt color: Int): BaseViewHolder {
        findViewById<View>(viewId)?.setBackgroundColor(color)
        return this
    }

    /**
     * Author: MrQinshou
     * Email:cqflqinhao@126.com
     * Date:2021/4/14 18:13
     * Description:Set the background of view.
     */
    fun setBackgroundResource(viewId: Int, @DrawableRes resId: Int): BaseViewHolder {
        findViewById<View>(viewId)?.setBackgroundResource(resId)
        return this
    }
}
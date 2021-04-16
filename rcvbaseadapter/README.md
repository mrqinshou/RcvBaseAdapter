# 简介
RecyclerView 通用适配器，不用再写繁琐的 onCreateViewHolder()、getItemCount() 等方法了，只需要在 bindViewHolder() 中关注数据绑定即可。
除满足一对一（单类型数据对应单类型布局）外，还可以快速实现多对多（多类型数据对应多类型布局），一对多（单类型数据
对应多类型布局）。
适配器还支持添加空布局、头布局、脚布局、骨架屏等功能。

# 如何使用
1. 项目的 build.gradle 添加如下依赖

```xml
 allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

2. app 的 build.gradle 添加如下依赖，当前最新版本 v0.0.1

```xml
dependencies {
        implementation 'com.github.mrqinshou:RcvBaseAdapter:Tag'
}
```

# 使用说明
一对一时继承 RcvSimpleAdapter，然后在 bindViewHolder() 方法中绑定 UI 即可。

多对多时继承 RcvMultipleAdapter，然后在对应的 ItemView 的 bindViewHolder() 方法中绑定 UI 即可，RcvMultipleAdapter 适配器中会根据不同的数据类型，分发到对应的 ItemView。

一对多时也是继承 RcvMultipleAdapter，但是由于数据类型一样，所以在绑定 UI 时适配器没办法分发到对应的 ItemView，需要自己重写 isForViewType() 方法来判断 ItemView 的显示时机。

## 一对一
1. 定义布局

    item_rcv_string.xml

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:background="#FFFFFF00"
           android:orientation="vertical">

        <TextView
            android:id="@+id/text_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:textColor="#FF000000"
            android:textSize="24sp" />
    </LinearLayout>
    ```

2. 定义适配器

    ```java
    class RcvOneToOneAdapter(context: Context) : RcvSimpleAdapter<String>(context, R.layout.item_rcv_string) {

        override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
            holder.setTvText(R.id.text_view, itemData)
        }
    }
    ```

3. 设置适配器并填充数据

   ```java
   class OneToOneActivity : AppCompatActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_one_to_one)
           val rcvOneToOneAdapter = RcvOneToOneAdapter(this)
           rcv_one_to_one.adapter = rcvOneToOneAdapter
           val list = ArrayList<String>()
           for (i in 0 until 100) {
               list.add("测试文字$i")
           }
           rcvOneToOneAdapter.dataList = list
       }
   }
   ```

## 多对多
1. 定义布局

   item_rcv_integer.xml

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:background="#00CED1"
       android:orientation="vertical">

       <TextView
           android:id="@+id/text_view"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:gravity="center"
           android:textColor="#FF000000"
           android:textSize="24sp" />
   </LinearLayout>
   ```

   item_rcv_string.xml

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:background="#FFFFFF00"
       android:orientation="vertical">

       <TextView
           android:id="@+id/text_view"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:gravity="center"
           android:textColor="#FF000000"
           android:textSize="24sp" />
   </LinearLayout>
   ```

2. 定义 ItemView

   IntegerItemView

   ```java
   /**
    * 多对多时,由于实体类型不一样,适配器内部会根据 item 类型来加载对应布局
    */
   class IntegerItemView(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<Int?>(context, R.layout.item_rcv_integer, rcvBaseAdapter) {

       override fun bindViewHolder(holder: BaseViewHolder, itemData: Int?, position: Int) {
           holder.setTvText(R.id.text_view, "" + itemData)
       }
   }
   ```

   StringItemView

   ```java
   /**
    * 多对多时,由于实体类型不一样,适配器内部会根据 item 类型来加载对应布局
    */
   class StringItemView(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<String?>(context, R.layout.item_rcv_string, rcvBaseAdapter) {

       override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
           holder.setTvText(R.id.text_view, itemData)
       }
   }
   ```

3. 定义适配器

   ```java
   class RcvManyToManyAdapter(context: Context) : RcvMultipleAdapter(context) {
       init {
           addItemView(IntegerItemView(context, this))
           addItemView(StringItemView(context, this))
       }
   }
   ```

4. 设置适配器并填充数据

   ```java
   class ManyToManyActivity : AppCompatActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_many_to_many)
           val rcvManyToManyAdapter = RcvManyToManyAdapter(this)
           rcv_many_to_many.adapter = rcvManyToManyAdapter
           val list = ArrayList<Any>()
           for (i in 0 until 100) {
               if (i % 2 == 0) {
                   list.add(i)
               } else {
                   list.add("测试文字$i")
               }
           }
           rcvManyToManyAdapter.dataList = list
       }
   }
   ```

## 一对多

1. 定义布局

   item_rcv_one_to_many_1.xml

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:background="#FFF5F5DC"
       android:orientation="vertical">

       <TextView
           android:id="@+id/text_view"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:gravity="left"
           android:textColor="#FF000000"
           android:textSize="24sp" />
   </LinearLayout>
   ```

   item_rcv_one_to_many_2.xml

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:background="#FF8FBC8F"
       android:orientation="vertical">

       <TextView
           android:id="@+id/text_view"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:gravity="right"
           android:textColor="#FF000000"
           android:textSize="24sp" />
   </LinearLayout>
   ```

2. 定义 ItemView

   OneToManyItemView1

   ```java
   class OneToManyItemView1(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<String?>(context, R.layout.item_rcv_one_to_many_1, rcvBaseAdapter) {

       // 一对多时,重写 isForViewType,根据自己的业务需求判断 ItemView 显示的时机
       override fun isForViewType(item: Any?, position: Int): Boolean {
           return position % 2 == 0
       }

       override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
           holder.setTvText(R.id.text_view, itemData)
       }
   }
   ```

   OneToManyItemView2

   ```java
   class OneToManyItemView2(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<String?>(context, R.layout.item_rcv_one_to_many_2, rcvBaseAdapter) {

       // 一对多时,重写 isForViewType,根据自己的业务需求判断 ItemView 显示的时机
       override fun isForViewType(item: Any?, position: Int): Boolean {
           return position % 2 != 0
       }

       override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
           holder.setTvText(R.id.text_view, itemData)
       }
   }
   ```

3. 定义适配器

   ```java
   class RcvOneToManyAdapter(context: Context) : RcvMultipleAdapter(context) {
       init {
           addItemView(OneToManyItemView1(context, this))
           addItemView(OneToManyItemView2(context, this))
       }
   }
   ```

4. 设置适配器并填充数据

   ```java
   class OneToManyActivity : AppCompatActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_one_to_many)
           val rcvOneToManyAdapter = RcvOneToManyAdapter(this)
           rcv_one_to_many.adapter = rcvOneToManyAdapter
           val list = ArrayList<Any>()
           for (i in 0 until 100) {
               list.add("测试文字$i")
           }
           rcvOneToManyAdapter.dataList = list
       }
   }
   ```

## 特殊 Item

可以继承 BaseItemView 实现自己的 ItemView，库中定义了几个特殊的 ItemView 帮助快速实现空布局、头布局、脚布局，甚至骨架屏。由于是添加的不同 ItemView，所以适配器需使用 RcvMultipleAdapter。

各个特殊的 ItemView 如果仅做展示的话，直接创建对应 ItemView 对应，通过 addItemView() 方法添加就好。如果对布局中的控件有逻辑操作的，重写 bindViewHolder() 方法，跟普通的 ItemView 一样做自己的逻辑即可。

骨架屏在添加后，需要设置 adapter 的 skeletonItemViewCount 属性来控制骨架屏条目数量，大于 0 时会显示骨架屏，小于等于 0 时不会显示。

详细用法可以参考 com.qinshou.demo.SpecialItemActivity

# 更新日志

- 0.0.1
  1. 增加基适配器，使用 kotlin 改写完成；
  2. 支持一对一、多对多、一对多功能；
  3. 支持空布局、头布局、脚布局、骨架屏。
# 简介
RecyclerView 通用适配器，不用再写繁琐的 onCreateViewHolder()、getItemCount() 等方法了，只需要在
bindViewHolder() 中关注数据绑定即可。
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
一对一时继承

## 一对一
1.定义布局
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
2.定义适配器
    ```java
    class RcvOneToOneAdapter(context: Context) : RcvSimpleAdapter<String>(context, R.layout.item_rcv_string) {

        override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
            holder.setTvText(R.id.text_view, itemData)
        }
    }
    ```java
3.设置适配器并填充数据
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
1.定义布局
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
    item_rcv_string
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
2.定义适配器
class RcvManyToManyAdapter(context: Context) : RcvMultipleAdapter(context) {
    init {
        addItemView(IntegerItemView(context, this))
        addItemView(StringItemView(context, this))
    }
}

class IntegerItemView(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<Int?>(context, R.layout.item_rcv_integer, rcvBaseAdapter) {

    override fun bindViewHolder(holder: BaseViewHolder, itemData: Int?, position: Int) {
        holder.setTvText(R.id.text_view, "" + itemData)
    }
}

class StringItemView(context: Context, rcvBaseAdapter: RcvBaseAdapter<Any>) : BaseItemView<String?>(context, R.layout.item_rcv_string, rcvBaseAdapter) {

    override fun bindViewHolder(holder: BaseViewHolder, itemData: String?, position: Int) {
        holder.setTvText(R.id.text_view, itemData)
    }
}
3.设置适配器并填充数据
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


- public Context getContext()：获取 Context 对象。
- public List<T> getDataList()：获取数据列表。
- public void setDataList(List<T> dataList)：设置数据列表，所有数据将被替换。
- public void addDataList(List<T> dataList)：添加数据，追加数据在原数据列表最后。
- public void addDataList(List<T> dataList, boolean isRefresh)：重载，第二个参数表示是否是刷新，如果为 true 则会先清空当前数据再添加，为 false 则会追加。
- public void setOnItemClickListener(IOnItemClickListener<T> onItemClickListener)：设置 item 的点击监听器。
- public void setOnItemLongClickListener(IOnItemLongClickListener<T> onItemLongClickListener)：设置 item 的长按点击监听器。

### RcvSingleBaseAdapter

item 为单布局的 RecyclerView 的适配器，如果列表只是相同布局的 item,只需要继承该适配器。

- public abstract void bindViewHolder(BaseViewHolder holder, T t)：必须实现该方法，在该方法中处理业务逻辑。

### RcvMultipleBaseAdapter

多布局类型的 RecyclerView 的适配器,可以实现一对多（一个实体类对应多种布局）和多对多（多个实体类对应多个布局）。使用时创建该适配器实例，创建不同的 ItemView 继承自 BaseItemView，然后调用 addItemView() 方法传入 ItemView 即可，BaseItemView 中有一个方法 isForViewType() 用来判断何时引入哪种子布局,多对多时不用覆盖该方法,内部会根据泛型的真实类型和该 position 的数据的类型判断，一对多时需覆盖该方法加入自己的逻辑判断，否则同一类型只会使用传入的第一个 ItemView。

- public void addItemView(BaseItemView baseItemView)：添加 itemView。
- public void removeItemView(BaseItemView baseItemView)：移除 itemView。

### baseholder 包

#### BaseViewHolder
封装一些常规操作。
- public <T extends View> T findViewById(int viewId)：根据 id 查找控件。
- public TextView getTextView(int textViewId)：根据 id 获取 TextView。
- public ImageView getImageView(int imageViewId)：根据 id 获取 ImageView。
- public BaseViewHolder setVisibility(int viewId, int visibility)：设置控件是否可见。
- public BaseViewHolder setTvText(int viewId, CharSequence text)：设置 TextView 文本内容。
- public BaseViewHolder setTvText(int viewId, @StringRes int resId)：设置 TextView 文本资源。
- public BaseViewHolder setBtnText(int viewId, CharSequence text)：设置 Button 文本内容。
- public BaseViewHolder setBtnText(int viewId, @StringRes int resId)：设置 Button 文本资源。
- public BaseViewHolder setIvImage(int viewId, Bitmap bitmap)：设置 ImageView 图片。
- public BaseViewHolder setIvImage(int viewId, @DrawableRes int resId)：设置 ImageView 图片资源。
- public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener)：设置控件点击监听器。
- public BaseViewHolder setSelected(int viewId, boolean selected)：设置控件的 selected 状态。
- public BaseViewHolder setBackgroundColor(int viewId, @ColorInt int color)：设置控件的背景色。
- public BaseViewHolder setBackgroundResource(int viewId, @DrawableRes int resId)：设置控件背景资源。
### itemview 包

#### BaseItemView
ItemView 的基类，通常不用继承该类，一对多和多对多时，或者需要添加空布局、头布局、脚布局时才会用到该类。
- public abstract void bindViewHolder(BaseViewHolder holder, T itemData, int position)：实现该方法来绑定 UI 和数据。
- public boolean isForViewType(T item, int position)：覆写该方法决定一对多和多对多时，ItemView 与当前数据是否匹配上。多对多时不用关心该方法,一对多时需覆写该方法来决定各布局引入时机。
- public RcvBaseAdapter<T> getRcvBaseAdapter()：获取所属 Adapter

#### EmptyItemView
该布局被添加到 RcvMultipleBaseAdapter 中会被认为是空布局。

#### FooterItemView
该布局被添加到 RcvMultipleBaseAdapter 中会被认为是脚布局，脚布局会按添加顺序倒序展示。

#### HeaderItemView
该布局被添加到 RcvMultipleBaseAdapter 中会被认为是头布局，脚布局会按添加顺序正序展示。

### listener 包

#### IOnItemClickListener<T>

item 点击事件监听器。

#### IOnItemLongClickListener<T>

item 长按时间监听器。

## rcvdecoration 包
封装一些 RecyclerView 常用的 Decoration。

### DividerDecoration
分隔线样式的 Decoration，使用 builder 建造者构建实例。
- public Builder setWidth(int width)：设置分隔线宽度。
- public Builder setColor(int color)：设置分隔线颜色。
- public Builder setMarginLeft(int marginLeft)：设置分隔线距离左边 margin。
- public Builder setMarginTop(int marginTop)：设置分隔线距离顶部 margin。
- public Builder setMarginRight(int marginRight)：设置分隔线距离右边 margin。
- public Builder setMarginBottom(int marginBottom)：设置分隔线距离底部 margin。
- public Builder setShowFirst(boolean showFirst)：设置是否显示第一个 decoration，默认 false。
- public Builder setAppendBottom(boolean appendBottom)：设置是否在底部追加 decoration，默认 false。
- public Builder setOffset(boolean offset)：设置是否 decoration 是否会使 item 偏移，即给 item 增加 padding，默认true。
- public DividerDecoration build()：创建 Decoration 实例。
### GridDividerDecoration
GridLayoutManager 的分隔线样式的 Decoration。

- public Builder setSpanCount(int spanCount)：设置 LayoutManager 的 spanCount。
- public Builder setColumnMargin(int columnMargin)：设置列之间的间距。
- public Builder setLeftColumnMargin(int leftColumnMargin)：设置最左边列的 item 左边的间距，需要在 {@link #setColumnMargin(int)} 之后调用,否则被 {@link #mColumnMargin} 覆盖。
- public Builder setRightColumnMargin(int rightColumnMargin)：设置最右边列的 item 右边的间距，需要在 {@link #setColumnMargin(int)} 之后调用,否则会被 {@link #mColumnMargin} 覆盖。
- public Builder setRowMargin(int rowMargin)：设置行之间的间距。
- public Builder setTopRowMargin(int topRowMargin)：设置第一行的 item 顶部的间距，需要在 {@link #setColumnMargin(int)} 之后调用,否则会被 {@link #setRowMargin} 覆盖。
- public Builder setBottomRowMargin(int bottomRowMargin)：设置最后一行的 item 底部的间距，需要在 {@link #setColumnMargin(int)} 之后调用,否则会被 {@link #setRowMargin} 覆盖。
- public Builder setColor(int color)：设置分隔线颜色。
- public GridDividerDecoration build()：创建 Decoration 实例。

### StickyDecoration
粘性头部的 Decoration
- public abstract String getStickyHeaderName(int position)：覆写该方法来设置每一个 position 的粘性头部的文字。


## util 包
常用工具类

### activityresultutil 包

通常的 startActivityForResult() 方法需要重写 protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) 来监听结果，我们可以通过一个代理 Fragment 来做这件事，并通过回调的方式来实现一个地方调用，异步返回的操作，从而减少重写。

#### ActivityResultUtil

- public static void startActivityForResult(FragmentActivity activity, Class<?> targetClass, OnActivityResultCallBack onActivityResultCallBack)：重载，默认 requestCode 为 200。
- public static void startActivityForResult(FragmentActivity activity, Class<?> targetClass, int requestCode, OnActivityResultCallBack onActivityResultCallBack)：无参启动 Activity 并等待返回值。
- public static void startActivityForResult(FragmentActivity activity, Intent intent, OnActivityResultCallBack onActivityResultCallBack)：重载，默认 requestCode 为 200。
- public static void startActivityForResult(FragmentActivity activity, Intent intent, int requestCode, OnActivityResultCallBack onActivityResultCallBack)：使用自己的 intent(可以传参),启动 Activity 并等待返回值。

### permissionutil 包

Android 6.0 以上部分权限需要动态申请，需要调用 requestPermissions 方法并重写 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) 来监听申请结果，同 ActivitiyResultUtil 一样，也可以使用一个代理 Fragment 来做这件事，通过回调的方式告诉调用者。

#### PermissionUtil

- public static boolean checkPermission(Context context, String permission)：检查是否拥有某个权限。
- public static void requestPermission(FragmentManager fragmentManager, IOnRequestPermissionResultCallBack onRequestPermissionResultCallBack, String... permissions)：申请权限，可同时申请多个

### device 包

设备相关工具类

#### AppUtil

App 相关工具类

- public static String getPackageName(Context context)：获取应用包名。
- public static String getVersionName(Context context)：获取应用版本名称。
- public static long getVersionCode(Context context)：获取应用版本号。
- public static Drawable getIcon(Context context)：获取应用图标。
- public static byte[] getSignature(Context context)：获取应用签名,可自己转为 md5,sha1,sha256 等。
- public static String getLaunchActivity(Context context)：获取启动类全限定名。
- public static void install(Context context, File file, String authority)：安装应用。
- public static void uninstall(Context context, String packageName)：要卸载应用的包名。

#### DeviceUtil

设备相关工具类

- public static String getAndroidVersion()：获取 Android 系统版本。
- public static String getBrand()：获取设备品牌。
- public static String getDevice()：获取设备参数。
- public static String getDisplay()：获取设备当前开发代号。
- public static String getHardware()：获取设备硬件名称。
- public static String getHost()：
- public static String getId()：获取设备 Id。
- public static String getManufacturer()：获取手机制造商。
- public static String getModel()：获取手机型号。
- public static String getProduct()：获取设备的名称。
- public static int getSDKVersion()：获取 SDK 版本。
- public static String getTags()：获取设备描述 Build 的标签。
- public static String getType()：获取设备描述 Build 的类型。
- public static String getUser()：获取设备描述 Build 的 USER。
- public static String getMac(Context context)：获取设备网卡唯一标识。
- public static String getAndroidId(Context context)：获取 AndroidId。
- public static String getDeviceId(Context context)：获取设备唯一标识，通过硬件信息拼的，可能会变化。

#### PhoneUtil

手机相关工具类

- public static String getNetworkOperator(Context context)：获取网络运营商代码,中国移动 46000/46002/46007,中国联通 46001,中国电信 46003。
- public static String getNetworkOperatorName(Context context)：获取网络运营商的名字。
- public static String getNetworkCountryIso(Context context)：获取网络运营商的国家代码。
- public static String getSimOperator(Context context)：获取 SIM 卡运营商的名字。
- public static String getSimOperatorName(Context context)：获取 SIM 卡运营商的代码,中国移动 46000/46002/46007,中国联通 46001,中国电信 46003。
- public static String getSimCountryIso(Context context)：获取 SIM 卡运营商的国家代码。
- public static int getNetworkType(Context context)：获取 SIM 卡网络类型。

#### ScreenUtil

屏幕相关工具类

- public static int dp2px(Context context, float dp)：dp 转为 px。
- public static int sp2px(Context context, float sp)：sp 转为 px。
- public static int px2dp(Context context, float px)：px 转为 dpS。
- public static int px2sp(Context context, float px)：px 转为 sp。
- public static int getScreenWidth(Context context)：获取屏幕宽度,不包括虚拟按键的宽度。
- public static int getScreenHeight(Context context)：获取屏幕高度,不包括虚拟按键的高度,异形屏(刘海屏,水滴屏,挖孔屏)还不包括异形高度。
- public static int getScreenRealWidth(Context context)：获取屏幕真实宽度,包括虚拟按键的高度。
- public static int getScreenRealHeight(Context context)：获取屏幕真实高度,包括虚拟按键的高度。
- public static int getSafeInsetTop(Activity activity)：获取异形屏(刘海屏,水滴屏,挖孔屏)的异形高度,需在 UI 绘制完成后调用。
- public static boolean isPortrait(Context context)：判断是否是竖屏。
- public static boolean isLockScreen(Context context)：判断是否是锁屏状态。

#### SDCardUtil

SD 卡工具类

- public static boolean isMounted()：判断 SD 卡是否挂载。
- public static File getRoot()：获取 SD 卡根路径。
- public static long getTotalSize()：获取 SD 卡总容量。
- public static long getAvailableSize()：获取 SD 卡可用容量。

### file 包

#### EncodeUtil

编解码工具类

- public static String urlEncode(String string)：URL 编码。
- public static String urlDecode(String string)：URL 解码。
- public static String base64Encode(byte[] bytes)：base64 编码,重载 {@link #base64Encode(byte[], String)}。
- public static String base64Encode(byte[] bytes, String header)：base64 编码,重载。
- public static byte[] base64Decode(String base64)：base64 解码。

#### EncryptUtil

加解密工具类

- public static String md5Encrypt(byte[] bytes)：字符串 MD5 计算。
- public static String sha1Encrypt(byte[] bytes)：字符串 SHA-1 计算。
- public static String sha224Encrypt(byte[] bytes)：字符串 SHA-224 计算。
- public static String sha256Encrypt(byte[] bytes)：字符串 SHA-256 计算。
- public static String sha384Encrypt(byte[] bytes)：字符串 SHA-384 计算。
- public static String sha512Encrypt(byte[] bytes)：字符串 SHA-512 计算。
- public static String generateDesSecret()：生成 DES 加密算法的密钥。
- public static String generateAesSecret()：生成 AES 加密算法的密钥。
- public static byte[] desEncrypt(String secret, byte[] encrypt)：DES 对称加密。
- public static byte[] desDecrypt(String secret, byte[] decrypt)：DES 对称解密。
- public static byte[] aesEncrypt(String secret, byte[] encrypt)：AES 对称加密。
- public static byte[] aesDecrypt(String secret, byte[] decrypt)：AES 对称解密。
- public static byte[] rsaEncrypt(boolean isPublicKey, byte[] secret, byte[] encrypt)：RSA 加密。
- public static byte[] rsaDecrypt(boolean isPublicKey, byte[] secret, byte[] decrypt)：RSA 解密。

#### FileUtil

文件相关工具类

- public static byte[] readFile(String path)：读取文件。
- public static boolean writeFile(byte[] bytes, String path)：写入文件。
- public static long getFileSize(File file)：获取文件大小。
- public static long getDirSize(File file)：获取文件夹大小。
- public static int getFileCount(File file)：获取目标文件夹下包含的文件个数。
- public static int getDirCount(File file)：获取目标文件夹下包含的文件夹个数。
- public static List<File> getFileList(File file, String keyword)：根据关键字获取文件列表,重载 {@link #getFileList(File, String, boolean)}。
- public static List<File> getFileList(File file, String keyword, boolean ignoreCase)：根据关键字获取文件列表。
- public static String getFileType(String path)：获取文件类型。
- public static String formatSize(long size)：格式化文件大小,重载 {@link #formatSize(long, String)}。
- public static String formatSize(long size, String contactCharacter)：格式化文件大小。

#### ZipUtil

文件压缩相关工具类

- public static boolean zip(File src, File dst)：压缩。
- public static boolean unzip(File src, File dst)：解压缩。

### log 包

#### ShowLogUtil

日志工具类，当前功能

1. 自动拆分过长日志；
2. 以及 release 版本；

### ui

#### KeyboardUtil

软键盘工具类

- public static void showKeyboard(Context context, EditText editText)：显示软键盘。
- public static void hideKeyboard(Activity activity)：隐藏软键盘。
- public static void hideKeyboard(Context context, EditText editText)：隐藏软键盘。
- public static boolean isKeyboardShown(Activity activity)：判断软键盘是否已显示。

#### NavigationBarUtil

NavigationBar 导航栏工具类

- public static boolean navigationBarIsShow(Activity activity)：判断 NavigationBar 是否显示,对全面屏同样生效。
- public static int getNavigationBarHeight(Context context)：获取 NavigationBar 高度。

#### StatusBarUtil

状态栏工具类

- public static void setStatusBarTranslucent(Window window, boolean translucent)：沉浸式(App 界面延伸到 StatusBar 底下)。
- public static void setStatusBarColor(final Window window, int color, boolean animated)：设置状态栏颜色。
- public static void setStatusBarStyle(Window window, boolean dark)：设置状态栏图标是否为黑色。
- public static void setStatusBarHidden(Window window, boolean hidden)：隐藏状态栏。
- public static void appendStatusBarPadding(View view, int viewHeight)：为顶部控件追加状态栏高度,需在 ui 绘制完成后调用,通常用于沉浸式时。
- public static void removeStatusBarPadding(View view, int viewHeight)：移除追加的状态栏高度。
- public static int getStatusBarHeight(Context context)：获取状态栏高度。

### ActivityManager

Activity 管理者，BaseApplication 中会实例化该类，外界不用创建该工具类实例。

- public Activity getTopActivity()：获取栈顶 Activity。
- public void exitActivityUntilOne(Class<?> clazz)：退出某个 Activity 上的所有 Activity。
- public void exit()：退出所有 Activity。

### CountDownHelper

倒计时工具类

- public CountDownHelper(long millisInFuture, long countDownInterval)：构造方法中决定倒计时总时长和倒计时间隔，单位：ms	。
- public abstract void onTick(long millisUntilFinished)：倒计时间隔回调。
- public abstract void onFinish()：倒计时结束回调。
- public synchronized final void start()：开始倒计时。
- public synchronized final void stop()：停止倒计时。
- public synchronized final void pause()：暂停倒计时,调用{@link #restart()}方法重新开始。
- public synchronized final void restart()：重新开始倒计时。
- public boolean isPause()：是否已暂停倒计时。
- public boolean isStop()：是否已结束倒计时。

### SharedPreferencesHelper

存储 SharedPreferences 的工具类

- public SharedPreferencesHelper(Context context, String name)：构造方法中决定文件名。
- public void putInt(String key, int value)：存 int 类型数据。
- public int getInt(String key)：取 int 类型数据,重载。
- public int getInt(String key, int defaultValue)：取 int 类型数据。
- public void putString(String key, String value)：存 String 类型数据	。
- public String getString(String key)：取 String 类型数据,重载。
- public String getString(String key, String defaultValue)：取 String 类型数据。
- public void putLong(String key, long value)：存 long 类型数据。
- public long getLong(String key)：取 long 类型数据，重载。
- public long getLong(String key, long defaultValue)：取 long 类型数据。
- public void putBoolean(String key, boolean value)：存 boolean 类型数据。
- public boolean getBoolean(String key)：取 boolean 类型数据,重载。
- public boolean getBoolean(String key, boolean defaultValue)：取 boolean 类型数据。
- public void putStringSet(String key, Set<String> value)：存 String 类型的集合数据。
- public Set<String> getStringSet(String key)：取 String 类型集合数据,重载。
- public Set<String> getStringSet(String key, Set<String> defaultValue)：取 String 类型集合数据。
- public boolean contains(String key)：是否有存储某个 key	。
- public void remove(String key)：删除某个 key 对应的数据。
- public void clearAll()：清空所有数据。

## widget 包
常用自定义控件。
### RefreshLayout
下拉刷新、上拉加载控件，继承自 SmartRefreshLayout，只是对 SmartRefreshLayout 的一个简单封装。
### SlideBackLayout
侧滑关闭 Activity 的控件

## ContainerActivity
CommonModule 中有一个 ContainerActivity，它可作为 Fragment 的容器 Activity，一些简单界面可以使用 Fragment 实现,然后由该 Activity 作为容器显示,就不用去 AndroidManifest.xml 中注册 Activity 了。

# 更新日志

- 1.2.5
  1. 增加若干工具类；
  2. 规范部分注释及版本号；
  3. AbsMvpActivity 和 AbsMvpFragment 修改状态栏时默认没有动画效果。
  4. 修改 Activity 管理类的数据结构为 stack；
  5. 修改 crash 日志默认保存位置；
  6. 增加获取权限是否拥有的方法。
package subdragon.ui.com.android_ui.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import subdragon.ui.com.android_ui.R;


/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description:
 */
public class BannerView extends RelativeLayout {

    private BannerViewPager mBannerViewPager;
    private TextView mBannerDesc;//文字
    private LinearLayout mDotContainerView;//指示点
    private Context mContext;
    private BannerAdapter mAdapter;
    private View mBannerBottomView;//底部容器

    private Drawable mIndicatorFcousDrawable;//点选中的Drawable
    private Drawable mIndicatorDefaultDrawable;//点默认的Drawable

    private int mBottomColor = Color.TRANSPARENT;//底部容器颜色默认透明

    private int mCurrentPosition = 0;//当前位置

    private int mDotGravity = 1;//默认在右边

    private int mDotSize = 8;//默认点的大小

    private int mDotDistance = 8;//默认点的间距

    private float mWidthRatio; //宽高比例

    private float mHeightRatio;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //加载布局到这个View里面
        inflate(context, R.layout.banner_ui_layout, this);

        initAttr(attrs);

        initView();

    }

    /**
     * 初始化自定义属性
     *
     * @param attrs
     */
    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        mDotGravity = typedArray.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        mIndicatorFcousDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mIndicatorFcousDrawable == null) {
            //如果没有配置，设置一个默认值
            mIndicatorFcousDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorDefaultDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mIndicatorDefaultDrawable == null) {
            //如果没有配置，设置一个默认值
            mIndicatorDefaultDrawable = new ColorDrawable(Color.WHITE);
        }

        //点的大小
        mDotSize = (int) typedArray.getDimension(R.styleable.BannerView_dotSize, dip2px(8));

        //点的距离
        mDotDistance = (int) typedArray.getDimension(R.styleable.BannerView_dotDistance, dip2px(8));

        //获取底部颜色
        mBottomColor = typedArray.getColor(R.styleable.BannerView_bottomColor, mBottomColor);
        //拿到图片宽高比例
        mWidthRatio = typedArray.getFloat(R.styleable.BannerView_withRatio, mWidthRatio);
        mHeightRatio = typedArray.getFloat(R.styleable.BannerView_heightRatio, mHeightRatio);
        //回收
        typedArray.recycle();
    }

    private void initView() {
        mBannerViewPager = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDesc = (TextView) findViewById(R.id.banner_desc_tv);
        mDotContainerView = (LinearLayout) findViewById(R.id.dot_container);
        mBannerBottomView = findViewById(R.id.rl_bottom_view);
        mBannerBottomView.setBackgroundColor(mBottomColor);//设置底部颜色
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //动态指定宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //计算高度
        int height = (int) (width * mHeightRatio / mWidthRatio);

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        mBannerViewPager.setAdapter(adapter);

        //初始化点容器
        initDotsIndicator();
        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //选中的时候调用
                pageSelect(position);
            }
        });
        //初始化广告描述
        String firstDesc = mAdapter.getBannerDesc(0);
        mBannerDesc.setText(firstDesc);
    }

    /**
     * 页面切换的回调
     *
     * @param position
     */
    private void pageSelect(int position) {
        //切换点的状态
        //把点亮的点关掉
        DotIndicatorView oldIndicatorView = (DotIndicatorView) mDotContainerView.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mIndicatorDefaultDrawable);
        //把当前的点点亮
        mCurrentPosition = position % mAdapter.getCount();
        DotIndicatorView currentIndicatorView = (DotIndicatorView) mDotContainerView.getChildAt(mCurrentPosition);
        currentIndicatorView.setDrawable(mIndicatorFcousDrawable);

        //设置广告位
        String bannerDesc = mAdapter.getBannerDesc(mCurrentPosition);
        mBannerDesc.setText(bannerDesc);
    }

    /**
     * 初始化点的指示器
     */
    private void initDotsIndicator() {
        int count = mAdapter.getCount();

        //设置点的位置
        mDotContainerView.setGravity(getDotGravity());

        for (int i = 0; i < count; i++) {
            //不断的往点的指示器添加圆点
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            //设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(mDotSize), dip2px(mDotSize));
            //设置间隔
            params.leftMargin = params.rightMargin = mDotDistance;
            indicatorView.setLayoutParams(params);
            //设置背景
//            indicatorView.setBackgroundColor(Color.RED);
            if (i == 0) {
                indicatorView.setDrawable(mIndicatorFcousDrawable);
            } else {
                indicatorView.setDrawable(mIndicatorDefaultDrawable);
            }
            mDotContainerView.addView(indicatorView);
        }
    }

    private int getDotGravity() {
        System.out.println("---mDotGravity-->" + mDotGravity);
        switch (mDotGravity) {
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.RIGHT;
            case -1:
                return Gravity.LEFT;
        }
        return Gravity.RIGHT;
    }

    /**
     * dip to px
     *
     * @param dip
     * @return
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    /**
     * 开始滚动
     */
    public void startRoll() {
        mBannerViewPager.startRoll();
    }

    /**
     * 设置页面切换的速率
     *
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
        mBannerViewPager.setScrollerDuration(scrollerDuration);
    }
}

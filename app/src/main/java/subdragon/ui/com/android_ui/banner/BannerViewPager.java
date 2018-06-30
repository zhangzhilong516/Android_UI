package subdragon.ui.com.android_ui.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description:
 */

public class BannerViewPager extends ViewPager {
    // 消息 msg.what
    private static final int SCROLL_MSG = 0x0011;
    private static final String TAG = "BannerViewPager";

    //默认两秒切换
    private int mCutDownTime = 2000;
    //自定义页面切换的Scroller
    private BannerScroller mBannerScroller;
    //自定义Adapter
    private BannerAdapter mAdapter;

    //界面复用
    private List<View> mConvertViews;

    //内存优化，管理Activity的声明周期
    private Activity mActivity;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //每隔多少秒切换图片
            setCurrentItem(getCurrentItem() + 1);
            //不断循环执行
            startRoll();
        }
    };


    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
        //改变ViewPager切换的速率
        //--改变 mScroller.start...的duration
        // 反射获取 mScroller 对象
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            //第一个参数表示当前属性属于那个累
            mBannerScroller = new BannerScroller(context);
            //设置参数
            field.set(this, mBannerScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mConvertViews = new ArrayList<>();
    }

    /**
     * 设置页面切换的速率
     *
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
        mBannerScroller.setScrollerDuration(scrollerDuration);
    }

    /**
     * 设置自定义的BannerAdapter
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        //设置父类Adapter
        setAdapter(new BannerPagerAdapter());

        //注册管理Activity的生命周期
        mActivity.getApplication().registerActivityLifecycleCallbacks(mLifecycleCallbacks);
    }

    /**
     * 实现自动轮播
     */
    public void startRoll() {
        //清除消息
        mHandler.removeMessages(SCROLL_MSG);
        // 默认两秒切换
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
        Log.e(TAG, "startRoll: ");
    }

    /**
     * 销毁Handler，解决内存泄漏问题
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
        // 解除绑定
        mActivity.getApplication().unregisterActivityLifecycleCallbacks(mLifecycleCallbacks);
    }

    /**
     * ViewPager适配器
     */
    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //为了实现无限循环
            return Integer.MAX_VALUE;
        }

        /**
         * 创建ViewPager条目回调
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            //使用Adapter设计模式，为了完全让用户自定义
            // position   0 --> 2的31次方
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount(), getConvertView());
            //添加到VIewPager
            container.addView(bannerItemView);

            //点击回调监听
            bannerItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position % mAdapter.getCount());
                }
            });

            return bannerItemView;
        }


        /**
         * 销毁条目回调方法
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mConvertViews.add((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //官方推荐
            return view == object;
        }
    }

    /**
     * 获取复用界面
     *
     * @return
     */
    private View getConvertView() {
        for (int i = 0; i < mConvertViews.size(); i++) {
            //获取没有添加在ViewPager里面的
            if (mConvertViews.get(i).getParent() != null) {
                return mConvertViews.get(i);
            }
        }
        return null;
    }

    //设置点击回调监听
    private BannerItemClickListener mListener;

    public void setOnBannerItemClickListener(BannerItemClickListener listener) {
        mListener = listener;
    }

    public interface BannerItemClickListener {
        void onItemClick(int position);
    }


    //管理Activity的生命周期
    private Application.ActivityLifecycleCallbacks mLifecycleCallbacks = new DefaultActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(Activity activity) {
            super.onActivityResumed(activity);
            //判断是否监听当前Activity的生命周期
            System.out.println("Acticity --> " + activity + ",context --> " + getContext());
            if (activity == mActivity) {

                //Activity可见就开始轮播
                mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            super.onActivityPaused(activity);
            if (activity == mActivity) {
                //Activity不可见停止轮播
                mHandler.removeMessages(SCROLL_MSG);
            }
        }
    };
}

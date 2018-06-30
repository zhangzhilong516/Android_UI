package subdragon.ui.com.android_ui.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;
/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description:
 */

public class BannerScroller extends Scroller {

    //动画持续时间
    private int mScrollerDuration = 880;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    /**
     * 设置动画持续时间
     *
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
        mScrollerDuration = scrollerDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }
}

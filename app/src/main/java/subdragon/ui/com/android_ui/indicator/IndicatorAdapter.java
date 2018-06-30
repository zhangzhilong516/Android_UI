package subdragon.ui.com.android_ui.indicator;

import android.view.View;
import android.view.ViewGroup;

/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description: 指示器适配器
 */
public abstract class IndicatorAdapter<T extends View> {
    // 获取总共的显示条数
    public abstract int getCount();

    // 根据当前的位置获取View
    public abstract T getView(int position, ViewGroup parent);

    // 高亮当前位置
    public void highLightIndicator(T view) {

    }

    // 重置当前位置
    public void restoreIndicator(T view) {

    }

    // 8.添加底部跟踪的指示器
    public View getBottomTrackView() {
        return null;
    }
}

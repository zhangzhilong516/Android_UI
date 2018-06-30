package subdragon.ui.com.android_ui.banner;

import android.view.View;

/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description:
 */

public abstract class BannerAdapter {

    /**
     * 根据位置获取ViewPager的子View
     *
     * @param position
     * @return
     */
    public abstract View getView(int position, View convertView);

    /**
     * 获取轮播图的数量
     *
     * @return
     */
    public abstract int getCount();

    /**
     * 广告位描述
     *
     * @param position
     * @return
     */
    public String getBannerDesc(int position){
        return "";
    }
}

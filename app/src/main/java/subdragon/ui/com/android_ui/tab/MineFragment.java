package subdragon.ui.com.android_ui.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import subdragon.ui.com.android_ui.R;

/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description:
 */

public class MineFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine,null);
    }
}

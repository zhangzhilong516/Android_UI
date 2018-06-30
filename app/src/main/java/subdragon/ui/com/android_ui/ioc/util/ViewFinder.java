package subdragon.ui.com.android_ui.ioc.util;

import android.app.Activity;
import android.view.View;

public class ViewFinder {
    private View mView;
    private Activity mActivity;
    public ViewFinder(View view){
        this.mView = view;
        this.mActivity = null;
    }

    public ViewFinder(Activity activity){
        this.mActivity = activity;
        this.mView = null;
    }


    public View findViewById(int viewId) {
        if(mView != null){
            return mView.findViewById(viewId);
        }
        return mActivity.findViewById(viewId);
    }
}

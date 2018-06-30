package subdragon.ui.com.android_ui.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import subdragon.ui.com.android_ui.R;
import subdragon.ui.com.android_ui.ioc.util.ViewUtils;
import subdragon.ui.com.android_ui.ioc.view.OnClick;

/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description:
 */

public class TabActivity extends AppCompatActivity{
    private HomeFragment mHomeFragment;
    private NewFragment mNewFragment;
    private FindFragment mFindFragment;
    private MineFragment mMineFragment;

    private FragmentManagerHelper mFragmentManagerHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ViewUtils.inject(this);
        mFragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(),R.id.frame_layout);

        if(mHomeFragment == null){
            mHomeFragment = new HomeFragment();
        }
        mFragmentManagerHelper.add(mHomeFragment);
    }

    @OnClick(R.id.rb_home)
    public void goToHome(){
        if(mHomeFragment == null){
            mHomeFragment = new HomeFragment();
        }
        mFragmentManagerHelper.show(mHomeFragment);
    }
    @OnClick(R.id.rb_new)
    public void goToNew(){
        if(mNewFragment == null){
            mNewFragment = new NewFragment();
        }
        mFragmentManagerHelper.show(mNewFragment);
    }
    @OnClick(R.id.rb_find)
    public void goToFind(){
        if(mFindFragment == null){
            mFindFragment = new FindFragment();
        }
        mFragmentManagerHelper.show(mFindFragment);
    }
    @OnClick(R.id.rb_mine)
    public void goToMine(){
        if(mMineFragment == null){
            mMineFragment = new MineFragment();
        }
        mFragmentManagerHelper.show(mMineFragment);
    }
}

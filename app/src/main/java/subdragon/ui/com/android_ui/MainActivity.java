package subdragon.ui.com.android_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import subdragon.ui.com.android_ui.banner.BannerActivity;
import subdragon.ui.com.android_ui.indicator.IndicatorActivity;
import subdragon.ui.com.android_ui.ioc.util.ViewUtils;
import subdragon.ui.com.android_ui.ioc.view.OnClick;
import subdragon.ui.com.android_ui.tab.TabActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
    }

    @OnClick(R.id.btn_tab)
    public void toTab(View view){
        startActivity(TabActivity.class);
    }

    @OnClick(R.id.btn_banner)
    public void toBanner(View view){
        startActivity(BannerActivity.class);
    }


    @OnClick(R.id.btn_indicator)
    public void toIndicator(View view){
        startActivity(IndicatorActivity.class);
    }


    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}

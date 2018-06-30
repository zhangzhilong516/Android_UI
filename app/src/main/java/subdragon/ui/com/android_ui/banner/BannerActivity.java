package subdragon.ui.com.android_ui.banner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import subdragon.ui.com.android_ui.R;

public class BannerActivity extends AppCompatActivity {

    private BannerView mBannerView;

    // 图片宽高为  498/623
    String[] imgs = {
            "https://ws1.sinaimg.cn/large/610dc034ly1fj78mpyvubj20u011idjg.jpg",
            "https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg",
            "https://ws1.sinaimg.cn/large/610dc034ly1fitcjyruajj20u011h412.jpg"
    };

    String[] desc = {
            "Invincible",
            "Concert",
            "Tour"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);


        mBannerView = (BannerView) findViewById(R.id.bannerView);
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView bannerIv = null;
                if (convertView == null) {
                    bannerIv = new ImageView(BannerActivity.this);
                } else {
                    bannerIv = (ImageView) convertView;
                }


                Glide.with(BannerActivity.this)
                        .load(imgs[position])
                        .centerCrop()
                        .into(bannerIv);

                return bannerIv;
            }

            @Override
            public int getCount() {
                return imgs.length;
            }

            public String getBannerDesc(int position) {
                return desc[position];
            }
        });

        //自动轮播
        mBannerView.startRoll();
        //设置图片切换的速率
        mBannerView.setScrollerDuration(1000);
    }
}

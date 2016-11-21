package dianruisdk.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import dianruisdk.service.DataServices;
import dianruisdk.service.ParamInfo;


/**
 *
 * Created by j on 2016/11/9.
 */

public class DianruiCenterFragment extends BaseFragment  {


    private Button btCountDown;
    private Button button;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //获取手机屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;

        //根
        RelativeLayout rlAll = new RelativeLayout(getActivity());
        rlAll.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
//        rlAll.setBackgroundColor(Color.RED);

        //广告
        RelativeLayout rlAd = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeigh / 2);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//        rlAd.setBackgroundColor(Color.BLUE);
        rlAll.addView(rlAd,lp);

        //关闭
        button = new Button(getActivity());
        button.setText("关闭(15)");
        button.setVisibility(View.GONE);
        button.setTextSize(13);
        button.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams lpButton = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        button.setBackgroundColor(Color.BLACK);
        rlAd.addView(button,lpButton);

        //广告的点击
        rlAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterBrowser(params.gotourl);
                destroy();
            }
        });

        //关闭的点击
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroy();
            }
        });

        //显示图片
        Bitmap bitmap = (Bitmap) this.getArguments().getParcelable("bitmap");
        params = (ParamInfo) this.getArguments().getSerializable("params");
        rlAd.setBackground(new BitmapDrawable(rlAd.getResources(), bitmap));
        new Thread(new mThread()).start();
        DataServices.getInstance().notifyClickReq(this.params.planid, 0, this.params.getImageTJ);
        return rlAll;
    }

    /*
    开启子线程实现倒计时效果
     */
    private class mThread implements Runnable {
        int overTime = 20;
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            overTime--;
                            button.setText("关闭" + "(" + overTime + ")");
                            if (overTime == 15) {
                                button.setVisibility(View.VISIBLE);
                            }
                            if (overTime < 0) {
                               DianruiCenterFragment.this.destroy();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

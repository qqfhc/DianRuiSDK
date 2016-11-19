package dianruisdk.fragment;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import dianruisdk.service.DataServices;
import dianruisdk.service.LoadingAdData;
import dianruisdk.service.ParamInfo;
import dianruisdk.utils.ImageLoading;
import dianruisdk.utils.Target;


/**
 * 广告条显示，关闭按钮在外面
 * Created by j on 2016/10/12.
 */
public class DianruiBannerFragment extends BaseFragment  {
    boolean flag = true;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //获取手机屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeigh = dm.heightPixels;

        //根LL
        LinearLayout layout = new LinearLayout(getActivity());
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.setLayoutParams(layoutParams1);//设置根布局的宽高属性以及margin等
        layout.setOrientation(LinearLayout.VERTICAL);//设置线性布局的方向

        //隐藏
        final RelativeLayout in_relativeLayout = new RelativeLayout(getActivity());
        in_relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT, screenHeigh/50));
        in_relativeLayout.setBackgroundColor(android.graphics.Color.parseColor("#00000000"));//设置背景色
        layout.addView(in_relativeLayout);

        //关闭
        final ImageView imageView = new ImageView(getActivity());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenHeigh/50,screenHeigh/50);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        imageView.setImageResource(R.mipmap.close);
        new ImageLoading().showImg("http://img.jjtxtx.com/sdk/close.png").execute(new Target() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onBitmapLoaded(final Bitmap bitmap) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
        imageView.setLayoutParams(layoutParams);
        imageView.setVisibility(View.GONE);
        in_relativeLayout.addView(imageView);

        //广告
        final RelativeLayout guanggao_relativeLayout = new RelativeLayout(getActivity());
        guanggao_relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT, screenHeigh/7));
        guanggao_relativeLayout.setBackgroundColor(android.graphics.Color.parseColor("#111111"));//设置背景色
        layout.addView(guanggao_relativeLayout);

        Bitmap bitmap = (Bitmap) this.getArguments().getParcelable("bitmap");
        params = (ParamInfo) this.getArguments().getSerializable("params");
        guanggao_relativeLayout.setBackground(new BitmapDrawable(guanggao_relativeLayout.getResources(), bitmap));


        //图片点击
        guanggao_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterBrowser(params.gotourl);
                DataServices.getInstance().notifyClickReq(params.planid, 1, params.getImageTJ);
            }
        });

        //关闭按钮点击
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击关闭按钮
                if (params.errrate == 1) {
                    enterBrowser(params.gotourl);
                    DataServices.getInstance().notifyClickReq(params.planid, 1, "0");
                }
                destroy();
                flag = false;
            }
        });

        //隐藏区域点击
        in_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (params.extrate == 1) {
                    enterBrowser(params.gotourl);
                    DataServices.getInstance().notifyClickReq(params.planid, 1, "0");
                }
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(3000);
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setVisibility(View.VISIBLE);
                            }
                        });

                        Thread.sleep(7000);
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setVisibility(View.GONE);
                            }
                        });
                        LoadingAdData.showAd(mActivity, guanggao_relativeLayout, 2 + "");
                        String imageTJ = LoadingAdData.getImageTJ;
                        int planid = LoadingAdData.planid;
                        System.out.println(">>>>>>>>>>>"+imageTJ+planid);
                        if (imageTJ != null && planid != 0) {
                            DataServices.getInstance().notifyClickReq(planid, 0, imageTJ);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        DataServices.getInstance().notifyClickReq(DianruiBannerFragment.this.params.planid, 0, DianruiBannerFragment.this.params.getImageTJ);
        return layout;
    }
}
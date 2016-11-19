package dianruisdk.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import dianruisdk.service.DataServices;
import dianruisdk.service.ParamInfo;

/**
 * 开启界面填充满屏幕广告，倒计时效果
 * <p>
 * Created by j on 2016/10/12.
 */
public class DianruiFullFragment extends BaseFragment  {

    private RelativeLayout rl;
    private Button button;
    private int isexist;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isexist = 1;
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //根
        rl = new RelativeLayout(getActivity());
        rl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rl.setBackgroundColor(Color.YELLOW);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        //关闭
        button = new Button(getActivity());
        button.setBackgroundColor(Color.TRANSPARENT);
        rl.addView(button, lp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroy();
            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterBrowser(params.gotourl);
                DataServices.getInstance().notifyClickReq(params.planid, 1, params.getImageTJ);
                destroy();
            }
        });
        params = (ParamInfo) this.getArguments().getSerializable("params");
        showView();
        return rl;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showView() {
        Bitmap bd = (Bitmap) this.getArguments().getParcelable("bitmap");
        rl.setBackground(new BitmapDrawable(rl.getResources(), bd));

        new Thread(new Runnable() {
            int time = 10;
            int passTime = 3;

            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        if (passTime > 0) {
                            passTime--;
                            System.out.println("passtime:" + passTime);
                        }
                        if (passTime <= 0) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (button.getVisibility() == View.GONE)
                                        button.setVisibility(View.VISIBLE);
                                    button.setText("关闭" + "(" + time + ")");
                                    button.setTextColor(Color.WHITE);
                                }
                            });
                            time--;
                            if (time < 0 && isexist==1) {
                                DianruiFullFragment.this.destroy();
                                break;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        isexist=2;
        super.onDestroy();
    }
}

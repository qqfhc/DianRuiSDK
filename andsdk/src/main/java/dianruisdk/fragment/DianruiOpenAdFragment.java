package dianruisdk.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dianrui.andsdk.R;

import dianruisdk.utils.ImageLoading;
import dianruisdk.utils.Target;


/**
 *
 * Created by j on 2016/10/28.
 */

public class DianruiOpenAdFragment extends BaseFragment {


    private long exitTime = 0;
    private String gotourl;
    private WebView web_view;
    private TextView tv_title_webview;

    public DianruiOpenAdFragment() {
    }

    @SuppressLint("ValidFragment")
    public DianruiOpenAdFragment(String gotourl) {
        this.gotourl = gotourl;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //获取手机屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        //根
        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams1);//设置根布局的宽高属性以及margin等
        layout.setOrientation(LinearLayout.VERTICAL);//设置线性布局的方向

        final RelativeLayout in_relativeLayout = new RelativeLayout(getActivity());
        in_relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, screenHeigh/13));
        in_relativeLayout.setBackgroundColor(android.graphics.Color.parseColor("#0099ff"));//设置背景色
        layout.addView(in_relativeLayout);

        final Button button_back = new Button(getActivity());
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(screenHeigh/20,screenHeigh/20 );
        layoutParams5.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        button_back.setBackgroundResource(R.mipmap.back);
        new ImageLoading().showImg("http://img.jjtxtx.com/sdk/back.png").execute(new Target() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onBitmapLoaded(Bitmap bitmap) {
                button_back.setBackground(new BitmapDrawable(bitmap));
//                button_back.setBackgroundResource();
            }
        });
        layoutParams5.setMargins(screenWidth/50,0,0,0);
        layoutParams5.addRule(RelativeLayout.CENTER_VERTICAL);
        in_relativeLayout.addView(button_back,layoutParams5);

         tv_title_webview = new TextView(getActivity());
        RelativeLayout.LayoutParams text_relative_layout_left = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        text_relative_layout_left.addRule(RelativeLayout.CENTER_IN_PARENT);//设置居中
        text_relative_layout_left.setMargins(screenWidth/8,0,screenWidth/10,0);
        tv_title_webview.setText("点锐移动广告平台");
        tv_title_webview.setEllipsize(TextUtils.TruncateAt.END);
        tv_title_webview.setMaxEms(12);
        tv_title_webview.setTextSize(18);
        tv_title_webview.setTextColor(Color.parseColor("#ffffff"));
        tv_title_webview.setMaxLines(1);
        tv_title_webview.setLayoutParams(text_relative_layout_left);
        in_relativeLayout.addView(tv_title_webview);//添加到父布局中

        web_view = new WebView(getActivity());
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        web_view.setLayoutParams(layoutParams3);
        layout.addView(web_view);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroy();
            }
        });


        initData();
        return  layout;
    }

    /**
     * 执行数据的加载
     */

    protected void initData() {
        WebSettings settings = web_view.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        web_view.loadUrl(gotourl);
        web_view.setWebChromeClient(new WebChromeClient() {
            /*
            设置title为url获取到的title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tv_title_webview.setText(title);
            }
        });

        web_view.setWebViewClient(new WebViewClient() {
            //在webview里打开新链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}

package dianruisdk.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dianruisdk.fragment.DianruiOpenAdFragment;
import dianruisdk.net.HttpClient;
import dianruisdk.net.HttpEncrypt;
import dianruisdk.net.ICall;
import dianruisdk.service.DataServices;

/**
 * @author fanhongchang E-mail: m13393111908_2@163.com
 *         Created by lx on 2016/11/17.
 */
public abstract class DianRuiADAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Activity activity;
    public int i = 5;
    public int j = 3;
    String gotourl;
    private RelativeLayout mView;
    private final int screenHeigh;
    private int planid;
    private String getImageTJ;
    private String getImageTJ1;

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public DianRuiADAdapter(Context context) {
        this.activity = (Activity) context;
        //获取手机屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenHeigh = dm.heightPixels;
    }

    @Override
    public int getItemViewType(int position) {
        if ((position - j + 1 + i) % i == 0 && position != 0) {
            return 111111222;
        } else {
            return getitemViewType(position);
        }
    }

    public abstract int getitemViewType(int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 111111222) {
            mView = new RelativeLayout(activity);
            mView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, screenHeigh / 5));
            return new DianRuiADAdapter.Viewholer(mView);
        } else {
            return oncreateViewHolder(parent, viewType);
        }
    }

    public abstract RecyclerView.ViewHolder oncreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DianRuiADAdapter.Viewholer) {

            Map<String, String> map = new HashMap<>();
            map.put("ad_type", 4 + "");
            new HttpClient().get("http://sdk.bccyyc.com/api.php?" + HttpEncrypt.getSecReqUrl(map)).execute(new ICall() {
                @Override
                public void onComplete(String content) throws JSONException {
                    JSONObject jb = new JSONObject(content);
                    String imgurl = jb.getString("imgurl");
                    gotourl = jb.getString("gotourl");
                    planid = jb.getInt("planid");
                    getImageTJ1 = jb.getString("getImageTJ");

                    new ImageLoading().showImg(imgurl).execute(new Target() {
                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((Viewholer) holder).imageView.setImageBitmap(bitmap);
                                    ((Viewholer) holder).imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    //zhanshiyici
                                    DataServices.getInstance().notifyClickReq(planid, 0, getImageTJ1);
                                }
                            });
                        }
                    });
                }

                @Override
                public void onError(String errorInfo) {

                }
            });

        } else {
            onbindViewHolder(holder, position);
        }
    }

    public abstract void onbindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return getitemCount();
    }

    public abstract int getitemCount();

    class Viewholer extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public Viewholer(RelativeLayout view) {
            super(view);
            imageView = new ImageView(activity);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, screenHeigh / 5);
            imageView.setBackgroundColor(Color.WHITE);
            imageView.setLayoutParams(layoutParams);
            view.addView(imageView);

            textView = new TextView(activity);
            RelativeLayout.LayoutParams layoutParamstv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, screenHeigh / 30);
            layoutParamstv.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            textView.setText("广告");
            textView.setLayoutParams(layoutParamstv);
            view.addView(textView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FragmentManager fragmentManager = activity.getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(android.R.id.content, new DianruiOpenAdFragment(gotourl));
                            fragmentTransaction.commit();
                            DataServices.getInstance().notifyClickReq(planid, 1, getImageTJ1);
                        }
                    });
                }
            });
        }
    }

}

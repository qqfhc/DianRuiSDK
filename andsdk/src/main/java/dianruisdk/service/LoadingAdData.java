package dianruisdk.service;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dianruisdk.net.HttpClient;
import dianruisdk.net.HttpEncrypt;
import dianruisdk.net.ICall;
import dianruisdk.utils.ImageLoading;
import dianruisdk.utils.Target;


/**
 *
 * Created by j on 2016/10/12.
 */
public class LoadingAdData {

    public static String getImageTJ;
    public static int planid;
    public static String gotourl;

    public static void showAd(final Activity activity, final ViewGroup viewGroup, String adType) {

        Map<String, String> map = new HashMap<>();
        map.put("ad_type", adType);
        new HttpClient().get( "http://sdk.bccyyc.com/api.php?"+HttpEncrypt.getSecReqUrl(map)).execute(new ICall() {
            @Override
            public void onComplete(String content) {
                try {
                    JSONObject jb = new JSONObject(content);
                    String imgurl = (String) jb.get("imgurl");
                    gotourl = (String) jb.get("gotourl");
                    getImageTJ = (String) jb.get("getImageTJ");
                    planid = jb.getInt("planid");
                    int succ = (int) jb.get("succ");
                    if (succ == 1) {
                        new ImageLoading().showImg(imgurl).execute(new Target() {
                            @Override
                            public void onBitmapLoaded(final Bitmap bitmap) {
                                if (activity != null) {
                                    activity.runOnUiThread(new Runnable() {
                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void run() {
                                            viewGroup.setBackground(new BitmapDrawable(viewGroup.getResources(), bitmap));

                                        }
                                    });
                                }

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String errorInfo) {

            }
        });
    }
}

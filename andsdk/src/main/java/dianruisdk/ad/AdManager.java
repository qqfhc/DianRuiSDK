package dianruisdk.ad;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import dianruisdk.constant.DRSDKConfig;
import dianruisdk.fragment.DianruiBannerFragment;
import dianruisdk.fragment.DianruiBannerInFragment;
import dianruisdk.fragment.DianruiCenterFragment;
import dianruisdk.fragment.DianruiEmbedFragment;
import dianruisdk.fragment.DianruiFullFragment;
import dianruisdk.net.HttpEncrypt;
import dianruisdk.safe.PhoneManager;
import dianruisdk.service.DataServices;
import dianruisdk.service.ParamInfo;

/**
 *
 * Created by j on 2016/10/31.
 */
public class AdManager
{
    private static Map<Integer,Class> dic = new HashMap<Integer, Class>() ;
    public static void setAppKey(Context context, String key)
    {

        String device_id = PhoneManager.getPhoneIMSI(context);
        String sim_number = PhoneManager.getPhoneIMEI(context);
        String product = Build.PRODUCT;
        String model = Build.MODEL;

        dic.put(2,DianruiBannerFragment.class) ;//底部横幅
        dic.put(3,DianruiCenterFragment.class) ;//中间
        dic.put(4,DianruiEmbedFragment.class) ;//贴片
        dic.put(5, DianruiFullFragment.class) ;//全屏显示
        dic.put(6, DianruiBannerInFragment.class); //关闭按钮在里面

        HttpEncrypt.init(key, device_id, product, sim_number, model, DRSDKConfig.sdk_ver);
    }


    private static void addViewFrame(final Activity activity, final int id, final Fragment fragment)
    {
        final FragmentManager fragmentManager = activity.getFragmentManager();
        final FragmentTransaction framentTransaction = fragmentManager.beginTransaction();
        framentTransaction.add(id, fragment);
        framentTransaction.commit();
    }

    /**
     * 贴片广告
     * @param activity
     * @param id
     */
    public static void showEmbed(final Activity activity, final int id)
    {
        show(activity,id,4,4);//贴片
    }

    /**
     * 底部横幅，关闭按钮在外面
     * @param activity
     * @param
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void showBottom(final Activity activity)
    {

        ViewGroup v1 = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup content = (ViewGroup) v1.findViewById(android.R.id.content);
        content.setPadding(0,0,0,0);
        LinearLayout l = new LinearLayout(activity);
        l.setId(View.generateViewId());
        int id = l.getId();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;


        content.addView(l,lp);
        show(activity,id,2,2);//横幅
    }

    /**
     * 中间插屏
     * @param activity
     * @param
     */
    public static void showCenter(final Activity activity)
    {

        show(activity,android.R.id.content,3,3);
    }

    /**
     * 底部横幅关闭显示在里面
     * @param activity
     * @param
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static  void showBottomIn(final Activity activity)
    {

        ViewGroup v1 = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup content = (ViewGroup) v1.findViewById(android.R.id.content);
        content.setPadding(0,0,0,0);
        LinearLayout l = new LinearLayout(activity);
        l.setId(View.generateViewId());
        int id = l.getId();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        content.addView(l,lp);
        show(activity,id,2,6);
    }




    /**
     * 全屏广告
     * @param activity
     * @param
     */
    public static void showFull(final Activity activity)
    {

        show(activity,android.R.id.content,3,5);
    }


    /**
     *
     * @param activity
     * @param id
     * @param ad_type 广告类型 2、3、4
     * @param view_type 显示类型
     */
    private static void show(final Activity activity, final int id, final int ad_type, final int view_type)
    {
        if(!dic.containsKey(view_type)){
            Log.e("TypeError","The ad type is error , please check !") ;
            return;
        }
        Map<String,String> map = new HashMap<String,String>() ;
        map.put("ad_type", ad_type+"");

        Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what==1)
                {
                    Bitmap bitmap = (Bitmap) msg.obj ;
                    Class c = dic.get(view_type) ;
                    try {
                        Fragment fragment=(Fragment) c.newInstance() ;
                        Bundle args = new Bundle() ;
                        ParamInfo info = (ParamInfo) msg.getData().getSerializable("params");
                        assert info != null;
                        int code = info.code;

                        //返回码是111，不加载广告
                        if (code != 111) {
                            args.putParcelable("bitmap",bitmap);
                            args.putSerializable("params",msg.getData().getSerializable("params"));
                            fragment.setArguments(args);
                            addViewFrame(activity,id,fragment);
                        }

                    }
                    catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        DataServices.getInstance().getAdData(map,handler);
    }
}

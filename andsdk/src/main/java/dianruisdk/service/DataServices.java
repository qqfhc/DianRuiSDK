package dianruisdk.service;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
 * Created by luqinglong on 2016/11/10.
 * Email :23503458@qq.com
 */

public class DataServices
{
    private static  DataServices services = null ;
    public static DataServices getInstance()
    {
        if(services==null){
            services = new DataServices() ;
        }
        return services ;
    }

    /**
     * 点击通知服务器
     * planid 计划ID
     * click :1 正常点击  0 :误操作
     * @param
     */
    public void notifyClickReq(int planid,int click,String getImageTJ)
    {
        Map<String,String> params = new HashMap<String,String>() ;
        params.put("planid",planid+"") ;
        params.put("click",click+"") ;
        params.put("getImageTJ",getImageTJ) ;
        new HttpClient()
                .get("http://sdk.bccyyc.com/reapi.php?" + HttpEncrypt.getSecReqUrl(params))
                .execute(new ICall() {
                    @Override
                    public void onComplete(String content) {
                        try {
                            JSONObject jb = new JSONObject(content);
                            Log.i("notifyClickReq:",content) ;
                            final ParamInfo info = new ParamInfo(jb);
                            if (info.succ == 1) {
                                Log.i("notifyClickReq:","the notify click complete.") ;
                            }
                            else
                            {
                                Log.e("sdk","return data error") ;
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Log.e("the json error>>>",content) ;
                        }
                    }

                    @Override
                    public void onError(String errorInfo) {
                    }
                });
    }


    /**
     *       Map<String, String> map = new HashMap<>();
     map.put("ad_type", adType);
     * @param params
     */
    public void getAdData(Map<String,String> params, final Handler handler)
    {

        new HttpClient()
                .get("http://sdk.bccyyc.com/api.php?" + HttpEncrypt.getSecReqUrl(params))
                .execute(new ICall() {
            @Override
            public void onComplete(String content) {
                try {
                    JSONObject jb = new JSONObject(content);
                    Log.i("the req json data :",content) ;
                    final ParamInfo info = new ParamInfo(jb);

                    if (info.succ == 1) {
                        new ImageLoading().showImg(info.imgurl).execute(new Target()
                        {
                            @Override
                            public void onBitmapLoaded(final Bitmap bitmap)
                            {
                                Message msg = Message.obtain() ;
                                msg.obj = bitmap ;
                                msg.what = 1 ;
                                Bundle arg = new Bundle() ;
                                arg.putSerializable("params",info);
                                msg.setData(arg);
                                handler.sendMessage(msg) ;
                            }
                        });
                    }
                    else
                    {
                        Log.e("sdk","return data error") ;
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("the json error>>>",content) ;
                }
            }

            @Override
            public void onError(String errorInfo) {

            }
        });
    }

}

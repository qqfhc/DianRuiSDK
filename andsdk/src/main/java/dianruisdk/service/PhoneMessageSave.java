package dianruisdk.service;

import android.content.Context;
import android.content.SharedPreferences;

import dianruisdk.safe.PhoneManager;

import static android.content.Context.MODE_PRIVATE;

/**
 *  获取手机信息保存到 sp和 服务器
 * Created by j on 2016/10/12.
 */

public class PhoneMessageSave {

    public static void save2Sp(Context context) {
//        //1，获取手机的真实性
//        Boolean phoneTruths = PhoneManager.getPhoneTruths(context);
//        Log.i("真机 " ,phoneTruths.toString());
        //2，获取到真机，然后就获取真机的信息

        String phoneUUID = PhoneManager.getPhoneUUID(context);
        String phoneIMEI = PhoneManager.getPhoneIMEI(context);
        String phoneIMSI = PhoneManager.getPhoneIMSI(context);
        long time = System.currentTimeMillis();

        //把获取到的数据连接存储到sp
        SharedPreferences sp = context.getSharedPreferences("phoneMessage", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("UUID",phoneUUID);
        edit.putString("IMEI",phoneIMEI);
        edit.putString("IMSI",phoneIMSI);
        edit.putLong("TIME",time);
        edit.commit();

    }

    public static void save2Service(String json) {
//        OkHttpUtils
//                .postString()
//                .url(DRSDKConfig.SERVER_BASE_URL+DRSDKConfig.CONN_URL)
//                .content(json)
//                .build()
//                .execute(new MyStringCallback());
//    }
//
//    private static class MyStringCallback extends Callback {
//
//        /**
//         * Thread Pool Thread
//         *
//         * @param response
//         * @param id
//         */
//        @Override
//        public Object parseNetworkResponse(Response response, int id) throws Exception {
//            return null;
//        }
//
//        @Override
//        public void onError(Call call, Exception e, int id) {
//            Log.i("上传错误",call.request().toString()+" ;"+id);
//        }
//
//        @Override
//        public void onResponse(Object response, int id) {
//            Log.i("上传成功", response.toString());
//        }
    }
}

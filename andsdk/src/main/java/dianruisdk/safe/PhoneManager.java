package dianruisdk.safe;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 *   获取手机的几个唯一码
 * Created by j on 2016/9/23.
 */
public class PhoneManager {

//    /*
//    判断是不是手机，true是真手机
//     */
//    public static Boolean getPhoneTruths(Context context) {
//        //Boolean emulator = AntiEmulator.CheckEmulator(context);
//        return AntiEmulator.CheckEmulator(context);
//    }

    /*
    获取手机的UUID
     */
    public static String getPhoneUUID(Context context) {
        if (context == null) {
            return null;
        }
        return new DeviceUuidFactory(context).getDeviceUuid().toString();
    }

    /*
    获取手机的获取手机的DEVICE_ID
     */

    public static String getPhoneIMEI(Context context) {
        TelephonyManager tm = null;
        if (context == null){
            return null;
        }
        tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();

    }

    /*
    获取手机的IMSI
     */

    public static String getPhoneIMSI(Context context) {
        TelephonyManager tm = null;
        if (context == null) {
            return null;
        }
        tm= (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }
}

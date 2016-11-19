package dianruisdk.safe;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.io.File;

/**
 *  判断手机的真实性，true是手机，false是模拟器
 *
 * Created by j on 2016/9/23.
 */
public class AntiEmulator {

    public static Boolean CheckEmulator(Context context) {
        return AntiEmulator.CheckImsi(context) && AntiEmulator.CheckImsiIDS(context)
                && AntiEmulator.CheckPhoneNumber(context) && AntiEmulator.CheckEmulatorBuild()
                && AntiEmulator.CheckOperatorNameAndroid(context) && AntiEmulator.checkPipes();
    }

    /*
    判断imsi卡
     */
    private static String []simulatorImsi = {"310260000000000"};
    private static   Boolean CheckImsi(Context context) {
        //TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELECOM_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager == null) {
            return true;//x
        }
        String imsi = telephonyManager.getSubscriberId();
        for (String simulatorImsi_id : simulatorImsi)
            if (simulatorImsi_id.equals(imsi)){
                return false;
            }
        return true;
    }

    /*
    判断imei卡
     */
    private static String [] simulatorImei = {"000000000000000"};
    private static   Boolean CheckImsiIDS(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return true;
        }
        try {
            String imsi_ids = telephonyManager.getDeviceId();
            for (String know_imsi : simulatorImei) {
                if (know_imsi.equalsIgnoreCase(imsi_ids)) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
    判断手机号码
     */
    private static String[] known_numbers = {"15555215554", "15555215556",
            "15555215558", "15555215560", "15555215562", "15555215564",
            "15555215566", "15555215568", "15555215570", "15555215572",
            "15555215574", "15555215576", "15555215578", "15555215580",
            "15555215582", "15555215584",};

    private static Boolean CheckPhoneNumber(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return true;
        }
        try {
            String phonenumber = telephonyManager.getLine1Number();

            for (String number : known_numbers) {
                if (number.equalsIgnoreCase(phonenumber)) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /*
    判断硬件信息
     */
    private static Boolean CheckEmulatorBuild() {
        try {

            String PRODUCT = android.os.Build.PRODUCT;
            String CPU_ABI = android.os.Build.CPU_ABI;
            String TAGS = android.os.Build.TAGS;
            String MODEL = android.os.Build.MODEL;
            String DEVICE = android.os.Build.DEVICE;
            String BRAND = android.os.Build.BRAND;
            String HARDWARE = android.os.Build.HARDWARE;
            if ("sdk_google_phone_x86".equals(PRODUCT) || "x86".equals(CPU_ABI)
                    || "text-keys".equals(TAGS) || "generic_x86".equals(DEVICE)
                    || "Android SDK built for x86".equals(MODEL) || "android".equals(BRAND) || "ranchu".equals(HARDWARE)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /*
        获取运营商的名字，是模拟器返回Android  
     */
    private static boolean CheckOperatorNameAndroid(Context context) {
        TelephonyManager telephonyManager = ((TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE));
        String szOperatorName = telephonyManager.getNetworkOperatorName();
        if (telephonyManager == null) {
            return true;
        }
        try {
            if (szOperatorName.toLowerCase().equals("android")) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /*

     */

    private static String[] known_pipes =
            {
                    "/dev/socket/qemud",
                    "/dev/qemu_pipe"
            };

    private static boolean checkPipes() {
        for (String pipes : known_pipes) {
            File qemu_socket = new File(pipes);
            if (qemu_socket.exists()) {
                return false;
            }
        }
        return true;
    }
}

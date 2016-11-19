package dianruisdk.net;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dianruisdk.utils.Base64Encode;
import dianruisdk.utils.MD5Util;

/**
 * Created by luqinglong on 2016/11/8.
 * Email :23503458@qq.com
 */
public class HttpEncrypt
{
    public static String app_key;//app key，后台生成
    public static String device_id ;//设备ID
    public static String product ;//产品名称
    public static String sim_number ;//sim卡号码
    public static String model ;//手机型号
    public static String sdk_ver ;//sdk 版本
    public static void init(String app_key,String device_id,String product,String sim_number,String model,String sdk_ver)
    {
        HttpEncrypt.app_key = app_key ;
        if(device_id==null || device_id.length()==0)
        {
            device_id = "unknow" ;
        }
        if(product== null || product.length()==0)
        {
            product = "unknow" ;
        }
        if(sim_number==null || sim_number.length()==0)
        {
            sim_number = "unknow" ;
        }
        if(sdk_ver==null || sdk_ver.length()==0)
        {
            sdk_ver = "unknow" ;
        }
        if(model==null || model.length()==0){
            model = "unknow" ;
        }
        HttpEncrypt.device_id = device_id ;
        HttpEncrypt.product = product ;
        HttpEncrypt.sim_number = sim_number ;
        HttpEncrypt.sdk_ver = sdk_ver ;
        HttpEncrypt.model = model ;
    }

    /**
     * 获取加密之后的地址
     * @param params
     * @return
     */
    public static String getSecReqUrl(Map<String,String> params)
    {

        String rm = String.valueOf(getTime()) ;
        String ts = rm.substring(0,5) ;
        System.out.println("随机数[0,5]:"+ts);
        List<String> list = new ArrayList<String>() ;
        list.add(app_key);
        list.add(device_id);
        list.add(product);
        list.add(sim_number);
        list.add(model);
        list.add(sdk_ver);
        list.add(ts);

        for(String key: params.keySet())
        {
            list.add(params.get(key)) ;
        }
        String[] args = new String[list.size()];
        for(int i=0;i<list.size();i++){
            args[i]= list.get(i) ;
        }
        Arrays.sort(args);
        StringBuffer buffer = new StringBuffer() ;
        for(int i=0;i<args.length;i++)
        {
            buffer.append(args[i]) ;
        }
        String md5 = MD5Util.MD5(buffer.toString()).toLowerCase() ;
        JSONObject obj = new JSONObject() ;
        try {
            obj.put("app_key",app_key) ;
            obj.put("device_id",device_id) ;
            obj.put("product",product) ;
            obj.put("sim_number",sim_number) ;
            obj.put("model",model) ;
            obj.put("sdk_ver",sdk_ver) ;
            for(String key: params.keySet())
            {
                obj.put(key,params.get(key)) ;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        String value = obj.toString();
        System.out.println("params json value is :"+value);

        try {
            value = Base64Encode.encode(value.getBytes("UTF-8")) ;
            System.out.println("Base64>>>>:"+value) ;
            value = URLEncoder.encode(value,"UTF-8") ;
            System.out.println("encode>>>>:"+value) ;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        System.out.println("send MD5:"+md5);
        String url = "s="+md5+"&t="+rm+"&k="+value ;
        System.out.println("URL address is :"+url) ;
        return url ;
    }

    public static long getTime()
    {
        return new Date().getTime()/1000 ;
    }

    public static void main(String[] args) throws JSONException {

        HttpEncrypt.init("323c043ecf234fd40363e3a99b218d7a", "hexcnfn5801708221s", "1", "4", "5", "a") ;
        Map<String,String> map= new HashMap<String,String>();
        map.put("ad_type","2") ;
        String url = "http://sdk.bccyyc.com/api.php?"+ HttpEncrypt.getSecReqUrl(map) ;
        new HttpClient().get(url).execute(new ICall() {
            @Override
            public void onComplete(String content) {
                System.out.println("测试的结果"+content);
            }

            @Override
            public void onError(String errorInfo) {

            }
        });
        System.out.println("url:"+url);

    }
}

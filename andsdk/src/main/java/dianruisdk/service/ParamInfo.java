package dianruisdk.service;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by luqinglong on 2016/11/10.
 * Email :23503458@qq.com
 */

public class ParamInfo implements Serializable
{

    public int exnum ;
    public int fullrate ;
    public int errrate ;
    public int extrate ;
    public int succ ;
    public int code ;
    public String imgurl ;
    public String gotourl ;
    public String getImageTJ ;
    public int planid ;


    /**
     *  fullrate 全屏点击
        errrate 误点(关闭按钮)
        extrate 点击扩展
     * @param obj
     * @throws JSONException
     */
    public ParamInfo(JSONObject obj) throws JSONException
    {
        this.exnum =  obj.getInt("exnum");
        this.planid = obj.getInt("planid") ;
        this.imgurl = obj.getString("imgurl");
        this.gotourl = obj.getString("gotourl");
        this.getImageTJ = obj.getString("getImageTJ");
        this.fullrate = obj.getInt("fullrate");
        this.errrate = obj.getInt("errrate");
        this.extrate = obj.getInt("extrate") ;
        this.succ = obj.getInt("succ");
        this.code = obj.getInt("code");
    }
}

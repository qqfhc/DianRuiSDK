package dianruisdk.net;

import org.json.JSONException;

/**
 *
 * Created by d on 2016/11/2.
 */

public interface ICall
{
    public void onComplete(String content) throws JSONException;
    public void onError(String errorInfo);
}

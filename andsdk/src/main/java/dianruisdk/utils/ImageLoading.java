package dianruisdk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by j on 2016/11/3.
 */

public class ImageLoading {

    public Target target;
    public ImageLoading execute(Target target) {
        this.target = target;
        return this;
    }

    public ImageLoading showImg(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL myFileURL;
                Bitmap bitmap = null;
                try {
                    myFileURL = new URL(url);
                    //获得连接  
//                    target.onPrepareLoad("zhunbei加载");
                    HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
                    //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制  
                    conn.setConnectTimeout(6000);
                    //连接设置获得数据流  
                    conn.setDoInput(true);
                    //不使用缓存  
                    conn.setUseCaches(false);
                    //这句可有可无，没有影响  
                    //conn.connect();  
                    //得到数据流  
                    InputStream is = conn.getInputStream();
                    //解析得到图片  
                    bitmap = BitmapFactory.decodeStream(is);
                    //关闭数据流  
                    is.close();
                    if (target != null) {
                        target.onBitmapLoaded(bitmap);
                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
//                    target.onBitmapFailed("IMAGE_ERROR");
                }
            }
        }).start();

        return this;
    }
}

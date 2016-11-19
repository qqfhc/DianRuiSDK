package dianruisdk.net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/** 
* @author luqinglong E-mail: 23503458@qq.com
* @version 创建时间：2016年11月12日 下午2:54:42
*/
public class HttpClient 
{
	private int timeOut = 5000 ;
	public ICall call ;
	public HttpClient execute(ICall call)
	{
		this.call = call ;
		return this ;
	}

	public HttpClient get(final String url)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				StringBuilder res = new StringBuilder();
				try {
					URL getUrl = new URL(url);
					HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
					connection.setConnectTimeout(timeOut);
					connection.setReadTimeout(timeOut);
					connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
					connection.connect();
					int response_code = connection.getResponseCode();
					if(response_code==HttpURLConnection.HTTP_OK)
					{
						BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
						String line ;
						while ((line = reader.readLine()) != null) {
							res.append(line) ;
						}
						reader.close();
						connection.disconnect();
						if(call!=null)
							call.onComplete(res.toString());
						else
						{
							call.onError("ERROR_CODE>>>:the call is null");
						}
					}
					else
					{
						System.out.println("the respone code is :"+response_code) ;
						call.onError("ERROR_CODE>>>:"+response_code);
					}
				}
				catch (MalformedURLException e)
				{
					e.printStackTrace();
					call.onError(e.getMessage());
				}
				catch (IOException e)
				{
					e.printStackTrace();
					call.onError(e.getMessage());
				}
				catch (Exception e)
				{
					e.printStackTrace();
					call.onError(e.getMessage());
				}
			}
		}).start();
		return this ;
	}

	public static void main(String[] args)
	{
		new HttpClient().get("http://www.baidu.com").execute(new ICall() {
			@Override
			public void onComplete(String content)
			{
				System.out.println(content);
			}

			@Override
			public void onError(String errorInfo) {
				System.out.println(errorInfo);
			}
		});
	}
}

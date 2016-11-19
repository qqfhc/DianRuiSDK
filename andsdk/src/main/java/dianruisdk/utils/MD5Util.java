package dianruisdk.utils;
import java.security.MessageDigest;

public class MD5Util {

	static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtsvsk/MIEI9YXvHzLfg+eEJkY3d7RmVynKBZY35T0xg3WwZgmC6GSPZqrMMcht6aiZYPJywhm9JiE6kBo/0Mvxklm5Wd35wIKeDXcq8Aqb4aQXalcwsD3f829OR1P2AqGilr14Rftv4ixyQATG/BqP2/kgft2rcq4e/E7bDWNLQIDAQAB";
	public static String privateKey="MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAO2y+yT8wgQj1he8"+
			"fMt+D54QmRjd3tGZXKcoFljflPTGDdbBmCYLoZI9mqswxyG3pqJlg8nLCGb0mITq"+
			"QGj/Qy/GSWblZ3fnAgp4NdyrwCpvhpBdqVzCwPd/zb05HU/YCoaKWvXhF+2/iLHJ"+
			"ABMb8Go/b+SB+3atyrh78TtsNY0tAgMBAAECgYEAnikEcOT517VcpCiXxPMZQyx6"+
			"IhCnTUHFY7gJvpLuUzxhQu9H5LDCjkMNYjEvnuDLMK1pVmERMyg3P+F6gCKxOZVN"+
			"hmnsJ0Ebv8NWRJ2taFSjiBQO+1m+Vx9Z0cn5N5h4F81MNjVW/CfFALSHj05jRH2o"+
			"l+NqT7RMJ3V0g3mVDYUCQQD9CN2+OdOJxdp4T5HO45ltwK+sD686x8RvICnfaxBu"+
			"hHUeZq4Ue9BETMbjsIhWkBt14aqKJ+bhqbf3AFiCl4OPAkEA8HwbSCTz1s3HkRLq"+
			"TxwKHJK5CkaPK4BUgwm50N0omE74gScm+rFqrnLJlS5cwGUBRapxewcU3/RvCTTE"+
			"y7WVgwJBAI0HVwfCFRvx0X1C5AKKqiYYwhDFQSYi12IUbCIg+yyN2PUTx6yVAZQ/"+
			"so6PAr40XYnLOBD+gll+DZG2eJ54shMCQQDrQm4qEMReomcWXGvOQn793ueYztgm"+
			"1V9X5f7a/lBFWBQxvQyb+U6FJJRiFb31FZq9/XUQxye7w9OQLQhMxPJjAkEAmAGK"+
			"RLIsyhcAr1Nqa2xOloHjaukQSnwqdyZtuFOZS6up1h7YvmczmZKcBCzK56JjYSB1"+
			"8U1zHHPRMkiho5jnYQ==";

	public final static String MD5(String s)
	{
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static String getFileMD5(byte[] b) {
	    if (b == null){
	      return null;
	    }
	    MessageDigest digest = null;
	    StringBuffer Sbuf = new StringBuffer("");
	    try {
	      digest = MessageDigest.getInstance("MD5");
		  digest.update(b, 0, b.length);
		  byte[] buff = digest.digest();
		  int i;
		  for(int offset = 0;offset < buff.length ; offset++){
			  i = buff[offset];
			  if(i < 0) i += 256;
			  if(i < 16) Sbuf.append("0");
			  Sbuf.append(Integer.toHexString(i));
		  }
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    }
	    return Sbuf.toString();
	}

	public static void main(String[] args) throws Exception
	{
		String v = MD5Util.MD5("hahah") ;
		System.out.println(v);
	}
}

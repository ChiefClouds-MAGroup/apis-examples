package com.chiefclouds.apis.example;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CreateSign {
  /**
   * 全局数组
   */
  private final static String[] hexDigits =
      {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

  /**
   * HMAC加密
   * 
   * @param data 需要加密的字节数组
   * @param key 密钥
   * @return 字节数组
   */
  public static String encryptHMAC(byte[] data, String key) {
    SecretKey secretKey;
    byte[] bytes = null;
    try {
      secretKey = new SecretKeySpec(key.getBytes("utf-8"), "HmacSHA1");
      Mac mac = Mac.getInstance(secretKey.getAlgorithm());
      mac.init(secretKey);
      bytes = mac.doFinal(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return byteArrayToHexString(bytes);
  }

  /**
   * 转换字节数组为十六进制字符串
   * 
   * @param bytes 字节数组
   * @return 十六进制字符串
   */
  public static String byteArrayToHexString(byte[] bytes) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < bytes.length; i++) {
      sb.append(byteToHexString(bytes[i]));
    }
    return sb.toString();
  }

  /**
   * 将一个字节转化成十六进制形式的字符串
   * 
   * @param b 字节数组
   * @return 字符串
   */
  private static String byteToHexString(byte b) {
    int ret = b;
    if (ret < 0) {
      ret += 256;
    }
    int m = ret / 16;
    int n = ret % 16;
    return hexDigits[m] + hexDigits[n];
  }

  /**
   * 测试方法
   * 
   * @param args
   */
  public static void main(String[] args) throws Exception {
    String key =
        "DvFzVT+0eAyCzPoF8gEjiiFchuOn7uRrJrUdzajfXA5UyjH0lSLnnXF4ZQ7dHaagbU10yQ+wupeHN5F8vi2k2w==";
    // step1: 生成请求内容
    String requestBody = "{" +
                            "\"orderid\":\"O123455667\"," +
                            "\"actuallypaid\": 21.5," +
                            "\"discountamount\": 2.3," +
                            "\"deliverytime\": \"2017-03-28 18:30:00\"," + 
                            "\"memberid\": \"819479047666380811\"," +
                            "\"discounttype\": \"rttr-4544\"," +
                            "\"membertransactionstatus\": 3," +
                            "\"productid\":\"P00123\"," +
                            "\"productname\": \"iPhone X\"," +
                            "\"storeid\":\"S001\"," +
                            "\"storename\": \"南京东路 Apple Store 零售店\"" +
                         "}";
    // step2: 生成请求内容字节数组          
    byte[] bodyBytes = requestBody.getBytes("UTF-8");
    // step3: 使用KEY计算HAMC-SHA1签名
    String sign = encryptHMAC(bodyBytes, key);
    // step4: 使用数字签名
    System.out.println("apis-sign: " + sign);
  }
}

package com.java.utils;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：  ksfxhw3818@sandbox.com   111111
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101900723088";  //必须设置
    // 商户私钥，您的PKCS8格式RSA2私钥//必须设置
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDoUEHJaCZnBVtLoL1ShlKKCDoAmmtMpLyzAgYasqmwPBnveNCmePaiMoEGFN/Lgc03g+S/UyeCZ2S24w94bYR40SI4dSPF8RaKfaftj878jOtT4J+jRUuzwiO8/lrNHZvyq8l6hwFUn4tLkXlSMvtkPdI7q9I597tyZwyJLnt2xg5M18mlID2lA1M3QtLy3akzGznP1pJFYlKimfkEDFWmVNZeO1zE0/PV8boYarJ28homcau++uS2MZN876Z6qjh1JDsWNTZWzFyqB/ChH8z7ge5HgA3z7ap0swke0lZ7kzs+uDbhzZqVYJGcOJICv092QOvEgcVQvIc01bYHcjCzAgMBAAECggEAAVCq/qvjQ8QI/0WarMPr5bIkOeql3toz40OIq1AFm+TLNMrXS4n1w3dLubAWkD1c3wqVGewrlhZlwI9KgCEGlfSBIBaxrCYwPXp2n+anglB96Iyl+rOiuRLJRwGdm/ml71q/mjz3afY5QKVDLY7yU1/yN7pCR2e6nOwuavxjYIm9ucbbxq0VIk99et1C5+svsPPEGFsFunY2YQlZvUgYMvpTBalUYdCoYCICRZ+tObG8KLCu1et/KpR4j/x4TzhDL9zalFBsa0nUuWpYeCp4lQ0+M1qUuhUETyw0Y653Nc9b3SDRDIBSCVENOcvimWCPSGXMl5ejF0TKNw5fZio6sQKBgQD72INDcPnGNBL/ZZzv4XVhXXmnqVBxcrjiA7Cby2fV6TeajbU3XPtO98UR8lb2r4kIj48XKkbHTXtiTOy0x1sveX9GnVpppWsO2m699XlW9hnpZ8OxMH/Q4OPcuQ/LJOyI2jSr1/ufDXnG32AjCBoS+dAjFq8kGwlXxTI1w/mB6QKBgQDsJUOVA/In0RyiPMfbWnyPgHE8gNYdCeem9gqj25qBwkXx/dK1hX1tz2srgeLYgQ4WIE896Vj4yOAvffZR1AkPzAEdEVlCMj4alDU5N8ROVDCkjGSsWVd/vjx/+CVK+ct+TfanzNfSYTMCvBQxYrrcmg4d+OCmGt8DYW6+r19AOwKBgGM0rsBqSHnjGXixtlhoH6zy/XEmKSIHZvzhJzi3r3d8HwMqvy/H2NcKkv62z7rs+rX8lwkxBiCMTQYz9ypfPXQ+D/zpDyx+lOWM9gKuJ9Yxq5Nd5KBv0mmdrSTNYYNMUEC4c/cdZWkKDeZhJ6WlE2BWN78auHMok02XyGeVgzvBAoGAANUUyuarqNZE2pGKRsyn2b2jKIpvtsR9K4p8eR1iCwkLRBIX9wnUalmr0AhyKz8xNITCor460Q2sNJ6mCp3LtmGKs3aHMQOKUn+pPF1k9L25OitAg5cBBvM9tVnDj+MXg4d00hyXuicbjevpyk7Fx9dHK3XgHNXFaXajdEqb9FcCgYEA9zxSBwPuNozNtj/kk7sp77QMvQRGv8HVsd/Z9Jx2Kj9vwqmW5TKo6uxLGlWwukoqhIJ5GvD4qPPdKQR8IrQo6zSCVuxKUKP5rYKAbSMQcKwSX1RQeCvVeaFnEXJP0XCY6tR+QZfW3cSatXy9xQhhYKDzJTyXaSNIwgppTA0zd/I=";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。//必须设置
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6FBByWgmZwVbS6C9UoZSigg6AJprTKS8swIGGrKpsDwZ73jQpnj2ojKBBhTfy4HNN4Pkv1MngmdktuMPeG2EeNEiOHUjxfEWin2n7Y/O/IzrU+Cfo0VLs8IjvP5azR2b8qvJeocBVJ+LS5F5UjL7ZD3SO6vSOfe7cmcMiS57dsYOTNfJpSA9pQNTN0LS8t2pMxs5z9aSRWJSopn5BAxVplTWXjtcxNPz1fG6GGqydvIaJnGrvvrktjGTfO+meqo4dSQ7FjU2VsxcqgfwoR/M+4HuR4AN8+2qdLMJHtJWe5M7Prg24c2alWCRnDiSAr9PdkDrxIHFULyHNNW2B3IwswIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 支付成功后路径回调 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8090/orders/myOrdersPay";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


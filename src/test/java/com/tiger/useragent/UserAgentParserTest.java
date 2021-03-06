package com.tiger.useragent;

import com.google.common.base.Function;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


/**
 * com.tiger.useragent
 * author : zhaolihe
 * email : dayingzhaolihe@126.com
 * date : 2017/5/9
 */
public class UserAgentParserTest {

    private static UserAgentParser parser;

    @BeforeClass
    public static void init() {
        try {
            parser = UserAgentParser.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserAgentParser() throws IOException {
        final String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.55 Safari/534.3";
        UserAgentInfo info = parser.getUserAgentInfo(userAgent);
        assertThat(info.getBrowserDetail().toString(), is("chrome 6.0"));
    }

    @Test
    public void testSample(){

        String[] uas = new String[]{
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
                "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
                "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
                "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
                "Mozilla/5.0 (Linux; U; Android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
                "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER",
                "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0"
        };
       Function<String,UserAgentParser> function = new Function<String,UserAgentParser>(){
           @Override
           public UserAgentParser apply(String s) {
               try {
                   return UserAgentParser.newInstance();
               } catch (IOException e) {
                   e.printStackTrace();
                   return null;
               }
           }
       };
        List<String> systems = Arrays.asList("Mac","Windows","iOS","Android");
        List<String> browsers = Arrays.asList("Safari","IE","firefox","Chrome","opera","Maxthon","QQ","360","android","Taobao","LieBao","Sogou");
        List<String> deviceTypes = Arrays.asList("PC","Phone","Pad");
        UserAgentParser ua = function.apply("");
        for (String str : uas) {
            UserAgentInfo info = ua.getUserAgentInfo(str);
            assertNotNull(info);
            assertTrue(systems.contains(info.getOsName()));
            assertTrue(browsers.contains(info.getBrowserName()));
            assertTrue(deviceTypes.contains(info.getDeviceType()));
        }
    }

    @Test
    public void testNullException(){
        String userAgent ="Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)";
        UserAgentInfo info = parser.getUserAgentInfo(userAgent);
        assertNotNull(info);
        assertThat(info.getOsDetail().toString(), is("Windows 7"));
        assertThat(info.getBrowserDetail().toString(),is("IE 8"));
    }

    @Test
    public void testGIONEE(){
        String uaExpr ="Mozilla/5.0 (Linux; U; Android 5.1; zh-cn;GIONEE-GN3003/GN3003 Build/IMM76D) AppleWebKit534.30(KHTML,like Gecko)Version/4.0 Mobile Safari/534.30 Id/25E4601A243673900E601298CB629449 RV/5.0.16";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
    }

    @Test
    public void testXIAOMIMX(){
        String uaExpr ="Mozilla/5.0 (Linux; Android 5.0.2; XiaoMi M5 Note Build/RJV90N; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getDeviceBrand().toString(),is("Xiaomi"));
        assertThat(info.getDeviceName().toString(),is("Xiaomi M5 Note"));

        uaExpr = "Mozilla/5.0 (Linux; Android 5.1.1; XiaoMi Mi-4c Build/RBL97Q; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome";
        info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getDeviceBrand().toString(),is("Xiaomi"));
        assertThat(info.getDeviceName().toString(),is("Xiaomi Mi-4c"));

        uaExpr ="Dalvik/2.1.0 (Linux; U; Android 4.4.2; OnePlus OnePlus A3000 Build/S4CNPU";
        info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getDeviceBrand().toString(),is("OnePlus"));
        assertThat(info.getDeviceName().toString(),is("OnePlus A3000"));

        uaExpr = "Mozilla/5.0 (Linux; U; Android 4.0.3; zh-CN; E6883 Build/32.4.A.0.160) AppleWebKit/537.36 (KHTML,like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.4.1.939 Mobile Safari/537.36";
        info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getDeviceBrand().toString(), is("Sony"));
        assertThat(info.getDeviceName().toString(),is("Sony E6883"));
    }

    @Test
    public void testMeizuMX4(){
        String uaExpr="Mozilla/5.0 (Linux; U; Android 4.4.5; zh-cn; MX4 Pro Build/JLS36C) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getDeviceBrand().toString(),is("Meizu"));
        assertThat(info.getDeviceName().toString(),is("Meizu MX4 Pro"));
        uaExpr = "Mozilla/5.0 (Linux; U; Android 6.0; zh-cn; Build/MRA58K ) AppleWebKit/534.30 (KHTML,like Gecko) Version/6.0 Mobile Safari/534.30 GIONEE-F100S/F100SD RV/6.0.1 GNBR/5.1.1.bs Id/2A4541250E1500AFBE03BD6865A07C29";
        info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
    }

    @Test
    public void testVivoX9(){
        String uaExpr ="Mozilla/5.0 (Linux; U; Android 4.0.3; zh-CN; X9 Build/KOT49H) AppleWebKit/537.36 (KHTML,like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.5.2.942 Mobile Safari/537.36";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
    }

    @Test
    public void testHTC(){
        String uaExpr = "Mozilla/5.0 (Linux; Android 4.3; HTC X920e Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.83 Mobile Safari/537.36";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getDeviceBrand().toString(),is("HTC"));
    }

    @Test
    public void testFunshion(){
        String uaExpr="Funshion/3.2.12.2 (ios/9.0); iphone; iPhone8.2)";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getBrowserName().toString(),is("Funshion"));
    }

    @Test
    public void testHonor8(){
        String uaExpr = "Mozilla/5.0(Linux;Android7.0;zh-cn;FRD-DL00Build/FRD-DL00)AppleWebKit/534.30(KHTML,likeGecko)Version/4.0MobileSafari/534.30)";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getDeviceName().toString(),is("Huawei Honor 8"));
        uaExpr ="UCWEB/2.0 (MIDP-2.0; U; zh-CN; VKY-AL00) U2/1.0.0 UCBrowser/10.7.2.940  U2/1.0.0 Mobile";
        info = parser.getUserAgentInfo(uaExpr);
        assertThat(info.getDeviceName().toString(),is("Huawei P10 Plus"));
    }

    @Test
    public void testQQ(){
        String uaExpr="Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; D510 Build/MocorDroid2.3.5) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 V1_AND_SQ_4.6.1_9_YYB_D QQ/5.3.1.660 NetType/WIFI 10000507";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getBrowserName().toString(),is("QQ"));
        assertThat(info.getNetType().toString(),is("WIFI"));
    }

    @Test
    public void testQingtingFM(){
        String uaExpr="Android-QingtingFM Dalvik/2.1.0 (Linux; U; Android 6.0; M621C Build/MRA58K)";
        UserAgentInfo info = parser.getUserAgentInfo(uaExpr);
        assertNotNull(info);
        assertThat(info.getBrowserName().toString(),is("QingtingFM"));
    }
}

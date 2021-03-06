package com.tiger.useragent;

import org.junit.BeforeClass;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * com.yanhe.useragent
 * author : zhaolihe
 * email : dayingzhaolihe@126.com
 * date : 2017/5/5
 */
public class BrowserParserTest {
    private final static String CONFIG_FILE = "/BrowserConfig.yaml";
    private static BrowserParser parser;

    @BeforeClass
    public static void init() throws IOException {
        Yaml yaml = new Yaml(new SafeConstructor());
        try (InputStream stream = Browser.class.getResourceAsStream(CONFIG_FILE)) {
            List<Map<String, String>> props = (List<Map<String, String>>) yaml.load(stream);
            parser = BrowserParser.fromList(props);
        }
    }

    private static Browser parse(String ua) {
        if (parser == null) {
            throw new IllegalStateException("init parser error");
        }
        return parser.parse(ua);
    }

    @Test
    public void testBrowser() {
        String uaString = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.55 Safari/534.3";

        Browser browser = parse(uaString);
        assertThat(browser.brand, is("Chrome"));
    }

    @Test
    public void testSample() {
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
        for (String ua : uas) {
            Browser browser = parse(ua);
            System.out.println(browser.brand + "$$" + browser.family + " " + browser.major + "." + browser.minor);
        }
    }

    @Test
    public void test360Browser(){
        String ua = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)";
        String regex = "(360[SE]E)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ua);
        int groupCount = matcher.groupCount();
        if (matcher.find()) {
            String group = matcher.group(1);
            assertThat(group,is("360SE"));
        }
    }

    @Test
    public void testChrome(){
        String ua = "Mozilla/5.0 (Linux; Android 5.1.1; vivo X9 Build/HQU06P; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome";
        Browser browser = parse(ua);
        assertThat(browser.brand,is("Chrome"));
    }

    @Test
    public void testFunshion(){
        String ua="Funshion/2.7.1.4 (Android/4.2.2; aphone; T200-C)";
        Browser browser = parse(ua);
        assertThat(browser.brand,is("Funshion"));
    }
}

package cn.yokiqust.spider.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * @author yokiqust
 */
public class CookieLogin {
	private String xsrfValue;
	private StringBuilder strb = new StringBuilder();
	private FileInputStream fis = null;
	private BufferedReader reader = null;

	public Site login() {
		getCookie();
		final Site site = Site.me().setRetryTimes(100).setSleepTime(1000).setCharset("UTF-8").setUserAgent(
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
		for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
			site.addCookie(entry.getKey(), entry.getValue().replaceAll("&quot;", "\""));
		}
		this.xsrfValue = cookieMap.get("_xsrf");
		for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
			this.strb.append(entry.getKey());
			this.strb.append("=");
			this.strb.append(entry.getValue().replaceAll("&quot;", "\""));
			this.strb.append("; ");
		}
		Spider spider = Spider.create(new PageProcessor() {
			public void process(Page page) {
				if (page.getHtml().toString().contains("yokiqust@qq.com")) {
					System.out.println("登陆成功");
				} else {
					System.err.println("登陆失败，请重新获取cookie文件");
					System.exit(0);
				}
			}

			public Site getSite() {
				return site;
			}
		});
		spider.addUrl("https://www.zhihu.com/");
		spider.run();
		return site;
	}

	public String getxsrf() {
		return this.xsrfValue;
	}

	public String getCookieString() {
		return strb.toString();
	}

	private Map<String, String> cookieMap = new HashMap<String, String>(64);

	private Map<String, String> getCookie() {
		try {
			fis = new FileInputStream("src/main/resources/cookies.txt");
			reader = new BufferedReader(new InputStreamReader(fis));
			for (int i = 0; i < 4; i++) {
				reader.readLine();
			}
			cookieMap = new HashMap<String, String>();
			String line = reader.readLine();
			while (line != null) {
				String[] keyValue = line.split("\\s+");
				cookieMap.put(keyValue[keyValue.length - 2], keyValue[keyValue.length - 1]);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return cookieMap;
	}
}
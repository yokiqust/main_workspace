package cn.yokiqust.httpclient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by gavin on 15-7-23.
 */
public class HttpClientLogin {
	public static void main(String[] args) {
		HttpClientLogin login = new HttpClientLogin();
		login.login();

	}

	private HttpContext context;
	private String xsrfValue;
	private StringBuilder strb = new StringBuilder();
	private FileInputStream fis = null;
	private BufferedReader reader = null;

	public Site login() {
		// 创建一个HttpClient

		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			// 创建一个get请求用来接收_xsrf信息
			HttpGet get = new HttpGet("http://www.zhihu.com/login/email");
			get.setHeader("user-agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
			// 获取_xsrf
			CloseableHttpResponse response = httpClient.execute(get, context);
			setCookie(response);
			String responseHtml = EntityUtils.toString(response.getEntity());
			Selectable select = new Html(responseHtml);
			if (select.xpath("//input[@id=captcha]/@placeholder").get().equals("验证码")) {
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
						// TODO Auto-generated method stub
						return site;
					}
				});
				spider.addUrl("https://www.zhihu.com/");
				spider.run();
				return site;
			}
			xsrfValue = responseHtml.split("<input type=\"hidden\" name=\"_xsrf\" value=\"")[1].split("\"/>")[0];
			response.close();

			// 构造post数据
			List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
			valuePairs.add(new BasicNameValuePair("_xsrf", xsrfValue));
			valuePairs.add(new BasicNameValuePair("email", "yokiqust@qq.com"));
			valuePairs.add(new BasicNameValuePair("password", "yx-123963w"));
			valuePairs.add(new BasicNameValuePair("remember_me", "true"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
			// 创建一个post请求
			HttpPost post = new HttpPost("http://www.zhihu.com/login/email");
			post.setHeader("Cookie",
					" cap_id=\"MWY1ZWM1MWI4MTc2NDMxYWFiNDc1MDdlZDkyMWQ4YmM=|1461065157|1e59fdd0f3beb96aa927936893f3df664c5c3206\"; ");

			// 注入post数据
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			// 打印登录是否成功信息
			System.out.println(httpResponse);
			printResponse(httpResponse);

			// 构造一个get请求，用来测试登录cookie是否拿到
			HttpGet g = new HttpGet("http://www.zhihu.com/question/following");
			// 得到post请求返回的cookie信息
			String c = setCookie(httpResponse);
			// 将cookie注入到get请求头当中
			g.setHeader("Cookie", c);
			HttpClientContext contexts = HttpClientContext.create();
			CloseableHttpResponse r = httpClient.execute(g, contexts);

			CookieStore cookieStore = contexts.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			// String content = EntityUtils.toString(r.getEntity());
			// System.out.println(content);
			for (Cookie cookie : cookies) {
				strb.append(cookie.getName());
				strb.append("=");
				strb.append(cookie.getValue());
				strb.append("; ");
			}
			r.close();
			Site site = Site.me().setRetryTimes(100).setSleepTime(1000).setCharset("UTF-8")
					.setUserAgent("Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			for (Cookie coo : cookies) {
				site.addCookie(coo.getName(), coo.getValue());
			}
			return site;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String getxsrf() {
		return this.xsrfValue;
	}

	public String getCookieString() {
		return strb.toString();
	}

	private void printResponse(HttpResponse httpResponse) throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		// System.out.println("status:" + httpResponse.getStatusLine());
		// System.out.println("headers:");
		// HeaderIterator iterator = httpResponse.headerIterator();
		// while (iterator.hasNext()) {
		// System.out.println("\t" + iterator.next());
		// }
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println(new JsonPathSelector("$.msg").select(responseString));
		}
	}

	private Map<String, String> cookieMap = new HashMap<String, String>(64);

	// 从响应信息中获取cookie
	private String setCookie(HttpResponse httpResponse) {
		// System.out.println("----setCookieStore");
		Header headers[] = httpResponse.getHeaders("Set-Cookie");
		if (headers == null || headers.length == 0) {
			System.out.println("----there are no cookies");
			return null;
		}
		String cookie = "";
		for (int i = 0; i < headers.length; i++) {
			cookie += headers[i].getValue();
			if (i != headers.length - 1) {
				cookie += ";";
			}
		}

		String cookies[] = cookie.split(";");
		for (String c : cookies) {
			c = c.trim();
			if (cookieMap.containsKey(c.split("=")[0])) {
				cookieMap.remove(c.split("=")[0]);
			}
			cookieMap.put(c.split("=")[0],
					c.split("=").length == 1 ? "" : (c.split("=").length == 2 ? c.split("=")[1] : c.split("=", 2)[1]));
		}
		// System.out.println("----setCookieStore success");
		String cookiesTmp = "";
		for (String key : cookieMap.keySet()) {
			cookiesTmp += key + "=" + cookieMap.get(key) + ";";
		}

		return cookiesTmp.substring(0, cookiesTmp.length() - 2);
	}

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
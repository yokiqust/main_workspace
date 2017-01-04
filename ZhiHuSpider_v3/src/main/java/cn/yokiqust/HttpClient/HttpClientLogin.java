package cn.yokiqust.HttpClient;

import org.apache.http.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by gavin on 15-7-23.
 */
@Service("login")
public class HttpClientLogin {

	@Autowired(required = false)
	private HttpContext context;
	@Autowired(required = false)
	private String xsrfValue;
	@Autowired(required = false)
	private StringBuilder strb = new StringBuilder();

	public Site login() {
		// 创建一个HttpClient
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			// 创建一个get请求用来接收_xsrf信息
			HttpGet get = new HttpGet("http://www.zhihu.com/");
			// 获取_xsrf
			CloseableHttpResponse response = httpClient.execute(get, context);
			setCookie(response);
			String responseHtml = EntityUtils.toString(response.getEntity());
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
					" cap_id=\"YjA5MjE0YzYyNGQ2NDY5NWJhMmFhN2YyY2EwODIwZjQ=|1437610072|e7cc307c0d2fe2ee84fd3ceb7f83d298156e37e0\"; ");

			// 注入post数据
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			// 打印登录是否成功信息
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

	public void printResponse(HttpResponse httpResponse) throws ParseException, IOException {
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

	public Map<String, String> cookieMap = new HashMap<String, String>(64);

	// 从响应信息中获取cookie
	public String setCookie(HttpResponse httpResponse) {
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
}
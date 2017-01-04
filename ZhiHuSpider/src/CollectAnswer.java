
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.yokiqust.HttpClient.HttpClientLogin;
import net.minidev.json.JSONObject;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class CollectAnswer {
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	private HttpPost post = new HttpPost("https://www.zhihu.com/collection/add");
	private HttpGet get = new HttpGet("https://www.zhihu.com/collections/json?answer_id=23611448");
	private HttpPost post2 = new HttpPost("https://zhihu-web-analytics.zhihu.com/collect");
	private HttpGet get2 = new HttpGet(
			"https://ssl.google-analytics.com/__utm.gif?utmwv=5.6.7&utms=48&utmn=745162363&utmhn=www.zhihu.com&utmt=event&utme=5(collection*click_collect_answer_box*question_answer_collect)8(user_attr*registration_date*entry_date*abtest_group)9(100-1*20151219*20151219*---------1--------------------)11(2!1*1)&utmcs=UTF-8&utmsr=1366x768&utmvp=1351x683&utmsc=24-bit&utmul=zh-cn&utmje=0&utmfl=21.0%20r0&utmdt=%E6%9C%89%E5%93%AA%E4%BA%9B%E5%8D%81%E5%B9%B4%E5%89%8D%E7%9A%84%E7%9C%8B%E4%BC%BC%E4%B8%8D%E9%9D%A0%E8%B0%B1%E7%9A%84%E9%A2%84%E6%B5%8B%E6%88%90%E7%9C%9F%E4%BA%86%EF%BC%9F%20-%20%E7%9F%A5%E4%B9%8E&utmhid=1916762900&utmr=-&utmp=%2Fquestion%2F36925599&utmht=1459343702566&utmac=UA-20961733-1&utmcc=__utma%3D51854390.1211956293.1459336036.1459336239.1459339398.3%3B%2B__utmz%3D51854390.1459336239.2.2.utmcsr%3Dgoogle%7Cutmccn%3D(organic)%7Cutmcmd%3Dorganic%7Cutmctr%3D(not%2520provided)%3B%2B__utmv%3D51854390.100-1%3B&utmjid=&utmu=6RMQAAAAAAAAAAAAAAAAAAAE~");
	private List<NameValuePair> value = new ArrayList<NameValuePair>();
	private CloseableHttpResponse response;
	private UrlEncodedFormEntity entity;

	public void collectAnswer(HttpClientLogin ht) {

		post.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
		post.setHeader("Host", "www.zhihu.com");
		post.setHeader("Referer", "https://www.zhihu.com/question/36925599");
		// post.setHeader("Cookie",
		// "_za=47f90c9b-6e9b-438d-9c80-ad26f6b1a669;
		// udid=\"AEDAWmpXlAmPTl1oOpOw3xNNjM5Lct4sHzw=|1457512607\";
		// d_c0=\"AECAvjtMoQmPTsn6MYXSvyHYcCgBbxfXAbQ=|1458223333\";
		// q_c1=d6a0c33989d84e4a9db8edbb7ad4db8f|1458648304000|1453350074000;
		// _xsrf=9ea552a4f4afcf71ffea5d643ff3ab71;
		// cap_id=\"YmM5YTU1YmZiMjhhNDc1MTkwZjQyMjY3NzFkMDFjMzY=|1459335470|c6c37408a4e680d316aa8a1a16b9a36eba4e9fd8\";
		// login=\"NGIzMDFjNDRjOWMwNGM3M2FmMzU4NWEwNmI4ZGFkYjU=|1459335614|227a2e3c4a581e5b49ed6f9e30ae09d725b56a90\";
		// l_cap_id=\"MjY2ODU0NDJiY2QzNGNmNzk0YjE3OTZkODQ4N2FjNDc=|1459335693|4a35cc234c36274a72a4e386a471c35dec2c2bb6\";
		// z_c0=\"QUJES2VuV0NMZ2tYQUFBQVlRSlZUUTRfSTFkQ3pITDlPU3ZIRFJTOFRLcDBVSXFObkZSQkt3PT0=|1459335693|6c7a3de630f22ce1f8d43c9ac38e519ed7446975\";
		// __utmt=1;
		// __utma=51854390.1211956293.1459336036.1459336239.1459339398.3;
		// __utmb=51854390.38.9.1459342285845; __utmc=51854390;
		// __utmz=51854390.1459336239.2.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided);
		// __utmv=51854390.100-1|2=registration_date=20151219=1^3=entry_date=20151219=1");
		post.setHeader("Cookie",
				"_xsrf=1fd311cc1dd3fb88e2e94b62ef79c2e7; cap_id=\"ODRhYWVhNTczYjI5NGQ3OTg5MTAzNDBjOTk4YTAxMWU=|1459340784|bc43d4f1dcc358e1c93e3ffb1659b0316b9497e3\"; l_cap_id=\"Y2ZjZjRkYjcyZTkwNDU4YWFmNjY0NmM2NTU2NGQzM2E=|1459340784|88b02474a013372dbfe0fe74078b33031f14898a\"; login=\"MDE0OTUxMTZjNzY0NDE0Y2E5NDhhYzU4MDgzY2Y3ZDk=|1459340785|0fa7671f15972fd69e4ff160d031aa8802df682f\"; n_c=1; q_c1=bb6bf999cc924aa4aa16b36845ce5aec|1459340785000|1459340785000; unlock_ticket=\"QUJES2VuV0NMZ2tYQUFBQVlRSlZUZm5NLTFaZ3FDSWJndE1YcEhseUdlMzlIakEzVUdwMFl3PT0=|1459340785|f8880d080c8de5a380962b40eca120e0e0b08ec5\"; z_c0=\"QUJES2VuV0NMZ2tYQUFBQVlRSlZUZkZTSTFjS0JrMTNBNjRwZFA2TW9wZ2ZyU1dLLVZGRGt3PT0=|1459340785|8ff7a963eedf3aa1d8cb85fb14ae61e98ae857ae\";");
		post.setHeader("Origin", "https://www.zhihu.com");
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		// post2.setHeader("Authorization", "Basic WkEtMTE0MjcyNjAyMDY=");
		// post2.setHeader("Content-Type", "application/json");
		// post2.setHeader("Host", "zhihu-web-analytics.zhihu.com");
		// post2.setHeader("Origin", "https://www.zhihu.com");
		// post2.setHeader("Referer",
		// "https://www.zhihu.com/question/36925599");
		// post2.setHeader("User-Agent",
		// "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like
		// Gecko) Chrome/49.0.2623.75 Safari/537.36");
		// JSONObject json = new JSONObject();
		// json.put("v", "1");
		// json.put("cid", "47f90c9b-6e9b-438d-9c80-ad26f6b1a669");
		// json.put("uid", "661609636352888832");
		// json.put("t", "event");
		// json.put("ni", "false");
		// json.put("dp", "/question/36925599");
		// json.put("ts", "1459337474094");
		// json.put("sr", "1366x768");
		// json.put("vp", "1366x358");
		// json.put("ul", "zh-CN");
		// json.put("ua",
		// "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like
		// Gecko) Chrome/49.0.2623.75 Safari/537.36");
		// json.put("dl", "https://www.zhihu.com/question/36925599");
		// json.put("dr", "");
		// json.put("de", "UTF-8");
		// json.put("dt", "有哪些十年前的看似不靠谱的预测成真了？ - 知乎");
		// json.put("sd", "24");
		// json.put("ea", "click_collect_answer_box");
		// json.put("ec", "collection");
		// json.put("el", "question_answer_collect");
		// StringEntity se = new StringEntity(json.toString(), "UTF-8");
		get.setHeader("Cookie",
				"_xsrf=1fd311cc1dd3fb88e2e94b62ef79c2e7; cap_id=\"ODRhYWVhNTczYjI5NGQ3OTg5MTAzNDBjOTk4YTAxMWU=|1459340784|bc43d4f1dcc358e1c93e3ffb1659b0316b9497e3\"; l_cap_id=\"Y2ZjZjRkYjcyZTkwNDU4YWFmNjY0NmM2NTU2NGQzM2E=|1459340784|88b02474a013372dbfe0fe74078b33031f14898a\"; login=\"MDE0OTUxMTZjNzY0NDE0Y2E5NDhhYzU4MDgzY2Y3ZDk=|1459340785|0fa7671f15972fd69e4ff160d031aa8802df682f\"; n_c=1; q_c1=bb6bf999cc924aa4aa16b36845ce5aec|1459340785000|1459340785000; unlock_ticket=\"QUJES2VuV0NMZ2tYQUFBQVlRSlZUZm5NLTFaZ3FDSWJndE1YcEhseUdlMzlIakEzVUdwMFl3PT0=|1459340785|f8880d080c8de5a380962b40eca120e0e0b08ec5\"; z_c0=\"QUJES2VuV0NMZ2tYQUFBQVlRSlZUZkZTSTFjS0JrMTNBNjRwZFA2TW9wZ2ZyU1dLLVZGRGt3PT0=|1459340785|8ff7a963eedf3aa1d8cb85fb14ae61e98ae857ae\";");
		get.setHeader("Host", "www.zhihu.com");
		get.setHeader("Referer", "https://www.zhihu.com/question/36925599");
		get.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
		get.setHeader("X-Requested-With", "XMLHttpRequest");
		value.add(new BasicNameValuePair("answer_id", "23611448"));
		value.add(new BasicNameValuePair("favlist_id", "30478833"));
		value.add(new BasicNameValuePair("_xsrf", "1fd311cc1dd3fb88e2e94b62ef79c2e7"));
		// get2.setHeader(":authority", "ssl.google-analytics.com");
		// get2.setHeader(":method", "GET");
		// get2.setHeader(":path",
		// "/__utm.gif?utmwv=5.6.7&utms=48&utmn=745162363&utmhn=www.zhihu.com&utmt=event&utme=5(collection*click_collect_answer_box*question_answer_collect)8(user_attr*registration_date*entry_date*abtest_group)9(100-1*20151219*20151219*---------1--------------------)11(2!1*1)&utmcs=UTF-8&utmsr=1366x768&utmvp=1351x683&utmsc=24-bit&utmul=zh-cn&utmje=0&utmfl=21.0%20r0&utmdt=%E6%9C%89%E5%93%AA%E4%BA%9B%E5%8D%81%E5%B9%B4%E5%89%8D%E7%9A%84%E7%9C%8B%E4%BC%BC%E4%B8%8D%E9%9D%A0%E8%B0%B1%E7%9A%84%E9%A2%84%E6%B5%8B%E6%88%90%E7%9C%9F%E4%BA%86%EF%BC%9F%20-%20%E7%9F%A5%E4%B9%8E&utmhid=1916762900&utmr=-&utmp=%2Fquestion%2F36925599&utmht=1459343702566&utmac=UA-20961733-1&utmcc=__utma%3D51854390.1211956293.1459336036.1459336239.1459339398.3%3B%2B__utmz%3D51854390.1459336239.2.2.utmcsr%3Dgoogle%7Cutmccn%3D(organic)%7Cutmcmd%3Dorganic%7Cutmctr%3D(not%2520provided)%3B%2B__utmv%3D51854390.100-1%3B&utmjid=&utmu=6RMQAAAAAAAAAAAAAAAAAAAE~");
		// get2.setHeader(":scheme", "https");
		// get2.setHeader("accept", "image/webp,image/*,*/*;q=0.8");
		// get2.setHeader("accept-encoding", "gzip, deflate, sdch");
		// get2.setHeader("accept-language", "zh-CN,zh;q=0.8");
		// get2.setHeader("referer", "https://www.zhihu.com/question/36925599");
		// get2.setHeader("user-agent",
		// "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like
		// Gecko) Chrome/49.0.2623.75 Safari/537.36");

		try {
			// post2.setEntity(se);
			// response = httpClient.execute(post2);
			// System.out.println(response.getStatusLine());
			System.out.println(ht.getxsrf());
			entity = new UrlEncodedFormEntity(value, "UTF-8");
			HttpClientContext contexts = HttpClientContext.create();
			response = httpClient.execute(get, contexts);
			// CookieStore cookieStore = contexts.getCookieStore();
			// List<Cookie> cookies = cookieStore.getCookies();
			// for (Cookie c : cookies) {
			// System.out.println(c.getName() + " " + c.getValue());
			// }
			List<String> list = new JsonPathSelector("$.msg[*]").selectList(EntityUtils.toString(response.getEntity()));
			for (Object str : list) {
				System.out.println(str);
			}
			// post2.setEntity(se);
			// response = httpClient.execute(post2);
			// System.out.println(response.getStatusLine());
			// response = httpClient.execute(get2);
			// System.out.println("get2 " + response.getStatusLine());
			post.setEntity(entity);
			response = httpClient.execute(post);
			// System.out.println(response.getEntity());
			System.out.println(EntityUtils.toString(response.getEntity()));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

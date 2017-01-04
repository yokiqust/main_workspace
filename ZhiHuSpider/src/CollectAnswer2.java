
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.yokiqust.HttpClient.HttpClientLogin;
import net.minidev.json.JSONObject;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class CollectAnswer2 {
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	private HttpPost post = new HttpPost("https://www.zhihu.com/collection/add");
	private HttpGet get = new HttpGet("https://www.zhihu.com/collections/json?answer_id=23611448");
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
				"_za=47f90c9b-6e9b-438d-9c80-ad26f6b1a669; udid=\"AEDAWmpXlAmPTl1oOpOw3xNNjM5Lct4sHzw=|1457512607\"; d_c0=\"AECAvjtMoQmPTsn6MYXSvyHYcCgBbxfXAbQ=|1458223333\"; q_c1=d6a0c33989d84e4a9db8edbb7ad4db8f|1458648304000|1453350074000; _xsrf=9ea552a4f4afcf71ffea5d643ff3ab71; cap_id=\"YmM5YTU1YmZiMjhhNDc1MTkwZjQyMjY3NzFkMDFjMzY=|1459335470|c6c37408a4e680d316aa8a1a16b9a36eba4e9fd8\"; login=\"NGIzMDFjNDRjOWMwNGM3M2FmMzU4NWEwNmI4ZGFkYjU=|1459335614|227a2e3c4a581e5b49ed6f9e30ae09d725b56a90\"; l_cap_id=\"MjY2ODU0NDJiY2QzNGNmNzk0YjE3OTZkODQ4N2FjNDc=|1459335693|4a35cc234c36274a72a4e386a471c35dec2c2bb6\"; z_c0=\"QUJES2VuV0NMZ2tYQUFBQVlRSlZUUTRfSTFkQ3pITDlPU3ZIRFJTOFRLcDBVSXFObkZSQkt3PT0=|1459335693|6c7a3de630f22ce1f8d43c9ac38e519ed7446975\"; __utma=51854390.1211956293.1459336036.1459336239.1459339398.3; __utmb=51854390.52.8.1459344459016; __utmc=51854390; __utmz=51854390.1459336239.2.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utmv=51854390.100-1|2=registration_date=20151219=1^3=entry_date=20151219=1");
		post.setHeader("Origin", "https://www.zhihu.com");
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

		get.setHeader("Cookie",
				"_xsrf=3295fb7438cfb0fd7cf716221712b693; cap_id=\"YjA0ZTg1YWZjMGQ3NDhjYmFkZjg1YzJhN2QzM2Y5N2E=|1459346903|b581094bc342eecf55cb15e2a8e9d1c471423ede\"; l_cap_id=\"MmQ3MTk5MTYzNGFjNGFjN2E5YjgwZWM3ZTlkYTRiZmE=|1459346903|ef77722b5d1e1aa5ee0889935e1baaaf254b3627\"; login=\"YjEyNGY1ODQ1NjkzNGUwMWJmZWJhOTBjMjkwZjQyNzI=|1459346903|bf10bb486e774a1aacf67a8a64375c546876cb01\"; n_c=1; q_c1=bcb64e6841bf44f39256f6cd43abfcd6|1459346903000|1459346903000; unlock_ticket=\"QUJES2VuV0NMZ2tYQUFBQVlRSlZUZF9rLTFZUTNLTDRmWTRiRDhRcmU5MGI3SW1nYUxSeHVBPT0=|1459346903|288f2b41fd1f9b5fb2881a17e4f32462dccbdb3a\"; z_c0=\"QUJES2VuV0NMZ2tYQUFBQVlRSlZUZGRxSTFlaVZGV1R4ZWRNS05NLWU4YnlxamlIR3B0VVJBPT0=|1459346903|9682d155ecfe6a0c4443aaa9dcc6904e57c42e37\";");
		get.setHeader("Host", "www.zhihu.com");
		get.setHeader("Referer", "https://www.zhihu.com/question/36925599");
		get.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
		get.setHeader("X-Requested-With", "XMLHttpRequest");
		value.add(new BasicNameValuePair("answer_id", "23611448"));
		value.add(new BasicNameValuePair("favlist_id", "30478833"));
		value.add(new BasicNameValuePair("_xsrf", "9ea552a4f4afcf71ffea5d643ff3ab71"));

		try {
			entity = new UrlEncodedFormEntity(value, "UTF-8");
			response = httpClient.execute(get);
			System.out.println("----->" + response.getStatusLine());
			List<String> list = new JsonPathSelector("$.msg[*]").selectList(EntityUtils.toString(response.getEntity()));
			for (String str : list) {
				System.out.println(str);
			}
			post.setEntity(entity);
			response = httpClient.execute(post);
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

package cn.yokiqust.Spider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

@ThreadSafe
public class MoreAnswerDownloader {
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	private HttpPost post = new HttpPost("https://www.zhihu.com/node/QuestionAnswerListV2");
	private List<NameValuePair> value = new ArrayList<NameValuePair>();
	private CloseableHttpResponse response;
	private UrlEncodedFormEntity entity;

	public List<Selectable> getMoreTopics(String question_id, int offset) {
		post.setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
		post.setHeader("Host", "www.zhihu.com");
		post.setHeader("Referer", "url");
		value.add(new BasicNameValuePair("method", "next"));
		value.add(new BasicNameValuePair("_xsrf", cn.yokiqust.spiderUtils.Const._xsrf));
		value.add(new BasicNameValuePair("params",
				"{\"url_token\":" + question_id + ",\"pagesize\":20,\"offset\":" + offset + "}"));
		List<String> answers = getJson();
		List<Document> docs = new ArrayList<Document>();
		for (String str : answers) {
			docs.add(Jsoup.parse(str));
		}
		return nodes(docs);
	}

	private List<String> getJson() {
		try {
			entity = new UrlEncodedFormEntity(value, "UTF-8");
			post.setEntity(entity);
			response = httpClient.execute(post);
			List<String> list = new JsonPathSelector("$.msg[*]").selectList(EntityUtils.toString(response.getEntity()));
			return list;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		try {
			response.close();
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Selectable> nodes(List<Document> docs) {
		List<Selectable> selectables = new ArrayList<Selectable>();
		for (Document e : docs) {
			List<Element> childElements = new ArrayList<Element>(1);
			childElements.add(e);
			selectables.add(new HtmlNode(childElements).xpath("//div[@class=zm-item-answer  ]"));
		}
		return selectables;
	}
}

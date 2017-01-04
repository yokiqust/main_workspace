package cn.yokiqust.spider.pipeline;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.yokiqust.model.Answer;
import cn.yokiqust.spider.utils.Const;
import cn.yokiqust.spider.utils.Result2Model;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.FilePersistentBase;

/**
 * @author yokiqust
 */

@ThreadSafe
public class CollectAnswerPipeLine extends FilePersistentBase implements Pipeline {
	private List<NameValuePair> value;
	private CloseableHttpResponse response;
	private UrlEncodedFormEntity entity;

	public void collectAnswer(Answer answer) {
		@SuppressWarnings("unused")
		List<String> list;
		CloseableHttpClient httpClient = null;
		int i = 0;
		while (i <= 4) {
			try {
				httpClient = HttpClients.createDefault();
				HttpPost post = new HttpPost("https://www.zhihu.com/collection/add");
				HttpGet get = new HttpGet("https://www.zhihu.com/collections/json?answer_id=" + answer.getAnswer_id());
				setGet(answer, get);
				response = httpClient.execute(get);
				list = new JsonPathSelector("$.msg[*]").selectList(EntityUtils.toString(response.getEntity()));
				setPost(answer, post);
				post.setEntity(entity);
				response = httpClient.execute(post);
				break;
			} catch (Exception e) {
				i++;
				continue;
			} finally {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void setPost(Answer answer, HttpPost post) throws UnsupportedEncodingException {
		post.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
		post.setHeader("Host", "www.zhihu.com");
		post.setHeader("Referer", "https://www.zhihu.com/question/" + answer.getQuestion().getId());
		post.setHeader("Cookie", Const.login.getCookieString());
		post.setHeader("Origin", "https://www.zhihu.com");
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		value = new ArrayList<NameValuePair>();
		value.add(new BasicNameValuePair("answer_id", Integer.toString(answer.getAnswer_id())));
		// value.add(new BasicNameValuePair("favlist_id", "32300379"));// 10K+
		value.add(new BasicNameValuePair("favlist_id", "30478833"));// 高分
		value.add(new BasicNameValuePair("_xsrf", Const.login.getxsrf()));
		entity = new UrlEncodedFormEntity(value, "UTF-8");
	}

	private void setGet(Answer answer, HttpGet get) {
		get.setHeader("Cookie", Const.login.getCookieString());
		get.setHeader("Host", "www.zhihu.com");
		get.setHeader("Referer", "https://www.zhihu.com/question/" + answer.getQuestion().getId());
		get.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
		get.setHeader("X-Requested-With", "XMLHttpRequest");
	}

	public void process(ResultItems resultItems, Task task) {
		if (resultItems.get("question") != null) {
			List<Answer> answers = Result2Model.result2Answer(resultItems);
			for (Answer answer : answers) {
				collectAnswer(answer);
			}
		}
	}

	public void process(List<Answer> answers) {
		for (Answer answer : answers) {
			collectAnswer(answer);
		}
	}
}

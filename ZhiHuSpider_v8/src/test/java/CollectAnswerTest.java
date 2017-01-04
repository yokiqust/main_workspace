import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yokiqust.dao.AnswerDAO;
import cn.yokiqust.model.Answer;
import cn.yokiqust.spider.utils.Const;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class CollectAnswerTest {
	private List<NameValuePair> value;
	private CloseableHttpResponse response;
	private UrlEncodedFormEntity entity;
	private int i = 0;

	public void collectAnswer(Answer answer) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<String> list;
		while (true) {
			try {
				HttpPost post = new HttpPost("https://www.zhihu.com/collection/add");
				HttpGet get = new HttpGet("https://www.zhihu.com/collections/json?answer_id=" + answer.getAnswer_id());
				setGet(answer, get);
				response = httpClient.execute(get);
				System.out.println(response.getStatusLine().getStatusCode());
				if (i == 1) {
					list = new JsonPathSelector("$.msg[*]").selectList(EntityUtils.toString(response.getEntity()));
					throw new IOException();
				}
				list = new JsonPathSelector("$.msg[*]").selectList(EntityUtils.toString(response.getEntity()));
				// if (list.get(1).equals("[]")) {
				System.out.println(list);
				setPost(answer, post);
				post.setEntity(entity);
				System.out.println("success");
				i++;
				break;
				// } else {
				// System.out.println("已收藏！");
				// }
			} catch (ParseException e) {
				System.out.println("");
				continue;
			} catch (IOException e) {
				System.out.println("get json again  " + httpClient);
				i++;
				continue;
			}
		}
		try {
		} finally {
			try {
				System.out.println("closed");
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		// value.add(new BasicNameValuePair("favlist_id", "31262121"));
		value.add(new BasicNameValuePair("favlist_id", "30478833"));
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

	private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/Beans.xml");
	private static AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");

	public static void main(String[] args) {
		List<Answer> answers = answerDAO.getAnswer();
		CollectAnswerTest test = new CollectAnswerTest();
		for (Answer answer : answers) {
			test.collectAnswer(answer);
		}
	}
}

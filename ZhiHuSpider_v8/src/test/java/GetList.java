import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.yokiqust.model.Answer;
import cn.yokiqust.spider.utils.Const;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class GetList {
	private static CloseableHttpResponse response;

	public static void main(String[] args) throws ClientProtocolException, IOException {
		List<String> list;
		CloseableHttpClient httpClient = null;
		Answer answer = new Answer();
		answer.setAnswer_id(37523991);
		httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet("https://www.zhihu.com/collections/json?answer_id=" + answer.getAnswer_id());
		setGet(answer, get);
		response = httpClient.execute(get);
		list = new JsonPathSelector("$.msg[*]").selectList(EntityUtils.toString(response.getEntity()));
		Map<String, String> lists = new HashMap<String, String>();
		for (String str : list) {
			for (String st : str.substring(1, str.lastIndexOf("]") - 1).split("],")) {
				if (st.split(",").length <= 1) {
					continue;
				}
				lists.put(st.substring(1, st.length()).split(",")[1], st.substring(1, st.length()).split(",")[0]);
			}
		}
	}

	private static void setGet(Answer answer, HttpGet get) {
		get.setHeader("Cookie", Const.login.getCookieString());
		get.setHeader("Host", "www.zhihu.com");
		get.setHeader("Referer", "https://www.zhihu.com/question/19752381");
		get.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
		get.setHeader("X-Requested-With", "XMLHttpRequest");
	}
}

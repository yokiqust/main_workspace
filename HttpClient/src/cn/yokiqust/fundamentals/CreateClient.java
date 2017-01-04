package cn.yokiqust.fundamentals;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CreateClient {
	public static void main(String[] args) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpOptions options = new HttpOptions("https://www.zhihu.com");
		HttpTrace trace = new HttpTrace("https://www.zhihu.com");
		HttpHead head = new HttpHead("https://www.baidu.com");
		CloseableHttpResponse response = httpClient.execute(head);
		try {
			for (Header header : response.getAllHeaders()) {
				System.out.println(header.getName() + ": " + header.getValue());
			}
		} finally {
			response.close();
		}
	}
}

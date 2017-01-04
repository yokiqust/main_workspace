package cn.yokiqust.fundamentals;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Release {
	public static void main(String[] args) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet("https://www.baidu.com");
		CloseableHttpResponse response = httpClient.execute(get);
		try {
			org.apache.http.HttpEntity entity = response.getEntity();
			if (entity != null) {
				entity.writeTo(System.out);
				try {
					// do something useful
				} finally {
					// instream.close();
				}
			}
		} finally {
			response.close();
		}
	}
}

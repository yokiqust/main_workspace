package cn.yokiqust.fundamentals;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ResponseHeader {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpOptions options = new HttpOptions("https://www.zhihu.com");
		HttpResponse reponse = httpClient.execute(options);
		System.out.println(EntityUtils.toString(reponse.getEntity()));
		for (Header header : reponse.getAllHeaders()) {
			System.out.println(header.getName() + ": " + header.getValue());
		}
	}
}

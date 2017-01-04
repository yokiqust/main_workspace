package cn.yokiqust.fundamentals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;

public class CreateUri {
	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder().setScheme("https").setHost("www.baidu.com").build();
		HttpGet get = new HttpGet(uri);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(get);
		System.out.println(response.getStatusLine());
		response.close();
		HttpResponse res = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		System.out.println(res.getProtocolVersion());
		System.out.println(res.getStatusLine().getStatusCode());
		System.out.println(res.getStatusLine().getReasonPhrase());
		System.out.println(res.getStatusLine());
	}
}

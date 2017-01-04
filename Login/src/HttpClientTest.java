import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by gavin on 15-7-23.
 */
public class HttpClientTest {
	public static void main(String[] args) {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost("https://www.zhihu.com/");
		post.setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
	}
}
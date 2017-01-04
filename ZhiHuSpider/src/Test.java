import cn.yokiqust.HttpClient.HttpClientLogin;

public class Test {
	public static void main(String[] args) {
		CollectAnswer ca = new CollectAnswer();
		HttpClientLogin hcl = new HttpClientLogin();
		hcl.login();
		ca.collectAnswer(hcl);
	}
}

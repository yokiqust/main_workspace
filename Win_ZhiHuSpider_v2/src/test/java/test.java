import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import cn.yokiqust.config.GetList;

public class test {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		GetList gl = new GetList();
		System.out.println(gl.getList());
	}
}

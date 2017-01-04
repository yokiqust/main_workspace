package cn.yokiqust.fundamentals;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

public class HttpEntity {
	public static void main(String[] args) throws ParseException, IOException {
		StringEntity entity = new StringEntity("important message", ContentType.create("text/plain", "UTF-8"));
		System.out.println(entity.getContentLength());
		System.out.println(entity.getContentType());
		System.out.println(EntityUtils.toString(entity));
		System.out.println(EntityUtils.toByteArray(entity).length);
	}
}

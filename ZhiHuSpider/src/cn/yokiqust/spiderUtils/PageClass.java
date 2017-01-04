package cn.yokiqust.spiderUtils;

import us.codecraft.webmagic.Page;

public class PageClass {
	public static int getClass(Page page) {
		if (page.getUrl().toString().equals(Site.ROOT_TOPIC_URL)) {
			return Site.ROOT_URL;
		}
		if (page.getUrl().toString().endsWith("organize/entire")) {
			return Site.SUBTOPIC_URL;
		}
		if (page.getUrl().toString().matches("https://www.zhihu.com/topic/\\d+/questions(.*)")) {
			return Site.SINGLE_TOPIC_URL;
		}
		if (page.getUrl().toString().matches("https://www.zhihu.com/question/\\d+")) {
			return Site.QUESTION;
		}
		return -1;
	}
}

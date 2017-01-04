package cn.yokiqust.spiderUtils;

import us.codecraft.webmagic.Page;

public class PageClass {
	public static int getClass(Page page) {
		if (page.getUrl().toString().equals(Const.ROOT_TOPIC_URL)) {
			return Const.ROOT_URL;
		}
		if (page.getUrl().toString().endsWith("organize/entire")) {
			return Const.SUBTOPIC_URL;
		}
		if (page.getUrl().toString().matches("https://www.zhihu.com/topic/\\d+/questions(.*)")) {
			return Const.SINGLE_TOPIC_URL;
		}
		if (page.getUrl().toString().matches("https://www.zhihu.com/question/\\d+")) {
			return Const.QUESTION;
		}
		return -1;
	}
}

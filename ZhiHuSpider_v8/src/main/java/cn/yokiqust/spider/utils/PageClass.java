package cn.yokiqust.spider.utils;

import us.codecraft.webmagic.Page;

public class PageClass {
	public static int getClass(Page page) {
		if (page.getUrl().toString().matches("https://www.zhihu.com/topic/\\d+/questions(.*)")) {
			return Const.SINGLE_TOPIC_URL;
		}
		if (page.getUrl().toString().matches("https://www.zhihu.com/question/\\d+")) {
			return Const.QUESTION;
		}
		return -1;
	}
}

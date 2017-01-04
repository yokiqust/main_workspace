package cn.yokiqust.spider.utils;

import cn.yokiqust.config.CreateSpider2;
import cn.yokiqust.model.Author;

public interface Const {
	CreateSpider2 spiderConfig = new CreateSpider2();
	Author anoyAuthor = new Author("匿名用户", "anoy", null);
	CookieLogin login = new CookieLogin();
	us.codecraft.webmagic.Site SITE = login.login();
	String _xsrf = login.getxsrf();
	String collect_id = spiderConfig.getCollect();
	String ROOT_TOPIC_URL = "https://www.zhihu.com/topic/19776749/questions";
	int SINGLE_TOPIC_URL = 3;
	int SINGLE_TOPIC_URL_PAGE = 4;
	int QUESTION = 5;
}

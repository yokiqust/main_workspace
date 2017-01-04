package cn.yokiqust.spider.utils;

import cn.yokiqust.config.CreateSpider;
import cn.yokiqust.httpclient.HttpClientLogin;
import cn.yokiqust.model.Author;
import cn.yokiqust.spider.pipeline.CollectAnswerPipeLine;

public interface Const {
	CreateSpider spiderConfig = new CreateSpider();
	Author anoyAuthor = new Author("匿名用户", "anoy", null);
	HttpClientLogin login = new HttpClientLogin();
	us.codecraft.webmagic.Site SITE = login.login();
	String _xsrf = login.getxsrf();
	String ROOT_TOPIC_URL = "https://www.zhihu.com/topic/19776749/questions";
	int ROOT_URL = 1;
	int SUBTOPIC_URL = 2;
	int SINGLE_TOPIC_URL = 3;
	int SINGLE_TOPIC_URL_PAGE = 4;
	int QUESTION = 5;
	CollectAnswerPipeLine collectAnswerPipeLine = new CollectAnswerPipeLine();
}

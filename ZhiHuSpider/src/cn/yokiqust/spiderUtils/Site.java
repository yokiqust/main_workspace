package cn.yokiqust.spiderUtils;

import cn.yokiqust.HttpClient.HttpClientLogin;
import cn.yokiqust.Model.Author;
import cn.yokiqust.config.SpiderConfig;

public interface Site {
	SpiderConfig spiderConfig = new SpiderConfig();
	Author anoyAuthor = new Author("匿名用户", null, null);
	HttpClientLogin login = new HttpClientLogin();
	us.codecraft.webmagic.Site SITE = login.login();
	String _xsrf = login.getxsrf();
	String ROOT_TOPIC_URL = "https://www.zhihu.com/topic/19776749/questions";
	int ROOT_URL = 1;
	int SUBTOPIC_URL = 2;
	int SINGLE_TOPIC_URL = 3;
	int SINGLE_TOPIC_URL_PAGE = 4;
	int QUESTION = 5;
}

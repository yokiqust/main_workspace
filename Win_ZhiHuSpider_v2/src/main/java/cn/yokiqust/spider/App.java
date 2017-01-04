package cn.yokiqust.spider;

import cn.yokiqust.spider.utils.Const;
import us.codecraft.webmagic.Spider;

public class App {
	public static void main(String[] args) {
		Spider spider = Const.spiderConfig.createSpider();
		spider.thread(10);
		spider.run();
	}
}

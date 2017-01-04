package cn.yokiqust.ZhiHuSpider_v2;

import cn.yokiqust.Spider.MyFilePipeline;
import cn.yokiqust.Spider.ZhiHuPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Spider spider = cn.yokiqust.spiderUtils.Const.spiderConfig.getSpider(new ZhiHuPageProcessor());
		spider.addPipeline(new MyFilePipeline("/home/yokiqust/zhihu"));
		// spider.addPipeline(new CollectAnswerPipeLine());
		System.out.println("spider start");
		spider.run();
	}
}

package cn.yokiqust.spider;

import cn.yokiqust.spider.pipeline.CollectAnswerPipeLine;
import cn.yokiqust.spider.pipeline.MysqlPipeLine;
import cn.yokiqust.spider.processor.ZhiHuPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Spider spider = cn.yokiqust.spider.utils.Const.spiderConfig.getSpider(new ZhiHuPageProcessor());
		spider.addPipeline(new CollectAnswerPipeLine());
		spider.addPipeline(new MysqlPipeLine());
		System.out.println("spider start");
		spider.run();
	}
}

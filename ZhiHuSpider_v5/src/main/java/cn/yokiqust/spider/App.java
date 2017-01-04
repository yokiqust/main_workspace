package cn.yokiqust.spider;

import javax.management.JMException;

import cn.yokiqust.spider.pipeline.CollectAnswerPipeLine;
import cn.yokiqust.spider.pipeline.MysqlPipeLine;
import cn.yokiqust.spider.processor.ZhiHuPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * @author yokiqust
 *
 */
public class App {
	public static void main(String[] args) throws JMException {
		Spider spider = cn.yokiqust.spider.utils.Const.spiderConfig.getSpider(new ZhiHuPageProcessor());
		spider.addPipeline(new CollectAnswerPipeLine());
		spider.addPipeline(new MysqlPipeLine());
		// SpiderMonitor.instance().register(spider);
		System.out.println("spider start");
		spider.run();
	}
}

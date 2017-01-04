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
		//通过配置文件创建spider
		Spider spider = cn.yokiqust.spider.utils.Const.spiderConfig.getSpider(new ZhiHuPageProcessor());
		//收藏答案
		spider.addPipeline(new CollectAnswerPipeLine());
		//持久化答案
		spider.addPipeline(new MysqlPipeLine());
		// SpiderMonitor.instance().register(spider);
		System.out.println("spider start");
		//爬虫启动
		spider.run();
	}
}

package cn.yokiqust.ZhiHuSpider_v3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yokiqust.Spider.MyFilePipeline;
import cn.yokiqust.config.CreateSpider;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/Beans.xml");
		Spider spider = ((CreateSpider) applicationContext.getBean("CreateSpider"))
				.getSpider((PageProcessor) applicationContext.getBean("zhPP"));
		spider.addPipeline(new MyFilePipeline("/home/yokiqust/zhihu"));
		//spider.addPipeline(new CollectAnswerPipeLine());
		System.out.println("spider start");
		spider.run();
	}
}

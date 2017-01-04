package cn.yokiqust.spider;

import java.util.List;
import java.util.Map;

import javax.management.JMException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yokiqust.dao.QuestionDAO;
import cn.yokiqust.spider.pipeline.CollectAnswerPipeLine;
import cn.yokiqust.spider.pipeline.MysqlPipeLine;
import cn.yokiqust.spider.processor.ZhiHuPageProcessor;
import cn.yokiqust.spider.utils.Const;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

/**
 * @author yokiqust
 *
 */
public class App {

	public static void main(String[] args) throws JMException {
		// 通过配置文件创建spider
		Spider spider = cn.yokiqust.spider.utils.Const.spiderConfig.getSpider(new ZhiHuPageProcessor());
		// 收藏答案
		if (Const.spiderConfig.getMode().equals("seeds")) {

		} else if (Const.spiderConfig.getMode().equals("update")) {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/Beans.xml");
			QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");
			List<Map<String, Object>> questions = questionDAO.getQuestion();
			for (Map<String, Object> id : questions) {
				String question_id = id.get("id").toString();
				spider.addUrl("https://www.zhihu.com/question/" + question_id);
			}
		}
		spider.addPipeline(new CollectAnswerPipeLine());
		// 持久化答案
		spider.addPipeline(new MysqlPipeLine());
		SpiderMonitor.instance().register(spider);
		System.out.println("spider start");
		// 爬虫启动
		spider.run();
	}
}

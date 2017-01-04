package cn.yokiqust.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.yokiqust.spiderUtils.Const;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.xsoup.Xsoup;

public class CreateSpider {
	private File configFile;
	private int stepby = 0;
	private int question_condition;
	private int answer_condition;

	public Spider getSpider(PageProcessor pageProcessor) {
		Spider spider = Spider.create(pageProcessor);
		try {
			Document doc = Jsoup.parse(getConfig(), null);
			String type = Xsoup.select(doc, "//Spider/@class").get();
			String step = Xsoup.select(doc, "//Spider/@stepby").get();
			String question_ = Xsoup.select(doc, "//Spider/if/question-condition/more/text()").get();
			String answer_ = Xsoup.select(doc, "//Spider/if/answer-condition/more/text()").get();
			String thread_ = Xsoup.select(doc, "//Spider/thread/text()").get();
			if (step == null) {
				stepby = 5;
			} else {
				stepby = Integer.parseInt(step);
			}
			System.out.println("config type: " + type);
			if (type.equals("seeds")) {
				System.out.println("config set success");
				List<String> topic_list = Xsoup.select(doc, "//Spider/Seeds/topic-id/text()").list();
				List<String> question_list = Xsoup.select(doc, "//Spider/Seeds/question-id/text()").list();
				if (topic_list != null) {
					for (String str : topic_list) {
						str = str.trim();
						for (int i = 1; i <= stepby; i++) {
							spider.addUrl("https://www.zhihu.com/topic/" + str + "/questions?page=" + i);
							System.out.println("config topic url: " + "https://www.zhihu.com/topic/" + str
									+ "/questions?page=" + i);
						}
					}
				}
				if (question_list != null) {
					for (String str : question_list) {
						str = str.trim();
						spider.addUrl("https://www.zhihu.com/question/" + str);
						System.out.println("config question url: " + "https://www.zhihu.com/question/" + str);
					}
				}
			} else if (type.equals("all")) {
				System.out.println("config set success");
				for (int i = 1; i <= stepby; i++) {
					spider.addUrl(Const.ROOT_TOPIC_URL + "?page=" + i);
					System.out.println("config question url: " + Const.ROOT_TOPIC_URL + "?page=" + i);
				}
			} else {
				for (int i = 1; i <= stepby; i++) {
					spider.addUrl(Const.ROOT_TOPIC_URL + "?page=" + i);
					System.out.println("config question url: " + Const.ROOT_TOPIC_URL + "?page=" + i);
				}
			}
			if (question_ != null) {
				question_ = question_.trim();
				question_condition = Integer.parseInt(question_);
			}
			if (answer_ != null) {
				answer_ = answer_.trim();
				answer_condition = Integer.parseInt(answer_);
			}
			int threadNum = 0;
			if (thread_ != null) {
				thread_ = thread_.trim();
				threadNum = Integer.parseInt(thread_);
			} else {
				threadNum = 10;
			}
			spider.thread(threadNum);
			return spider;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getQuestion_condition() {
		return question_condition;
	}

	public int getAnswer_condition() {
		return answer_condition;
	}

	public int getStepBy() {
		return stepby;
	}

	private File getConfig() {
		configFile = new File("SpiderConfig.xml");
		return configFile;
	}
}

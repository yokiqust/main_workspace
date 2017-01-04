package cn.yokiqust.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.yokiqust.spiderUtils.ErrorSeedClassException;
import cn.yokiqust.spiderUtils.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.xsoup.Xsoup;

public class SpiderConfig {
	private File configFile;
	private int stepby = 0;

	public Spider getSpider(PageProcessor pageProcessor, int threadNum) {
		Spider spider = Spider.create(pageProcessor).thread(threadNum);
		try {
			Document doc = Jsoup.parse(getConfig(), null);
			String type = Xsoup.select(doc, "//Seeds/@class").get();
			String step = Xsoup.select(doc, "//Seeds/@stepby").get();
			if (step == null) {
				stepby = 5;
			} else {
				stepby = Integer.parseInt(Xsoup.select(doc, "//Seeds/@stepby").get());
			}
			System.out.println("config type: " + type);
			if (type.equals("seeds")) {
				System.out.println("config set success");
				List<String> topic_list = Xsoup.select(doc, "//topic-id/text()").list();
				List<String> question_list = Xsoup.select(doc, "//question-id/text()").list();
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
				return spider;
			} else if (type.equals("all")) {
				System.out.println("config set success");
				for (int i = 1; i <= stepby; i++) {
					spider.addUrl(Site.ROOT_TOPIC_URL + "?page=" + i);
					System.out.println("config question url: " + Site.ROOT_TOPIC_URL + "?page=" + i);
				}
				return spider;
			} else {
				throw new ErrorSeedClassException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ErrorSeedClassException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getStepBy() {
		return stepby;
	}

	private File getConfig() {
		configFile = new File("SpiderConfig.xml");
		return configFile;
	}
}

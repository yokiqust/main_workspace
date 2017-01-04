package cn.yokiqust.config;

import cn.yokiqust.spider.utils.Const;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.xsoup.Xsoup;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author yokiqust
 *
 */
public class CreateSpider {
	// 爬虫配置文件
	private File configFile;
	// 步长，每次抓取的页数
	private int stepby = 0;
	// 抓取条件：问题回答数
	private int question_condition;
	// 抓取条件：答案支持数
	private int answer_condition;
	// 每一问题最多抓取多少答案
	private int answer_max;
	private String mode;
	private String collect;

	public Spider getSpider(PageProcessor pageProcessor) {
		Spider spider = Spider.create(pageProcessor);
		try {
			// 载入配置文件解析
			Document doc = Jsoup.parse(getConfig(), null);
			// 获取抓取类型，all为从根话题抓取，seeds从特定问题、话题抓取
			String type = Xsoup.select(doc, "//Spider/@class").get();
			String step = Xsoup.select(doc, "//Spider/@stepby").get();
			String question_ = Xsoup.select(doc, "//Spider/if/question-condition/more/text()").get();
			String answer_ = Xsoup.select(doc, "//Spider/if/answer-condition/more/text()").get();
			String thread_ = Xsoup.select(doc, "//Spider/thread/text()").get();
			String answer_max_ = Xsoup.select(doc, "//Spider/if/question-condition/max/text()").get();
			if (step == null) {
				stepby = 10;
			} else {
				stepby = Integer.parseInt(step);
			}
			System.out.println("config type: " + type);
			type = type.trim();
			setMode(type);
			if (type.equals("seeds")) {
				System.out.println("in seeds");
				List<String> topic_list = Xsoup.select(doc, "//Spider/Seeds/topic-id/text()").list();
				List<String> question_list = Xsoup.select(doc, "//Spider/Seeds/question-id/text()").list();
				if (topic_list != null) {
					for (String str : topic_list) {
						str = str.trim();
						String[] strs = str.split(",");
						if (strs.length == 1) {
							for (int i = 1; i <= stepby; i++) {
								spider.addUrl("https://www.zhihu.com/topic/" + str + "/questions?page=" + i);
								System.out.println("config topic url: " + "https://www.zhihu.com/topic/" + str
										+ "/questions?page=" + i);
							}
						} else if (strs.length == 2) {
							int startPage = Integer.parseInt(strs[1]);
							for (int i = startPage; i <= (startPage + stepby); i++) {
								spider.addUrl("https://www.zhihu.com/topic/" + strs[0] + "/questions?page=" + i);
								System.out.println("config topic url: " + "https://www.zhihu.com/topic/" + strs[0]
										+ "/questions?page=" + i);
							}
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
				System.out.println("in all");
				for (int i = 1; i <= stepby; i++) {
					spider.addUrl(Const.ROOT_TOPIC_URL + "?page=" + i);
					System.out.println("config question url: " + Const.ROOT_TOPIC_URL + "?page=" + i);
				}
			} else if (type.equals("update")) {
				System.out.println("in update");
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
			if (answer_max_ != null) {
				answer_max_ = answer_max_.trim();
				answer_max = Integer.parseInt(answer_max_);
			} else {
				answer_max = 2000;
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

	public int getAnswer_Max() {
		return answer_max;
	}

	private File getConfig() {
		configFile = new File("src/main/resources/SpiderConfig.xml");
		return configFile;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getList() {
		Document doc = null;
		try {
			doc = Jsoup.parse(getConfig(), null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String collect_ = Xsoup.select(doc, "//Spider/collect/text()").get();
		if (collect_ != null) {
			collect_ = collect_.trim();
			collect = collect_;
		}
		String id = null;
		try {
			System.out.println(GetList.getList());
			System.out.println("\"" + collect + "\"");
			id = GetList.getList().get("\"" + collect + "\"");
			System.out.println("collect_id = " + id);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
}

package cn.yokiqust.Spider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.annotation.ThreadSafe;

import cn.yokiqust.Model.Question;
import cn.yokiqust.spiderUtils.Const;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

@ThreadSafe
public class PageProcess {
	private static Set<String> topic_url = new HashSet<String>();

	@Deprecated
	public void rootUrlProcess(Page page) {
		List<String> topics = page.getHtml().xpath("//a[@class=zm-item-tag]/text()").all();
		List<String> topic_id = page.getHtml().xpath("//a[@class=zm-item-tag]/@data-token").all();
		for (String s : topic_id) {
			System.out.println("https://www.zhihu.com/topic/" + s + "/organize/entire");
			// page.addTargetRequest("https://www.zhihu.com/topic/" + s +
			// "/organize/entire");
			// page.addTargetRequest("https://www.zhihu.com/topic/" + s +
			// "/questions");
		}
		System.out.println(topics);
	}

	@Deprecated
	public void subTopicUrlProcess(Page page) {
		List<String> topics = page.getHtml().xpath("//a[@class=zm-item-tag]/text()").all();
		List<String> topic_id = page.getHtml().xpath("//a[@class=zm-item-tag]/@data-token").all();
		for (String s : topic_id) {
			if (topics.size() != 1) {
				page.addTargetRequest("https://www.zhihu.com/topic/" + s + "/organize/entire");
			}
			// page.addTargetRequest("https://www.zhihu.com/topic/" + s +
			// "/questions");
		}
		// page.putField("url", page.getUrl());
		// page.putField("topics", topics);
	}

	public void singleTopicUrlProcess(Page page) {
		System.out.println("url:" + page.getUrl());
		List<Selectable> question_link = page.getHtml().xpath("//div[@class=feed-item ]").nodes();
		List<String> page_ = page.getHtml().xpath("//div[@class=zm-invite-pager]/span/a/text()").regex("\\d+").all();
		int max_num = Integer.parseInt(page_.get(page_.size() - 1));
		for (Selectable select : question_link) {
			String link = select.xpath("//a[@class=question_link]/@href").get();
			@SuppressWarnings("unused")
			String name = select.xpath("//a[@class=question_link]/text()").get();
			int answer_count = Integer.parseInt(select.xpath("//meta[@itemprop=answerCount]/@content").get());
			if (answer_count >= Const.spiderConfig.getQuestion_condition()) {
				System.out.println("url:" + link);
				page.addTargetRequest(link);
			}
		}
		int page_num = Integer.parseInt(page.getUrl().toString()
				.substring(page.getUrl().toString().lastIndexOf("=") + 1, page.getUrl().toString().length()));

		if ((page_num + Const.spiderConfig.getStepBy()) < max_num) {
			page.addTargetRequest(page.getUrl().toString().substring(0, page.getUrl().toString().lastIndexOf("=") + 1)
					+ (page_num + Const.spiderConfig.getStepBy()));
		}

	}

	public void questionUrlProcess(Page page) {
		System.out.println("url:" + page.getUrl());
		String question_name = page.getHtml().xpath("//h2[@class=zm-item-title ]/text()").get();
		String follow_num = page.getHtml().xpath("//div[@class=zg-gray-normal]/a/strong/text()").get();
		int answer_count = Integer.parseInt(page.getHtml().xpath("//h3/@data-num").get());
		int current_answer_count = 20;
		List<Selectable> question_list = page.getHtml().xpath("//div[@class=zm-item-answer  ]").nodes();
		String question_id = page.getUrl().toString().substring(page.getUrl().toString().lastIndexOf("/") + 1,
				page.getUrl().toString().length());
		MoreAnswerDownloader getMore = new MoreAnswerDownloader();
		while ((answer_count > 2000 ? 2000 : answer_count) > current_answer_count) {
			question_list.addAll(getMore.getMoreTopics(question_id, current_answer_count));
			current_answer_count += 20;
		}
		Question question = new Question();
		question.setAnswer_num(answer_count);
		question.setFollow_num(Integer.parseInt(follow_num));
		question.setName(question_name);
		question.setId(Integer.parseInt(question_id));
		List<Object> list = new ArrayList<Object>();
		list.add(question);
		list.addAll(question_list);
		page.putField("question", list);
		getMore.close();
	}

	public static Set<String> getTopic_url() {
		return topic_url;
	}
}

package cn.yokiqust.spider.processor;

import java.nio.file.NoSuchFileException;

import cn.yokiqust.spider.utils.PageClass;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ZhiHuPageProcessor implements PageProcessor {
	private static final PageProcess process = new PageProcess();

	public Site getSite() {
		return cn.yokiqust.spider.utils.Const.SITE;
	}

	@SuppressWarnings("deprecation")
	public void process(Page page) {
		int pClass = PageClass.getClass(page);
		switch (pClass) {
		case cn.yokiqust.spider.utils.Const.ROOT_URL:
			process.rootUrlProcess(page);
			break;
		case cn.yokiqust.spider.utils.Const.SUBTOPIC_URL:
			process.subTopicUrlProcess(page);
			break;
		case cn.yokiqust.spider.utils.Const.SINGLE_TOPIC_URL:
			process.singleTopicUrlProcess(page);
			break;
		case cn.yokiqust.spider.utils.Const.QUESTION:
			process.questionUrlProcess(page);
			break;
		default:
			try {
				throw new NoSuchFileException(page.getUrl().toString());
			} catch (NoSuchFileException e) {
				e.printStackTrace();
			}
		}
	}
}

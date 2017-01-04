package cn.yokiqust.Spider;

import java.nio.file.NoSuchFileException;

import cn.yokiqust.spiderUtils.PageClass;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ZhiHuPageProcessor implements PageProcessor {
	private static final PageProcess process = new PageProcess();

	@Override
	public Site getSite() {
		return cn.yokiqust.spiderUtils.Site.SITE;
	}

	@Override
	public void process(Page page) {
		int pClass = PageClass.getClass(page);
		switch (pClass) {
		case cn.yokiqust.spiderUtils.Site.ROOT_URL:
			process.rootUrlProcess(page);
			break;
		case cn.yokiqust.spiderUtils.Site.SUBTOPIC_URL:
			process.subTopicUrlProcess(page);
			break;
		case cn.yokiqust.spiderUtils.Site.SINGLE_TOPIC_URL:
			process.singleTopicUrlProcess(page);
			break;
		case cn.yokiqust.spiderUtils.Site.QUESTION:
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

	public static void main(String[] args) {
		Spider spider = cn.yokiqust.spiderUtils.Site.spiderConfig.getSpider(new ZhiHuPageProcessor(), 20);
		spider.addPipeline(new MyFilePipeline("/home/yokiqust/zhihu"));
		System.out.println("spider start");
		spider.run();
	}
}

package cn.yokiqust.Spider;

import java.nio.file.NoSuchFileException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.yokiqust.spiderUtils.PageClass;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Service("zhPP")
public class ZhiHuPageProcessor implements PageProcessor {
	@Autowired
	@Qualifier("pageProcess")
	private PageProcess process;
	@Autowired
	@Qualifier("login")
	private cn.yokiqust.HttpClient.HttpClientLogin login;

	public Site getSite() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++" + login.getxsrf());
		return login.login();
	}

	@SuppressWarnings("deprecation")
	public void process(Page page) {
		int pClass = PageClass.getClass(page);
		switch (pClass) {
		case cn.yokiqust.spiderUtils.Const.ROOT_URL:
			process.rootUrlProcess(page);
			break;
		case cn.yokiqust.spiderUtils.Const.SUBTOPIC_URL:
			process.subTopicUrlProcess(page);
			break;
		case cn.yokiqust.spiderUtils.Const.SINGLE_TOPIC_URL:
			process.singleTopicUrlProcess(page);
			break;
		case cn.yokiqust.spiderUtils.Const.QUESTION:
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

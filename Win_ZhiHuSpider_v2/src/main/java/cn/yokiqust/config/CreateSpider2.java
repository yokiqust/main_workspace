package cn.yokiqust.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.yokiqust.spider.pipeline.CollectAnswerPipeLine;
import cn.yokiqust.spider.processor.ZhiHuPageProcessor;
import cn.yokiqust.spider.utils.Const;
import us.codecraft.webmagic.Spider;

/**
 * 
 * @author yokiqust
 *
 */
public class CreateSpider2 {
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
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public Spider createSpider() {
		System.out.println("***************windows版知乎爬虫***************");
		Spider spider = new Spider(new ZhiHuPageProcessor());
		spider.addPipeline(new CollectAnswerPipeLine());
		mode = setMode();
		System.out.println();
		stepby = setStepBy();
		System.out.println();
		spider = setSpider(spider, mode);
		collect = setCollect();
		System.out.println();
		question_condition = setQuestion_condition();
		System.out.println();
		answer_max = 1000;
		answer_condition = setAnswer_condition();
		return spider;
	}

	private int setAnswer_condition() {
		while (true) {
			System.out.println("答案抓取条件 (≧∇≦)ﾉ：");
			System.out.println("	赞同数超过多少才能被收藏，建议为1000 (⊙ˍ⊙) ");
			System.out.print("输入：");
			try {
				String qc = br.readLine();
				if (qc != "") {
					Integer num = Integer.parseInt(qc);
					return num;
				} else {
					return 1000;
				}
			} catch (NumberFormatException e) {
				System.out.println("输入数字 o(≧口≦)o ！ ");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return -1;
		}
	}

	private int setQuestion_condition() {
		while (true) {
			System.out.println("问题抓取条件 (≧∇≦)ﾉ：");
			System.out.println("	回答数超过多少才能被进一步查看，建议为50 (⊙ˍ⊙) ");
			System.out.print("输入：");
			try {
				String qc = br.readLine();
				if (qc != "") {
					Integer num = Integer.parseInt(qc);
					return num;
				} else {
					return 50;
				}
			} catch (NumberFormatException e) {
				System.out.println("输入数字 o(≧口≦)o ！ ");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return -1;
		}
	}

	private String setCollect() {
		while (true) {
			System.out.println("收藏夹名称 ╰(￣▽￣)╭ ：");
			System.out.println("	必须是在自己创建的，且名称要一毛一样 ⊙▽⊙ ");
			System.out.print("输入吧：");
			try {
				String s = br.readLine();
				String id = GetList.getList().get("\"" + s + "\"");
				if (id == null) {
					System.out.println("没有这个收藏夹 (;´༎ຶД༎ຶ`) 你的收藏夹有：" + showSet(GetList.getList().keySet()));
					continue;
				}
				return id;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private String showSet(Set<String> keySet) {
		StringBuilder sb = new StringBuilder();
		for (String s : keySet) {
			sb.append(s.substring(1, s.length() - 1));
			sb.append(", ");
		}
		return sb.toString().substring(0, sb.toString().length() - 2);
	}

	private int setStepBy() {
		while (true) {
			System.out.println("设置步长 n(*≧▽≦*)n ：");
			System.out.println("	步长就是抓取话题时每次抓取几页,建议为10");
			System.out.print("输入步长（必须是数字哦 (⊙ˍ⊙) ）：");
			try {
				String s = br.readLine();
				if (s != "\n") {
					Integer num = Integer.parseInt(s);
					return num;
				} else {
					Integer num = 10;
					return num;
				}
			} catch (NumberFormatException e) {
				System.out.println("都说了必须是数字 o(≧口≦)o ！ ");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String setMode() {
		while (true) {
			System.out.println("请选择爬虫模式(๑•̀ㅂ•́)و✧：");
			System.out.println("	1.全局模式（无论什么话题只要符合条件就要！ヾ(≧∇≦*)ゝ ）；");
			System.out.println("	2.种子模式（把问题或者话题网址加入到config文件夹中的url.txt中就行了，每个网址一行哦 <(￣︶￣)↗[GO!] ）；");
			System.out.println("	3.增量更新模式（windows版暂不支持 o(TヘTo) ）；");
			try {
				System.out.print("在这里输入吧：");
				String in = br.readLine();
				Integer num = Integer.parseInt(in);
				switch (num) {
				case 1:
					System.out.println("全局模式启动 (o゜▽゜)o☆[BINGO!] ");
					return "all";
				case 2:
					System.out.println("种子模式启动 (๑•̀ㅂ•́)و✧ ");
					return "seeds";
				case 3:
					System.out.println("都说了不支持 (*￣︿￣) ");
					continue;
				default:
					System.out.println("请输入1,2,3这三个数字 ◐▽◑  ");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("请输入1,2,3这三个数字 ◐▽◑  ");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Spider setSpider(Spider spider, String mode) {
		if (mode.equals("all")) {
			for (int i = 1; i <= stepby; i++) {
				spider.addUrl(Const.ROOT_TOPIC_URL + "?page=" + i);
			}
		} else if (mode.equals("seeds")) {
			BufferedReader fr = null;
			try {
				fr = new BufferedReader(new FileReader("config/url.txt"));
				String url = fr.readLine();
				while (url != null) {
					if (url.matches("https://www.zhihu.com/question/(\\d+)(.*)")) {
						Pattern p = Pattern.compile("https://www.zhihu.com/question/(\\d+)(.*)");
						Matcher m = p.matcher(url);
						if (m.find()) {
							String question_ = m.group(1);
							spider.addUrl("https://www.zhihu.com/question/" + question_);
						}
					} else if (url.matches("https://www.zhihu.com/topic/(\\d+)(.*)")) {
						Pattern p = Pattern.compile("https://www.zhihu.com/topic/(\\d+)(.*)");
						Matcher m = p.matcher(url);
						if (m.find()) {
							String topic_ = m.group(1);
							for (int i = 1; i <= stepby; i++) {
								spider.addUrl("https://www.zhihu.com/topic/" + topic_ + "/questions?page=" + i);
							}
						}
					}
					url = fr.readLine();
				}
			} catch (FileNotFoundException e) {
				System.out.println("没有这个文件，在当前文件夹中的config文件夹中没有url.txt这个文件  ┭┮﹏┭┮  ");
				System.out.print("按任意键退出：");
				String s = null;
				try {
					s = br.readLine();
					if (s != "") {
						System.exit(1);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return spider;
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

	public String getMode() {
		return mode;
	}

	public String getCollect() {
		return collect;
	}
}

package cn.yokiqust.Spider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.http.annotation.ThreadSafe;

import cn.yokiqust.Model.Answer;
import cn.yokiqust.Model.Author;
import cn.yokiqust.Model.Question;
import cn.yokiqust.spiderUtils.Site;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.FilePersistentBase;

@ThreadSafe
public class MyFilePipeline extends FilePersistentBase implements Pipeline {
	public MyFilePipeline() {
		setPath("/data/webmagic/");
	}

	public MyFilePipeline(String path) {
		setPath(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		String path = this.path + PATH_SEPERATOR;
		try {
			PrintWriter printWriter = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(getFile(path + "result" + ".txt"), true), "UTF-8"));
			Question question = null;
			if (resultItems.get("question") != null) {
				System.out.println("come in");
				for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
					List<Object> value = (List<Object>) entry.getValue();
					for (Object obj : value) {
						if (obj instanceof Question) {
							System.out.println("get question");
							question = Question.class.cast(obj);
							System.out.println(question);
							printWriter.println(question.getName() + " 回答数: " + question.getAnswer_num() + " 关注数: "
									+ question.getFollow_num());
						} else if (obj instanceof Selectable) {
							Selectable select = Selectable.class.cast(obj);
							Author author = getAuthor(select);
							Answer answer = new Answer();
							answer.setId(
									Integer.parseInt(select.xpath("//div[@class=zm-item-answer ]/@data-aid").get()));
							answer.setCount(select.xpath("//span[@class=count]/text()").get());
							answer.setAuthor(author);
							answer.setQuestion(question);
							if (answer.getCount().endsWith("K")) {
								printWriter
										.println(answer.getQuestion().getName() + " 作者: " + answer.getAuthor().getName()
												+ "[" + answer.getAuthor().getBio() + "] 赞同数: " + answer.getCount());
							} else {
								int count = Integer.parseInt(answer.getCount());
								if (count >= 1000) {
									printWriter.println(answer.getQuestion().getName() + " 作者: "
											+ answer.getAuthor().getName() + "[" + answer.getAuthor().getBio()
											+ "] 赞同数: " + answer.getCount());
								}
							}
						} else {
							System.out.println("nothing");
						}
					}

				}
			}
			printWriter.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	private Author getAuthor(Selectable selectable) {
		String author_name = selectable.xpath("//a[@class=author-link]/text()").get();
		Author author = null;
		if (author_name != null) {
			author = new Author();
			author.setName(author_name);
			String bio = selectable.xpath("//span[@class=bio]/text()").get();
			if (bio != null) {
				author.setBio(bio.substring(1, bio.length()));
			} else {
				author.setBio(null);
			}
			author.setId(selectable.xpath("//a[@class=author-link]/@href").get().substring(
					selectable.xpath("//a[@class=author-link]/@href").get().lastIndexOf("/") + 1,
					selectable.xpath("//a[@class=author-link]/@href").get().length()));
			return author;
		}
		return Site.anoyAuthor;
	}

}

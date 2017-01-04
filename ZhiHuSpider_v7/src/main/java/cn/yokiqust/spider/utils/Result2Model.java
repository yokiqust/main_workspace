package cn.yokiqust.spider.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.yokiqust.model.Answer;
import cn.yokiqust.model.Author;
import cn.yokiqust.model.Question;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.selector.Selectable;

public class Result2Model {

	public static Question result2Question(ResultItems resultItems) {
		Question question;
		if (resultItems.get("question") != null) {
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				@SuppressWarnings("unchecked")
				List<Object> value = (List<Object>) entry.getValue();
				for (Object obj : value) {
					if (obj instanceof Question) {
						question = Question.class.cast(obj);
						return question;
					}
				}
			}
		}
		return null;
	}

	public static List<Answer> result2Answer(ResultItems resultItems) {
		Question question = result2Question(resultItems);
		List<Answer> answers = new ArrayList<Answer>();
		if (resultItems.get("question") != null) {
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				@SuppressWarnings("unchecked")
				List<Object> value = (List<Object>) entry.getValue();
				for (Object obj : value) {
					if (obj instanceof Selectable) {
						Selectable select = Selectable.class.cast(obj);
						Author author = getAuthor(select);
						Answer answer = new Answer();
						String answer_id = select
								.xpath("//div[@class=zm-item-answer ]/div[@class=zm-item-rich-text ]/@data-entry-url")
								.get();
						
						if (answer_id == null) {
							continue;
						}
		
						answer.setId(Integer
								.parseInt(answer_id.substring(answer_id.lastIndexOf("/") + 1, answer_id.length())));
						answer.setCount(select.xpath("//span[@class=count]/text()").get());
						answer.setAuthor(author);
						answer.setQuestion(question);
						answer.setAnswer_id(
								Integer.parseInt(select.xpath("//div[@class=zm-item-answer ]/@data-aid").get()));

						answer.setAnswer_content(
								select.regex("<div class=\"zm-editable-content clearfix\">(.*?)</div>").toString());
						int count = 0;
						if (answer.getCount().endsWith("K")) {
							count = Integer.parseInt(answer.getCount().substring(0, answer.getCount().length() - 1));
							count *= 1000;
						} else {
							count = Integer.parseInt(answer.getCount());
						}
						if (count >= Const.spiderConfig.getAnswer_condition()) {
							answers.add(answer);
						}
					}
				}
			}
		}
		return answers;
	}

	private static Author getAuthor(Selectable selectable) {
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
		return Const.anoyAuthor;
	}
}

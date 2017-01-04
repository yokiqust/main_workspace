package cn.yokiqust.spider.pipeline;

import java.util.List;

import org.apache.http.annotation.ThreadSafe;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yokiqust.dao.AnswerDAO;
import cn.yokiqust.dao.AuthorDAO;
import cn.yokiqust.dao.QuestionDAO;
import cn.yokiqust.model.Answer;
import cn.yokiqust.spider.utils.Result2Model;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@ThreadSafe
public class MysqlPipeLine implements Pipeline {
	private ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/Beans.xml");
	private AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
	private QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");
	private AuthorDAO authorDAO = (AuthorDAO) context.getBean("authorDAO");

	public void process(ResultItems resultItems, Task task) {
		if (resultItems.get("question") != null) {
			List<Answer> answers = Result2Model.result2Answer(resultItems);
			if (answers.size() > 0) {
				questionDAO.addQuestion(answers.get(0).getQuestion());
				for (Answer answer : answers) {
					answerDAO.addAnswer(answer);
					authorDAO.addAuthor(answer.getAuthor());
				}
			}
		}
	}
}

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yokiqust.dao.AnswerDAO;
import cn.yokiqust.dao.QuestionDAO;
import cn.yokiqust.model.Answer;
import cn.yokiqust.model.Question;

public class Test {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/Beans.xml");
	private static AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
	private static QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");

	public static void main(String[] args) {
		int i = 0;
		double followCount = 0, answerCount = 0;
		List<Answer> answers = answerDAO.getAnswer();
		for (Answer answer : answers) {
			if (answer.getCount().endsWith("K")) {
				Question question = questionDAO.getQuestionById(answer.getQuestion().getId());
				followCount += question.getFollow_num();
				answerCount += question.getAnswer_num();
				i++;
			} else {
				int num = Integer.parseInt(answer.getCount());
				if (num >= 10000) {
					Question question = questionDAO.getQuestionById(answer.getQuestion().getId());
					followCount += question.getFollow_num();
					answerCount += question.getAnswer_num();
					i++;
				}
			}
		}
		System.out.println(followCount / i);
		System.out.println(answerCount / i);
	}
}

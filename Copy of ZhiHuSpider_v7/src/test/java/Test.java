import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yokiqust.dao.AnswerDAO;
import cn.yokiqust.model.Answer;

public class Test {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/Beans.xml");
	private static AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");

	public static void main(String[] args) {
		List<Answer> answers = answerDAO.getAnswer();
		for (Answer answer : answers) {
			if (answer.getCount().endsWith("K")) {
				continue;
			} else {
				int num = Integer.parseInt(answer.getCount());
				if (num < 1000) {
					// answerDAO.deleteAnswer(answer.getId());
					System.out.println(answer.getCount());
				}
			}
		}
	}
}

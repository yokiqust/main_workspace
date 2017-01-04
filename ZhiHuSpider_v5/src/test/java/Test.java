import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yokiqust.dao.AnswerDAO;
import cn.yokiqust.model.Answer;

public class Test {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/Beans.xml");
	private static AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");

	public static void main(String[] args) {
		String content = answerDAO.getAnswer(29242931);
		// System.out.println(content);
		Answer answer = new Answer();
		answer.setAnswer_content(content);
	}
}

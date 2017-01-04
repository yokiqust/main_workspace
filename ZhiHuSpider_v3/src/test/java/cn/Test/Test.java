package cn.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yokiqust.Model.Answer;
import cn.yokiqust.Model.Author;
import cn.yokiqust.Model.Question;
import cn.yokiqust.dao.AnswerDAO;

public class Test {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/Beans.xml");
		AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
		Answer answer = new Answer();
		Question question = new Question();
		question.setAnswer_num(100);
		question.setFollow_num(100);
		question.setId(123);
		question.setName("测试问题");
		Author author = new Author();
		author.setBio("233333");
		author.setId("wwwww");
		author.setName("测试用户");
		answer.setAuthor(author);
		answer.setCount("1k");
		answer.setId(123);
		answer.setQuestion(question);
		answerDAO.addAnswer(answer);
	}
}

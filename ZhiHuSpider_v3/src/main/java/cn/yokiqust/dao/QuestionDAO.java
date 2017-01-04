package cn.yokiqust.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.yokiqust.Model.Question;

public class QuestionDAO {
	private JdbcTemplate jdbcTemplate;

	public boolean addQuestion(Question question) {
		String sql = "insert into question values(?,?,?,?)";
		int col = jdbcTemplate.update(sql, question.getId(), question.getFollow_num(), question.getAnswer_num(),
				question.getName());
		if (col == 0) {
			return false;
		}
		return true;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

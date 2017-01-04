package cn.yokiqust.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.yokiqust.model.Question;

public class QuestionDAO {
	private JdbcTemplate jdbcTemplate;

	public boolean addQuestion(Question question) {
		String sql = "insert into question values(?,?,?,?)";
		String sql1 = "select count(*) from question where id=?";
		int c = jdbcTemplate.queryForObject(sql1, new Object[] { question.getId() }, Integer.class);
		int col = 0;
		if (c == 0) {
			col = jdbcTemplate.update(sql, question.getId(), question.getFollow_num(), question.getAnswer_num(),
					question.getName());
		}
		if (col == 0) {
			return false;
		}
		return true;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

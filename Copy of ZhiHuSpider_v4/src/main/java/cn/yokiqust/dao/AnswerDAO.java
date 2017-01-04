package cn.yokiqust.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.yokiqust.model.Answer;

public class AnswerDAO {
	private JdbcTemplate jdbcTemplate;

	public boolean addAnswer(Answer answer) {
		String sql = "insert into answer values(?,?,?,?)";
		String sql1 = "select count(*) from answer where id=?";
		int col = 0;
		int c = jdbcTemplate.queryForObject(sql1, new Object[] { answer.getId() }, Integer.class);
		if (c == 0) {
			col = jdbcTemplate.update(sql, answer.getId(), answer.getQuestion().getId(), answer.getAuthor().getId(),
					answer.getCount());
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

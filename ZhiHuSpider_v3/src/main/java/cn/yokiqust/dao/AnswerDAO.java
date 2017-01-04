package cn.yokiqust.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.yokiqust.Model.Answer;

public class AnswerDAO {
	private JdbcTemplate jdbcTemplate;

	public boolean addAnswer(Answer answer) {
		String sql = "insert into answer values(?,?,?,?)";
		int col = jdbcTemplate.update(sql, answer.getId(), answer.getQuestion().getId(), answer.getAuthor().getId(),
				answer.getCount());
		if (col == 0) {
			return false;
		}
		return true;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

package cn.yokiqust.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.yokiqust.model.Answer;

public class AnswerDAO {
	private JdbcTemplate jdbcTemplate;

	public boolean addAnswer(Answer answer) {
		String sql = "insert into answer values(?,?,?,?,?)";
		String sql1 = "select count(*) from answer where id=?";
		String update = "update answer set count=?,answer_content=? where id=?";
		int col = 0;
		int c = jdbcTemplate.queryForObject(sql1, new Object[] { answer.getId() }, Integer.class);
		if (c == 0) {
			col = jdbcTemplate.update(sql, answer.getId(), answer.getQuestion().getId(), answer.getAuthor().getId(),
					answer.getCount(), answer.getAnswer_content());
		} else {
			col = jdbcTemplate.update(update, answer.getCount(), answer.getAnswer_content(), answer.getId());
		}
		if (col == 0) {
			return false;
		}
		return true;
	}

	public String getAnswer(int id) {
		String sql = "select answer_content from answer where id = " + id;
		String str = jdbcTemplate.queryForObject(sql, String.class);
		return str;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

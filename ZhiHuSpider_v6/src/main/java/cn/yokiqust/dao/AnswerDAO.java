package cn.yokiqust.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

	public List<Answer> getAnswer() {
		String sql = "select * from answer";
		List<Answer> answers = jdbcTemplate.query(sql, new RowMapper<Answer>() {
			public Answer mapRow(ResultSet result, int arg1) throws SQLException {
				Answer answer = new Answer();
				answer.setId(result.getInt("id"));
				answer.setCount(result.getString("count"));
				return answer;
			}
		});
		return answers;
	}

	public void deleteAnswer(int id) {
		String sql = "delete from answer where id = ?";
		jdbcTemplate.update(sql, id);
	}
}

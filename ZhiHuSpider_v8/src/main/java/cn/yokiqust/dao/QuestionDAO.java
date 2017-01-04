package cn.yokiqust.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cn.yokiqust.model.Question;

public class QuestionDAO {
	private JdbcTemplate jdbcTemplate;

	public boolean addQuestion(Question question) {
		String sql = "insert into question values(?,?,?,?,?)";
		String sql1 = "select count(*) from question where id=?";
		String update = "update question set follow_num=?,answer_num=?,tips=? where id=?";
		int c = jdbcTemplate.queryForObject(sql1, new Object[] { question.getId() }, Integer.class);
		int col = 0;
		if (c == 0) {
			col = jdbcTemplate.update(sql, question.getId(), question.getFollow_num(), question.getAnswer_num(),
					question.getName(), question.getTipsString());
		} else {
			col = jdbcTemplate.update(update, question.getFollow_num(), question.getAnswer_num(),
					question.getTipsString(), question.getId());
		}
		if (col == 0) {
			return false;
		}
		return true;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Map<String, Object>> getQuestion() {
		String sql = "select id from question";
		List<Map<String, Object>> ids = jdbcTemplate.queryForList(sql);
		return ids;
	}

	public Question getQuestionById(int id) {
		String sql = "select * from question where id=?";
		Question question = jdbcTemplate.queryForObject(sql, new Object[] { id }, new RowMapper<Question>() {

			public Question mapRow(ResultSet result, int arg1) throws SQLException {
				Question question = new Question();
				question.setId(result.getInt("id"));
				question.setFollow_num(result.getInt("follow_num"));
				question.setAnswer_num(result.getInt("answer_num"));
				question.setName(result.getString("name"));
				question.setTips(Arrays.asList(result.getString("tips").split(",")));
				return question;
			}
		});
		return question;
	}

}

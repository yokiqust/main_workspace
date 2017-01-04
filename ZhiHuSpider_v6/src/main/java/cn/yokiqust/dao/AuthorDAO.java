package cn.yokiqust.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.yokiqust.model.Author;

public class AuthorDAO {
	private JdbcTemplate jdbcTemplate;

	public boolean addAuthor(Author author) {
		String sql = "insert into author values(?,?,?)";
		String sql1 = "select count(*) from author where id=?";
		String update = "update author set bio=? where id=?";
		int c = jdbcTemplate.queryForObject(sql1, new Object[] { author.getId() }, Integer.class);
		int col = 0;
		if (c == 0) {
			col = jdbcTemplate.update(sql, author.getId(), author.getName(), author.getBio());
		} else {
			col = jdbcTemplate.update(update, author.getBio(), author.getId());
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

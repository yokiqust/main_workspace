package cn.yokiqust.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.yokiqust.Model.Author;

public class AuthorDAO {
	private JdbcTemplate jdbcTemplate;

	public boolean addAuthor(Author author) {
		String sql = "insert into author values(?,?,?)";
		int col = jdbcTemplate.update(sql, author.getId(), author.getName(), author.getBio());
		if (col == 0) {
			return false;
		}
		return true;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

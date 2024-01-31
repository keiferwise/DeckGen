package com.kif.deckgenmodels.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kif.deckgenmodels.User;

public class UserRowMapper implements RowMapper<User>{

	public UserRowMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();

		user.setAdmin(rs.getBoolean("admin"));
		
		return user;
	}

}

package com.kif.deckgenmodels.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kif.deckgenmodels.User;

@Component
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

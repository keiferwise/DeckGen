package com.kif.deckgenmodels.daos;

import java.math.BigInteger;
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
		user.setEmail(rs.getString("email"));
		user.setName(rs.getString("name"));
		user.setUserId(rs.getString("user_id"));
		user.setUserName(rs.getString("username"));
		user.setRole(rs.getString("ROLE"));
		BigInteger tokens = BigInteger.valueOf(rs.getInt("tokens"));
		user.setTokens(tokens);
		user.setEnabled(rs.getBoolean("enabled"));
		user.setAdmin(rs.getBoolean("admin"));

		return user;
	}

}

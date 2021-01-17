package com.anish.weather.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.anish.weather.model.User;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	// This method finds a 'User' object from the database which matches the given username
	public User findByUsername(String username) {
		
		// Finding the number of users with the given username
		int numOfUsers = findNumOfUsers(username);
		
		// If there is no user with the given username, returning null
		if(numOfUsers == 0)
			return null;
		
		return jdbc.queryForObject("select UserId, FirstName, LastName, Username, Password from [User] where Username = ?",
				this::mapRowToUser, username);
	}
	
	@Override
	// This method finds the number of users with the given username
	public Integer findNumOfUsers(String username) {
		return jdbc.queryForObject("select COUNT(UserId) as UserId from [User] where Username=?", this::mapRowToInt, username);
	}
	
	// This method maps each row in the result set to a 'User' object and returns it
	private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
		
		return new User(rs.getLong("UserId"), rs.getString("FirstName"), rs.getString("LastName"),
				rs.getString("Username"), rs.getString("Password"));
	}
	
	// This method maps the only row of the result set to an Integer
	private Integer mapRowToInt(ResultSet rs, int rowNum) throws SQLException {
		return rs.getInt("UserId");
	}

	@Override
	// This method saves the given user into the database
	public User save(User user) {
		
		// Stores the primary key in the database table
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		// Creating the sql command
		PreparedStatementCreatorFactory pscFact = new PreparedStatementCreatorFactory("insert into [User]" +
				"(FirstName, LastName, Username, Password) values (?, ?, ?, ?)",
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR);
		
		// Setting the generated keys column names
		pscFact.setGeneratedKeysColumnNames(new String[] {"UserId"});
		
		// Giving the parameters for the above sql command
		PreparedStatementCreator psc = pscFact.newPreparedStatementCreator(Arrays.asList(user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getPassword()));
		
		// Updating the generated key in the database
		jdbc.update(psc, generatedKeyHolder);
		
		// Setting the generated key into the 'User' object
		user.setUserId(generatedKeyHolder.getKey().longValue());
		
		// Returning the 'User' object
		return user;
	}
}

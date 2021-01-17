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
public class JdbcUserCitiesRepositoryImpl implements UserCitiesRepository {
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	// This function saves a city for a user
	public void saveUserCity(User user, Long cityId) {
		
		// Holds the generated primary key
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		// Preparing the SQL command
		PreparedStatementCreatorFactory pscFact = new PreparedStatementCreatorFactory("insert into UserCities (UserId, CityId)"
				+ "values(?, ?)", Types.VARCHAR, Types.BIGINT);
		
		// Setting the primary key column
		pscFact.setGeneratedKeysColumnNames(new String[] {"UserCitiesId"});
		
		// Providing the parameters for the SQL statement
		PreparedStatementCreator psc = pscFact.newPreparedStatementCreator(Arrays.asList(user.getUserId(), cityId));
		
		// Executing the statement
		jdbc.update(psc, generatedKeyHolder);
	}

	@Override
	// This function returns all the city names of a user
	public Iterable<Long> getAllUserCities(User user) {	
		
		// Querying for the required rows
		return jdbc.query("select CityId from UserCities where UserId = ?", this::mapRowToUserCity, user.getUserId());
	}
	
	// This function maps each row of the result set to the name of the city
	private Long mapRowToUserCity(ResultSet rs, int rowNum) throws SQLException {
		
		return rs.getLong("CityId");
	}

	@Override
	// This function deletes a city from the collection of a user
	public void deleteCityFromUser(User user, Long cityId) {
		
		// Carrying out the SQL command to delete a city from the user's collection
		jdbc.update("delete from UserCities where UserId = ? and CityId = ?", user.getUserId(), cityId);
	}

	@Override
	// This function deletes all the cities from a user's collection
	public void deleteAllCitiesOfUser(User user) {
		
		// Carrying out the SQL command to delete all the cities from the user's collection
		jdbc.update("delete from UserCities where UserId = ?", user.getUserId());
	}
}

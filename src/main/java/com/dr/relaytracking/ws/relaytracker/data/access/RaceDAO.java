
package com.dr.relaytracking.ws.relaytracker.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.dr.relaytracking.ws.relaytracker.entity.Race;

@Component
public class RaceDAO {
	private static final String RACE_COLUMNS_ALL = "raceid,racename,racetype,racedate,racewebsite,createdbyid,createdbyname,recordstatus";
	private static final String RACE_COLUMNS_FIELDS = "racename,racetype,racedate,racewebsite,createdbyid,createdbyname";
	
	private static final String GET_RACE_BY_ID = "Select "+ RACE_COLUMNS_ALL + " from race where raceid = ?";
	private static final String GET_RACES = "Select "+ RACE_COLUMNS_ALL + " from race";
	
	private static final String INSERT_RACE = "INSERT INTO race ("+RACE_COLUMNS_FIELDS+") VALUES(?,?,?,?,?,?)";
	private static final String UPDATE_RACE = "UPDATE race SET racename = ?,racetype = ?,racedate = ?, racewebsite = ?,createdbyid = ?,createdbyname = ? WHERE raceid = ?";
	private static final String DELETE_RACE = "DELETE FROM race WHERE raceid = ?";

	@Autowired
	 private JdbcTemplate jdbcTemplate; 
	
	private RowMapper<Race> buildRacesFtn = (rs,rowNum) -> {
		return buildRace(rs);
	};
 
	private ResultSetExtractor<Race> buildRaceFtn = (rs)-> {
		if (rs.next()){
			return buildRace(rs);
			}else{
				throw new SQLException("No records returned");
			}
		
	};
	
	public int delete(Long raceId){
		return this.jdbcTemplate.update(new PreparedStatementCreator(){
			public PreparedStatement createPreparedStatement(Connection connection)
				    throws SQLException {
				    PreparedStatement ps =connection.prepareStatement(DELETE_RACE);
				    ps.setLong(1, raceId);
				    return ps;
				}
				});
	}
	
	public int update(Race race){
		int row= this.jdbcTemplate.update(new PreparedStatementCreator(){
		public PreparedStatement createPreparedStatement(Connection connection)
		    throws SQLException {
		    PreparedStatement ps =connection.prepareStatement(UPDATE_RACE);
		    ps.setString(1, race.getRaceName());
		    ps.setString(2, race.getRaceType());
		    ps.setDate(3, java.sql.Date.valueOf(race.getRaceDate()));
		    ps.setString(4, race.getRaceWebsite());
		    ps.setString(5, race.getCreatedById());
		    ps.setString(6, race.getCreatedByName());
		    ps.setLong(7, race.getRaceid());
		    return ps;
		}
		});

		return row;
	}
	
	public Race insert(Race race){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row= this.jdbcTemplate.update(new PreparedStatementCreator(){
		public PreparedStatement createPreparedStatement(Connection connection)
		    throws SQLException {
		    PreparedStatement ps =connection.prepareStatement(INSERT_RACE, new String[] { "raceid" });
		    ps.setString(1, race.getRaceName());
		    ps.setString(2, race.getRaceType());
		    ps.setDate(3, java.sql.Date.valueOf(race.getRaceDate()));
		    ps.setString(4, race.getRaceWebsite());
		    ps.setString(5, race.getCreatedById());
		    ps.setString(6, race.getCreatedByName());
		    return ps;
		}
		},keyHolder);

		if (row > 0)
			race.setRaceid(keyHolder.getKey().longValue()); //line 72
		return race;
	}
	
	public Race findByID(Long id){
		Object [] params = {id};
		return jdbcTemplate.query(GET_RACE_BY_ID,params,buildRaceFtn);
	}
	
	public List<Race> findAll(){
		return jdbcTemplate.query(GET_RACES,buildRacesFtn);
	}
	
	
	private Race buildRace(ResultSet rs)throws SQLException{
		return new Race(rs.getLong("raceid"),
				 rs.getString("racename"),
				 rs.getString("racetype"),
				 (LocalDate)rs.getObject("raceDate",LocalDate.class),
				 rs.getString("racewebsite"),
				 rs.getString("createdbyid"),
				 rs.getString("createdbyname"),
				 rs.getString("recordStatus"));
	}
}

package com.dr.relaytracking.ws.relaytracker.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.dr.relaytracking.ws.relaytracker.entity.RaceLeg;
@Component
public class RaceLegDAO {
	
	private static final String RACELEG_COLUMNS_ALL = "racelegid,race_leg,race_segment,run, van, status, distance, difficulty, elevationgain, elevationloss, relativedistance, legfactor, raceid, created_at, recordstatus";
	private static final String RACELEG_COLUMNS_BUILD = "race_leg,race_segment,run, van, status,legfactor, raceid";
	
	private static final String GET_RACELEG_BY_RACEID = "Select "+ RACELEG_COLUMNS_ALL + " from raceleg where raceid = ?";
	private static final String INSERT_RACELEG_BUILD = "INSERT INTO raceleg ("+RACELEG_COLUMNS_BUILD+") VALUES(?,?,?,?,?,?,?)";
	private static final String DELETE_RACELEGS_BY_RACEID = "DELETE FROM raceleg where raceid =?";

	private static final String UPDATE_RACE = "UPDATE raceleg SET distance = ?,difficulty = ?,elevationgain = ?, elevationloss WHERE racelegid = ?";
	
	@Autowired
	 private JdbcTemplate jdbcTemplate; 
	
	private RowMapper<RaceLeg> buildRaceLegsFtn = (rs,rowNum) -> {
		return buildRaceLeg(rs);
	};
	public List<RaceLeg> findByRaceId(Long id){
		Object [] params = {id};
		return jdbcTemplate.query(GET_RACELEG_BY_RACEID,params,buildRaceLegsFtn);
	}
	
	public int deleteByRaceid(Long raceid){
		return this.jdbcTemplate.update(new PreparedStatementCreator(){
			public PreparedStatement createPreparedStatement(Connection connection)
				    throws SQLException {
				    PreparedStatement ps =connection.prepareStatement(DELETE_RACELEGS_BY_RACEID);
				    ps.setLong(1, raceid);
				    return ps;
				}
				});
	}
	
	public int update(RaceLeg raceleg){
		int row= this.jdbcTemplate.update(new PreparedStatementCreator(){
		public PreparedStatement createPreparedStatement(Connection connection)
		    throws SQLException {
		    PreparedStatement ps =connection.prepareStatement(UPDATE_RACE);
		    ps.setDouble(1, raceleg.getDistance());
		    ps.setString(2, raceleg.getDifficulty());
		    ps.setInt(3, raceleg.getElevationGain());
		    ps.setInt(4, raceleg.getElevationLoss());
		    ps.setLong(5, raceleg.getRacelegid());
		    return ps;
		}
		});

		return row;
	}
	
	public List<RaceLeg> buildLegsFromTemplate(List<RaceLeg> legs){
		legs.forEach(r ->{buildLegFromTemplate(r);});
		return legs;
	}
	public RaceLeg buildLegFromTemplate(RaceLeg raceleg){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row= this.jdbcTemplate.update(new PreparedStatementCreator(){
		public PreparedStatement createPreparedStatement(Connection connection)
		    throws SQLException {
		    PreparedStatement ps =connection.prepareStatement(INSERT_RACELEG_BUILD, new String[] { "racelegid" });
		    ps.setInt(1, raceleg.getRace_leg());
		    ps.setInt(2, raceleg.getRace_segment());
		    ps.setInt(3, raceleg.getRun());
		    ps.setInt(4, raceleg.getVan());	    
		    ps.setString(5, raceleg.getStatus());
		    ps.setDouble(6, raceleg.getLegfactor());
		    ps.setLong(7, raceleg.getRaceid());
		    return ps;
		}
		},keyHolder);

		if (row > 0)
			raceleg.setRacelegid(keyHolder.getKey().longValue()); //line 72
		return raceleg;
	}
	
	private RaceLeg buildRaceLeg(ResultSet rs)throws SQLException{
		RaceLeg rl =  new RaceLeg(rs.getLong("racelegid"),
				 rs.getInt("race_leg"),
				 rs.getInt("race_segment"),
				 rs.getInt("run"),
				 rs.getInt("van"),
				 rs.getString("status"),
				 rs.getDouble("legfactor"),
				 rs.getLong("raceid"));
		rl.setDistance(rs.getDouble("distance"));
		rl.setDifficulty(rs.getString("difficulty"));
		rl.setElevationGain(rs.getInt("elevationgain"));
		rl.setElevationLoss(rs.getInt("elevationloss"));
		rl.setRecordStatus(rs.getString("recordStatus"));
		return rl;
	}

}

package com.dr.relaytracking.ws.relaytracker.data.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.dr.relaytracking.ws.relaytracker.entity.RaceTemplate;

@Component
public class RaceTemplateDAO {
	
	private final static String RACETEMPLATE_COLUMNS_ALL = "id,race_leg,race_segment,run, van, status, series,legfactor";
	
	private static final String GET_RACETEMPLATE_BY_SERIES = "Select "+ RACETEMPLATE_COLUMNS_ALL + " from racetemplate where series = ?";
	private static final String GET_RACETEMPLATES = "Select "+ RACETEMPLATE_COLUMNS_ALL + " from racetemplate";
	
	@Autowired
	 private JdbcTemplate jdbcTemplate; 
	
	private RowMapper<RaceTemplate> buildTemplatesFtn = (rs,rowNum) -> {
		return buildTemplate(rs);
	};

//	private ResultSetExtractor<RaceTemplate> buildTemplateFtn = (rs)-> {
//		if (rs.next()){
//			return buildTemplate(rs);
//			}else{
//				throw new SQLException("No records returned");
//			}
//		
//	};
	
	public List<RaceTemplate> findAll(){
		return jdbcTemplate.query(GET_RACETEMPLATES,buildTemplatesFtn);
	}
	public List<RaceTemplate> findBySeries(String series){
		Object [] params = {series};
		return jdbcTemplate.query(GET_RACETEMPLATE_BY_SERIES,params,buildTemplatesFtn);
	}
    
    private RaceTemplate buildTemplate(ResultSet rs)throws SQLException{
		RaceTemplate rt =  new RaceTemplate(rs.getLong("id"),
				 rs.getInt("race_leg"),
				 rs.getInt("race_segment"),
				 rs.getInt("run"),
				 rs.getString("status"),
				 rs.getString("series"));
		rt.setVan(rs.getInt("van"));
		rt.setLegfactor(rs.getDouble("legfactor"));
		return rt;
	}
}


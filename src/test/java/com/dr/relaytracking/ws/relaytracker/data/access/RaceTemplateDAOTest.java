package com.dr.relaytracking.ws.relaytracker.data.access;
	
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.dr.relaytracking.ws.relaytracker.entity.Race;
import com.dr.relaytracking.ws.relaytracker.entity.RaceTemplate;

@RunWith(SpringRunner.class)
public class RaceTemplateDAOTest  {
		@MockBean 
		private JdbcTemplate jdbcTemplate; 
		@Autowired
		private RaceTemplateDAO raceTemplateDAO;
		@MockBean
		private ResultSetExtractor<Race> rse;
		
		private RaceTemplate mockRaceTemplate;
		
		@Before
		public void setup(){
			mockRaceTemplate = buildRaceTemplate(new Long(2),1,1,1,1,"info","trail");
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void findBySeries() {
			List<RaceTemplate> races = new ArrayList<RaceTemplate>();
			races.add(mockRaceTemplate);
			when(jdbcTemplate.query(anyString(),(RowMapper<RaceTemplate>) Mockito.anyObject())).thenReturn(races);
			List<RaceTemplate> actual = raceTemplateDAO.findBySeries(Mockito.anyString());
			
			List<RaceTemplate> expected = new ArrayList<RaceTemplate>();
			expected.add(buildRaceTemplate(new Long(2),1,1,1,1,"info","trail"));
			assertEquals(expected, actual);
			verify(jdbcTemplate, atLeastOnce()).query(anyString(),(RowMapper<Race>) anyObject());
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void findBySeries_Exception() {
			DataAccessException expected = new RecoverableDataAccessException("Test Exception");
			when(jdbcTemplate.query(anyString(),(RowMapper<RaceTemplate>) Mockito.anyObject())).thenThrow(expected);
			
			try {
				raceTemplateDAO.findBySeries(Mockito.anyString());
			} catch (DataAccessException actual) {
				assertEquals(expected.getMessage(), actual.getMessage());
			}
			
			verify(jdbcTemplate, atLeastOnce()).query(anyString(),(RowMapper<RaceTemplate>) Mockito.anyObject());
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void findAll() {
			List<RaceTemplate> races = new ArrayList<RaceTemplate>();
			races.add(mockRaceTemplate);
			when(jdbcTemplate.query(anyString(),(RowMapper<RaceTemplate>) Mockito.anyObject())).thenReturn(races);
			List<RaceTemplate> actual = raceTemplateDAO.findAll();
			
			List<RaceTemplate> expected = new ArrayList<RaceTemplate>();
			expected.add(buildRaceTemplate(new Long(2),1,1,1,1,"info","trail"));
			assertEquals(expected, actual);
			verify(jdbcTemplate, atLeastOnce()).query(anyString(),(RowMapper<Race>) anyObject());
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void findAll_Exception() {
			DataAccessException expected = new RecoverableDataAccessException("Test Exception");
			when(jdbcTemplate.query(anyString(),(RowMapper<RaceTemplate>) Mockito.anyObject())).thenThrow(expected);
			
			try {
				raceTemplateDAO.findAll();
			} catch (DataAccessException actual) {
				assertEquals(expected.getMessage(), actual.getMessage());
			}
			
			verify(jdbcTemplate, atLeastOnce()).query(anyString(),(RowMapper<RaceTemplate>) Mockito.anyObject());
		}

		private RaceTemplate buildRaceTemplate(Long id,Integer race_leg, Integer race_segment, Integer run,Integer van,String status, String series){
			RaceTemplate rl =  new RaceTemplate(id,
					 race_leg,
					 race_segment,
					 run,
					 status,
					 series);
			rl.setVan(van);
			return rl;
		}
		
		@Configuration
	    @Import(RaceTemplateDAO.class) 
	    static class Config {
	    }
}

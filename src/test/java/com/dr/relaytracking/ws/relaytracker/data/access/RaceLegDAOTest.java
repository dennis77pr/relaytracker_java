package com.dr.relaytracking.ws.relaytracker.data.access;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.dr.relaytracking.ws.relaytracker.entity.RaceLeg;

@RunWith(SpringRunner.class)
public class RaceLegDAOTest {
	@MockBean 
	private JdbcTemplate jdbcTemplate; 
	@Autowired
	private RaceLegDAO raceLegDAO;
	@MockBean
	private ResultSetExtractor<RaceLeg> rse;
	@MockBean
	private KeyHolder keyholder;
	
	private RaceLeg mockRaceLeg;
	
	@Before
	public void setup(){
		mockRaceLeg = buildRaceLeg(new Long(2),1,1,1,1,
				"default",
				1.1,
				new Long(55),
				3.1,
				"Moderate",
				100,
				100, "A");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findByRaceID() {
		List<RaceLeg> mockLegs = new ArrayList<RaceLeg>();
		mockLegs.add(mockRaceLeg);
		when(jdbcTemplate.query(anyString(), (Object[]) Mockito.anyObject(),(RowMapper<RaceLeg>) Mockito.anyObject())).thenReturn(mockLegs);
		List<RaceLeg> actual = raceLegDAO.findByRaceId(mockRaceLeg.getRaceid());
		
		List<RaceLeg> expected = new ArrayList<RaceLeg>();
		expected.add(buildRaceLeg(new Long(2),1,1,1,1,
				"default",
				1.1,
				new Long(55),
				3.1,
				"Moderate",
				100,
				100, "A"));
		assertEquals(expected, actual);
		verify(jdbcTemplate, atLeastOnce()).query(anyString(), (Object[]) anyObject(),(RowMapper<RaceLeg>) anyObject());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findByID_Exception() {
		DataAccessException expected = new RecoverableDataAccessException("Test Exception");
		when(jdbcTemplate.query(anyString(), (Object[]) Mockito.anyObject(),(RowMapper<RaceLeg>) Mockito.anyObject())).thenThrow(expected);
		
		try {
			raceLegDAO.findByRaceId(mockRaceLeg.getRaceid());
		} catch (DataAccessException actual) {
			assertEquals(expected.getMessage(), actual.getMessage());
		}
		
		verify(jdbcTemplate, atLeastOnce()).query(anyString(), (Object[]) anyObject(),(RowMapper<RaceLeg>) anyObject());
	}
	
	@Test
	public void buildRaceLeg() {
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject(),(KeyHolder)Mockito.anyObject())).thenReturn(1);
		RaceLeg actual = raceLegDAO.buildLegFromTemplate(mockRaceLeg);
		
		RaceLeg expected = buildRaceLeg(new Long(2),1,1,1,1,
				"default",
				1.1,
				new Long(55),
				3.1,
				"Moderate",
				100,
				100, "A");
		assertEquals(expected, actual);
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject(),(KeyHolder)Mockito.anyObject());
		
	}
	
	@Test
	public void buildRaceLeg_Exception() {
		DataAccessException expected = new RecoverableDataAccessException("Test Exception");
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject(),(KeyHolder)Mockito.anyObject())).thenThrow(expected);
		
		try {
			raceLegDAO.buildLegFromTemplate(mockRaceLeg);
		} catch (DataAccessException actual) {
			assertEquals(expected.getMessage(), actual.getMessage());
		}
		
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject(),(KeyHolder)Mockito.anyObject());
	}
	
	@Test
	public void delete() {
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject())).thenReturn(1);
		int actual = raceLegDAO.deleteByRaceid(mockRaceLeg.getRaceid());
		
		assertEquals(1, actual);
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject());
	}
	
	@Test
	public void deleteException() {
		DataAccessException expected = new RecoverableDataAccessException("Test Exception");
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject())).thenThrow(expected);
		
		try{
			raceLegDAO.deleteByRaceid(mockRaceLeg.getRaceid());
		} catch (DataAccessException actual) {
			assertEquals(expected.getMessage(), actual.getMessage());
		}
		
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject());
	}
	
	@Test
	public void update() {
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject())).thenReturn(1);
		int actual = raceLegDAO.update(mockRaceLeg);
		
		assertEquals(1, actual);
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject());
	}
	
	@Test
	public void updateException() {
		DataAccessException expected = new RecoverableDataAccessException("Test Exception");
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject())).thenThrow(expected);
		
		try{
			raceLegDAO.update(mockRaceLeg);
		} catch (DataAccessException actual) {
			assertEquals(expected.getMessage(), actual.getMessage());
		}
		
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject());
	}
	
	private RaceLeg buildRaceLeg(Long racelegid, int raceleg, int racesegment, int run, int van, String status, Double legfactor, Long raceid, Double distance, String difficulty, int eGain, int eLoss, String recordStatus){
		RaceLeg rl =  new RaceLeg(racelegid,
				 raceleg,
				 racesegment,
				 run,
				 van,
				 status,
				 legfactor,
				 raceid);
		rl.setDistance(distance);
		rl.setDifficulty(difficulty);
		rl.setElevationGain(eGain);
		rl.setElevationLoss(eLoss);
		rl.setRecordStatus(recordStatus);
		return rl;
	}
	
	@Configuration
    @Import(RaceLegDAO.class) 
    static class Config {
    }

}
	
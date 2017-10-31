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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.dr.relaytracking.ws.relaytracker.entity.Race;

@RunWith(SpringRunner.class)
public class RaceDAOTest {
	@MockBean 
	private JdbcTemplate jdbcTemplate; 
	@Autowired
	private RaceDAO raceDAO;
	@MockBean
	private ResultSetExtractor<Race> rse;
	
	private Race mockRace;
	
	@Before
	public void setup(){
		mockRace = buildRace(new Long(2),
				"mockRace",
				"long race",
				LocalDate.now(),
				"test.com",
				"123",
				"me", "A");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findByID() {
		when(jdbcTemplate.query(anyString(), (Object[]) Mockito.anyObject(),(ResultSetExtractor<Race>) Mockito.anyObject())).thenReturn(mockRace);
		Race actual = raceDAO.findByID(mockRace.getRaceid());
		
		Race expected = buildRace(new Long(2),
				"mockRace",
				"long race",
				LocalDate.now(),
				"test.com",
				"123",
				"me", "A");
		assertEquals(expected, actual);
		verify(jdbcTemplate, atLeastOnce()).query(anyString(), (Object[]) anyObject(),(ResultSetExtractor<Race>) anyObject());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findByID_Exception() {
		DataAccessException expected = new RecoverableDataAccessException("Test Exception");
		when(jdbcTemplate.query(anyString(), (Object[]) Mockito.anyObject(),(ResultSetExtractor<Race>) Mockito.anyObject())).thenThrow(expected);
		
		try {
			raceDAO.findByID(mockRace.getRaceid());
		} catch (DataAccessException actual) {
			assertEquals(expected.getMessage(), actual.getMessage());
		}
		
		verify(jdbcTemplate, atLeastOnce()).query(anyString(), (Object[]) anyObject(),(ResultSetExtractor<Race>) anyObject());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findAll() {
		List<Race> races = new ArrayList<Race>();
		races.add(mockRace);
		when(jdbcTemplate.query(anyString(),(RowMapper<Race>) Mockito.anyObject())).thenReturn(races);
		List<Race> actual = raceDAO.findAll();
		
		List<Race> expected = new ArrayList<Race>();
		expected.add(buildRace(new Long(2),
				"mockRace",
				"long race",
				LocalDate.now(),
				"test.com",
				"123",
				"me", "A"));
		assertEquals(expected, actual);
		verify(jdbcTemplate, atLeastOnce()).query(anyString(),(RowMapper<Race>) anyObject());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findAll_Exception() {
		DataAccessException expected = new RecoverableDataAccessException("Test Exception");
		
		List<Race> races = new ArrayList<Race>();
		races.add(mockRace);
		when(jdbcTemplate.query(anyString(),(RowMapper<Race>) Mockito.anyObject())).thenThrow(expected);
		
		try {
			raceDAO.findAll();
		} catch (DataAccessException actual) {
			assertEquals(expected.getMessage(), actual.getMessage());
		}
		
		verify(jdbcTemplate, atLeastOnce()).query(anyString(),(RowMapper<Race>) anyObject());
	}
	
	@Test
	public void delete() {
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject())).thenReturn(1);
		int actual = raceDAO.delete(mockRace.getRaceid());
		
		assertEquals(1, actual);
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject());
	}
	
	@Test
	public void deleteException() {
		DataAccessException expected = new RecoverableDataAccessException("Test Exception");
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject())).thenThrow(expected);
		
		try{
			raceDAO.delete(mockRace.getRaceid());
		} catch (DataAccessException actual) {
			assertEquals(expected.getMessage(), actual.getMessage());
		}
		
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject());
	}
	
	@Test
	public void update() {
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject())).thenReturn(1);
		int actual = raceDAO.update(mockRace);
		
		assertEquals(1, actual);
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject());
	}
	
	@Test
	public void updateException() {
		DataAccessException expected = new RecoverableDataAccessException("Test Exception");
		when(jdbcTemplate.update((PreparedStatementCreator)Mockito.anyObject())).thenThrow(expected);
		
		try{
			raceDAO.update(mockRace);
		} catch (DataAccessException actual) {
			assertEquals(expected.getMessage(), actual.getMessage());
		}
		
		verify(jdbcTemplate, atLeastOnce()).update((PreparedStatementCreator)Mockito.anyObject());
	}
	
	private Race buildRace(Long raceid,String racename,String racetype,LocalDate racedate,String racewebsite, String createdbyid, String createdbyname, String recordstatus){
		return new Race(raceid,
				 racename,
				 racetype,
				 racedate,
				 racewebsite,
				 createdbyid,
				 createdbyname,
				 recordstatus);
	}
	
	@Configuration
    @Import(RaceDAO.class) 
    static class Config {
    }

}
	
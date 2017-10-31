package com.dr.relaytracking.ws.relaytracker.data.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.dr.relaytracking.ws.relaytracker.controller.RaceHelper;
import com.dr.relaytracking.ws.relaytracker.data.access.RaceDAO;
import com.dr.relaytracking.ws.relaytracker.data.access.RaceLegDAO;
import com.dr.relaytracking.ws.relaytracker.data.access.RaceTemplateDAO;
import com.dr.relaytracking.ws.relaytracker.entity.Race;

@RunWith(SpringRunner.class)
public class RaceServiceTest {
	@MockBean 
	 private RaceDAO raceDAO;
	@MockBean 
	 private RaceLegDAO racelegDAO;
	@MockBean 
	 private RaceTemplateDAO raceTemplateDAO;
	@MockBean 
	 private RaceHelper raceHelper;
	
	private Race mockRace;
	
	@Autowired
	private RaceService raceService;
	
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

	@Test
	public void findByID() {
		Mockito.when(raceDAO.findByID(Mockito.anyLong())).thenReturn(mockRace);
		Race actual = raceService.findByID(mockRace.getRaceid());
		
		Race expected = buildRace(new Long(2),
				"mockRace",
				"long race",
				LocalDate.now(),
				"test.com",
				"123",
				"me", "A");
		assertEquals(expected, actual);
		verify(raceDAO, atLeastOnce()).findByID(mockRace.getRaceid());
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
    @Import(RaceService.class) 
    static class Config {
    }
}

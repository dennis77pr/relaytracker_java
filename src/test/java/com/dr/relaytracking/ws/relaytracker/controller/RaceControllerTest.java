package com.dr.relaytracking.ws.relaytracker.controller;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dr.relaytracking.ws.relaytracker.data.service.RaceService;
import com.dr.relaytracking.ws.relaytracker.entity.Race;
@RunWith(SpringRunner.class)
@WebMvcTest(value = RaceController.class, secure = false)
public class RaceControllerTest {

	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean 
	private RaceService raceService;
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
	
	@Test
	public void findARace()throws Exception {
		Mockito.when(raceService.findByID(Mockito.anyLong())).thenReturn(mockRace);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/race/2").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = buildRaceJSON(mockRace);

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
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
	
	private String buildRaceJSON(Race race){
		LocalDate rDate = race.getRaceDate();
		String raceDateJSON =String.format("{year: %d,month: %s,monthValue: %d,chronology: {id: %s,calendarType: %s},dayOfMonth: %d,dayOfWeek: %s,era: %s,dayOfYear: %d,leapYear: %b}"
				,rDate.getYear(),rDate.getMonth(),rDate.getMonthValue(),rDate.getChronology().getId(),rDate.getChronology().getCalendarType(),rDate.getDayOfMonth(),rDate.getDayOfWeek(),rDate.getEra(),rDate.getDayOfYear(),rDate.isLeapYear());
		return String.format("{raceid: %d,raceName: '%s',raceType: '%s',raceDate: %s ,raceWebsite: '%s',createdById: '%s',createdByName: '%s',recordStatus: '%s'}",
				race.getRaceid(),race.getRaceName(),race.getRaceType(),raceDateJSON,race.getRaceWebsite(),race.getCreatedById(),race.getCreatedByName(),race.getRecordStatus());
	}

}

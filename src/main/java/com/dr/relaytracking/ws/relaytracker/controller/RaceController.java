package com.dr.relaytracking.ws.relaytracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dr.relaytracking.ws.relaytracker.data.service.RaceService;
import com.dr.relaytracking.ws.relaytracker.entity.Race;
import com.dr.relaytracking.ws.relaytracker.entity.RaceLeg;


@RestController
public class RaceController {
	 @Autowired
	 private RaceService raceService;

	 @RequestMapping(value = "race/create", method = RequestMethod.POST)
	 public @ResponseBody Race create(@RequestBody Race race) {
	     if (race.validForInsert()){
	    	 return raceService.buildFromTemplate(race);
	     }else{
	    	 throw new RuntimeException("The race could not be created.");
	     }
	     
	 }
	 
	 @RequestMapping(value = "race/{id}", method = RequestMethod.GET)
	 public @ResponseBody Race get(@PathVariable Long id) {
		 return raceService.findByID(id);
	 }
	 
	 @RequestMapping(value = "race/update", method = RequestMethod.POST)
	 public @ResponseBody Race updateRace(@RequestBody Race race) { 
		 if (race.validForInsert()){
			 race.setRecordStatus("A");
			 return raceService.update(race);
		 }
		 throw new RuntimeException("The race could not be updated.");
	 }
	 
	 @RequestMapping(value = "race/leg/update", method = RequestMethod.POST)
	 public @ResponseBody RaceLeg updateRace(@RequestBody RaceLeg raceleg) { 
		 if (raceleg.validForInsert()){
			 raceleg.setRecordStatus("A");
			 return raceService.update(raceleg);
		 }
		 throw new RuntimeException("The raceleg could not be updated.");
	 }
	 
	 @RequestMapping(value = "race/{id}", method = RequestMethod.DELETE)
	 public @ResponseBody Integer delete(@PathVariable Long id) { 
		 return raceService.deleteByRaceId(id);
	 }
}

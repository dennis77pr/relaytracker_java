package com.dr.relaytracking.ws.relaytracker.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dr.relaytracking.ws.relaytracker.controller.RaceHelper;
import com.dr.relaytracking.ws.relaytracker.data.access.RaceDAO;
import com.dr.relaytracking.ws.relaytracker.data.access.RaceLegDAO;
import com.dr.relaytracking.ws.relaytracker.data.access.RaceTemplateDAO;
import com.dr.relaytracking.ws.relaytracker.entity.Race;
import com.dr.relaytracking.ws.relaytracker.entity.RaceLeg;
import com.dr.relaytracking.ws.relaytracker.entity.RaceTemplate;

@Component
public class RaceService {

	@Autowired
	 private RaceDAO raceDAO;
	@Autowired
	 private RaceLegDAO racelegDAO;
	@Autowired
	 private RaceTemplateDAO raceTemplateDAO;
	 @Autowired
	 private RaceHelper raceHelper;
	 
	public Race findByID(Long id){
		Race race = raceDAO.findByID(id);
		race.setRaceLegs(racelegDAO.findByRaceId(id));
		return race;
	}
	
	@Transactional
	public int deleteByRaceId(Long raceId){
		int row = raceDAO.delete(raceId);
		racelegDAO.deleteByRaceid(raceId);
		return row;
	}
	
	@Transactional
	public Race update(Race race){
		raceDAO.update(race);
		return race;
	}
	
	@Transactional
	public RaceLeg update(RaceLeg raceLeg){
		racelegDAO.update(raceLeg);
		return raceLeg;
	}
	
	@Transactional
	public Race buildFromTemplate(Race race){
		race = raceDAO.insert(race);
    	//get legs from template
	     List<RaceTemplate> legsTemplate = raceTemplateDAO.findBySeries(race.getRaceType());
	     List<RaceLeg> rl = raceHelper.buildFromRaceTemplate(race.getRaceid(),legsTemplate);
	     race.setRaceLegs(racelegDAO.buildLegsFromTemplate(rl));
		return race;
	}
}

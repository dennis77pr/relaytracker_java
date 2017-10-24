package com.dr.relaytracking.ws.relaytracker.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dr.relaytracking.ws.relaytracker.data.access.RaceTemplateDAO;
import com.dr.relaytracking.ws.relaytracker.entity.RaceTemplate;

@Component
public class RaceTemplateService {
	
	@Autowired
	 private RaceTemplateDAO raceTemplateDAO;
	
	public List<RaceTemplate> findTemplate(String series){
		if (series.equals("all")){
	    	 return raceTemplateDAO.findAll();
	     }else{
	    	 return raceTemplateDAO.findBySeries(series);
	     }
	}

}

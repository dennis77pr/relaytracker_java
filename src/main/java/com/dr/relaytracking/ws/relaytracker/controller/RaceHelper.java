package com.dr.relaytracking.ws.relaytracker.controller;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dr.relaytracking.ws.relaytracker.entity.RaceLeg;
import com.dr.relaytracking.ws.relaytracker.entity.RaceTemplate;
import com.dr.relaytracking.ws.relaytracker.exception.RaceTemplateException;
@Component
public class RaceHelper {
	public List<RaceLeg> buildFromRaceTemplate(Long raceId, List<RaceTemplate> legsTemplate){
		 Function<RaceTemplate, RaceLeg> mapTemplateToLeg = (RaceTemplate template) -> {
		    	RaceLeg rl = new RaceLeg(template.getRace_leg(),template.getRace_segment(),template.getRun(),template.getVan(),template.getStatus(),template.getLegfactor());
		    	rl.setRaceid(raceId);
		    	rl.setRecordStatus("A");
		    	return rl;
		 };
		 Comparator<RaceLeg> byRaceLeg =(RaceLeg o1, RaceLeg o2)->o1.getRace_leg().compareTo(o2.getRace_leg());
		    
		 List<RaceLeg> raceLegs = legsTemplate
				 					.stream()
				 					.map(mapTemplateToLeg)
				 					.collect(Collectors.toList());
		 //Make sure race legs are sorted
		 raceLegs.sort(byRaceLeg);

		 
		 return raceLegs;
	 }
	
	public List<RaceLeg> buildFromExistingLegs(Long raceId, List<RaceLeg> legs){
		 Function<RaceLeg, RaceLeg> mapTemplateToLeg = (RaceLeg leg) -> {
		    	RaceLeg rl = new RaceLeg(leg.getRace_leg(),leg.getRace_segment(),leg.getRun(),leg.getVan(),leg.getStatus(),leg.getLegfactor(),raceId);
		    	rl.setRecordStatus("A");
		    	return addUserEnteredLegInfo(rl,leg.getDifficulty(),leg.getDistance(),leg.getElevationGain(),leg.getElevationLoss(),leg.getRelativeDistance());
		 };
		 Comparator<RaceLeg> byRaceLeg =(RaceLeg o1, RaceLeg o2)->o1.getRace_leg().compareTo(o2.getRace_leg());
		    
		 List<RaceLeg> raceLegs = legs
				 					.stream()
				 					.map(mapTemplateToLeg)
				 					.collect(Collectors.toList());
		 //Make sure race legs are sorted
		 raceLegs.sort(byRaceLeg);

		 
		 return raceLegs;
	 }
	
	public RaceLeg addUserEnteredLegInfo(RaceLeg rl, String difficulty, Double distance, Integer elevationGain, Integer elevationLoss, Double relativeDistance){
    	rl.setDifficulty(difficulty);
    	rl.setDistance(distance);
    	rl.setElevationGain(elevationGain);
    	rl.setElevationLoss(elevationLoss);
    	rl.setRelativeDistance(relativeDistance);
    	return rl;
		
	}
	
	public Double calculateRelativeDistance(Double distance,Integer elevationGain, Integer elevationLoss){
		if (elevationGain == null){ elevationGain = 0;}
		if (elevationLoss == null){ elevationLoss = 0;}
		
		if (distance != null && distance > 0){
			Double relativeDistance = distance;
			if (elevationGain > 0 && elevationLoss > 0){
				//TODO figure out the constructor needed to truncate at 2 decimal points.
				relativeDistance = distance + (elevationGain/1000)+(elevationLoss/2000);
			}
			return relativeDistance;
		}
		return 0.00;
    }
	
	public int verifyTemplate(List<RaceTemplate> template,String series)throws RaceTemplateException{
		if (template !=null && template.size() > 0){
	    	 // verify template
			int size = template.size();
			List<RaceTemplate> tempTemplate = template.stream()
					.filter(r->r.getSeries().equals(series))
					.collect(Collectors.toList());
			if(size == tempTemplate.size()){
				return size;
			}else{
				throw new RaceTemplateException("The race template contained an error. Please contact the adminstrator.");
			}
	     }else{
	    	 throw new RaceTemplateException("The race type had no template defined.  Race could not be created.");
	     }
	}
	 
}

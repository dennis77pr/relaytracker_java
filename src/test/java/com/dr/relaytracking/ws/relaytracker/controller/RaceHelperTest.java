package com.dr.relaytracking.ws.relaytracker.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dr.relaytracking.ws.relaytracker.entity.RaceLeg;
import com.dr.relaytracking.ws.relaytracker.entity.RaceTemplate;
import com.dr.relaytracking.ws.relaytracker.exception.RaceTemplateException;

public class RaceHelperTest {

	private RaceHelper rh = new RaceHelper();
	@Test
	public void testBuildRaceLegsFromRaceTemplate() {
		Long raceId = new Long(1);
		List<RaceTemplate> template = buildTemplate();
		List<RaceLeg>raceLegs = rh.buildFromRaceTemplate(raceId,template);
		assertNotNull(raceLegs);
		assertEquals(template.size(),raceLegs.size());
	}
	
	@Test
	public void testBuildRaceLegsFromExistingLegs() {
		Long raceId = new Long(1);
		List<RaceLeg> existingLegs = buildLegs();
		List<RaceLeg>raceLegs = rh.buildFromExistingLegs(raceId,existingLegs);
		assertNotNull(raceLegs);
		assertEquals(existingLegs.size(),raceLegs.size());
	}
	
	@Test
	public void testAddUserEnteredLegInfo() {
		RaceLeg tr = buildLeg(1,1,1,null,"danger",1.0,new Long(1));
		tr = addUserEnteredLeg(tr,"Moderate",4.2,100,123,4.3);
		RaceLeg r = buildLeg(1,1,1,null,"danger",1.0,new Long(1));
		RaceLeg actual = rh.addUserEnteredLegInfo(r,"Moderate",4.2,100,123,4.3);
		assertNotNull(actual);
		assertEquals(tr.getDifficulty(),actual.getDifficulty());
		assertEquals(tr.getDistance(),actual.getDistance());
		assertEquals(tr.getElevationGain(),actual.getElevationGain());
		assertEquals(tr.getElevationLoss(),actual.getElevationLoss());
		assertEquals(tr.getRelativeDistance(),actual.getRelativeDistance());
	}
	
	@Test
	public void testCalculateRelativeDistance() {
		Double rd = rh.calculateRelativeDistance(4.2, 1000, 200);
		assertEquals(new Double(5.2),rd);
	}
	
	@Test
	public void testCalculateRelativeDistanceNullElevGain() {
		Double rd = rh.calculateRelativeDistance(4.2, null, 200);
		assertEquals(new Double(4.2),rd);
	}
	
	@Test
	public void testCalculateRelativeDistanceNullElevLoss() {
		Double rd = rh.calculateRelativeDistance(4.2, 200, null);
		assertEquals(new Double(4.2),rd);
	}
	
	@Test
	public void testCalculateRelativeDistanceNoElevGain() {
		Double rd = rh.calculateRelativeDistance(4.2, 0, 100);
		assertEquals(new Double(4.2),rd);
	}
	
	@Test
	public void testCalculateRelativeDistanceNoElevLoss() {
		Double rd = rh.calculateRelativeDistance(4.2, 1000, 0);
		assertEquals(new Double(4.2),rd);
	}
	
	@Test
	public void testCalculateRelativeDistanceNullDistance() {
		Double rd = rh.calculateRelativeDistance(null, 1000, 100);
		assertEquals(new Double(0.0),rd);
	}
	
	@Test
	public void testCalculateRelativeDistanceNoDistance() {
		Double rd = rh.calculateRelativeDistance(0.00, 1000, 100);
		assertEquals(new Double(0.0),rd);
	}
	
	@Test
	public void testVerifyTemplateNull() {
		try {
			rh.verifyTemplate(null, "series");
		} catch (RaceTemplateException e) {
			String expected = "The race type had no template defined.  Race could not be created.";
			assertEquals(expected,e.getMessage());
		}
	}
	
	@Test
	public void testVerifyTemplateEmpty() {
		try {
			rh.verifyTemplate(new ArrayList<RaceTemplate>(), "series");
		} catch (RaceTemplateException e) {
			String expected = "The race type had no template defined.  Race could not be created.";
			assertEquals(expected,e.getMessage());
		}
	}
	
	@Test
	public void testVerifyTemplateBadData() {
		try {
			rh.verifyTemplate(buildTemplate(), "series");
		} catch (RaceTemplateException e) {
			String expected = "The race template contained an error. Please contact the adminstrator.";
			assertEquals(expected,e.getMessage());
		}
	}
	
	@Test
	public void testVerifyTemplate() throws RaceTemplateException {
		List<RaceTemplate> rt = buildTemplate();
		int actual = rh.verifyTemplate(rt, "trail");
		assertEquals(rt.size(),actual);
	}
	
	
	private List<RaceTemplate> buildTemplate(){
		List<RaceTemplate>rt = new ArrayList<RaceTemplate>();
		rt.add(buildLegTemplate(1,1,1,"danger","trail",null,1.0));
		return rt;
	}
	
	private RaceTemplate buildLegTemplate(int race_leg, int race_segment, int run, String status, String series, Integer van, Double legfactor){
		RaceTemplate rt = new RaceTemplate(race_leg, race_segment,run,status,series);
		rt.setVan(van);
		rt.setLegfactor(legfactor);
		return rt;
	}
	
	private List<RaceLeg> buildLegs(){
		List<RaceLeg>rl = new ArrayList<RaceLeg>();
		RaceLeg r = buildLeg(1,1,1,null,"danger",1.0,new Long(1));
		r = addUserEnteredLeg(r,"Moderate",4.2,100,123,4.3);
		rl.add(r);
		return rl;
	}
	
	private RaceLeg buildLeg(Integer race_leg, Integer race_segment, Integer run,Integer van, String status,Double legfactor,Long raceId){
		RaceLeg rt = new RaceLeg(race_leg, race_segment,run,van,status,legfactor,raceId);
		return rt;
	}
	
	private RaceLeg addUserEnteredLeg(RaceLeg rl, String difficulty, Double distance, Integer elevationGain, Integer elevationLoss, Double relativeDistance){
    	rl.setDifficulty(difficulty);
    	rl.setDistance(distance);
    	rl.setElevationGain(elevationGain);
    	rl.setElevationLoss(elevationLoss);
    	rl.setRelativeDistance(relativeDistance);
    	return rl;
		
	}

}

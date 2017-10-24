package com.dr.relaytracking.ws.relaytracker.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Race {
	 //Generated
    private Long raceid;
    private String raceName;
 // trail or relay
    private String raceType;
    private LocalDate raceDate;
    private String raceWebsite;
    private String createdById;
    private String createdByName;
    private List<RaceLeg> raceLegs;
    private String recordStatus;

    protected Race() {}

    public Race(String raceName, String raceType, LocalDate raceDate, String raceWebsite,String createdById, String createdByName,String recordStatus) {
    	this.raceName = raceName;
        this.raceType = raceType;
        this.raceDate = raceDate;
        this.raceWebsite = raceWebsite;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.recordStatus = recordStatus;
    }
    public Race(Long raceid, String raceName, String raceType,LocalDate raceDate, String raceWebsite, String createdById, String createdByName,String recordStatus) {
    	this.raceid = raceid;
    	this.raceName = raceName;
        this.raceType = raceType;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.recordStatus = recordStatus;
    }

	public Long getRaceid() {
		return raceid;
	}
	
	public void setRaceid(Long id) {
		raceid = id;
	}
	
	public void setRaceDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
		raceDate = LocalDate.parse(date, formatter);
	}

	public String getRaceName() {
		return raceName;
	}

	public LocalDate getRaceDate() {
		return raceDate;
	}

	public String getRaceWebsite() {
		return raceWebsite;
	}

	public String getRaceType() {
		return raceType;
	}

	public String getCreatedById() {
		return createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}
	
	public List<RaceLeg> getRaceLegs() {
		return raceLegs;
	}

	public void setRaceLegs(List<RaceLeg> raceLegs) {
		this.raceLegs = raceLegs;
	}
	
	
	
	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public boolean validForInsert(){
		 if (this.raceName == null || this.raceName.isEmpty()){return false;}
		 if (this.raceWebsite == null || this.raceWebsite.isEmpty()){return false;}
		 if (this.raceDate == null || this.raceDate.isAfter(LocalDate.now())){return false;}
		 if (this.raceType == null || this.raceType.isEmpty()){return false;}
		return true;
	 }

	@Override
	public String toString() {
		return "Race [id=" + raceid + ", raceName=" + raceName + ", raceType=" + raceType + ", raceDate=" + raceDate
				+ ", raceWebsite=" + raceWebsite + ", createdById=" + createdById + ", createdByName=" + createdByName
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raceid == null) ? 0 : raceid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Race other = (Race) obj;
		if (raceid == null) {
			if (other.raceid != null)
				return false;
		} else if (!raceid.equals(other.raceid))
			return false;
		return true;
	}
	

}

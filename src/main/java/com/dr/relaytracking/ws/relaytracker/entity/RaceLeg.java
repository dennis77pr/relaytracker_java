package com.dr.relaytracking.ws.relaytracker.entity;

public class RaceLeg {
    
    private Long racelegid;
    private Integer race_leg;
    private Integer race_segment;
    private Integer run;
    private Integer van;
 // default, info or danger
    private String status;
    private Double legFactor;
    private Long raceid;
    private Double distance;
    private String difficulty;
    private Integer elevationGain;
    private Integer elevationLoss;
    private Double relativeDistance;
    private String recordStatus;

    protected RaceLeg() {}

    public RaceLeg(Integer race_leg, Integer race_segment, Integer run,Integer van, String status,Double legfactor,Long raceId) {
    	this.race_leg = race_leg;
        this.race_segment = race_segment;
        this.run = run;
        this.van = van;
        this.status = status;
        this.legFactor = legfactor;
        this.raceid = raceId;
    }
    
    public RaceLeg(Long id,Integer race_leg, Integer race_segment, Integer run,Integer van, String status,Double legfactor,Long raceId) {
    	this.racelegid = id;
    	this.race_leg = race_leg;
        this.race_segment = race_segment;
        this.run = run;
        this.van = van;
        this.status = status;
        this.legFactor = legfactor;
        this.raceid = raceId;
    }
    
    // For raceTemplates.
    public RaceLeg(Integer race_leg, Integer race_segment, Integer run, Integer van, String status, Double legfactor) {
    	this.race_leg = race_leg;
        this.race_segment = race_segment;
        this.run = run;
        this.van = van;
        this.status = status;
        this.legFactor = legfactor;
    }
    
    public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getElevationGain() {
		return elevationGain;
	}

	public void setElevationGain(Integer elevationGain) {
		this.elevationGain = elevationGain;
	}

	public Integer getElevationLoss() {
		return elevationLoss;
	}

	public void setElevationLoss(Integer elevationLoss) {
		this.elevationLoss = elevationLoss;
	}

	public Double getRelativeDistance() {
		return relativeDistance;
	}

	public void setRelativeDistance(Double relativeDistance) {
		this.relativeDistance = relativeDistance;
	}

	public Long getRaceid() {
		return raceid;
	}

	public void setRaceid(Long raceId) {
		this.raceid = raceId;
	}

	public Integer getVan() {
		return van;
	}

	public void setVan(Integer van) {
		this.van = van;
	}

	public Double getLegfactor() {
		return legFactor;
	}

	public void setLegfactor(Double legfactor) {
		this.legFactor = legfactor;
	}

	public Long getRacelegid() {
		return racelegid;
	}

	public Integer getRace_leg() {
		return race_leg;
	}

	public Integer getRace_segment() {
		return race_segment;
	}

	public Integer getRun() {
		return run;
	}

	public String getStatus() {
		return status;
	}
	
	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	

	public void setRacelegid(Long racelegid) {
		this.racelegid = racelegid;
	}

	public boolean validForInsert(){
		 if (this.distance == null || this.distance.isNaN()){return false;}
		 if (this.relativeDistance == null || this.relativeDistance.isNaN()){return false;}
		 if (this.difficulty == null || this.difficulty.isEmpty()){return false;}
		 if (this.elevationGain == null){return false;}
		 if (this.elevationLoss == null){return false;}
		return true;
	 }
	
	@Override
	public String toString() {
		return "RaceLeg [id=" + racelegid + ", race_leg=" + race_leg + ", race_segment=" + race_segment + ", run=" + run
				+ ", van=" + van + ", status=" + status + ", legfactor=" + legFactor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((racelegid == null) ? 0 : racelegid.hashCode());
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
		RaceLeg other = (RaceLeg) obj;
		if (racelegid == null) {
			if (other.racelegid != null)
				return false;
		} else if (!racelegid.equals(other.racelegid))
			return false;
		return true;
	}
	
	

}

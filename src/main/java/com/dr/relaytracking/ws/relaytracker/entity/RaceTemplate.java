package com.dr.relaytracking.ws.relaytracker.entity;

public class RaceTemplate {
	private Long id;
    private Integer race_leg;
    private Integer race_segment;
    private Integer run;
    private Integer van;
 // default, info or danger
    private String status;
    private String series;
    private Double legfactor;

    protected RaceTemplate() {}

    public RaceTemplate(Integer race_leg, Integer race_segment, Integer run,String status, String series) {
    	this.race_leg = race_leg;
        this.race_segment = race_segment;
        this.run = run;
        this.status = status;
        this.series = series;
    }
    
    public RaceTemplate(Long id,Integer race_leg, Integer race_segment, Integer run,String status, String series) {
    	this.id = id;
    	this.race_leg = race_leg;
        this.race_segment = race_segment;
        this.run = run;
        this.status = status;
        this.series = series;
    }

	public Integer getVan() {
		return van;
	}

	public void setVan(Integer van) {
		this.van = van;
	}

	public Double getLegfactor() {
		return legfactor;
	}

	public void setLegfactor(Double legfactor) {
		this.legfactor = legfactor;
	}

	public Long getId() {
		return id;
	}

	public int getRace_leg() {
		return race_leg;
	}

	public int getRace_segment() {
		return race_segment;
	}

	public int getRun() {
		return run;
	}

	public String getStatus() {
		return status;
	}

	public String getSeries() {
		return series;
	}

	@Override
	public String toString() {
		return "RaceTemplate [id=" + id + ", race_leg=" + race_leg + ", race_segment=" + race_segment + ", run=" + run
				+ ", van=" + van + ", status=" + status + ", series=" + series + ", legfactor=" + legfactor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		RaceTemplate other = (RaceTemplate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}

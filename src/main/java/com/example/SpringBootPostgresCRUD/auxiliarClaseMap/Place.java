package com.example.SpringBootPostgresCRUD.auxiliarClaseMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {
    @JsonProperty("place_id")
    private int placeId;
    private String licence;
    @JsonProperty("osm_type")
    private String osmType;
    @JsonProperty("osm_id")
    private Long osmId;
    private String[] boundingbox;
    private String lat;
    private String lon;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("place_rank")
    private int placeRank;
    private String category;
    private String type;
    private double importance;
    private String icon;
    public int getPlaceId() {
        return placeId;
    }
    
    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
    
    public String getLicence() {
        return licence;
    }
    
    public void setLicence(String licence) {
        this.licence = licence;
    }
    
    public String getOsmType() {
        return osmType;
    }
    
    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }
    
    public Long getOsmId() {
        return osmId;
    }
    
    public void setOsmId(Long osmId) {
        this.osmId = osmId;
    }
    
    public String[] getBoundingbox() {
        return boundingbox;
    }
    
    public void setBoundingbox(String[] boundingbox) {
        this.boundingbox = boundingbox;
    }
    
    public String getLat() {
        return lat;
    }
    
    public void setLat(String lat) {
        this.lat = lat;
    }
    
    public String getLon() {
        return lon;
    }
    
    public void setLon(String lon) {
        this.lon = lon;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public int getPlaceRank() {
        return placeRank;
    }
    
    public void setPlaceRank(int placeRank) {
        this.placeRank = placeRank;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public double getImportance() {
        return importance;
    }
    
    public void setImportance(double importance) {
        this.importance = importance;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
}    
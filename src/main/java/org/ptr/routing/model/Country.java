package org.ptr.routing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * DTO object for keeping country data , read from countries.json
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country implements Serializable {

    @JsonProperty(value = "cca3")
    private String code;

    @JsonProperty(value = "region")
    private String region;

    @JsonProperty(value = "borders")
    private List<String> borders;



    public Country() {
    }

    public Country(String code, List<String> borders) {
        this.code = code;
        this.borders = borders;
    }

    public Country(String code, String region, List<String> borders) {
        this.code = code;
        this.region = region;
        this.borders = borders;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        return Objects.equals(code, country.code) &&
            Objects.equals(region, country.region) &&
            Objects.equals(borders, country.borders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, region, borders);
    }

    @Override
    public String toString() {
        return "Country{" +
            "code='" + code + '\'' +
            ", region='" + region + '\'' +
            ", borders=" + borders +
            '}';
    }
}

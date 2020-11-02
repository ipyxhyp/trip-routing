package org.ptr.routing.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;


/**
 * DTO for routing response
 *
 * */
public class Route implements Serializable {

    @JsonProperty("route")
    private List<String> route;

    public Route() {
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Route{" +
            "route=" + route +
            '}';
    }
}

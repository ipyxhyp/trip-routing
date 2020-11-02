package org.ptr.routing.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.ptr.routing.controller.response.Route;
import org.ptr.routing.exception.Failure;
import org.ptr.routing.service.RoutingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routing")
public class RoutingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutingController.class);

    private RoutingService routingService;

    @Autowired
    public void setRoutingService(RoutingService routingService) {
        this.routingService = routingService;
    }

    /**
     * Searches the path between origin and destination countries landing borders
     *
     * @param origin - country as source
     * @param destination - country as destination
     * @return list of country codes if the path between origin country and destination is identified
     * @throws Failure and sends 400 if input is incorrect (countryCode does not exist)
     * or path not found between countries (or countries has same code origin/destination)
     *
     * */
    @GetMapping(path = "/{origin}/{destination}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Route getRoute(@PathVariable String origin, @PathVariable String destination, HttpServletResponse response)
        throws IOException {
        Route route = new Route();
        List<String> routeList;
        try {
            routeList = routingService.searchRoutingPath(origin, destination);
            route.setRoute(routeList);
            LOGGER.info(" routeList : {} ", routeList);
        } catch (Failure failure){
            LOGGER.error(failure.getDetailedMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, failure.getDetailedMessage());
        }
        return route;
    }
}

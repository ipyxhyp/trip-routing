package org.ptr.routing.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ptr.routing.app.RoutingApplication;
import org.ptr.routing.exception.Failure;
import org.ptr.routing.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
    RoutingApplication.class
})
public class TestRoutingService {

    private final String CZE = "CZE";

    @Autowired
    private RoutingService routingService;

    @Test
    public void testSearchRoutingPathSuccess(){

        List<String> result;

        String ita = "ITA";
        String esp = "ESP";
        String rus = "RUS";
        String prt = "PRT";
        String chn = "CHN";

        result = routingService.searchRoutingPath(CZE, ita);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());

        result.clear();
        result = routingService.searchRoutingPath(CZE, esp);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());

        result.clear();
        result = routingService.searchRoutingPath(CZE, prt);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals( 5, result.size());

        result.clear();
        result = routingService.searchRoutingPath(prt, rus);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals( 6, result.size());

        result.clear();
        result = routingService.searchRoutingPath(prt, chn);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(7, result.size());
    }

    @Test(expected = Failure.class)
    public void testSearchRoutingPathFailed() {

        String usa = "USA";
        String jpn = "JPN";
        String bra = "BRA";
        routingService.searchRoutingPath(jpn, usa);

    }

    @Test(expected = Failure.class)
    public void testSearchRoutingPathIncorrectInput() {

        String country111 = "111";
        routingService.searchRoutingPath(CZE, country111);

    }

    @Test(expected = Failure.class)
    public void testSearchRoutingPathSameCountryInput() {

        routingService.searchRoutingPath(CZE, CZE);
    }
}

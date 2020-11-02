package org.ptr.routing.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.ptr.routing.exception.Failure;
import org.ptr.routing.exception.Failure.ReasonType;
import org.ptr.routing.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


/**
 * RoutingService provides search path between two given countries
 *
 * */
@Service
public class RoutingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutingService.class);

    private Map<String, Country> countryMap ;
    private List<Country> countryList;
    private final String FILE_PATH = "./countries.json";


    /**
     * Reads the countries.json resource file and
     * populates the countryList and countryMap internal fields
     * @throws IOException if file reading is failed
     *
     * */
    @PostConstruct
    protected void initCountries() throws IOException {

        final InputStream jsonFileInputStream = new ClassPathResource(FILE_PATH).getInputStream();
        final ObjectMapper mapper = new ObjectMapper();
        this.countryList = mapper.readValue(jsonFileInputStream, new TypeReference<List<Country>>() {});
        this.countryMap = countryList.stream().collect(Collectors.toMap(Country::getCode, country -> country));
        LOGGER.info("countryMap : {} ", countryMap);
    }

    public Map<String, Country> getCountryMap() {
        return countryMap;
    }

    public List<Country> getCountryList() {
        return countryList;
    }


    /**
     * Searches the path between origin and destination countries landing borders
     *
     * @param sourceCountryCode - origin country code
     * @param targetCountryCode - target country code
     * @return List of country codes if the path between origin country and destination is identified
     * @throws Failure if input is incorrect (countryCode does not exist) or path not found between countries
     *
     * */
    public List<String> searchRoutingPath(final String sourceCountryCode, String targetCountryCode) throws Failure{

        Country targetCountry = getCountryMap().get(targetCountryCode);
        Country sourceCountry = getCountryMap().get(sourceCountryCode);

        validateCountryCodes(sourceCountryCode, targetCountryCode, targetCountry, sourceCountry);

        Boolean isMatchedTarget = Boolean.FALSE;
        final List<String> path = new ArrayList<>();
        final Map<String, String> previousMap = new HashMap<>();
        final List<Country> vertexCountryList = new ArrayList<>(getCountryList());
        final Map<String, Integer> distanceMap =
            getCountryList().stream().collect(Collectors.toMap( Country::getCode,
                country ->  Integer.valueOf(Integer.MAX_VALUE)));
        distanceMap.put(sourceCountryCode, 0);

        while(!vertexCountryList.isEmpty() && !isMatchedTarget ){
            Country smallest = vertexCountryList.stream().reduce( targetCountry, (prevCountry, currCountry) ->
                (distanceMap.get(currCountry.getCode()) < distanceMap.get(prevCountry.getCode()) ? currCountry : prevCountry));
            if(smallest != null){
                int index = vertexCountryList.indexOf(smallest);
                if(!vertexCountryList.isEmpty() && index != -1){
                    vertexCountryList.remove(index);
                }
                if(smallest.getCode().equals(targetCountryCode)){
                    LOGGER.info(" target country and searched country are matched ");
                    isMatchedTarget = true;
                }
                if(smallest.getBorders() != null && !smallest.getBorders().isEmpty()){
                    for(String border : smallest.getBorders()){
                        int altSmallest = distanceMap.get(smallest.getCode());
                        altSmallest++;
                        int borderDistance = distanceMap.get(border);
                        if(altSmallest < borderDistance){
                            distanceMap.put(border, altSmallest);
                            previousMap.put(border, smallest.getCode());
                        }
                    }
                }
            }
        }
        while (targetCountryCode != null) {
            path.add(0, targetCountryCode);
            String prevTargetCountryCode = previousMap.get(targetCountryCode);
            targetCountryCode = prevTargetCountryCode;
        }
        if(path.isEmpty() || (path.size() == 1 && path.get(0).equals(targetCountry.getCode()))){
            LOGGER.error("No direct land borders between the countries : {} , {} ", sourceCountryCode, targetCountry.getCode());
            throw new Failure(String.format("No direct land borders between the countries : %s, %s ", sourceCountryCode, targetCountry.getCode()),
                HttpURLConnection.HTTP_BAD_REQUEST, ReasonType.CODE_400);
        }
        LOGGER.info(" result : {} , hops count : {} ", path, path.size() - 1 );
        return path;
    }


    /**
     * validates input country codes, if they are existing and not the same origin/target
     * @param sourceCountryCode
     * @param targetCountry
     * @param sourceCountry
     * @param targetCountryCode
     * @throws Failure in case some of validation has failed
     *
     * */
    private void validateCountryCodes(String sourceCountryCode, String targetCountryCode, Country targetCountry,
        Country sourceCountry) {
        if(targetCountry == null || sourceCountry == null){
            LOGGER.error("Either targetCountry or sourceCountry does not exist by the provided country codes : {}, {} ", sourceCountryCode, targetCountryCode);
            throw new Failure(String.format("Either targetCountry or sourceCountry does not exist by the provided country codes : %s, %s ", sourceCountryCode, targetCountryCode),
                HttpURLConnection.HTTP_BAD_REQUEST, ReasonType.CODE_400);
        }
        if(targetCountry != null && sourceCountry != null && targetCountry.equals(sourceCountry)){
            LOGGER.error("Target Country is the same as the Origin Country : {}, {} ", sourceCountryCode, targetCountryCode);
            throw new Failure(String.format("Target Country is the same as the Origin Country : %s, %s ", sourceCountryCode, targetCountryCode),
                HttpURLConnection.HTTP_BAD_REQUEST, ReasonType.CODE_400);
        }
    }

}

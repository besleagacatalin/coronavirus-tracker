package com.catalin.coronavirustracker.services;

import com.catalin.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> allStatsList= new ArrayList<>();

    public List<LocationStats> getAllStatsList() {
        return allStatsList;
    }

    @PostConstruct
    @Scheduled(cron = "* * * 1 * *") //second, minute, hour, day of month, month, day(s) of week
    private void getVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStatsList = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();

            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));

            int totalCases = Integer.parseInt(record.get(record.size() -1));
            int deltaCases = totalCases - Integer.parseInt(record.get(record.size() -2));

            locationStats.setLastDayCases(totalCases);
            locationStats.setPrevDayCases(deltaCases);

            newStatsList.add(locationStats);
        }
        this.allStatsList = newStatsList;
    }
}

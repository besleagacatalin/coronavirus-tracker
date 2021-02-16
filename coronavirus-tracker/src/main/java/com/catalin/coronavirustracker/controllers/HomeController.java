package com.catalin.coronavirustracker.controllers;

import com.catalin.coronavirustracker.models.LocationStats;
import com.catalin.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStatsList();

        LocationStats romania = allStats.stream()
                .filter(state -> "Romania".equals(state.getCountry()))
                .findAny()
                .orElse(null);

        model.addAttribute("stateList", allStats);
        model.addAttribute("totalCasesRomania", romania.getLastDayCases());
        model.addAttribute("newCasesRomania", romania.getPrevDayCases());
        return "home";
    }
}

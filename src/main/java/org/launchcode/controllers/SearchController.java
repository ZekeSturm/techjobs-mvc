package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute("prevSelect", "all");
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results

    @RequestMapping(value = "results", method = RequestMethod.GET)
    public String search(Model model, @RequestParam String searchTerm, @RequestParam String searchType) {

        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute("prevSelect", searchType);

        Boolean noSearch = new Boolean(searchTerm.length() <= 0);
        model.addAttribute("noSearch",noSearch);

        if (noSearch) {
            return "search";
        }

        ArrayList<HashMap<String,String>> jobs = new ArrayList<>();

        for (HashMap<String,String> job : JobData.findAll()) {

            if (searchType.equals("all")) {

                for (String column : job.keySet()) {

                    if (job.get(column).toLowerCase().contains(searchTerm.toLowerCase()) && !jobs.contains(job)) {
                        jobs.add(job);
                        break;
                    }

                }
            } else {

                if (job.get(searchType).toLowerCase().contains(searchTerm.toLowerCase()) && !jobs.contains(job)) {
                    jobs.add(job);
                }
            }
        }

        model.addAttribute("jobs",jobs);

        return "search";
    }

}

package uk.gov.ons.collection.cucumber;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {
    @RequestMapping(method={RequestMethod.GET},value={"/version"})
    public String getVersion() {
        return "1.0";
    }

    @RequestMapping(method={RequestMethod.GET},value={"/getJSON"})
    public String getJSON() {
        return "{'firstName':'Mona', 'lastName':'Lisa'}";
    }

    @RequestMapping(method={RequestMethod.GET},value={"/getJSONArray"})
    public String getJSONArray() {
        return "[{'firstName':'Mona', 'lastName':'Lisa'},{'empty_1':1, 'empty_1':2 }]";
    }
}
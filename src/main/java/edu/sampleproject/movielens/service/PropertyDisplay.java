package edu.sampleproject.movielens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class PropertyDisplay {
    @Autowired
    private Environment env;

    @Value("${top.n:1}")
    private int topN;

    @GetMapping(path = "/property")
    public String getPropertyValue(@RequestParam String prop) {
        return String.format("Property: %s  Value: %s  Value2: %s", prop, env.getProperty(prop), topN);
    }
}

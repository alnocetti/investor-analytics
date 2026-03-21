package com.test.investor_analytics.controller;

import com.test.investor_analytics.repository.MongoHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DiagnosticController {

    private final MongoHelper mongoHelper;

    public DiagnosticController(MongoHelper mongoHelper) {
        this.mongoHelper = mongoHelper;
    }

    @GetMapping("/diagnose")
    public Map<String, Object> diagnose() {
        return mongoHelper.diagnose();
    }
}
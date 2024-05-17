package com.example.schoolapp.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SchoolInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> schoolMap = new HashMap<String, String>();
        schoolMap.put("App Name", "SchoolApp");
        schoolMap.put("App Description", "Winterfell School Web Application for Students and Admin");
        schoolMap.put("App Version", "1.0.0");
        schoolMap.put("Contact Email", "info@winterfellschool.com");
        schoolMap.put("Contact Mobile", "+1(21) 673 4587");

        builder.withDetail("winterfellSchool-info", schoolMap);
    }
}

package com.mayora.meetingroom.integration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mayora.meetingroom.dto.response.AssignmentProjectResponse;

@Service
public class AssignmentProjectClient {

    @Autowired
    private RestTemplate restTemplate;

    public List<AssignmentProjectResponse> getProjects() {

        // String url = "http://192.168.0.107:9090/projects/";
        String url = "http://localhost:9005/projects/";

        ResponseEntity<AssignmentProjectResponse[]> response =
                restTemplate.getForEntity(
                        url,
                        AssignmentProjectResponse[].class
                );

        return Arrays.asList(response.getBody());
    }
}

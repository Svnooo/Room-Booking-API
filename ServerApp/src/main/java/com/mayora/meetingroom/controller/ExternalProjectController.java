package com.mayora.meetingroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayora.meetingroom.dto.response.AssignmentProjectResponse;
import com.mayora.meetingroom.integration.AssignmentProjectClient;

@RestController
public class ExternalProjectController {

    @Autowired
    private AssignmentProjectClient assignmentProjectClient;

    @GetMapping("/api/public/external-projects")
    public List<AssignmentProjectResponse> getExternalProjects() {

        return assignmentProjectClient.getProjects();
    }
}
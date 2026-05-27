package com.mayora.meetingroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignmentProjectResponse {

    private int id;
    private String name;

    private String pdfName;
    private String pdfType;

    private String imageName;
    private String imageType;

}

package com.example.testimage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class TestDto {

    private MultipartFile images;

    private String id;
}

package com.example.testimage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public List<String> uploadFile(@RequestParam("images") MultipartFile[] multipartFile) throws IOException {
        List<String> uploaded = imageService.upload(multipartFile);
        return uploaded;
    }
//asdasddfgdfgfghfghasdasdasdasdjkljklㅁㄴㅇㅁㄴㅇ
    @PostMapping("/upload-test")
    public Void uploadTestFile(@ModelAttribute TestDto testDto) throws IOException {
        imageService.uploadtest(testDto);
        return null;
    }

    @PostMapping("/download")
    public String downloadFile() throws IOException {
        String download = imageService.download();
        return download;
    }

    @PostMapping("/downloads")
    public List<String> downloadFiles() throws IOException {
        List<String> downloads = imageService.downloads();
        return downloads;
    }
}

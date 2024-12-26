package com.example.testimage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3Client amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> upload(MultipartFile[] multipartFiles) throws IOException {
        List<String> uploadList = new ArrayList<>();
        Long testCardId = 1L;
        StringBuilder packageName = new StringBuilder("/");
        packageName.append(testCardId);
        int firstIndex = 1;

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileName = multipartFile.getOriginalFilename();
            String s3FileName = firstIndex + getFileExtension(originalFileName);
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(multipartFile.getInputStream().available());

            amazonS3.putObject(bucket + "/card" + packageName, s3FileName, multipartFile.getInputStream(), objMeta);
            uploadList.add(getPublicUrl("card" + packageName + "/" + s3FileName));
            firstIndex += 1;
        }

//        String s3FileName = multipartFile.getOriginalFilename();
//        ObjectMetadata objMeta = new ObjectMetadata();
//        objMeta.setContentLength(multipartFile.getInputStream().available());
//
//        amazonS3.putObject(bucket+"/test", s3FileName, multipartFile.getInputStream(), objMeta);

        return uploadList;
    }

    public void uploadtest(MultipartFile multipartFile) throws IOException {
        String s3FileName = multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket+"/test", s3FileName, multipartFile.getInputStream(), objMeta);
    }

    private String getFileExtension(String originalFileName) {
        int lastIndexOfDot = originalFileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return ""; // 확장자가 없는 경우
        }
        return originalFileName.substring(lastIndexOfDot);
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }

    public String download() {
        return String.valueOf(amazonS3.getUrl(bucket + "/test", "수정본.jpg"));
    }

    public List<String> downloads() {
        List<String> urlList = new ArrayList<>();
        String prefix = "card/1";
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucket)
                .withPrefix(prefix);

        ListObjectsV2Result result = amazonS3.listObjectsV2(request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        for (S3ObjectSummary objectSummary : objects) {
            String objectKey = objectSummary.getKey();
            String objectUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, "us-east-1", objectKey);
            urlList.add(objectUrl);
        }
        return urlList;
    }
}

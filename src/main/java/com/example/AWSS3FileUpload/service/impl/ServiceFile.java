package com.example.AWSS3FileUpload.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.AWSS3FileUpload.entity.EntityFile;
import com.example.AWSS3FileUpload.repository.RepositoryFile;
import com.example.AWSS3FileUpload.service.IfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import java.io.InputStream;
import java.util.List;
@Service
public class ServiceFile implements IfileService {

    @Autowired
    private RepositoryFile repositoryFile;

    @Value("${aws.s3.access.key}")
    private String AWSS3ACCESSKEY;

    @Value("${aws.s3.secret.key}")
    private String AWSS3SECRETKEY;

    @Override
    public EntityFile saveFile(MultipartFile file, String name) {
        String saveFileURL = saveFileToAWS3Bucket(file);
        EntityFile fileToSave = EntityFile.builder()
                .fileUrl(saveFileURL)
                .name(name)
                .build();
        return repositoryFile.save(fileToSave);
    }

    @Override
    public List<EntityFile> getAllFiles() {
        return repositoryFile.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    private String saveFileToAWS3Bucket(MultipartFile file){
        try{
            String s3FileName = file.getOriginalFilename();
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AWSS3ACCESSKEY,AWSS3SECRETKEY);
            AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();
            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpe");
            String bucketName = "file-upload-test-practice";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,s3FileName,inputStream,objectMetadata);
            amazonS3Client.putObject(putObjectRequest);
            return "https://" + bucketName + ".s3.amazonaws.com/" + s3FileName;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        }
    }

}

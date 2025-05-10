package com.example.AWSS3FileUpload.service;

import com.example.AWSS3FileUpload.entity.EntityFile;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface IfileService {
    EntityFile saveFile(MultipartFile file, String name);
    List<EntityFile> getAllFiles();
}

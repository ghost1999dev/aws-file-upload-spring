package com.example.AWSS3FileUpload.repository;

import com.example.AWSS3FileUpload.entity.EntityFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryFile extends JpaRepository<EntityFile,Long> {

}

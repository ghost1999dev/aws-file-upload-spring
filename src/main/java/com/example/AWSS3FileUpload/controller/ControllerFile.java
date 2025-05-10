package com.example.AWSS3FileUpload.controller;

import com.example.AWSS3FileUpload.entity.EntityFile;
import com.example.AWSS3FileUpload.service.IfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class ControllerFile {

    @Autowired
    private IfileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Object> saveFile(@RequestParam(required = false)MultipartFile file,
                                           @RequestParam(required = false) String name
                                           ){
        if(file.isEmpty() || name.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The file is required to save to Bucket");
        }
        return ResponseEntity.ok(fileService.saveFile(file,name));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<EntityFile>>getAllFile(){
        return ResponseEntity.ok(fileService.getAllFiles());
    }
}

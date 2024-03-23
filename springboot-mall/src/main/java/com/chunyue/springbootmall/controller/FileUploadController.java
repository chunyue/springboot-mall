package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.configuration.FileUploadProperties;
import com.chunyue.springbootmall.service.FileUploadService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileUploadController {

    @Autowired
    FileUploadProperties fileUploadProperties;

    @Autowired
    FileUploadService fileUploadService;

    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(
            mediaType = "multipart/form-data",
            schema = @Schema(type = "object"),
            schemaProperties = {
                    @SchemaProperty(
                            name = "file",
                            schema = @Schema(type = "string", format = "binary")
                    )
            })})
    @PostMapping("product/{productId}/upload")
    public String uploadImage(@PathVariable Long productId, @RequestPart MultipartFile file) throws IOException {
        return fileUploadService.uploadImage(productId,file);
    }

}

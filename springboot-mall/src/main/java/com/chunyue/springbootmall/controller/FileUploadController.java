package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.configuration.FileUploadProperties;
import com.chunyue.springbootmall.model.ResourceDetails;
import com.chunyue.springbootmall.service.FileUploadService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    final FileUploadProperties fileUploadProperties;
    final FileUploadService fileUploadService;

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
    public String uploadImage(@PathVariable String productId, @RequestPart MultipartFile file) throws IOException {
        long productIdParseLong;

        //  檢查輸入的id是否為數字
        if(checkIsNumber(productId)){
            productIdParseLong = Long.parseLong(productId);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product ID: " + productId);
        }

        // 檢查文件是否為空
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }
        return fileUploadService.uploadImage(productIdParseLong,file);
    }

    @PostMapping("product/image")
    public ResourceDetails getImageResource(@RequestBody String path) {

        return fileUploadService.getImageResource(path);
    }

    private boolean checkIsNumber(String stringInput){
        if (stringInput == null || "".equals(stringInput)){
            return false;
        }
        for(char c : stringInput.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}

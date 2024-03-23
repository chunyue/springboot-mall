package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.configuration.FileUploadProperties;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final FileUploadProperties fileUploadProperties;

    public String uploadImage(Long Id, MultipartFile imageFile) throws IOException {
        String rootpath = fileUploadProperties.getRootPath();

        //檢查上傳的檔案是否為空
        if(imageFile.isEmpty() || Objects.isNull(imageFile.getOriginalFilename())){
            throw new IllegalArgumentException();
        }

        //把絕對路徑先找出來，放在Path物件 （parent path）
        String fileDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy" + File.separator + "MM" + File.separator + "dd"));
        Path parePath = Paths.get(rootpath,fileDir);
        log.info("儲存檔案至： " + parePath + "資料夾中");

        //解析出檔名, 1-UUID-檔名加密
        String endoceFileName = Id.toString()
                + "-"
                + UUID.randomUUID().toString().replace("-","")
                + "-"
                + Base64.getUrlEncoder().encodeToString(imageFile.getOriginalFilename().getBytes(StandardCharsets.UTF_8));
        Path wholePath = parePath.resolve(endoceFileName);

        //此方法會檢查父層資料夾是否存在，如果不存在就創建，存在的話 也不會報錯
        Files.createDirectories(parePath);
        //Mutipart方法將檔案送到目標path
        imageFile.transferTo(wholePath);
        //用URL的加密方法，給這個檔案一個關聯用的名稱存到DB
        return URLEncoder.encode(wholePath.toString().replace(File.separator,"_"),StandardCharsets.UTF_8);

    }


}

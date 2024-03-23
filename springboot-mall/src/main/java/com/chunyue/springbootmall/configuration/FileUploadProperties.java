package com.chunyue.springbootmall.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "mall.file-upload")
public class FileUploadProperties {

    String rootPath;
}

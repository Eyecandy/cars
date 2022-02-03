package no.linska.webapp.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class StorageProperties {

    @Value("${storage.upload.dir}")
    private String uploadDir;


    public String getUploadDir() {
        return uploadDir;
    }


}

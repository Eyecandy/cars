package no.linska.mailsender.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileWatcherProperty {

    @Value("${filewatcher.upload.dir.path}")
    private String dirPath;


    public String getDirPath() {
        return dirPath;
    }
}

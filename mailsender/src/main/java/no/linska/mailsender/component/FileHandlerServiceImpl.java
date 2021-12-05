package no.linska.mailsender.component;

import no.linska.mailsender.properties.FileHandlerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {

    @Autowired
    FileHandlerProperties fileHandlerProperties;

    @Override
    public void moveFile(File file) {
        String newName = fileHandlerProperties.getDestinationPath() +  java.time.LocalTime.now() + "_" + file.getName();
        if (!file.renameTo(new File(newName))) {
            try {
                throw new Exception("FAILED TO RENAME FILE");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

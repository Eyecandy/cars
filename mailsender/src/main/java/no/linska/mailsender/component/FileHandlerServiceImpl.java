package no.linska.mailsender.component;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {


    @Override
    public void moveFile(File file, String destination) {
        String newName = destination +  java.time.LocalTime.now() + "_" + file.getName();
        if (!file.renameTo(new File(newName))) {
            try {
                throw new Exception("FAILED TO RENAME FILE");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

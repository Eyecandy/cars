package no.linska.mailsender.component;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface FileHandlerService {

    void moveFile(File file);

}

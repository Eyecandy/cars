package no.linska.webapp.mailsender.component;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface FileHandlerService {

    void moveFile(File file);

}

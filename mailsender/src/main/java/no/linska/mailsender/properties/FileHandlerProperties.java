package no.linska.mailsender.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileHandlerProperties {

    @Value("${filehandler.mailsent.destination.path}")
    private String destinationPath;


    public String getDestinationPath() {
        return destinationPath;
    }
}

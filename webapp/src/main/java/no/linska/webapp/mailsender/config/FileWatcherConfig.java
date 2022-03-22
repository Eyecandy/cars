package no.linska.webapp.mailsender.config;

import lombok.extern.slf4j.Slf4j;
import no.linska.webapp.mailsender.component.CustomFileChangeListener;
import no.linska.webapp.mailsender.properties.FileWatcherProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;
import java.util.List;

@Slf4j
@Configuration
public class FileWatcherConfig {

    @Autowired
    private FileWatcherProperty fileWatcherProperty;

    @Autowired
    CustomFileChangeListener customFileChangeListener;

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(5000L), Duration.ofMillis(3000L));
        Iterable<File> iterable = List.of(new File(fileWatcherProperty.getDirPath()));
        fileSystemWatcher.addSourceDirectories(iterable);
        fileSystemWatcher.addListener(customFileChangeListener);
        fileSystemWatcher.start();
        log.info("started FileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }

}

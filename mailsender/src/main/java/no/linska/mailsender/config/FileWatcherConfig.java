package no.linska.mailsender.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import no.linska.mailsender.component.CustomFileChangeListener;
import no.linska.mailsender.properties.FileWatcherProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(5000L), Duration.ofMillis(3000L));
        Iterable<File> iterable = List.of(new File(fileWatcherProperty.getDirPath()));
        fileSystemWatcher.addSourceDirectories(iterable);
        fileSystemWatcher.addListener(new CustomFileChangeListener());
        fileSystemWatcher.start();
        System.out.println("started fileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }

}

package no.linska.mailsender.component;


import no.linska.mailsender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;


@Component
public class CustomFileChangeListener implements FileChangeListener {
    @Autowired
    private FileHandlerService fileHandlerService;

    @Autowired
    private EmailService emailService;

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for(ChangedFiles cfiles : changeSet) {
            for(ChangedFile cfile: cfiles.getFiles()) {
                if(cfile.getType().equals(ChangedFile.Type.ADD)
                      && !isLocked(cfile.getFile().toPath())) {

                    emailService.sendMessageWithAttachment("seller1@linska.local", cfile.getFile());
                    fileHandlerService.moveFile(cfile.getFile());

                }
            }
        }
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }
}

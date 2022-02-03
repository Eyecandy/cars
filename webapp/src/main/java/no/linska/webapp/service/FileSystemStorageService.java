package no.linska.webapp.service;

import no.linska.webapp.exception.StorageException;
import no.linska.webapp.exception.StorageFileNotFoundException;
import no.linska.webapp.exception.reason.Reason;
import no.linska.webapp.properties.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service("fileSystemStorageService")
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;


	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getUploadDir());
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException(Reason.STORAGE_COULD_NOT_STORE_EMPTY_FILE);
			}
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				Reason reason = Reason.STORAGE_MUST_BE_CORRECT_DIRECTORY;
				throw new StorageException(reason,reason.getMessage(this.rootLocation.toAbsolutePath()));
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException(Reason.STORAGE_FILE_CAN_NOT_BE_STORED, e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException(Reason.STORAGE_FILES_CAN_NOT_BE_READ,e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				Reason reason = Reason.STORAGE_COULD_NOT_READ_FILE;
				throw new StorageFileNotFoundException(
						reason,reason.getMessage(filename));

			}
		}
		catch (MalformedURLException e) {
			Reason reason = Reason.STORAGE_FILE_NOT_FOUND_EXCEPTION;
			throw new StorageFileNotFoundException(reason, reason.getMessage(filename));
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}


	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException(Reason.STORAGE_COULD_NOT_INIT);
		}
	}
}

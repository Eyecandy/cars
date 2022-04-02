package no.linska.webapp.service;

import no.linska.webapp.entity.User;
import no.linska.webapp.exception.StorageException;
import no.linska.webapp.exception.StorageFileNotFoundException;
import no.linska.webapp.exception.reason.Reason;
import no.linska.webapp.properties.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

@Service("fileSystemStorageService")
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;


	@Autowired
	UserService userService;


	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getUploadDir());
	}

	@Override
	public Path store(MultipartFile file) throws StorageException {
		Path storePath;
		try {
			if (file.isEmpty()) {
				throw new StorageException(Reason.STORAGE_COULD_NOT_STORE_EMPTY_FILE);
			}

			storePath = getDestinationPath(file);



			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, storePath,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException(Reason.STORAGE_FILE_CAN_NOT_BE_STORED, e);
		}
		return storePath;

	}

	@Override
	public Stream<Path> loadAll() throws StorageException {
		Path userPath = getUserPath();
		try {
			return Files.walk(userPath, 1)
				.filter(path -> !path.equals(userPath))
				.map(userPath::relativize);
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
	public Resource loadAsResource(String filename) throws StorageException {
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
		FileSystemUtils.deleteRecursively(getUserPath().toFile());
	}

	// TODO: consider a better placement for init() function
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException(Reason.STORAGE_COULD_NOT_INIT);
		}
	}

	//TODO: consider a better place to put creteUserDir() function
	@Override
	public void createUserDir(String userPath) {
		try {
			Files.createDirectories(rootLocation.resolve(userPath));
		}
		catch (IOException e) {
			throw new StorageException(Reason.STORAGE_COULD_NOT_INIT);
		}
	}

	private Path getDestinationPath(MultipartFile file) {
		Path userPath = getUserPath();

		Path destinationPath =  userPath.resolve(
						Paths.get(Objects.requireNonNull(file.getOriginalFilename()))
								+ new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())
				)
				.normalize().toAbsolutePath();
		if (!destinationPath.getParent().equals(userPath.toAbsolutePath())) {
			// This is a security check
			Reason reason = Reason.STORAGE_MUST_BE_CORRECT_DIRECTORY;
			throw new StorageException(reason,reason.getMessage(userPath.toAbsolutePath()));
		}
		return destinationPath;

	}
	// TODO: see if we can many calls of this function
	// suggestion:  add user Path in custom UserDetails impl and get it from session.
	private Path getUserPath() throws StorageFileNotFoundException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = userService.findByEmail(name);
		Path userPath = rootLocation.resolve(user.getId().toString());
		if (!userPath.toFile().exists()) {
			createUserDir(user.getId().toString());
			Reason reason = Reason.STORAGE_COULD_NOT_READ_FILE;
			throw new StorageFileNotFoundException(
					reason,reason.getMessage(userPath.getFileName()
					+ "(should been create at time of registration, but was created now)"));
		}
		return rootLocation.resolve(user.getId().toString());
	}
}

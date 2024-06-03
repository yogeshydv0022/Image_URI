package com.upload.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.upload.exception.FileNotSupportedException;
import com.upload.model.FileData;
import com.upload.repository.FileDataRepository;

@Service
public class FileDataService {

	public FileDataService() throws IOException {
	}

	@Autowired
	private FileDataRepository fileDataRepository;

	// Paths
	private final Path UPLOAD_PATH = Paths.get(new ClassPathResource("").getFile().getAbsolutePath() + File.separator
			+ "static" + File.separator + "image");

	// Upload Images
	public FileData uploadFile(MultipartFile file) throws IOException {
		if (!Files.exists(UPLOAD_PATH)) {
			Files.createDirectories(UPLOAD_PATH);
		}
		// file format validation
		if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
			throw new FileNotSupportedException("only .jpeg and .png images are " + "supported");
		}
		String timeStampedFileName = new SimpleDateFormat("ssmmHHddMMyyyy").format(new Date()) + "_"
				+ file.getOriginalFilename();
		Path filePath = UPLOAD_PATH.resolve(timeStampedFileName);
		Files.copy(file.getInputStream(), filePath);
		String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(timeStampedFileName)
				.toUriString();
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download/")
				.path(timeStampedFileName).toUriString();
		FileData fileDetails = new FileData(file.getOriginalFilename(), fileUri, fileDownloadUri, file.getSize());
		FileData Records = fileDataRepository.save(fileDetails);
		return Records;
	}

	// fatch Single Image
	// ============================Not Working========================================
	public Resource fetchFileAsResource(String fileName) throws FileNotFoundException {
		try {
			Path filePath = UPLOAD_PATH.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}
	}

	// get All Images
	public List<FileData> getAllFiles() {
		return fileDataRepository.findAll();
	}

	// Delete By Id
	public void DeleteById(long id) {
		fileDataRepository.deleteById(id);
	}

}
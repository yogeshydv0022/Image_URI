package com.upload.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileData {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;

	  private String fileName;
	  
	  private String fileUri;
	  
	  private String fileDownloadUri;
	 
	  private long fileSize;

	public FileData(String fileName, String fileUri, String fileDownloadUri, long fileSize) {
		super();
		this.fileName = fileName;
		this.fileUri = fileUri;
		this.fileDownloadUri = fileDownloadUri;
		this.fileSize = fileSize;
	}
	  
	  
	
	

}

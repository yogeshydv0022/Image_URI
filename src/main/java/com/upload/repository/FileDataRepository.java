package com.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upload.model.FileData;

public interface FileDataRepository  extends JpaRepository<FileData,Long>  {
	

}

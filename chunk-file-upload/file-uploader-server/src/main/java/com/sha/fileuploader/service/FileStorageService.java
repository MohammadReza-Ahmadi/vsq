package com.sha.fileuploader.service;

import com.sha.fileuploader.model.CompleteFileInfo;
import com.sha.fileuploader.model.FileMetadata;
import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface FileStorageService {
    FileMetadata mergeFile(final CompleteFileInfo fileInfo);

    String storeFile(FileMetadata fileMetadata, InputStream stream, int currentChunk);

    Resource loadFileAsResource(String fileName);
}

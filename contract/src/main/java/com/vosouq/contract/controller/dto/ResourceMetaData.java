package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.FileType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@Getter
@Setter
@NoArgsConstructor
public class ResourceMetaData {

    private Resource resource;
    private MediaType mediaType;
    private FileType fileType;
    private Long fileSize;
}

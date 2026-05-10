package nl.novi.backendeindopdracht.controllers;

import org.springframework.core.io.Resource;




public class FileResponseDto {

    private final Resource resource;
    private final String contentType;
    private final String filename;

    public FileResponseDto(Resource resource, String contentType, String filename) {
        this.resource = resource;
        this.contentType = contentType;
        this.filename = filename;
    }

    public Resource getResource() {
        return resource;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFilename() {
        return filename;
    }





}

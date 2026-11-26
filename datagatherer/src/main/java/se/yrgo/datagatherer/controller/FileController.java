package se.yrgo.datagatherer.controller;


import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.yrgo.datagatherer.service.MetadataService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileController {

    private MetadataService metadataService;

    public FileController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping("/track/{hashId}")
    public ResponseEntity<Resource> getTrackFile(@PathVariable String hashId) throws IOException {

        String trackPath = metadataService.getTrackPathById(hashId);

        try {
            Resource resource = getResource(trackPath);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/ogg"))
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cover/{hashId}")
    public ResponseEntity<Resource> getCoverFile(@PathVariable String hashId) throws IOException {

        String coverPath = metadataService.getCoverPathById(hashId);

        try {
            Resource resource = getResource(coverPath);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Resource getResource(String pathString) {
        Path path = Path.of(pathString);

        if (!Files.isRegularFile(path)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new FileSystemResource(path);
    }

}

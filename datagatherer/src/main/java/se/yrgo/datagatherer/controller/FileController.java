package se.yrgo.datagatherer.controller;


import org.springframework.core.io.Resource;
import jakarta.websocket.server.PathParam;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.yrgo.datagatherer.entity.CoverFile;
import se.yrgo.datagatherer.entity.TrackFile;
import se.yrgo.datagatherer.repository.CoverFileRepository;
import se.yrgo.datagatherer.repository.TrackFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileController {

    CoverFileRepository coverRepo;
    TrackFileRepository trackRepo;

    public FileController(CoverFileRepository coverRepo, TrackFileRepository trackRepo) {
        this.coverRepo = coverRepo;
        this.trackRepo = trackRepo;
    }

    @GetMapping("/track/{hashId}")
    public ResponseEntity<Resource> getTrackFile(@PathVariable String hashId) throws IOException {

        TrackFile trackFile = trackRepo.findById(hashId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try {
            Resource resource = getResource(trackFile.getPath());
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

        CoverFile coverFile = coverRepo.findByHashId(hashId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try {
            Resource resource = getResource(coverFile.getPath());

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

    @GetMapping("/test")
    public String test() {
        return trackRepo.findById("4fg27WmVUB6grfVy").isPresent()
                ? "Found"
                : "Not found";
    }


}

package se.yrgo.datagatherer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.yrgo.datagatherer.metadata.MetadataExtractor;

import java.util.List;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    @GetMapping
    public ResponseEntity<List<MetadataExtractor.Track>> generateMetadata(){
        return ResponseEntity.ok(MetadataExtractor.extractAllMetadata());
    }

}

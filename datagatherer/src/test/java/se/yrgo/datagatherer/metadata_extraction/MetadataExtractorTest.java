package se.yrgo.datagatherer.metadata_extraction;
import org.junit.jupiter.api.Test;
import se.yrgo.datagatherer.entity.TrackMetadata;
import se.yrgo.datagatherer.service.MetadataService;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetadataExtractorTest {

    @Test
    public void testExtract() {
        MetadataService ms = mock(MetadataService.class);
        when(ms.getCachedMetadata()).thenReturn(List.of());
        MetadataExtractor.scanAllTracks(ms);
    }

}

package se.yrgo.metadataprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.metadataprovider.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long>{
    
}

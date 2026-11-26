package se.yrgo.metadataprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.metadataprovider.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}

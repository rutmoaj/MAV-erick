package se.yrgo.datagatherer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.datagatherer.entity.CoverFile;

import java.util.Optional;


public interface CoverFileRepository extends JpaRepository<CoverFile, String> {
    public Optional<CoverFile> findByHashId(String hashId);

    public void deleteAll();

}

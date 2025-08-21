package service;

import entity.LocationSection;
import org.springframework.stereotype.Service;
import repository.LocationSectionRepository;

import java.util.List;

@Service
public class LocationSectionService {
    private final LocationSectionRepository sectionRepo;

    public LocationSectionService(LocationSectionRepository sectionRepo) {
        this.sectionRepo = sectionRepo;
    }

    public List<LocationSection> getAllSections() {
        return sectionRepo.findAll();
    }

    public LocationSection getSectionById(Long id) {
        return sectionRepo.findById(id).orElseThrow();
    }

    public LocationSection saveSection(LocationSection section) {
        return sectionRepo.save(section);
    }

    public void deleteSection(Long id) {
        sectionRepo.deleteById(id);
    }
}
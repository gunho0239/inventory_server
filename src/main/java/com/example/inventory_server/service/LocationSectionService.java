package com.example.inventory_server.service;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.LocationSection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.inventory_server.repository.LocationSectionRepository;

import java.util.ArrayList;
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

    public List<LocationSection> saveAllSections(List<LocationSection> sections) {
        List<LocationSection> savedSections = new ArrayList<>();

        for (LocationSection section : sections) {
            if (!sectionRepo.existsBySection(
                    section.getSection()
            )) {
                savedSections.add(sectionRepo.save(section));
            }
        }

        return savedSections;
    }

    public void deleteSection(Long id) {
        sectionRepo.deleteById(id);
    }

    public BulkRequestResponse deleteAllSections(List<Long> ids) {
        BulkRequestResponse result = new BulkRequestResponse();

        for (Long id : ids) {
            try {
                sectionRepo.deleteById(id);
                result.increaseSuccussCount();
            } catch (DataIntegrityViolationException e) {
                result.increaseFailedCount();
            }
        }

        return result;
    }
}
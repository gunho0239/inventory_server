package com.example.inventory_server.service;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.Location;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.inventory_server.repository.LocationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepo;

    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<Location> getAllLocations() {
        return locationRepo.findAllByOrderBySection_SectionAscNumberAsc();
    }

    public Location getLocationById(Long id) {
        return locationRepo.findById(id).orElseThrow();
    }

    public List<Location> getLocationBySectionId(Long sectionId) { return locationRepo.findBySection_IdOrderByNumberAsc(sectionId); }

    public Location saveLocation(Location location) {
        if (!locationRepo.existsBySection_IdAndNumber(
                location.getSection().getId(),
                location.getNumber()
        )) {
            return locationRepo.save(location);
        }
        return null;
    }

    public List<Location> saveAllLocations(List<Location> locations) {
        List<Location> savedLocations = new ArrayList<>();

        for (Location location : locations) {
            if (!locationRepo.existsBySection_IdAndNumber(
                    location.getSection().getId(),
                    location.getNumber()
            )) {
                savedLocations.add(locationRepo.save(location));
            }
        }

        return savedLocations;
    }

    public void deleteLocation(Long id) {
        locationRepo.deleteById(id);
    }

    public BulkRequestResponse deleteAllLocations(List<Long> ids) {
        BulkRequestResponse result = new BulkRequestResponse();

        for (Long id : ids) {
            try {
                locationRepo.deleteById(id);
                result.increaseSuccussCount();
            } catch (DataIntegrityViolationException e) {
                result.increaseFailedCount();
            }
        }

        return result;
    }
}

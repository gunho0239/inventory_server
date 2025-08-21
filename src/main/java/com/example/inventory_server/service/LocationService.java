package service;

import entity.Location;
import org.springframework.stereotype.Service;
import repository.LocationRepository;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepo;

    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }

    public Location getLocationById(Long id) {
        return locationRepo.findById(id).orElseThrow();
    }

    public Location saveLocation(Location location) {
        return locationRepo.save(location);
    }

    public void deleteLocation(Long id) {
        locationRepo.deleteById(id);
    }
}

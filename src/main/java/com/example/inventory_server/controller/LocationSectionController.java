package controller;

import entity.LocationSection;
import org.springframework.web.bind.annotation.*;
import service.LocationSectionService;

import java.util.List;

@RestController
@RequestMapping("/api/location-sections")
public class LocationSectionController {
    private final LocationSectionService sectionService;

    public LocationSectionController(LocationSectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<LocationSection> getSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/{id}")
    public LocationSection getSection(@PathVariable Long id) {
        return sectionService.getSectionById(id);
    }

    @PostMapping
    public LocationSection createSection(@RequestBody LocationSection section) {
        return sectionService.saveSection(section);
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
    }
}

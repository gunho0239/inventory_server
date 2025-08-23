package com.example.inventory_server.service;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.Part;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.inventory_server.repository.PartRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartService {
    private final PartRepository partRepo;

    public PartService(PartRepository partRepo) {
        this.partRepo = partRepo;
    }

    public List<Part> getAllParts() {
        return partRepo.findAllByOrderByType_TypeAscSpecificationAsc();
    }

    public Part getPartById(Long id) {
        return partRepo.findById(id).orElseThrow();
    }

    public List<Part> getPartByTypeId(Long typeId) {
        return partRepo.findByType_IdOrderBySpecificationAsc(typeId);
    }

    public List<Part> getPartByMakerId(Long makerId) {
        return partRepo.findByMaker_IdOrderByType_TypeAscSpecificationAsc(makerId);
    }

    public List<Part> getParts(Long typeId, Long makerId, String specification) {
        if (typeId != null && makerId != null && specification != null) {
            return partRepo.findByType_IdAndMaker_IdAndSpecificationContainingOrderBySpecificationAsc(
                    typeId, makerId, specification);
        }

        if (typeId != null && makerId != null) {
            return partRepo.findByType_IdAndMaker_IdOrderBySpecificationAsc(typeId, makerId);
        }

        if (typeId != null && specification != null) {
            return partRepo.findByType_IdAndSpecificationContainingOrderBySpecificationAsc(typeId, specification);
        }

        if (makerId != null && specification != null) {
            return partRepo.findByMaker_IdAndSpecificationContainingOrderByType_TypeAscSpecificationAsc(
                    makerId, specification);
        }

        if (typeId != null) {
            return getPartByTypeId(typeId);
        }

        if (makerId != null) {
            return getPartByMakerId(makerId);
        }

        if (specification != null) {
            return partRepo.findBySpecificationContainingOrderByType_TypeAscSpecificationAsc(specification);
        }

        return getAllParts();
    }

//    public List<Part> getPartBySpecification(String specification) {
//        return partRepo.findBySpecificationContainingOrderBySpecificationAsc(specification);
//    }

//    public List<Part> getPartByTypeIdAndSpecification(Long typeId, String specification) {
//        return partRepo.findByType_IdAndSpecificationContainingOrderBySpecificationAsc(typeId, specification);
//    }

//    public List<Part> getPartByMakerIdAndSpecification(Long makerId, String specification) {
//        return partRepo.findByMaker_IdAndSpecificationContainingOrderBySpecificationAsc(makerId, specification);
//    }

    public Part savePart(Part part) {
        if (!partRepo.existsByType_IdAndSpecificationAndMaker_IdAndUnit_Id(
                part.getType().getId(),
                part.getSpecification(),
                part.getMaker().getId(),
                part.getUnit().getId()
        )) {
            return partRepo.save(part);
        }
        return null;
    }

    public List<Part> saveAllParts(List<Part> parts) {
        List<Part> savedParts = new ArrayList<>();

        for (Part part : parts) {
            if (!partRepo.existsByType_IdAndSpecificationAndMaker_IdAndUnit_Id(
                    part.getType().getId(),
                    part.getSpecification(),
                    part.getMaker().getId(),
                    part.getUnit().getId()
            )) {
                savedParts.add(partRepo.save(part));
            }
        }

        return savedParts;
    }

    public void deletePart(Long id) {
        partRepo.deleteById(id);
    }

    public BulkRequestResponse deleteAllParts(List<Long> ids) {
        BulkRequestResponse result = new BulkRequestResponse();

        for (Long id : ids) {
            try {
                partRepo.deleteById(id);
                result.increaseSuccussCount();
            } catch (DataIntegrityViolationException e) {
                result.increaseFailedCount();
            }
        }

        return result;
    }
}

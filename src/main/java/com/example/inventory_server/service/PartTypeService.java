package com.example.inventory_server.service;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.PartType;
import com.example.inventory_server.repository.PartTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartTypeService {
    private final PartTypeRepository typeRepo;

    public PartTypeService(PartTypeRepository typeRepo) {
        this.typeRepo = typeRepo;
    }

    public List<PartType> getAllTypes() {
        return typeRepo.findAll();
    }

    public PartType getTypeById(Long id) {
        return typeRepo.findById(id).orElseThrow();
    }

    public PartType saveType(PartType type) {
        return typeRepo.save(type);
    }

    public List<PartType> saveAllTypes(List<PartType> types) {
        List<PartType> savedTypes = new ArrayList<>();

        for (PartType type : types) {
            if (!typeRepo.existsByType(
                    type.getType()
            )) {
                savedTypes.add(typeRepo.save(type));
            }
        }

        return savedTypes;
    }

    public void deleteType(Long id) {
        typeRepo.deleteById(id);
    }

    public BulkRequestResponse deleteAllTypes(List<Long> ids) {
        BulkRequestResponse result = new BulkRequestResponse();

        for (Long id : ids) {
            try {
                typeRepo.deleteById(id);
                result.increaseSuccussCount();
            } catch (DataIntegrityViolationException e) {
                result.increaseFailedCount();
            }
        }

        return result;
    }
}
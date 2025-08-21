package com.example.inventory_server.service;

import com.example.inventory_server.dto.BulkRequestResponse;
import com.example.inventory_server.entity.PartMaker;
import com.example.inventory_server.repository.PartMakerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartMakerService {
    private final PartMakerRepository makerRepo;

    public PartMakerService(PartMakerRepository makerRepo) {
        this.makerRepo = makerRepo;
    }

    public List<PartMaker> getAllMakers() {
        return makerRepo.findAll();
    }

    public PartMaker getMakerById(Long id) {
        return makerRepo.findById(id).orElseThrow();
    }

    public PartMaker saveMaker(PartMaker maker) {
        return makerRepo.save(maker);
    }

    public List<PartMaker> saveAllMakers(List<PartMaker> makers) {
        List<PartMaker> savedMakers = new ArrayList<>();

        for (PartMaker maker : makers) {
            if (!makerRepo.existsByMaker(
                    maker.getMaker()
            )) {
                savedMakers.add(makerRepo.save(maker));
            }
        }

        return savedMakers;
    }

    public void deleteMaker(Long id) {
        makerRepo.deleteById(id);
    }

    public BulkRequestResponse deleteAllMakers(List<Long> ids) {
        BulkRequestResponse result = new BulkRequestResponse();

        for (Long id : ids) {
            try {
                makerRepo.deleteById(id);
                result.increaseSuccussCount();
            } catch (DataIntegrityViolationException e) {
                result.increaseFailedCount();
            }
        }

        return result;
    }
}
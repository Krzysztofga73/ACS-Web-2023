package com.example.accesscontrolsystem.service;

import com.example.accesscontrolsystem.model.BuildingModel;
import com.example.accesscontrolsystem.model.RoomModel;
import com.example.accesscontrolsystem.repository.BuildingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor


public class BuildingService {
    private final BuildingRepository buildingRepository;

    public void addBuilding(BuildingModel buildingModel) {
        buildingRepository.save(buildingModel);
    }

    public List<BuildingModel> getBuildingList() {
        return buildingRepository.findAll();
    }

    public BuildingModel getBuildingById(Long id) {
        return buildingRepository.findById(id).orElse(null);
    }

    public void saveEditBuilding(BuildingModel building) {
        buildingRepository.save(building);
    }

    public void removeBuilding(Long id) {
        buildingRepository.deleteById(id);
    }

    public void sortBuildings(List<BuildingModel> buildings, String sortBy) {
        if (sortBy != null) {
            switch (sortBy) {
                case "id":
                    buildings.sort(Comparator.comparing(BuildingModel::getId));
                    break;
                case "name":
                    buildings.sort(Comparator.comparing(u -> u.getName().toLowerCase()));
                    break;
                default:
                    // Obsłuż nieznany parametr sortowania
                    break;
            }
        }
    }

    public String getBuildingsCount(List<BuildingModel> list) {
        return "Total number of building: " + list.size();
    }
}

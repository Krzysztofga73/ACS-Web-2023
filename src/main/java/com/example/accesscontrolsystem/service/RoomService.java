package com.example.accesscontrolsystem.service;

import com.example.accesscontrolsystem.model.RoomModel;
import com.example.accesscontrolsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public void addRoom(RoomModel user) {
        roomRepository.save(user);
    }

    public List<RoomModel> getRoomList() {
        return roomRepository.findAll();
    }

    public RoomModel getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void saveEditRoom(RoomModel room) {
        roomRepository.save(room);
    }

    public void removeRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public void sortRooms(List<RoomModel> rooms, String sortBy) {
        if (sortBy != null) {
            switch (sortBy) {
                case "id":
                    rooms.sort(Comparator.comparing(RoomModel::getId));
                    break;
                case "name":
                    rooms.sort(Comparator.comparing(u -> u.getName().toLowerCase()));
                    break;
                case "buildingModel":
                    rooms.sort(Comparator.comparing(e -> e.getBuilding().getId()));
                    break;
                case "zone":
                    rooms.sort(Comparator.comparing(u -> u.getZone().toString().toLowerCase()));
                    break;
                case "department":
                    rooms.sort(Comparator.comparing(u -> u.getDepartments().toString().toLowerCase()));
                    break;
                default:
                    // Obsłuż nieznany parametr sortowania
                    break;
            }
        }
    }

    public String getRoomCount(List<RoomModel> rooms) {
        return "Total number of rooms: " + rooms.size();
    }
}

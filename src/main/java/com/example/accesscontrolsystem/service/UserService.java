package com.example.accesscontrolsystem.service;

import com.example.accesscontrolsystem.model.BuildingModel;
import com.example.accesscontrolsystem.model.UserModel;
import com.example.accesscontrolsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserModel> getUserList() {
        return (List<UserModel>) userRepository.findAll();
    }

    public void addUser(UserModel user) {
        userRepository.save(user);
    }

    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveEditUser(UserModel user) {
        userRepository.save(user);
    }

    public void removeUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.deleteById(id);
    }

    public String getUsersCount(List<UserModel> user) {
        return "Total number of users: " + user.size();
    }

    public void sortUsers(List<UserModel> users, String sortBy) {
        if (sortBy != null) {
            switch (sortBy) {
                case "id":
                    users.sort(Comparator.comparing(UserModel::getId));
                    break;
                case "name":
                    users.sort(Comparator.comparing(u -> u.getName().toLowerCase()));
                    break;
                case "surname":
                    users.sort(Comparator.comparing(u -> u.getSurname().toLowerCase()));
                    break;
                case "buildingModel":
                    users.sort(Comparator.comparing(u -> u.getBuildingModels().isEmpty() ? null : u.getBuildingModels().get(0).getName()));

                    break;
                case "department":
                    users.sort(Comparator.comparing(u -> u.getDepartment().getDisplayText().toLowerCase()));
                    break;
                case "accessList":
                    users.sort(Comparator.comparing(user -> user.getAccessList().isEmpty() ? null : user.getAccessList().get(0)));
                    break;
                case "positions":
                    users.sort(Comparator.comparing(u -> u.getPositions().getDisplayText().toLowerCase()));
                    break;
                default:
                    // Obsłuż nieznany parametr sortowania
                    break;
            }
        }
    }
}

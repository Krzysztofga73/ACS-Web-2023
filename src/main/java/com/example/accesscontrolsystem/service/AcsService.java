package com.example.accesscontrolsystem.service;

import com.example.accesscontrolsystem.model.BuildingModel;
import com.example.accesscontrolsystem.model.Positions;
import com.example.accesscontrolsystem.model.RoomModel;
import com.example.accesscontrolsystem.model.UserModel;
import com.example.accesscontrolsystem.repository.AcsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AcsService {
    private final BuildingService buildingService;
    private final UserService userService;
    private final RoomService roomService;
    private final AcsRepository acsRepository;
    private final AccessCheckResultService accessCheckResultService;


    public String checkAccess(Long userId, Long roomId) {   //'Access to '+ ${room.name} + ' is granted for user '+ ${selectedUser.name}+'.'"></p>
        UserModel user = userService.getUserById(userId);
        RoomModel room = roomService.getRoomById(roomId);
        if (checkPositionUser(userId)) {
            return "Access to " + room + " is granted for user " + user.getName() + " " + user.getSurname() + ". User has access everywhere.";
        } else {
            if (checkAccessList(userId, roomId) && checkIfUserHasAccessToBuilding(userId, roomId)) {
                return "Access to " + room + " is granted for user " + user.getName() + " " + user.getSurname() + ".";
            } else {
                if (!checkAccessList(userId, roomId) && !checkIfUserHasAccessToBuilding(userId, roomId)) {
                    return "Access to " + room + " is not granted for user " + user.getName() + " " + user.getSurname() + ". Building and access list for this user is noncompatible.";
                } else if (!checkAccessList(userId, roomId) && checkIfUserHasAccessToBuilding(userId, roomId)) {
                    return "Access to " + room + " is not granted for user " + user.getName() + " " + user.getSurname() + ". Access list for this user is noncompatible.";
                } else if (checkAccessList(userId, roomId) && !checkIfUserHasAccessToBuilding(userId, roomId)) {
                    return "Access to " + room + " is not granted for user " + user.getName() + " " + user.getSurname() + ". Building for this user is noncompatible.";
                } else {
                    return "Not checked";
                }
            }
        }
    }

    public boolean checkIfUserHasAccessToBuilding(Long userId, Long roomId) {
        UserModel user = userService.getUserById(userId);
        RoomModel room = roomService.getRoomById(roomId);
        List<BuildingModel> userBuildingList = user.getBuildingModels();
        if (userBuildingList.contains(room.getBuilding())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPositionUser(Long userId) {
        UserModel user = userService.getUserById(userId);
        if (user.getPositions() == Positions.MANAGER) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkAccessList(Long userId, Long roomId) {
        UserModel user = userService.getUserById(userId);
        RoomModel room = roomService.getRoomById(roomId);
        if (user.getAccessList().contains(room.getZone())) {
            return true;
        } else {
            return false;
        }
    }
}


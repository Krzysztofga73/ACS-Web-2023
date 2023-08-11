package com.example.accesscontrolsystem.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.accesscontrolsystem.model.*;
import com.example.accesscontrolsystem.service.AcsService;
import com.example.accesscontrolsystem.service.RoomService;
import com.example.accesscontrolsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AcsServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private AcsService yourService;

    @Test
    void testCheckAccess_UserHasPositionAndAccess() {

        Long userId = 1L;
        Long roomId = 2L;

        // Tworzenie przykładowego użytkownika z odpowiednimi wartościami
        UserModel user = new UserModel();
        user.setName("John");
        user.setPositions(Positions.MANAGER);

        // Tworzenie przykładowego pokoju
        RoomModel room = new RoomModel();
        room.setName("Conference Room");

        // Mockowanie metody getUserById zwracając obiekt user
        when(userService.getUserById(userId)).thenReturn(user);

        // Mockowanie metody getRoomById zwracając obiekt room
        when(roomService.getRoomById(roomId)).thenReturn(room);

        // Wywołanie testowanej metody
        String result = yourService.checkAccess(userId, roomId);

        assertThat(result).isEqualTo("Access to Conference Room is granted for user John null. User has access everywhere.");
    }

    @Test
    public void testCheckIfUserHasAccessToBuildingWithAccess() {
        // Tworzenie przykładowego użytkownika z listą budynków zawierającą BuildingModel o id 1
        UserModel user = new UserModel();
        BuildingModel building = new BuildingModel();
        building.setId(1L);
        user.setBuildingModels(List.of(building));

        // Przekazanie budynku id 1 do przykładowego pokoju o
        RoomModel room = new RoomModel();
        BuildingModel roomBuilding = new BuildingModel();
        roomBuilding.setId(1L);
        room.setBuilding(building);

        // Definiowanie zachowań metod getUserById(userId) i getRoomById(roomId) w mockach userService i roomService
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(roomService.getRoomById(anyLong())).thenReturn(room);

        // Wywołanie testowanej metody
        boolean result = yourService.checkIfUserHasAccessToBuilding(1L, 1L);

        // Sprawdzenie czy wynik jest zgodny z oczekiwaniami
        assertTrue(result);
    }

    @Test
    public void testCheckIfUserHasAccessToBuildingWithoutAccess() {
        // Tworzenie przykładowego użytkownika z listą budynków nie zawierającą BuildingModel o id 1
        UserModel user = new UserModel();
        BuildingModel building = new BuildingModel();
        building.setId(2L); // Załóżmy, że to inny budynek
        user.setBuildingModels(List.of(building));

        // Tworzenie przykładowego pokoju o budynku o id 1
        RoomModel room = new RoomModel();
        BuildingModel roomBuilding = new BuildingModel();
        roomBuilding.setId(1L);
        room.setBuilding(roomBuilding);

        // Definiowanie zachowań metod getUserById(userId) i getRoomById(roomId) w mockach userService i roomService
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(roomService.getRoomById(anyLong())).thenReturn(room);

        // Wywołanie testowanej metody
        boolean result = yourService.checkIfUserHasAccessToBuilding(1L, 1L);

        // Sprawdzenie czy wynik jest zgodny z oczekiwaniami
        assertFalse(result);
    }


    @Test
    public void testCheckPositionUserForManager() {
        // Tworzenie przykładowego użytkownika z pozycją MANAGER
        UserModel user = new UserModel();
        user.setPositions(Positions.MANAGER);

        // Definiowanie zachowania metody getUserById(userId) w mocku userService
        when(userService.getUserById(anyLong())).thenReturn(user);

        // Wywołanie testowanej metody
        boolean result = yourService.checkPositionUser(1L);

        // Sprawdzenie czy wynik jest zgodny z oczekiwaniami
        assertTrue(result);
    }

    @Test
    public void testCheckPositionUserForNonManager() {
        // Tworzenie przykładowego użytkownika z pozycją inna niż MANAGER
        UserModel user = new UserModel();
        user.setPositions(Positions.REGULAR); // Załóżmy, że to jest inna pozycja

        // Definiowanie zachowania metody getUserById(userId) w mocku userService
        when(userService.getUserById(anyLong())).thenReturn(user);

        // Wywołanie testowanej metody
        boolean result = yourService.checkPositionUser(1L);

        // Sprawdzenie czy wynik jest zgodny z oczekiwaniami
        assertFalse(result);
    }

    @Test
    public void testCheckAccessListWithAccess() {
        // Tworzenie przykładowego użytkownika z listą dostępu zawierającą AccessList.ZONE_A
        UserModel user = new UserModel();
        user.setAccessList(List.of(AccessList.SECURE));

        // Tworzenie przykładowego pokoju z AccessList.ZONE_A
        RoomModel room = new RoomModel();
        room.setZone(AccessList.SECURE);

        // Definiowanie zachowań metod getUserById(userId) i getRoomById(roomId) w mockach userService i roomService
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(roomService.getRoomById(anyLong())).thenReturn(room);

        // Wywołanie testowanej metody
        boolean result = yourService.checkAccessList(1L, 1L);

        // Sprawdzenie czy wynik jest zgodny z oczekiwaniami
        assertTrue(result);
    }

    @Test
    public void testCheckAccessListWithoutAccess() {
        // Tworzenie przykładowego użytkownika z listą dostępu nie zawierającą AccessList.ZONE_A
        UserModel user = new UserModel();
        user.setAccessList(List.of(AccessList.PUBLIC)); // Załóżmy, że to jest ina strefa

        // Tworzenie przykładowego pokoju z AccessList.ZONE_A
        RoomModel room = new RoomModel();
        room.setZone(AccessList.SECURE);

        // Definiowanie zachowań metod getUserById(userId) i getRoomById(roomId) w mockach userService i roomService
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(roomService.getRoomById(anyLong())).thenReturn(room);

        // Wywołanie testowanej metody
        boolean result = yourService.checkAccessList(1L, 1L);

        // Sprawdzenie czy wynik jest zgodny z oczekiwaniami
        assertFalse(result);
    }

}


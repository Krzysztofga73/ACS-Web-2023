package com.example.accesscontrolsystem.controller;

import com.example.accesscontrolsystem.model.AccessCheckResultModel;
import com.example.accesscontrolsystem.model.BuildingModel;
import com.example.accesscontrolsystem.model.RoomModel;
import com.example.accesscontrolsystem.model.UserModel;
import com.example.accesscontrolsystem.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class AcsController {
    private final BuildingService buildingService;
    private final UserService userService;
    private final RoomService roomService;
    private final AcsService acsService;
    private final AccessCheckResultService accessCheckResultService;

    @GetMapping("/acs")
    public String getAcs(Model model) { //, @RequestParam Boolean res -> trzeba było usunąć bo nie działało get maping na acs tylko za każdym razem trzeba było podać parametr
        List<BuildingModel> listBuildings = buildingService.getBuildingList();
        List<RoomModel> listRoom = roomService.getRoomList();
        List<UserModel> list = userService.getUserList();
        model.addAttribute("buildings", listBuildings);
        model.addAttribute("rooms", listRoom);
        model.addAttribute("users", list);
        return "acs/acs";
    }

    // METODY SPRAWDZAJĄCE POSZCZEGÓLNE WARUNKI DOSTĘPU (WYNIK SPRAWDZENIA NIE JEST ZAPISYWANY)
    @PostMapping("/checkBuilding")
    public String postCheckBuilding(@RequestParam Long userId, @RequestParam Long roomId, Model model) {
        List<RoomModel> listRoom = roomService.getRoomList();
        List<UserModel> list = userService.getUserList();
        model.addAttribute("rooms", listRoom);
        model.addAttribute("users", list);
        Boolean resBuilding = acsService.checkIfUserHasAccessToBuilding(userId, roomId);
        model.addAttribute("resBuilding", resBuilding);
        return "acs/acs";
    }

    @PostMapping("/checkPosition")
    public String postCheckPosition(@RequestParam Long userId, Model model) {
        List<UserModel> list = userService.getUserList();
        model.addAttribute("users", list);
        Boolean resPosition = acsService.checkPositionUser(userId);
        model.addAttribute("resPosition", resPosition);
        return "acs/acs";
    }

    @PostMapping("/checkAccessList")
    public String postCheckAccessLists(@RequestParam Long userId, @RequestParam Long roomId, Model model) {
        List<RoomModel> listRoom = roomService.getRoomList();
        List<UserModel> list = userService.getUserList();
        model.addAttribute("rooms", listRoom);
        model.addAttribute("users", list);
        Boolean resAccessList = acsService.checkAccessList(userId, roomId);
        model.addAttribute("resAccessList", resAccessList);
        return "acs/acs";
    }

    @PostMapping("/checkAccessAll")
    public String postCheckAccessAll(@RequestParam Long userId, @RequestParam Long roomId, Model model) {
        List<RoomModel> listRoom = roomService.getRoomList();
        List<UserModel> list = userService.getUserList();
        model.addAttribute("rooms", listRoom);
        model.addAttribute("users", list);
        String resAccess = acsService.checkAccess(userId, roomId);
        model.addAttribute("resAccess", resAccess);
        return "acs/acs";
    }

    // METODY DO SPRAWDZANIA DOSTĘPU DLA JEDNEGO UŻYTKOWNIKA I LISTY POMIESZCZEŃ (WYNIK ZAPISYWANY DO BAZY DANYCH)
    @GetMapping("/checkAccess")
    public String getCheckAccessForList(Model model) {
        List<RoomModel> listRoom = roomService.getRoomList();
        List<UserModel> list = userService.getUserList();
        model.addAttribute("rooms", listRoom);
        model.addAttribute("users", list);
        return "acs/check-access-list";
    }

    @PostMapping("/checkAccess")
    public String postCheckAccessForList(@RequestParam Long userId, @RequestParam List<RoomModel> rooms, Model model) {
        List<RoomModel> listRoom = roomService.getRoomList();
        List<UserModel> list = userService.getUserList();
        List<String> results = new ArrayList<>();                                       //-> lista wyników tymczasowa do wyświetlenia ich na stronie

        UserModel selectedUser = userService.getUserById(userId);                       //-> wybrany user do sprawdzenia

        model.addAttribute("rooms", listRoom);
        model.addAttribute("users", list);
        model.addAttribute("results", results);
        model.addAttribute("selectedUser", selectedUser);
        model.addAttribute("selectedRooms", rooms);

        for (RoomModel room : rooms) {
            String resAccess = acsService.checkAccess(userId, room.getId());            // -> metoda sprawdzająca dostęp do każdego z pomieszczeń
            results.add(resAccess);                                                     // -> przypisanie wyniku do listy wyświetlanej na stronie
            AccessCheckResultModel accessCheckResult = new AccessCheckResultModel();    // -> utworzenie nowej instancji wyniku dla każedej iteracji sprawdzania dostępu
            accessCheckResult.setDescription(resAccess);                                // -> ustawienie opisu
            accessCheckResultService.addAccessCheckResult(accessCheckResult);           // -> zapisanie wyniku do bazy danych

            model.addAttribute("resAccess", resAccess);
            model.addAttribute("room", room);
        }

        return "acs/check-access-list";
    }
}







package com.example.accesscontrolsystem.controller;

import com.example.accesscontrolsystem.model.BuildingModel;
import com.example.accesscontrolsystem.model.RoomModel;
import com.example.accesscontrolsystem.repository.BuildingRepository;
import com.example.accesscontrolsystem.service.BuildingService;
import com.example.accesscontrolsystem.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class RoomController {
    private final RoomService roomService;
    private final BuildingService buildingService;

    @GetMapping("/rooms")
    public String getRoomList(@RequestParam(value = "sortBy", required = false) String sortBy, Model model) {
        List<RoomModel> list = roomService.getRoomList();
        roomService.sortRooms(list, sortBy);
        model.addAttribute("rooms", list);
        model.addAttribute("count", roomService.getRoomCount(list));
        return "room/room";
    }

    @GetMapping("/addRooms")
    public String getAddRoom(Model model) {
        List<BuildingModel> buildingList = buildingService.getBuildingList();
        model.addAttribute("buildingsList", buildingList);
        model.addAttribute("addedRoom", new RoomModel());
        return "room/add-room";
    }

    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute("addedRoom") @Valid RoomModel newRoom, BindingResult result, Model model) {
        List<BuildingModel> buildingList = buildingService.getBuildingList();
        model.addAttribute("buildingsList", buildingList);

        if (result.hasErrors()) {
            return "room/add-room";
        }

        roomService.addRoom(newRoom);
        return "redirect:/rooms";
    }

    @GetMapping("editRoom/{id}")
    public String getEditRoom(@PathVariable("id") Long id, Model model) {
        List<BuildingModel> buildingList = buildingService.getBuildingList();
        model.addAttribute("buildingsList", buildingList);
        RoomModel roomToEdit = roomService.getRoomById(id);
        model.addAttribute("roomToEdit", roomToEdit);
        return "room/update-room";
    }

    @PostMapping("/editRoom/{id}")
    public String editRoom(RoomModel editRoom) {
        roomService.saveEditRoom(editRoom);
        return "redirect:/rooms";
    }

    @GetMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {
        roomService.removeRoom(id);
        return "redirect:/rooms";
    }
}

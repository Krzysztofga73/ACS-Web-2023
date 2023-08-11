package com.example.accesscontrolsystem.controller;

import com.example.accesscontrolsystem.model.BuildingModel;
import com.example.accesscontrolsystem.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;


    @GetMapping("/building")
    public String getBuildingList(@RequestParam(value = "sortBy", required = false) String sortBy, Model model) {
        List<BuildingModel> list = buildingService.getBuildingList();
        buildingService.sortBuildings(list, sortBy);
        model.addAttribute("buildings", list);
        model.addAttribute("count", buildingService.getBuildingsCount(list));
        return "building/building";
    }

    @GetMapping("/addBuildings")
    public String getAddBuilding(Model model) {
        model.addAttribute("addedBuilding", new BuildingModel());
        return "building/add-building";
    }

    @PostMapping("/addBuilding")
    public String addBuilding(BuildingModel newBuilding, BindingResult result) {
        if (result.hasErrors()) {
            return "building/add-building";
        }
        buildingService.addBuilding(newBuilding);
        return "redirect:/building";
    }

    @GetMapping("editBuilding/{id}")
    public String getEditBuilding(@PathVariable("id") Long id, Model model) {
        BuildingModel buildingModel = buildingService.getBuildingById(id);
        model.addAttribute("buildingToEdit", buildingModel);
        return "building/update-building";
    }

    @PostMapping("/editBuilding/{id}")
    public String editBuilding(BuildingModel editBuilding) {
        buildingService.saveEditBuilding(editBuilding);
        return "redirect:/building";
    }

    @GetMapping("/deleteBuilding/{id}") //dlaczego GET a nie post????
    public String removeBuilding(@PathVariable("id") Long id) {
        buildingService.removeBuilding(id);
        return "redirect:/building";
    }

}

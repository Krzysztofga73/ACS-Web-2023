package com.example.accesscontrolsystem.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuildingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(name ="name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, mappedBy = "building")
    private List<RoomModel> roomModels = new ArrayList<>();

    @ManyToMany(mappedBy = "buildingModels", cascade = CascadeType.REMOVE)
    private List<UserModel> userModels = new ArrayList<>();

//    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, mappedBy = "buildingModel")
//    private List<UserModel> userModels = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}

//    @ElementCollection

//   @Convert
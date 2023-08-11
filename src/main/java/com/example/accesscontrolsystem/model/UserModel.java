package com.example.accesscontrolsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


//    @NotBlank(message = "Email is mandatory")

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @NotNull(message = "Department is required")
    @Column(name = "department")
    private Departments department;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_building",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "building_id")
    )
    private List<BuildingModel> buildingModels = new ArrayList<>();

    @NotEmpty(message = "Access List is required")
    @ElementCollection(targetClass = AccessList.class, fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private List<AccessList> accessList;


    @NotNull(message = "Position is required")
    @Column(name = "positions")
    private Positions positions;

}

package com.example.accesscontrolsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String name;


    @NotNull(message = "Building is required")
    @ManyToOne
    @JoinColumn(name = "buildingModel_id")
    private BuildingModel building;

    @NotNull(message = "Access List is required")
    @Enumerated(value = EnumType.STRING)
    private AccessList zone;

    @ElementCollection
    @Enumerated(EnumType.STRING)

    @Column(name = "department")
    private List<Departments> departments;

    @Override
    public String toString() {
        return name;
    }


}

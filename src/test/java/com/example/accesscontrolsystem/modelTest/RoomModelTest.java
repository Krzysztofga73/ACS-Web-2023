package com.example.accesscontrolsystem.modelTest;

import com.example.accesscontrolsystem.model.AccessList;
import com.example.accesscontrolsystem.model.BuildingModel;
import com.example.accesscontrolsystem.model.RoomModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

import java.util.Set;

public class RoomModelTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidRoomModel() {
        RoomModel room = new RoomModel();
        room.setName("Conference Room");
        room.setBuilding(new BuildingModel());
        room.setZone(AccessList.PUBLIC);
        room.setDepartments(new ArrayList<>());

        Set<ConstraintViolation<RoomModel>> violations = validator.validate(room);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidRoomModel() {
        RoomModel room = new RoomModel();
        room.setBuilding(null); // Invalid: Building is required
        room.setZone(null); // Invalid: Access List is required

        Set<ConstraintViolation<RoomModel>> violations = validator.validate(room);
        assertEquals(2, violations.size());
    }
}

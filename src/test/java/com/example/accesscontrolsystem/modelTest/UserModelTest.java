package com.example.accesscontrolsystem.modelTest;

import com.example.accesscontrolsystem.model.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class UserModelTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidUserModel() {
        UserModel userModel = new UserModel();
        userModel.setName("John");
        userModel.setSurname("Doe");
        userModel.setDepartment(Departments.IT);
        userModel.setPositions(Positions.MANAGER);

        List<BuildingModel> buildings = new ArrayList<>();
        BuildingModel building = new BuildingModel();
        buildings.add(building);
        userModel.setBuildingModels(buildings);

        List<AccessList> accessList = new ArrayList<>();
        accessList.add(AccessList.SECURE);
        userModel.setAccessList(accessList);

        Set<ConstraintViolation<UserModel>> violations = validator.validate(userModel);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testInvalidUserModel() {
        UserModel userModel = new UserModel();
        userModel.setDepartment(null); // Invalid: Department is required

        List<AccessList> accessList = new ArrayList<>();
        userModel.setAccessList(accessList); // Invalid: Access List is required

        List<Positions> positions = new ArrayList<>();
        userModel.setPositions(null); // Invalid: Position is required

        Set<ConstraintViolation<UserModel>> violations = validator.validate(userModel);
        assertThat(violations).hasSize(3);

    }
}


package com.example.accesscontrolsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccessCheckResultModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private Date creationDate;

    @Column
    private String description;

    public String[] toStringRecord(){
        return new String [] {this.Id.toString(), this.description, this.creationDate.toString()};
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = new Date();
    }
}

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
@Table(name = "access_check_result")
@DiscriminatorValue("access_check") // Add discriminator value
public class AccessCheckResultModel extends RecordAbstractModel{
}

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected Long Id;
//
//
//    @Column(name = "creation_date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @CreatedDate
//    protected Date creationDate;
//
//    @Column
//    protected String description;
//
//    public String[] toStringRecord(){
//        return new String [] {this.Id.toString(), this.description, this.creationDate.toString()};
//    }
//
//    @PrePersist
//    public void prePersist() {
//        this.creationDate = new Date();
//    }
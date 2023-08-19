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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorColumn(name = "record_type", discriminatorType = DiscriminatorType.STRING) // Add a discriminator column
public class RecordAbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long Id;


    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    protected Date creationDate;

    @Column
    protected String description;

    public String[] toStringRecord() {
        return new String[]{this.Id.toString(), this.description, this.creationDate.toString()};
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = new Date();
    }
}

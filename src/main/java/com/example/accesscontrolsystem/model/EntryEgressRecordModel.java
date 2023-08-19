package com.example.accesscontrolsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "entry_egress_record")
@DiscriminatorValue("entry_egress") // Add discriminator value
public class EntryEgressRecordModel extends RecordAbstractModel{


}

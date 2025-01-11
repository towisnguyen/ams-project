package com.r23.ams.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.r23.ams.models.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="roles")
public class Role extends BaseEntity {
    //primary key is ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<AppUser> appUserList = new ArrayList<AppUser>();

}

package com.r23.ams.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.r23.ams.models.BaseEntity;
import lombok.*;

import javax.management.relation.RoleList;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "email")
    @NotEmpty
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private String birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    @NotEmpty
    private String role;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "avatar")
    private String avatar;
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Role sysRole;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<Asset> assets;

    @ManyToMany(mappedBy = "userSend")
    private List<Request> requestsSend;

    @ManyToMany(mappedBy = "userReceive")
    private List<Request> requestsReceive;

    @ManyToMany(mappedBy = "userApprove")
    private List<Request> requestsApprove;
}

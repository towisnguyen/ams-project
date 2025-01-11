package com.r23.ams.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.r23.ams.models.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class Request extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "request_type")
    private String requestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset assets;

    @ManyToMany
    @JoinTable(
            name = "request_userSend",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<AppUser> userSend;

    @ManyToMany
    @JoinTable(
            name = "request_userReceive",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<AppUser> userReceive;

    @ManyToMany
    @JoinTable(
            name = "request_userProcess",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<AppUser> userApprove;

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    private String reason;
    private Date updateAt;
}

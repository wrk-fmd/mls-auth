package at.wrk.fmd.mls.auth.entity;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Concern {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;
}

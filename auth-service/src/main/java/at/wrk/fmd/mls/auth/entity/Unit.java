package at.wrk.fmd.mls.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Unit {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    private Concern concern;

    @Column(nullable = false)
    private String name;

    public String getExternalId() {
        return String.format("%d-%d", id, concern.getId());
    }
}

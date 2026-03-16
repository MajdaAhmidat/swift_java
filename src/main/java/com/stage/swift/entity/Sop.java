package com.stage.swift.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "swift", name = "sop")
public class Sop {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle_sop", nullable = false, length = 255)
    private String libelleSop;

    /** SOP has_many VIREMENT_EMIS */
    @OneToMany(mappedBy = "sop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementEmis> virementsEmis = new ArrayList<>();

    /** SOP has_many VIREMENT_RECU */
    @OneToMany(mappedBy = "sop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementRecu> virementsRecu = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleSop() {
        return libelleSop;
    }

    public void setLibelleSop(String libelleSop) {
        this.libelleSop = libelleSop;
    }

    public List<VirementEmis> getVirementsEmis() {
        return virementsEmis;
    }

    public void setVirementsEmis(List<VirementEmis> virementsEmis) {
        this.virementsEmis = virementsEmis;
    }

    public List<VirementRecu> getVirementsRecu() {
        return virementsRecu;
    }

    public void setVirementsRecu(List<VirementRecu> virementsRecu) {
        this.virementsRecu = virementsRecu;
    }
}

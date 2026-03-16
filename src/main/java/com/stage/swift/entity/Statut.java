package com.stage.swift.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "swift", name = "statut")
public class Statut {

    @Id
    @Column(name = "id_statut")
    private Long idStatut;

    @Column(name = "code_statut", nullable = false, unique = true, length = 50)
    private String codeStatut;

    @Column(name = "libelle_statut", nullable = false, length = 255)
    private String libelleStatut;

    /** STATUT: VIREMENT_EMIS has_one STATUT => Statut has_many VIREMENT_EMIS */
    @OneToMany(mappedBy = "statut", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementEmis> virementsEmis = new ArrayList<>();

    /** STATUT: VIREMENT_RECU has_one STATUT => Statut has_many VIREMENT_RECU */
    @OneToMany(mappedBy = "statut", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementRecu> virementsRecu = new ArrayList<>();

    public Long getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(Long idStatut) {
        this.idStatut = idStatut;
    }

    public String getCodeStatut() {
        return codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public String getLibelleStatut() {
        return libelleStatut;
    }

    public void setLibelleStatut(String libelleStatut) {
        this.libelleStatut = libelleStatut;
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

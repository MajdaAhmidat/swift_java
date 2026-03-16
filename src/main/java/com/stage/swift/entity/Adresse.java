package com.stage.swift.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "swift", name = "adresse")
public class Adresse {

    @Id
    @Column(name = "id_adresse")
    private Long idAdresse;

    @Column(name = "ligne1", length = 255)
    private String ligne1;

    /** ADRESSE has_many VIREMENT_EMIS */
    @OneToMany(mappedBy = "adresse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementEmis> virementsEmis = new ArrayList<>();

    /** ADRESSE has_many VIREMENT_RECU */
    @OneToMany(mappedBy = "adresse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementRecu> virementsRecu = new ArrayList<>();

    @Column(name = "ligne2", length = 255)
    private String ligne2;

    @Column(name = "ville", length = 150)
    private String ville;

    @Column(name = "code_postal", length = 20)
    private String codePostal;

    @Column(name = "pays", length = 100)
    private String pays;

    /** Type d'adresse : 1 = DBTR (débiteur), 2 = CDTR (créditeur). FK vers swift.type_adresse */
    @Column(name = "id_type_adresse", nullable = false)
    private Long idTypeAdresse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_adresse", insertable = false, updatable = false)
    private TypeAdresse typeAdresse;

    public Long getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Long idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getLigne1() {
        return ligne1;
    }

    public void setLigne1(String ligne1) {
        this.ligne1 = ligne1;
    }

    public String getLigne2() {
        return ligne2;
    }

    public void setLigne2(String ligne2) {
        this.ligne2 = ligne2;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Long getIdTypeAdresse() {
        return idTypeAdresse;
    }

    public void setIdTypeAdresse(Long idTypeAdresse) {
        this.idTypeAdresse = idTypeAdresse;
    }

    public TypeAdresse getTypeAdresse() {
        return typeAdresse;
    }

    public void setTypeAdresse(TypeAdresse typeAdresse) {
        this.typeAdresse = typeAdresse;
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

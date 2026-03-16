package com.stage.swift.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "swift", name = "bic")
public class Bic {

    @Id
    @Column(name = "code_bic")
    private Long codeBic;

    @Column(name = "bic_ordonnateur", nullable = false, length = 255)
    private String bicOrdonnateur;

    /** VIREMENT_EMIS has_one BIC => BIC has_many VIREMENT_EMIS */
    @OneToMany(mappedBy = "bic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementEmis> virementsEmis = new ArrayList<>();

    /** VIREMENT_RECU has_one BIC => BIC has_many VIREMENT_RECU */
    @OneToMany(mappedBy = "bic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementRecu> virementsRecu = new ArrayList<>();

    @Column(name = "bic_beneficiare", nullable = false, length = 255)
    private String bicBeneficiare;

    @Column(name = "libelle_bic", nullable = false, length = 255)
    private String libelleBic;

    @Column(name = "libelle_bq", length = 11)
    private String libelleBq;

    @Column(name = "code_bq", nullable = false, length = 4)
    private String codeBq;

    @Column(name = "id_sop")
    private Long idSop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sop", referencedColumnName = "id", insertable = false, updatable = false)
    private Sop sop;

    public Long getCodeBic() {
        return codeBic;
    }

    public void setCodeBic(Long codeBic) {
        this.codeBic = codeBic;
    }

    public String getBicOrdonnateur() {
        return bicOrdonnateur;
    }

    public void setBicOrdonnateur(String bicOrdonnateur) {
        this.bicOrdonnateur = bicOrdonnateur;
    }

    public String getBicBeneficiare() {
        return bicBeneficiare;
    }

    public void setBicBeneficiare(String bicBeneficiare) {
        this.bicBeneficiare = bicBeneficiare;
    }

    public String getLibelleBic() {
        return libelleBic;
    }

    public void setLibelleBic(String libelleBic) {
        this.libelleBic = libelleBic;
    }

    public String getLibelleBq() {
        return libelleBq;
    }

    public void setLibelleBq(String libelleBq) {
        this.libelleBq = libelleBq;
    }

    public String getCodeBq() {
        return codeBq;
    }

    public void setCodeBq(String codeBq) {
        this.codeBq = codeBq;
    }

    public Long getIdSop() {
        return idSop;
    }

    public void setIdSop(Long idSop) {
        this.idSop = idSop;
    }

    public Sop getSop() {
        return sop;
    }

    public void setSop(Sop sop) {
        this.sop = sop;
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

package com.stage.swift.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "swift", name = "type_message")
public class TypeMessage {

    @Id
    @Column(name = "code_msg")
    private Long codeMsg;

    @Column(name = "libelle_msg", nullable = false, length = 255)
    private String libelleMsg;

    @Column(name = "type_msg", nullable = false, length = 255)
    private String typeMsg;

    /** TYPE_MESSAGE has_many VIREMENT_EMIS */
    @OneToMany(mappedBy = "typeMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementEmis> virementsEmis = new ArrayList<>();

    /** TYPE_MESSAGE has_many VIREMENT_RECU */
    @OneToMany(mappedBy = "typeMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementRecu> virementsRecu = new ArrayList<>();

    public Long getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(Long codeMsg) {
        this.codeMsg = codeMsg;
    }

    public String getLibelleMsg() {
        return libelleMsg;
    }

    public void setLibelleMsg(String libelleMsg) {
        this.libelleMsg = libelleMsg;
    }

    public String getTypeMsg() {
        return typeMsg;
    }

    public void setTypeMsg(String typeMsg) {
        this.typeMsg = typeMsg;
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

package com.stage.swift.entity;

import javax.persistence.*;

/**
 * Type d'adresse : débiteur (DBTR) ou créditeur (CDTR).
 * Référencé par swift.adresse.id_type_adresse.
 */
@Entity
@Table(schema = "swift", name = "type_adresse")
public class TypeAdresse {

    @Id
    @Column(name = "id_type_adresse")
    private Long idTypeAdresse;

    @Column(name = "code_type_adresse", length = 50, nullable = false)
    private String codeTypeAdresse;

    @Column(name = "libelle_type_adresse", length = 50, nullable = false)
    private String libelleTypeAdresse;

    public Long getIdTypeAdresse() {
        return idTypeAdresse;
    }

    public void setIdTypeAdresse(Long idTypeAdresse) {
        this.idTypeAdresse = idTypeAdresse;
    }

    public String getCodeTypeAdresse() {
        return codeTypeAdresse;
    }

    public void setCodeTypeAdresse(String codeTypeAdresse) {
        this.codeTypeAdresse = codeTypeAdresse;
    }

    public String getLibelleTypeAdresse() {
        return libelleTypeAdresse;
    }

    public void setLibelleTypeAdresse(String libelleTypeAdresse) {
        this.libelleTypeAdresse = libelleTypeAdresse;
    }
}

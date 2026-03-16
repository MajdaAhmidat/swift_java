package com.stage.swift.dto.entity;

import java.io.Serializable;

/**
 * DTO pour l'entité TypeMessage (référentiel des types de message SWIFT/MX).
 * <p><b>Rôle :</b> Expose code, libellé et type. Utilisé pour le CRUD type-messages et la liaison avec les virements.</p>
 */
public class TypeMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codeMsg;
    private String libelleMsg;
    private String typeMsg;

    public TypeMessageDTO() {
    }

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
}

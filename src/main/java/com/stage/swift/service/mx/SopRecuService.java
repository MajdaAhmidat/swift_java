package com.stage.swift.service.mx;


public interface SopRecuService {

    /**
     * Extrait la référence du XML, trouve le(s) VirementRecu correspondant(s) et met à jour id_statut (traité).
     */
    void processSopRecu(String xmlContent);
}

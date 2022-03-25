/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

/**
 *
 * @author PC
 */
public class Stock {
    
    private int id;
    private String libelle;
    private int prix;
    private int quantite;
    private Produit IdProduit;
    private String disponibilite;
    private Emplacement emplacement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Produit getIdProduit() {
        return IdProduit;
    }

    public void setIdProduit(Produit IdProduit) {
        this.IdProduit = IdProduit;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public Stock(int id, String libelle, int prix, int quantite, Produit IdProduit, String disponibilite) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.IdProduit = IdProduit;
        this.disponibilite = disponibilite;
    }

    public Stock(String libelle, int prix, int quantite, Produit IdProduit, String disponibilite) {
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.IdProduit = IdProduit;
        this.disponibilite = disponibilite;
    }

    public Stock() {
    }
    

    
}

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
public class Commande {
      
    private int id;
    //private User idUser;
    private Produit idProduit;
    private int nbProduits;


    
        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

  

    public Produit getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Produit idProduit) {
        this. idProduit =  idProduit;
    }

    public int getNbProduits() {
        return nbProduits;
    }

    public void setNbProduits(int nbProduits) {
        this.nbProduits = nbProduits;
    }

    
    
      //constructeurs 

        public Commande(int id, int idUser, Produit idProduit, int  nbProduits) {
        this.id = id;
        this.idProduit = idProduit;
        this.nbProduits = nbProduits;
    }
        
        public Commande() {
    }

    public String getid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        













}

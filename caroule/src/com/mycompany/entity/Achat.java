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
public class Achat {
            
    // declaration
    
    private int id;
    private String date;
    private String nomClient;
    private int numeroClient;
    //private User idUser;
    //private Produit idProduit;
   
  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this. nomClient =  nomClient;
    }

    public int getNumeroClient() {
        return numeroClient;
    }

    public void setNumeroClient(int numeroClient) {
        this.numeroClient = numeroClient;
    }

/*
    public Produit getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Produit idProduit) {
        this.idProduit = idProduit;
    }
    */

      //constructeurs 

        public Achat(int id, String date, String nomClient, int  numeroClient) {
        this.id = id;
        this.date = date;
        this.nomClient = nomClient;
        this.numeroClient = numeroClient;
        //this.idUser = idUser;
        //this.idProduit = idProduit;
    }
        
        public Achat() {
    }
        
    
   

    
}

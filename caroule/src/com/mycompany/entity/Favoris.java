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
public class Favoris {
    
    private int id;
    private int IdProduit;
    private int IdUser;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduit() {
        return IdProduit;
    }

    public void setIdProduit(int IdProduit) {
        this.IdProduit = IdProduit;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int IdUser) {
        this.IdUser = IdUser;
    }

    //constructeur

    public Favoris(int id, int IdProduit, int IdUser) {
        this.id = id;
        this.IdProduit = IdProduit;
        this.IdUser = IdUser;
    }

    public Favoris(int id, int IdProduit) {
        this.id = id;
        this.IdProduit = IdProduit;
    }

    public Favoris() {
    }
    
    
}
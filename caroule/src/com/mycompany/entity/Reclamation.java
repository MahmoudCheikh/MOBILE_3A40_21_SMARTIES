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
public class Reclamation {
    
    private int id;
    private int id_user_id;
    private String description ;
    private String date ;
    private String objet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user_id() {
        return id_user_id;
    }

    public void setId_user_id(int id_user_id) {
        this.id_user_id = id_user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public Reclamation(int id, int id_user_id, String description, String date, String objet) {
        this.id = id;
        this.id_user_id = id_user_id;
        this.description = description;
        this.date = date;
        this.objet = objet;
    }
    
    public Reclamation (){
        
    }
}
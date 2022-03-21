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
public class Emplacement {
    private int id;
    private int lieu;
    private int capacite;
    private int Stock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLieu() {
        return lieu;
    }

    public void setLieu(int lieu) {
        this.lieu = lieu;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

 //constructeurs

    public Emplacement(int id, int lieu, int capacite, int Stock) {
        this.id = id;
        this.lieu = lieu;
        this.capacite = capacite;
        this.Stock = Stock;
    }

    public Emplacement(int lieu, int capacite, int Stock) {
        this.lieu = lieu;
        this.capacite = capacite;
        this.Stock = Stock;
    }

    public Emplacement() {
    }
    
    
}

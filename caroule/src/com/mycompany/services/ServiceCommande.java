/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entity.Produit;
import com.mycompany.entity.Commande;
import com.mycompany.utils.PageWeb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map; 
import java.util.List; 
import com.mycompany.myapp.AjoutCommande;
import com.mycompany.myapp.CommandeFrom;


/**
 *
 * @author Ahmed Elmoez
 */
public class ServiceCommande {
    
    
    public ArrayList<Commande> Commande;

        public boolean resultOK;

    public static ServiceCommande instance = null;
    private ConnectionRequest req;
    
    public static ServiceCommande getInstance(){
        if(instance ==null)
            instance = new ServiceCommande();
            return instance;
    }
        
    public ServiceCommande(){
        req = new ConnectionRequest();
    }
    
    
     //affichage des Commandes :
    public ArrayList<Commande>affichageCommande(){
        ArrayList<Commande> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"commande/displayall";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapCommande = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapCommande.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Commande comm = new Commande();
                    float id = Float.parseFloat(obj.get("id").toString());
                    float idUser = Float.parseFloat(obj.get("idUser").toString());
                    float idProduit = Float.parseFloat(obj.get("idProduit").toString());
                    float nbProduits = Float.parseFloat(obj.get("nbProduits").toString());
                        
                    
//inserer les données dans une liste
                     result.add(comm);
                }                
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    public ArrayList<Commande> getAllCommandes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
                //affichage des Emplacement
    public ArrayList<Commande>AffichageCommande(){
        ArrayList<Commande> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"commande/displayall";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapCommande = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapCommande.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Commande c = new Commande();
                    
                    float id = Float.parseFloat(obj.get("id").toString());
                    c.setId((int) id);
                    float nbProduits = Float.parseFloat(obj.get("nbProduits").toString());
                    c.setId((int) id);
                c.setNbProduits((int) nbProduits);
                    
                //inserer les données dans une liste
                result.add(c);
                }                
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    
     public boolean delete(int id) {
        String url = PageWeb.BASE_URL + "commande/deletemobilecomm/"+id;
        req.setUrl(url);
        //req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
    }
   
    
    
    public void Add(Commande c ,Form previous,Resources res) {
        String url = PageWeb.BASE_URL + "commande/ajoutMobilecomm?NbProduits="+c.getNbProduits();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
    
    }




 public void Update(Commande c ,Form previous,Resources res) {
        String url = PageWeb.BASE_URL + "commande/modifiermobilecomm?NbProduits="+c.getNbProduits();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
        
        new CommandeFrom(previous,res).show();
        NetworkManager.getInstance().addToQueueAndWait(req);
    
    }






}
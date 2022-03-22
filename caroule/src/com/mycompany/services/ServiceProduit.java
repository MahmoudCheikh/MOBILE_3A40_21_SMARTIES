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
import com.mycompany.entity.Emplacement;
import com.mycompany.entity.Favoris;
import com.mycompany.entity.Produit;
import com.mycompany.entity.Stock;
import com.mycompany.myapp.AjoutProduitForm;
import com.mycompany.utils.PageWeb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map; 
import java.util.List; 

/**
 *
 * @author PC
 */
public class ServiceProduit {
    
public ArrayList<Produit> Produit;

    public static ServiceProduit instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceProduit() {
        req = new ConnectionRequest();
    }

    public static ServiceProduit getInstance() {
        if (instance == null) {
            instance = new ServiceProduit();
        }
        return instance;
    }
    
  //parseproduit
    public ArrayList<Produit> parseEquipes(String jsonText) {
        try {
            Produit = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> EquipeListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) EquipeListJson.get("root");
            for(Map<String,Object> obj :list){
           
                Produit p = new Produit();

                float id = Float.parseFloat(obj.get("id").toString());
                float prix = Float.parseFloat(obj.get("prix").toString());
                p.setId((int) id);

                p.setLibelle(obj.get("libelle").toString());
                p.setDescription(obj.get("description").toString());
                p.setType(obj.get("type").toString());
                p.setPrix(prix);
               

              //  h.setDateCreation((Date) Date.parseDate(obj.get("DateCreation").toString()));
               

                Produit.add(p);
            }

        } catch (IOException ex) {

        }
        return Produit;
    }
    
    //affichage des produits :
    public ArrayList<Produit> getAllProduits() {
        
 req = new ConnectionRequest();
        String url = PageWeb.BASE_URL +"/display";
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Produit = parseEquipes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Produit;
    }
    
                //affichage des Emplacement
    public ArrayList<Emplacement>affichageEmplecement(){
        ArrayList<Emplacement> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"/displayEmplacement";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapEmplacement = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapEmplacement.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Emplacement e = new Emplacement();
                    
                    float id = Float.parseFloat(obj.get("id").toString());
                    e.setId((int) id);
                    float capacite = Float.parseFloat(obj.get("capacite").toString());
                    e.setId((int) id);

                e.setLieu(obj.get("lieu").toString());
                e.setCapacite((int) capacite);
                    
                //inserer les données dans une liste
                result.add(e);
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
    
    //afficher les détails d'un produits
    public Produit DetailProduit(int id,Produit prod){
        String url = PageWeb.BASE_URL+"/exploreProduit"+id;
        req.setUrl(url);
        
        String str = new String (req.getResponseData());
        req.addResponseListener( ( (event)-> {
            
            JSONParser jsonp =new JSONParser();
            try {
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));  
            
                prod.setLibelle(obj.get("libelle").toString());
                prod.setDescription(obj.get("description").toString());
                prod.setImage(obj.get("image").toString());
                prod.setType(obj.get("type").toString());
                prod.setPrix(Integer.parseInt(obj.get("prix").toString()));
                prod.getFavoris();
            }
            catch(IOException ex){
                System.out.println("error related to SQL : ("+ex.getMessage());
            }
            System.out.println("data === "+str);
        }));
                NetworkManager.getInstance().addToQueueAndWait(req);

                return prod;
    }
    
    
    //affichage des favoris
    
        public ArrayList<Favoris>affichageFavoris(){
        ArrayList<Favoris> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"/displayFavoris";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapFavoris= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapFavoris.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Favoris fav = new Favoris();
                    float id = Float.parseFloat(obj.get("id").toString());
                    
                //inserer les données dans une liste
                result.add(fav);
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
        
        //Ajouter un produit
 public void AjouterProduit(Produit p ) {
        String url = PageWeb.BASE_URL + "/AjouterProduit?libelle="+p.getLibelle()+"&description="+p.getDescription()+"&image="+p.getImage()+"&prix="+p.getPrix()+"&type="+p.getType();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
        
        //new AjoutProduitForm(previous).show();
        NetworkManager.getInstance().addToQueueAndWait(req);
    
    }
    

}

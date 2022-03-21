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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entity.Favoris;
import com.mycompany.entity.Produit;
import com.mycompany.entity.Stock;
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
    
    public static ServiceProduit instance = null;
    private ConnectionRequest req;
    
    public static ServiceProduit getInstance(){
        if(instance ==null)
            instance = new ServiceProduit();
            return instance;
    }
        
    public ServiceProduit(){
        req = new ConnectionRequest();
    }
    
    //affichage des produits :
    public ArrayList<Produit>affichageProduit(){
        ArrayList<Produit> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"/display";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapProduit = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapProduit.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Produit prod = new Produit();
                    float id = Float.parseFloat(obj.get("id").toString());
                    
                    String libelle = (obj.get("libelle").toString());
                    String image = (obj.get("image").toString());
                    String description = (obj.get("description").toString());
                    float prix = Float.parseFloat(obj.get("prix").toString());
                    String type = (obj.get("type").toString());
                    
                //inserer les données dans une liste
                result.add(prod);
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
    
            //affichage des favoris
    
        public ArrayList<Stock>affichageStock(){
        ArrayList<Stock> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"/displayStock";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapStock= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapStock.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Stock s = new Stock();
                    float id = Float.parseFloat(obj.get("id").toString());
                    
                //inserer les données dans une liste
                result.add(s);
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
}

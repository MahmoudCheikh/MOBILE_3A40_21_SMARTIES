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
import com.mycompany.utils.PageWeb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class ServicesFavoris {
    
  public ArrayList<Favoris> Favoris;

    public static ServicesFavoris instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServicesFavoris() {
        req = new ConnectionRequest();
    }

    public static ServicesFavoris getInstance() {
        if (instance == null) {
            instance = new ServicesFavoris();
        }
        return instance;
    } 
    
    //affichage front 
    //parseproduit
    public ArrayList<Favoris> parseEquipes(String jsonText) {
        try {
            Favoris = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> EquipeListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) EquipeListJson.get("root");
            for(Map<String,Object> obj :list){
           
                Favoris f = new Favoris();

                float id = Float.parseFloat(obj.get("id").toString());
                float idProduit = Float.parseFloat(obj.get("idProduit").toString());
                float idUser = Float.parseFloat(obj.get("idUser").toString());
                f.setId((int) id);
                f.setIdProduit((int) idProduit);
                f.setIdUser((int) idUser);
              //  h.setDateCreation((Date) Date.parseDate(obj.get("DateCreation").toString()));
               

                Favoris.add(f);
            }

        } catch (IOException ex) {

        }
        return Favoris;
    }
    
    //affichage des produits :
    public ArrayList<Favoris> getAllFavoris() {
        
 req = new ConnectionRequest();
        String url = PageWeb.BASE_URL +"displayFavoris";
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override   
            public void actionPerformed(NetworkEvent evt) {
                Favoris = parseEquipes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Favoris;
    }
    
    
    
        //affichage des favoris
    
        public ArrayList<Favoris>affichageFavoris(){
        ArrayList<Favoris> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"displayFavoris";
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
    
}

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
import com.mycompany.entity.Produit;
import com.mycompany.entity.Sujet;
import com.mycompany.utils.PageWeb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class ServiceSujet {
     public static ServiceSujet instance = null;
    private ConnectionRequest req;
    
    public static ServiceSujet getInstance(){
        if(instance ==null)
            instance = new ServiceSujet();
            return instance;
    }
        
    public ServiceSujet(){
        req = new ConnectionRequest();
    }
    
    //affichage des produits :
    public ArrayList<Produit>affichageSujet(){
        ArrayList<Produit> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"/sujet/display";
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
                    int id = Integer.parseInt(obj.get("id").toString());
                    int nbReponses = Integer.parseInt(obj.get("nbReponses").toString());
                    int nbVues = Integer.parseInt(obj.get("nbVues").toString());                    
                    String titre = (obj.get("titre").toString());
                    String contenu = (obj.get("contenu").toString());

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
    public Sujet DetailProduit(int id,Sujet sujet){
        String url = PageWeb.BASE_URL+"/display/"+id;
        req.setUrl(url);
        
        String str = new String (req.getResponseData());
        req.addResponseListener( ( (event)-> {
            
            JSONParser jsonp =new JSONParser();
            try {
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));  
            
                sujet.setContenu(obj.get("contenu").toString());
                sujet.setTitre(obj.get("titre").toString());
                sujet.setId(Integer.parseInt(obj.get("id").toString()));
                sujet.setNbReponses(Integer.parseInt(obj.get("nbReponses").toString()));
                sujet.setNbVues(Integer.parseInt(obj.get("nbVues").toString()));
                
            
            }
            catch(IOException ex){
                System.out.println("error related to SQL : ("+ex.getMessage());
            }
            System.out.println("data === "+str);
        }));
                NetworkManager.getInstance().addToQueueAndWait(req);

                return sujet;
    }
}

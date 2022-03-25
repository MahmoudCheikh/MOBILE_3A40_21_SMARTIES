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
import com.mycompany.entity.Achat;
import com.mycompany.myapp.AffichageAchat;
import com.mycompany.utils.PageWeb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map; 
import java.util.List; 
import com.mycompany.myapp.AjoutAchatForm;
import com.mycompany.myapp.AchatForm;

/**
 *
 * @author Ahmed Elmoez
 */
public class ServiceAchat {
    
    
 
    public ArrayList<Achat> Commande;

        public boolean resultOK;

    public static ServiceAchat instance = null;
    private ConnectionRequest req;
    
    public static ServiceAchat getInstance(){
        if(instance ==null)
            instance = new ServiceAchat();
            return instance;
    }
        
    public ServiceAchat(){
        req = new ConnectionRequest();
    }
    
    
     //affichage des Commandes :
    public ArrayList<Achat>affichageAchat(){
        ArrayList<Achat> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"achat/displayachats";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapAchat = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapAchat.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Achat a = new Achat();
                    float id = Float.parseFloat(obj.get("id").toString());
                    //float idUser = Float.parseFloat(obj.get("idUser").toString());
                    //float idProduit = Float.parseFloat(obj.get("idProduit").toString());
                    float numeroClient = Float.parseFloat(obj.get("numeroClient").toString());
                    
                    a.setNomClient(obj.get("NomClient").toString());
                a.setDate(obj.get("date").toString());
                        
                    
//inserer les données dans une liste
                     result.add(a);
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

    public ArrayList<Achat> getAllAchats() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
                //affichage des Emplacement
    public ArrayList<Achat>AffichageAchat(){
        ArrayList<Achat> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"achat/displayachats";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapAchat = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapAchat.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Achat a = new Achat();
                    
                    float id = Float.parseFloat(obj.get("id").toString());
                    a.setId((int) id);
                    float NumeroClient = Float.parseFloat(obj.get("Numero Client").toString());
                    a.setNumeroClient((int) NumeroClient);
                                    a.setNomClient(obj.get("Nom Client").toString());
                a.setDate(obj.get("Date").toString());

                    
                    
                //inserer les données dans une liste
                result.add(a);
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
        String url = PageWeb.BASE_URL + "achat/deletemobileachat/"+id;
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
   
    
    
 public void Add(Achat a ,Form previous,Resources res) {
        String url = PageWeb.BASE_URL + "achat/ajoutmobileachat?Id="+a.getId()+"&date="+a.getDate()+"&NomClient"+a.getNomClient();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
    
    }




 public void Update(Achat a ,Form previous,Resources res) {
        String url = PageWeb.BASE_URL + "achat/modifiermobileacha?Id="+a.getId();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
        
        new AchatForm(previous,res).show();
        NetworkManager.getInstance().addToQueueAndWait(req);
    
    }








}
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
import com.mycompany.entity.Produit;
import com.mycompany.myapp.AchatForm;
import com.mycompany.utils.PageWeb;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class ServiceAchat {
                   


    public static ServiceAchat instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceAchat() {
        req = new ConnectionRequest();
    }

    public static ServiceAchat getInstance() {
        if (instance == null) {
            instance = new ServiceAchat();
        }
        return instance;
    }
     //affichage des Emplacement
    public ArrayList<Achat>affichageAchat(){
        ArrayList<Achat> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"achat/displayachats?idUser="+4;
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
                    Achat s = new Achat();
                    
                    float id = Float.parseFloat(obj.get("id").toString());
                    s.setId((int) id);
                s.setDate(obj.get("date").toString());
                //s.setNomClient(obj.get("Nom Client").toString());
                   //float numeroclient = Float.parseFloat(obj.get("Numero Client").toString());


               // s.setNumeroClient((int) numeroclient);
                    
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
    
     public void add(Achat s ,Form previous,Resources res) {
        String url = PageWeb.BASE_URL + "achat/ajoutmobileachat?IdUser="+s.getIdUser()+"&idProduit"+s.getIdProduit();
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
        public void Update(Achat s ,Form previous,Resources res) {
        String url = PageWeb.BASE_URL + "achat/modifiermobileachat?IdProduit="+s.getIdProduit();
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
        
        public boolean delete(int id) {
        String url = PageWeb.BASE_URL + "achat/deletemobileachat"+id;
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
}

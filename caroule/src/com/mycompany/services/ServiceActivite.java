/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;


import com.mycompany.entity.Activite;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.utils.PageWeb;
import java.util.ArrayList;
import java.util.Map; 
import java.util.List; 
 
/**
 *
 * @author Lenovo
 */
public class ServiceActivite {
     public static ServiceActivite instance = null;
    private ConnectionRequest req;
    
    public static ServiceActivite getInstance(){
        if(instance ==null)
            instance = new ServiceActivite();
            return instance;
    }
        
    public ServiceActivite(){
        req = new ConnectionRequest();
    }
      //affichage des activites :
    public ArrayList<Activite>affichageActivite(){
        ArrayList<Activite> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"/afficherAct";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapActivite = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapActivite.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Activite act = new Activite();
                    float id = Float.parseFloat(obj.get("id").toString());
                    String nom = (obj.get("nom").toString());
                    String description = (obj.get("description").toString());
                    String image = (obj.get("image").toString());
                    //float idEvenement = Float.parseFloat(obj.get("idEvenement").toString());
                   
                    
                //inserer les données dans une liste
                result.add(act);
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

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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entity.Evenement;
import com.mycompany.utils.PageWeb;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map; 
import java.util.List; 
/**
 *
 * @author Lenovo
 */
public class ServiceEvenement {
    public static ServiceEvenement instance = null;
    private ConnectionRequest req;
    
    public static ServiceEvenement getInstance(){
        if(instance ==null)
            instance = new ServiceEvenement();
            return instance;
    }
        
    public ServiceEvenement(){
        req = new ConnectionRequest();
    }
     //affichage des eveneements :
    public ArrayList<Evenement>affichageEvenement(){
        ArrayList<Evenement> result = new ArrayList<>();
        
        String url = PageWeb.BASE_URL+"/afficher";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapEvenement = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));   
                    List<Map<String,Object> > listOfMaps = (List<Map<String,Object> >) mapEvenement.get("root");
                
                for(Map<String,Object> obj : listOfMaps) {
                    Evenement event = new Evenement();
                    float id = Float.parseFloat(obj.get("id").toString());
                    
                    String nom = (obj.get("nom").toString());
                    String lieu = (obj.get("lieu").toString());
                    String description = (obj.get("description").toString());
                    float nb_participants = Float.parseFloat(obj.get("nb_participants").toString());
                    float nb_places = Float.parseFloat(obj.get("nb_places").toString());
                    String type = (obj.get("type").toString());
                    String activite = (obj.get("activite").toString());
                   
                    //afficher date 
String DateConverter = obj.get("dateD").toString().substring(obj.get("dateD").toString().indexOf("timestamp") + 10, obj.get("obj").toString().lastIndexOf("}"));
 String DateConverter1 = obj.get("dateF").toString().substring(obj.get("dateF").toString().indexOf("timestamp") + 10, obj.get("obj").toString().lastIndexOf("}"));
 Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
 Date currentTime1 = new Date(Double.valueOf(DateConverter1).longValue() * 1000);
 SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
 String dateString = formatter.format(currentTime);
event.setDateD(dateString);
String dateString1 = formatter.format(currentTime1);
event.setDateF(dateString1);           
                //inserer les données dans une liste
                result.add(event);
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

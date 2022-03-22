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
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entity.Activite;
import com.mycompany.entity.Evenement;
import com.mycompany.myapp.AjoutEvenementForm;
import com.mycompany.myapp.EvenementForm;
import com.mycompany.utils.PageWeb;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map; 
import java.util.List; 
import com.mycompany.utils.PageWeb;
import java.io.IOException;
/**
 *
 * @author Lenovo
 */
public class ServiceEvenement {
   /* public static ServiceEvenement instance = null;
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
                   // String activite = (obj.get("activite").toString());
                   
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
    }*/
     public ArrayList<Evenement> Evenements;

    public static ServiceEvenement instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceEvenement() {
        req = new ConnectionRequest();
    }

    public static ServiceEvenement getInstance() {
        if (instance == null) {
            instance = new ServiceEvenement();
        }
        return instance;
    }
public ArrayList<Evenement> parseEvenements(String jsonText) {
        try {
            Evenements = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> EvenementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) EvenementListJson.get("root");
            for(Map<String,Object> obj :list){
           
                Evenement h = new Evenement();
             /*  float nb_participants = Float.parseFloat(obj.get("nb_participants").toString());
                float nb_places = Float.parseFloat(obj.get("nb_places").toString());*/
                float id = Float.parseFloat(obj.get("id").toString());
                h.setId((int) id);
    
                h.setNom(obj.get("nom").toString());
                h.setDateD(obj.get("dateD").toString());
                h.setDateF(obj.get("dateF").toString());
                h.setLieu(obj.get("lieu").toString());
                h.setType(obj.get("type").toString());
               // h.setActivite((List<Activite>) obj.get("activite"));
               // h.setNb_participants((int) nb_participants);
              // h.setNb_places((int) nb_places);
               

             //  h.setDateD((Date) Date.parseDate(obj.get("DateD").toString()));
               

                Evenements.add(h);
            }

        } catch (IOException ex) {

        }
        return Evenements;
    } 
 public ArrayList<Evenement> getAllEvenements() {
        
 req = new ConnectionRequest();
        String url = PageWeb.BASE_URL +"evenement/afficher";
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Evenements = parseEvenements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Evenements;
    }
 public void Add(Evenement p ,Form previous,Resources res) {
        String url = PageWeb.BASE_URL + "evenement/addEvent?nom="+p.getNom()+"&dated="+p.getDateD()+"&datef="+p.getDateF()+"&lieu="+p.getLieu()+"&type="+p.getType()+"&nbparticipants="+p.getNb_participants()+"&nbplaces="+p.getNb_places();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
        
        new EvenementForm(previous,res).show();
        NetworkManager.getInstance().addToQueueAndWait(req);
    
    }
      
}

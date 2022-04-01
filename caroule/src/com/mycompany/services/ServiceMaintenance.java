/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entity.Maintenance;
import com.mycompany.utils.PageWeb;
import java.util.ArrayList;
/**
 *
 * @author PC
 */
public class ServiceMaintenance {
    public ArrayList<Maintenance> Maintenance;

    public static ServiceMaintenance instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceMaintenance() {
        req = new ConnectionRequest();
    }

    public static ServiceMaintenance getInstance() {
        if (instance == null) {
            instance = new ServiceMaintenance();
        }
        return instance;
    }
    
    
    
    
     public void Add(Maintenance p ,Form previous,Resources res ) {
        String url = PageWeb.BASE_URL + "maintenance/AjouterMaintenance?adresse="+p.getAdresse()+"&description="+p.getDescription()+"&dated="+p.getDate_debut()+"&datef="+p.getDate_fin()+"&etat="+p.getEtat()+"&relation="+1+"&reclamation_id="+10+"&id_produit_id="+1;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
            }
        });
        
        //new MaintenanceForm(previous,res).show();
        NetworkManager.getInstance().addToQueueAndWait(req);
    
    }
}

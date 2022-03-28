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
import com.mycompany.entity.Message;
import com.mycompany.utils.PageWeb;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author user
 */
public class ServiceMessage {

    public ArrayList<Message> messages;

    public static ServiceMessage instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceMessage() {
        req = new ConnectionRequest();
    }

    public static ServiceMessage getInstance() {
        if (instance == null) {
            instance = new ServiceMessage();
        }
        return instance;
    }

    public String[] splitvirgule(String str) {
        ArrayList<String> splitArray = new ArrayList<>();
        StringTokenizer arr = new StringTokenizer(str, ", ");//split by commas
        while (arr.hasMoreTokens()) {
            splitArray.add(arr.nextToken());
        }
        return splitArray.toArray(new String[splitArray.size()]);
    }
    public String[] splitegal(String str) {
        ArrayList<String> splitArray = new ArrayList<>();
        StringTokenizer arr = new StringTokenizer(str, "=");//split by commas
        while (arr.hasMoreTokens()) {
            splitArray.add(arr.nextToken());
        }
        return splitArray.toArray(new String[splitArray.size()]);
    }

    public ArrayList<Message> parseMessage(String jsonText) {
        messages = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> MessageListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) MessageListJson.get("root");
            for (Map<String, Object> obj : list) {

                Message m = new Message();
                /*  float nb_participants = Float.parseFloat(obj.get("nb_participants").toString());
                float nb_places = Float.parseFloat(obj.get("nb_places").toString());*/
                float id = Float.parseFloat(obj.get("id").toString());
                m.setId((int) id);
                System.out.println(obj);
                m.setContenu(obj.get("contenu").toString());
                m.setDate(obj.get("Date").toString());

                Map<String, String> map = new HashMap<>();
                String usertomap = obj.get("idUser").toString().substring(1, obj.get("idUser").toString().length() - 1 );
                System.out.println(usertomap);
                
                for(String keyValue : splitvirgule(usertomap)) {
        		String[] key = splitegal(keyValue);
        		map.put(key[0], key[1]);
    		}
                for (Map.Entry<String, String> entry : map.entrySet()) {
    System.out.println(entry.getKey() + "=" + entry.getValue());
}
                //java.util.List<Map<String, Object>> list2 = (java.util.List<Map<String, Object>>) IdUserMap.get("root");
                // Map<String, Object> user = list2.get(0);
                //  String userstring = user.get("id").toString();
                //  System.out.println("user id kezamlekzmaekmazezakml" + userstring);
                //float idUser = Float.parseFloat(obj.get("idUser").get("id").toString());
                // m.setId((int) idUser);
                //System.out.println("eazjlezajelkazlkj" + idUser);

                //h.setNb_participants((int) nb_participants);
                //h.setNb_places((int) nb_places);
                //  h.setDateCreation((Date) Date.parseDate(obj.get("DateCreation").toString()));
                messages.add(m);
                System.out.println(messages);
            }

        } catch (IOException ex) {

        }
        return messages;
    }

    public ArrayList<Message> getAllMessage(int sujet) {

        req = new ConnectionRequest();
        String url = PageWeb.BASE_URL + "message/display/" + sujet;
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                messages = parseMessage(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return messages;
    }
}

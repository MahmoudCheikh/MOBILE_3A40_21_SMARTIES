/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.NewsfeedForm;
import com.mycompany.utils.PageWeb;
import com.mycompany.myapp.SessionManager;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class ServiceUser {

    //singleton 
    public static ServiceUser instance = null;

    public static boolean resultOk = true;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public ServiceUser() {
        req = new ConnectionRequest();
    }

    //Signup
    public void signup(TextField username, TextField password, TextField email, TextField confirmPassword, ComboBox<String> roles, Resources res) {

        String url = PageWeb.BASE_URL + "/user/signup?username=" + username.getText().toString() + "&email=" + email.getText().toString()
                + "&password=" + password.getText().toString() + "&roles=" + roles.getSelectedItem().toString();

        req.setUrl(url);

        //Control saisi
        if (username.getText().equals(" ") && password.getText().equals(" ") && email.getText().equals(" ")) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        }

        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e) -> {

            //njib data ly7atithom fi form 
            byte[] data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 

            System.out.println("data ===>" + responseData);
        }
        );

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    //SignIn
    public void signin(String email, String password, Resources res) {

        String url = PageWeb.BASE_URL + "loginmobile?email=" + email + "&password=" + password;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";

            try {

                if (json.equals("false")) {
                    Dialog.show("Echec d'authentification", "Username ou mot de passe éronné", "OK", null);
                } else {
                    System.out.println("data ==" + json);

                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    //Session 
                    float id = Float.parseFloat(user.get("id").toString());
                    SessionManager.setId((int) id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i

                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setUserName(user.get("email").toString());
                    SessionManager.setEmail(user.get("email").toString());

                    //photo 
                    

                    if (user.size() > 0) // l9a user
                    // new ListReclamationForm(rs).show();//yemchi lel list reclamation
                    {
                        new NewsfeedForm(res).show();
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    //heki 5dmtha taw nhabtha ala description
    public String getPasswordByEmail(String email, Resources rs) {

        String url = PageWeb.BASE_URL + "/user/getPasswordByEmail?email=" + email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            json = new String(req.getResponseData()) + "";

            try {

                System.out.println("data ==" + json);

                Map<String, Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entity.Message;
import com.mycompany.services.ServiceMessage;

/**
 *
 * @author PC
 */
public class ModifierMessageForm extends Form{

    public ModifierMessageForm(Resources res, Message m) {
        super("Message", BoxLayout.y());

        TextField contenu = new TextField(m.getContenu(), "titre", 20, TextArea.TEXT_CURSOR);
        TextField date = new TextField(m.getDate(), "contenu", 20, TextArea.TEXT_CURSOR);
        TextField sujet = new TextField(String.valueOf(m.getIdSujet()), "titre", 20, TextArea.TEXT_CURSOR);
        TextField user = new TextField(String.valueOf(m.getIdUser()), "contenu", 20, TextArea.TEXT_CURSOR);

        Button modif = new Button("modifier");
        Button b8 = new Button("liste Message");
        setVisible(true);

        modif.addActionListener(l
                -> {

            if (date.getText().equals("")) {
                Dialog.show("Erreur", "Champ vide de nom ", "OK", null);

            } else if (contenu.getText().equals("")) {
                Dialog.show("Erreur", "Champ vide de Date debut ", "OK", null);
            } else if (sujet.getText().equals("")) {
                Dialog.show("Erreur", "Champ vide de lieu ", "OK", null);
            } else if (user.getText().equals("")) {
                Dialog.show("Erreur", "Champ vide de type ", "OK", null);
            } else {                
                m.setContenu(contenu.getText());
                m.setDate(date.getText());
                m.setIdSujet(Integer.valueOf(sujet.getText()));
                m.setIdUser(Integer.valueOf(user.getText()));
                
                ServiceMessage sp = new ServiceMessage();
                Form previous = null;
                sp.Update(m, previous, res);
                Dialog.show("modifier", "modifier avec succés", "OK", null);
                ConnectionRequest cnreq = new ConnectionRequest();
                cnreq.setPost(false);

                NetworkManager.getInstance().addToQueueAndWait(cnreq);
            }
        });
        this.add(contenu).add(date).add(sujet).add(user).add(modif);
        /*        Personnes p=new Personnes();
       p.setEmail(SessionManager.getEmail());*/

        add(b8);
        Form pre = null;
        b8.addActionListener(l -> new MessageForm(pre, res).show());

    }

}
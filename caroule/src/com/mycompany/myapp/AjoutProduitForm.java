/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.entity.Produit;
import com.mycompany.services.ServiceProduit;
import java.io.IOException;
import static java.lang.System.out;

/**
 *
 * @author PC
 */
public class AjoutProduitForm extends BaseForm {
    
     String file ;
  
     
          Resources theme;
                      Resources themee = UIManager.initFirstTheme("/theme");

    
     public AjoutProduitForm(Resources res)  {
                super("Publicite", BoxLayout.y());
                //System.out.println("emailllll+ "+email);
                   TextField Libelle = new TextField("", "Libelle", 20, TextArea.TEXT_CURSOR);
                
                TextField Image = new TextField("", "Image", 20, TextArea.TEXT_CURSOR);
                
                 TextField Description = new TextField("", "Description", 20, TextArea.EMAILADDR);
                 
                 TextField prix = new TextField("", "prix", 20, TextArea.TEXT_CURSOR);
                 
                 TextField type = new TextField("", "type", 20, TextArea.NUMERIC);
                 
                 
                 
                 TextField affichage = new TextField("", "Affichage", 20, TextArea.TEXT_CURSOR);
                 Label lab = new Label("0 DT");
                 affichage.addActionListener(www->{
                     
                    lab.setText(String.valueOf( Integer.valueOf(affichage.getText())*  2000           )+" DT");
                 });
                 
                  Button upload = new Button("Upload Image");
                  
                         Validator val_firstname = new Validator();
                            val_firstname.addConstraint(Libelle, new LengthConstraint(5));
                            String text_saisir_des_caracteres = "^[0-9]+$";
                            val_firstname.addConstraint(Description, new RegexConstraint(text_saisir_des_caracteres, ""));
                            // val lastname   
                            Validator val_lastname = new Validator();
                            val_lastname.addConstraint(Libelle, new LengthConstraint(8));
                            val_lastname.addConstraint(Libelle, new RegexConstraint(text_saisir_des_caracteres, ""));
                  
                  
           String text_mail="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                            
                             // val mail   
                            Validator val_mail = new Validator();
                            val_mail.addConstraint(Libelle, new LengthConstraint(8));
                            val_mail.addConstraint(Libelle, new RegexConstraint(text_mail, ""));
        Button save = new Button("Ajouter");
        
                 
               
       upload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    String fileNameInServer = "";
                    MultipartRequest cr = new MultipartRequest();
                    String filepath = Capture.capturePhoto(-1, -1);
                    cr.setUrl("http://localhost/image/uploadimage.php");
                    cr.setPost(true);
                    String mime = "image/jpeg";
                    cr.addData("file", filepath, mime);
                    //String out = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    cr.setFilename("file", out + ".jpg");//any unique name you want
                    
                    fileNameInServer += out + ".jpg";
                    System.err.println("path2 =" + fileNameInServer);
                    file=fileNameInServer;
                    InfiniteProgress prog = new InfiniteProgress();
                    Dialog dlg = prog.showInifiniteBlocking();
                    cr.setDisposeOnCompletion(dlg);
                    NetworkManager.getInstance().addToQueueAndWait(cr);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                                        
            }
        });  
           save.addActionListener(l
                                -> {

                          
                            if (Libelle.getText().equals("")) {
                                Dialog.show("Erreur", "Champ vide de Prenom ", "OK", null);

                            } else if (val_firstname.isValid()) {
                                Dialog.show("Erreur FIRSTNAME !", "il faut saisir des caracteres  !", "OK", null);

                            } else if (Description.getText().equals("")) {
                                Dialog.show("Erreur", "Champ vide de Nom ", "OK", null);

                            }else if (val_lastname.isValid()) {
                                Dialog.show("Erreur LASTNAME !", "il faut saisir des caracteres  !", "OK", null);

                            } 
                            
                            
                            else if (type.getText().equals("")) {
                                Dialog.show("Erreur", "Champ vide de email ", "OK", null);

                            } else if (!val_mail.isValid()) {
                                Dialog.show("Erreur EMAIL !", "email incorrect", "OK", null);

                            }  
                              else if (prix.getText().equals("")) {
                                Dialog.show("Erreur", "Champ vide de domaine ", "OK", null);

                            }
                            
                            else {
                                          
                                          
                                Produit p = new Produit();
                                p.setLibelle(Libelle.getText());
                                p.setImage(file);
                                p.setDescription(Description.getText());
                                 p.setPrix(prix.getText());
                                        p.setType(type.getText());
                                
                                
                                p.setPrix(Integer.valueOf(prix.getText()));
                                ServiceProduit sp = new ServiceProduit();
                                //sp.AjouterProduit(p, previous);
                                 Dialog.show("Ajout", "Ajout", "OK", null);
                                 // String url = "http://localhost/pdf/ex.php";
/*Button btn = new Button("hee");
this.add(btn);

btn.addActionListener(ll->{

});
*/
                               ConnectionRequest cnreq = new ConnectionRequest();
                cnreq.setPost(false);/*
                String data = "Nom : " + Libelle.getText() + "<br>  Prenom : " + Description.getText() + " <br>  mail :" + mail.getText() + " <br> domaine : " + domaine.getText() + " <br> lien : " + lien.getText()+ " <br> Prix : " + String.valueOf( Integer.valueOf(affichage.getText())*  2000           )+" DT"+"<br> Merci pour votre confiance &#128525;";

                cnreq.addArgument("data", data);
                cnreq.setUrl(url);
                cnreq.addResponseListener(evx
                        -> {
                    String valeur = new String(cnreq.getResponseData());
                     Dialog.show("PDF", "PDF", "OK", null);
                

                }
                );*/
                                NetworkManager.getInstance().addToQueueAndWait(cnreq);
                                 
                                 
                                 
                                 
                                 
                                 
                                 
                                                                    
                            }
           });
        
        
        
        
        
        
          this.add(Libelle).add(Description).add(prix).add(type).add(upload);
                  Produit p=new Produit();
      // p.setEmail(SessionManager.getEmail());
        
           this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
          new WalkthruForm(themee).show();
        });
        
        
        
                 
     }
}

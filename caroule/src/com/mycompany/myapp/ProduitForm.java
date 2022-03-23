/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entity.Produit;
import com.mycompany.services.ServiceProduit;


/**
 *
 * @author PC
 */
public class ProduitForm extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
//  Resources themee = UIManager.initFirstTheme("/theme_1");
    public ProduitForm(Form previous,Resources res)
    {
           super("Produits Ajoutés",BoxLayout.y());
             this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
             for (Produit p : new ServiceProduit().getAllProduits()) {

            this.add(addItem_Publicite(p));

        }
             
              this.revalidate();
            });
        });

        this.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : this.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                this.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : this.getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                  String line3 = mb.getTextLine3();
                  String line4 = mb.getTextLine4();
                    /*boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1;*/
                   mb.setHidden(false);
                    mb.setVisible(true);
                }
                this.getContentPane().animateLayout(150);
            }
        }, 6);
               this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
           new MyApplication().start();
        });
                   this.getToolbar().addCommandToOverflowMenu("Ajouter", null, ev -> {
         new AjoutProduitForm(res).show();
        });
                     /*this.getToolbar().addCommandToOverflowMenu("Stat", null, ev -> {
     new StatForm().createPieChartForm("Pubs", new ServicePublicite().findAllStat());
        });*/
    }
    
    
     public MultiButton addItem_Publicite(Produit p) {

 MultiButton m = new MultiButton();
   String url = "http://localhost/image/"+p.getImage();
   
      m.setText(p.getLibelle());
      m.setTextLine2(p.getDescription());
      m.setTextLine3(p.getType());
      m.setTextLine1(String.valueOf(p.getPrix()));
      m.setEmblem(theme.getImage(url));
         Image imge;
         EncodedImage enc;
        enc = EncodedImage.createFromImage(theme.getImage("bike3.png"), false);
        imge = URLImage.createToStorage(enc, url, url);
        m.setIcon(imge);
         m.setEmblem(theme.getImage(url));

      //m.setVisible(true);
      
/********************************************************************************************************************/      
         Button b5=new Button("supprimer event");
	setVisible(true); 
        Button b6=new Button("modifier event");
	setVisible(true);  
        //b.addActionListener(e -> new AffichageActivite(res).show());
         add(b5);
         add(b6);
        Resources res = null;
         
         b6.addActionListener(l->new ModifierProduitForm(res,p).show());
       
//Click delete icon
b5.addPointerPressedListener(l -> {
    Dialog dig = new Dialog("Suprression");
        if(dig.show("Suppression", "Vous voulez supprimeer ce produit ?","Annuler","Oui")) {
        //ProduitForm(Form previous,Resources res).show;
        dig.dispose ();

        }
        else {
        dig.dispose();
        if (ServiceProduit.getInstance().delete(p.getId())) {
        refreshTheme();
        }
}
});
/**************************************************************************************************************/
         
        m.addActionListener(e -> {

            Form f2 = new Form("Detail",BoxLayout.y());
           
         
        
            f2.add("libelle : "+p.getLibelle()).add("description : "+p.getDescription()).add("Type : "+p.getType()).add("prix : "+p.getPrix());
        String url2 = "http://localhost/image/"+p.getImage();

  Image imge2;
        EncodedImage enc2;
        enc2 = EncodedImage.createFromImage(theme.getImage("bike3.png"), false);
        imge2 = URLImage.createToStorage(enc2, url2, url2);
        f2.add(imge2);
           
      /* Personnes p=new Personnes();
       p.setEmail(SessionManager.getEmail());*/
         
     /*  Button btnretour=new Button();
      btnretour.setUIID("selectBar1");
       btnretour.addActionListener(w -> new Brainovationuser(theme,p.getEmail()).show());
           
             f2.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
            new Brainovationuser(themee,p.getEmail()).show();
        }); */
 f2.show();
        });
      
     

     
        return m;
}
}

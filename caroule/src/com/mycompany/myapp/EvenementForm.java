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
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import com.mycompany.entity.Evenement;
import com.mycompany.services.ServiceEvenement;
/**
 *
 * @author Lenovo
 */
public class EvenementForm extends Form {
    Resources theme = UIManager.initFirstTheme("/theme");
  Resources themee = UIManager.initFirstTheme("/theme_1");
    public EvenementForm(Form previous,Resources res)
    {
           super("Evenement",BoxLayout.y());
             this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
             for (Evenement c : new ServiceEvenement().getAllEvenements()) {

            this.add(addItem_Publicite(c));

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
                  
                    /*boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1;*/
                   mb.setHidden(false);
                    mb.setVisible(true);
                }
                this.getContentPane().animateLayout(150);
            }
        }, 7);
        
             
               this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
           new MyApplication().start();
        });
                   this.getToolbar().addCommandToOverflowMenu("Ajouter", null, ev -> {
         new AjoutEvenementForm(res).show();
        });
                     /*this.getToolbar().addCommandToOverflowMenu("Stat", null, ev -> {
     new StatForm().createPieChartForm("Pubs", new ServicePublicite().findAllStat());
        });*/
    }
    
     public MultiButton addItem_Publicite(Evenement c) {

 MultiButton m = new MultiButton();
 //  String url = "http://localhost/image/"+c.getImagee();
   
     m.setTextLine1(c.getNom());
      m.setTextLine2(c.getDateD());
        m.setTextLine3(c.getDateF());
       m.setTextLine4(String.valueOf(c.getNb_participants()));
      m.setText(String.valueOf(c.getNb_places()));
        m.setText(c.getLieu());
          m.setText(c.getType());
         m.setEmblem(theme.getImage("arrow.png"));
       /* Image imge;
        EncodedImage enc;
        enc = EncodedImage.createFromImage(theme.getImage("arrow.png"), false);
        imge = URLImage.createToStorage(enc, url, url);
        m.setIcon(imge);*/
      m.setVisible(true);
        m.addActionListener(e -> {

            Form f2 = new Form("Detail",BoxLayout.y());
           
         
        
            f2.add("nom : "+c.getNom()).add("date debut : "+c.getDateD()).add("date fin : "+c.getDateF()).add("type : "+c.getType()).add("lieu : "+c.getLieu()).add("nombre participants : "+c.getNb_participants()).add("nombre places : "+c.getNb_places());
        //  String url2 = "http://localhost/image/"+c.getImagee();

  /*Image imge2;
        EncodedImage enc2;
        enc2 = EncodedImage.createFromImage(theme.getImage("image.jpg"), false);
        imge2 = URLImage.createToStorage(enc2, url2, url2);
        f2.add(imge2);*/
           
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
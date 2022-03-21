/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.getCurrentForm;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
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
import com.mycompany.entity.Produit;
import com.mycompany.services.ServiceProduit;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class AffichageProduit extends BaseForm {
    Form current;
    public AffichageProduit(Resources res){
        super("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Afficher Produits");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e-> {
            
        });
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("backbike.jpg"),"","",res);
        
        //
        
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        //*****************************************
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("home", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Forum", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Produits", barGroup);
        partage.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("Evenements", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, mesListes, liste, partage, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));
        
        mesListes.addActionListener((e)->{
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInfiniteBlocking();
            AffichageProduit a = new AffichageProduit(res);
                    a.show();           
        });
        
        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        bindButtonSelection(myFavorite, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        ///Appel affichage données :***********************************************************************************************************
        
       ArrayList<Produit> list = ServiceProduit.getInstance().affichageProduit();
       
       for( Produit produit : list){
           String urlImage = "backbike.jpg";
           Image placeHolder = Image.createImage(120,90);
           EncodedImage enc = EncodedImage.createFromImage(placeHolder,false);
           URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage,URLImage.RESIZE_SCALE);
           
           addItem(produit);
           
           //ScaleImageLabel image = new ScaleImageLabel(urlim);
           //Container containerImg = new Container();
           
           //image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
           //System.out.println(produit.getLibelle());
       }        
       
       
    }
        
        //****************************************

    private void addButton(Image image, String morbi_per_tincidunt_tellus_sit_of_amet_er, boolean b, int i, int i0) {
    }

    private void updateArrowPosition(RadioButton radioButton, Label arrow) {
    }

    private void bindButtonSelection(RadioButton all, Label arrow) {
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
    int size = Math.min(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight());
    
    if(image.getHeight() < size){
        image = image.scaledHeight(size);
    }
    
    if(image.getHeight() < Display.getInstance().getDisplayHeight() /2){
        image = image.scaledHeight(Display.getInstance().getDisplayHeight() /2);
    }
       ScaleImageLabel imageScale = new ScaleImageLabel(image);
       imageScale.setUIID("Container");
       imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
       
       Label overlay = new Label("","ImageOverlay");
       
        Container page1 =
               LayeredLayout.encloseIn(imageScale,
                       overlay,
                        BorderLayout.south(BoxLayout.encloseY(
                                new SpanLabel(text, "LargerWhiteText"),
                                spacer
                        )    
                       )
               );
        swipe.addTab("",res.getImage("backbike.jpg"),page1);
    }

    private void addButton(Image img,String libelle) {
    
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        

        
            Container cnt = new Container();
             TextArea ta = new TextArea(libelle);
             
             ta.setUIID("libelle");
             ta.setEditable(false);
             cnt.add(BoxLayout.encloseX(ta));
             
             add(cnt);
        
        
    }

    private void addItem(Produit produit) {
        //ImageViewer img = null;
        //Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));

        
        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label lib = new Label(produit.getLibelle());
        Label desc = new Label(produit.getDescription());
        //Label prix = new Label(produit.getPrix().toString());
        Label type = new Label(produit.getType());

        lib.addPointerPressedListener((ActionListener) (ActionEvent evt) -> {
            Dialog.show("Produit", "libelle : " + lib.getText() + " \n Description : " + desc.getText(), "Ok", null);
        });

        C1.add(lib);
        C1.add(desc);
        C1.add(type);
        //C1.add(C2);
        C1.setLeadComponent(lib);
        C1.setLeadComponent(desc);
        C1.setLeadComponent(type);
        
        //lib.refreshTheme();
    }
    
    
    
    
}

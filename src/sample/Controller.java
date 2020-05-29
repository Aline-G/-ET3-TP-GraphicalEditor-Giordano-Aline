package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.awt.event.ActionEvent;

public class Controller {

    private Modele modele;
    @FXML
    private RadioButton ellipseRB;
    @FXML
    private RadioButton rectangleRB;
    @FXML
    private RadioButton lineRB;
    @FXML
    private ColorPicker colorP;
    @FXML
    private ToggleGroup forme;
    @FXML
    private Pane dessin;



    @FXML
    public void initialize() {

        this.modele = new Modele(this);

        ellipseRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ellipseClick(mouseEvent);
                System.out.println(modele.getElli());
            }
        });
        rectangleRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rectangleClick(mouseEvent);
                System.out.println(modele.getRec());
            }
        });
        lineRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                lineClick(mouseEvent);
                System.out.println(modele.getLine());
            }
        });
/*
        dessin.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (modele.getRec()) {
                            dessinerRec(t.getX(),t.getY());
                        }
                        System.out.println("dessin mouse pressed");
                    }
                });*/
        dessin.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (modele.getRec()) {
                    System.out.println(modele.getCouleur());
                    Rectangle rec = dessinerRec(mouseEvent.getX(),mouseEvent.getY(),modele.getCouleur());
                    dessin.getChildren().add(rec);
                }
                if (modele.getElli()){
                    Ellipse elli = dessinerElli();
                    dessin.getChildren().add(elli);
                }

            }
        });
/*
        canvas.setOnDragDetected(new EventHandler() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = canvas.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello!");
                db.setContent(content);
                event.consume();
            }
        });


        rectangleRB.isSelected();

        forme.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                System.out.println("Selected Radio Button - " + chk.getText());

            }
        });*/
    }


    private void ellipseClick(MouseEvent event){
        modele.setElli(true);
        modele.setRec(false);
        modele.setLine(false);
        modele.setSelect(false);
    }
    private void rectangleClick(MouseEvent event){
        modele.setElli(false);
        modele.setRec(true);
        modele.setLine(false);
        modele.setSelect(false);
    }
    private void lineClick(MouseEvent event){
        modele.setElli(false);
        modele.setRec(false);
        modele.setLine(true);
        modele.setSelect(false);
    }
    private void selectClick(ActionEvent event){
        modele.setElli(false);
        modele.setRec(false);
        modele.setLine(false);
        modele.setSelect(true);
    }
    private void couleurClick(MouseEvent event){
        //modele.couleur;
      //  modele.setCouleur();
    }

    private Rectangle dessinerRec(double x, double y, Color couleur){
        Rectangle rec = new Rectangle (x,y,100,140);
        rec.setStroke (Color.BLACK);
        rec.setFill(couleur);
        return rec;
    }

    private Ellipse dessinerElli() {
        Ellipse elli = new Ellipse (300, 300, 75, 40);
        elli.setStroke (Color.BLACK);
        elli.setFill(null);
        return elli;
    }
}

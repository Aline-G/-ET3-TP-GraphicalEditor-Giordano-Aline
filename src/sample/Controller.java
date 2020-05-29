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
import java.util.ArrayList;

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
        ArrayList<Rectangle> listeRec = new ArrayList();
        ArrayList<Ellipse> listeElli = new ArrayList();

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

        colorP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                couleurClick(mouseEvent);
                System.out.println(modele.getCouleur());
            }
        });

        dessin.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (modele.getRec()) {
                    Rectangle rec = dessinerRec(mouseEvent.getX(), mouseEvent.getY(), modele.getCouleur());
                    listeRec.add(rec);
                    dessin.getChildren().add(rec);
                }
                if (modele.getElli()) {
                    Ellipse elli = dessinerElli(mouseEvent.getX(), mouseEvent.getY(), modele.getCouleur());
                    listeElli.add(elli);
                    dessin.getChildren().add(elli);
                }
            }
        });


        dessin.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (modele.getRec()) {
                    resizeRec(listeRec.get(listeRec.size()-1),mouseEvent.getX(), mouseEvent.getY());
                    dessin.getChildren().add(listeRec.get(listeRec.size()-1));
                }
                if (modele.getElli()) {
                    resizeElli(listeElli.get(listeElli.size()-1),mouseEvent.getX(), mouseEvent.getY());
                    dessin.getChildren().add(listeElli.get(listeElli.size()-1));
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
*/
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
        modele.setCouleur(colorP.getValue());
    }

    private Rectangle dessinerRec(double x, double y, Color couleur){
        Rectangle rec = new Rectangle (x,y,2,2);
        rec.setStroke (Color.BLACK);
        rec.setFill(couleur);
        return rec;
    }
    private void resizeRec(Rectangle rec,double x, double y){
        rec.setHeight(x);
        rec.setWidth(y);
    }

    private Ellipse dessinerElli(double x, double y, Color couleur) {
        Ellipse elli = new Ellipse (x, y, 75, 40);
        elli.setStroke (Color.BLACK);
        elli.setFill(couleur);
        return elli;
    }
    private void resizeElli(Ellipse elli, double x, double y) {
        elli.setRadiusX(x);
        elli.setRadiusY(y);
    }


}

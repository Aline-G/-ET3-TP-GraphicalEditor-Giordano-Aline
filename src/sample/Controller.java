package sample;
import javafx.scene.effect.Shadow;
import javafx.scene.transform.Translate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
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
    private RadioButton selectRB;
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
        //liste qui prennent en mémoire les positions X et Y de la souris
        ArrayList<Double> listeX = new ArrayList<Double>();
        ArrayList<Double> listeY = new ArrayList<Double>();

        ellipseRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ellipseClick(mouseEvent);
            }
        });
        rectangleRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rectangleClick(mouseEvent);
            }
        });
        lineRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                lineClick(mouseEvent);
            }
        });
        selectRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectClick(mouseEvent);
            }
        });

        colorP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                couleurClick(mouseEvent);
            }
        });

        dessin.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Double x = mouseEvent.getX();
                Double y = mouseEvent.getY();
                if (modele.getRec()) {
                    Rectangle rec = dessinerRec(x, y);

                    rec.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(modele.getSelect()){
                                Shadow s = new Shadow();
                                rec.setEffect(s);
                                //rec.setHeight(rec.getHeight()+10);
                            }
                        }
                    });
                    listeRec.add(rec);
                    listeX.add(x);
                    listeY.add(y);
                }
                if (modele.getElli()) {
                    Ellipse elli = dessinerElli(x, y);
                    elli.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(modele.getSelect()){
                                elli.setRadiusX(elli.getRadiusX()+10);
                                elli.setRadiusY(elli.getRadiusY()+10);

                            }
                        }
                    });
                    listeElli.add(elli);
                    listeX.add(x);
                    listeY.add(y);
                }
            }
        });


        dessin.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (modele.getRec()) {
                    resizeRec(listeRec.get(listeRec.size() - 1), mouseEvent.getX() - (listeX.get(listeX.size() - 1)), mouseEvent.getY() - (listeY.get(listeY.size() - 1)));
                    listeRec.get(listeRec.size() - 1).addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(modele.getSelect()){
                                listeRec.get(listeRec.size() - 1).setTranslateX(mouseEvent.getX()-(listeX.get(listeX.size() - 1)));
                                listeRec.get(listeRec.size() - 1).setTranslateY(mouseEvent.getY()-(listeY.get(listeY.size() - 1)));
                            }
                        }
                    });
                    dessin.getChildren().add(listeRec.get(listeRec.size() - 1));
                }
                if (modele.getElli()) {
                    resizeElli(listeElli.get(listeElli.size() - 1), mouseEvent.getX() - (listeX.get(listeX.size() - 1)), mouseEvent.getY() - (listeY.get(listeY.size() - 1)));
                    dessin.getChildren().add(listeElli.get(listeElli.size() - 1));
                }

            }
        });



    }


    private void ellipseClick(MouseEvent event) {
        modele.setElli(true);
        modele.setRec(false);
        modele.setLine(false);
        modele.setSelect(false);
    }

    private void rectangleClick(MouseEvent event) {
        modele.setElli(false);
        modele.setRec(true);
        modele.setLine(false);
        modele.setSelect(false);
    }

    private void lineClick(MouseEvent event) {
        modele.setElli(false);
        modele.setRec(false);
        modele.setLine(true);
        modele.setSelect(false);
    }

    private void selectClick(MouseEvent event) {
        modele.setElli(false);
        modele.setRec(false);
        modele.setLine(false);
        modele.setSelect(true);
    }

    private void couleurClick(MouseEvent event) {
        modele.setCouleur(colorP.getValue());
    }

    private Rectangle dessinerRec(double x, double y) {
        Rectangle rec = new Rectangle(x, y, 2, 2);
        rec.setStroke(Color.BLACK);
        rec.setFill(colorP.getValue());
        return rec;
    }

    private void resizeRec(Rectangle rec, double x, double y) {
        rec.setHeight(x);
        rec.setWidth(y);
    }

    private Ellipse dessinerElli(double x, double y) {
        Ellipse elli = new Ellipse(x, y, 75, 40);
        elli.setStroke(Color.BLACK);
        elli.setFill(colorP.getValue());
        return elli;
    }

    private void resizeElli(Ellipse elli, double x, double y) {
        elli.setRadiusX(x);
        elli.setRadiusY(y);
    }


}

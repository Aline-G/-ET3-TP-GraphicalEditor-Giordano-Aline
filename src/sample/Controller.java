package sample;
import javafx.scene.effect.Reflection;
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

import java.awt.*;
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
        //on initialize le modele
        this.modele = new Modele(this);
        //on crée une liste pour récupérer l'ensemble des rectangles et des ellipses
        ArrayList<Rectangle> listeRec = new ArrayList();
        ArrayList<Ellipse> listeElli = new ArrayList();
        //liste qui prennent en mémoire les positions X et Y de la souris
        ArrayList<Double> listeX = new ArrayList<Double>();
        ArrayList<Double> listeY = new ArrayList<Double>();

        //Permet d'ajouter d'appeler la fonction qui met à jour l'état des boutons quand on clique sur le bouton ellipse
        ellipseRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ellipseClick(mouseEvent);
            }
        });

        //Permet d'ajouter d'appeler la fonction qui met à jour l'état des boutons quand on clique sur le bouton rectangle
        rectangleRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rectangleClick(mouseEvent);
            }
        });

        //Permet d'ajouter d'appeler la fonction qui met à jour l'état des boutons quand on clique sur le bouton line
        lineRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                lineClick(mouseEvent);
            }
        });

        //Permet d'ajouter d'appeler la fonction qui met à jour l'état des boutons quand on clique sur le bouton Select/move
        selectRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectClick(mouseEvent);
            }
        });

        //Permet d'ajouter d'appeler la fonction qui met à jour la valeur de la couleur dans le modele quand on clique sur le colorPicker
        colorP.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                couleurClick(mouseEvent);
            }
        });


        //Ajoute un événement quand on clique avec la souris dans le pane
        dessin.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Double x = mouseEvent.getX();
                Double y = mouseEvent.getY();
                //on vérifie quel bouton est enclenché
                if (modele.getRec()) {
                    Rectangle rec = dessinerRec(x, y); //création du rectangle
                    //permet de faire apparaitre un feedback quand un rectangle est sélectionné
                    rec.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(modele.getSelect()){
                                Reflection r = new Reflection();
                                r.setFraction(0.7);
                                rec.setEffect(r); //on ajoute de la reflexion au rectangle

                            }
                        }
                    });
                    //Tant que je maintiens le click de la souris alors le feedback reste sinon il n'est pas selectionné
                    rec.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(modele.getSelect()){
                                Reflection r = new Reflection();
                                r.setFraction(0);
                                rec.setEffect(r); //on ajoute de la reflexion égale à 0 au rectangle
                            }
                        }
                    });
                    listeRec.add(rec); //on ajoute le rectangle ainsi créé à la liste de rectangle
                    listeX.add(x); //ces valeurs vont nous permettre de dessiner de manière plus précise les rectangles
                    listeY.add(y);
                }
                if (modele.getElli()) {
                    Ellipse elli = dessinerElli(x, y); //création de l'ellipse
                    //permet de faire apparaitre un feedback quand un rectangle est sélectionné
                    elli.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(modele.getSelect()){
                                Reflection r = new Reflection();
                                r.setFraction(0.7);
                                elli.setEffect(r); //on ajoute de la reflexion à l'ellipse
                            }
                        }
                    });
                    //Tant que je maintiens le click de la souris alors le feedback reste sinon il n'est pas selectionné
                    elli.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(modele.getSelect()){
                                Reflection r = new Reflection();
                                r.setFraction(0);
                                elli.setEffect(r); //on ajoute de la reflexion égale à 0 à l'ellipse
                            }
                        }
                    });
                    listeElli.add(elli); //on ajoute l'ellipse ainsi créée à la liste d'ellipses
                    listeX.add(x);
                    listeY.add(y);
                }
            }
        });

        //Ajoute un événement quand on reste appuyer avec la souris et qu'on la bouge
        dessin.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (modele.getRec()) { //on vérifie quel bouton est enclenché ensuite on utilise la fonction qui met à la bonne taille en fonction des paramètres rentrés
                    resizeRec(listeRec.get(listeRec.size() - 1), mouseEvent.getX() - (listeX.get(listeX.size() - 1)), mouseEvent.getY() - (listeY.get(listeY.size() - 1)));
                    listeRec.get(listeRec.size() - 1).addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() { //on crée un nouvel événement pour faire bouger le rectangle ainsi créé
                        @Override
                        public void handle(MouseEvent me) {
                            if(modele.getSelect()){
                                listeRec.get(listeRec.size() - 1).setTranslateX(me.getX()-(listeX.get(listeX.size() - 1)));
                                listeRec.get(listeRec.size() - 1).setTranslateY(me.getY()-(listeY.get(listeY.size() - 1)));
                            }
                        }
                    });
                    dessin.getChildren().add(listeRec.get(listeRec.size() - 1)); // on ajoute notre forme au pane
                }
                if (modele.getElli()) {
                    resizeElli(listeElli.get(listeElli.size() - 1), mouseEvent.getX() - (listeX.get(listeX.size() - 1)), mouseEvent.getY() - (listeY.get(listeY.size() - 1)));
                    listeElli.get(listeElli.size() - 1).addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent me) {
                            if(modele.getSelect()){
                                listeElli.get(listeElli.size() - 1).setCenterX(me.getX()-(listeX.get(listeX.size() - 1)));
                                listeElli.get(listeElli.size() - 1).setCenterY(me.getY()-(listeY.get(listeY.size() - 1)));
                            }
                        }
                    });
                    dessin.getChildren().add(listeElli.get(listeElli.size() - 1));
                }

            }
        });
    }

    //fonctions qui mettent à jour les états des radios boutons
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
    //fin des fonctions qui mettent à jour les radios boutons

    //fonction qui met à jour la couleur dans le modele
    private void couleurClick(MouseEvent event) {
        modele.setCouleur(colorP.getValue());
    }
    //fonction qui crée le rectangle
    private Rectangle dessinerRec(double x, double y) {
        Rectangle rec = new Rectangle(x, y, 2, 2);
        rec.setStroke(Color.BLACK);
        rec.setFill(colorP.getValue());
        return rec;
    }
    //fonction qui modifie la taille du rectangle
    private void resizeRec(Rectangle rec, double x, double y) {
        rec.setHeight(x);
        rec.setWidth(y);
    }

    //fonction qui crée l'ellipse
    private Ellipse dessinerElli(double x, double y) {
        Ellipse elli = new Ellipse(x, y, 75, 40);
        elli.setStroke(Color.BLACK);
        elli.setFill(colorP.getValue());
        return elli;
    }

    //fonction qui modifie la taille de l'ellipse
    private void resizeElli(Ellipse elli, double x, double y) {
        elli.setRadiusX(x);
        elli.setRadiusY(y);
    }


}

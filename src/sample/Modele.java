package sample;
import javafx.scene.paint.Color;


public class Modele {
    private Controller controlleur;
    public Color couleur;
    private boolean rec = false;
    private boolean elli = false;
    private boolean line = false;
    private boolean selec = false;

    public Modele(Controller controller){
        this.controlleur = controller;
    }

    public boolean getRec(){
        return rec;
    }
    public void setRec(boolean etat){
        this.rec = etat;
    }
    public boolean getElli(){
        return elli;
    }
    public void setElli(boolean etat){
        this.elli = etat;
    }
    public boolean getLine(){
        return line;
    }
    public void setLine(boolean etat){
        this.line = etat;
    }
    public boolean getSelect(){
        return selec;
    }
    public void setSelect(boolean etat){
        this.selec = etat;
    }
    public Color getCouleur(){
        return couleur;
    }
    public void setCouleur(Color couleur){
        this.couleur = couleur;
        System.out.println(this.couleur);
    }
    public void selectionner(){
        rec = false;
        elli = false;
        line = false;
        System.out.println("ça marche");
    }


}

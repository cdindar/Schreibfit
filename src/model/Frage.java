package model;

public class Frage {
    public String typ;
    public String frage;
    public String antwort;
    public String stufe;

    public Frage(String typ, String frage, String antwort, String stufe) {
        this.typ = typ;
        this.frage = frage;
        this.antwort = antwort;
        this.stufe = stufe;
    }
}

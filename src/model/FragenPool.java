package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
public class FragenPool {

    private Frage[] fragen = new Frage[2000];
    private int anzahl = 0;

    private int sessionsLeicht = 0;
    private int sessionsMittel = 0;
    private int sessionsSchwer = 0;

    private double[] historyProzent = new double[2000];
    private int historyCount = 0;

    public int getAnzahl() {
        return anzahl;
    }

    public Frage getFrage(int index) {
        if (index < 0 || index >= anzahl) return null;
        return fragen[index];
    }

    public void addFrage(Frage f) {
        if (f == null) return;
        if (anzahl >= fragen.length) {
            Frage[] neu = new Frage[fragen.length * 2];
            for (int i = 0; i < fragen.length; i++) neu[i] = fragen[i];
            fragen = neu;
        }
        fragen[anzahl] = f;
        anzahl++;
    }

    public void updateFrage(int index, Frage f) {
        if (index < 0 || index >= anzahl) return;
        fragen[index] = f;
    }

    public void deleteFrage(int index) {
        if (index < 0 || index >= anzahl) return;
        for (int i = index; i < anzahl - 1; i++) {
            fragen[i] = fragen[i + 1];
        }
        fragen[anzahl - 1] = null;
        anzahl--;
    }

    public void clear() {
        for (int i = 0; i < anzahl; i++) fragen[i] = null;
        anzahl = 0;
    }

    public void ladeAusDatei(String pfad) {
        clear();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pfad));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) continue;
                if (line.startsWith("#")) continue;

                String[] t = line.split(";");
                if (t.length >= 4) {
                    String typ = t[0].trim();
                    String frage = t[1].trim();
                    String antwort = t[2].trim();
                    String stufe = t[3].trim();
                    addFrage(new Frage(typ, frage, antwort, stufe));
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden: " + e.getMessage());
        } finally {
            try { if (br != null) br.close(); } catch (IOException e) { }
        }
    }

    public void speichereInDatei(String pfad) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(pfad);
            for (int i = 0; i < anzahl; i++) {
                Frage f = fragen[i];
                String line = safe(f.typ) + ";" + safe(f.frage) + ";" + safe(f.antwort) + ";" + safe(f.stufe);
                fw.write(line);
                fw.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        } finally {
            try { if (fw != null) fw.close(); } catch (IOException e) { }
        }
    }

    private String safe(String s) {
        if (s == null) return "";
        return s.replace(";", ",");
    }

    public Frage[] erstelleQuizSession(int maxAnzahl, String stufeFilter) {
        int[] idx = new int[anzahl];
        int n = 0;
        for (int i = 0; i < anzahl; i++) {
            Frage f = fragen[i];
            if (f == null) continue;
            if (stufeFilter == null || stufeFilter.equals("Alle")) {
                idx[n++] = i;
            } else {
                if (f.stufe != null && f.stufe.equalsIgnoreCase(stufeFilter)) idx[n++] = i;
            }
        }

        shuffleIntArray(idx, n);

        int sessionCount = n < maxAnzahl ? n : maxAnzahl;
        Frage[] session = new Frage[sessionCount];
        for (int i = 0; i < sessionCount; i++) {
            session[i] = fragen[idx[i]];
        }

        if (stufeFilter != null) {
            if (stufeFilter.equalsIgnoreCase("Leicht")) sessionsLeicht++;
            else if (stufeFilter.equalsIgnoreCase("Mittel")) sessionsMittel++;
            else if (stufeFilter.equalsIgnoreCase("Schwer")) sessionsSchwer++;
        }

        return session;
    }

    public void registriereErgebnis(double prozent) {
        if (historyCount < historyProzent.length) {
            historyProzent[historyCount] = prozent;
            historyCount++;
        }
    }

    public String getStatistikInfo() {
        double avg = 0;
        if (historyCount > 0) {
            double sum = 0;
            for (int i = 0; i < historyCount; i++) sum += historyProzent[i];
            avg = sum / historyCount;
        }
        return String.format("Sessions: L: %d | M: %d | S: %d  —  Schnitt: %.1f%%",
                sessionsLeicht, sessionsMittel, sessionsSchwer, avg);
    }

    public int punkteFuer(Frage f) {
        if (f == null || f.stufe == null) return 1;
        if (f.stufe.equalsIgnoreCase("Leicht")) return 1;
        if (f.stufe.equalsIgnoreCase("Mittel")) return 3;
        return 5;
    }

    private void shuffleIntArray(int[] arr, int n) {
        Random rnd = new Random();
        for (int i = n - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}

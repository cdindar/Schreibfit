package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WortsalatPool {
    private WortsalatWort[] woerter = new WortsalatWort[500];
    private int anzahl = 0;

    public int getAnzahl() {
        return anzahl;
    }

    public WortsalatWort get(int index) {
        if (index < 0 || index >= anzahl) return null;
        return woerter[index];
    }

    public void clear() {
        for (int i = 0; i < anzahl; i++) woerter[i] = null;
        anzahl = 0;
    }

    public void ladeAusDatei(String pfad) {
        clear();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pfad));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) continue;
                if (line.startsWith("#")) continue;

                String[] t = line.split(";");
                if (t.length >= 2) {
                    add(new WortsalatWort(t[0].trim(), t[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Wortsalat Laden Fehler: " + e.getMessage());
        } finally {
            try { if (br != null) br.close(); } catch (IOException e) { }
        }
    }

    private void add(WortsalatWort w) {
        if (anzahl >= woerter.length) {
            WortsalatWort[] neu = new WortsalatWort[woerter.length * 2];
            for (int i = 0; i < woerter.length; i++) neu[i] = woerter[i];
            woerter = neu;
        }
        woerter[anzahl] = w;
        anzahl++;
    }

    public WortsalatWort[] erstelleSession(int maxAnzahl) {
        int n = anzahl < maxAnzahl ? anzahl : maxAnzahl;

        int[] idx = new int[anzahl];
        for (int i = 0; i < anzahl; i++) idx[i] = i;

        shuffle(idx, anzahl);

        WortsalatWort[] session = new WortsalatWort[n];
        for (int i = 0; i < n; i++) session[i] = woerter[idx[i]];
        return session;
    }

    private void shuffle(int[] arr, int n) {
        java.util.Random rnd = new java.util.Random();
        for (int i = n - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
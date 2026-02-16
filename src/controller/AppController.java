package controller;
import model.Frage;
import model.FragenPool;
import model.WortsalatPool;
import model.WortsalatWort;
import view.LoginView;
import view.MainFrame;
import view.MenuView;
import view.QuizView;
import view.SpielView;
import view.VerwaltungView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class AppController {
    private MainFrame frame;
    private FragenPool pool;

    private WortsalatPool wortsalatPool;

    private int score = 0;
    private String userName = "";

    private Frage[] quizSession = new Frage[0];
    private int quizIndex = 0;
    private int quizRichtig = 0;
    private int quizFalsch = 0;

    private WortsalatWort[] wortsalatSession = new WortsalatWort[0];
    private int wortsalatIndex = 0;
    private int wortsalatRichtig = 0;
    private int wortsalatFalsch = 0;

    private String dateiPfad = "fragen.txt";

    public AppController(MainFrame frame, FragenPool pool) {
        this.frame = frame;
        this.pool = pool;
        wortsalatPool = new WortsalatPool();
        wortsalatPool.ladeAusDatei("wortsalat.txt");
        frame.updateScore(0);
        zeigeLogin();
    }

    private void zeigeLogin() {
        final LoginView v = new LoginView();
        v.emailField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                v.passField.requestFocusInWindow();
            }
        });
        v.passField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(v);
            }
        });
        v.loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(v);
            }
        });
        frame.setView(v);
    }

    private void handleLogin(LoginView v) {
        String email = v.emailField.getText().trim();
        String pass = new String(v.passField.getPassword()).trim();
        if (email.length() == 0 || pass.length() == 0) {
            v.showError("Bitte Email und Passwort eingeben!");
            return;
        }
        if (!istTgmEmail(email)) {
            v.showError("Ungültige TGM Email-Adresse!");
            return;
        }
        userName = extractName(email);
        v.clearFields();
        zeigeHauptmenue();
    }

    private boolean istTgmEmail(String email) {
        if (email == null) return false;
        String lower = email.toLowerCase();
        if (!lower.endsWith("@student.tgm.ac.at")) return false;
        String[] parts = email.split("@");
        if (parts.length < 1) return false;
        String vorAt = parts[0];
        if (vorAt.length() < 2) return false;
        return vorAt.matches("[a-zA-Z]+");
    }

    private String extractName(String email) {
        String vorAt = email.split("@")[0];
        if (vorAt.length() < 2) return "TGM Schüler";
        String first = vorAt.substring(0, 1).toUpperCase();
        String rest = vorAt.substring(1);
        rest = rest.substring(0, 1).toUpperCase() + rest.substring(1);
        return first + "." + rest;
    }

    private void zeigeHauptmenue() {
        MenuView menu = new MenuView();
        menu.setTitel("SchreibFit Pro");

        String info;
        if (userName != null && userName.length() > 0) {
            info = "Angemeldet als: " + userName + "    |    " + pool.getStatistikInfo();
        } else {
            info = pool.getStatistikInfo();
        }
        menu.setInfo(info);

        JButton b1 = new JButton("Verwaltung");
        JButton b2 = new JButton("Quiz");
        JButton b3 = new JButton("Wortsalat-Spielmodus");
        JButton b4 = new JButton("Score zurücksetzen");
        JButton b5 = new JButton("Abmelden");

        menu.setButtons(new JButton[]{b1, b2, b3, b4, b5});
        b1.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { zeigeVerwaltung(); }
        });
        b2.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { zeigeQuizStufe(); }
        });
        b3.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { starteWortsalat(); }
        });
        b4.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                score = 0;
                frame.updateScore(0);
                zeigeHauptmenue();
            }
        });
        b5.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                userName = "";
                score = 0;
                frame.updateScore(0);
                zeigeLogin();
            }
        });
        frame.setView(menu);
    }

    private void zeigeVerwaltung() {
        final VerwaltungView v = new VerwaltungView();
        refreshListe(v);
        v.backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zeigeHauptmenue();
            }
        });
        v.neuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                v.liste.clearSelection();
                v.clearFields();
                v.setStatus("Neu: Felder ausfüllen und Speichern/Update drücken", true);
            }
        });
        v.liste.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                int idx = v.liste.getSelectedIndex();
                if (idx >= 0) {
                    Frage f = pool.getFrage(idx);
                    if (f != null) {
                        v.typBox.setSelectedItem(f.typ);
                        v.frageField.setText(f.frage);
                        v.antwortField.setText(f.antwort);
                        v.stufeBox.setSelectedItem(f.stufe);
                        v.setStatus("Eintrag geladen (Index " + idx + ")", true);
                    }
                }
            }
        });
        v.speichernBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typ = (String) v.typBox.getSelectedItem();
                String frage = v.frageField.getText().trim();
                String antwort = v.antwortField.getText().trim();
                String stufe = (String) v.stufeBox.getSelectedItem();
                if (frage.length() == 0 || antwort.length() == 0) {
                    v.setStatus("Frage/URL und Antwort dürfen nicht leer sein!", false);
                    return;
                }
                int sel = v.liste.getSelectedIndex();
                Frage neu = new Frage(typ, frage, antwort, stufe);
                if (sel >= 0) {
                    pool.updateFrage(sel, neu);
                    v.setStatus("Eintrag aktualisiert", true);
                } else {
                    pool.addFrage(neu);
                    v.setStatus("Neue Frage hinzugefügt", true);
                }
                refreshListe(v);
                v.clearFields();
                v.liste.clearSelection();
            }
        });

        v.loeschenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sel = v.liste.getSelectedIndex();
                if (sel < 0) {
                    v.setStatus("Bitte zuerst einen Eintrag auswählen!", false);
                    return;
                }
                pool.deleteFrage(sel);
                refreshListe(v);
                v.clearFields();
                v.liste.clearSelection();
                v.setStatus("Eintrag gelöscht", true);
            }
        });
        v.ladenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                // Hier war der Fehler: APPROVE_OPTION statt APPROVE_VALUE
                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    String pfad = chooser.getSelectedFile().getAbsolutePath();
                    pool.ladeAusDatei(pfad);
                    refreshListe(v);
                    v.setStatus("Datei geladen: " + chooser.getSelectedFile().getName(), true);
                }
            }
        });

        v.dateiSpeichernBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    String pfad = chooser.getSelectedFile().getAbsolutePath();
                    if (!pfad.toLowerCase().endsWith(".txt")) {
                        pfad += ".txt";
                    }
                    pool.speichereInDatei(pfad);
                    v.setStatus("Gespeichert als: " + chooser.getSelectedFile().getName(), true);
                }
            }
        });
        frame.setView(v);
    }
    private void refreshListe(VerwaltungView v) {
        v.listeModel.clear();
        for (int i = 0; i < pool.getAnzahl(); i++) {
            Frage f = pool.getFrage(i);
            v.listeModel.addElement(buildKurzText(f));
        }
    }

    private String buildKurzText(Frage f) {
        if (f == null) return "";
        String head = f.typ + " (" + f.stufe + "): ";
        String body = f.frage == null ? "" : f.frage;
        if (body.length() > 40) body = body.substring(0, 40) + "...";
        String ans = f.antwort == null ? "" : f.antwort;
        return head + body + " -> " + ans;
    }

    private void zeigeQuizStufe() {
        MenuView menu = new MenuView();
        menu.setTitel("Quiz - Stufe wählen");
        menu.setInfo("Wähle die Schwierigkeit (oder Alle)");
        JButton b1 = new JButton("Alle");
        JButton b2 = new JButton("Leicht");
        JButton b3 = new JButton("Mittel");
        JButton b4 = new JButton("Schwer");
        JButton b5 = new JButton("Zurück");
        menu.setButtons(new JButton[]{b1, b2, b3, b4, b5});
        ActionListener start = new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String stufe = ((JButton) e.getSource()).getText();
                starteQuiz(stufe);
            }
        };
        b1.addActionListener(start);
        b2.addActionListener(start);
        b3.addActionListener(start);
        b4.addActionListener(start);
        b5.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { zeigeHauptmenue(); }
        });
        frame.setView(menu);
    }

    private void starteQuiz(String stufeFilter) {
        quizSession = pool.erstelleQuizSession(10, stufeFilter);
        quizIndex = 0;
        quizRichtig = 0;
        quizFalsch = 0;
        if (quizSession.length == 0) {
            JOptionPane.showMessageDialog(frame, "Keine Fragen gefunden. Bitte Fragenpool füllen.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            zeigeHauptmenue();
            return;
        }
        zeigeQuizFrage();
    }
    private void zeigeQuizFrage() {
        final QuizView v = new QuizView();
        final Frage f = quizSession[quizIndex];
        v.fortschrittLabel.setText((quizIndex + 1) + "/" + quizSession.length);
        v.feedbackLabel.setText(" ");
        v.antwortField.setEnabled(true);
        v.pruefenBtn.setEnabled(true);
        v.antwortField.setText("");
        v.backBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { zeigeHauptmenue(); }
        });
        if (f.typ != null && f.typ.equalsIgnoreCase("IMAGE")) {
            v.frageTextLabel.setText("Schreibe das Wort zur URL:");
            v.showUrl(f.frage);
        } else {
            v.hideUrl();
            v.bildLabel.setIcon(null);
            v.bildLabel.setText("");
            v.frageTextLabel.setText(f.frage);
        }
        v.pruefenBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { quizPruefen(v, f); }
        });
        v.antwortField.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { quizPruefen(v, f); }
        });
        frame.setView(v);
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() { v.antwortField.requestFocusInWindow(); }
        });
    }

    private void quizPruefen(QuizView v, Frage f) {
        String eingabe = v.antwortField.getText().trim();
        boolean richtig = false;
        if (f.antwort != null) {
            richtig = eingabe.equalsIgnoreCase(f.antwort.trim());
        }
        if (richtig) {
            quizRichtig++;
            int p = pool.punkteFuer(f);
            score += p;
            frame.updateScore(score);
            v.feedbackLabel.setText("Richtig! +" + p + " Punkte");
            v.feedbackLabel.setForeground(new Color(0, 255, 0));
        } else {
            quizFalsch++;
            v.feedbackLabel.setText("Falsch! Richtig: " + f.antwort);
            v.feedbackLabel.setForeground(new Color(255, 0, 0));
        }
        v.pruefenBtn.setEnabled(false);
        v.antwortField.setEnabled(false);
        Timer t = new Timer(1500, new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                quizIndex++;
                if (quizIndex >= quizSession.length) quizEnde();
                else zeigeQuizFrage();
            }
        });
        t.setRepeats(false);
        t.start();
    }
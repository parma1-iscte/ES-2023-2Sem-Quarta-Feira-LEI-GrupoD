package Horario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class Main {

    public static class MainMenuBar extends JMenuBar {

        public static MainMenuBar factory() {
            JMenu menu, submenuDosConvertores, submenuDosUploader, submenuDosUploaderLocal, submenuDosUploaderRemoto;
            JMenuItem getHorarioFromCsvRemoto, getHorarioFromJsonLocal, getHorarioFromCsvLocal,
                    getHorarioFromJsonRemoto,
                    CSVParaJSON, JSONparaCSV;

            menu = new JMenu("Menu");
            submenuDosConvertores = new JMenu("Converter");
            submenuDosUploader = new JMenu("Upload");
            submenuDosUploaderLocal = new JMenu("Locais");
            submenuDosUploaderRemoto = new JMenu("Remoto");
            MainMenuBar mb = new MainMenuBar();

            getHorarioFromCsvRemoto = new JMenuItem("Upload csv remoto");
            getHorarioFromJsonLocal = new JMenuItem("Upload json local");
            getHorarioFromCsvLocal = new JMenuItem("Upload csv local");
            getHorarioFromJsonRemoto = new JMenuItem("Upload json remoto");
            CSVParaJSON = new JMenuItem("converter csv para json");
            JSONparaCSV = new JMenuItem("converter json para csv");
            menu.add(submenuDosConvertores);
            menu.add(submenuDosUploader);
            menu.add(submenuDosUploaderLocal);
            menu.add(submenuDosUploaderRemoto);

            submenuDosConvertores.add(CSVParaJSON);
            submenuDosConvertores.add(JSONparaCSV);
            submenuDosUploader.add(submenuDosUploaderLocal);
            submenuDosUploader.add(submenuDosUploaderRemoto);
            submenuDosUploaderRemoto.add(getHorarioFromJsonRemoto);
            submenuDosUploaderRemoto.add(getHorarioFromCsvRemoto);
            submenuDosUploaderLocal.add(getHorarioFromCsvLocal);
            submenuDosUploaderLocal.add(getHorarioFromJsonLocal);

            getHorarioFromCsvRemoto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame csvRemotoFrame = new JFrame("Upload de ficheiro remoto", null);
                    csvRemotoFrame.setJMenuBar(factory());
                    final JTextField tf = new JTextField();
                    JButton jb = new JButton("Donwload", null);
                    jb.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try {
                                Horario.getHorarioFromCsvRemoto(tf.getText(), null, null);
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    });
                    tf.setBounds(50, 150, 150, 20);
                    jb.setBounds(50, 200, 150, 20);
                    csvRemotoFrame.add(tf);
                    csvRemotoFrame.add(jb);

                    csvRemotoFrame.setSize(400, 400);

                    csvRemotoFrame.setLayout(null);
                    csvRemotoFrame.setVisible(true);
                }
            });

            getHorarioFromCsvLocal.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame csvLocalFrame = new JFrame("Upload de ficheiro remoto", null);
                    csvLocalFrame.setJMenuBar(factory());
                    final JTextField tf = new JTextField();
                    JButton jb = new JButton("Donwload", null);
                    jb.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try {
                                File file = new File(tf.getText());
                                Horario.getHorarioFromCsvLocal(file);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    });
                    tf.setBounds(50, 150, 150, 20);
                    jb.setBounds(50, 200, 150, 20);
                    csvLocalFrame.add(tf);
                    csvLocalFrame.add(jb);

                    csvLocalFrame.setSize(400, 400);

                    csvLocalFrame.setLayout(null);
                    csvLocalFrame.setVisible(true);
                }
            });
            getHorarioFromJsonRemoto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame jsonRemotoFrame = new JFrame("Upload de ficheiro remoto", null);
                    jsonRemotoFrame.setJMenuBar(factory());
                    final JTextField tf = new JTextField(20);
                    JButton jb = new JButton("Donwload", null);

                    jb.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try {
                                Horario.getHorarioFromJsonRemote(tf.getText(), null, null);
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    });
                    tf.setBounds(50, 150, 150, 20);
                    jb.setBounds(50, 200, 150, 20);

                    jsonRemotoFrame.add(tf);
                    jsonRemotoFrame.add(jb);

                    jsonRemotoFrame.setSize(400, 400);

                    jsonRemotoFrame.setLayout(null);
                    jsonRemotoFrame.setVisible(true);
                }
            });
            getHorarioFromJsonLocal.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame JsonLocalFrame = new JFrame("Upload de ficheiro remoto", null);
                    JsonLocalFrame.setJMenuBar(factory());
                    final JTextField tf = new JTextField();
                    JButton jb = new JButton("Donwload", null);
                    jb.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try {
                                File file = new File(tf.getText());
                                Horario.getHorarioFromJsonLocal(file);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    });
                    tf.setBounds(50, 150, 150, 20);
                    jb.setBounds(50, 200, 150, 20);
                    JsonLocalFrame.add(tf);
                    JsonLocalFrame.add(jb);

                    JsonLocalFrame.setSize(400, 400);

                    JsonLocalFrame.setLayout(null);
                    JsonLocalFrame.setVisible(true);
                }
            });
            JSONparaCSV.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            CSVParaJSON.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            mb.add(menu);  

            return mb;
        }

        public MainMenuBar() {
            super();
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Gestor de horarios");
        f.setJMenuBar(MainMenuBar.factory());

        f.setSize(400, 400);

        f.setLayout(null);
        f.setVisible(true);
    }
}

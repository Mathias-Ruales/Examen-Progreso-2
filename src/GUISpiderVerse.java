import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUISpiderVerse {
    private JTabbedPane tabbedPane1;
    private JButton botonAñadir;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtEXP;
    private JComboBox cbUniverso;
    private JComboBox cbPoderes;
    private JButton buscarButtont2;
    private JButton confirmarCambiosButton;
    private JComboBox cbOrdenarPoderes;
    private JTable tbFiltrada;
    private JButton contarButton;
    private JPanel pGeneral;
    private JButton filtrarButton;
    private JButton registrarButton;
    private JTextField txtRegCodigo;
    private JTextField txtRegNombre;
    private JComboBox cbRegUniverso;
    private JComboBox cbRegPoder;
    private JTextField txtRegExperiencia;
    private JTable tbRegistro;
    private JTextArea resultadoArea;

    ListaSimple listaHeroes = new ListaSimple();

    public GUISpiderVerse() {

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"Código", "Nombre", "Poderes Especiales", "Universo", "Nivel de Experiencia"}, 0);
                tbRegistro.setModel(model);
                try {
                    int cod = Integer.parseInt(txtRegCodigo.getText());
                    String nombre = txtRegNombre.getText();
                    String universo = cbRegUniverso.getSelectedItem().toString();
                    String poder = cbRegPoder.getSelectedItem().toString();
                    int xp = Integer.parseInt(txtRegExperiencia.getText());
                    listaHeroes.agregarHeroe(new SpiderverseHero(cod, nombre, universo, poder, xp), tbRegistro);

                    txtRegCodigo.setText("");
                    txtRegNombre.setText("");
                    txtRegExperiencia.setText("");
                    cbRegUniverso.setSelectedIndex(0);
                    cbRegPoder.setSelectedIndex(0);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo realizar el registro");
                }
                listaHeroes.actualizarTabla(tbRegistro);
            }
        });


        buscarButtont2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int cod = Integer.parseInt(txtCodigo.getText());
                    SpiderverseHero hero = listaHeroes.buscarPorCodigo(cod);
                    if (hero != null) {
                        txtNombre.setText(hero.getNombre());
                        cbUniverso.setSelectedItem(hero.getUniverso());
                        cbPoderes.setSelectedItem(hero.getPoderesEspeciales());
                        txtEXP.setText(String.valueOf(hero.getNivelExperiencia()));



                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un numero valido.");
                }
            }
        });

        confirmarCambiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int cod = Integer.parseInt(txtCodigo.getText());
                    String nombre = txtNombre.getText();
                    String universo = cbUniverso.getSelectedItem().toString();
                    String poder = cbPoderes.getSelectedItem().toString();
                    int xp = Integer.parseInt(txtEXP.getText());

                    SpiderverseHero hero = listaHeroes.buscarPorCodigo(cod);
                    if (hero != null) {
                        hero.setNombre(nombre);
                        hero.setUniverso(universo);
                        hero.setPoderesEspeciales(poder);
                        hero.setNivelExperiencia(xp);
                        JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");

                        txtCodigo.setText(" ");
                        txtNombre.setText(" ");
                        txtEXP.setText(" ");
                        cbRegUniverso.setSelectedIndex(0);
                        cbRegPoder.setSelectedIndex(0);

                        listaHeroes.actualizarTabla(tbRegistro);
                    } else {
                        JOptionPane.showMessageDialog(null, "Héroe no encontrado.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese valores válidos.");
                }
            }
        });


        filtrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"Código", "Nombre", "Poderes Especiales", "Universo", "Nivel de Experiencia"}, 0);
                tbFiltrada.setModel(model);
                String poder = cbOrdenarPoderes.getSelectedItem().toString();
                ListaSimple listaFiltrada = listaHeroes.filtrarHeroes(poder, tbFiltrada);
                listaFiltrada.ordenarPorNivelExperiencia();
                listaFiltrada.actualizarTabla(tbFiltrada);
            }
        });

        contarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] universos = {"Tierra-616", "Tierra-1610", "Tierra-12041", "Tierra-90214", "Tierra-138"};
                StringBuilder resultado = new StringBuilder();
                for (String universo : universos) {
                    int count = listaHeroes.contarHeroesUniverso(universo);
                    if (count==1){
                        resultado.append(universo).append(": ").append(count).append(" héroe\n");
                    } else {
                    resultado.append(universo).append(": ").append(count).append(" héroes\n");
                    }
                }
                resultadoArea.setText(resultado.toString());
            }
        });


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUISpiderVerse");
        frame.setContentPane(new GUISpiderVerse().pGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}

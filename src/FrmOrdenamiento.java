import java.awt.BorderLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

public class FrmOrdenamiento extends JFrame {

    private final JButton btnOrdenarBurbuja;
    private final JButton btnOrdenarRapido;
    private final JButton btnOrdenarInsercion;
    private final JToolBar tbOrdenamiento;
    private final JComboBox<String> cmbCriterio;
    private final JTextField txtTiempo;
    private final JButton btnBuscar;
    private final JTextField txtBuscar;

    private final JTable tblDocumentos;

    public FrmOrdenamiento() {
        tbOrdenamiento = new JToolBar();
        btnOrdenarBurbuja = new JButton();
        btnOrdenarInsercion = new JButton();
        btnOrdenarRapido = new JButton();
        cmbCriterio = new JComboBox<>();
        txtTiempo = new JTextField();

        btnBuscar = new JButton();
        txtBuscar = new JTextField();

        tblDocumentos = new JTable();

        setSize(600, 400);
        setTitle("Ordenamiento Documentos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnOrdenarBurbuja.setIcon(new ImageIcon(getClass().getResource("/iconos/Ordenar.png")));
        btnOrdenarBurbuja.setToolTipText("Ordenar Burbuja");
        btnOrdenarBurbuja.addActionListener(evt -> btnOrdenarBurbujaClick());
        tbOrdenamiento.add(btnOrdenarBurbuja);

        btnOrdenarRapido.setIcon(new ImageIcon(getClass().getResource("/iconos/OrdenarRapido.png")));
        btnOrdenarRapido.setToolTipText("Ordenar Rapido");
        btnOrdenarRapido.addActionListener(evt -> btnOrdenarRapidoClick());
        tbOrdenamiento.add(btnOrdenarRapido);

        btnOrdenarInsercion.setIcon(new ImageIcon(getClass().getResource("/iconos/OrdenarInsercion.png")));
        btnOrdenarInsercion.setToolTipText("Ordenar Inserción");
        btnOrdenarInsercion.addActionListener(evt -> btnOrdenarInsercionClick());
        tbOrdenamiento.add(btnOrdenarInsercion);

        cmbCriterio.setModel(new DefaultComboBoxModel<>(
                new String[]{"Nombre Completo, Tipo de Documento", "Tipo de Documento, Nombre Completo"}));
        tbOrdenamiento.add(cmbCriterio);
        tbOrdenamiento.add(txtTiempo);

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/iconos/Buscar.png")));
        btnBuscar.setToolTipText("Buscar");
        tbOrdenamiento.add(btnBuscar);
        tbOrdenamiento.add(txtBuscar);

        getContentPane().add(tbOrdenamiento, BorderLayout.NORTH);

        JScrollPane spDocumentos = new JScrollPane(tblDocumentos);
        getContentPane().add(spDocumentos, BorderLayout.CENTER);

        String nombreArchivo = System.getProperty("user.dir") + "/src/datos/Datos.csv";
        Documento.obtenerDatosDesdeArchivo(nombreArchivo);
        Documento.mostrarDatos(tblDocumentos);
    }

    private void btnOrdenarBurbujaClick() {
        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();
            Documento.ordenarBurbuja(cmbCriterio.getSelectedIndex());
            txtTiempo.setText(Util.getTextoTiempoCronometro());
            Documento.mostrarDatos(tblDocumentos);
        }
    }

    private void btnOrdenarRapidoClick() {
        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();
            Documento.ordenarRapido(0, Documento.documentos.size() - 1, cmbCriterio.getSelectedIndex());
            txtTiempo.setText(Util.getTextoTiempoCronometro());
            Documento.mostrarDatos(tblDocumentos);
        }
    }

    private void btnOrdenarInsercionClick() {
        if (cmbCriterio.getSelectedIndex() >= 0) {
            Util.iniciarCronometro();
            Documento.ordenarInsercion(cmbCriterio.getSelectedIndex());
            txtTiempo.setText(Util.getTextoTiempoCronometro());
            Documento.mostrarDatos(tblDocumentos);
        }
    }

}

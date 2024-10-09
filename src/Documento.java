import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Documento {

    private final String apellido1;
    private final String apellido2;
    private final String nombre;
    private final String documento;

    public Documento(String apellido1, String apellido2, String nombre, String documento) {
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nombre = nombre;
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return apellido1 + " " + apellido2 + " " + nombre;
    }

    // ********** Atributos y Metodos estaticos **********
    // Almacena la lista de documentos
    public static List<Documento> documentos = new ArrayList<>();
    public static String[] encabezados;

    // Metodo que obtiene los datos desde el archivo CSV
    public static void obtenerDatosDesdeArchivo(String nombreArchivo) {
        documentos.clear();
        BufferedReader br = Archivo.abrirArchivo(nombreArchivo);
        if (br != null) {
            try {
                String linea = br.readLine();
                encabezados = linea.split(";");
                linea = br.readLine();
                while (linea != null) {
                    String[] textos = linea.split(";");
                    if (textos.length >= 4) {
                        Documento d = new Documento(textos[0], textos[1], textos[2], textos[3]);
                        documentos.add(d);
                    }
                    linea = br.readLine();
                }
            } catch (IOException ex) {
                // Para depuración
                
            }
        }
    }

    // Metodo para mostrar los datos en una tabla
    public static void mostrarDatos(JTable tbl) {
        String[][] datos = null;
        if (!documentos.isEmpty()) {
            datos = new String[documentos.size()][encabezados.length];
            for (int i = 0; i < documentos.size(); i++) {
                datos[i][0] = documentos.get(i).apellido1;
                datos[i][1] = documentos.get(i).apellido2;
                datos[i][2] = documentos.get(i).nombre;
                datos[i][3] = documentos.get(i).documento;
            }
        }
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);
    }

    // metodo para intercambiar elementos
    private static void intercambiar(int origen, int destino) {
        Documento temporal = documentos.get(origen);
        documentos.set(origen, documentos.get(destino));
        documentos.set(destino, temporal);
    }

    // metodo para verificar si un documento es mayor que otro
    public static boolean esMayor(Documento d1, Documento d2, int criterio) {
        if (criterio == 0) {
            // ordenar primero por Nombre Completo y luego por Tipo de Documento
            return ((d1.getNombreCompleto().compareTo(d2.getNombreCompleto()) > 0)
                    || (d1.getNombreCompleto().equals(d2.getNombreCompleto())
                            && d1.getDocumento().compareTo(d2.getDocumento()) > 0));
        } else {
            // ordenar primero por Tipo de Documento y luego por Nombre Completo
            return ((d1.getDocumento().compareTo(d2.getDocumento()) > 0)
                    || (d1.getDocumento().equals(d2.getDocumento())
                            && d1.getNombreCompleto().compareTo(d2.getNombreCompleto()) > 0));
        }
    }

    // Método que ordena los datos según el algoritmo de la BURBUJA recursivo
    public static void ordenarBurbujaRecursivo(int n, int criterio) {
        if (n == documentos.size() - 1) {
        } else {
            for (int i = n + 1; i < documentos.size(); i++) {
                if (esMayor(documentos.get(n), documentos.get(i), criterio)) {
                    intercambiar(n, i);
                }
            }
            ordenarBurbujaRecursivo(n + 1, criterio);
        }
    }

    // Método que ordena los datos según el algoritmo de la BURBUJA
    public static void ordenarBurbuja(int criterio) {
        for (int i = 0; i < documentos.size() - 1; i++) {
            for (int j = i + 1; j < documentos.size(); j++) {
                if (esMayor(documentos.get(i), documentos.get(j), criterio)) {
                    intercambiar(i, j);
                }
            }
        }
    }

    private static int localizarPivote(int inicio, int fin, int criterio) {
        int pivote = inicio;
        Documento documentoP = documentos.get(pivote);
        for (int i = inicio + 1; i <= fin; i++) {
            if (esMayor(documentoP, documentos.get(i), criterio)) {
                intercambiar(i, pivote);
                pivote++;
            }
        }
        return pivote;
    }

    public static void ordenarRapido(int inicio, int fin, int criterio) {
        if (inicio >= fin) {
            return;
        }
        int pivote = localizarPivote(inicio, fin, criterio);
        ordenarRapido(inicio, pivote - 1, criterio);
        ordenarRapido(pivote + 1, fin, criterio);
    }

    // Método para ordenar por inserción
    public static void ordenarInsercion(int criterio) {
        for (int i = 1; i < documentos.size(); i++) {
            Documento key = documentos.get(i);
            int j = i - 1;

            while (j >= 0 && esMayor(documentos.get(j), key, criterio)) {
                documentos.set(j + 1, documentos.get(j));
                j = j - 1;
            }
            documentos.set(j + 1, key);
        }
    }

    // Método para ordenar por selección
    public static void ordenarSeleccion(int criterio) {
        for (int i = 0; i < documentos.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < documentos.size(); j++) {
                if (esMayor(documentos.get(minIndex), documentos.get(j), criterio)) {
                    minIndex = j;
                }
            }
            intercambiar(i, minIndex);
        }
    }

    // Método para ordenar por mezcla (Merge Sort)
    public static void ordenarMezcla(int criterio) {
        documentos = mergeSort(documentos, criterio);
    }

    private static List<Documento> mergeSort(List<Documento> lista, int criterio) {
        if (lista.size() <= 1) {
            return lista;
        }

        int mid = lista.size() / 2;
        List<Documento> left = mergeSort(lista.subList(0, mid), criterio);
        List<Documento> right = mergeSort(lista.subList(mid, lista.size()), criterio);

        return merge(left, right, criterio);
    }

    private static List<Documento> merge(List<Documento> left, List<Documento> right, int criterio) {
        List<Documento> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (esMayor(left.get(i), right.get(j), criterio)) {
                result.add(right.get(j));
                j++;
            } else {
                result.add(left.get(i));
                i++;
            }
        }

        // Agregar elementos restantes
        result.addAll(left.subList(i, left.size()));
        result.addAll(right.subList(j, right.size()));

        return result;
    }
}

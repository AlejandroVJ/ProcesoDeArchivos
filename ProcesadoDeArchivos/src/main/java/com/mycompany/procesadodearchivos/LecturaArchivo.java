package com.mycompany.procesadodearchivos;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Práctica tema 2 de Acceso a datos
 *
 * @author USUARIO
 */
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USUARIO
 */
public class LecturaArchivo extends Component {
    //Lo primero que he hecho es importar la librería apache commons en el pom.xml

    public static void main(String[] args) {
        /*
        Crearemos la variable archivo donde almacenaremos el contenido de nuestro txt
        Le decimos al usuario que abra el archivo en pantalla        
         */

        String nombre;
        String archivo = "";
        nombre = JOptionPane.showInputDialog("Indique que archivo quiere abrir:");

        /*
        Creamos el BufferedReader y elFileReader para leer el txt
        Se llena de lineas el archivo vacío en cada iteración
        Hacemos un try y catch
         */
        try ( BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            String lineas;
            while ((lineas = br.readLine()) != null) {
                archivo += lineas + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Aquí crearemos la impresión por pantalla del txt
        Metemos al archivo en un arreglo formando un arreglo de palabras
         */
        System.out.println(archivo);
        archivo = archivo.replaceAll("[\\.\\,\\(\\)]", "");
        String[] Palabras = archivo.split(" ");

        /*
        Creamos el diccionario
        Y contabilizamos las palabras mayores a 2 caracteres
         */
        HashMap<String, Integer> histograma = new HashMap<>();

        for (String p : Palabras) {
            if (histograma.containsKey(p) && (p.length() > 2)) {
                histograma.put(p, histograma.get(p) + 1);
            } else if (p.length() > 2) {
                histograma.put(p, 1);
            }
        }

        /*
        Interacción del diccionario para que imprima los resultados por pantalla
        Y un try y catch para la creación del CSV
         */
        for (Map.Entry<String, Integer> entry : histograma.entrySet()) {
            System.out.printf("En el archivo, '%s' nos aparece %d\n", entry.getKey(), entry.getValue());
        }

        try {
            crearCSV(histograma, nombre);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    En este método crearemos el CSV, definiendo la cabecera y las líneas
     */
    public static void crearCSV(HashMap<String, Integer> map, String nombre) throws IOException {
        String[] HEADERS = {"palabra", "numero"};
        FileWriter out = new FileWriter(nombre + "_histograma.csv");
        try ( CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            map.forEach((palabra, numero) -> {
                /**
                 * La impresión de la cabecera se rodea de un try/catch
                 */
                try {
                    printer.printRecord(palabra, numero);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

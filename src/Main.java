import javax.swing.plaf.synth.SynthTextAreaUI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        List<String> inputS = new ArrayList<String>();
        HashMap<String, String> estadosRelaciones = new HashMap<>();
        HashMap<String, String> resultado;
        LinkedList<String> alpha = new LinkedList<>();

        //Lectura del archivo
        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                String[] strTemp = sb.toString().split("\n");
                inputS = Arrays.asList(strTemp);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        //Termina lectura del archivo

        //Guardar el alfabeto
        Iterator iter1 = inputS.listIterator(1);

        //Llenado del hashmap
        if (iter1.hasNext()) {
            String[] strTemp = iter1.next().toString().split(",");
            for (String a : strTemp) {
                alpha.add(a);
            }
        }

        //HashMap Relaciones entre estados
        Iterator iter = inputS.listIterator(4);

        //Llenado del hashmap
        while (iter.hasNext()) {
            String[] strTemp = iter.next().toString().split("=>");
            estadosRelaciones.put(strTemp[0], strTemp[1]);
        }

        //Creacion del objeto que contruye el dfa
        DfaBuilder builder = new DfaBuilder();

        resultado = builder.builderDfa(estadosRelaciones, alpha);

        System.out.println(resultado);

        //System.out.println(resultado);
    }
}


import javax.swing.plaf.synth.SynthTextAreaUI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        List<String> inputS = new ArrayList<String>();
        HashMap<String, String> estadosRelaciones = new HashMap<String, String>();
        HashMap<String,String> resultado = new HashMap<>();
        LinkedList<String> alphabet = new LinkedList<>();

        DfaBuilder builder = new DfaBuilder();

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

        Iterator iter1 = inputS.listIterator(1);

        //Llenado del hashmap alfabeto
        if (iter1.hasNext()) {
            String[] strTemp = iter1.next().toString().split(",");
            for (int i = 0; i < strTemp.length ; i++) {
                alphabet.add(strTemp[i]);
            }
        }

        //HashMap Relaciones entre estados
        Iterator iter = inputS.listIterator(4);

        //Llenado del hashmap
        while (iter.hasNext()) {
            String[] strTemp = iter.next().toString().split("=>");
            estadosRelaciones.put(strTemp[0],strTemp[1]);
        }

        builder.builderDfa(estadosRelaciones, alphabet);

        //System.out.println(resultado);
    }
}


import javax.swing.plaf.synth.SynthTextAreaUI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        List<String> inputS = new ArrayList<String>();
        HashMap<String, String> estadosRelaciones = new HashMap<>();
        HashMap<String, String> resultado;
        LinkedList<String> alpha = new LinkedList<>();
        LinkedList<String> eFInal = new LinkedList<>();
        LinkedList<String> resFinal;
        //Lectura del archivo
        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {
            // Lectura linea por linea
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                String[] strTemp = sb.toString().split("\n");
                inputS = Arrays.asList(strTemp);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        //Guardar el alfabeto
        Iterator iterAlpha = inputS.listIterator(1);
        
        if (iterAlpha.hasNext()) {
            String[] strTemp = iterAlpha.next().toString().split(",");
            for (String a : strTemp) {
                alpha.add(a);
            }
        }
        //Guardar los estados finales
        Iterator iterFinal = inputS.listIterator(3);
        
        if (iterFinal.hasNext()) {
            String[] strTemp = iterFinal.next().toString().split(",");
            for (String a : strTemp) {
                eFInal.add(a);
            }
        }
        //HashMap Relaciones entre estados
        Iterator iter = inputS.listIterator(4);
        
        while (iter.hasNext()) {
            String[] strTemp = iter.next().toString().split("=>");
            estadosRelaciones.put(strTemp[0], strTemp[1]);
        }
        //Creacion del objeto que contruye el dfa
        DfaBuilder builder = new DfaBuilder();

        resultado = builder.builderDfa(estadosRelaciones, alpha);

        resFinal = finalFinder(eFInal, resultado);


        try {
            writeFile(resultado, resFinal, alpha);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<String> finalFinder (LinkedList<String> firstFinal, HashMap<String, String> automataDFA) {
        LinkedList<String> resFinal = new LinkedList<>();
        for (String a : firstFinal) {
            for(Map.Entry<String, String> entry : automataDFA.entrySet() ) {
                String key = entry.getKey();
                String value = entry.getValue();
                String[] valueSplit = entry.getValue().split(",");
                for (String c : valueSplit) {
                    if (a.equals(c)) {
                        if (!resFinal.contains(value) ) {
                            resFinal.add(key.substring(0, key.length() - 2));
                        }
                    }
                }
            }
        }
        return resFinal;
    }

    public static void writeFile(HashMap<String, String> hashFinal, LinkedList<String> eFinal, LinkedList<String> alpha) throws IOException {
        File fout = new File("DFA-Resultado.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        StringBuilder sb = new StringBuilder();

        //Escribir a un archivo el Alfabeto
        bw.write("NDFA to DFA result");
        bw.newLine();
        bw.write("Alphabet:");
        for (int i = 0; i < alpha.size(); i++) {
            if (i < alpha.size() - 1) {
                sb.append(alpha.get(i)).append(",");
            } else {
                sb.append(alpha.get(i));
            }
        }
        bw.write(sb.toString());
        bw.newLine();
        //Escribir a un archivo los estados iniciales
        bw.write("Initial State: q0");
        bw.newLine();
        //Escribir a un archivo los estados finales
        bw.write("Final States:");
        bw.newLine();
        for (int i = 0; i < eFinal.size(); i++) {
            bw.write( "{"+eFinal.get(i)+"}" );
            bw.newLine();
        }
        bw.write("States transition of the DFA:");
        bw.newLine();
        for(Map.Entry<String, String> entry : hashFinal.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!key.equals("") || !value.equals("")) {
                sb.setLength(0);
                String resTemp = sb.append(key).append("=>").append(value).toString();
                bw.write(resTemp);
                bw.newLine();
            }
        }

        bw.close();
    }
}


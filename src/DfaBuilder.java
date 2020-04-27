import javax.print.DocFlavor;
import java.util.*;

public class DfaBuilder {
    static HashMap<String, String> res = new HashMap<>();

    public static HashMap<String, String> builderDfa(HashMap<String, String> original) {
        HashMap<String, String> resLocalA = new HashMap<>();
        HashMap<String, String> resLocalB = new HashMap<>();
        HashMap<String, String> resLocalEsteEsElBueno = new HashMap<>();

        for ( Map.Entry<String, String> entry : original.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("q0,a")) {
                resLocalA.put(key, value);
                res.put(key, value);
            }
            if (key.equals("q0,b")) {
                resLocalB.put(key, value);
                res.put(key, value);
            }
        };

        resLocalA = searchRelation(resLocalA, original);
        resLocalB = searchRelation(resLocalB, original);

        resLocalEsteEsElBueno.putAll(resLocalA);
        resLocalEsteEsElBueno.putAll(resLocalB);

        return resLocalEsteEsElBueno;
    }

    public static HashMap<String, String> searchRelation(HashMap<String, String> recursion, HashMap<String, String> original){
        LinkedList<String> ar = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        String keyLetter = "";

        try {
            for (Map.Entry<String, String> entry : recursion.entrySet()) {
                String key = entry.getKey();
                String[] value = key.split(",");
                String relation = entry.getValue();
                String estado = value[0] + "," + value[value.length-1];

                if( recursion.containsKey(relation+",a") || recursion.containsKey(relation+",b") ) {
                    return res;
                } else if (estado.equals(key)) {
                    String[] temp = relation.split(",");
                    for (String temp2 : temp) {
                        //Obtener combinaciones de qN
                        String temp3 = original.get(temp2 + "," + value[value.length-1]);
                        if (temp3 != null) {
                            String[] temp4 = temp3.split(",");
                            for (String a : temp4) {
                                if (!ar.contains(a)){
                                    ar.add(a);
                                }
                            }
                            keyLetter = relation + "," + value[value.length-1];
                        }
                    }
                    for (int i = 0; i < ar.size(); i++) {
                        if (i < ar.size() - 1) {
                            sb.append(ar.get(i)).append(",");
                        } else {
                            sb.append(ar.get(i));
                        }
                    }
                    String temp6 = sb.toString();
                    res.put(keyLetter, temp6);
                }
            }
        } catch (Exception e) {
            System.out.println("That node doesn't exist");
        }
        return searchRelation(res, original);
    }
}
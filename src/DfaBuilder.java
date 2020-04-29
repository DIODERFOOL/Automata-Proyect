import javax.print.DocFlavor;
import java.util.*;

public class DfaBuilder {
    static HashMap<String, String> res = new HashMap<>();
    static HashMap<String, String> resCopy = new HashMap<>();

    public static HashMap<String, String> builderDfa(HashMap<String, String> original, LinkedList<String> alphabet) {
        HashMap<String, String> resL;


        for (Map.Entry<String, String> entry : original.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("q0,a")) {
                res.put(key, value);
            }
            if (key.equals("q0,b")) {
                res.put(key, value);
            }
        }

        resL = buildDFA(res,original,alphabet);


        //resL = searchAllSetOfRelation(hashTemp, original, alpha);
        resL.putAll(res);
        //hashTemp.putAll(resL);

        return resL;
    }

    public static String[] searchRelation(String key, String relation, HashMap<String, String> original, String alpha) {
        String[] resTemp = new String[2];
        LinkedList<String> ar = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        String keyLetter = "";

        try {
            //String[] value = key.split(",");
            String[] temp = relation.split(",");

            for (String temp2 : temp) {
                //Obtener combinaciones de qN
                String temp3 = original.get(temp2 + "," + alpha);
                if (temp3 != null) {
                    String[] temp4 = temp3.split(",");
                    for (String a : temp4) {
                        if (!ar.contains(a)) {
                            ar.add(a);
                        }
                    }
                    keyLetter = relation + "," + alpha;
                }
            }
            for (int i = 0; i < ar.size(); i++) {
                if (i < ar.size() - 1) {
                    sb.append(ar.get(i)).append(",");
                } else {
                    sb.append(ar.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("That node doesn't exist");
        }

        resTemp[0] = keyLetter;
        resTemp[1] = sb.toString();

        return resTemp;
    }

    public static HashMap<String, String> buildDFA(HashMap<String,String> res,HashMap<String,String> original, LinkedList<String> alphabet) {
        String[] hashValueTemp;
        HashMap<String,String> hashTemporal = new HashMap<>();

        for (Map.Entry<String, String> entry : res.entrySet()) {
            String key = entry.getKey();
            String relation = entry.getValue();

            if (res.containsKey( res.get(relation+",a") + ",a" ) || res.containsKey( res.get(relation+",a")+",b" ) ) {
                return res;
            } else {
                for (String a : alphabet) {
                    hashValueTemp = searchRelation(key, relation, original, a);
                    hashTemporal.put(hashValueTemp[0], hashValueTemp[1]);
                }
            }
        }
        resCopy.putAll(hashTemporal);

        return buildDFA(resCopy, original, alphabet);
    }
}
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class DfaBuilder {
    public static HashMap<String, String> builderDfa(HashMap<String, String> original, LinkedList<String> alphabet) {
        HashMap<String, String> res = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int count = 0;

        //Construir DFA, recorriendo el HashMap de las Relaciones
        try {
                for (Map.Entry<String, String> entry : original.entrySet()) {
                    String key = entry.getKey();
                    String[] value = key.split(",");
                    String relation = entry.getValue();

                    String estado = value[0] + "," + value[1];

                    if (estado.equals("q0," + value[1]))
                        res.put(key, relation);
                    if (!estado.equals("q0," + value[1]) && estado.equals(key)) {
                        String[] temp = relation.split(",");
                        for (String temp2 : temp) {
                            //Obtener combinaciones de qN
                            String temp3 = original.get(temp2 + "," + value[1]);
                            if (temp3 != null) {
                                //Agregar resultado de las relaciones y meter a un string
                                sb.append(temp3);
                                System.out.println(sb.toString());
                            }
                        }
                    }
                    count++;
                }
        } catch (Exception e) {
            System.out.println("That node doesn't exist");
        }

        return res;
    }
}
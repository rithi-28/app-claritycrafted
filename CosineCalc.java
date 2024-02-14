import java.util.*;

public class CosineCalc{
	ArrayList<String> qvector = new ArrayList<>();
	ArrayList<String> dvector = new ArrayList<>();
	public void vectorLengthChecker() {
		if (qvector.size()>dvector.size()) {
			for (int i=dvector.size(); i<qvector.size(); i++) {
				dvector.set(i, "0");
			}
		}
		else if (dvector.size()>qvector.size()) {
			for (int i=qvector.size(); i<dvector.size(); i++) {
				qvector.set(i, "0");
			}
		}
	}
	
	public double makeList(String doc, String question) {
		String[] qvectorList =  question.split(",");
		String[] dvectorList = doc.split(",");
		for (int i=0; i<qvectorList.length; i++) {
			qvector.add(qvectorList[i]);
		}
		for (int i=0; i<dvectorList.length; i++) {
			dvector.add(dvectorList[i]);
		}
		return cosineSimilarity();
	}
	
	public double cosineSimilarity() {
		vectorLengthChecker();
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    for (int i = 0; i < qvector.size(); i++) {
	        dotProduct += Double.parseDouble(qvector.get(i)) * Double.parseDouble(dvector.get(i));
	        normA += Math.pow(Double.parseDouble(qvector.get(i)), 2);
	        normB += Math.pow(Double.parseDouble(dvector.get(i)), 2);
	    }   
	    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
	public List<String> processor(Map<Integer, String> vectorId, String qresult) {
		HashMap<Integer, Double> resultMap = new HashMap<>();
		for (Map.Entry<Integer, String> entry : vectorId.entrySet()) {
			double result = makeList(entry.getValue(), qresult);
			resultMap.put(entry.getKey(), result);
		}
		LinkedHashMap<Integer, Double> sortedMap = new LinkedHashMap<>();
		sortedMap = sortByValueDescending(resultMap);
		int firstKey = sortedMap.keySet().iterator().next();
		DBHandler db = new DBHandler("jdbc:postgresql://localhost:5432/vectordocsrepository", "rithika", "password@1");
		db.getConnection();
		return db.getResults(firstKey);
		
	}
	
	public static LinkedHashMap<Integer, Double> sortByValueDescending(HashMap<Integer, Double> hashMap) {
        List<Map.Entry<Integer, Double>> list = new LinkedList<>(hashMap.entrySet());

        
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue()); // Compare in descending order
            }
        });

        
        LinkedHashMap<Integer, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
	
}
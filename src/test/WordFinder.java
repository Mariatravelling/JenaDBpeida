package test;

public class WordFinder {

	public static void main(String[] args) {
		
		int start = 550;
		String queryString=
				"PREFIX dbo: <http://dbpedia.org/ontology/>"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"PREFIX dbp: <http://dbpedia.org/property/>"+
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"PREFIX dct: <http://purl.org/dc/terms/>"+
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
				"SELECT DISTINCT ?name ?actor ?Description ?Taxonomy ?Director ?Writer WHERE { ?film rdf:type dbo:Film.?film rdfs:label  ?name . ?film dct:subject  ?Taxonomy. ?film dbo:director ?Director.optional{?film rdfs:comment  ?Description.}";
			
		
		String target="";
		for (int i = 0; i < queryString.length(); i++) {
			
			if(i>=start){
				target+=queryString.charAt(i);
				
			}
			
		}
		System.out.println(target);

	}

}

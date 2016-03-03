package test;


import java.util.ArrayList;
import java.util.HashSet;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;


public class test {

	public static void main (String[] args){
		
		
		
		HashSet<String> directors = new HashSet<>();
		HashSet<String> writers = new HashSet<>();
		HashSet<String> producers = new HashSet<>();
		HashSet<String> musicians = new HashSet<>();
		HashSet<String> countries = new HashSet<>();
		HashSet<String> actors = new HashSet<>();
		HashSet<String> descriptions = new HashSet<>();
		HashSet<String> taxonomies = new HashSet<>();
		
		ArrayList<String> endResult = new ArrayList<>();
		
		String queryString=
				"PREFIX dbo: <http://dbpedia.org/ontology/>"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"PREFIX dbp: <http://dbpedia.org/property/>"+
				"PREFIX dct: <http://purl.org/dc/terms/>"+
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"SELECT  ?title ?Director ?Writer "
				+"?Producer "
						+"?Music  ?Country" 
						+"?Starring   ?Description" 
						+"?Taxonomy "+
						"WHERE {"+
						"?Film rdf:type dbo:Film ."+
						"?Film foaf:name ?title ."+
						"optional{?Film dbo:director  ?Director}."+
						"optional{?Film dbo:writer    ?Writer}."+
						"optional{?Film dbo:producer  ?Producer}."+
						"optional{?Film dbp:music  ?Music.}"+
						"optional{?Film dbp:country  ?Country.}"+
						"optional{?Film dbo:starring  ?Starring.}"+
						"optional{?Film rdfs:comment ?Description.}"+
						"?Film dct:subject   ?Taxonomy FILTER( ?title =\"Ronin\"@en  && lang(?title) = 'en' && lang(?Description) = 'en').}";

	
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet result = exec.execSelect();
		try {
			while(result.hasNext()){
				QuerySolution soln = result.nextSolution();
				
				try {
					if(soln.getResource("Director")!=null)directors.add(soln.getResource("Director").toString().replace("http://dbpedia.org/resource/", ""));

				} catch (ClassCastException e) {
					if(soln.getLiteral("Director")!=null)directors.add(soln.getLiteral("Director").getLexicalForm());
				}
				
				try {
					if(soln.getResource("Writer")!=null)writers.add(soln.getResource("Writer").toString().replace("http://dbpedia.org/resource/", ""));

				} catch (ClassCastException e) {
					if(soln.getLiteral("Writer")!=null)directors.add(soln.getLiteral("Writer").getLexicalForm());
				}
				
				try {
					if(soln.getResource("Producer")!=null)producers.add(soln.getResource("Producer").toString().replace("http://dbpedia.org/resource/", ""));

				} catch (ClassCastException e) {
					if(soln.getLiteral("Producer")!=null)directors.add(soln.getLiteral("Producer").getLexicalForm());
				}
				try {
					if(soln.getResource("Music")!=null)musicians.add(soln.getResource("Music").toString().replace("http://dbpedia.org/resource/", ""));
				} catch (ClassCastException e) {
					if(soln.getLiteral("Music")!=null)musicians.add(soln.getLiteral("Music").getLexicalForm());
				}
				try {
					if(soln.getLiteral("Country")!=null)countries.add(soln.getLiteral("Country").getLexicalForm());

				} catch (ClassCastException e) {
					if(soln.getResource("Country")!=null)countries.add(soln.getResource("Country").toString().replace("http://dbpedia.org/resource/", ""));
				}
				
				try {
					if(soln.getResource("Starring")!=null)actors.add(soln.getResource("Starring").toString().replace("http://dbpedia.org/resource/", ""));

				} catch (ClassCastException e) {
					if(soln.getLiteral("Starring")!=null)musicians.add(soln.getLiteral("Starring").getLexicalForm());
				}
				try {
					if(soln.getLiteral("Description")!=null)descriptions.add(soln.getLiteral("Description").getLexicalForm());

				} catch (ClassCastException e) {
					if(soln.getResource("Description")!=null)countries.add(soln.getResource("Description").toString().replace("http://dbpedia.org/resource/", ""));
				}
				try {
					if(soln.getResource("Taxonomy")!=null)taxonomies.add(soln.getResource("Taxonomy").toString().replace("http://dbpedia.org/resource/Category:", ""));

				} catch (ClassCastException e) {
					if(soln.getLiteral("Taxonomy")!=null)musicians.add(soln.getLiteral("Taxonomy").getLexicalForm());
				}
			}
		} finally {
			exec.close();
		}
		ArrayList<HashSet<String>> sets = new ArrayList<>();
		sets.add(directors);
		sets.add(writers);
		sets.add(producers);
		sets.add(musicians);
		sets.add(countries);
		sets.add(actors);
		sets.add(descriptions);
		sets.add(taxonomies);
		
		for (int i = 0; i < sets.size(); i++) {
			if(sets.get(i).size()>0){
				endResult.add(sets.get(i).toString().substring(1, sets.get(i).toString().length()-1).replace("_", " "));
			}
			else{
				endResult.add("---");
			}
		}
		
		System.out.println(endResult.get(6));
		System.out.println(endResult.get(7));
		
	}

}




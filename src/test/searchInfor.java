package test;

import java.io.IOException;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;

public class searchInfor {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String queryString=
				"PREFIX dbo: <http://dbpedia.org/ontology/>"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"PREFIX dbp: <http://dbpedia.org/property/>"+
				"PREFIX dct: <http://purl.org/dc/terms/>"+
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
				"SELECT  ?title "+
						"WHERE {"+
						"?Film rdf:type dbo:Film ."+
						"?Film foaf:name ?title .?Film dbp:language ?language. FILTER(?language=\"English\"@en).}ORDER BY ?title LIMIT 2000 OFFSET 33650";

		int num=0;
		
		Query query = QueryFactory.create(queryString);
		QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		
		ResultSet result = exec.execSelect();
		try {
			while(result.hasNext()){
				num++;
				QuerySolution soln = result.nextSolution();
 				//Resource res = soln.getResource("Taxonomy");
				Literal name = soln.getLiteral("title");
				String input=name.toString().substring(0, name.toString().length()-3);
				//System.out.println(" >>> "+input);
				FindByName fd=new FindByName();
				input.replaceAll("[\\pP°Æ°Ø°∞°±]", "");
				System.out.println(num);
				String show=fd.findMovie(input);
				fd.writeFile(input, show);
				//System.out.println(show);
					
			}
		} finally {
			exec.close();
		}
	}
	
	

}

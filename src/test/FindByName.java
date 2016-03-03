package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;





import java.util.ArrayList;
import java.util.HashSet;


public class FindByName {

	
	public String findMovie(String input)
	{
		
		
		
		HashSet<String> directors = new HashSet<>();
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
				"SELECT  ?title ?Director "
						+"?Starring   ?Description" 
						+"?Taxonomy "+
						"WHERE {"+
						"?Film rdf:type dbo:Film ."+
						"?Film foaf:name ?title ."+
						"optional{?Film dbo:director  ?Director}."+
						"optional{?Film dbo:starring  ?Starring.}"+
						"optional{?Film rdfs:comment ?Description.}"+
						"?Film dct:subject   ?Taxonomy FILTER( ?title =\""+input+"\"@en  && lang(?title) = 'en' && lang(?Description) = 'en').} ";

	
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
					if(soln.getLiteral("title")!=null)countries.add(soln.getLiteral("title").getLexicalForm());

				} catch (ClassCastException e) {
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
		String content="";
		//System.out.println(endResult.size());
		for(int j=0;j<endResult.size();j++)
		{
			content=content+endResult.get(j)+";;";
		}
		
		return content;
		
	}
	
	public void writeFile(String fileName,String content) throws IOException
	{
		if(fileName.contains("/"))
		{
			fileName=fileName.replaceAll("[/.():]", "");
		}
	     PrintWriter pw = new PrintWriter( new FileWriter( "/Users/wangzehui/Desktop/Semantic Web Tech/DBData/"+fileName+".txt" ) );
	     pw.print( content );
	     pw.close( );
	}

}



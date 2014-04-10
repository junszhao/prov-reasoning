package uk.ac.lancaster.prov.reasoning;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;

public class OWLReasoningProv {
	
	static String queryStringEntity = "select (count (distinct ?s) as ?cnt) where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Entity> .}";
	static String queryStringActivity = "select (count (distinct ?s) as ?cnt) where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Activity> .}";
	static String queryStringAgent = "select (count (distinct ?s) as ?cnt) where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Agent> .}";
	 
	static String queryGeneration = "select (count(distinct *) as ?cnt) where {?s <http://www.w3.org/ns/prov#wasGeneratedBy> ?o.}";
	static String queryDerivation = "select (count(distinct *) as ?cnt) where {?s <http://www.w3.org/ns/prov#wasDerivedFrom> ?o.}";
	static String queryAttribution = "select (count(distinct *) as ?cnt) where {?s <http://www.w3.org/ns/prov#wasAttributedTo> ?o.}";
	static String queryStartTime = "select (count(distinct *) as ?cnt)where {?s <http://www.w3.org/ns/prov#wasStartedAt> ?o.}";
	static String queryEndTime = "select (count(distinct *) as ?cnt)where {?s <http://www.w3.org/ns/prov#wasEndedAt> ?o.}";
	static String queryUsage = "select (count(distinct *) as ?cnt) where {?s <http://www.w3.org/ns/prov#used> ?o.}";
	static String queryInfluence = "select (count(distinct *) as ?cnt) where {?s <http://www.w3.org/ns/prov#wasInformedBy> ?o.}";
	static String queryAssociation = "select (count(distinct *) as ?cnt) where {?s <http://www.w3.org/ns/prov#wasAssociatedWith> ?o.}";
	static String queryDelegation = "select (count(distinct *) as ?cnt) where {?s <http://www.w3.org/ns/prov#actedOnBehalfOf> ?o.}";

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		OWLReasoningProv owlprov = new OWLReasoningProv();
		
		OutputStream os = new FileOutputStream("count_query_results_inferred_ta.txt");
    	PrintStream writer = new PrintStream(os);
    	
		try {
			//owlprov.reasoning(args);
			//owlprov.validating(args);
			
			Model schema = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/provo.ttl");
		    Model data2 = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/workflowrun.prov.ttl");
		    
		    Reasoner reasoner2 = ReasonerRegistry.getOWLReasoner();
		    reasoner2 = reasoner2.bindSchema(schema);
		    
		    InfModel infmodel2 = ModelFactory.createInfModel(reasoner2, data2);
			
			owlprov.queryInferredProv(queryStringEntity, "Entities", infmodel2, writer);
			owlprov.queryInferredProv(queryStringActivity, "Activities", infmodel2, writer);
			owlprov.queryInferredProv(queryStringAgent, "Agents", infmodel2, writer);
			owlprov.queryInferredProv(queryGeneration, "wasGeneratedBy", infmodel2, writer);
			owlprov.queryInferredProv(queryDerivation, "wasDerivedFrom", infmodel2, writer);
			owlprov.queryInferredProv(queryAttribution, "wasAttributedTo", infmodel2, writer);
			owlprov.queryInferredProv(queryStartTime, "startedAtTime", infmodel2, writer);
			owlprov.queryInferredProv(queryEndTime, "endedAtTime", infmodel2, writer);
			owlprov.queryInferredProv(queryUsage, "used", infmodel2, writer);
			owlprov.queryInferredProv(queryInfluence, "wasInformedBy", infmodel2, writer);
			owlprov.queryInferredProv(queryAssociation, "wasAssociatedWith", infmodel2, writer);
			owlprov.queryInferredProv(queryDelegation, "actedOnBehalfOf", infmodel2, writer);
			
			writer.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void queryInferredProv(String queryString, String queryType, Model infmodel2, PrintStream writer)throws Exception {
		
	    
	    Query query = QueryFactory.create(queryString) ;
	    
	    QueryExecution qexec = QueryExecutionFactory.create(query, infmodel2) ;
	    
	    try {
	        ResultSet results = qexec.execSelect() ;
	        
	        writer.println("=== Find " + queryType + " === ");
	        
	        ResultSetFormatter.out(writer, results, query) ;
	        
	      } finally { qexec.close() ; }
	    
	    //printAllStatements(infmodel2);
		
	}
	
	public void reasoning(String[] args)throws Exception {
		Model schema = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/provo.ttl");
	    Model data = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/workflowrun.prov.ttl");
	    
	    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	    reasoner = reasoner.bindSchema(schema);
	    
	    InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
	    
	    Resource nForce = infmodel.getResource("http://ns.taverna.org.uk/2011/data/97dc1a94-8639-476e-a6e6-9fe8abfacf56/list/6d01b122-368b-4146-9ce6-232b20081948/false/2");
	    System.out.println("nforce :");
	    printStatements(infmodel, nForce, null, null);
		
	}
	
	
	public void validating (String[] args)throws Exception {
		Model schema = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/provo.ttl");
	    Model data = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/workflowrun.prov.ttl");
	    
	    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	    reasoner = reasoner.bindSchema(schema);
	    
	    InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
	    
	    ValidityReport validity = infmodel.validate();
	    if (validity.isValid()) {
	        System.out.println("OK");
	    } else {
	        System.out.println("Conflicts");
	        for (Iterator i = validity.getReports(); i.hasNext(); ) {
	            ValidityReport.Report report = (ValidityReport.Report)i.next();
	            System.out.println(" - " + report);
	        }
	    }

	}
	
	
	public void printStatements(Model m, Resource s, Property p, Resource o) {
        for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }

}

package uk.ac.lancaster.prov.reasoning;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.RDF;

public class InferMissingClasses {
	
	static String queryStringEntity = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Entity> .}";
	static String queryStringActivity = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Activity> .}";
	static String queryStringAgent = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Agent> .}";
	
	static String queryStringAssociation = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Association> .}";
	static String queryStringUsage = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Usage> .}";
	static String queryStringGeneration = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Generation> .}";
	
	static String queryStringCollection = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Collection> .}";
	static String queryStringDerivation = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Derivation> .}";
	static String queryStringAttribution = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Attribution> .}";
	
	static String queryStringInfluence = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Influence> .}";
	static String queryStringDelegation = "select distinct * where {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/prov#Delegation> .}";


	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		InferMissingClasses owlprov = new InferMissingClasses();
		
    	try {

			Model schema = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/provo.ttl");
		    Model data2 = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/workflowrun.prov.ttl");
		    
		    Reasoner reasoner2 = ReasonerRegistry.getOWLReasoner();
		    reasoner2 = reasoner2.bindSchema(schema);
		    
		    InfModel infmodel2 = ModelFactory.createInfModel(reasoner2, data2);
			
			owlprov.writeInferredProv(queryStringEntity, "Entity", infmodel2);
			owlprov.writeInferredProv(queryStringActivity, "Activity", infmodel2);
			owlprov.writeInferredProv(queryStringAgent, "Agent", infmodel2);
			owlprov.writeInferredProv(queryStringAssociation, "Association", infmodel2);
			owlprov.writeInferredProv(queryStringUsage, "Usage", infmodel2);
			owlprov.writeInferredProv(queryStringGeneration, "Generation", infmodel2);
			owlprov.writeInferredProv(queryStringCollection, "Collection", infmodel2);
			owlprov.writeInferredProv(queryStringDerivation, "Derivation", infmodel2);
			owlprov.writeInferredProv(queryStringAttribution, "Attribution", infmodel2);
			owlprov.writeInferredProv(queryStringInfluence, "Influence", infmodel2);
			owlprov.writeInferredProv(queryStringDelegation, "Delegation", infmodel2);
			
			//owlprov.validating(args);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean isProvClass(String instance, String classname) throws Exception {
		Model schema = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/provo.ttl");
	    Model data = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/workflowrun.prov.ttl");
	    
	    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	    reasoner = reasoner.bindSchema(schema);
	    
	    InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
	    
	    Resource _nForce = infmodel.getResource(instance);
	    
	    writeStatements(infmodel, _nForce, null, null);
	    
	    Model _testStms = ModelFactory.createDefaultModel();
	    
	    RDFNode _class = _testStms.createResource(classname);
	    
	    return infmodel.contains(_nForce, RDF.type, _class);
	    
	}
	
	
	public InfModel reasoning(String[] args)throws Exception {
		Model schema = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/provo.ttl");
	    Model data = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/workflowrun.prov.ttl");
	    
	    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	    reasoner = reasoner.bindSchema(schema);
	    
	    InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
	    
	    
	    
//	    Resource nForce = infmodel.getResource("http://ns.taverna.org.uk/2011/data/97dc1a94-8639-476e-a6e6-9fe8abfacf56/list/6d01b122-368b-4146-9ce6-232b20081948/false/2");
//	    System.out.println("nforce :");
	    return infmodel;
		
	}
	
	
	public void printStatements(Model m, Resource s, Property p, Resource o) {
        for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }
	
	public void writeStatements(Model m, Resource s, Property p, Resource o) throws FileNotFoundException {
		
		OutputStream os = new FileOutputStream("temp.ttl");
    	PrintStream writer = new PrintStream(os);
    	
        for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            writer.println(PrintUtil.print(stmt));
        }
    }
	
	public void printAllStatements(InfModel m) {
        for (StmtIterator i = m.getDeductionsModel().listStatements(); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }
	
	public void writeInferredProv(String queryString, String queryType, Model infmodel2)throws Exception {
		
	    
	    Query query = QueryFactory.create(queryString) ;
	    
	    QueryExecution qexec = QueryExecutionFactory.create(query, infmodel2) ;
	    
	    try {
	    	OutputStream os = new FileOutputStream(queryType+".txt");
	    	PrintStream writer = new PrintStream(os);
	    	
	        ResultSet results = qexec.execSelect() ;
	        
	        for ( ; results.hasNext() ; ) {
	        	QuerySolution soln = results.nextSolution() ;
	        	String resourceURI = soln.getResource("s").toString();
	        	writer.println(PrintUtil.print("<"+resourceURI+">\t<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>\t<http://www.w3.org/ns/prov#"+queryType +"> ."));
	        	
	        }
	        
	        writer.close();
	        
	        
	      } finally { qexec.close() ; }
	    
		
	}
	
	

}

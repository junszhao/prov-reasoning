package uk.ac.lancaster.prov.reasoning;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.reasoner.rulesys.RDFSRuleReasonerFactory;
import com.hp.hpl.jena.vocabulary.*;


public class ReasoningExamples {
	public static void main(String args[]) throws Exception {
		ReasoningExamples example = new ReasoningExamples();
		example.subPropertyReasoning(args);
		example.customizedReasoner(args);
	}
	
	public void subPropertyReasoning(String[] args)throws Exception {
		String NS = "urn:x-hp-jena:eg/";
		
		// Build a trivial example data set
		// we say <a> <p> "foo" . <p> <rdfs:subPropertyOf> <q>
		Model rdfsExample = ModelFactory.createDefaultModel();
		Property p = rdfsExample.createProperty(NS, "p");
		Property q = rdfsExample.createProperty(NS, "q");
		rdfsExample.add(p, RDFS.subPropertyOf, q);
		rdfsExample.createResource(NS+"a").addProperty(p, "foo");
		
		InfModel inf = ModelFactory.createRDFSModel(rdfsExample);  // [1]
		
		Resource a = inf.getResource(NS+"a");
	    System.out.println("Statement: " + a.getProperty(q));
	}
	
	public void customizedReasoner(String[] args)throws Exception {
		String NS = "urn:x-hp-jena:eg/";
		
		// Build a trivial example data set
		// we say <a> <p> "foo" . <p> <rdfs:subPropertyOf> <q>
		Model rdfsExample = ModelFactory.createDefaultModel();
		Property p = rdfsExample.createProperty(NS, "p");
		Property q = rdfsExample.createProperty(NS, "q");
		rdfsExample.add(p, RDFS.subPropertyOf, q);
		rdfsExample.createResource(NS+"a").addProperty(p, "foo");
		
		Reasoner reasoner = RDFSRuleReasonerFactory.theInstance().create(null);
	    reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel,
	                          ReasonerVocabulary.RDFS_SIMPLE); // in order to config the reasoner
		
		InfModel inf = ModelFactory.createInfModel(reasoner, rdfsExample);
		
		Resource a = inf.getResource(NS+"a");
	    System.out.println("Statement: " + a.getProperty(q));
	}
}
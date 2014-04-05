package uk.ac.lancaster.prov.reasoning;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;

public class OWLReasoningProv {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OWLReasoningProv owlprov = new OWLReasoningProv();
		try {
			owlprov.reasoning(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void reasoning(String[] args)throws Exception {
		Model schema = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/provo.owl");
	    Model data = FileManager.get().loadModel("/Users/zhaoj/workspace/prov-reasoning/data/workflowrun.prov.ttl");
	    
	    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	    reasoner = reasoner.bindSchema(schema);
	    
	    InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
	    
	    Resource nForce = infmodel.getResource("http://ns.taverna.org.uk/2011/data/97dc1a94-8639-476e-a6e6-9fe8abfacf56/list/6d01b122-368b-4146-9ce6-232b20081948/false/2");
	    System.out.println("nforce :");
	    printStatements(infmodel, nForce, null, null);
		
	}
	
	public void printStatements(Model m, Resource s, Property p, Resource o) {
        for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }

}

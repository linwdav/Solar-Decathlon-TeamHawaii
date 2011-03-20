package edu.hawaii.ihale.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.w3c.dom.Document; 
import org.w3c.dom.NodeList;

public class Parser {
  private static XPathFactory factory = XPathFactory.newInstance(); 
  private static XPath xpath = factory.newXPath(); 

public Map<String,String> parse(Representation rep) 
    throws IOException, XPathExpressionException {
  DomRepresentation dom = new DomRepresentation(rep);
  Document doc = dom.getDocument();
  Map<String,String> ret = new HashMap <String,String>();
  
  XPathExpression expr = xpath.compile("//state"); 
  Object result = expr.evaluate(doc, XPathConstants.NODESET); 
  NodeList nodes = (NodeList) result;
  for(int i = 0; i < nodes.getLength(); i++) {
    ret.put(nodes.item(i).getAttributes().getNamedItem("key").getTextContent(), 
        nodes.item(i).getAttributes().getNamedItem("value").getTextContent());
  }
  System.out.println(ret);
  return ret;
}
}

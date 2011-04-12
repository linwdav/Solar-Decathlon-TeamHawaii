package edu.hawaii.ihale.backend;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import javax.xml.xpath.XPathExpressionException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.representation.FileRepresentation;

/**
 * JUnit tests for XmlHandler.
 * 
 * @author Michael Cera
 */
public class TestXmlHandler {

  private static FileRepresentation historyRepresentation;
  private static FileRepresentation eGaugeRepresentation;
  private static XmlHandler handle = new XmlHandler();

  /**
   * Reads in XML test files.
   */
  @BeforeClass
  public static void xmltoRepresentation() {

    String eGaugeXml = System.getProperty("user.dir") + "/test-egauge.xml";
    String historyXml = System.getProperty("user.dir") + "/test-history.xml";

    historyRepresentation = new FileRepresentation(historyXml, MediaType.TEXT_XML);
    eGaugeRepresentation = new FileRepresentation(eGaugeXml, MediaType.TEXT_XML);
  }

  /**
   * Reads in XML representation of test-history.xml.
   * 
   * @throws IOException IOException
   * @throws XPathExpressionException XPathExpressionException
   */
  @Test
  public void testXml2StateEntry() throws XPathExpressionException, IOException {
    boolean testPass = false;
    testPass = handle.parseIhaleXml2StateEntry(historyRepresentation);
    assertTrue("testXml2StateEntry failed", testPass);
  }

  /**
   * Reads in XML representation of test-egauge.xml.
   * 
   * @throws IOException IOException
   * @throws XPathExpressionException XPathExpressionException
   */
  @Test
  public void testEgauge2StateEntry() throws XPathExpressionException, IOException {
    boolean testPass = false;
    testPass = handle.parseEGaugeXmlToStateEntry(eGaugeRepresentation);
    assertTrue("testEgauge2StateEntry failed", testPass);
  }
}

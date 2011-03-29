package edu.hawaii.ihale.backend;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

    String xmlDirectory =
        System.getProperty("user.dir") + "/src/edu/hawaii/ihale/backend/xml/test/";
    String eGaugeXml = xmlDirectory + "test-egauge.xml";
    String historyXml = xmlDirectory + "history.xml";

    historyRepresentation = new FileRepresentation(historyXml, MediaType.TEXT_XML);
    eGaugeRepresentation = new FileRepresentation(eGaugeXml, MediaType.TEXT_XML);
  }

  /**
   * Reads in XML representation of test-history.xml.
   */
  @Test
  public void testXml2StateEntry() {
    boolean testPass = false;
    try {
      testPass = handle.xml2StateEntry(historyRepresentation);
    }
    catch (XPathExpressionException e) {
      fail("XPathExpressionException");
      e.printStackTrace();
    }
    catch (IOException e) {
      fail("IOException");
      e.printStackTrace();
    }
    assertTrue(testPass);
  }

  /**
   * Reads in XML representation of test-egauge.xml.
   */
  @Test
  public void testEgauge2StateEntry() {
    boolean testPass = false;
    try {
      testPass = handle.eGuage2StateEntry(eGaugeRepresentation);
    }
    catch (XPathExpressionException e) {
      fail("XPathExpressionException");
      e.printStackTrace();
    }
    catch (IOException e) {
      fail("IOException");
      e.printStackTrace();
    }
    assertTrue(testPass);
  }

}
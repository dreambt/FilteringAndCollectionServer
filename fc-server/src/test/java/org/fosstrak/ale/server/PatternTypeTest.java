package org.fosstrak.ale.server;

import java.net.URL;

import org.fosstrak.ale.server.PatternType;
import org.apache.log4j.PropertyConfigurator;



import junit.framework.TestCase;

public class PatternTypeTest extends TestCase {

	private static final String GID_96 = "gid-96";
	private static final String SGTIN_64 = "sgtin-64";
	private static final String SSCC_64 = "sscc-64";

	private static final int GID_96_DATAFIELDS = 3;
	private static final int SGTIN_64_DATAFIELDS = 4;
	private static final int SSCC_64_DATAFIELDS = 3;

	protected void setUp() throws Exception {

		super.setUp();

		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);

	}

	public void testGetType() throws Exception {

		assertEquals(PatternType.GID_96, PatternType.getType(GID_96));
		assertEquals(PatternType.SGTIN_64, PatternType.getType(SGTIN_64));
		assertEquals(PatternType.SSCC_64, PatternType.getType(SSCC_64));

	}

	public void testGetNumberOfDataFields() throws Exception {

		assertEquals(GID_96_DATAFIELDS, PatternType.GID_96.getNumberOfDatafields());
		assertEquals(SGTIN_64_DATAFIELDS, PatternType.SGTIN_64.getNumberOfDatafields());
		assertEquals(SSCC_64_DATAFIELDS, PatternType.SSCC_64.getNumberOfDatafields());

	}

	public void testToString() throws Exception {

		assertEquals(GID_96, PatternType.GID_96.toSring());
		assertEquals(SGTIN_64, PatternType.SGTIN_64.toSring());
		assertEquals(SSCC_64, PatternType.SSCC_64.toSring());

	}

}
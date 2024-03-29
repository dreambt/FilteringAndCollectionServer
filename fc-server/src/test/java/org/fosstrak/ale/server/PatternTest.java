package org.fosstrak.ale.server;

import java.net.URL;

import org.fosstrak.ale.server.Pattern;
import org.fosstrak.ale.server.PatternUsage;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.apache.log4j.PropertyConfigurator;


import junit.framework.TestCase;

public class PatternTest extends TestCase {

	private static final String TAG_PATTERN = "urn:epc:pat:sgtin-64:1.2.3.4";
	private static final String FILTER_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].*.*";
	private static final String GROUP_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].X.*";
	private static final String GROUP_MEMBER_PATTERN = "urn:epc:pat:sgtin-64:1.2.3.4";
	private static final String GROUP_NAME_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].3.*";
	private static final String GROUP_NOT_MEMBER_PATTERN = "urn:epc:pat:sgtin-64:1.0.3.4";
	private static final String DISJOINT_GROUP_PATTERN = "urn:epc:pat:sgtin-64:1.[3-4].X.*";
	private static final String INVALID_PATTERN = "urn:epc:pat:sgtin-64:a.b.c.d";

	protected void setUp() throws Exception {

		super.setUp();

		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);

	}

	public void testCreateTagPattern() throws Exception {

		new Pattern(TAG_PATTERN, PatternUsage.TAG);

	}

	public void testCreateTagPatternWithInvalidStringRepresentation() throws Exception {

		try {
			new Pattern(FILTER_PATTERN, PatternUsage.TAG);
		} catch (ECSpecValidationExceptionResponse e) {
			assertEquals("Invalid data field '[1-2]'. Only 'int' is allowed.", e.getMessage());
			return;
		}

		fail("Should throw an ECSpecValidationException because the string representation of the tag pattern contains a '*'.");

	}

	public void testCreateFilterPattern() throws Exception {

		new Pattern(TAG_PATTERN, PatternUsage.FILTER);
		new Pattern(FILTER_PATTERN, PatternUsage.FILTER);

	}

	public void testCreateFilterPatternWithInvalidStringRepresentation() throws Exception {

		try {
			new Pattern(GROUP_PATTERN, PatternUsage.FILTER);
		} catch (ECSpecValidationExceptionResponse e) {
			assertEquals("Invalid data field 'X'. Only '*', '[lo-hi]' or 'int' are allowed.", e.getMessage());
			return;
		}

		fail("Should throw an ECSpecValidationException because the string representation of the filter pattern contains a 'X'.");

	}

	public void testCreateGroupPattern() throws Exception {

		new Pattern(TAG_PATTERN, PatternUsage.GROUP);
		new Pattern(FILTER_PATTERN, PatternUsage.GROUP);
		new Pattern(GROUP_PATTERN, PatternUsage.GROUP);

	}

	public void testCreateGroupPatternWithInvalidStringRepresentation() throws Exception {

		try {
			new Pattern(INVALID_PATTERN, PatternUsage.GROUP);
		} catch (ECSpecValidationExceptionResponse e) {
			assertEquals("Invalid data field 'a'. Only '*', 'X', '[lo-hi]' or 'int' are allowed.", e.getMessage());
			return;
		}

		fail("Should throw an ECSpecValidationException because the string representation of the group pattern is invalid.");

	}

	public void testIsDisjointString() throws Exception {

		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);

		assertTrue(groupPattern.isDisjoint(DISJOINT_GROUP_PATTERN));
		assertFalse(groupPattern.isDisjoint(GROUP_PATTERN));

	}

	public void testIsDisjointPattern() throws Exception {

		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		Pattern disjointGroupPattern = new Pattern(DISJOINT_GROUP_PATTERN, PatternUsage.GROUP);

		assertTrue(groupPattern.isDisjoint(disjointGroupPattern));
		assertFalse(groupPattern.isDisjoint(groupPattern));

	}

	public void testIsMember() throws Exception {

		Pattern filterPattern = new Pattern(FILTER_PATTERN, PatternUsage.FILTER);

		assertTrue(filterPattern.isMember(GROUP_MEMBER_PATTERN));
		assertFalse(filterPattern.isMember(GROUP_NOT_MEMBER_PATTERN));

	}

	public void testGetGroupName() throws Exception {

		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);

		assertEquals(GROUP_NAME_PATTERN, groupPattern.getGroupName(GROUP_MEMBER_PATTERN));
		assertNull(groupPattern.getGroupName(GROUP_NOT_MEMBER_PATTERN));

	}

	public void testToString() throws Exception {

		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);

		assertEquals(GROUP_PATTERN, groupPattern.toString());

	}

}

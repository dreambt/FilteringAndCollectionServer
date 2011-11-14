package org.fosstrak.ale.client;

import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;

/**
 * This interface specifies a ReportHandlerListener.
 */
public interface ReportHandlerListener {

	public void dataReceived(ECReports reports);
	
}
package org.fosstrak.ale.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.reader.rp.proxy.RPProxyException;
import org.apache.log4j.Logger;

/**
 * This class listen to a specified port for ec reports and notifies his subscribers about new ec reports.
 */
public class ReportHandler implements Runnable {

	/** the logger */
	private static Logger log = Logger.getLogger(ReportHandler.class);
	
	/** the thread */
	private final Thread thread;
	
	/** contains the subscribers of this ReportHandler */
	private final List<ReportHandlerListener> listeners = new Vector<ReportHandlerListener>();
	
	/** server socket to communicate with the ALE */
	private final ServerSocket ss;

	/**
	 * Constructor opens the server socket and starts the thread.
	 * 
	 * @param port on which the ALE notifies
	 * @throws ImplementationException if server socket could not be created on specified port.
	 */
	public ReportHandler(int port) throws ImplementationExceptionResponse {
		
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			throw new ImplementationExceptionResponse(e.getMessage());
		}
		
		thread = new Thread(this);
		thread.start();
		
	}
	
	/**
	 * This mehtod adds a new subscriber to the list of listeners.
	 * 
	 * @param listener to add to this ReportHandler
	 */
	public void addListener(ReportHandlerListener listener) {
		
		listeners.add(listener);
		
	}
	
	/**
	 * This method removes a subscriber from the list of listeners.
	 * 
	 * @param listener to remove from this ReportHandler
	 */
	public void removeListener(ReportHandlerListener listener) {
		
		listeners.remove(listener);
		
	}
	
	/**
	 * This method contains the main loop of the thread, in which data is read from the socket
	 * and forwarded to the method notifyListeners().
	 */
	public void run() {
		
		try {
			while (true) {
				Socket s = ss.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
				StringBuffer data = new StringBuffer();
				String line = null;
				while (!reader.ready()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
				while (!"".equals(line) && reader.ready()) {
					line = reader.readLine();
					data.append(line);
				};
				log.debug("Incoming ecReports: " + data);
				notifyListeners(data);
				s.close();
			}
		} catch (Exception e) {}
		
	}
	
	/**
	 * This method stops the thread and closes the socket
	 */
	public void stop() {
		
		// stop thread
		if (thread.isAlive()) {
			thread.interrupt();
		}
		
		// close socket
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method starts the ReportHandler.
	 * 
	 * @param args command line arguments, which can contain the port number
	 * @throws RPProxyException if something goes wrong while creating the ReportHandler
	 */
	public static void main(String[] args) throws ImplementationExceptionResponse {
		
		int port = 9000;
		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {}
		}
		new ReportHandler(port);
		
	}
	
	//
	// private
	//
	
	/**
	 * This method parses the data to a ec reports and notifies all subscribers about the newly received ec reports.
	 * 
	 * @param data string buffer with ec reports as string
	 * @throws Exception 
	 */
	private void notifyListeners(StringBuffer data) throws Exception {
		
		ECReports ecReports = null;
		//FIXME
		//ecReports = DeserializerUtil.deserializeECReports(new ByteArrayInputStream(data.toString().getBytes()));
			
		Iterator listenerIt = listeners.iterator();
		while (listenerIt.hasNext()) {
			((ReportHandlerListener)listenerIt.next()).dataReceived(ecReports);
		}
	
	}

}
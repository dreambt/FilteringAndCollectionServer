package org.fosstrak.ale.wsdl.ale.epcglobal;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.ws.Endpoint;

import org.fosstrak.ale.server.ALE;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;

/**
 * This class was generated by Apache CXF (incubator) 2.0.4-incubator
 * Sun Mar 02 20:39:51 CET 2008
 * Generated source version: 2.0.4-incubator
 *
 */
public class ALEServicePortTypeImpl implements ALEServicePortType {

    private static final Logger LOG = Logger.getLogger(ALEServicePortTypeImpl.class.getName());

    private static boolean isInitialized = false;

	public ALEServicePortTypeImpl() {
		if (!isInitialized) { initialize(); }
	}

	private static void initialize() {
		if (!ALE.isReady()) {
			LOG.info("starting ALE.");
			try {
				ALE.initialize();
			} catch (ImplementationExceptionResponse e) {
				e.printStackTrace();
				LOG.info("ERROR IN ALE STARTUP");
			}
		}
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#immediate(org.fosstrak.ale.wsdl.ale.epcglobal.Immediate  parms )*
     */
    public org.fosstrak.ale.xsd.ale.epcglobal.ECReports immediate(Immediate parms) throws ECSpecValidationExceptionResponse , ImplementationExceptionResponse , SecurityExceptionResponse    {
    	if (!isInitialized) { initialize(); }
        return ALE.immediate(parms.getSpec());
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#getVendorVersion(org.fosstrak.ale.wsdl.ale.epcglobal.EmptyParms  parms )*
     */
    public java.lang.String getVendorVersion(EmptyParms parms) throws ImplementationExceptionResponse    {
    	if (!isInitialized) { initialize(); }
        return ALE.getVendorVersion();
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#unsubscribe(org.fosstrak.ale.wsdl.ale.epcglobal.Unsubscribe  parms )*
     */
    public org.fosstrak.ale.wsdl.ale.epcglobal.VoidHolder unsubscribe(Unsubscribe parms) throws ImplementationExceptionResponse , SecurityExceptionResponse , NoSuchNameExceptionResponse , NoSuchSubscriberExceptionResponse , InvalidURIExceptionResponse    {
    	if (!isInitialized) { initialize(); }
    	ALE.unsubscribe(parms.getSpecName(), parms.getNotificationURI());
    	return new VoidHolder();
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#getECSpec(org.fosstrak.ale.wsdl.ale.epcglobal.GetECSpec  parms )*
     */
    public org.fosstrak.ale.xsd.ale.epcglobal.ECSpec getECSpec(GetECSpec parms) throws ImplementationExceptionResponse , SecurityExceptionResponse , NoSuchNameExceptionResponse    {
    	if (!isInitialized) { initialize(); }
    	return ALE.getECSpec(parms.getSpecName());
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#getStandardVersion(org.fosstrak.ale.wsdl.ale.epcglobal.EmptyParms  parms )*
     */
    public java.lang.String getStandardVersion(EmptyParms parms) throws ImplementationExceptionResponse    {
    	if (!isInitialized) { initialize(); }
        return ALE.getStandardVersion();
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#subscribe(org.fosstrak.ale.wsdl.ale.epcglobal.Subscribe  parms )*
     */
    public org.fosstrak.ale.wsdl.ale.epcglobal.VoidHolder subscribe(Subscribe parms) throws ImplementationExceptionResponse , SecurityExceptionResponse , NoSuchNameExceptionResponse , InvalidURIExceptionResponse , DuplicateSubscriptionExceptionResponse    {
    	if (!isInitialized) { initialize(); }
    	ALE.subscribe(parms.getSpecName(), parms.getNotificationURI());
    	return new VoidHolder();
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#poll(org.fosstrak.ale.wsdl.ale.epcglobal.Poll  parms )*
     */
    public org.fosstrak.ale.xsd.ale.epcglobal.ECReports poll(Poll parms) throws ImplementationExceptionResponse , SecurityExceptionResponse , NoSuchNameExceptionResponse    {
    	if (!isInitialized) { initialize(); }
    	return ALE.poll(parms.getSpecName());
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#getSubscribers(org.fosstrak.ale.wsdl.ale.epcglobal.GetSubscribers  parms )*
     */
    public org.fosstrak.ale.wsdl.ale.epcglobal.ArrayOfString getSubscribers(GetSubscribers parms) throws ImplementationExceptionResponse , SecurityExceptionResponse , NoSuchNameExceptionResponse    {
    	if (!isInitialized) { initialize(); }
    	ArrayOfString aof = new ArrayOfString();

    	for (String sub : ALE.getSubscribers(parms.getSpecName())) {
    		aof.getString().add(sub);
    	}
    	return aof;
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#undefine(org.fosstrak.ale.wsdl.ale.epcglobal.Undefine  parms )*
     */
    public org.fosstrak.ale.wsdl.ale.epcglobal.VoidHolder undefine(Undefine parms) throws ImplementationExceptionResponse , SecurityExceptionResponse , NoSuchNameExceptionResponse    {
    	if (!isInitialized) { initialize(); }
    	ALE.undefine(parms.getSpecName());
    	return new VoidHolder();
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#define(org.fosstrak.ale.wsdl.ale.epcglobal.Define  parms )*
     */
    public org.fosstrak.ale.wsdl.ale.epcglobal.VoidHolder define(Define parms) throws ECSpecValidationExceptionResponse , ImplementationExceptionResponse , SecurityExceptionResponse , DuplicateNameExceptionResponse    {
    	if (!isInitialized) { initialize(); }
    	try {
    		ALE.define(parms.getSpecName(), parms.getSpec());
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return new VoidHolder();
    }

    /* (non-Javadoc)
     * @see org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType#getECSpecNames(org.fosstrak.ale.wsdl.ale.epcglobal.EmptyParms  parms )*
     */
    public org.fosstrak.ale.wsdl.ale.epcglobal.ArrayOfString getECSpecNames(EmptyParms parms) throws ImplementationExceptionResponse , SecurityExceptionResponse    {
    	if (!isInitialized) { initialize(); }
    	ArrayOfString aof = new ArrayOfString();

    	for (String sub : ALE.getECSpecNames()) {
    		aof.getString().add(sub);
    	}
    	return aof;
    }
 }

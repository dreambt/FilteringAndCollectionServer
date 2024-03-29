package org.fosstrak.ale.util;

import org.fosstrak.ale.wsdl.alelr.epcglobal.AddReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.RemoveReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetProperties;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetReaders;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;

/**
 * This class provides some methods to serialize ec specifications and reports.
 */
public class SerializerUtil {

    private final static QName _ECSpec_QNAME = new QName("urn:epcglobal:ale:xsd:1", "ECSpec");
    private final static QName _ECReports_QNAME = new QName("urn:epcglobal:ale:xsd:1", "ECReports");

    private final static QName _LRProperty_QNAME = new QName("urn:epcglobal:ale:xsd:1", "LRProperty");
    private final static QName _LRSpec_QNAME = new QName("urn:epcglobal:ale:xsd:1", "LRSpec");

    /**
     * This method serializes an ec specification to an xml and writes it into a writer.
     *
     * @param ecSpec to serialize
     * @param writer containing the xml
     * @throws IOException if serialization fails
     */
    public static void serializeECSpec(ECSpec ecSpec, FileOutputStream writer) throws IOException {

        serializeECSpec(ecSpec, writer, false);

    }

    /**
     * This method serializes an ec specification to a well formed xml and writes it into a writer.
     *
     * @param ecSpec to serialize
     * @param writer to write the well formed xml into
     * @throws IOException if serialization fails
     */
    public static void serializeECSpecPretty(ECSpec ecSpec, FileOutputStream writer) throws IOException {

        serializeECSpec(ecSpec, writer, true);

    }

    /**
     * This method serializes ec reports to an xml and writes it into a writer.
     *
     * @param ecReports to serialize
     * @param writer    to write the xml into
     * @throws IOException if serialization fails
     */
    public static void serializeECReports(ECReports ecReports, Writer writer) throws IOException {

        serializeECReports(ecReports, writer, false);

    }

    /**
     * This method serializes ec reports to a well formed xml and writes it into a writer.
     *
     * @param ecReports to serialize
     * @param writer    to write the well formed xml into
     * @throws IOException if serialization fails
     */
    public static void serializeECReportsPretty(ECReports ecReports, Writer writer) throws IOException {

        serializeECReports(ecReports, writer, true);

    }

    /**
     * This method serializes an LRSpec to an xml and writes it into a file.
     *
     * @param spec     the LRSpec to be written into a file
     * @param pathName the file where to store
     * @param pretty   flag whether well-formed xml or not
     * @throws IOException whenever an io problem occurs
     */
    public static void serializeLRSpec(LRSpec spec, String pathName, boolean pretty) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.wsdl.ale.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory();
            JAXBElement<LRSpec> thespec = objFactory.createLRSpec(spec);

            // store the file to the file path
            marshaller.marshal(thespec, new FileOutputStream(pathName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method serializes an LRSpec to an xml and writes it into a file.
     *
     * @param spec     the LRSpec to be written into a file
     * @throws IOException whenever an io problem occurs
     */
    public static void serializeLRSpec(LRSpec spec, Writer writer) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.wsdl.ale.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory();
            JAXBElement<LRSpec> thespec = objFactory.createLRSpec(spec);

            // store the file to the file path
            marshaller.marshal(thespec, writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes an SetProperties to xml and stores this xml into a file.
     *
     * @param props    the SetProperties to be serialized.
     * @param pathName the path to the file where to store the xml.
     * @throws IOException if the file cannot be used.
     */
    public static void serializeSetProperties(SetProperties props, String pathName) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.wsdl.alelr.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory();
            JAXBElement<SetProperties> theprops = objFactory.createSetProperties(props);

            // store the file to the file path
            marshaller.marshal(theprops, new FileOutputStream(pathName));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes a RemoveReaders to xml and stores this xml into a file.
     *
     * @param readers  the RemoveReaders to be serialized.
     * @param pathName the path to the file where to store the xml.
     * @throws IOException if the file cannot be used.
     */
    public static void serializeRemoveReaders(RemoveReaders readers, String pathName) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.wsdl.alelr.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory();
            JAXBElement<RemoveReaders> theReaders = objFactory.createRemoveReaders(readers);

            // store the file to the file path
            marshaller.marshal(theReaders, new FileOutputStream(pathName));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes a SetReaders to xml and stores this xml into a file.
     *
     * @param readers  the SetReaders to be serialized.
     * @param pathName the path to the file where to store the xml.
     * @throws IOException if the file cannot be used.
     */
    public static void serializeSetReaders(SetReaders readers, String pathName) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.wsdl.alelr.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory();
            JAXBElement<SetReaders> theReaders = objFactory.createSetReaders(readers);

            // store the file to the file path
            marshaller.marshal(theReaders, new FileOutputStream(pathName));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes an AddReaders to xml and stores this xml into a file.
     *
     * @param readers  the AddReaders to be serialized.
     * @param pathName the path to the file where to store the xml.
     * @throws IOException if the file cannot be used.
     */
    public static void serializeAddReaders(AddReaders readers, String pathName) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.wsdl.alelr.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory();
            JAXBElement<AddReaders> theReaders = objFactory.createAddReaders(readers);

            // store the file to the file path
            marshaller.marshal(theReaders, new FileOutputStream(pathName));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    //
    // private methods
    //

    /**
     * This method serializes an ec specification to an xml and writes it into a writer.
     *
     * @param ecSpec to serialize
     * @param writer to write the xml into
     * @param pretty indicates if the xml should be well formed or not
     * @throws IOException if deserialization fails
     */
    private static void serializeECSpec(ECSpec ecSpec, FileOutputStream writer, boolean pretty) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.xsd.ale.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory();
            JAXBElement<ECSpec> theSpec = objFactory.createECSpec(ecSpec);

            // store the file to the file path
            marshaller.marshal(theSpec, writer);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method serializes en ECSpec to an xml and writes it into a writer.
     *
     * @param ecSpec spec to be serialized.
     * @param writer to writer where to store.
     * @throws IOException if the file cannot be read.
     */
    public static void serializeECSpec(ECSpec ecSpec, Writer writer) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.xsd.ale.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();


            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory();
            JAXBElement<ECSpec> theSpec = objFactory.createECSpec(ecSpec);

            // store the file to the file path
            marshaller.marshal(theSpec, writer);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method serializes ec reports to an xml and writes it into a writer.
     *
     * @param ecReports to serialize
     * @param writer    to write the xml into
     * @param pretty    indicates if the xml should be well formed or not
     * @throws IOException if deserialization fails
     */
    private static void serializeECReports(ECReports ecReports, Writer writer, boolean pretty) throws IOException {
        try {
            String JAXB_CONTEXT = "org.fosstrak.ale.xsd.ale.epcglobal";
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

            org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory objFactory = new org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory();
            JAXBElement<ECReports> theReports = objFactory.createECReports(ecReports);

            // store the file to the file path
            marshaller.marshal(theReports, writer);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
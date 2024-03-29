/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Fosstrak (www.fosstrak.org).
 *
 * Fosstrak is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Fosstrak is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Fosstrak; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.fosstrak.ale.server.readers;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.ALE;
import org.fosstrak.ale.server.readers.gen.LogicalReaders;
import org.fosstrak.ale.server.readers.gen.ObjectFactory;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateNameException;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameException;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.SecurityException;
import org.fosstrak.ale.wsdl.ale.epcglobal.SecurityExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.*;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * represents the LogicalReader API according 10.3 API from EPC global ALE standard.
 *
 * @author sawielan
 * @author haennimi
 */
public class LogicalReaderManager {

    /**
     * logger.
     */
    private static final Logger LOG = Logger.getLogger(LogicalReaderManager.class);

    /**
     * package containing the generated jaxb classes.
     */
    private static final String JAXB_CONTEXT = "org.fosstrak.ale.server.readers.gen";

    /**
     * default path to file which contains the initial logical reader configuration.
     */
    private static final String LOAD_FILEPATH = "/LogicalReaders.xml";

    /**
     * default path to file which contains the current setting of logical readers.
     */
    private static final String STORE_FILEPATH = "/StoreLogicalReaders.xml";

    /**
     * logical reader configuration loaded from file.
     */
    public static LogicalReaders logicalReadersConfiguration;

    /**
     * a map of all LogicalReaders. the readers are mapped against their name.
     */
    private static java.util.Map<String, LogicalReader> logicalReaders = new HashMap<String, LogicalReader>();

    private static final Schema SCHEMA_FILEPATH = null;

    /**
     * indicates if the manager is initialized or not.
     */
    private static boolean initialized = false;

    /**
     * returns the vendor version of the ale (see 10.3 API).
     *
     * @return vendor version of the ale
     * @throws ImplementationException whenever something goes wrong inside the implementation
     */
    public static String getVendorVersion() throws ImplementationExceptionResponse {
        return ALE.getVendorVersion();
    }

    /**
     * returns the standard version of the ale (see 10.3 API).
     *
     * @return standard version of the ale
     * @throws ImplementationException whenever something goes wrong inside the implementation
     */
    public static String getStandardVersion() throws ImplementationExceptionResponse {
        return ALE.getStandardVersion();
    }

    /**
     * returns the current value of the specified property for reader name (see 10.3 API).
     *
     * @param name         the reader the property value is requested
     * @param propertyName the property that for the value is requested
     * @return returns a value for a requested property
     * @throws NoSuchNameExceptionResponse     whenever the specified name is not defined in the logicalReader API
     * @throws SecurityExceptionResponse       the operation was not permitted due to access restrictions
     * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation
     */
    public static String getPropertyValue(String name, String propertyName) throws NoSuchNameExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {
        LogicalReader logRd = logicalReaders.get(name);
        List<LRProperty> propList = logRd.getProperties();
        Iterator iterator = propList.iterator();
        while (iterator.hasNext()) {
            LRProperty prop = (LRProperty) iterator.next();
            if (prop.getName().equalsIgnoreCase(propertyName)) {
                return prop.getValue();
            }
        }
        return null;
    }

    /**
     * changes properties for the reader name (see 10.3 API).
     *
     * @param name       name of the reader to change
     * @param properties new properties for the reader
     * @throws NoSuchNameException      whenever the specified name is not defined in the logicalReader API
     * @throws InUseException           Is thrown when you try to undefine a Reader that is still referenced by EC or CC
     * @throws ImmutableReaderException whenever you want to change a immutable reader
     * @throws SecurityException        the operation was not permitted due to access restrictions
     * @throws ImplementationException  whenever something goes wrong inside the implementation
     * @throws ValidationException      the provided LRSpec is invalid
     */
    public static void setProperties(String name, List<LRProperty> properties) throws NoSuchNameExceptionResponse, ValidationExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {
        LogicalReader logRd = logicalReaders.get(name);
        LRSpec spec = logRd.getLRSpec();
        if (spec.getProperties() == null) {
            spec.setProperties(new LRSpec.Properties());
        }
        spec.getProperties().getProperty().addAll(properties);
        LOG.debug("set the properties");
        try {
            update(name, spec);
        } catch (ReaderLoopExceptionResponse e) {
            throw new ImplementationExceptionResponse("reader loop detected");
        }
        // TODO what is table below?
    }

    /**
     * removes the specified logical readers from the components of the composite reader (see 10.3 API).
     *
     * @param name    name of the composite reader
     * @param readers list of readers to be removed
     * @throws NoSuchNameException         whenever the specified name is not defined in the logicalReader API
     * @throws InUseException              Is thrown when you try to undefine a Reader that is still referenced by EC or CC
     * @throws ImmutableReaderException    whenever you want to change a immutable reader
     * @throws SecurityException           the operation was not permitted due to access restrictions
     * @throws ImplementationException     whenever something goes wrong inside the implementation
     * @throws NonCompositeReaderException addReader or setReader or removeReader was called on a non compositeReader
     */
    public static void removeReaders(String name, java.util.List<String> readers) throws NoSuchNameExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, NonCompositeReaderExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {
        LogicalReader lgRd = logicalReaders.get(name);
        if (!(lgRd instanceof CompositeReader)) {
            throw new NonCompositeReaderExceptionResponse("reader " + name + " is not composite");
        }
        // get the readers that are still in the spec
        LRSpec spec = lgRd.getLRSpec();
        List<String> res = new ArrayList<String>();
        if ((spec.getReaders() != null) && (spec.getReaders().getReader().size() > 0)) {
            for (String reader : spec.getReaders().getReader()) {
                if (!readers.contains(reader)) {
                    res.add(reader);
                }
            }
        }

        // add the resulting readers
        spec.setReaders(new LRSpec.Readers());
        spec.getReaders().getReader().addAll(res);
        lgRd.update(spec);
    }

    /**
     * changes the list of readers in a composite reader (see 10.3 API).
     *
     * @param name    name of the composite reader
     * @param readers list of readers to be changed
     * @throws NoSuchNameException         whenever the specified name is not defined in the logicalReader API
     * @throws ValidationException         the provided LRSpec is invalid
     * @throws InUseException              Is thrown when you try to undefine a Reader that is still referenced by EC or CC
     * @throws ImmutableReaderException    whenever you want to change a immutable reader
     * @throws ReaderLoopException         the reader would include itself which would result in a loop
     * @throws SecurityException           the operation was not permitted due to access restrictions
     * @throws ImplementationException     whenever something goes wrong inside the implementation
     * @throws NonCompositeReaderException addReader or setReader was called on a non compositeReader
     */
    public static void setReaders(String name, java.util.List<String> readers) throws NoSuchNameExceptionResponse, ValidationExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, NonCompositeReaderExceptionResponse, ReaderLoopExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {
        LogicalReader logRd = logicalReaders.get(name);
        if (!(logRd instanceof CompositeReader)) {
            throw new NonCompositeReaderExceptionResponse("reader " + name + " is not composite");
        }
        LRSpec spec = logRd.getLRSpec();
        spec.setReaders(new LRSpec.Readers());
        spec.getReaders().getReader().addAll(readers);
        update(name, spec);
    }

    /**
     * adds the specified logical readers to a composite reader (see 10.3 API).
     *
     * @param name    name of the composite reader
     * @param readers list of readers to be added to the composite reader
     * @throws NoSuchNameException      whenever the specified name is not defined in the logicalReader API
     * @throws ValidationException      the provided LRSpec is invalid
     * @throws InUseException           Is thrown when you try to undefine a Reader that is still referenced by EC or CC
     * @throws ImmutableReaderException whenever you want to change a immutable reader
     * @throws ReaderLoopException      the reader would include itself which would result in a loop
     * @throws SecurityException        the operation was not permitted due to access restrictions
     * @throws ImplementationException  whenever something goes wrong inside the implementation
     */
    public static void addReaders(String name, java.util.List<String> readers) throws NoSuchNameExceptionResponse, ValidationExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, ReaderLoopExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {
        LogicalReader logRd = logicalReaders.get(name);
        LRSpec spec = logRd.getLRSpec();
        if (spec.getReaders() == null) {
            spec.setReaders(new LRSpec.Readers());
        }
        for (String reader : readers) {
            if (!spec.getReaders().getReader().contains(reader)) {
                spec.getReaders().getReader().add(reader);
            }
        }
        update(name, spec);
    }

    /**
     * returns an LRSpec that describes a logical reader called name (see 10.3 API).
     *
     * @param name name of the logical reader
     * @return LRSpec for the logical reader name
     * @throws NoSuchNameException     whenever the specified name is not defined in the logicalReader API
     * @throws SecurityException       the operation was not permitted due to access restrictions
     * @throws ImplementationException whenever something goes wrong inside the implementation
     */
    public static LRSpec getLRSpec(String name) throws NoSuchNameExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {
        LogicalReader logRd = logicalReaders.get(name);
        return logRd.getLRSpec();
    }

    /**
     * returns a list of the logical readers in the reader (see 10.3 API).
     *
     * @return list of String containing the logicalReaders
     * @throws SecurityException       the operation was not permitted due to access restrictions
     * @throws ImplementationException whenever something goes wrong inside the implementation
     */
    public static java.util.List<String> getLogicalReaderNames() throws SecurityExceptionResponse, ImplementationExceptionResponse {
        List<String> rdNames = new ArrayList<String>();
        Iterable<String> it = logicalReaders.keySet();
        for (String reader : it) {
            rdNames.add(reader);
        }
        return rdNames;
    }

    /**
     * .
     * removes the logicalReader name (see 10.3 API).
     *
     * @param name name for the logical reader to be undefined
     * @throws NoSuchNameException      whenever the specified name is not defined in the logicalReader API
     * @throws InUseException           Is thrown when you try to undefine a Reader that is still referenced by EC or CC
     * @throws SecurityException        the operation was not permitted due to access restrictions
     * @throws ImmutableReaderException whenever you want to change a immutable reader
     * @throws ImplementationException  whenever an internal error occurs
     */
    public static void undefine(String name) throws NoSuchNameExceptionResponse, InUseExceptionResponse, SecurityExceptionResponse, ImmutableReaderExceptionResponse, ImplementationExceptionResponse {
        // the logicalReader must delete himself from its observables
        LOG.debug("undefining reader " + name);
        LogicalReader reader = LogicalReaderManager.getLogicalReader(name);

        // according to the EPC standard a reader cannot be undefined when there is
        // an active CC or EC pointing to the reader
        // this raises an InUseException
        if (reader.countObservers() > 0) {
            throw new InUseExceptionResponse();
        }

        if (reader instanceof CompositeReader) {
            CompositeReader composite = (CompositeReader) reader;
            composite.unregisterAsObserver();
        } else if (reader instanceof BaseReader) {
            BaseReader basereader = (BaseReader) reader;
            basereader.disconnectReader();
            basereader.cleanup();
        }
        logicalReaders.remove(name);
    }

    /**
     * Changes the definition of the logical reader named name to
     * match the specification in the spec parameter. This is
     * different than calling undefine followed by define, because
     * update may be called even if there are defined ECSpecs,
     * CCSpecs, or other logical readers that refer to this
     * logical reader.
     *
     * @param name a valid name for the reader to be changed.
     * @param spec an LRSpec describing the changes to the reader
     * @throws ImmutableReaderException whenever you want to change a immutable reader
     * @throws ValidationException      the provided LRSpec is invalid
     * @throws InUseException           Is thrown when you try to undefine a Reader that is still referenced by EC or CC
     * @throws ReaderLoopException      the reader would include itself which would result in a loop
     * @throws SecurityException        the operation was not permitted due to access restrictions
     * @throws ImplementationException  whenever something goes wrong inside the implementation
     * @throws NoSuchNameException      whenever the specified name is not defined in the logicalReader API
     */
    public static void update(String name, LRSpec spec) throws NoSuchNameExceptionResponse, ValidationExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, ReaderLoopExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {
        LogicalReader logRd = logicalReaders.get(name);
        logRd.update(spec);
    }

    /**
     * creates a new logical Reader according to spec (see 10.3 API). this variant works on jaxb LRSpec
     *
     * @param name name of the new logicalReader
     * @param spec LRSpec how to build the reader
     * @throws DuplicateNameException  when a reader name is already defined
     * @throws ValidationException     the provided LRSpec is invalid
     * @throws SecurityException       the operation was not permitted due to access restrictions
     * @throws ImplementationException whenever something goes wrong inside the implementation
     */
    public static void define(String name, org.fosstrak.ale.server.readers.gen.LRSpec spec) throws DuplicateNameExceptionResponse, ValidationExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {

        LRSpec thespec = new LRSpec();

        // add the readers
        thespec.setReaders(new LRSpec.Readers());
        if (spec.getReaders() != null) {
            thespec.getReaders().getReader().addAll(spec.getReaders());
        }

        // set if composite reader or basereader
        thespec.setIsComposite(spec.isIsComposite());

        // set the properties
        thespec.setProperties(new LRSpec.Properties());
        for (org.fosstrak.ale.server.readers.gen.LRProperty prop : spec.getLRProperty()) {
            LRProperty property = new LRProperty();
            property.setName(prop.getName());
            property.setValue(prop.getValue());
            thespec.getProperties().getProperty().add(property);
        }

        // at the ReaderType property
        LRProperty property = new LRProperty();
        property.setName("ReaderType");
        property.setValue(spec.getReaderType());
        thespec.getProperties().getProperty().add(property);

        define(name, thespec);
    }

    /**
     * creates a new logical Reader according to spec (see 10.3 API). this variant works directly on LRSpec
     *
     * @param name name of the new logicalReader
     * @param spec LRSpec how to build the reader
     * @throws DuplicateNameException  when a reader name is already defined
     * @throws ValidationException     the provided LRSpec is invalid
     * @throws SecurityException       the operation was not permitted due to access restrictions
     * @throws ImplementationException whenever something goes wrong inside the implementation
     */
    public static void define(String name, LRSpec spec) throws DuplicateNameExceptionResponse, ValidationExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse {
        LogicalReader logRead = LogicalReader.createReader(name, spec);
        // establish connection when basereader
        if (logRead instanceof BaseReader) {
            ((BaseReader) logRead).connectReader();
        }
        logicalReaders.put(name, logRead);
    }


    /**
     * This method initializes the manager by loading the default definition from file.
     *
     * @throws ImplementationException whenever something goes wrong inside the implementation
     * @throws SecurityException       the operation was not permitted due to access restrictions
     * @throws DuplicateNameException  when a reader name is already defined
     * @throws ValidationException     the provided LRSpec is invalid
     */
    public static void initialize() throws ImplementationExceptionResponse, SecurityExceptionResponse, DuplicateNameExceptionResponse, ValidationExceptionResponse {

        initializeFromFile(LOAD_FILEPATH);

    }

    /**
     * This method initializes the manager by loading the definition from the specified file
     * and creating corresponding logical reader stubs.
     *
     * @param loadFilePath to initialize
     * @throws ImplementationException whenever something goes wrong inside the implementation
     * @throws SecurityException       the operation was not permitted due to access restrictions
     * @throws DuplicateNameException  when a reader name is already defined
     * @throws ValidationException     the provided LRSpec is invalid
     */
    public static void initializeFromFile(String loadFilePath) throws ImplementationExceptionResponse, SecurityExceptionResponse, DuplicateNameExceptionResponse, ValidationExceptionResponse {

        LOG.debug("Initialize LogicalReaderManager");

        // if configuration file path is not set, set it to default value
        if (loadFilePath == null) {
            loadFilePath = LOAD_FILEPATH;
        }

        // try to parse reader configuration file
        LOG.debug("Parse configuration file :" + loadFilePath);
        List<org.fosstrak.ale.server.readers.gen.LogicalReader> genLogicalReaders;
        try {
            // initialize jaxb context and unmarshaller
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());

            // unmarshal logical reader configuration file
            //InputStream inputStream = LogicalReaderManager.class.getResourceAsStream(loadFilePath);
            logicalReadersConfiguration = (LogicalReaders) unmarshaller.unmarshal(LogicalReaderManager.class.getResourceAsStream(loadFilePath));
            // trying to validate schema

            unmarshaller.setSchema(SCHEMA_FILEPATH);
            boolean isValidating = unmarshaller.getSchema() != null;

            genLogicalReaders = logicalReadersConfiguration.getLogicalReader();
        } catch (JAXBException e) {
            e.printStackTrace();
            return;
        }

        // iterate over logical readers
        for (org.fosstrak.ale.server.readers.gen.LogicalReader logicalReader : genLogicalReaders) {

            // get logical reader name
            String logName = "";
            logName = logicalReader.getName();
            org.fosstrak.ale.server.readers.gen.LRSpec spec = logicalReader.getLRSpec();
            define(logName, spec);
        }

        // set initialized to true
        initialized = true;
        LOG.debug("LogicalReaderManager successfully initialized");

        LOG.debug("starting the readers");
        for (LogicalReader reader : LogicalReaderManager.getLogicalReaders()) {
            reader.start();
        }
    }

    /**
     * This method stores the current setting of logicalreaders to a .xml file.
     *
     * @param storeFilePath configurationFilePath to initialize
     * @throws ImplementationException whenever something goes wrong inside the implementation
     * @throws SecurityException       the operation was not permitted due to access restrictions
     * @throws DuplicateNameException  when a reader name is already defined
     * @throws ValidationException     the provided LRSpec is invalid
     * @throws FileNotFoundException   the provided file was not found
     */
    public static void storeToFile(String storeFilePath) throws ImplementationExceptionResponse, SecurityExceptionResponse, DuplicateNameExceptionResponse, ValidationExceptionResponse, FileNotFoundException {

        LOG.debug("Store LogicalReaderManager");

        // if store file path is not set, set it to default value
        if (storeFilePath == null) {
            storeFilePath = STORE_FILEPATH;
        }

        // try to generate store file
        LOG.debug("Generate store file");

        try {
            // initialize jaxb context and marshaller
            JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
            ObjectFactory objFactory = new ObjectFactory();

            LogicalReaders genLogReaders = (LogicalReaders) objFactory.createLogicalReaders();
            Iterable<String> names = logicalReaders.keySet();
            for (String name : names) {
                LogicalReader logRd = logicalReaders.get(name);
                LRSpec spec = logRd.getLRSpec();
                org.fosstrak.ale.server.readers.gen.LogicalReader genLogRd = objFactory.createLogicalReader();
                genLogRd.setName(logRd.getName());
                genLogRd.setName(name);
                org.fosstrak.ale.server.readers.gen.LRSpec genSpec = objFactory.createLRSpec();
                genSpec.setIsComposite(spec.isIsComposite());
                if (genSpec.isIsComposite()) {
                    if (spec.getReaders() != null) {
                        Iterator<String> it = spec.getReaders().getReader().iterator();
                        while (it.hasNext()) {
                            genSpec.getReaders().add(it.next());
                        }
                    }
                } else {
                    Iterator<LRProperty> it = logRd.getProperties().iterator();
                    while (it.hasNext()) {
                        org.fosstrak.ale.server.readers.gen.LRProperty genProp = objFactory.createLRProperty();
                        genProp.setName(it.next().getName());
                        genProp.setValue(it.next().getValue());
                        genSpec.getLRProperty().add(genProp);
                    }
                    genLogRd.setLRSpec(genSpec);
                    genLogReaders.getLogicalReader().add(genLogRd);
                }
            }
            // store the file to the file path
            marshaller.marshal(genLogReaders, new FileOutputStream(storeFilePath));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        LOG.info("LogicalReaderManager successfully stored");
    }


    /**
     * returns the requested logicalReader.
     *
     * @param readerName name of the requested reader
     * @return a logicalReder
     */
    public static LogicalReader getLogicalReader(String readerName) {
        LogicalReader reader = null;
        if (LogicalReaderManager.logicalReaders.containsKey(readerName)) {
            reader = LogicalReaderManager.logicalReaders.get(readerName);

            if (!reader.isStarted()) {
                reader.start();
            }
        }
        return reader;
    }

    /**
     * returns all available logicalReaders.
     *
     * @return Set of LogicalReader
     */
    public static Collection<LogicalReader> getLogicalReaders() {
        return LogicalReaderManager.logicalReaders.values();
    }

    /**
     * @param reader a logicalReader to be stored in the manager
     * @throws ImplementationException whenever something goes wrong inside the implementation
     */
    public static void setLogicalReader(LogicalReader reader) throws ImplementationExceptionResponse {
        if (LogicalReaderManager.logicalReaders.containsKey(reader.getName())) {
            throw new ImplementationExceptionResponse();    //	"reader duplicated");
        }

        LogicalReaderManager.logicalReaders.put(reader.getName(), reader);
    }

    /**
     * This method indicates if the manager contains a logical reader with specified name.
     *
     * @param logicalReaderName to search
     * @return true if the logical reader exists and false otherwise
     */
    public static boolean contains(String logicalReaderName) {

        // initialize if necessary
        try {
            if (!initialized) {
                initialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logicalReaders.containsKey(logicalReaderName);
    }

    public static boolean isInitialized() {
        return initialized;
    }

}

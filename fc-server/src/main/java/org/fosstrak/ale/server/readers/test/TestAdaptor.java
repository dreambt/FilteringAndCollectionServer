package org.fosstrak.ale.server.readers.test;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.readers.BaseReader;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.fosstrak.hal.HardwareException;
import org.fosstrak.hal.Observation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * test adaptor for the filtering and collection triggering tag in regular time intervals.
 *
 * @author sawielan
 */
public class TestAdaptor extends BaseReader {

    /**
     * logger.
     */
    private static final Logger log = Logger.getLogger(TestAdaptor.class);

    private Thread thread = null;

    private class TestAdaptorRunnable implements Runnable {

        private TestAdaptor tb = null;

        public TestAdaptorRunnable(TestAdaptor target) {
            tb = target;
        }

        public void run() {
            long tps = Long.parseLong(tb.logicalReaderProperties.get("tps"));
            long wt = Long.parseLong(tb.logicalReaderProperties.get("wt"));
            long gain = Long.parseLong(tb.logicalReaderProperties.get("gain"));
            TestAdaptor.log.debug(String.format("starting tagbomb\ntps: %s\nwt: %s\ngain: %s\n", tps, wt, gain));
            String readerName = tb.getName();

            long i = 0;
            while (true) {
                TestAdaptor.log.debug("firing " + tps + " tags");
                // generate tags
                LinkedList<Tag> tags = new LinkedList<Tag>();
                for (long j = 0; j < tps; j++) {
                    tags.add(new Tag(readerName, null, readerName + i, System.currentTimeMillis()));
                    i++;
                }

                tb.addTags(tags);

                try {
                    Thread.sleep(wt);
                } catch (InterruptedException e) {
                    // we have been interrupted so stop the thread
                    TestAdaptor.log.debug("we got an interrupt, so stop the tag bomb.");
                    return;
                }
                tps += gain;
            }
        }

    }

    public TestAdaptor() {
        super();
    }

    @Override
    public void initialize(String name, LRSpec spec) throws ImplementationExceptionResponse {
        super.initialize(name, spec);
    }

    @Override
    public void addTag(Tag tag) {
        tag.addTrace(getName());

        setChanged();
        notifyObservers(tag);
    }

    @Override
    public void addTags(List<Tag> tags) {
        setChanged();
        for (Tag tag : tags) {
            tag.addTrace(getName());
        }
        notifyObservers(tags);
    }

    @Override
    public void connectReader() {
        setConnected();
    }

    @Override
    public void disconnectReader() {
        stop();
        setDisconnected();
    }

    @Override
    public void start() {
        if (thread != null) {
            stop();
        }
        thread = new Thread(new TestAdaptorRunnable(this));
        thread.start();
        setStarted();
    }

    @Override
    public void stop() {
        if (thread != null) {
            thread.interrupt();
        }
        thread = null;
        setStopped();
    }

    @Override
    public void update(LRSpec spec) {
        boolean started = isStarted();

        disconnectReader();
        logicalReaderSpec = spec;
        logicalReaderProperties = new HashMap<String, String>();
        properties = new LinkedList<LRProperty>();

        if (spec.getProperties() != null) {
            for (LRProperty prop : spec.getProperties().getProperty()) {
                logicalReaderProperties.put(prop.getName(), prop.getValue());
                properties.add(prop);
            }
        }
        connectReader();
        if (started) {
            start();
        }
    }

    @Override
    public Observation[] identify(String[] readPointNames)
            throws HardwareException {
        return null;
    }
}

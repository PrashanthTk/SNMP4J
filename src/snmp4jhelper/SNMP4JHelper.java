package SNMP4JHelper;

import java.io.IOException;

import org.snmp4j.smi.OID;

import SNMP4JHelper.SNMPManager;

public class SNMP4JHelper {

	static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");

	public static void main(String[] args) throws IOException {
		SNMP4JHelper client = new SNMP4JHelper("udp:127.0.0.1/161");
		client.init();
	}
        
	SNMPAgent agent = null;
	/**
	 * This is the client which we have created earlier
	 */
	SNMPManager client = null;

	String address = null;

	/**
	 * Constructor
	 *
	 * @param add
	 */
	public SNMP4JHelper(String add) {
		address = add;
	}

	private void init() throws IOException {
		agent = new SNMPAgent("0.0.0.0/2001");
		agent.start();

		// Since BaseAgent registers some MIBs by default we need to unregister
		// one before we register our own sysDescr. Normally you would
		// override that method and register the MIBs that you need
		agent.unregisterManagedObject(agent.getSnmpv2MIB());

		// Register a system description, use one from you product environment
		// to test with
		agent.registerManagedObject(MOCreator.createReadOnly(sysDescr,
				"Data / Object sent by Agent"));

		// Setup the client to use our newly started agent
		client = new SNMPManager("udp:127.0.0.1/2001");
		client.start();
		// Get back Value which is set
		System.out.println(client.getAsString(sysDescr));
	}

}
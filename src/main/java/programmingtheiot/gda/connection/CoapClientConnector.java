/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.connection;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.config.CoapConfig;
import org.eclipse.californium.elements.config.UdpConfig;
import org.eclipse.californium.elements.exception.ConnectorException;

import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;

import programmingtheiot.data.DataUtil;

import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;

import programmingtheiot.data.DataUtil;

/**
 * Shell representation of class for student implementation.
 *
 */
public class CoapClientConnector implements IRequestResponseClient
{
	// static
	
	private static final Logger _Logger =
		Logger.getLogger(CoapClientConnector.class.getName());
	
	// params
	private String     protocol;
	private String     host;
	private int        port;
	private String     serverAddr;
	private CoapClient clientConn;
	private IDataMessageListener dataMsgListener;
	
	
	// constructors
	
	/**
	 * Default.
	 * 
	 * All config data will be loaded from the config file.
	 */
	public CoapClientConnector()
	{
		ConfigUtil config = ConfigUtil.getInstance();
		this.host = config.getProperty(ConfigConst.COAP_GATEWAY_SERVICE, ConfigConst.HOST_KEY, ConfigConst.DEFAULT_HOST);

		if (config.getBoolean(ConfigConst.COAP_GATEWAY_SERVICE, ConfigConst.ENABLE_CRYPT_KEY)) {
			this.protocol = ConfigConst.DEFAULT_COAP_SECURE_PROTOCOL;
			this.port     = config.getInteger(ConfigConst.COAP_GATEWAY_SERVICE, ConfigConst.SECURE_PORT_KEY, ConfigConst.DEFAULT_COAP_SECURE_PORT);
		} else {
			this.protocol = ConfigConst.DEFAULT_COAP_PROTOCOL;
			this.port     = config.getInteger(ConfigConst.COAP_GATEWAY_SERVICE, ConfigConst.PORT_KEY, ConfigConst.DEFAULT_COAP_PORT);
		}
		// NOTE: URL does not have a protocol handler for "coap",
		// so we need to construct the URL manually
		this.serverAddr = this.protocol + "://" + this.host + ":" + this.port;

		initClient();

		_Logger.info("Using URL for server conn: " + this.serverAddr);
	}
		
	private void initClient() {
		// TODO Auto-generated method stub
		try {
			this.clientConn = new CoapClient(this.serverAddr);
			
			_Logger.info("Created client connection to server / resource: " + this.serverAddr);
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to connect to broker: " + (this.clientConn != null ? this.clientConn.getURI() : this.serverAddr), e);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param host
	 * @param isSecure
	 * @param enableConfirmedMsgs
	 */
	public CoapClientConnector(String host, boolean isSecure, boolean enableConfirmedMsgs)
	{
	}
	
	
	// public methods
	
	@Override
	public boolean sendDiscoveryRequest(int timeout)
	{
		Set<WebLink> wlSet;
		try {
			wlSet = this.clientConn.discover();
			
			if (wlSet != null) {
			    for (WebLink wl : wlSet) {
			        _Logger.info(" --> URI: " + wl.getURI() + ". Attributes: " + wl.getAttributes());
			    }
			}
		} catch (ConnectorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return true;
	}
	
	
	
	@Override
	public boolean sendDeleteRequest(ResourceNameEnum resource, String name, boolean enableCON, int timeout)
	{
		return false;
	}

	
	
	
	
	
	
	@Override
	public boolean sendGetRequest(ResourceNameEnum resource, String name, boolean enableCON, int timeout)
	{
		CoapResponse response = null;

		if (enableCON) {
			this.clientConn.useCONs();
		} else {
			this.clientConn.useNONs();
		}

		this.clientConn.setURI(this.serverAddr + "/" + resource.getResourceName());

		// TODO: determine which MediaTypeRegistry const should be used for this call
		try {
			response = this.clientConn.get();
		} catch (ConnectorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (response != null) {
			// TODO: implement your logic here
			
			_Logger.info("Handling POST. Response: " + response.isSuccess() + " - " + response.getOptions() + " - " +
				response.getCode() + " - " + response.getResponseText());
			
			if (this.dataMsgListener != null) {
				// TODO: implement this
			}
			
			return true;
		} else {
			_Logger.warning("Handling POST. No response received.");
		}

		return false;
	}

	
	
	
	
	
	
	
	@Override
	public boolean sendPostRequest(ResourceNameEnum resource, String name, boolean enableCON, String payload, int timeout)
	{
		return false;
	}

	@Override
	public boolean sendPutRequest(ResourceNameEnum resource, String name, boolean enableCON, String payload, int timeout)
	{
		return false;
	}

	@Override
	public boolean setDataMessageListener(IDataMessageListener listener)
	{
		return false;
	}

	public void clearEndpointPath()
	{
	}
	
	public void setEndpointPath(ResourceNameEnum resource)
	{
	}
	
	@Override
	public boolean startObserver(ResourceNameEnum resource, String name, int ttl)
	{
		return false;
	}

	@Override
	public boolean stopObserver(ResourceNameEnum resourceType, String name, int timeout)
	{
		return false;
	}

	
	// private methods
	
}













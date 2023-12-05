package programmingtheiot.gda.connection;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

import programmingtheiot.common.ResourceNameEnum;

public class SensorDataObserverHandler implements CoapHandler
{
	private static final Logger _Logger =
		Logger.getLogger(SensorDataObserverHandler.class.getName());
	private Object clientConn;
	private Object dataMsgListener;
	
	public SensorDataObserverHandler ()
	{
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.californium.core.CoapHandler#onError()
	 */
	public void onError()
	{
		_Logger.warning("Handling CoAP error...");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.californium.core.CoapHandler#onLoad(org.eclipse.californium.core.CoapResponse)
	 */
	public void onLoad1(CoapResponse response)
	{
		_Logger.info("Received CoAP response (payload should be SensorData in JSON): " + response.getResponseText());
	}

	@Override
	public void onLoad(CoapResponse response) {
		// TODO Auto-generated method stub
		
	}
	public boolean startObserver(ResourceNameEnum resource, String name, int ttl)
	{
		String uriPath = createUriPath(resource, name);
		
		_Logger.info("Observing resource [START]: " + uriPath);
		
		((Object) this.clientConn).setURI(uriPath);
		
		// TODO: Check the resource type:
		//   - If it references SensorData, create the SensorDataObserverHandler
		//   - If it references SystemPerformanceData, create the SystemPerformanceDataObserverHandler
		SensorDataObserverHandler handler = new SensorDataObserverHandler();
		handler.setDataMessageListener(this.dataMsgListener);
		
		CoapObserveRelation cor = ((Object) this.clientConn).observe(handler);
		
		// TODO: store a reference to the relation instance and map it to the resource under observation,
		// as it will be needed if the caller wants to cancel the observation at a later time
		
		return (! cor.isCanceled());
	}

	private void setDataMessageListener(Object dataMsgListener2) {
		// TODO Auto-generated method stub
		
	}

	private String createUriPath(ResourceNameEnum resource, String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
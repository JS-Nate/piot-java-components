package programmingtheiot.gda.connection.handlers;

import org.eclipse.californium.core.server.resources.CoapExchange;

import programmingtheiot.common.IDataMessageListener;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.DataUtil;
import programmingtheiot.data.SensorData;


import java.util.logging.Logger; // Import the Logger class




public class UpdateTelemetryResourceHandler {
	private static final Logger _Logger =
	        Logger.getLogger(UpdateTelemetryResourceHandler.class.getName());

    private IDataMessageListener dataMsgListener = null;
    private String resourceName;


    public UpdateTelemetryResourceHandler(String resourceName) {
    	super();
        this.resourceName = resourceName;;
    }

    public void setDataMessageListener(IDataMessageListener listener) {
        if (listener != null) {
            this.dataMsgListener = listener;
        }
    }

    public void handlePUT(CoapExchange context) {
        ResponseCode code = ResponseCode.NOT_ACCEPTABLE;

        context.accept();

        if (this.dataMsgListener != null) {
            try {
                String jsonData = new String(context.getRequestPayload());

                SensorData sensorData =
                        DataUtil.getInstance().jsonToSensorData(jsonData);

                // Assuming CDA_SENSOR_MSG_RESOURCE is the correct ResourceNameEnum
                this.dataMsgListener.handleSensorMessage(
                        ResourceNameEnum.CDA_SENSOR_MSG_RESOURCE, sensorData);

                code = ResponseCode.CHANGED;
            } catch (Exception e) {
                // Assuming _Logger is a logger instance available in your class
                _Logger.warning(
                        "Failed to handle PUT request. Message: " +
                                e.getMessage());

                code = ResponseCode.BAD_REQUEST;
            }
        } else {
            // Assuming _Logger is a logger instance available in your class
            _Logger.info(
                    "No callback listener for request. Ignoring PUT.");

            code = ResponseCode.CONTINUE;
        }
        
        

        String msg = "Update telemetry data request handled: " + this.resourceName;

        context.respond(code, msg);
        
        
        
    }
}



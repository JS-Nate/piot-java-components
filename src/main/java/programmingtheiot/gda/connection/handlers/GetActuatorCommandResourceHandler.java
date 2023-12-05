package programmingtheiot.gda.connection.handlers;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

import programmingtheiot.data.DataUtil;
import programmingtheiot.common.IActuatorDataListener;
import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.ActuatorData;

public class GetActuatorCommandResourceHandler extends CoapResource implements IActuatorDataListener {

    // static
    private static final Logger _Logger = Logger.getLogger(GetActuatorCommandResourceHandler.class.getName());

    // params
    private ActuatorData actuatorData = null;

    // constructors
    public GetActuatorCommandResourceHandler(String resourceName) {
        super(resourceName);

        // set the resource to be observable
        super.setObservable(true);
    }

    @Override
    public boolean onActuatorDataUpdate(ActuatorData data) {
        if (data != null && this.actuatorData != null) {
            this.actuatorData.updateData(data);

            // notify all connected clients
            super.changed();

            _Logger.fine("Actuator data updated for URI: " + super.getURI() + ": Data value = "
                    + this.actuatorData.getValue());

            return true;
        }

        return false;
    }

    @Override
    public void handleGET(CoapExchange context) {
        // accept the request
        context.accept();

        // convert the locally stored ActuatorData to JSON using DataUtil
        String jsonData = DataUtil.getInstance().actuatorDataToJson(this.actuatorData);

        // generate a response message, set the content type, and set the response code
        context.respond(ResponseCode.CONTENT, jsonData);
    }
}

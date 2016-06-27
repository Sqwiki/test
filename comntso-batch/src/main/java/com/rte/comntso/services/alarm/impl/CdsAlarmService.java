package com.rte.comntso.services.alarm.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleInputRegister;

/**
 * The Class CdsAlarmService.
 */
public class CdsAlarmService {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(CdsAlarmService.class);

	/**
	 * Cds alarm.
	 *
	 * @param address
	 *            the address
	 * @param port
	 *            the port
	 * @param register
	 *            the register
	 * @param value
	 *            the value
	 */
	public static void cdsAlarm(InetAddress address, int port, int register, int value) {
		Exception error = null;
		TCPMasterConnection connection = null;
		logger.info(
				"Trigger alarm " + address + ":" + port + " write register : " + register + " with value : " + value);
		try {
			connection = new TCPMasterConnection(address);
			connection.setPort(port);
			connection.setTimeout(10 * 1000);
			connection.connect();

			Register reg = new SimpleInputRegister(register);
			WriteSingleRegisterRequest request = new WriteSingleRegisterRequest(value, reg);

			logger.info("alarm request : " + request.getHexMessage());

			ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
			transaction.setRequest(request);

			transaction.execute();

			WriteSingleRegisterResponse response = (WriteSingleRegisterResponse) transaction.getResponse();
			logger.info("alarm response : " + response.getHexMessage());
		} catch (UnknownHostException e) {
			error = e;
		} catch (Exception e) {
			error = e;
		} finally {
			if (connection != null && connection.isConnected()) {
				connection.close();
			}
			if (error != null) {
				logger.error("Une erreur est survenue lors du déclenchement de l'alarme.", error);
			}
		}
	}

}

package com.rte.comntso;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.rte.comntso.services.alarm.impl.CdsAlarmService;

/**
 * The Class TriggerCDSAlarm.
 */
public class TriggerCDSAlarm {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(TriggerCDSAlarm.class);

	/** The Constant PORT. */
	private static final String PORT = "port";

	/** The Constant ADDRESS. */
	private static final String ADDRESS = "address";

	/** The Constant CONFIG. */
	private static final String CONFIG = "config";

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		try {
			if (args != null && 2 == args.length) {
				// Recuperation des donnees de configuration
				ResourceBundle bundle = ResourceBundle.getBundle(CONFIG);
				InetAddress address = InetAddress.getByName(bundle.getString(ADDRESS));
				int port = Integer.parseInt(bundle.getString(PORT));
				CdsAlarmService.cdsAlarm(address, port, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			} else {
				throw new UnknownHostException("Erreur sur les arguments en entrée : Nombre d'arguments en erreur");
			}
		} catch (UnknownHostException e) {
			logger.error("Une erreur est survenue lors du déclenchement de l'alarme.", e);
		} catch (NumberFormatException e) {
			logger.error("Erreur sur les arguments en entrée", e);
		}

	}

}

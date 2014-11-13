/**
 * 
 */
package de.quaddy_services.ptc.enterprise;

import java.awt.EventQueue;

import de.quaddy_services.ptc.DisplayHelper;
import de.quaddy_services.ptc.MainController;
import de.quaddy_services.ptc.log.Log;

class EnterpriseUtilSaveReportThread extends Thread {
	/**
	 * 
	 */
	private final String serverName;
	private final StringBuilder tempReport;
	private final MainController controller;
	private final Log LOG = new Log(this.getClass());
	private Object result;
	private Runnable resultRunnable = new Runnable() {
		// Ensure it's loader on sucessfull execution to show errors
		// when Server is down on next call.
		public void run() {
			if (result instanceof Throwable) {
				DisplayHelper.displayException(controller.getFrame(), (Throwable) result);
			} else {
				DisplayHelper.displayText(controller.getFrame(), "Tasks saved.", "Tasks saved to booking system.\n"
						+ result, true);

			}
		};
	};

	EnterpriseUtilSaveReportThread(String aServerName, StringBuilder aTempReport, MainController aController) {
		serverName = aServerName;
		tempReport = aTempReport;
		controller = aController;
	}

	@Override
	public void run() {
		PtcRemoteCall tempRemote = new PtcRemoteCall(serverName);
		String tempUserName = System.getProperty("user.name");
		LOG.info("Store Tasks to " + serverName + " for " + tempUserName);
		try {
			final String tempInfo = tempRemote.saveReport(tempUserName, tempReport.toString());
			result = tempInfo;
		} catch (final Throwable e) {
			LOG.exception(e);
			result = e;
		}
		EventQueue.invokeLater(resultRunnable);
	}
}
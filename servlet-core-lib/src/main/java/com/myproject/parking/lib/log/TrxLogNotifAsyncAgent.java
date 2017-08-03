package com.myproject.parking.lib.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TrxLogNotifAsyncAgent implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(TrxLogNotifAsyncAgent.class);
	
//	private final AppsMonitoringService monitoringService;
	//private final TransactionNotifLogFacade logFacade;
/*	private final TransactionNotifVO task;
	*/
	/*public TrxLogNotifAsyncAgent(TransactionNotifLogFacade logFacade, TransactionNotifVO task) {
//		this.monitoringService = monitoringService;
		this.logFacade = logFacade;
		this.task = task;
	}
	*/
	@Override
	public void run() { 
		String errorMsg = null;
		boolean success = false;
		int maxRetry = 3;
		while (maxRetry-- > 0) {
			/*try {
				// insert task
				logFacade.createLog(task);
				success = true;
				LOG.debug("[#" + task.getOrderId() + "] Log has been saved");
				break;
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOG.warn("[#" + task.getOrderId() + "] Exception occured", e);
				try {
					Thread.sleep(200);
				} catch (InterruptedException ie) {}
			}*/
		}
		if (!success) {
//			monitoringService.reportFailedLog(task, errorMsg);
		}
//		monitoringService.checkTrxProcessTime(task);
	}
	

}

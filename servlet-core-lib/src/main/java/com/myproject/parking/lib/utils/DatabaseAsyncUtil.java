package com.myproject.parking.lib.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DatabaseAsyncUtil {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseAsyncUtil.class);

	private int agentCount = 2;
	private ExecutorService es;
		
/*	@Autowired
	private TransactionLogFacade transactionLogFacade;
	
	@Autowired
	private TransactionNotifLogFacade transactionNotifLogFacade;
	*/

	
	public void start() {
		LOG.info("Starting DatabaseAsyncUtil with {} agent(s)", agentCount);
		es = Executors.newFixedThreadPool(agentCount);
	}
	
	public void stop() {
		LOG.info("Stopping DatabaseAsyncUtil");
		try {
			es.shutdown();
			es.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {}
	}
	
/*	public void logTransaction(TransactionVO task) {
		LOG.debug("[#{}] logTransaction: {}", task.getOrderId(), task);
		TrxLogAsyncAgent agent = new TrxLogAsyncAgent(transactionLogFacade, task);
		es.execute(agent);
	}*/
/*	
	public void logTransactionNotif(TransactionNotifVO task) {
		LOG.debug("[#{}] logTransactionNotif: {}", task.getOrderId(), task);
		TrxLogNotifAsyncAgent agent = new TrxLogNotifAsyncAgent(transactionNotifLogFacade, task);
		es.execute(agent);
	}*/

	public void setAgentCount(int agentCount) {
		this.agentCount = agentCount;
	} 
}

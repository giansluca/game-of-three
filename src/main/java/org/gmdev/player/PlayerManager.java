package org.gmdev.player;

import static org.gmdev.event.type.Event.EventType.*;
import static org.gmdev.event.subscriber.EventSubscriber.Subscriber.*;
import static org.gmdev.event.message.Message.StartMode.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.gmdev.event.*;
import org.gmdev.event.message.*;
import org.gmdev.event.message.Message.StartMode;
import org.gmdev.event.type.*;
import org.gmdev.player.tasks.StartMessageQueueConsumerTask;
import org.gmdev.player.workers.*;
import org.gmdev.serviceprocessor.ServiceScheduler;
import org.gmdev.utils.Factory;

import io.github.giansluca.jargs.Jargs;

public class PlayerManager {
	
	public static final String ERROR = "ERROR";
	public static final String STOP = "STOP";
	
	private Factory factory = Factory.getFactory();
	private EventManager eventManager;
	private WorkerBag workerBag;
	private String playerWinnerName;
	private AtomicBoolean gameover = new AtomicBoolean(false);
	
	public PlayerManager() {
		workerBag = AbstractWorker.createWorkerBag();
		eventManager = new EventManager();
	}
	
	public void start(Jargs arguments) {
		boolean sendFirst= arguments.has("sendfirst");
		Optional<Integer> initNumber = arguments.has("initnumber")
				? Optional.of(arguments.getInt("initnumber"))
				: Optional.empty();
		
		registerSubscribers();
		
		startMessageQueueConsumer();
		startListen(sendFirst ? SEND_FIRST : ONLY_LISTEN);
		if (sendFirst)
			sendFirst(initNumber);
	}
	
	private void registerSubscribers() {
		eventManager.subscribe(
				factory.createEventSubscriber(SEND_SUBSCRIBER), 
				SEND);
		eventManager.subscribe(
				factory.createEventSubscriber(SENDFIRST_SUBSCRIBER), 
				SENDFIRST);
		eventManager.subscribe(
				factory.createEventSubscriber(LISTEN_SUBSCRIBER), 
				LISTEN);
	}
	
	private void startMessageQueueConsumer() {
		ServiceScheduler serviceScheduler = ServiceScheduler.getServiceScheduler();
		StartMessageQueueConsumerTask startMessageQueueConsumerTask = 
				new StartMessageQueueConsumerTask(eventManager, workerBag, exitCallback);
		
		serviceScheduler.schedule(startMessageQueueConsumerTask);
	}
	
	public Consumer<String> exitCallback = winner -> {
		playerWinnerName = winner;
		exitGame();
	};
	
	private void startListen(StartMode startMode) {
		var listenMessage = new ListenMessage(startMode);
		try {
			eventManager.notifySubscribers(new ListenEvent(listenMessage));
		} catch (Exception e) {
        	shutdownWithError();
        	throw new IllegalStateException(e);
        }	
	}

	private void sendFirst(Optional<Integer> initNumber) {
		var sendFirstMessage = new SendFirstMessage(initNumber);
		try {
			eventManager.notifySubscribers(new SendFirstEvent(sendFirstMessage));
		} catch (Exception e) {
			shutdownWithError();
			throw new IllegalStateException(e);
		}
	}
	
	private void shutdownWithError() {
		putSuthdownMessageOnQueue(ERROR);
		exitGame();
	}
	
	public void shutdown() {
		putSuthdownMessageOnQueue(STOP);
		exitGame();
	}
	
	private void putSuthdownMessageOnQueue(String message) {
		try {
			workerBag.getMessageQueue().put(message);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private void exitGame() {
		workerBag.getSocketServerManager().stop();
		ServiceScheduler.getServiceScheduler().shutdown();
		gameover.set(true);
	}
	
	public boolean gameover() {
		return gameover.get();
	}
	
	public String getPlayerWinnerName() {
		return playerWinnerName;
	}
	
}

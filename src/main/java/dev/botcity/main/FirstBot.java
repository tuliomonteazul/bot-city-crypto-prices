package dev.botcity.main;

import java.awt.event.KeyEvent;

import dev.botcity.framework.bot.DesktopBot;
import dev.botcity.maestro_sdk.BotExecutor;
import dev.botcity.maestro_sdk.runner.BotExecution;
import dev.botcity.maestro_sdk.runner.RunnableAgent;

public class FirstBot extends DesktopBot implements RunnableAgent
{
	private int sleepAfterAction = 300;
	
	public FirstBot() {
		try {
			setResourceClassLoader(this.getClass().getClassLoader());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void action(BotExecution botExecution) {

		try {
			// To open a web page
			browse("https://docs.google.com/spreadsheets/d/1NrGi7HMf1DFPClKQ9Y1kOXCAJSTSbYZyzPfSrsL6Tes/edit#gid=1321304527");

			if(!find( "link_header", 0.97, 10000)) {	notFound("link_header");	return;}
			click();
			
			keyDown();
			
			while(haveLink()) {
				
				// open link
				altEnter();
				// waiting for CoinMarketCap to open
				wait(2000);
				
				procuraSimboloDePreco();

				clickRelative(46, 10);
				doubleclick();
				controlC();
				controlW();
				keyLeft();
				controlShiftV();
				
				keyDown();
				keyRight();
			}
			
 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean haveLink() {
		controlC();
		String clipboard = getClipboard();
		if (clipboard != null && clipboard.startsWith("https://")) {
			return true;
		}
		return false;
	}
	
	private void procuraSimboloDePreco() {
		if(!find( "price_symbol", 0.97, 10000)) {
			wait(2000);
			if(!find( "price_symbol_2", 0.97, 10000)) {	notFound("price_symbol_2");	return;}
		}
	}
	
	private void doubleKeyActions(int keyCodeFirst, int keyCodeSecond, int waitAfter) {
		getRobot().keyPress(keyCodeFirst);
		getRobot().keyPress(keyCodeSecond);
		getRobot().keyRelease(keyCodeSecond);
		getRobot().keyRelease(keyCodeFirst);
		wait(waitAfter);
	}
	
	private void tripleKeyActions(int keyCodeFirst, int keyCodeSecond, int keyCodeThird, int waitAfter) {
		getRobot().keyPress(keyCodeFirst);
		getRobot().keyPress(keyCodeSecond);
		getRobot().keyPress(keyCodeThird);
		getRobot().keyRelease(keyCodeThird);
		getRobot().keyRelease(keyCodeSecond);
		getRobot().keyRelease(keyCodeFirst);
		wait(waitAfter);
	}
	
	private void altEnter() {
		doubleKeyActions(KeyEvent.VK_ALT, KeyEvent.VK_ENTER, sleepAfterAction);
	}
	
	private void controlShiftV() {
		tripleKeyActions(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_V, sleepAfterAction);
	}

	
	private void notFound(String label) {
		System.out.println("Element not found: "+label);
	}

	public static void main(String[] args) {
		BotExecutor.run(new FirstBot(), args);
	}
}
	











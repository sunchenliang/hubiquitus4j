/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hubiquitus.TwitterBot;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hubiquitus.hubotsdk.Actor;
import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;

public class TwitterBot extends Actor  {


	private static Logger logger = Logger.getLogger(TwitterBot.class);
	
	public static void main( String[] args )throws Exception{
		BasicConfigurator.configure();    
		TwitterBot bot  = new TwitterBot();
		bot.start();
	}

	@Override
	protected void inProcessMessage(HMessage messageIncoming) {
		logger.info(messageIncoming.toString());
	}

	@Override
	protected void inProcessCommand(HCommand commandIncoming) {		
	}



}

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

package org.hubiquitus.hubotsdk;

import java.util.Map;

import org.apache.camel.builder.RouteBuilder;

public class RouteGenerator extends RouteBuilder {

	private Map< String, Class<Object> > outboxMap;

	public RouteGenerator(Map< String, Class<Object> >  outboxMap) {
		this.outboxMap = outboxMap;
	}

	@Override
	public void configure() throws Exception {

		/* Create route for inboxQueue */
		from("seda:inbox")
			.to("bean:actor?method=inProcess");


		/* Create route for all outboxesQueue */
		from("seda:hubotAdapterOutbox")
			.to("bean:hubotAdapterOutbox?method=onOutGoing");

		for(String key : outboxMap.keySet()) {
			
			String routeName = "seda:" + key; 
			String beanText ="bean:adapter1Outbox?method=onOutGoing";
			from(routeName)
				.to(beanText);
		}
	}



}

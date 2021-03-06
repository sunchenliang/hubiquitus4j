/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
 */


package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hubiquitus.hapi.client.HClient;
import org.hubiquitus.hapi.client.HCommandDelegate;
import org.hubiquitus.hapi.client.HMessageDelegate;
import org.hubiquitus.hapi.client.HResultDelegate;
import org.hubiquitus.hapi.client.HStatusDelegate;
import org.hubiquitus.hapi.hStructures.ConnectionError;
import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HFilterTemplate;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hapi.hStructures.HMessageOptions;
import org.hubiquitus.hapi.hStructures.HOptions;
import org.hubiquitus.hapi.hStructures.HResult;
import org.hubiquitus.hapi.hStructures.HStatus;
import org.hubiquitus.hapi.util.HJsonDictionnary;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author speed
 * @version 0.3
 * The panel for this example
 */

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements HStatusDelegate, HMessageDelegate, HCommandDelegate, HResultDelegate  {
	private HClient client;
	final MainPanel outerClass = this;

	private HOptions option = new HOptions();

	private JTextField usernameField = new JTextField("");
	private JTextField passwordField = new JTextField("");
	private JTextField endPointField = new JTextField("");
	private JTextField serverPortField = new JTextField("");
	private JTextField serverHostField = new JTextField("");
	private JTextField chidField = new JTextField("test");
	private JTextField messageField = new JTextField("");
	private JTextField nbLastMessagesField = new JTextField("");
	private JTextField convidField = new JTextField("");
	private JTextField convstateField = new JTextField("");
	private JTextField filterNameField = new JTextField("");
	private JTextField filterAttrField = new JTextField("");
	private JTextField filterValueField = new JTextField("");
	
	private JButton connectButton = new JButton("Connect");
	private JButton disconnectButton = new JButton("Disconnect");
	private JButton hcommandButton = new JButton("hEcho");
	private JButton subscribeButton = new JButton("subscribe");
	private JButton unsubscribeButton = new JButton("unsubscribe");
	private JButton publishButton = new JButton("publish");
	private JButton getLastMessagesButton = new JButton("getLstMsg");
	private JButton getSubscriptionsButton = new JButton("getSubs");
	private JButton getThreadButton = new JButton("getThread");
	private JButton getThreadsButton = new JButton("getThreads");
	private JButton pubConvStateButton = new JButton("pubConvState"); 
	private JButton setFilterButton = new JButton("setFilter");
	private JButton listFiltersButton = new JButton("listFilters");
	private JButton unsetFilterButton = new JButton("unsetFilter"); 
	private JButton getRelevantMessagesButton = new JButton("getRelevantMessages"); 
	private JButton cleanButton = new JButton("Clean");
	
	
	private JRadioButton xmppRadioButton = new JRadioButton("XMPP");
	private JRadioButton socketRadioButton = new JRadioButton("Socket IO");
	private ButtonGroup buttonGroup = new ButtonGroup();
	
	private JRadioButton transientRadioButton = new JRadioButton("transient");
	private JRadioButton notTransientRadioButton = new JRadioButton("not transient");
	private ButtonGroup transientGroup = new ButtonGroup();
	
	private JTextArea logArea = new JTextArea(30,100);
	private JTextArea statusArea = new JTextArea(1,90);
	
	public MainPanel() {
		super();
		initComponents();
		initListeners();
		client = new HClient();
		client.onStatus(this);
		client.onMessage(this);
	}

	/**
	 * Initialization of all the component
	 */
	public void initComponents() {
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		
		buttonGroup.add(xmppRadioButton);
		buttonGroup.add(socketRadioButton);
		xmppRadioButton.setSelected(true);
		
		transientGroup.add(transientRadioButton);
		transientGroup.add(notTransientRadioButton);
		transientRadioButton.setSelected(true);

		//Initialization of Labels,TextFields and RadioButtons
		JPanel paramsPanel = new JPanel();
		GridLayout paramsLayout = new GridLayout(16, 2);
		paramsPanel.setLayout(paramsLayout);
		paramsPanel.add(new JLabel("username"));
		paramsPanel.add(usernameField);
		paramsPanel.add(new JLabel("password"));
		paramsPanel.add(passwordField);
		paramsPanel.add(new JLabel("endPoint"));
		paramsPanel.add(endPointField);
		paramsPanel.add(new JLabel("serverHost"));
		paramsPanel.add(serverHostField);
		paramsPanel.add(new JLabel("serverPort"));
		paramsPanel.add(serverPortField);
		paramsPanel.add(new JLabel("Channel id"));
		paramsPanel.add(chidField);
		paramsPanel.add(new JLabel("nbLastMessages"));
		paramsPanel.add(nbLastMessagesField);
		paramsPanel.add(new JLabel("Message"));
		paramsPanel.add(messageField);
		paramsPanel.add(new JLabel("convid"));
		paramsPanel.add(convidField);
		paramsPanel.add(new JLabel("status"));
		paramsPanel.add(convstateField);
		paramsPanel.add(new JLabel("Filter Name"));
		paramsPanel.add(filterNameField);
		paramsPanel.add(new JLabel("Filter attr"));
		paramsPanel.add(filterAttrField);
		paramsPanel.add(new JLabel("Filter value"));
		paramsPanel.add(filterValueField);
		paramsPanel.add(transientRadioButton);
		paramsPanel.add(notTransientRadioButton);
		paramsPanel.add(xmppRadioButton);
		paramsPanel.add(socketRadioButton);
		
		statusArea.setEditable(false);
		paramsPanel.add(statusArea);
		
		//Initialization of Buttons
		JPanel controlsPanel = new JPanel();
		GridLayout controlsLayout = new GridLayout(3, 6);
		controlsPanel.setLayout(controlsLayout);
		controlsPanel.add(connectButton);
		controlsPanel.add(disconnectButton);
		controlsPanel.add(hcommandButton);
		controlsPanel.add(subscribeButton);
		controlsPanel.add(unsubscribeButton);
		controlsPanel.add(publishButton);
		controlsPanel.add(getLastMessagesButton);
		controlsPanel.add(getSubscriptionsButton);
		controlsPanel.add(getThreadButton);
		controlsPanel.add(getThreadsButton);
		controlsPanel.add(pubConvStateButton);
		controlsPanel.add(setFilterButton);
		controlsPanel.add(listFiltersButton);
		controlsPanel.add(unsetFilterButton);
		controlsPanel.add(getRelevantMessagesButton);
		controlsPanel.add(cleanButton);
		
		
		//Initialization of the TextArea
		JPanel consolePanel = new JPanel();
		logArea.setEditable(false);
		consolePanel.add(logArea);
		JScrollPane txtScrol=new JScrollPane(logArea);
		txtScrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		consolePanel.add(txtScrol);

		//Add all in the layout
		this.add(paramsPanel, BorderLayout.NORTH);
		this.add(controlsPanel, BorderLayout.CENTER);
		this.add(consolePanel, BorderLayout.SOUTH);
	}

	/**
	 * Initialization of the listeners 
	 */
	public void initListeners() {
		connectButton.addMouseListener(new ConnectionButtonListener());
		disconnectButton.addMouseListener(new DisconnectionButtonListener());
		hcommandButton.addMouseListener(new HCommandButtonListener());
		subscribeButton.addMouseListener(new SubscribeButtonListener());
		unsubscribeButton.addMouseListener(new UnsubscribeButtonListener());
		publishButton.addMouseListener(new PublishButtonListener());
		getLastMessagesButton.addMouseListener(new GetLastMessagesButtonListener());
		getSubscriptionsButton.addMouseListener(new GetSubscriptionButtonListener());
		getThreadButton.addMouseListener(new GetThreadButtonListener());
		getThreadsButton.addMouseListener(new GetThreadsButtonListener());
		pubConvStateButton.addMouseListener(new PubConvStateButtonListener());
		setFilterButton.addMouseListener(new SetFilterListener());
		listFiltersButton.addMouseListener(new ListFiltersListener());
		unsetFilterButton.addMouseListener(new UnsetFilterListener());
		getRelevantMessagesButton.addMouseListener(new GetRelevantMessagesListener());
		cleanButton.addMouseListener(new CleanButtonListener());
		
	}
	
	/**
	 * Add a text to the TextArea
	 * @param text
	 */
	public void addTextArea(String text) {
		String tempTxt = this.logArea.getText() + "\n" + text;
		this.logArea.setText(tempTxt);
	}
	
	/**
	 * Clean the TextArea
	 */
	public void cleanTextArea() {
		this.logArea.setText("clean");
	}
	
	/**
	 * Change the status 
	 * @param text
	 */
	public void setStatusArea(String text) {
		this.statusArea.setText(text);
	}
	
	// Listener of button connection
	class ConnectionButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			
			String endpoint = endPointField.getText();
			option.getEndpoints().clear();
			if (endpoint == null || endpoint.equalsIgnoreCase("")) {
				option.setEndpoints(null);
			} else {
				ArrayList<String> endpoints = new ArrayList<String>();
				endpoints.add(endpoint);
				option.setEndpoints(endpoints);
			}
			String serverHost = serverHostField.getText();
			if(serverHost != null) {
				option.setServerHost(serverHost);
			}
			String serverPort = serverPortField.getText();
			if(serverPort != null) {
				option.setServerPort(serverPort);
			}
			
			if(socketRadioButton.isSelected())
				option.setTransport("socketio");
			else
				option.setTransport("xmpp");
			client.connect(usernameField.getText(), passwordField.getText(), option);
		}
	}

	// Listener of button disconnection
	class DisconnectionButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			client.disconnect();
		}
	}

	// Listener of button hcommand
	class HCommandButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			HJsonDictionnary jsonObj = new HJsonDictionnary();
			try {
				jsonObj.put("text",  messageField.getText());
				HCommand cmd = new HCommand("hnode@hub.novediagroup.com","hecho",jsonObj);
				client.command(cmd,outerClass);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	// Listener of button clean
	class CleanButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			cleanTextArea();
		}
	}
	
	// Listener of button subscribe
	class SubscribeButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			client.subscribe(chidField.getText(),outerClass);
		}
	}
	
	// Listener of button unsubscribe
	class UnsubscribeButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			client.unsubscribe(chidField.getText(),outerClass);
		}
	}
	
	// Listener of button publish
	class PublishButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			HMessage message = new HMessage();
			message.setPublisher(usernameField.getText());
			message.setChid(chidField.getText());
			message.setPublished(new GregorianCalendar());
			message.setType("obj");
			
			if(transientRadioButton.isSelected())
				message.setTransient(true);
			else
				message.setTransient(false);
			
			HJsonDictionnary payload = new HJsonDictionnary();
			payload.put("text", messageField.getText());
			message.setPayload(payload);
			client.publish(message,outerClass);
		}
	}
	
	// Listener of button publish
	class GetLastMessagesButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			String chid = chidField.getText();
			try {
				int nbLastMessage = Integer.parseInt(nbLastMessagesField.getText());
				if(nbLastMessage > 0) {
					client.getLastMessages(chid, nbLastMessage,outerClass);
				} else {
					client.getLastMessages(chid,outerClass);
				}
			} catch (Exception e) {
				client.getLastMessages(chid,outerClass);
			}
		}
	}
	
	//Listener of button getsubscriptions
	class GetSubscriptionButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			client.getSubscriptions(outerClass);
		}
	}
	

	//Listener of button getThread
	class GetThreadButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			String chid = chidField.getText();
			String convid = convidField.getText();
			try{
				client.getThread(chid, convid, outerClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Listener of button getThreads 
	class GetThreadsButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			String chid = chidField.getText();
			String status = convstateField.getText();
			try{
				client.getThreads(chid, status, outerClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Listener of button getsubscriptions
	class PubConvStateButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			String chid = chidField.getText();
			String convid = convidField.getText();
			String status = convstateField.getText();
			HMessageOptions msgOptions = new HMessageOptions();

			if(transientRadioButton.isSelected())
				msgOptions.setTransient(true);
			else
				msgOptions.setTransient(false);
			
			try{
				HMessage pubMsg = client.buildConvState(chid, convid, status, msgOptions);
				client.publish(pubMsg, outerClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Listener of button setFilter
	class SetFilterListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			String chid = chidField.getText();
			String filterName = filterNameField.getText();
			String filterAttr = filterAttrField.getText();
			String filterValue = filterValueField.getText();
			
			JSONObject jsonObj = new JSONObject();
			System.out.println("filter Attr : " + filterAttr + "filter valu : " + filterValue);
			try {
				jsonObj.put(filterAttr, filterValue);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			HMessage template = new HMessage(jsonObj);
			HFilterTemplate filter = new HFilterTemplate();
			filter.setChid(chid);
			filter.setName(filterName);
			filter.setTemplate(template);
			client.setFilter(filter, outerClass);
		}
	}
	
	//Listener of button listerFilter
	class ListFiltersListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			String chid = chidField.getText();
			if(chid  == "")
				client.listFilters(null, outerClass);
			else
				client.listFilters(chid, outerClass);
		}
	}
	
	//Listener of button unsetFilter
	class UnsetFilterListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			String chid = chidField.getText();
			String filterName = filterNameField.getText();
			client.unsetFilter(filterName,chid,outerClass);
		}
	}
	
	//Listener of button getRelevantMessages
	class GetRelevantMessagesListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			String chid = chidField.getText();
			client.getRelevantMessages(chid,outerClass);
		}	
	}
	
	
	/* Override for Delegate */
	
	@Override
	public void onResult(HResult result) {
		this.addTextArea(result.toString());		
	}

	@Override
	public void onMessage(HMessage message) {
		Calendar date  = message.getPublished();
		Calendar dateNow = new GregorianCalendar();
		System.out.println("nowdate : " + dateNow.toString());
		System.out.println("date : " + date.toString());
		this.addTextArea(message.toString());	
	}

	@Override
	public void onStatus(HStatus status) {
		this.setStatusArea("hstatus");
		this.addTextArea(status.toString());
		if(status.getErrorCode() == ConnectionError.NO_ERROR || status.getErrorMsg() == null) {
			this.setStatusArea(status.getStatus().toString());
		} else {
			this.setStatusArea(status.getStatus().toString() + " : " + status.getErrorMsg() );
		}
	}
	
	@Override
	public void onCommand(HCommand command) {
		this.addTextArea(command.toString());
	}
	
	
	
	
	/* Getters & Setters */
	public void setTextArea(String text){
		this.logArea.setText(text);
	}

	

	
	
	
}

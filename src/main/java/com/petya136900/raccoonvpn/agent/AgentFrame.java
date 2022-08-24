package com.petya136900.raccoonvpn.agent;

import java.awt.CardLayout;
import java.awt.Color;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.fusesource.jansi.AnsiConsole;

import com.petya136900.racoonvpn.tools.Tools;

public class AgentFrame extends JFrame {
	static {
		AnsiConsole.systemInstall();
	}
	private static final long serialVersionUID = 7733233782390669415L;
	public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	AgentFrame agentFrame = new AgentFrame();
            	agentFrame.pack();
            	agentFrame.setVisible(true);
            }
        });
	}
	private JPanel panelsContainer;
	private LoginPanel panelLogin;
	private ConnectedPanel panelConnected;
	private CardLayout cardLayout;
	public Socket setSocketToServer;
	private Agent agent;
	public void showLogin() {
		cardLayout.show(panelsContainer, "panelLogin");
	}
	public void showConnected() {
		cardLayout.show(panelsContainer, "panelConnected");
	}
	public void blockButtonLogin() {
		panelLogin.getjButtonLogin().setEnabled(false);
	}
	public void unBlockButtonLogin() {
		try {
			panelLogin.getjButtonLogin().setEnabled(true);
		} catch (NullPointerException e) {}
	}
	public void setLastError(String message) {
		panelLogin.getLastMessage().setForeground(Color.red);
		panelLogin.getLastMessage().setText(message);
	}
	public void setLastInfo(String message) {
		panelLogin.getLastMessage().setForeground(Color.green);
		panelLogin.getLastMessage().setText(message);
	}
	public AgentFrame() {
		super();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("RaccoonVPN Agent");
		setResizable(false);
		panelsContainer = new JPanel();
		panelLogin = new LoginPanel(this);
		panelsContainer.add(panelLogin);
		panelConnected = new ConnectedPanel(this);
		panelsContainer.add(panelConnected);
		cardLayout = new CardLayout();
		panelsContainer.setLayout(cardLayout);
		panelsContainer.add(panelLogin, "panelLogin");
		panelsContainer.add(panelConnected, "panelConnected");
		add(panelsContainer);
		cardLayout.show(panelsContainer, "panelLogin");
		try {
			Save save = Save.fromJson(Tools.readFileToString("save"));
			if(save.getLogin()!=null)
				panelLogin.loadSave(save);
		} catch (Exception e) {
			//
		}
	}
	public void showDisconnectInLoginPanel() {
		unBlockButtonLogin();
		panelLogin.getjButtonLogin().setText("Отключиться");
		panelLogin.getjButtonLogin().setBackground(Color.red);
		panelLogin.getjButtonLogin().setForeground(Color.black);
		panelLogin.setAction(panelLogin.getDiscAction());
	}
	public void showNormalInLoginPanel() {
		try {
			unBlockButtonLogin();
			panelLogin.getjButtonLogin().setText("Вход");
			panelLogin.getjButtonLogin().setBackground(Color.blue);
			panelLogin.getjButtonLogin().setForeground(Color.white);
			panelLogin.setAction(panelLogin.getDefaultAction());
			showLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void disconnectFromAgent() {
		panelLogin.disconnectFromAgent();
		agent.setBye(true);
		try {
			agent.setAutoReconnect(false);
		} catch (Exception e) {}
		try {
			setSocketToServer.close();
		} catch (Exception e) {}
	}
	public void setAgent(Agent agent) {
		this.agent=agent;
	}
	public void setServerHost(String string) {
		panelConnected.getjTextFieldServerHostPort().setText(string);
	}
	public void setWeb(String string) {
		panelConnected.getjTextFieldWeb().setText(string);
	}
}

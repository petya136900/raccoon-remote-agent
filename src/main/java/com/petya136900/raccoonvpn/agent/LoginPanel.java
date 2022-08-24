package com.petya136900.raccoonvpn.agent;
import java.awt.Dimension;

import javax.swing.JLabel;

/**
 *
 * @author petya136900
 */
public class LoginPanel extends javax.swing.JPanel {
	private AgentFrame agentFrame;
	
	private Action action = getDefaultAction();
	
	private Thread agentThread;
	
	private static final long serialVersionUID = -6553848050701743832L;
    public LoginPanel(AgentFrame agentFrame) {
    	this.agentFrame=agentFrame;
        initComponents();
    }                    
    private void initComponents() {

        jPanelLogin = new javax.swing.JPanel();
        jTextFieldLogin = new javax.swing.JTextField();
        jPanelLogo = new javax.swing.JPanel();
        jLabelRaccoonVPN = new javax.swing.JLabel();
        jLabelRaccoonLogo = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jLabelServer = new javax.swing.JLabel();
        jLabelLogin = new javax.swing.JLabel();
        jTextFieldServerHostPort = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        jButtonLogin = new javax.swing.JButton();
        jCheckBoxAutoreconnect = new javax.swing.JCheckBox();
        lastMessage = new javax.swing.JLabel();
        lastMessage.setMinimumSize(new Dimension(30,17));

        jPanelLogin.setBackground(new java.awt.Color(255, 255, 255));

        jTextFieldLogin.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jTextFieldLogin.setText("admin");
        jTextFieldLogin.setToolTipText("Логин");
        jTextFieldLogin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLoginActionPerformed(evt);
            }
        });

        jPanelLogo.setBackground(new java.awt.Color(255, 255, 255));

        jLabelRaccoonVPN.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabelRaccoonVPN.setText("RaccoonVPN");

        jLabelRaccoonLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RaccoonFinal64.png"))); // NOI18N

        javax.swing.GroupLayout jPanelLogoLayout = new javax.swing.GroupLayout(jPanelLogo);
        jPanelLogo.setLayout(jPanelLogoLayout);
        jPanelLogoLayout.setHorizontalGroup(
            jPanelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLogoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabelRaccoonLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelRaccoonVPN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLogoLayout.setVerticalGroup(
            jPanelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLogoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabelRaccoonVPN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLogoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelRaccoonLogo)
                .addContainerGap())
        );

        jLabelPassword.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabelPassword.setText("Пароль");

        jLabelServer.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabelServer.setText("Сервер");

        jLabelLogin.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabelLogin.setText("Логин");

        jTextFieldServerHostPort.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jTextFieldServerHostPort.setText("vpn.furr.cf:8444");
        jTextFieldServerHostPort.setToolTipText("Сервер");
        jTextFieldServerHostPort.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldServerHostPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldServerHostPortActionPerformed(evt);
            }
        });

        jPasswordField.setToolTipText("");
        jPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordFieldActionPerformed(evt);
            }
        });

        jButtonLogin.setBackground(new java.awt.Color(102, 153, 255));
        jButtonLogin.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jButtonLogin.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin.setText("Вход");
        jButtonLogin.setToolTipText("");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jCheckBoxAutoreconnect.setText("Авто-переподключение");
        jCheckBoxAutoreconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAutoreconnectActionPerformed(evt);
            }
        });

        lastMessage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lastMessage.setText("");

        javax.swing.GroupLayout jPanelLoginLayout = new javax.swing.GroupLayout(jPanelLogin);
        jPanelLogin.setLayout(jPanelLoginLayout);
        jPanelLoginLayout.setHorizontalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jPanelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLoginLayout.createSequentialGroup()
                .addContainerGap(115, Short.MAX_VALUE)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastMessage)
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelPassword)
                            .addComponent(jLabelLogin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldLogin)
                            .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addComponent(jLabelServer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldServerHostPort, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxAutoreconnect)
                            .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(117, 117, 117))
        );
        jPanelLoginLayout.setVerticalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(lastMessage)
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldServerHostPort, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelServer))
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxAutoreconnect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }                       
    private void jTextFieldLoginActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               
    private void jTextFieldServerHostPortActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        // TODO add your handling code here:
    }                                                        
    private void jPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              
    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {              
    	action.perfome();
    }
    public Action getDiscAction() {
    	return ()->{
    		agentFrame.disconnectFromAgent();
    	};
    }
    public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Action getDefaultAction() {
    	return ()->{
    		String host = jTextFieldServerHostPort.getText().replaceAll(":.*$", "");
        	Integer port;
        	try {
        		port = Integer.parseInt(jTextFieldServerHostPort.getText().replaceAll("^.*:", ""));
        	} catch (Exception e) {
        		port = 8444;
    		}
        	if(port<1||port>65535) {
        		lastMessage.setForeground(java.awt.Color.red);
        		lastMessage.setText("Некорректный порт");
        		return;
        	}
        	final Integer portFinal = port;
        	Boolean autoReconnect = jCheckBoxAutoreconnect.isSelected();
        	String login = jTextFieldLogin.getText();
        	String password = String.valueOf(jPasswordField.getPassword());
    		prConnect(host,portFinal,login,password,autoReconnect,false,true,agentFrame);
    	};
    }
	
	public void prConnect(String host, Integer portFinal, String login, String password, Boolean autoReconnect, Boolean debug, Boolean useTls, AgentFrame agentFrame) {
		agentFrame.blockButtonLogin();
    	agentThread = new Thread(()->{
    		new Agent().connect(host,portFinal,login,password,autoReconnect,false,true,agentFrame);
    	});
    	agentThread.start();
	}
	
	private void jCheckBoxAutoreconnectActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        // TODO add your handling code here:
    }                                                               
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JCheckBox jCheckBoxAutoreconnect;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelRaccoonLogo;
    private javax.swing.JLabel jLabelRaccoonVPN;
    private javax.swing.JLabel jLabelServer;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPanel jPanelLogo;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextFieldLogin;
    private javax.swing.JTextField jTextFieldServerHostPort;
    private javax.swing.JLabel lastMessage;
	public JLabel getLastMessage() {
		return lastMessage;
	}
    public javax.swing.JButton getjButtonLogin() {
		return jButtonLogin;
	}
	public void disconnectFromAgent() {
		try {
			agentThread.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void loadSave(Save save) {
		jTextFieldLogin.setText(save.getLogin());
		jTextFieldServerHostPort.setText(save.getHost()+":"+save.getPort());
		jPasswordField.setText(save.getPassword());
		jCheckBoxAutoreconnect.setSelected(save.isAutoReconnect());
		if(save.isAutoReconnect()) {
			prConnect(save.getHost(),save.getPort(),save.getLogin(),save.getPassword(),true,false,true,agentFrame);
		}
	}              
}

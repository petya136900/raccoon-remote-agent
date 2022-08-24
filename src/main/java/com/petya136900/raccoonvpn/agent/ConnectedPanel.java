package com.petya136900.raccoonvpn.agent;

/**
 *
 * @author petya136900
 */
public class ConnectedPanel extends javax.swing.JPanel {
	private static final long serialVersionUID = -658335731822086971L;
	AgentFrame agentFrame;
	public ConnectedPanel(AgentFrame agentFrame) {
		this.agentFrame=agentFrame;
        initComponents();
    }                    
    private void initComponents() {
        jPanelLogin = new javax.swing.JPanel();
        jTextFieldLogin = new javax.swing.JTextField();
        jPanelLogo = new javax.swing.JPanel();
        jLabelRaccoonVPN = new javax.swing.JLabel();
        jLabelRaccoonLogo = new javax.swing.JLabel();
        jLabelWeb = new javax.swing.JLabel();
        jLabelServer = new javax.swing.JLabel();
        jLabelLogin = new javax.swing.JLabel();
        jTextFieldServerHostPort = new javax.swing.JTextField();
        jButtonDisc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldWeb = new javax.swing.JTextField();
        jPanelLogin.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldLogin.setEditable(false);
        jTextFieldLogin.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldLogin.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
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

        jLabelWeb.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabelWeb.setText("Веб");
        jLabelServer.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabelServer.setText("Сервер");
        jLabelLogin.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabelLogin.setText("Логин");
        jTextFieldServerHostPort.setEditable(false);
        jTextFieldServerHostPort.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldServerHostPort.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jTextFieldServerHostPort.setText("oracle.petya136900.tk:8444");
        jTextFieldServerHostPort.setToolTipText("Сервер");
        jTextFieldServerHostPort.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldServerHostPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldServerHostPortActionPerformed(evt);
            }
        });
        jButtonDisc.setBackground(new java.awt.Color(255, 51, 51));
        jButtonDisc.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jButtonDisc.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDisc.setText("Отключение");
        jButtonDisc.setToolTipText("");
        jButtonDisc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDiscActionPerformed(evt);
            }
        });
        jLabel1.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 51));
        jLabel1.setText("Подключен");
        jTextFieldWeb.setEditable(false);
        jTextFieldWeb.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldWeb.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jTextFieldWeb.setText("https://oracle.petya136900.tk:8443");
        jTextFieldWeb.setToolTipText("Web-Адрес");
        jTextFieldWeb.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLogin1ActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanelLoginLayout = new javax.swing.GroupLayout(jPanelLogin);
        jPanelLogin.setLayout(jPanelLoginLayout);
        jPanelLoginLayout.setHorizontalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jPanelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(jButtonDisc))
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(jLabel1))
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLoginLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabelLogin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldLogin))
                            .addGroup(jPanelLoginLayout.createSequentialGroup()
                                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelServer)
                                    .addComponent(jLabelWeb))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldWeb)
                                    .addComponent(jTextFieldServerHostPort, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(98, 98, 98))
        );
        jPanelLoginLayout.setVerticalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldServerHostPort, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelServer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelWeb)
                    .addComponent(jTextFieldWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLogin)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jButtonDisc, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
    }                                              
    public javax.swing.JTextField getjTextFieldWeb() {
		return jTextFieldWeb;
	}
	public void setjTextFieldWeb(javax.swing.JTextField jTextFieldWeb) {
		this.jTextFieldWeb = jTextFieldWeb;
	}
	public javax.swing.JTextField getjTextFieldServerHostPort() {
		return jTextFieldServerHostPort;
	}
	public void setjTextFieldServerHostPort(javax.swing.JTextField jTextFieldServerHostPort) {
		this.jTextFieldServerHostPort = jTextFieldServerHostPort;
	}
	private void jTextFieldServerHostPortActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        
    }                                                        
    private void jButtonDiscActionPerformed(java.awt.event.ActionEvent evt) {
    	agentFrame.disconnectFromAgent();
    }                                           
    private void jTextFieldLogin1ActionPerformed(java.awt.event.ActionEvent evt) {                                                 
       
    }                                                                 
    private javax.swing.JButton jButtonDisc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelWeb;
    private javax.swing.JLabel jLabelRaccoonLogo;
    private javax.swing.JLabel jLabelRaccoonVPN;
    private javax.swing.JLabel jLabelServer;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPanel jPanelLogo;
    private javax.swing.JTextField jTextFieldLogin;
    private javax.swing.JTextField jTextFieldWeb;
    private javax.swing.JTextField jTextFieldServerHostPort; 
}

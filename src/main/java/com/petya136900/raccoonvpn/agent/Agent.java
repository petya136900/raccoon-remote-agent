package com.petya136900.raccoonvpn.agent;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.net.util.SubnetUtils;
import org.fusesource.jansi.Ansi;

import com.petya136900.raccoonvpn.exceptions.BadTokenException;
import com.petya136900.raccoonvpn.exceptions.DeviceAlreadyConnectedException;
import com.petya136900.raccoonvpn.exceptions.RegenDeviceException;
import com.petya136900.raccoonvpn.exceptions.ServerSupportTlsException;
import com.petya136900.raccoonvpn.exceptions.ServerUnavailableException;
import com.petya136900.raccoonvpn.exceptions.UserNotFoundException;
import com.petya136900.raccoonvpn.exceptions.WrongPassException;
import com.petya136900.racoonvpn.tools.JsonParser;
import com.petya136900.racoonvpn.tools.Tools;

public class Agent {

	private static final SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	
	private ConcurrentHashMap<String, TaskThread> taskThreads = new ConcurrentHashMap<>();
	
	private static int TIMEOUT_SEC = 10;
	private String devId;
	private Long userId;
	private Socket socketToServer;
	private String serverIp;
	private String serverHost;
	private Integer serverWebPort;
	private String token;
	private String lastErrorCode;
	private boolean agentConnected=false;
	private RProtocol rProtocol;
	private boolean debug;
	private String host;
	private Integer port;
	private String login;
	private String password;
	private boolean useTls;
	private boolean autoReconnect;
	private AgentFrame agentFrame;
	private boolean bye = false;
	
	public void connect(String host, Integer port, String login, String password, boolean autoReconnect, boolean debug, boolean useTls, AgentFrame agentFrame) {
		info("Host: "+host);
		info("Port: "+port);
		info("Login: "+login);
		info("Password: "+password.replaceAll(".{1}", "*"));
		info("Use TLS: "+useTls);
		info("Auto Reconnect: "+autoReconnect);
		info("Debug: "+debug);
		printLine();
		this.debug=debug;
		this.host=host;
		this.port=port;
		this.useTls=useTls;
		this.autoReconnect=autoReconnect;
		this.login=login;
		this.password=password;
		this.agentFrame=agentFrame;
		rProtocol=new RProtocol().setDebug(this.debug);
		if(this.agentFrame!=null)
			this.agentFrame.setLastInfo("");
		do {
			if(agentFrame!=null) {
				agentFrame.setAgent(this);
			}
			agentConnected=false;
			bye=false;
			try {
				connect();
				info("Подключен к серверу");
				if(agentFrame!=null) {
					setSocketToServer(socketToServer);
					try {
						Tools.createFile("save", new Save(host,port,login,password,autoReconnect,debug,useTls).toJson());
					} catch (IOException e) {}
				}
				if(this.useTls) {
					upgradeToTls();
					info("TLS-Соединение установлено");
				}
				try {
					login();
					info("Успешно вошёл в аккаунт: \nToken: "+token);
					try {
						connectAgent();
						info("Агент успешно подключен к серверу");
						info(String.format("serverHost: %s, serverIp: %s, serverWebPort: %s",serverHost,serverIp,serverWebPort+""));
						try {
							socketToServer.setSoTimeout(TIMEOUT_SEC*1000);
							if(agentFrame!=null) {
								agentFrame.showConnected();
								agentFrame.setSocketToServer = socketToServer;
							}
							while((!socketToServer.isClosed())&&(!Thread.currentThread().isInterrupted())) {
								Data rData = null;
								try {
									rData = rProtocol.read(socketToServer);
								} catch (SocketTimeoutException e) {
									rProtocol.write(socketToServer,Data.code(Codes.RACCOON_PING));
									continue;
								}
								if(rData.codeEqual(Codes.RACCOON_PING)) {
									rProtocol.write(socketToServer,Data.code(Codes.RACCOON_OK));
								} else if(rData.codeEqual(Codes.RACCOON_OK)) {
									//
								} else if(rData.codeEqual(Codes.RACCON_REQ_NET)) {
									String taskId = rData.getTaskId();
									SubnetUtils utils = new SubnetUtils(rData.gettIp()+"/"+rData.gettMask());
									final String[] allIps = utils.getInfo().getAllAddresses();
									final int tTimeout = rData.gettTimeout();
									TaskThread tt = new TaskThread((sb)->{
										boolean f = true;
										int counter=0;
										for(String oneIp : allIps) {
											if(counter++>100) {
												counter=0;
												try {
													rProtocol.write(socketToServer,Data.code(Codes.RACCOON_TASK_DATA)
															.setData(null)
															.setTaskId(taskId));
												} catch (Exception e) {}
											}												
											if(Thread.currentThread().isInterrupted())
												break;
											try {
												if (InetAddress.getByName(oneIp).isReachable(tTimeout)){
													rProtocol.write(socketToServer,Data.code(Codes.RACCOON_TASK_DATA)
															.setTaskId(taskId)
															.setData((f?"":", ")+oneIp+" - "+InetAddress.getByName(oneIp).getHostName()));
													f=false;
												}
											} catch (Exception e) {}
										}
										taskThreads.remove(taskId);
									});
									taskThreads.put(taskId, tt);
									tt.start();
								} else if(rData.codeEqual(Codes.RACCON_CHECK_TASK)) {
									if(taskThreads.containsKey(rData.getTaskId())) {
										rProtocol.write(socketToServer,Data.code(Codes.RACCOON_TASK_OK).setTaskId(rData.getTaskId()));
									} else {
										rProtocol.write(socketToServer,Data.code(Codes.RACCOON_TASK_NF).setTaskId(rData.getTaskId()));
									}
								} else if(rData.codeEqual(Codes.RACCOON_TASK_NF)) {
									TaskThread tt = taskThreads.remove(rData.getTaskId());
									if(tt!=null) {
										tt.stop();
									}
								} else if(rData.codeEqual(Codes.RACCOON_NEED_SOCKET)) {
									info("Запрос на подключение к: "+rData.getHost()+":"+rData.getPort()+" от "+rData.getAsker());
									try {
										Socket socketToTask = new Socket(rData.getHost(),rData.getPort());
										Socket socketForTaskServer = new Socket(host,port);
										rProtocol.write(socketForTaskServer, Data.code(Codes.RACCOON_SOCKET).setTaskId(rData.getTaskId()));
										exchange(socketToTask,socketForTaskServer,rData.getAsker());
									} catch (Exception e) {
										rProtocol.write(socketToServer, 
												Data.code(Codes.RACCOON_SOCKET_REJECT)
												.setTaskId(rData.getTaskId())
												.setHost(rData.getHost())
												.setPort(rData.getPort()));
									}
								} else if(rData.codeEqual(Codes.RACCOON_BYE)) {
									if(agentFrame!=null) {
										agentFrame.unBlockButtonLogin();
										agentFrame.showLogin();
									}
									bye=true;
									error("Сервер отключил нас");
									this.autoReconnect=false;
									this.agentConnected=false;
									socketToServer.close();
									break;
								} else {
									rProtocol.write(socketToServer,Data.code(Codes.RACCOON_UNKNOWN_CODE));
								}
							}
						} catch (Exception e) {
							throw new ServerUnavailableException();
						}
					} catch (DeviceAlreadyConnectedException e) {
						if(agentFrame!=null) {
							agentFrame.unBlockButtonLogin();
							agentFrame.showLogin();
						}
						error("Устройство уже подключено");
						break;
					} 
				} catch (UserNotFoundException e) {
					if(agentFrame!=null) {
						agentFrame.unBlockButtonLogin();
						agentFrame.showLogin();
					}
					error("Пользователь не найден");
					break;
				} catch (WrongPassException e) {
					if(agentFrame!=null) {
						agentFrame.unBlockButtonLogin();
						agentFrame.showLogin();
					}
					error("Неправильный пароль");
					break;
				} catch (BadTokenException e) {
					if(agentFrame!=null) {
						agentFrame.showLogin();
					}
					error("Неправильный токен, пробуем получить заного..");
					delay(100);
				}
			} catch (ServerUnavailableException e) {
				if(agentFrame!=null&&(this.autoReconnect)) {
					agentFrame.showLogin();
					agentFrame.showDisconnectInLoginPanel();
				}
				if(!bye) {
					error("Сервер["+this.host+":"+this.port+"] недоступен.. "+((this.autoReconnect)?"переподключение через 5с":""));
				} else {
					info("Отключен");
				}
				if(this.autoReconnect) {
					delay(5000);
				}
			} catch (UnknownHostException e) {
				if(agentFrame!=null) {
					agentFrame.unBlockButtonLogin();
					agentFrame.showLogin();
				}
				error("Неправильное имя сервера");
				break;
			} catch (ServerSupportTlsException e) {
				if(agentFrame!=null) {
					agentFrame.unBlockButtonLogin();
					agentFrame.showLogin();
				}
				error("Сервер не поддерживает TLS");
				break;
			} 
		} while(this.autoReconnect&&(!Thread.currentThread().isInterrupted()));
		if(this.agentFrame!=null)
			agentFrame.showNormalInLoginPanel();
	}
	public boolean isBye() {
		return bye;
	}
	public void setBye(boolean bye) {
		this.bye = bye;
	}
	public Socket getSocketToServer() {
		return socketToServer;
	}
	public void setSocketToServer(Socket socketToServer) {
		this.socketToServer = socketToServer;
	}
	public boolean isAutoReconnect() {
		return autoReconnect;
	}
	public void setAutoReconnect(boolean autoReconnect) {
		this.autoReconnect = autoReconnect;
	}
	private void printLine() {
		System.out.println(Ansi.ansi().fgBlue().a("______________________________________").fgDefault());
		
	}
	private void upgradeToTls() throws ServerUnavailableException, ServerSupportTlsException {
		info("Устанавливаем TLS-Соединение");
		try {
			Data rData = rProtocol.send(socketToServer, Data.code(Codes.RACCOON_UPGRADE_TO_TLS));
			if(rData.codeEqual(Codes.RACCOON_UPGRADE_TO_TLS)) {
				info("Сервер готов");
				SSLSocketFactory sslSocketFactory =
						getSslSocketFactory();
				SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(
									socketToServer,
									socketToServer.getInetAddress().getHostAddress(),
									socketToServer.getPort(),
									false);
					    sslSocket.setUseClientMode(true);
					    sslSocket.startHandshake();		
					    socketToServer = sslSocket;
			} else {
				throw new ServerSupportTlsException();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServerUnavailableException();
		}
	}
	public static SSLSocketFactory getSslSocketFactory() throws IOException {
		try {
			TrustManager trm = new X509TrustManager() {
			    public X509Certificate[] getAcceptedIssuers() {return null;}
			    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
			    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
			};
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { trm }, null);
			return sc.getSocketFactory();
		} catch (Exception e) {
			throw new IOException();
		}
	}
	private void connectAgent() throws DeviceAlreadyConnectedException, ServerUnavailableException, BadTokenException {
		info("Подключаем агента");
		for(int i=1;i<=3;i++) {
			try {
				connectExisting();
			} catch (RegenDeviceException e) {
				try {
					connectNew();
				}catch (RegenDeviceException e3) {}
			}
			if(agentConnected)
				break;
		}
		if(!agentConnected)
			throw new ServerUnavailableException();
	}

	private void connectNew() throws ServerUnavailableException, BadTokenException, RegenDeviceException {
		devId = Tools.generateToken();
		try {
			try {
				Tools.createFile(this.host+"-deviceid-"+userId, devId);
			} catch (Exception e) {
				error("Не удалось создать файл deviceid");
			}
			Data rData = rProtocol.send(socketToServer,Data.code(Codes.RACCOON_NEW).
					setToken(token)
					.setDevId(devId)
					.setDevName(Tools.getLocalHostName())
					.setLocalIp(Tools.getLocalIP()));
			if(rData.codeEqual(Codes.RACCOON_CONNECTED)) {
				deviceConnected(rData);
			} else if(rData.codeEqual(Codes.RACCOON_BAD_TOKEN)) {
				throw new BadTokenException();
			} else if(rData.codeEqual(Codes.RACCOON_REGEN)) {
				throw new RegenDeviceException();
			} else {
				throw unknownException(rData);
			}
		} catch (IOException e) {
			throw new ServerUnavailableException();
		}
	}

	private void connectExisting() throws ServerUnavailableException, DeviceAlreadyConnectedException, RegenDeviceException, BadTokenException {
		devId = null;
		try {
			try {
				devId = Tools.readFileToString(this.host+"-deviceid-"+userId).trim();
			} catch (Exception e) {
				throw new RegenDeviceException();
			}
			rProtocol.write(socketToServer,Data.code(Codes.RACCOON_CONNECT).
					setToken(token)
					.setDevId(devId)
					.setDevName(Tools.getLocalHostName())
					.setLocalIp(Tools.getLocalIP()));
			while(!socketToServer.isClosed()) {
				Data rData = null;
				try {
					rData = rProtocol.read(socketToServer);
				} catch (SocketTimeoutException e) {
					rProtocol.write(socketToServer,Data.code(Codes.RACCOON_PING));
					continue;
				}
				if(rData.codeEqual(Codes.RACCOON_CHECKING)) {
					info("Подождите, проверка..");
					continue;
				} if(rData.codeEqual(Codes.RACCOON_CONNECTED)) {
					deviceConnected(rData);
					break;
				}if(rData.codeEqual(Codes.RACCOON_PING)) {
					rProtocol.write(socketToServer,Data.code(Codes.RACCOON_OK));
				} else if(rData.codeEqual(Codes.RACCOON_ALREADY_CONNECTED)) {
					throw new DeviceAlreadyConnectedException();
				} else if(rData.codeEqual(Codes.RACCOON_BAD_TOKEN)) {
					throw new BadTokenException();
				} else if(rData.codeEqual(Codes.RACCOON_REGEN)) {
					throw new RegenDeviceException();
				} else {
					throw unknownException(rData);
				}
			}
		} catch (IOException e) {
			throw new ServerUnavailableException();
		}
	}

	private void deviceConnected(Data rData) {
		serverHost = rData.getServerHost();
		serverIp = rData.getServerIp();
		serverWebPort = rData.getServerWebPort();
		agentConnected=true;
		if(agentFrame!=null) {
			String host = socketToServer.getInetAddress().getHostName();
			agentFrame.setServerHost(host+":"+socketToServer.getPort());
			agentFrame.setWeb("https://"+host+":"+rData.getServerWebPort());
		}
	}

	private static void delay(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {}
	}

	private boolean login() throws ServerUnavailableException, UserNotFoundException, WrongPassException, UnknownHostException {
		try {
			Data rData = rProtocol.send(socketToServer,Data.code(Codes.RACCOON_LOGIN).setLogin(login).setPasswordHash(Tools.hashSHA256(password)));
			if(rData.codeEqual(Codes.RACCOON_SUCCESS_LOGIN)) {
				token = rData.getToken();
				userId = rData.getUserId();
				return true;
			} else if(rData.codeEqual(Codes.RACCOON_WRONG_PASS)) {
				throw new WrongPassException();
			} else if(rData.codeEqual(Codes.RACCOON_USER_NOT_FOUND)) {
				throw new UserNotFoundException();
			} else {
				throw unknownException(rData);
			}
		} catch (UnknownHostException e) {
			throw new UnknownHostException();
		} catch (IOException e) {
			throw new ServerUnavailableException();
		}
	}
	
	private ServerUnavailableException unknownException(Data rData) throws ServerUnavailableException {
		try {
			lastErrorCode=rData.getCodeName();
			error("Неизвестная ошибка: "+lastErrorCode);
			error(JsonParser.toJson(rData));
		} catch (Exception e) {e.printStackTrace();}
		return new ServerUnavailableException();
	}

	private boolean connect() throws ServerUnavailableException, UnknownHostException {
		if(socketToServer!=null) {
			try {
				socketToServer.close();
			} catch (IOException e1) {}
		}
		try {
			socketToServer = new Socket(this.host,this.port);
			Data rData;
			rData = rProtocol.send(socketToServer,Data.code(Codes.RACCOON_PING));
			if(rData.codeEqual(Codes.RACCOON_OK))
				return true;
			throw new ServerUnavailableException();
		} catch (UnknownHostException e) {
			throw e;
		} catch (IOException e) {
			throw new ServerUnavailableException();
		}
	}
	
	private void exchange(Socket agentTaskSocket, Socket agentServerSocket, String asker) throws IOException {
		InputStream clientIS = agentServerSocket.getInputStream();
		InputStream targetIS = agentTaskSocket.getInputStream();
		
		OutputStream clientOS = agentServerSocket.getOutputStream();
		OutputStream targetOS = agentTaskSocket.getOutputStream();
		
		String labelCT = "CLIENT -> TARGET | "+asker+" -> "+agentTaskSocket.getInetAddress()+":"+agentTaskSocket.getPort();
		String labelTC = "TARGET -> CLIENT | "+agentTaskSocket.getInetAddress()+":"+agentTaskSocket.getPort()+" -> "+asker;
		
		new Thread(readThenWrite(clientIS,targetOS,labelCT,null),labelCT).start();
		new Thread(readThenWrite(targetIS,clientOS,labelTC,null),labelTC).start();
	}
	
	private Runnable readThenWrite(InputStream is, OutputStream os, String label, byte[] dataFromClient) {
		return new Runnable() {
			DataInputStream in = new DataInputStream(is);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] data = null;
		    int length = -1;
		    int zeroCount=0;
			@Override
			public void run() {
				if(dataFromClient!=null) {
				    try {
				    	os.write(dataFromClient);
				    } catch(Exception e) {}
				}
			    while ((!Thread.currentThread().isInterrupted())&&agentConnected) {
			    	try {
				        if ((length = in.available()) > 0) {
				        	data = new byte[length];
				            in.readFully(data, 0, length);
				            os.write(data, 0, length);
				            debug(label+" | Bytes sended: "+length);
				        } else {
				        	if(zeroCount++>4096) {
				        		zeroCount=0;
				        		if(debug)
				        			debug(label+" | Long idle, check manually..");
				        		int firstByte=in.read();
				        		if(firstByte!=-1) {
				        			if(debug)
				        				debug(label+" | And.. gotcha!");
					        		length = in.available();
					        		outStream.write(firstByte);
					        		data = new byte[length];
						            in.readFully(data, 0, length);
						            outStream.write(data);
					        		os.write(outStream.toByteArray(), 0, outStream.size());
					        		outStream.reset();
					        		if(debug)
					        			debug(label+" | Bytes sended: "+length+1);
				        		} else {
				        			if(debug)
				        				debug(label+" | socket was closed?");
				        			break;
				        		}
				        	} else {
				        		try {
									Thread.sleep(0,250);
								} catch (InterruptedException e) {
									
								}
				        	}
				        }
			    	} catch (IOException e) {
						break;
					}
			    }
			    if(debug)
			    	debug(label+" | Socket closed");
				try {
					is.close();
					if(debug)
						debug(label+" | is closed");
				} catch (IOException e1) {
					if(debug)					
						debug(label+" | can't close is");
				}
				try {
					os.close();
					if(debug)
						debug(label+" | os closed");
				} catch (IOException e1) {
					if(debug)
						debug(label+" | can't close os");
				}
			}	
		};
	}
	private void debug(String string) {
		if(debug)
			System.out.println(Ansi.ansi().fgBlue().a(format.format(new Date())).fgDefault().a(" | ").fgCyan().a(" DEBUG | ").fgDefault().a(string).fgDefault());
	}
	private void info(String string) {
		if(this.agentFrame!=null)
			this.agentFrame.setLastInfo(string);
		System.out.println(Ansi.ansi().fgBlue().a(format.format(new Date())).fgDefault().a(" | ").fgBrightYellow().a(string).fgDefault());
	}	
	private void error(String string) {
		if(this.agentFrame!=null)
			this.agentFrame.setLastError(string);
		System.out.println(Ansi.ansi().fgBlue().a(format.format(new Date())).fgDefault().a(" | ").fgCyan().a(" ERROR | ").fgBrightRed().a(string).fgDefault());
	}	
}

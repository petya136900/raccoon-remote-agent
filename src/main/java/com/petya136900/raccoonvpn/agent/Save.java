package com.petya136900.raccoonvpn.agent;

import com.petya136900.racoonvpn.tools.JsonParser;

public class Save {
	private String host;
	private Integer port;
	private String login;
	private String password;
	private boolean useTls;
	private boolean debug;
	private boolean autoReconnect;
	public Save(String host, Integer port, String login, String password, boolean autoReconnect, boolean debug,
			boolean useTls) {
		this.host=host;
		this.port=port;
		this.login=login;
		this.password=password;
		this.autoReconnect=autoReconnect;
		this.debug=debug;
		this.useTls = useTls;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isUseTls() {
		return useTls;
	}

	public void setUseTls(boolean useTls) {
		this.useTls = useTls;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isAutoReconnect() {
		return autoReconnect;
	}

	public void setAutoReconnect(boolean autoReconnect) {
		this.autoReconnect = autoReconnect;
	}
	public String toJson() {
		return JsonParser.toJson(this);
	}
	public static Save fromJson(String json) {
		return JsonParser.fromJson(json, Save.class);
	}
}

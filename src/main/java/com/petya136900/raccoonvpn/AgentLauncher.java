package com.petya136900.raccoonvpn;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.petya136900.raccoonvpn.agent.Action;
import com.petya136900.raccoonvpn.agent.Agent;
import com.petya136900.raccoonvpn.agent.AgentFrame;
import com.petya136900.raccoonvpn.agent.HashedPassword;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import com.petya136900.racoonvpn.tools.Tools;

public class AgentLauncher {
	static {
		AnsiConsole.systemInstall();
	}
	public static boolean nogui = false;
	private static String[] argsList;
	public static void main(String[] args) {
		Tools.fixCharset();
		argsList = args;
		if((args.length<1)) { 
			AgentFrame.main(args);
			return;
		}
		nogui=true;
		readArg(new String[] {"--help","help","-help"}, false, null, null, ()->{
			System.out.println(s("--help",sa("-h"),false,false,"Show this message"));
			System.out.println(s("--login",sa("-l"),true,false,"Account login","-l admin"));
			System.out.println(s("--host",sa("-h"),true,false,"RaccoonVPN Server host","-h vpn.furr.cf"));
			System.out.println(s("--port",sa("--aport","-ap","-p"),true,false,"RaccoonVPN Server agents port","-p 8444"));
			System.out.println(s("--use-tls",sa("--tls","-tls"),true,false,"Use tls encryption","--tls false"));
			System.out.println(s("--password",sa("--pass","-pa"),true,false,"Account password","-pa password"));
			System.out.println(s("--autoreconnect",sa("-ar"),false,false,"Autoreconnect if server unavailable","-ar"));
			System.out.println(s("--debug",sa("-d"),false,false,"Show debug messages","--debug"));
			System.out.println(s("--no-gui",sa("--nogui","-ng"),false,false,"Run agent as console app","-ng"));
			System.out.println(Ansi.ansi().fgDefault());
			System.exit(0);
		}); 
		String host = readArg(new String[] {"--host","-h"}, true, null, "vpn.furr.cf");
		String port = readArg(new String[] {"--port","-p"}, true, null, "8444");
		String login = readArg(new String[] {"--login","-l"}, true, null, "admin");
		String password = readArg(new String[] {"--password","--pass","-pa"}, true, null, "password");
		Boolean autoReconnect = readArg(new String[] {"--autoreconnect","-ar"}, false, true, false);
		Boolean debug = readArg(new String[] {"--debug","-d"}, false, true, false);
		String useTls = readArg(new String[] {"--use-tls","--tls","-tls"}, true, null, "true");
		new Agent().connect(host,Integer.parseInt(port),login, HashedPassword.password(password),autoReconnect,debug,Boolean.valueOf(useTls),null);
	}
	private static <T> T readArg(String[] names, boolean needValue, T ifFound, T ifNot) {
		return readArg(names, needValue, ifFound, ifNot, null);
	}
	@SuppressWarnings("unchecked")
	private static <T> T readArg(String[] names, boolean needValue, T ifFound, T ifNot, Action actionIfFound) {
		Stream<String> stream = Stream.of(names);
		for(int i=0;i<argsList.length;i++) {
			String arg = argsList[i];
			Boolean hasNext = argsList.length>i+1;
			String nextArg = hasNext?argsList[i+1]:null;
			stream = Stream.of(names);
			if(stream.anyMatch(x->x.equals(arg))) {
				if(actionIfFound!=null)
					actionIfFound.perfome();
				if(needValue) {
					if(hasNext) {
						try {
							return (T) new String(nextArg);
						} catch (Exception e) {
							return ifNot;
						}
					} else {
						return ifNot;
					}
				} else {
					return ifFound;
				}
			}
			stream.close();
		}
		return ifNot;
	}
	private static String[] sa(String ...elements) {
		return elements;
	}
	private static Ansi s(String argName, String[] altNames, Boolean hasValue,Boolean isArrayValues, String desc) {
		return s(argName,altNames,hasValue,isArrayValues,desc,null);
	}
	private static Ansi s(String argName, String[] altNames, Boolean hasValue,Boolean isArrayValues, String desc, String example) {
		String altNamesString ="";
		if(altNames!=null&&altNames.length>0) {
			altNamesString = Stream.of(altNames)
				.filter(x->x!=null&&x.length()>0)
				.map(x->"OR "+x.trim()+" ")
				.collect(Collectors.joining());
		}
		return Ansi.ansi().a("\n").fgBrightYellow().a(argName).a(" ")
		.fgYellow().a(altNamesString)
		.fgBrightBlue().a((hasValue?(isArrayValues?"[value1,value2,..,valueN] ":"[value] "):""))
		.fgDefault()
		.a("| ")
		.fgBrightGreen()
		.a(desc)
		.fgCyan()
		.a(((example!=null&&example.length()>0)?"\n\tFor example: "+example:""));
	}
}

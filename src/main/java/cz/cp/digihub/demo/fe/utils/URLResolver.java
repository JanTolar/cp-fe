package cz.cp.digihub.demo.fe.utils;

import javax.servlet.http.HttpServletRequest;

public class URLResolver {

	public static String getBaseURl(final HttpServletRequest request) {

		final String scheme = request.getScheme();
		final String serverName = request.getServerName();
		final int serverPort = request.getServerPort();
		final String contextPath = request.getContextPath();
		final StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);
		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		if (url.toString().endsWith("/")) {
			url.append("/");
		}
		return url.toString();
	}

}

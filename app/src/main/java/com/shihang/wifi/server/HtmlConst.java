package com.shihang.wifi.server;

public abstract class HtmlConst {

	public static String DIR_NAME = "WifiTest";
	public static final int PORT = 51345;

	public static final String HTML_STRING =
			         "<html>"
					+ 		"<head>"
					+			"<title>wifi传书</title>"
					+		 	"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
					+		"</head>"
					+ 		"<body>"
					+ 				"<form action=\"\" method=\"post\" enctype=\"multipart/form-data\" name=\"form1\" id=\"form1\">"
					+ 				"<label><input type=\"file\" name=\"file\" id=\"file\" /></label>"
					+	 			"<br/><br/>"
					+ 				"<input type=\"submit\" name=\"button\" id=\"button\" value=\"提交\" />"
					+ 				"</form>"
					+ 		"</body>"
					+"</html>";

}

package com.shihang.wifi.server;

import com.shihang.wifi.util.FileAccessUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class SimpleFileServer extends NanoHTTPD {

	public SimpleFileServer(int port) {
		super(port);
	}

	@Override
	public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms, Map<String, String> files) {
		if (Method.GET.equals(method)) {
			return new Response(HtmlConst.HTML_STRING);
		} else {
			for (String s : files.keySet()) {
				try {
					FileInputStream fis = new FileInputStream(files.get(s));
					FileOutputStream fos = new FileOutputStream(FileAccessUtil.getFile(HtmlConst.DIR_NAME + "/" + parms.get("file")));
					byte[] buffer = new byte[1024];
					while (true) {
						int byteRead = fis.read(buffer);
						if (byteRead == -1) {
							break;
						}
						fos.write(buffer, 0, byteRead);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return new Response(HtmlConst.HTML_STRING);
		}
	}
}

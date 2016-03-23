package com.shihang.wifi.server;

import android.os.Handler;

import com.shihang.wifi.util.FileAccessUtil;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class SimpleFileServer extends NanoHTTPD {

	private Handler handler;

	public SimpleFileServer(int port) {
		super(port);
		handler = new Handler();
	}

	@Override
	public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms, Map<String, String> files) {
		if (Method.GET.equals(method)) {
			return new Response(HtmlConst.HTML_STRING);
		} else {
			for (String s : files.keySet()) {
				String filePath = HtmlConst.DIR_NAME + "/" + parms.get("file");
				try {
					FileInputStream fis = new FileInputStream(files.get(s));
					FileOutputStream fos = new FileOutputStream(FileAccessUtil.getFile(filePath));
					byte[] buffer = new byte[1024];
					while (true) {
						int byteRead = fis.read(buffer);
						if (byteRead == -1) {
							break;
						}
						fos.write(buffer, 0, byteRead);
					}
					handler.post(new TransmissionResult(true, filePath, "传输完成" ));
				} catch (Exception e) {
					e.printStackTrace();
					handler.post(new TransmissionResult(false, filePath, "传输失败:" + e.toString()));
				}
			}
			return new Response(HtmlConst.HTML_STRING);
		}
	}

	class TransmissionResult implements Runnable{

		private final boolean success;
		private final String filePath;
		private final String msg;

		public TransmissionResult(boolean success, String filePath, String msg){
			this.success = success;
			this.filePath = filePath;
			this.msg = msg;
		}

		@Override
		public void run() {
			WifiTransmissionManager.transmissionResult(success, filePath, msg);
		}
	}

}

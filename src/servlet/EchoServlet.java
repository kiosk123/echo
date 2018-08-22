package servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.swing.internal.plaf.basic.resources.basic;

@WebServlet("/EchoServlet")
public class EchoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(EchoServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ServletInputStream in= request.getInputStream()){
			byte[] buff = new byte[1024];
			while(!in.isFinished()) {
				int len = in.read(buff, 0, buff.length);
				baos.write(buff, 0, len);
			}
		} catch (IOException e) {
			logger.error("When reading data, error occurred", e);
			throw e;
		}
		
		logger.info("read message is {}", baos.toString());
		
		try (ServletOutputStream out = response.getOutputStream()){
			out.write(baos.toByteArray());
		} catch (IOException e) {
			logger.error("When writing data, error occurred", e);
			throw e;
		}
		
		logger.info("success creating response message - {}", baos.toString());
	}

}

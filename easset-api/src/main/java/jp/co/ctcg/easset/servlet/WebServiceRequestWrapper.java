package jp.co.ctcg.easset.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import jp.co.ctcg.easset.flex_common.Logging;

/**
 * Webサービスリクエストを処理するためのラッパークラス
 */
public class WebServiceRequestWrapper extends HttpServletRequestWrapper {
    /**
     * リクエストコンテンツを保持
     */
    private byte[] requestBody;

    /**
     * e申請からのHTTPリクエストコンテンツを、eAsset-Webサービスで処理できるように変換する。
     * @param request HTTPリクエスト
     */
    public WebServiceRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        // リクエスト取得
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        char c[] = new char[1024];
        int len;
        StringBuffer insb = new StringBuffer();
        while((len = isr.read(c) ) != -1) {
            insb.append(c, 0, len);
        }

        // リクエスト変換
        // ネームスペース無しの承認リクエストにネームスペースを付加
        String before = insb.toString();
        String after = before;
        String[] replaceMethods = {"approve", "approveSuperior"};
        for(String replaceMethod : replaceMethods) {
            after = after.replaceFirst("<SOAP-ENV:Header/>\\s*<SOAP-ENV:Body>\\s*<" + replaceMethod + ">", "<SOAP-ENV:Header/><SOAP-ENV:Body><ns:" + replaceMethod + " xmlns:ns=\"http://ws.easset.ctcg.co.jp\">");
            after = after.replaceFirst("</" + replaceMethod + ">\\s*</SOAP-ENV:Body>\\s*</SOAP-ENV:Envelope>", "</ns:" + replaceMethod + "></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        }
        // リクエストログ
        StringBuffer sb = new StringBuffer();
        sb.append(" - Entity: ").append(before);
        if(!before.equals(after)) {
            sb.append("\n - Entity(Replaced): ").append(after);
        }        
        Logging.info("HTTP SOAP REQUEST : " + sb.toString());

        this.requestBody = after.getBytes();
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrpper( this.requestBody );
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getReader()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
    }

    /**
     * WebサービスリクエストのInputStreamを処理するためのラッパークラス
     */
    public class ServletInputStreamWrpper extends ServletInputStream {
        /**
         * リクエストInputStream
         */
        private ByteArrayInputStream inputStream;

        /**
         * 
         * @param buffer
         */
        public ServletInputStreamWrpper(byte[] b) {
            this.inputStream = new ByteArrayInputStream(b);
        }
        
        /**
         * @see javax.servlet.ServletInputStream#available()
         */
        @Override
        public int available() throws IOException {
            return inputStream.available();
        }
        
        /**
         * @see javax.servlet.ServletInputStream#read()
         */
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
        
        /**
         * @see javax.servlet.ServletInputStream#read(byte[], int, int)
         */
        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return inputStream.read(b, off, len);
        }
    }
}
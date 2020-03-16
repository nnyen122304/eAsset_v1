package jp.co.ctcg.easset.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Date;
import java.util.Iterator;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class MessageCreator {
	/**
	 * メールヘッダ
	 */
	private static final String MAIL_HEAD_X_PRIORITY = "X-Priority";

	private static final String CONT_DISPOSITION = "Content-Disposition";
	private static final String CONT_TYPE = "Content-Type";

	private static final String CONT_DISPOSITION_INLINE = "inline";

	/**
	 * 件名
	 */
	private String _subject;

	/**
	 * 送り元メールアドレス
	 */
	private InternetAddress _from;

	/**
	 * メールの送先アドレスのリスト
	 */
	private HashSet<InternetAddress> _to = new HashSet<InternetAddress>();

	/**
	 * CCのリスト
	 */
	private HashSet<InternetAddress> _cc = new HashSet<InternetAddress>();

	/**
	 * BCCのリスト
	 */
	private HashSet<InternetAddress> _bcc = new HashSet<InternetAddress>();

	/**
	 * Flag
	 */
	private Flags.Flag _flag;

	/**
	 * 重要度
	 */
	private int _priority;

	/**
	 * メールの本文
	 */
	private String _text;
	private String _simpleText;

	/**
	 * テキストをHTMLとしてメッセージを作成するかどうかのフラグ
	 */
	private boolean _htmlFlag;

	/**
	 * テキストのエンコード
	 */
	private String _encode;

	/**
	 * 添付ファイル：絶対パス文字列を格納する。
	 */
	private ArrayList<File> _files = new ArrayList<File>();

	/**
	 * 添付メッセージ
	 */
	private Message[] _childMessages;

	/**
	 * smtpサーバとのセッション
	 */
	private Session _session;

	/**
	 * Constructor
	 *
	 * @param session smtp session
	 */
	public MessageCreator(Session session) {
		clearMessageBody();
		_session = session;
	}

	/**
	 * メッセージの初期化
	 */
	public void clearMessage() {
		clearMessageBody();
	}

	/**
	 * Toの初期化
	 */
	public void clearTo() {
		_to.clear();
	}

	/**
	 * CCの初期化
	 */
	public void clearCC() {
		_cc.clear();
	}

	/**
	 * BCCの初期化
	 */
	public void clearBCC() {
		_bcc.clear();
	}

	/**
	 * 添付ファイルの初期化
	 */
	public void clearFiles() {
		_files.clear();
	}

	/**
	 * 添付メッセージの初期化
	 */
	public void clearChildMessages() {
		_childMessages = null;
	}

	/**
	 * 件名のセット
	 *
	 * @param subject 件名
	 */
	public void setSubject(String subject) {
		if (subject == null) subject = "";

		_subject = subject;
	}

	/**
	 * Fromのセット
	 *
	 * @param from メールの送り元
	 */
	public void setFrom(String from) {
		if (from == null) from = "";
		try {
			_from = new InternetAddress(getAddress(from), getPersonal(from), Charset.JIS);
		} catch (UnsupportedEncodingException usee) {
		}
	}

	/**
	 * Fromのセット
	 *
	 * @param from メールの送り元
	 * @param enc エンコード
	 */
	public void setFrom(String from, String enc) throws UnsupportedEncodingException {
		if (from == null) from = "";
		_from = new InternetAddress(getAddress(from), getPersonal(from), enc);
	}

	/**
	 * Toのセット
	 *
	 * @param to メールの送り先
	 */
	public void addTo(String to) {
		try {
			addToBody(getAddress(to), getPersonal(to), Charset.JIS);
		} catch (UnsupportedEncodingException uee) {
		}
	}

	/**
	 * Toのセット
	 *
	 * @param to メールの送り先
	 * @param enc エンコード
	 */
	public void addTo(String to, String enc) throws UnsupportedEncodingException {
		addToBody(getAddress(to), getPersonal(to), enc);
	}

	/**
	 * Toのセット
	 *
	 * @param to メールの送り先
	 */
	public void addTo(String[] to) {
		try {
			if (to != null) {
				for (int i = 0; i < to.length; i++) {
					addToBody(getAddress(to[i]), getPersonal(to[i]), Charset.JIS);
				}
			}
		} catch (UnsupportedEncodingException uee) {
		}
	}

	/**
	 * CCのセット
	 *
	 * @param cc CC
	 */
	public void addCC(String cc) {
		try {
			addCCBody(getAddress(cc), getPersonal(cc), Charset.JIS);
		} catch (UnsupportedEncodingException uee) {
		}
	}

	/**
	 * CCのセット
	 *
	 * @param cc CC
	 * @param enc エンコード
	 */
	public void addCC(String cc, String enc) throws UnsupportedEncodingException {
		addCCBody(getAddress(cc), getPersonal(cc), enc);
	}

	/**
	 * CCのセット
	 *
	 * @param cc CC
	 */
	public void addCC(String[] cc) {
		try {
			if (cc != null) {
				for (int i = 0; i < cc.length; i++) {
					addCCBody(getAddress(cc[i]), getPersonal(cc[i]), Charset.JIS);
				}
			}
		} catch (UnsupportedEncodingException uee) {
		}
	}

	/**
	 * BCCのセット
	 *
	 * @param bcc BCC
	 */
	public void addBCC(String bcc) {
		try {
			addBCCBody(getAddress(bcc), getPersonal(bcc), Charset.JIS);
		} catch (UnsupportedEncodingException uee) {
		}
	}

	/**
	 * BCCのセット
	 *
	 * @param bcc BCC
	 * @param enc エンコード
	 */
	public void addBCC(String bcc, String enc) throws UnsupportedEncodingException {
		addBCCBody(getAddress(bcc), getPersonal(bcc), enc);
	}

	/**
	 * BCCのセット
	 *
	 * @param bcc BCC
	 */
	public void addBCC(String[] bcc) {
		try {
			if (bcc != null) {
				for (int i = 0; i < bcc.length; i++) {
					addBCCBody(getAddress(bcc[i]), getPersonal(bcc[i]), Charset.JIS);
				}
			}
		} catch (UnsupportedEncodingException uee) {
		}
	}

	public void setFrom(InternetAddress[] address) {
		if (address != null) {
			int size = address.length;
			if (size > 0) {
				_from = address[0];
			}
		}
	}

	public void addTo(InternetAddress[] address) {
		if (address != null) {
			int size = address.length;
			for (int i = 0; i < size; i++) {
				_to.add(address[i]);
			}
		}
	}

	public void addCC(InternetAddress[] address) {
		if (address != null) {
			int size = address.length;
			for (int i = 0; i < size; i++) {
				_cc.add(address[i]);
			}
		}
	}

	public void addBCC(InternetAddress[] address) {
		if (address != null) {
			int size = address.length;
			for (int i = 0; i < size; i++) {
				_bcc.add(address[i]);
			}
		}
	}

	/**
	 * Flagのセット
	 *
	 * @param flag Flag
	 */
	public void setFlag(Flags.Flag flag) {
		_flag = flag;
	}

	/**
	 * 重要度のセット
	 *
	 * @param priority 重要度:1(high) - 5(low)
	 */
	public void setPriority(int priority) {
		if (priority >= 1) priority = 1;
		else if (priority <= 5) priority = 5;

		_priority = priority;
	}

	/**
	 * 本文のセット
	 *
	 * @param text 本文
	 */
	public void setText(String text) {
		setTextBody(text, Charset.JIS, false);
	}

	/**
	 * 簡易本文のセット
	 *
	 * @param text 簡易本文
	 */
	public void setSimpleText(String text) {
		if (text != null) _simpleText = text;
	}

	/**
	 * 本文のセット
	 *
	 * @param text 本文
	 * @param enc エンコード
	 */
	public void setText(String text, String enc) {
		setTextBody(text, enc, false);
	}

	/**
	 * 本文のセット
	 *
	 * @param text 本文
	 * @param htmlFlag 本文をHTMLとして送るかどうか
	 */
	public void setText(String text, boolean htmlFlag) {
		setTextBody(text, _encode, htmlFlag);
	}

	/**
	 * 本文のセット
	 *
	 * @param text 本文
	 * @param enc エンコード
	 * @param htmlFlag 本文をHTMLとして送るかどうか
	 */
	public void setText(String text, String enc, boolean htmlFlag) {
		setTextBody(text, enc, htmlFlag);
	}

	/**
	 * 添付ファイルの追加
	 *
	 * @param text ファイルの絶対パス
	 */
	public void addFile(File file) {
		addFileBody(file);
	}

	/**
	 * 添付ファイルの追加
	 *
	 * @param text ファイルの絶対パス
	 */
	public void addFiles(File[] file) {
		if (file != null) {
			for (int i = 0; i < file.length; i++) {
				addFileBody(file[i]);
			}
		}
	}

	/**
	 * 添付メッセージのセット
	 */
	public void setChildMessages(Message[] msg) {
		_childMessages = msg;
	}

	/**
	 * smtpセッションのセット
	 */
	public void setSession(Session session) {
		_session = session;
	}

	/**
	 * 件名の取得
	 *
	 * @return 件名
	 */
	public String getSubject() {
		return _subject;
	}

	/**
	 * Fromの取得
	 *
	 * @return From
	 */
	public String getFrom() {
		return getPersonalAddress(_from);
	}

	/**
	 * Toの取得
	 *
	 * @return To
	 */
	public String[] getTo() {
		int size = _to.size();

		String[] ret = new String[size];

		Iterator<InternetAddress> iter = _to.iterator();
		int i = 0;
		while (iter.hasNext()) {
			ret[i++] = getPersonalAddress((InternetAddress) iter.next());
		}
		return ret;
	}

	/**
	 * CCの取得
	 *
	 * @return CCの文字列配列
	 */
	public String[] getCC() {
		int size = _cc.size();

		String[] ret = new String[size];

		Iterator<InternetAddress> iter = _cc.iterator();
		int i = 0;

		while (iter.hasNext()) {
			ret[i++] = getPersonalAddress((InternetAddress) iter.next());
		}
		return ret;
	}

	/**
	 * BCCの取得
	 *
	 * @return BCCの文字列配列
	 */
	public String[] getBCC() {
		int size = _bcc.size();

		String[] ret = new String[size];

		Iterator<InternetAddress> iter = _bcc.iterator();
		int i = 0;

		while (iter.hasNext()) {
			ret[i++] = getPersonalAddress((InternetAddress) iter.next());
		}
		return ret;
	}

	/**
	 * Flagの取得
	 *
	 * @return Flag
	 */
	public Flags.Flag getFlag() {
		return _flag;
	}

	/**
	 * 重要度の取得
	 *
	 * @return 重要度
	 */
	public int getPriority() {
		return _priority;
	}

	/**
	 * 本文の取得
	 *
	 * @return 本文
	 */
	public String getText() {
		return _text;
	}

	/**
	 * 簡易本文の取得
	 *
	 * @return 簡易本文
	 */
	public String getSimpleText() {
		return _simpleText;
	}

	/**
	 * 添付ファイルに指定したパスの取得
	 *
	 * @return 絶対パスの文字列配列
	 */
	public ArrayList<File> getFiles() {
		return _files;
	}

	/**
	 * smtpセッションの取得
	 */
	public Session getSession() {
		return _session;
	}

	/**
	 * 本文がHTLかどうかを判別
	 */
	public boolean isHTML() {
		return _htmlFlag;
	}

	/**
	 * 属性に設定したデータを元にメッセージを作成
	 *
	 * @return Message
	 * @throws MessagingException
	 */
	public MimeMessage createMessage() throws MessagingException {
		return createMessageBody();
	}

	/**
	 * 属性に設定したデータを元にメッセージを作成
	 *
	 * @return Message
	 * @throws MessagingException
	 */
	public void replaceText(MimeMessage msg) throws MessagingException, IOException {
		String text = _text; // 本文
		String simpleText = _simpleText; // 簡易本文

		Object obj = msg.getContent();

		if (obj instanceof MimeMultipart) {
			MimeMultipart multi = (MimeMultipart) obj;
			if (multi.getContentType().indexOf(MimeType.MULTIPART_MIXED) != -1) {
				MimeBodyPart body = (MimeBodyPart) multi.getBodyPart(0);
				Object obj2 = body.getContent();
				if (obj2 instanceof MimeMultipart) {
					multi = (MimeMultipart) obj;
				} else {
					body.setText(text, _encode);
					// Set Text
					if (_htmlFlag) {
						// html
						body.setHeader(CONT_TYPE, MimeType.TEXT_HTML + "; charset=" + _encode);
					}
				}
			}

			// HTMLメールで、alternativeでラップされている
			if (multi.getContentType().indexOf(MimeType.MULTIPART_ALTERNATIVE) != -1) {
				MimeBodyPart body = (MimeBodyPart) multi.getBodyPart(0); // text
				MimeBodyPart bodyHtml = (MimeBodyPart) multi.getBodyPart(1); // html

				body.setText(simpleText, _encode);
				bodyHtml.setText(text, _encode);
				// html
				bodyHtml.setHeader(CONT_TYPE, MimeType.TEXT_HTML + "; charset=" + _encode);
			}
		} else {
			msg.setText(text, _encode);
			// Set Text
			if (_htmlFlag) {
				// html
				msg.setHeader(CONT_TYPE, MimeType.TEXT_HTML + "; charset=" + _encode);
			}
		}
	}

	/**
	 * msgで渡したメールを送信
	 *
	 * @param msg Message
	 */
	public void send(Message msg) throws MessagingException {
		sendBody(msg);
	}

	/**
	 * メール送信 プロパティに設定したデータを元にメッセージを作成して送る
	 *
	 * @return 送信されたメッセージオブジェクト
	 */
	public MimeMessage send() throws MessagingException {
		MimeMessage msg = createMessageBody();
		sendBody(msg);
		return msg;
	}

	/**
	 * clearMessageメソッドの本体
	 */
	private void clearMessageBody() {
		_subject = "";
		_from = null;
		_to.clear();
		_cc.clear();
		_bcc.clear();
		_flag = null;
		_priority = 3;
		_text = null;
		_simpleText = null;
		_encode = Charset.JIS;
		_htmlFlag = false;
		_files.clear();
		_childMessages = null;
	}

	/**
	 * addToメソッドの本体
	 */
	private void addToBody(String address, String personal, String enc) throws UnsupportedEncodingException {
		if (address != null && !address.equals("")) {
			_to.add(new InternetAddress(address, personal, enc));
		}
	}

	/**
	 * addCCメソッドの本体
	 */
	private void addCCBody(String address, String personal, String enc) throws UnsupportedEncodingException {
		if (address != null && !address.equals("")) {
			_cc.add(new InternetAddress(address, personal, enc));
		}
	}

	/**
	 * addBCCメソッドの本体
	 */
	private void addBCCBody(String address, String personal, String enc) throws UnsupportedEncodingException {
		if (address != null && !address.equals("")) {
			_bcc.add(new InternetAddress(address, personal, enc));
		}
	}

	/**
	 * addFileメソッドの本体
	 */
	private void addFileBody(File file) {
		if (file != null) {
			_files.add(file);
		}
	}

	/**
	 * setTextメソッドの本体
	 */
	private void setTextBody(String text, String enc, boolean htmlFlag) {
		if (text == null) text = "";
		_text = text;
		_encode = enc;
		_htmlFlag = htmlFlag;
	}

	// /**
	//  * ListからString配列を得る。
	//  */
	// private String[] getStringArray(List list) {
	// 	if (list == null) return null;
	// 	int size = list.size();

	// 	String[] ret = new String[size];

	// 	for (int i = 0; i < size; i++) {
	// 		ret[i] = list.get(i).toString();
	// 	}

	// 	return ret;
	// }

	/**
	 * 住所情報を取得する
	 */
	private String getAddress(String addr) {
		if (addr == null) return null;
		int start_pos = addr.indexOf("<");
		int end_pos;

		if (start_pos < 0) {
			return addr;
		}
		end_pos = addr.indexOf(">", start_pos);
		if (end_pos < 0) {
			return null;
		}
		return addr.substring(start_pos + 1, end_pos);
	}

	/**
	 * 個人情報を取得する
	 */
	private String getPersonal(String addr) {
		if (addr == null) return null;

		int pos = addr.indexOf("<");

		if (pos < 0) {
			return null;
		}
		return addr.substring(0, pos);
	}

	/**
	 * InternetAddressからpersonal <address>を得る
	 */
	private String getPersonalAddress(InternetAddress ia) {
		if (ia == null) {
			return "";
		}
		String addr = ia.getAddress();
		String personal = ia.getPersonal();
		if (addr == null) addr = "";
		if (personal == null) {
			personal = "";
		} else {
			addr = "<" + addr + ">";
		}
		return personal + addr;
	}

	/**
	 * sendメソッドの本体
	 */
	private void sendBody(Message msg) throws MessagingException {
		// Send
		msg.setSentDate(new Date());
		Transport.send(msg);
	}

	/**
	 * createMessageメソッドの本体。
	 */
	private MimeMessage createMessageBody() throws MessagingException {
		if (_from == null || (_to.size() == 0)) {
			throw new MessagingException("PLEASE SET PROPERTY");
		}

		MimeMessage msg = new MimeMessage(_session);

		// Set From
		msg.setFrom(_from);

		// Set To
		int size = _to.size();

		InternetAddress[] to = new InternetAddress[size];

		Iterator<InternetAddress> iter = _to.iterator();
		int ct = 0;

		while (iter.hasNext()) {
			to[ct++] = (InternetAddress) iter.next();
		}
		msg.setRecipients(Message.RecipientType.TO, to);

		// Set CC & BCC
		size = _cc.size();
		if (size != 0) {
			InternetAddress[] cc = new InternetAddress[size];
			iter = _cc.iterator();

			ct = 0;
			while (iter.hasNext()) {
				cc[ct++] = (InternetAddress) iter.next();
			}
			msg.setRecipients(Message.RecipientType.CC, cc);
		}
		size = _bcc.size();
		if (size != 0) {
			InternetAddress[] bcc = new InternetAddress[size];
			iter = _bcc.iterator();

			ct = 0;
			while (iter.hasNext()) {
				bcc[ct++] = (InternetAddress) iter.next();
			}
			msg.setRecipients(Message.RecipientType.BCC, bcc);
		}

		// Set Subject
		//		if( _subject != null && !_subject.trim().equals( "" ) ) {
		//			msg.setSubject( encoder.encodeHeader(_subject) , "text/plain;
		// charset=\"ISO-2022-JP\"" );
		//			msg.setSubject( encoder.encodeHeader(_subject) , _encode );
		//		} else {
		try {
			String subject = _subject;
			if(subject == null) subject = "";
			if (_subject.length() <= 1) {
				_subject += " "; // 短すぎるとエラーが発生するため
			}
			msg.setSubject(MimeUtility.encodeText(_subject, _encode, "B"));
		} catch (UnsupportedEncodingException e) {
			throw new MessagingException(e.getMessage(), e);
		}
		//			msg.setSubject( encode( _subject ), _encode );
		//		}
		// Set Flag
		if (_flag != null) {
			msg.setFlag(_flag, true);
		}

		// Set Priority
		if (_priority != 3) {
			msg.setHeader(MAIL_HEAD_X_PRIORITY, String.valueOf(_priority));
		}

		String text = _text; // 本文
		if (text == null) text = "";
		if (text.length() <= 1) {
			text += " "; // 短すぎるとエラーが発生するため
		}
		if (text.charAt(text.length() - 1) != '\n') {
			text += "\n"; // 最後が改行じゃないとヘンな文字が挿入されるため
		}

		/*
		 * if( _text != null && !_text.trim().equals( "" ) ) { text =
		 * EncodeToJis.encodeBody( _text ); // エンコード } else { text = _text; }
		 */
		// multipart
		if ((_childMessages != null) || (_files.size() != 0)) {
			// Create MessageBody
			Multipart mp = new MimeMultipart();
			MimeBodyPart body;

			body = new MimeBodyPart();
			body.setText(text, _encode);

			// Set Text
			if (_htmlFlag) {
				// html
				body.setHeader(CONT_TYPE, MimeType.TEXT_HTML + "; charset=" + _encode);
				if (_simpleText != null && !_simpleText.equals("")) {
					MimeMultipart mpAlternative = new MimeMultipart("alternative");
					MimeBodyPart simpleBody = new MimeBodyPart();
					simpleBody.setText(_simpleText, _encode);
					mpAlternative.addBodyPart(simpleBody);
					mpAlternative.addBodyPart(body);
					MimeBodyPart bd = new MimeBodyPart();
					bd.setContent(mpAlternative);
					mp.addBodyPart(bd);
				} else {
					mp.addBodyPart(body);
				}
			} else {
				mp.addBodyPart(body);
			}

			// Set Transfer Messages
			if (_childMessages != null) {
				for (int i = 0; i < _childMessages.length; i++) {
					body = new MimeBodyPart();
					body.setContent(_childMessages[i], MimeType.MESSAGE_RFC822);
					body.setHeader(CONT_DISPOSITION, CONT_DISPOSITION_INLINE);
					mp.addBodyPart(body);
				}
			}

			// Set File
			for (int i = 0; i < _files.size(); i++) {
				File file = (File) _files.get(i);
				if (file.exists() && file.isFile()) {
					FileDataSource fds = new FileDataSource(file);
					body = new MimeBodyPart();
					/*
					 * if( fds.getContentType().indexOf( MimeType.TEXT_PLAIN ) != -1 ) { }
					 * else
					 */{
						body.setDataHandler(new DataHandler(fds));
					}
					String fname = file.getName();
					try {
						fname = MimeUtility.encodeText(fname, _encode, null);
					} catch (UnsupportedEncodingException uee) {
						throw new MessagingException(uee.getMessage(), uee);
					}
					body.setFileName(fname);
				} else {
					throw new MessagingException("FILE NOT EXISTS: " + file.getName());
				}
				mp.addBodyPart(body);
			}
			msg.setContent(mp);
		} else {
			if (_htmlFlag && _simpleText != null && !_simpleText.equals("")) {
				MimeBodyPart body;
				body = new MimeBodyPart();
				body.setText(text, _encode);
				body.setHeader(CONT_TYPE, MimeType.TEXT_HTML + "; charset=" + _encode);
				MimeMultipart mpAlternative = new MimeMultipart("alternative");
				MimeBodyPart simpleBody = new MimeBodyPart();
				simpleBody.setText(_simpleText, _encode);
				mpAlternative.addBodyPart(simpleBody);
				mpAlternative.addBodyPart(body);
				msg.setContent(mpAlternative);
			} else {
				msg.setText(text, _encode);
				// Set Text
				if (_htmlFlag) {
					msg.setHeader(CONT_TYPE, MimeType.TEXT_HTML + "; charset=" + _encode);
				}
			}
		}
		return msg;
	}

	/**
	 * messageからメッセージを復元する
	 */
	public void readMessage(MimeMessage message, String savePath) throws MessagingException, IOException {
		if (message == null) throw new MessagingException("message is null");
		clearMessageBody();
		setFrom((InternetAddress[]) message.getFrom());
		addTo((InternetAddress[]) message.getRecipients(Message.RecipientType.TO));
		addCC((InternetAddress[]) message.getRecipients(Message.RecipientType.CC));
		addBCC((InternetAddress[]) message.getRecipients(Message.RecipientType.BCC));
		_subject = decode(message.getSubject());

		readPart(message, savePath, true);
	}

	/**
	 * メッセージの解析
	 *
	 * @param part 解析するパート
	 * @param savePath 保存するディレクトリ
	 * @param flag true : 通常 false : alternativeの最初
	 */
	private void readPart(Part part, String savePath, boolean flag) throws MessagingException, IOException {
		if (part == null) throw new MessagingException("part is null");

		Object content = part.getContent();

		if (content == null) throw new MessagingException("content is null");

		if (content instanceof Multipart) {
			Multipart mPart = (Multipart) content;
			int ct = mPart.getCount();
			boolean isAlt = false;
			if (mPart.getContentType().indexOf(MimeType.MULTIPART_ALTERNATIVE) != -1) {
				isAlt = true;
			}
			for (int i = 0; i < ct; i++) {
				if (isAlt && i == 0) {
					readPart(mPart.getBodyPart(i), savePath, false);
				} else {
					readPart(mPart.getBodyPart(i), savePath, true);
				}
			}
		} else {
			InputStream is = null;
			try {
				is = part.getInputStream();

				if (_text == null && flag) { // TEXTセット
					ByteArrayOutputStream baos = null;
					try {
						baos = new ByteArrayOutputStream();
						byte[] b = new byte[2048];
						int ct = 0;
						while (ct != -1) {
							baos.write(b, 0, ct);
							ct = is.read(b);
						}
						_text = decode(baos.toString("JISAutoDetect"));
						if (part.getContentType().indexOf(MimeType.TEXT_HTML) != -1) {
							_htmlFlag = true;
						}
					} finally {
						if (baos != null) baos.close();
					}
				} else if (_simpleText == null && !flag) {
					ByteArrayOutputStream baos = null;
					try {
						baos = new ByteArrayOutputStream();
						byte[] b = new byte[2048];
						int ct = 0;
						while (ct != -1) {
							baos.write(b, 0, ct);
							ct = is.read(b);
						}
						_simpleText = decode(baos.toString("JISAutoDetect"));
					} finally {
						if (baos != null) baos.close();
					}
				} else { // 添付ファイル
					String fname = part.getFileName();
					try {
						fname = MimeUtility.decodeText(fname);
					} catch (UnsupportedEncodingException uee) {
					}

					BufferedOutputStream bos = null;

					try {
						File file = new File(savePath + File.separator + fname);
						bos = new BufferedOutputStream(new FileOutputStream(file));
						byte[] b = new byte[2048];
						int ct = 0;
						while (ct != -1) {
							bos.write(b, 0, ct);
							ct = is.read(b);
						}
						addFile(file);
					} finally {
						if (bos != null) bos.close();
					}
				}
			} finally {
				if (is != null) is.close();
			}
		}
	}

	// private String encode(String str) {

	// 	if (str == null || str.equals("")) return str;

	// 	char[] c = str.toCharArray(); // 文字配列を取得

	// 	for (int i = 0; i < c.length; i++) {
	// 		switch (c[i]) {
	// 		case 65374:
	// 			c[i] = 12316; // ～
	// 			break;
	// 		case 65293:
	// 			c[i] = 8722; // －
	// 			break;
	// 		}
	// 	}

	// 	return new String(c);
	// }

	private String decode(String str) {

		if (str == null || str.equals("")) return str;

		char[] c = str.toCharArray(); // 文字配列を取得

		for (int i = 0; i < c.length; i++) {
			switch (c[i]) {
			case 12316:
				c[i] = 65374; // ～
				break;
			case 8722:
				c[i] = 65293; // －
				break;
			}
		}

		return new String(c);
	}
}

/**
 * This class implements a typed DataSource from : an InputStream a byte array a
 * String
 */

class ByteArrayDataSource implements DataSource {
	private byte[] data; // data
	private String type; // content-type

	/* Create a datasource from an input stream */
	ByteArrayDataSource(InputStream is, String type) {
		this.type = type;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int ch;

			while ((ch = is.read()) != -1)
				// must be made more efficient by
				// doing buffered reads, rather than one byte reads
				os.write(ch);
			data = os.toByteArray();
		} catch (IOException ioex) {
		}
	}

	/* Create a datasource from a byte array */
	ByteArrayDataSource(byte[] data, String type) {
		this.data = data;
		this.type = type;
	}

	/* Create a datasource from a String */
	ByteArrayDataSource(String data, String type, String charset) {
		try {
			// Assumption that the string contains only ascii
			// characters ! Else just pass in a charset into this
			// constructor and use it in getBytes()
			this.data = data.getBytes(charset);
		} catch (UnsupportedEncodingException uex) {
		}

		this.type = type;
	}

	public InputStream getInputStream() throws IOException {
		if (data == null) {
			throw new IOException("no data");
		}
		return new ByteArrayInputStream(data);
	}

	public OutputStream getOutputStream() throws IOException {
		throw new IOException("cannot do this");
	}

	public String getContentType() {
		return type;
	}

	public String getName() {
		return "dummy";
	}
}

class Charset {
	public static final String JIS = "iso-2022-jp";
	public static final String ASCII = "iso-8859-1";
}

class MimeType {
	public static final String MULTIPART = "multipart";
	public static final String MULTIPART_MIXED = "multipart/mixed";
	public static final String MULTIPART_RELATED = "multipart/related";
	public static final String MULTIPART_ALTERNATIVE = "multipart/alternative";
	public static final String MULTIPART_DIGEST = "multipart/digest";

	public static final String TEXT = "text";
	public static final String TEXT_PLAIN = "text/plain";
	public static final String TEXT_HTML = "text/html";

	public static final String IMAGE = "image";

	public static final String MESSAGE = "message";
	public static final String MESSAGE_RFC822 = "message/rfc822";
}
/*
 * @(#)LDAPManager.java
 */
package jp.co.ctcg.easset.util;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.template.utils.SysException;

/**
 * LDAP処理クラス
 */

public class LDAPManager {

	private DirContext context;		// DIRコンテキスト

	private String host;				// LDAPサーバーホスト
	private int port;				// LDAPサービスポート
	private String baseDn;			// ベースDN
	private String principal;		// プリンシパル
	private String credentials;		// パスワード

	/**
	 * LDAPコンテキスト取得
	 * @throws SysException
	 */
	public void connectDirContext() throws SysException {

		/* 各種接続環境情報の設定 */
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		StringBuffer providerUrl = new StringBuffer("ldap://");
		providerUrl.append(host);
		providerUrl.append(":");
		providerUrl.append(port);
//		providerUrl.append("/");
//		providerUrl.append(Function.nvl(baseDn, ""));
		env.put(Context.PROVIDER_URL, providerUrl.toString());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, principal);
		env.put(Context.SECURITY_CREDENTIALS, credentials);
		env.put("com.sun.jndi.ldap.connect.timeout", "10000");

		/* ディレクトリサーバとの接続 */
		try {
			context = new InitialDirContext(env);
		} catch (NamingException e) {
			throw new SysException("LDAPサーバー接続に失敗しました。管理者に連絡して下さい。", e);
		}
	}

	/**
	 * LDAPコンテキスト取得
	 * @param ldapName 接続先LDAP名
	 * @throws SysException
	 */
	public void connectDirContext(String ldapName) throws SysException {

		MasterDAO mDao = new MasterDAO();

		List<CodeName> cList = null;
		try {
			cList = mDao.selectCodeNameList("LDAP_INFO", ldapName, null, null, null, true);

		} catch (Exception e) {
			throw new SysException("LDAP接続情報の取得に失敗しました。管理者に連絡して下さい。", e);
		}

		if(cList == null || cList.size() == 0){
			throw new SysException("LDAP接続情報が見つかりません。管理者に連絡して下さい。");
		}

		CodeName ldapInfo = (CodeName)cList.get(0);

		host = Function.nvl(ldapInfo.getValue1(), "");
		port = Integer.valueOf(Function.nvl(ldapInfo.getValue2(), "0")).intValue();
		baseDn = Function.nvl(ldapInfo.getValue3(), "");
		principal = Function.nvl(ldapInfo.getValue4(), "");
		credentials = Function.nvl(ldapInfo.getValue5(), "");

		connectDirContext();
	}

	/**
     * LDAPに対するSEARCHコマンドの実行
     *
     * @param base 検索ルートDN
     * @param filter LDAP検索フィルタ文字列
     * @param scope スコープの指定
     * @return エントリーを表すSearchResultクラスを格納したNamingEnumeration
     * @exception LdapControlException LDAPコントロール用エクセプション
     */
    private NamingEnumeration<SearchResult> search(String base, String filter, int scope) throws SysException
    {
        /* スコープの指定 */
        SearchControls sc = new SearchControls();
        sc.setSearchScope(scope);
        sc.setCountLimit(0);
        sc.setTimeLimit(60000);
        /* その他属性の設定 */
        if (base == null) {
            base = "";
        }
        if (filter == null) {
            filter = "";
        }

        /* 検索の実行 */
        NamingEnumeration<SearchResult> entries = null;
        try {
            entries = context.search(base, filter, sc);
        } catch (NamingException e) {
            throw new SysException("LDAPの検索に失敗しました！", e);
        }

        return entries;
    }

    /**
     * 指定された検索ルート直下のエントリーを検索対象とし、指定された条件にて検索を実行します。
     * base変数によって指定されたDN直下に配置されるエントリーを検索対象とし、それらの中からfilter変数に指定されたLDAPフィルターの条件を満たすエントリーに相当するjavax.naming.directory.SearchResultクラス（０～検索対象数）をjavax.naming.NamingEnumerationクラスに格納して返します。
     *
     * @param searchDN 検索ルートDN
     * @param filter LDAPフィルタ
     * @return エントリーを表すSearchResultクラスを格納したNamingEnumeration
     * @exception SysException
     */
    public NamingEnumeration<SearchResult> searchByOneLevelScope(String searchDN, String filter) throws SysException
    {
    	return search(searchDN, filter, SearchControls.ONELEVEL_SCOPE);
    }

    /**
     * 指定された検索ルート以下全てのエントリーを検索対象とし、指定された条件にて検索を実行します。
     * base変数によって指定されたDN以下全てのエントリーを検索対象とし、それらの中からfilter変数に指定されたLDAPフィルターの条件を満たすエントリーに相当するjavax.naming.directory.SearchResultクラス（０～検索対象数）をjavax.naming.NamingEnumerationクラスに格納して返します。
     *
     * @param searchDN 検索ルートDN
     * @param filter LDAPフィルタ
     * @return エントリーを表すSearchResultクラスを格納したNamingEnumeration
     * @exception SysException
     */
    public NamingEnumeration<SearchResult> searchBySubTreeScope(String searchDN, String filter) throws SysException
    {
    	return search(searchDN, filter, SearchControls.SUBTREE_SCOPE);
    }

    /**
     * 指定された検索ルートのみを検索対象とし、指定された条件にて検索を実行します。
     * base変数によって指定されたDNがfilter変数に指定されたLDAPフィルターの条件を満たす場合には、エントリーを表すjavax.naming.directory.SearchResultクラスをjavax.naming.NamingEnumerationクラスに格納して返します。
     *
     * @param searchDN 検索対象DN
     * @param filter LDAPフィルタ
     * @return エントリーを表すSearchResultクラスを格納したNamingEnumeration
     * @exception SysException
     */
    public NamingEnumeration<SearchResult> searchByObjectScope(String searchDN, String filter) throws SysException
    {
    	return search(searchDN, filter, SearchControls.OBJECT_SCOPE);
    }

	/**
	 * アトリビュート追加
	 * @param name 属性を追加されるオブジェクトの名前
	 * @param attrs 追加する属性
	 * @throws SysException
	 */
	public void addAttributes(String name, Attributes attrs) throws SysException {
		modifyAttributes(name, DirContext.ADD_ATTRIBUTE, attrs);
	}

	/**
	 * アトリビュート更新
	 * @param name 属性が変更されるオブジェクトの名前
	 * @param attrs 変更に使用される属性
	 * @throws SysException
	 */
	public void replaceAttributes(String name, Attributes attrs) throws SysException {
		if(addDummyAttribute(name, attrs)) {
			modifyAttributes(name, DirContext.REPLACE_ATTRIBUTE, attrs);
		}
	}

	/**
	 * アトリビュート削除
	 * @param name 属性が削除されるオブジェクトの名前
	 * @param attrs 削除する属性
	 * @throws SysException
	 */
	public void removeAttributes(String name, Attributes attrs) throws SysException {
		if(addDummyAttribute(name, attrs)) {
			modifyAttributes(name, DirContext.REMOVE_ATTRIBUTE, attrs);
		}
	}

	/**
	 * 無い属性を更新・削除しようとするとエラーが発生するため項目を空で追加する。
	 * @param name
	 * @param attrs
	 * @throws SysException
	 */
	private boolean addDummyAttribute(String name, Attributes attrs) throws SysException {
		try {
			// 既存データ取得
			NamingEnumeration<SearchResult> results = searchBySubTreeScope(name, "objectclass=*");

			if(results == null) return false; // 更新・削除の対象が無い

			BasicAttributes bAttrs = new BasicAttributes();
			for(NamingEnumeration<? extends Attribute> ne = attrs.getAll(); ne.hasMore();) {
				Attribute attr = (Attribute) ne.next();
				boolean bFlg = false;
				while(results.hasMore()) {
	                SearchResult entry = (SearchResult)results.next();
	                Attributes resAttrs = entry.getAttributes();
	                if ( resAttrs != null ) {
	                    Attribute attribute = resAttrs.get(attr.getID());
	    				if(attribute != null) { // 既存に無い属性はダミー追加
	    					bFlg = true;
	    					break;
	    				}
	                }
				}
				if(!bFlg) { // 既存に無い属性はダミー追加
					bAttrs.put(new BasicAttribute(attr.getID(), ""));
				}
			}
			addAttributes(name, bAttrs);
			return true;
		} catch (NamingException e) {
			throw new SysException("LDAPの空アトリビュート作成失敗しました！", e);
		}
	}

	/**
	 * 属性の存在確認
	 * @param name
	 * @param attrs
	 * @throws SysException
	 */
	@SuppressWarnings("unchecked")
	public boolean isAttribute(String name, String attrId, String attrValue) throws SysException {
		try {

			String id = Function.nvl(attrId, "").toLowerCase();
			String value = Function.nvl(attrValue, "").toLowerCase();

			// 既存データ取得
			NamingEnumeration<SearchResult> results = searchBySubTreeScope(name, "objectclass=*");

			if(results == null) return false; // 対象が無い

			while(results.hasMore()){
                SearchResult entry = (SearchResult)results.next();

				NamingEnumeration<Attribute> attributes = (NamingEnumeration<Attribute>) entry.getAttributes().getAll();
				while (attributes.hasMore()) {
					Attribute attribute = (Attribute) attributes.next();

					String ldapId = Function.nvl(attribute.getID(), "").toLowerCase();

					if(ldapId.equals(id)){
						if(value.equals("")) return true;

						NamingEnumeration<String> attributeValues = (NamingEnumeration<String>) attribute.getAll();
						while(attributeValues.hasMore()){
							String ldapValue = Function.nvl(attributeValues.next(), "").toLowerCase();

							if(ldapValue.equals(value)) return true;
						}
					}
				}
			}
			return false;
		} catch (NamingException e) {
			throw new SysException("LDAPの空アトリビュート作成失敗しました！", e);
		}
	}

	/**
	 * アトリビュート更新
	 * @param name 属性が変更されるオブジェクトの名前
	 * @param op 変更操作 DirContext.ADD_ATTRIBUTE、DirContext.REPLACE_ATTRIBUTE、DirContext.REMOVE_ATTRIBUTE
	 * @param attrs 変更に使用される属性
	 * @throws SysException
	 */
	private void modifyAttributes(String name, int op, Attributes attrs) throws SysException {

		if(context == null) return;

		try {
			context.modifyAttributes(name, op, attrs);
		} catch (NamingException e) {
			throw new SysException("LDAPのアトリビュート更新に失敗しました！" + e.getMessage(), e);
		}
	}

	/**
	 * 関連付けられた属性とともに、名前をオブジェクトにバインドします。
	 * @param name 空ではない作成されるコンテキストの名前
	 * @param attrs 新しく作成されたコンテキストに関連付けるオブジェクト
	 * @return
	 * @throws SysException
	 */
	public DirContext createSubContext(String name, Attributes attrs) throws SysException {

        if(context == null) return null;

		try {
			return context.createSubcontext(name, attrs);
		} catch (NamingException e) {
			throw new SysException("LDAPのサブコンテキスト作成に失敗しました！", e);
		}
	}

	/**
	 * 指定されたコンテキストを破棄し、名前空間から削除します。
	 * @param name 破棄されるコンテキストの名前。空は不可
	 * @throws LdapControlException
	 */
	public void destroySubContext(String name) throws SysException {

		if(context == null) return;

		try {
			context.destroySubcontext(name);
		} catch (NamingException e) {
			throw new SysException("LDAPのサブコンテキスト破棄に失敗しました！", e);
		} finally {
			closeContext();
		}
	}

	/**
	 * 新しい名前を、以前の名前にバインドされたオブジェクトにバインドし、以前の名前をアンバインドします。
	 * @param oldName 既存のバインディングの名前。空は不可
	 * @param newName 新しいバインディングの名前。空は不可
	 * @throws SysException
	 */
	public void rename(String oldName, String newName) throws SysException {

		if(context == null) return;

		try {
			context.rename(oldName, newName);
		} catch (NamingException e) {
			throw new SysException("LDAPの名前変更に失敗しました！", e);
		} finally {
			closeContext();
		}
	}

	/**
	 * コンテキストのclose
	 * @throws SysException
	 */
	public void closeContext() throws SysException {
		if(context != null) try {
			context.close();
		} catch (NamingException e) {
			throw new SysException("LDAPの切断に失敗しました！", e);
		}
	}

	/**
	 * LDAPサーバーホストの取得
	 */
	public String getHost() throws SysException {
		return host;
	}

	/**
	 * LDAPサービスポートの取得
	 */
	public int getPort() throws SysException {
		return port;
	}

	/**
	 * ベースDNの取得
	 */
	public String getBaseDn() throws SysException {
		return baseDn;
	}

	/**
	 * プリンシパルの取得
	 */
	public String getPrincipal() throws SysException {
		return principal;
	}

	/**
	 * パスワードの取得
	 */
	public String getCredentials() throws SysException {
		return credentials;
	}
}
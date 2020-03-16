/*
 * 作成日: 2012/03/09
 */
package jp.co.ctcg.easset.dao;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.TimeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchResult;

import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.LDAPManager;

import jp.co.ctcg.easset.template.utils.SysException;

/**
 * eGuard連携LDAP用DAO
 */
public class EGuardLdapDAO {

	private static final String EGUARD_LDAP = "EGUARD_LDAP";

	/**
	 * 情報機器管理番号の登録
	 * @param assetId 情報機器管理番号
	 * @return true:新規作成 false:使用可に更新（既に存在する）
	 * @throws SysException
	 */
	public boolean createAssetId(String assetId) throws SysException {

		if(assetId == null || assetId.equals("")) return false;

		LDAPManager lm = new LDAPManager();

		try {
			boolean ret = false;

			lm.connectDirContext(EGUARD_LDAP);
			if(!Function.nvl(lm.getBaseDn(), "").equals("")) { // LDAP連携設定有り
				String cn = "cn=" + assetId + "," + lm.getBaseDn();

				// LDAPデータ存在チェック（有効・無効含む）
				if(!isExists(lm, assetId, false)){ // 存在しない


					BasicAttributes assetAttrs = new BasicAttributes();

					assetAttrs.put(new BasicAttribute("objectClass", "computer"));
					assetAttrs.put(new BasicAttribute("userAccountControl", "4128"));
					assetAttrs.put(new BasicAttribute("sAMAccountName", assetId + "$"));

					lm.createSubContext(cn, assetAttrs);

					ret = true;
				} else if(!isExists(lm, assetId, true)) { // 無効データが存在する
					BasicAttributes assetAttrs = new BasicAttributes();
					assetAttrs.put(new BasicAttribute("userAccountControl", "4128"));
					lm.replaceAttributes(cn, assetAttrs);
				}
			}

			return ret;

        } catch(Exception e) {
        	throw new SysException("eGuard ADへの登録処理でエラーが発生しました。(情報機器管理番号：" + assetId + ")", e);
        } finally {
			lm.closeContext();
		}
	}

	/**
	 * 情報機器管理番号の削除
	 * @param assetId 情報機器管理番号
	 * @return true:使用不可に更新 false:何もしない（存在しない）
	 * @throws SysException
	 */
	public boolean deleteAssetId(String assetId) throws SysException {

		if(assetId == null || assetId.equals("")) return false;

		LDAPManager lm = new LDAPManager();

		try {
			boolean ret = false;
			lm.connectDirContext(EGUARD_LDAP);

			if(!Function.nvl(lm.getBaseDn(), "").equals("")) { // LDAP連携設定有り
				String cn = "cn=" + assetId + "," + lm.getBaseDn();

				if(isExists(lm, assetId, true)){ // 有効なLDAPデータのみ存在チェック
					BasicAttributes assetAttrs = new BasicAttributes();
					assetAttrs.put(new BasicAttribute("userAccountControl", "4130"));
					lm.replaceAttributes(cn, assetAttrs);

					ret = true;
				}
			}

			return ret;
        } catch(Exception e) {
        	throw new SysException("eGuard ADへの削除処理でエラーが発生しました。(情報機器管理番号：" + assetId + ")", e);
        } finally {
			lm.closeContext();
		}
	}

	/**
	 * 登録されているかどうかチェック
     * @param LDAPManager ldap接続管理
	 * @param assetId 情報機器管理晩後う
     * @param enableOnly 有効なデータのみ検索
     * @return エントリーを表すSearchResultクラスを格納したNamingEnumeration
     * @exception LdapControlException LDAPコントロール用エクセプション
     */
	private boolean isExists(LDAPManager lm, String assetId, boolean enableOnly) {

		boolean ret = false;
		String filter = "cn=" + assetId;
		if(enableOnly) filter = "(&(" + filter + ")(userAccountControl=4128))";

		NamingEnumeration<SearchResult> ldapResults = null;
		try {
			ldapResults = lm.searchByOneLevelScope(lm.getBaseDn(), filter);
        } catch(Exception e) {
        	return false;
        }


        try {
            while (ldapResults.hasMore()) {
                /* cnの抽出 */
                SearchResult entry = (SearchResult)ldapResults.next();
                Attributes ldapAttributes = entry.getAttributes();
                if ( ldapAttributes != null ) {
                    Attribute attribute = ldapAttributes.get("cn");
                    if ( attribute != null ) {
                        String ldapUid = attribute.get().toString();
                        if(assetId.equals(ldapUid))
                        	ret = true;
                    }
                }
            }
        } catch (SizeLimitExceededException sle) {
        	return false;
        } catch (TimeLimitExceededException tle) {
        	return false;
        } catch (NamingException ne) {
        	return false;
        } finally {
        	try {
        		ldapResults.close();
			} catch (NamingException e) {
	        	return false;
			}
        }

        return ret;
	}
}

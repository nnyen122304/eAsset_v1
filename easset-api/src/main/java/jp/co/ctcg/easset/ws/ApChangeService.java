/**
 * ApChangeService.java
 *
 */

package jp.co.ctcg.easset.ws;

public interface ApChangeService extends java.rmi.Remote {
    public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String[] oldRouteStaffCodeArray, java.lang.String[] oldRouteChargeDispArray, java.lang.String[] oldRouteDispTypeArray, java.lang.String[] newRouteStaffCodeArray, java.lang.String[] newRouteChargeDispArray, java.lang.String[] newRouteDispTypeArray, java.lang.String[] approveRouteAuthDispArray, java.lang.String[] approveRouteChargeDispArray, java.lang.String[] approveRouteDispTypeArray, java.lang.String[] acceptRouteAuthDispArray, java.lang.String[] acceptRouteChargeDispArray, java.lang.String[] acceptRouteDispTypeArray) throws java.rmi.RemoteException, java.net.SocketException;
}

/**
 * ApGetIntService.java
 */

package jp.co.ctcg.easset.ws;

public interface ApGetIntService extends java.rmi.Remote {
    public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationVersion, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String[] applyRouteAuthDispArray, java.lang.String[] applyRouteChargeDispArray, java.lang.String[] applyRouteDispTypeArray, java.lang.String[] approveRouteAuthDispArray, java.lang.String[] approveRouteChargeDispArray, java.lang.String[] approveRouteDispTypeArray, java.lang.String[] acceptRouteAuthDispArray, java.lang.String[] acceptRouteChargeDispArray, java.lang.String[] acceptRouteDispTypeArray, java.util.Calendar specialApproveDate) throws java.rmi.RemoteException, java.net.SocketException;
}

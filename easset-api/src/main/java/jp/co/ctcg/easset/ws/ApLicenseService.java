/**
 * ApLicenseService.java
 *
 */

package jp.co.ctcg.easset.ws;

public interface ApLicenseService extends java.rmi.Remote {
    public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String acceptRouteAuthDisp, java.lang.String acceptRouteChargeDisp, java.lang.String acceptRouteDispType) throws java.rmi.RemoteException, java.net.SocketException;
}

package jp.co.ctcg.easset.ws;

public class ApBgnIntServiceProxy implements jp.co.ctcg.easset.ws.ApBgnIntService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.ApBgnIntService apBgnIntService = null;

  public ApBgnIntServiceProxy() {
    _initApBgnIntServiceProxy();
  }

  public ApBgnIntServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initApBgnIntServiceProxy();
  }

  private void _initApBgnIntServiceProxy() {
    try {
      apBgnIntService = (new jp.co.ctcg.easset.ws.ApBgnIntServiceServiceLocator()).getApBgnIntService();
      if (apBgnIntService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)apBgnIntService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)apBgnIntService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (apBgnIntService != null)
      ((javax.xml.rpc.Stub)apBgnIntService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public jp.co.ctcg.easset.ws.ApBgnIntService getApBgnIntService() {
    if (apBgnIntService == null)
      _initApBgnIntServiceProxy();
    return apBgnIntService;
  }

  public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String[] applyRouteAuthDispArray, java.lang.String[] applyRouteChargeDispArray, java.lang.String[] applyRouteDispTypeArray, java.lang.String[] approveRouteAuthDispArray, java.lang.String[] approveRouteChargeDispArray, java.lang.String[] approveRouteDispTypeArray, java.lang.String[] acceptRouteAuthDispArray, java.lang.String[] acceptRouteChargeDispArray, java.lang.String[] acceptRouteDispTypeArray, java.util.Calendar specialApproveDate) throws java.rmi.RemoteException, java.net.SocketException{
    if (apBgnIntService == null)
      _initApBgnIntServiceProxy();
    return apBgnIntService.apply(applicationId, applicationType, companyCode, apSectionCode, apCreateStaffCode, apStaffCode, apTel, apTitle, apSubTitle, apListTitle, eAssetUrl, applyRouteAuthDispArray, applyRouteChargeDispArray, applyRouteDispTypeArray, approveRouteAuthDispArray, approveRouteChargeDispArray, approveRouteDispTypeArray, acceptRouteAuthDispArray, acceptRouteChargeDispArray, acceptRouteDispTypeArray, specialApproveDate);
  }


}
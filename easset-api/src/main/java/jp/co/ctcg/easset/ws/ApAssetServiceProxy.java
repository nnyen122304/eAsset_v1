package jp.co.ctcg.easset.ws;

public class ApAssetServiceProxy implements jp.co.ctcg.easset.ws.ApAssetService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.ApAssetService apAssetService = null;

  public ApAssetServiceProxy() {
    _initApAssetServiceProxy();
  }

  public ApAssetServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initApAssetServiceProxy();
  }

  private void _initApAssetServiceProxy() {
    try {
      apAssetService = (new jp.co.ctcg.easset.ws.ApAssetServiceServiceLocator()).getApAssetService();
      if (apAssetService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)apAssetService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)apAssetService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (apAssetService != null)
      ((javax.xml.rpc.Stub)apAssetService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public jp.co.ctcg.easset.ws.ApAssetService getApAssetService() {
    if (apAssetService == null)
      _initApAssetServiceProxy();
    return apAssetService;
  }

  public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String acceptRouteAuthDisp, java.lang.String acceptRouteChargeDisp, java.lang.String acceptRouteDispType) throws java.rmi.RemoteException, java.net.SocketException{
    if (apAssetService == null)
      _initApAssetServiceProxy();
    return apAssetService.apply(applicationId, applicationType, companyCode, apSectionCode, apCreateStaffCode, apStaffCode, apTel, apTitle, apSubTitle, apListTitle, eAssetUrl, acceptRouteAuthDisp, acceptRouteChargeDisp, acceptRouteDispType);
  }


}
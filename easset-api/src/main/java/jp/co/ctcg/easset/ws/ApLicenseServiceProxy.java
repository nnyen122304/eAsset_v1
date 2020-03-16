package jp.co.ctcg.easset.ws;

public class ApLicenseServiceProxy implements jp.co.ctcg.easset.ws.ApLicenseService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.ApLicenseService apLicenseService = null;

  public ApLicenseServiceProxy() {
    _initApLicenseServiceProxy();
  }

  public ApLicenseServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initApLicenseServiceProxy();
  }

  private void _initApLicenseServiceProxy() {
    try {
      apLicenseService = (new jp.co.ctcg.easset.ws.ApLicenseServiceServiceLocator()).getApLicenseService();
      if (apLicenseService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)apLicenseService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)apLicenseService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (apLicenseService != null)
      ((javax.xml.rpc.Stub)apLicenseService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public jp.co.ctcg.easset.ws.ApLicenseService getApLicenseService() {
    if (apLicenseService == null)
      _initApLicenseServiceProxy();
    return apLicenseService;
  }

  public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String acceptRouteAuthDisp, java.lang.String acceptRouteChargeDisp, java.lang.String acceptRouteDispType) throws java.rmi.RemoteException, java.net.SocketException{
    if (apLicenseService == null)
      _initApLicenseServiceProxy();
    return apLicenseService.apply(applicationId, applicationType, companyCode, apSectionCode, apCreateStaffCode, apStaffCode, apTel, apTitle, apSubTitle, apListTitle, eAssetUrl, acceptRouteAuthDisp, acceptRouteChargeDisp, acceptRouteDispType);
  }


}
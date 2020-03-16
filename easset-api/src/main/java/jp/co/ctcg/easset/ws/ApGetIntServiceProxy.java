package jp.co.ctcg.easset.ws;

public class ApGetIntServiceProxy implements jp.co.ctcg.easset.ws.ApGetIntService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.ApGetIntService apGetIntService = null;

  public ApGetIntServiceProxy() {
    _initApGetIntServiceProxy();
  }

  public ApGetIntServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initApGetIntServiceProxy();
  }

  private void _initApGetIntServiceProxy() {
    try {
      apGetIntService = (new jp.co.ctcg.easset.ws.ApGetIntServiceServiceLocator()).getApGetIntService();
      if (apGetIntService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)apGetIntService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)apGetIntService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (apGetIntService != null)
      ((javax.xml.rpc.Stub)apGetIntService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public jp.co.ctcg.easset.ws.ApGetIntService getApGetIntService() {
    if (apGetIntService == null)
      _initApGetIntServiceProxy();
    return apGetIntService;
  }

  public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationVersion, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String[] applyRouteAuthDispArray, java.lang.String[] applyRouteChargeDispArray, java.lang.String[] applyRouteDispTypeArray, java.lang.String[] approveRouteAuthDispArray, java.lang.String[] approveRouteChargeDispArray, java.lang.String[] approveRouteDispTypeArray, java.lang.String[] acceptRouteAuthDispArray, java.lang.String[] acceptRouteChargeDispArray, java.lang.String[] acceptRouteDispTypeArray, java.util.Calendar specialApproveDate) throws java.rmi.RemoteException, java.net.SocketException{
    if (apGetIntService == null)
      _initApGetIntServiceProxy();
    return apGetIntService.apply(applicationId, applicationVersion, applicationType, companyCode, apSectionCode, apCreateStaffCode, apStaffCode, apTel, apTitle, apSubTitle, apListTitle, eAssetUrl, applyRouteAuthDispArray, applyRouteChargeDispArray, applyRouteDispTypeArray, approveRouteAuthDispArray, approveRouteChargeDispArray, approveRouteDispTypeArray, acceptRouteAuthDispArray, acceptRouteChargeDispArray, acceptRouteDispTypeArray, specialApproveDate);
  }


}
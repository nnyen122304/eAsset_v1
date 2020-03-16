package jp.co.ctcg.easset.ws;

public class ApEndReServiceProxy implements jp.co.ctcg.easset.ws.ApEndReService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.ApEndReService apEndReService = null;

  public ApEndReServiceProxy() {
    _initApEndReServiceProxy();
  }

  public ApEndReServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initApEndReServiceProxy();
  }

  private void _initApEndReServiceProxy() {
    try {
      apEndReService = (new jp.co.ctcg.easset.ws.ApEndReServiceServiceLocator()).getApEndReService();
      if (apEndReService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)apEndReService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)apEndReService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (apEndReService != null)
      ((javax.xml.rpc.Stub)apEndReService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public jp.co.ctcg.easset.ws.ApEndReService getApEndReService() {
    if (apEndReService == null)
      _initApEndReServiceProxy();
    return apEndReService;
  }

  public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String[] applyRouteAuthDispArray, java.lang.String[] applyRouteChargeDispArray, java.lang.String[] applyRouteDispTypeArray, java.lang.String[] approveRouteAuthDispArray, java.lang.String[] approveRouteChargeDispArray, java.lang.String[] approveRouteDispTypeArray, java.lang.String[] acceptRouteAuthDispArray, java.lang.String[] acceptRouteChargeDispArray, java.lang.String[] acceptRouteDispTypeArray, java.util.Calendar specialApproveDate) throws java.rmi.RemoteException, java.net.SocketException{
    if (apEndReService == null)
      _initApEndReServiceProxy();
    return apEndReService.apply(applicationId, applicationType, companyCode, apSectionCode, apCreateStaffCode, apStaffCode, apTel, apTitle, apSubTitle, apListTitle, eAssetUrl, applyRouteAuthDispArray, applyRouteChargeDispArray, applyRouteDispTypeArray, approveRouteAuthDispArray, approveRouteChargeDispArray, approveRouteDispTypeArray, acceptRouteAuthDispArray, acceptRouteChargeDispArray, acceptRouteDispTypeArray, specialApproveDate);
  }


}
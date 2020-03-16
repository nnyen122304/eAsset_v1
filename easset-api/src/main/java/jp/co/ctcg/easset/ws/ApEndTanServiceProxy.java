package jp.co.ctcg.easset.ws;

public class ApEndTanServiceProxy implements jp.co.ctcg.easset.ws.ApEndTanService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.ApEndTanService apEndTanService = null;

  public ApEndTanServiceProxy() {
    _initApEndTanServiceProxy();
  }

  public ApEndTanServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initApEndTanServiceProxy();
  }

  private void _initApEndTanServiceProxy() {
    try {
      apEndTanService = (new jp.co.ctcg.easset.ws.ApEndTanServiceServiceLocator()).getApEndTanService();
      if (apEndTanService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)apEndTanService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)apEndTanService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (apEndTanService != null)
      ((javax.xml.rpc.Stub)apEndTanService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public jp.co.ctcg.easset.ws.ApEndTanService getApEndTanService() {
    if (apEndTanService == null)
      _initApEndTanServiceProxy();
    return apEndTanService;
  }

  public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String[] applyRouteAuthDispArray, java.lang.String[] applyRouteChargeDispArray, java.lang.String[] applyRouteDispTypeArray, java.lang.String[] approveRouteAuthDispArray, java.lang.String[] approveRouteChargeDispArray, java.lang.String[] approveRouteDispTypeArray, java.lang.String[] acceptRouteAuthDispArray, java.lang.String[] acceptRouteChargeDispArray, java.lang.String[] acceptRouteDispTypeArray, java.util.Calendar specialApproveDate) throws java.rmi.RemoteException, java.net.SocketException{
    if (apEndTanService == null)
      _initApEndTanServiceProxy();
    return apEndTanService.apply(applicationId, applicationType, companyCode, apSectionCode, apCreateStaffCode, apStaffCode, apTel, apTitle, apSubTitle, apListTitle, eAssetUrl, applyRouteAuthDispArray, applyRouteChargeDispArray, applyRouteDispTypeArray, approveRouteAuthDispArray, approveRouteChargeDispArray, approveRouteDispTypeArray, acceptRouteAuthDispArray, acceptRouteChargeDispArray, acceptRouteDispTypeArray, specialApproveDate);
  }


}
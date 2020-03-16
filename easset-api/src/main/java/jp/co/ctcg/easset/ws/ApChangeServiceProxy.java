package jp.co.ctcg.easset.ws;

public class ApChangeServiceProxy implements jp.co.ctcg.easset.ws.ApChangeService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.ApChangeService apChangeService = null;
  
  public ApChangeServiceProxy() {
    _initApChangeServiceProxy();
  }
  
  public ApChangeServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initApChangeServiceProxy();
  }
  
  private void _initApChangeServiceProxy() {
    try {
      apChangeService = (new jp.co.ctcg.easset.ws.ApChangeServiceServiceLocator()).getApChangeService();
      if (apChangeService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)apChangeService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)apChangeService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (apChangeService != null)
      ((javax.xml.rpc.Stub)apChangeService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public jp.co.ctcg.easset.ws.ApChangeService getApChangeService() {
    if (apChangeService == null)
      _initApChangeServiceProxy();
    return apChangeService;
  }
  
  public java.lang.String apply(java.lang.String applicationId, java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl, java.lang.String[] oldRouteStaffCodeArray, java.lang.String[] oldRouteChargeDispArray, java.lang.String[] oldRouteDispTypeArray, java.lang.String[] newRouteStaffCodeArray, java.lang.String[] newRouteChargeDispArray, java.lang.String[] newRouteDispTypeArray, java.lang.String[] approveRouteAuthDispArray, java.lang.String[] approveRouteChargeDispArray, java.lang.String[] approveRouteDispTypeArray, java.lang.String[] acceptRouteAuthDispArray, java.lang.String[] acceptRouteChargeDispArray, java.lang.String[] acceptRouteDispTypeArray) throws java.rmi.RemoteException, java.net.SocketException{
    if (apChangeService == null)
      _initApChangeServiceProxy();
    return apChangeService.apply(applicationId, applicationType, companyCode, apSectionCode, apCreateStaffCode, apStaffCode, apTel, apTitle, apSubTitle, apListTitle, eAssetUrl, oldRouteStaffCodeArray, oldRouteChargeDispArray, oldRouteDispTypeArray, newRouteStaffCodeArray, newRouteChargeDispArray, newRouteDispTypeArray, approveRouteAuthDispArray, approveRouteChargeDispArray, approveRouteDispTypeArray, acceptRouteAuthDispArray, acceptRouteChargeDispArray, acceptRouteDispTypeArray);
  }


}
package jp.co.ctcg.easset.ws;

public class InvServiceProxy implements jp.co.ctcg.easset.ws.InvService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.InvService invService = null;

  public InvServiceProxy() {
    _initInvServiceProxy();
  }

  public InvServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initInvServiceProxy();
  }

  private void _initInvServiceProxy() {
    try {
      invService = (new jp.co.ctcg.easset.ws.InvServiceServiceLocator()).getInvService();
      if (invService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)invService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)invService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (invService != null)
      ((javax.xml.rpc.Stub)invService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public jp.co.ctcg.easset.ws.InvService getInvService() {
    if (invService == null)
      _initInvServiceProxy();
    return invService;
  }

  public java.lang.String apply(java.lang.String applicationType, java.lang.String companyCode, java.lang.String apSectionCode, java.lang.String apCreateStaffCode, java.lang.String apStaffCode, java.lang.String apTel, java.lang.String apTitle, java.lang.String apSubTitle, java.lang.String apListTitle, java.lang.String eAssetUrl) throws java.rmi.RemoteException, java.net.SocketException{
    if (invService == null)
      _initInvServiceProxy();
    return invService.apply(applicationType, companyCode, apSectionCode, apCreateStaffCode, apStaffCode, apTel, apTitle, apSubTitle, apListTitle, eAssetUrl);
  }


}
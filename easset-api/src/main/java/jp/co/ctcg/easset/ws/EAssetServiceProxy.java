package jp.co.ctcg.easset.ws;

public class EAssetServiceProxy implements jp.co.ctcg.easset.ws.EAssetService {
  private String _endpoint = null;
  private jp.co.ctcg.easset.ws.EAssetService eAssetService = null;

  public EAssetServiceProxy() {
    _initEAssetServiceProxy();
  }

  public EAssetServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initEAssetServiceProxy();
  }

  private void _initEAssetServiceProxy() {
    try {
    	eAssetService = (new jp.co.ctcg.easset.ws.EAssetServiceServiceLocator()).getEAssetServiceSoapPort();
      if (eAssetService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)eAssetService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)eAssetService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (eAssetService != null)
      ((javax.xml.rpc.Stub)eAssetService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public jp.co.ctcg.easset.ws.EAssetService getEAssetService_PortType() {
    if (eAssetService == null)
      _initEAssetServiceProxy();
    return eAssetService;
  }

  public java.lang.Long getEAppId(java.lang.String eassetId) throws java.rmi.RemoteException, jp.co.ctcg.easset.ws.EAssetControlException{
    if (eAssetService == null)
      _initEAssetServiceProxy();
    return eAssetService.getEAppId(eassetId);
  }


}
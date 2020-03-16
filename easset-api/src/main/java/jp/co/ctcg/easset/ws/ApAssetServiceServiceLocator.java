/**
 * ApAssetServiceServiceLocator.java
 *
 * このファイルはWSDLから自動生成されました / [en]-(This file was auto-generated from WSDL)
 * Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java生成器によって / [en]-(by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.)
 */

package jp.co.ctcg.easset.ws;


public class ApAssetServiceServiceLocator extends org.apache.axis.client.Service implements jp.co.ctcg.easset.ws.ApAssetServiceService {
    public static final long serialVersionUID = 1L;

    public ApAssetServiceServiceLocator() {
    }


    public ApAssetServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ApAssetServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // ApAssetServiceのプロキシクラスの取得に使用します / [en]-(Use to get a proxy class for ApAssetService)
    private java.lang.String ApAssetService_address = "http://localhost:8080/eAsset_Web/services/ApAssetService";

    public java.lang.String getApAssetServiceAddress() {
        return ApAssetService_address;
    }

    // WSDDサービス名のデフォルトはポート名です / [en]-(The WSDD service name defaults to the port name.)
    private java.lang.String ApAssetServiceWSDDServiceName = "ApAssetService";

    public java.lang.String getApAssetServiceWSDDServiceName() {
        return ApAssetServiceWSDDServiceName;
    }

    public void setApAssetServiceWSDDServiceName(java.lang.String name) {
        ApAssetServiceWSDDServiceName = name;
    }

    public jp.co.ctcg.easset.ws.ApAssetService getApAssetService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ApAssetService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getApAssetService(endpoint);
    }

    public jp.co.ctcg.easset.ws.ApAssetService getApAssetService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            jp.co.ctcg.easset.ws.ApAssetServiceSoapBindingStub _stub = new jp.co.ctcg.easset.ws.ApAssetServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getApAssetServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setApAssetServiceEndpointAddress(java.lang.String address) {
        ApAssetService_address = address;
    }

    /**
     * 与えられたインターフェースに対して、スタブの実装を取得します。 / [en]-(For the given interface, get the stub implementation.)
     * このサービスが与えられたインターフェースに対してポートを持たない場合、 / [en]-(If this service has no port for the given interface,)
     * ServiceExceptionが投げられます。 / [en]-(then ServiceException is thrown.)
     */
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (jp.co.ctcg.easset.ws.ApAssetService.class.isAssignableFrom(serviceEndpointInterface)) {
                jp.co.ctcg.easset.ws.ApAssetServiceSoapBindingStub _stub = new jp.co.ctcg.easset.ws.ApAssetServiceSoapBindingStub(new java.net.URL(ApAssetService_address), this);
                _stub.setPortName(getApAssetServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("インターフェースに対するスタブの実装がありません: / [en]-(There is no stub implementation for the interface:)  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * 与えられたインターフェースに対して、スタブの実装を取得します。 / [en]-(For the given interface, get the stub implementation.)
     * このサービスが与えられたインターフェースに対してポートを持たない場合、 / [en]-(If this service has no port for the given interface,)
     * ServiceExceptionが投げられます。 / [en]-(then ServiceException is thrown.)
     */
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ApAssetService".equals(inputPortName)) {
            return getApAssetService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.easset.ctcg.co.jp", "ApAssetServiceService");
    }

    private java.util.HashSet<javax.xml.namespace.QName> ports = null;

    public java.util.Iterator<javax.xml.namespace.QName> getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet<javax.xml.namespace.QName>();
            ports.add(new javax.xml.namespace.QName("http://ws.easset.ctcg.co.jp", "ApAssetService"));
        }
        return ports.iterator();
    }

    /**
    * 指定したポート名に対するエンドポイントのアドレスをセットします / [en]-(Set the endpoint address for the specified port name.)
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ApAssetService".equals(portName)) {
            setApAssetServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" 未知のポートに対してはエンドポイントのアドレスをセットできません / [en]-(Cannot set Endpoint Address for Unknown Port)" + portName);
        }
    }

    /**
    * 指定したポート名に対するエンドポイントのアドレスをセットします / [en]-(Set the endpoint address for the specified port name.)
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

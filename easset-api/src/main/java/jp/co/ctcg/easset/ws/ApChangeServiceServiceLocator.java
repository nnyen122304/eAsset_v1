/**
 * ApChangeServiceServiceLocator.java
 *
 * このファイルはWSDLから自動生成されました / [en]-(This file was auto-generated from WSDL)
 * Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java生成器によって / [en]-(by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.)
 */

package jp.co.ctcg.easset.ws;

public class ApChangeServiceServiceLocator extends org.apache.axis.client.Service implements jp.co.ctcg.easset.ws.ApChangeServiceService {
    public static final long serialVersionUID = 1L;

    public ApChangeServiceServiceLocator() {
    }


    public ApChangeServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ApChangeServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // ApChangeServiceのプロキシクラスの取得に使用します / [en]-(Use to get a proxy class for ApChangeService)
    private java.lang.String ApChangeService_address = "http://localhost:8080/eAsset_Web/services/ApChangeService";

    public java.lang.String getApChangeServiceAddress() {
        return ApChangeService_address;
    }

    // WSDDサービス名のデフォルトはポート名です / [en]-(The WSDD service name defaults to the port name.)
    private java.lang.String ApChangeServiceWSDDServiceName = "ApChangeService";

    public java.lang.String getApChangeServiceWSDDServiceName() {
        return ApChangeServiceWSDDServiceName;
    }

    public void setApChangeServiceWSDDServiceName(java.lang.String name) {
        ApChangeServiceWSDDServiceName = name;
    }

    public jp.co.ctcg.easset.ws.ApChangeService getApChangeService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ApChangeService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getApChangeService(endpoint);
    }

    public jp.co.ctcg.easset.ws.ApChangeService getApChangeService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            jp.co.ctcg.easset.ws.ApChangeServiceSoapBindingStub _stub = new jp.co.ctcg.easset.ws.ApChangeServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getApChangeServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setApChangeServiceEndpointAddress(java.lang.String address) {
        ApChangeService_address = address;
    }

    /**
     * 与えられたインターフェースに対して、スタブの実装を取得します。 / [en]-(For the given interface, get the stub implementation.)
     * このサービスが与えられたインターフェースに対してポートを持たない場合、 / [en]-(If this service has no port for the given interface,)
     * ServiceExceptionが投げられます。 / [en]-(then ServiceException is thrown.)
     */
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (jp.co.ctcg.easset.ws.ApChangeService.class.isAssignableFrom(serviceEndpointInterface)) {
                jp.co.ctcg.easset.ws.ApChangeServiceSoapBindingStub _stub = new jp.co.ctcg.easset.ws.ApChangeServiceSoapBindingStub(new java.net.URL(ApChangeService_address), this);
                _stub.setPortName(getApChangeServiceWSDDServiceName());
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
        if ("ApChangeService".equals(inputPortName)) {
            return getApChangeService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.easset.ctcg.co.jp", "ApChangeServiceService");
    }

    private java.util.HashSet<javax.xml.namespace.QName> ports = null;

    public java.util.Iterator<javax.xml.namespace.QName> getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet<javax.xml.namespace.QName>();
            ports.add(new javax.xml.namespace.QName("http://ws.easset.ctcg.co.jp", "ApChangeService"));
        }
        return ports.iterator();
    }

    /**
    * 指定したポート名に対するエンドポイントのアドレスをセットします / [en]-(Set the endpoint address for the specified port name.)
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ApChangeService".equals(portName)) {
            setApChangeServiceEndpointAddress(address);
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

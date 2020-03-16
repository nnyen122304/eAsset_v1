/**
 * EAssetServiceServiceLocator.java
 *
 * このファイルはWSDLから自動生成されました / [en]-(This file was auto-generated from WSDL)
 * Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java生成器によって / [en]-(by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.)
 */

package jp.co.ctcg.easset.ws;

public class EAssetServiceServiceLocator extends org.apache.axis.client.Service implements jp.co.ctcg.easset.ws.EAssetServiceService {
    public static final long serialVersionUID = 1L;

    public EAssetServiceServiceLocator() {
    }


    public EAssetServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EAssetServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // EAssetServiceSoapPortのプロキシクラスの取得に使用します / [en]-(Use to get a proxy class for EAssetServiceSoapPort)
    private java.lang.String EAssetServiceSoapPort_address = "http://localhost:8080/EAssetServices/EAssetService";

    public java.lang.String getEAssetServiceSoapPortAddress() {
        return EAssetServiceSoapPort_address;
    }

    // WSDDサービス名のデフォルトはポート名です / [en]-(The WSDD service name defaults to the port name.)
    private java.lang.String EAssetServiceSoapPortWSDDServiceName = "EAssetServiceSoapPort";

    public java.lang.String getEAssetServiceSoapPortWSDDServiceName() {
        return EAssetServiceSoapPortWSDDServiceName;
    }

    public void setEAssetServiceSoapPortWSDDServiceName(java.lang.String name) {
        EAssetServiceSoapPortWSDDServiceName = name;
    }

    public jp.co.ctcg.easset.ws.EAssetService getEAssetServiceSoapPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EAssetServiceSoapPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEAssetServiceSoapPort(endpoint);
    }

    public jp.co.ctcg.easset.ws.EAssetService getEAssetServiceSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            jp.co.ctcg.easset.ws.EAssetServiceSoapBindingStub _stub = new jp.co.ctcg.easset.ws.EAssetServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getEAssetServiceSoapPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEAssetServiceSoapPortEndpointAddress(java.lang.String address) {
        EAssetServiceSoapPort_address = address;
    }

    /**
     * 与えられたインターフェースに対して、スタブの実装を取得します。 / [en]-(For the given interface, get the stub implementation.)
     * このサービスが与えられたインターフェースに対してポートを持たない場合、 / [en]-(If this service has no port for the given interface,)
     * ServiceExceptionが投げられます。 / [en]-(then ServiceException is thrown.)
     */
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (jp.co.ctcg.easset.ws.EAssetService.class.isAssignableFrom(serviceEndpointInterface)) {
                jp.co.ctcg.easset.ws.EAssetServiceSoapBindingStub _stub = new jp.co.ctcg.easset.ws.EAssetServiceSoapBindingStub(new java.net.URL(EAssetServiceSoapPort_address), this);
                _stub.setPortName(getEAssetServiceSoapPortWSDDServiceName());
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
        if ("EAssetServiceSoapPort".equals(inputPortName)) {
            return getEAssetServiceSoapPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://jp/co/ctcg/soa/easset/service", "EAssetService");
    }

    private java.util.HashSet<javax.xml.namespace.QName> ports = null;

    public java.util.Iterator<javax.xml.namespace.QName> getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet<javax.xml.namespace.QName>();
            ports.add(new javax.xml.namespace.QName("http://jp/co/ctcg/soa/easset/service", "EAssetServiceSoapPort"));
        }
        return ports.iterator();
    }

    /**
    * 指定したポート名に対するエンドポイントのアドレスをセットします / [en]-(Set the endpoint address for the specified port name.)
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

if ("EAssetServiceSoapPort".equals(portName)) {
            setEAssetServiceSoapPortEndpointAddress(address);
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

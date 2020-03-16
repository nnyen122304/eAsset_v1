/**
 * ApEndReServiceServiceLocator.java
 *
 * このファイルはWSDLから自動生成されました / [en]-(This file was auto-generated from WSDL)
 * Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java生成器によって / [en]-(by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.)
 */

package jp.co.ctcg.easset.ws;

public class ApEndReServiceServiceLocator extends org.apache.axis.client.Service implements jp.co.ctcg.easset.ws.ApEndReServiceService {
    public static final long serialVersionUID = 1L;

    public ApEndReServiceServiceLocator() {
    }


    public ApEndReServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ApEndReServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // ApEndReServiceのプロキシクラスの取得に使用しま�? / [en]-(Use to get a proxy class for ApEndReService)
    private java.lang.String ApEndReService_address = "http://localhost:8080/eAsset_Web/services/ApEndReService";

    public java.lang.String getApEndReServiceAddress() {
        return ApEndReService_address;
    }

    // WSDDサービス名�?��?フォルト�?�ポ�?�ト名で�? / [en]-(The WSDD service name defaults to the port name.)
    private java.lang.String ApEndReServiceWSDDServiceName = "ApEndReService";

    public java.lang.String getApEndReServiceWSDDServiceName() {
        return ApEndReServiceWSDDServiceName;
    }

    public void setApEndReServiceWSDDServiceName(java.lang.String name) {
        ApEndReServiceWSDDServiceName = name;
    }

    public jp.co.ctcg.easset.ws.ApEndReService getApEndReService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ApEndReService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getApEndReService(endpoint);
    }

    public jp.co.ctcg.easset.ws.ApEndReService getApEndReService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            jp.co.ctcg.easset.ws.ApEndReServiceSoapBindingStub _stub = new jp.co.ctcg.easset.ws.ApEndReServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getApEndReServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setApEndReServiceEndpointAddress(java.lang.String address) {
        ApEndReService_address = address;
    }

    /**
     * 与えられたインターフェースに対して、スタブ�?�実�?を取得します�?? / [en]-(For the given interface, get the stub implementation.)
     * こ�?�サービスが与えられたインターフェースに対してポ�?�トを持たな�?場合�?? / [en]-(If this service has no port for the given interface,)
     * ServiceExceptionが投げられます�?? / [en]-(then ServiceException is thrown.)
     */
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (jp.co.ctcg.easset.ws.ApEndReService.class.isAssignableFrom(serviceEndpointInterface)) {
                jp.co.ctcg.easset.ws.ApEndReServiceSoapBindingStub _stub = new jp.co.ctcg.easset.ws.ApEndReServiceSoapBindingStub(new java.net.URL(ApEndReService_address), this);
                _stub.setPortName(getApEndReServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("インターフェースに対するスタブ�?�実�?がありません: / [en]-(There is no stub implementation for the interface:)  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * 与えられたインターフェースに対して、スタブ�?�実�?を取得します�?? / [en]-(For the given interface, get the stub implementation.)
     * こ�?�サービスが与えられたインターフェースに対してポ�?�トを持たな�?場合�?? / [en]-(If this service has no port for the given interface,)
     * ServiceExceptionが投げられます�?? / [en]-(then ServiceException is thrown.)
     */
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ApEndReService".equals(inputPortName)) {
            return getApEndReService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.easset.ctcg.co.jp", "ApEndReServiceService");
    }

    private java.util.HashSet<javax.xml.namespace.QName> ports = null;

    public java.util.Iterator<javax.xml.namespace.QName> getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet<javax.xml.namespace.QName>();
            ports.add(new javax.xml.namespace.QName("http://ws.easset.ctcg.co.jp", "ApEndReService"));
        }
        return ports.iterator();
    }

    /**
    * �?定した�?��?�ト名に対するエンド�?�イント�?�アドレスをセ�?トしま�? / [en]-(Set the endpoint address for the specified port name.)
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

if ("ApEndReService".equals(portName)) {
            setApEndReServiceEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" 未知のポ�?�トに対してはエンド�?�イント�?�アドレスをセ�?トできません / [en]-(Cannot set Endpoint Address for Unknown Port)" + portName);
        }
    }

    /**
    * �?定した�?��?�ト名に対するエンド�?�イント�?�アドレスをセ�?トしま�? / [en]-(Set the endpoint address for the specified port name.)
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

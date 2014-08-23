RestWS
======
REST
The RESTful Web services are completely stateless. This can be tested by restarting the server and checking if the interactions are able to survive.
Restful services provide a good caching infrastructure over HTTP GET method (for most servers). This can improve the performance, if the data the Web service returns is not altered frequently and not dynamic in nature.
The service producer and service consumer need to have a common understanding of the context as well as the content being passed along as there is no standard set of rules to describe the REST Web services interface.
REST is particularly useful for restricted-profile devices such as mobile and PDAs for which the overhead of additional parameters like headers and other SOAP elements are less.

SOAP
The Web Services Description Language (WSDL) contains and describes the common set of rules to define the messages, bindings, operations and location of the Web service. WSDL is a sort of formal contract to define the interface that the Web service offers.
SOAP requires less plumbing code than REST services design, (i.e., transactions, security, coordination, addressing, trust, etc.) Most real-world applications are not simple and support complex operations, which require conversational state and contextual information to be maintained. With the SOAP approach, developers need not worry about writing this plumbing code into the application layer themselves.
SOAP Web services (such as JAX-WS) are useful in handling asynchronous processing and invocation.
SOAP supports several protocols and technologies, including WSDL, XSDs, SOAP, WS-Addressing


REST	SOAP
Assumes a point-to-point communication model–not usable for distributed computing environment where message may go through one or more intermediaries	Designed to handle distributed computing environments
Minimal tooling/middleware is necessary. Only HTTP support is required	Requires significant tooling/middleware support
URL typically references the resource being accessed/deleted/updated	The content of the message typically decides the operation e.g. doc-literal services
Not reliable – HTTP DELETE can return OK status even if a resource is not deleted	Reliable
Formal description standards not in widespread use. WSDL 1.2, WADL are candidates.	Well defined mechanism for describing the interface e.g. WSDL+XSD, WS-Policy
Better suited for point-to-point or where the intermediary does not play a significant role	Well suited for intermediated services
No constraints on the payload	Payload must comply with the SOAP schema
Only the most well established standards apply e.g. HTTP, SSL. No established standards for other aspects.  DELETE and PUT methods often disabled by firewalls, leads to security complexity.	A large number of supporting standards for security, reliability, transactions.
Built-in error handling (faults)	No error handling
Tied to the HTTP transport model	Both SMTP and HTTP are valid application layerprotocols used as Transportfor SOAP
Less verbose	More verbose


2. JAX-RS with Jersey
2.1. JAX-RS and Jersey
Java defines REST support via the Java Specification Request 311 (JSR). This specificiation is called JAX-RS (The Java API for RESTful Web Services). JAX-RS uses annotations to define the REST relevance of Java classes.
Jersey is the reference implementation for this specification. Jersey contains basically a REST server and a REST client. The core client can be used provides a library to communicate with the server.
On the server side Jersey uses a servlet which scans predefined classes to identify RESTful resources. Via the web.xmlconfiguration file for your web application, registers this servlet which is provided by the Jersey distribution
The base URL of this servlet is:
http://your_domain:port/display-name/url-pattern/path_from_rest_class 
This servlet analyzes the incoming HTTP request and selects the correct class and method to respond to this request. This selection is based on annotations in the class and methods.
A REST web application consists therefore out of data classes (resources) and services. These two types are typically maintained in different packages as the Jersey servlet will be instructed via the web.xml to scan certain packages for data classes.
JAX-RS supports the creation of XML and JSON via the Java Architecture for XML Binding (JAXB).
JAXB is described in the JAXB Tutorial.
2.2. JAX-RS annotations
The most important annotations in JAX-RS are listed in the following table.
Table 1. JAX-RS annotations
Annotation	Description
@PATH(your_path)	Sets the path to base URL + /your_path. The base URL is based on your application name, the servlet and the URL pattern from the web.xml" configuration file.
@POST	Indicates that the following method will answer to a HTTP POST request
@GET	Indicates that the following method will answer to a HTTP GET request
@PUT	Indicates that the following method will answer to a HTTP PUT request
@DELETE	Indicates that the following method will answer to a HTTP DELETE request
@Produces(MediaType.TEXT_PLAIN [, more-types ])	@Produces defines which MIME type is delivered by a method annotated with @GET. In the example text ("text/plain") is produced. Other examples would be "application/xml" or "application/json".
@Consumes(type [, more-types ])	@Consumes defines which MIME type is consumed by this method.
@PathParam	Used to inject values from the URL into a method parameter. This way you inject for example the ID of a resource into the method to get the correct object.

The complete path to a resource is basd on the base URL and the @PATh annotation in your class.
http://your_domain:port/display-name/url-pattern/path_from_rest_class 
3. Installation
3.1. Jersey
Download Jersey from the Jersey Homepage.
http://jersey.java.net/ 
As of the time of writing the file is called A zip of Jersey containing the Jersey jars, core dependencies (it does not provide dependencies for third party jars beyond the those for JSON support) and JavaDoc. Download this zip; it contains the jar files required for the REST functionality.
3.2. Eclipse and Tomcat
This tutorial is using Tomcat as servlet container and Eclipse WTP as a development environment. Please seeEclipse WTP and Apache Tomcat for instructions on how to install and use Eclipse WTP and Apache Tomcat.
Alternative you could also use the Google App Engine for running the server part of the following REST examples. If you use the Google App Engine you do not have to setup Tomcat. If you are using GAE/J you have to create App Engine projects instead of Dynamic Web Project.
4. Prerequisites
The following description assumes that you are familiar with creating web applications in Eclipse. See Eclipse WTP development for an introduction into creating web applications with Eclipse.
5. Create your first RESTful Webservice
5.1. Create project with Jersey libraries
Create a new Dynamic Web Project called de.vogella.jersey.first.

Copy all jars from your Jersey download into the WEB-INF/lib folder.
5.2. Java Class
Create the following class.
package de.vogella.jersey.first;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {

  // This method is called if TEXT_PLAIN is request
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String sayPlainTextHello() {
    return "Hello Jersey";
  }

  // This method is called if XML is request
  @GET
  @Produces(MediaType.TEXT_XML)
  public String sayXMLHello() {
    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
  }

  // This method is called if HTML is request
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHtmlHello() {
    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
  }

} 
This class register itself as a get resource via the @GET annotation. Via the @Produces annotation it defines that it delivers the text and the HTML MIME types. It also defines via the @Path annotation that its service is available under the hello URL.
The browser will always request the html MIME type. To see the text version you can use tool like curl.
5.3. Define Jersey Servlet dispatcher
You need to register Jersey as the servlet dispatcher for REST requests. Open the file web.xml and modify the file to the following.
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>de.vogella.jersey.first</display-name>
  <servlet>
    <servlet-name>Jersey REST Service</servlet-name>
    <servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
    <init-param>
      <param-name>com.sun.jersey.config.property.packages</param-name>
      <param-value>de.vogella.jersey.first</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>Jersey REST Service</servlet-name>
    <url-pattern>/rest/*</url-pattern>
  </servlet-mapping>
</web-app> 
The parameter "com.sun.jersey.config.property.package" defines in which package jersey will look for the web service classes. This property must point to your resources classes. The URL pattern defines part of the base URL your application will be placed.
5.4. Run your rest service
Run you web application in Eclipse. See Eclipse WTP for details on how to run dynamic web applications.
Test your REST service under: "http://localhost:8080/de.vogella.jersey.first/rest/hello". This name is derived from the "display-name" defined in the web.xml file, augmented with the servlet-mapping url-pattern and the "hello" @Path annotation from your class file. You should get the message "Hello Jersey".
The browser requests the HTML representation of your resource. In the next chapter we are going to write a client which will read the XML representation.
5.5. Create a client
Jersey contains a REST client library which can be used for testing or to build a real client in Java. Alternative you could use Apache HttpClient to create a client.
Create a new Java "de.vogella.jersey.first.client" and add the jersey jars to the project and the project build path. Create the following test class.
package de.vogella.jersey.first.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Test {
  public static void main(String[] args) {
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);
    WebResource service = client.resource(getBaseURI());
    // Fluent interfaces
    System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString());
    // Get plain text
    System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_PLAIN).get(String.class));
    // Get XML
    System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_XML).get(String.class));
    // The HTML
    System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_HTML).get(String.class));

  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:8080/de.vogella.jersey.first").build();
  }

} 
6. Restful webservices and JAXB
JAX-RS supports the automatic creation of XML and JSON via JAXB. For an introduction into XML please see Java and XML - Tutorial. For an introduction into JAXB please see JAXB. You can continue this tutorial without reading these tutorials but they contain more background information.
6.1. Create project
Create a new Dynamic Web Project called de.vogella.jersey.jaxb and copy all jersey jars into the WEB-INF/lib folder.
Create your domain class.
package de.vogella.jersey.jaxb.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
// Isn't that cool?
public class Todo {
  private String summary;
  private String description;
  public String getSummary() {
    return summary;
  }
  public void setSummary(String summary) {
    this.summary = summary;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
} 
Create the following resource class. This class simply return an instance of the Todo class.
package de.vogella.jersey.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.vogella.jersey.jaxb.model.Todo;

@Path("/todo")
public class TodoResource {
  // This method is called if XMLis request
  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public Todo getXML() {
    Todo todo = new Todo();
    todo.setSummary("This is my first todo");
    todo.setDescription("This is my first todo");
    return todo;
  }
  
  // This can be used to test the integration with the browser
  @GET
  @Produces({ MediaType.TEXT_XML })
  public Todo getHTML() {
    Todo todo = new Todo();
    todo.setSummary("This is my first todo");
    todo.setDescription("This is my first todo");
    return todo;
  }

} 
Change web.xml to the following.
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
  id="WebApp_ID" version="2.5">
  <display-name>de.vogella.jersey.jaxb</display-name>
  <servlet>
    <servlet-name>Jersey REST Service</servlet-name>
    <servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
    <init-param>
      <param-name>com.sun.jersey.config.property.packages</param-name>
      <param-value>de.vogella.jersey.jaxb</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>Jersey REST Service</servlet-name>
    <url-pattern>/rest/*</url-pattern>
  </servlet-mapping>
</web-app> 
Run you web application in Eclipse and validate that you can access your service. Your application should be available under the following URL.
http://localhost:8080/de.vogella.jersey.jaxb/rest/todo 



SOAP

In this article, I will implement a simple Document/Literal web service, from an existing WSDL file, using three different databinding frameworks and two low-level approaches. The goal is to compare the frameworks, mainly from the ease-of-use perspective. For demo purposes, I have chosen NetBeans IDE 6.1 with its standard Web Service functionality and its Axis2 plugin. The standard Web Service support in NetBeans IDE is based on the JAX-WS stack. These are the frameworks used in this article:
JAX-WS 2.1 (Java artifacts generated with wsimport)
JAX-WS 2.1 (Provider API implementation)
Axis with ADB (Axis Databinding)
AXIOM (Axis Object Model)
Axis with Xmlbeans
WSDL File
Initially I wanted to use the simple AddNumbers.html file from JAX-WS2.1 examples but I encountered problems related to Java artifacts generation with both Axis ADB and Xmlbeans. After some investigation I found the reason: ADB and Xmlbeans have problems generating Java artifacts if (1) wsdl namespace and schema namespace are identical and (2) the <wsdl:message> representing <wsdl:fault> has the same name as schema element. Though the wsdl file is WS-I compliant, the 2 databinding technologies failed. I also tested JiBX databinding, without success. To go on I had to modify the wsdl file a little: AddNumbers.wsdl (the schema namespace is changed).Note: Even after this change the JiBX databinding failed to generate Java classes (I used the Axis2 wsdl4j task) so I decided not to use JiBX in this article.
JAX-WS 2.1 (Java artifacts generated with wsimport)
For this purpose I've created new web application project and used Web Service Wrom WSDLwizard in Web Services category. The implementation was easy:
view source
print?
01.@WebService(serviceName = "AddNumbersService", portName = "AddNumbersPort", endpointInterface = "org.example.duke.AddNumbersPortType",
02.targetNamespace = "http://duke.example.org", wsdlLocation = "WEB-INF/wsdl/AddNumbersImpl/AddNumbers.wsdl")
03.public class AddNumbersImpl implements AddNumbersPortType {
04. 
05.public int addNumbers(int arg0, int arg1) throws AddNumbersFault {
06.int result = arg0+arg1;
07.if (result < 0) {
08.org.example.duke.xsd.AddNumbersFault fault = new org.example.duke.xsd.AddNumbersFault();
09.fault.setMessage("the result is negative");
10.fault.setFaultInfo("negative result: "+result);
11.throw new AddNumbersFault("error", fault);
12.} else {
13.return result;
14.}
15.}
16. 
17.public void oneWayInt(int arg0) {
18.System.out.println("JAX-WS: oneWayInt request "+arg0);
19.}
20. 
21.}
 
The implementation is really easy as wsimport by default generates Web Service (SEI class) methods in Wrapping Style mode, which means for requests, responses represented by a sequence of xsd primitive types, the WS method works directly with Java primitive types. For that reason JAX-WS uses the @javax.xml.ws.RequestWrapper and @javax.xml.ws.ResponseWrapper annotations in generated SEI class. The most complex, but not difficult, was the implementation of AddNumbersFault: the exception is thrown when the result is negative. NetBeans Code Completion helped me greatly in this area.
 
JAX-WS 2.1 (Provider API implementation)
To implement the low level approach supporting by JAX-WS 2.1. I used the standard Web Service from WSDL wizard, and I checked the Use Provider checkbox (new NetBeans IDE 6.1 feature). The implementation requires to know the structure of XML request and XML response. See that XML DOM API is used to process the request, while the response is written directly as plain XML text. The hardiest part for me was to implement the Fault correctly.
view source
print?
01.@ServiceMode(value = javax.xml.ws.Service.Mode.PAYLOAD)
02.@WebServiceProvider(serviceName = "AddNumbersService", portName = "AddNumbersPort",
03.targetNamespace = "http://duke.example.org", wsdlLocation = "WEB-INF/wsdl/AddNumbersImpl/AddNumbers.wsdl")
04.public class AddNumbersImpl implements javax.xml.ws.Provider<javax.xml.transform.Source> {
05. 
06.public javax.xml.transform.Source invoke(javax.xml.transform.Source source) {
07.try {
08.DOMResult dom = new DOMResult();
09.Transformer trans = TransformerFactory.newInstance().newTransformer();
10.trans.transform(source, dom);
11.Node node = dom.getNode();
12.Node root = node.getFirstChild();
13.Node first = root.getFirstChild();
14.int number1 = Integer.decode(first.getFirstChild().getNodeValue());
15.Node second = first.getNextSibling();
16.int number2 = Integer.decode(second.getFirstChild().getNodeValue());
17.int result = number1+number2;
18.if (result < 0) {
19.return getFault(result);
20.} else {
21.return getResponse(result);
22.}
23.} catch(Exception e) {
24.throw new RuntimeException("Error in provider endpoint", e);
25.}
26.}
27. 
28.private Source getResponse(int result) {
29.String body =
30."<ns:addNumbersResponse xmlns:ns=\"http://duke.example.org/xsd\"><ns:return>"
31.+result
32.+"</ns:return></ns:addNumbersResponse>";
33.Source source = new StreamSource(
34.new ByteArrayInputStream(body.getBytes()));
35.return source;
36.}
37. 
38.private Source getFault(int result) {
39.String body =
40."<nsf:Faultxmlns:nsf=\"http://schemas.xmlsoap.org/soap/envelope/\">"
41.+"<faultcode>nsf:Server</faultcode>"
42.+"<faultstring>error</faultstring>"
43.+"<detail>"
44.+"<ns:AddNumbersFault xmlns:ns=\"http://duke.example.org/xsd\">"
45.+"<ns:faultInfo>negative result "+result+"</ns:faultInfo>"
46.+"<ns:message>the result is negative</ns:message>"
47.+"</ns:AddNumbersFault>"
48.+"</detail>"
49.+"</nsf:Fault>";
50.Source source = new StreamSource(
51.new ByteArrayInputStream(body.getBytes()));
52.return source;
53.}
54.}
Another option is to use JAXB data binding to process the request and/or to generate the response. JAXB can be used comfortably with Provider API. Advantage of this approach is that implementation code works with JAXB classes generated from schema file rather than with low level DOM objects. The dark side is that JAXB classes (derived from schema file) should be generated in advance. I just copied the content of org.example.duke.xsd package from previous example:
view source
print?
01.public javax.xml.transform.Source invoke(javax.xml.transform.Source source) {
02.try {
03.JAXBContext jc = JAXBContext.newInstance( "org.example.duke.xsd" );
04.Unmarshaller unmarshaller = jc.createUnmarshaller();
05.JAXBElement<AddNumbers> requestEl = (JAXBElement) unmarshaller.unmarshal(source);
06.AddNumbers addNum = requestEl.getValue();
07.int result = addNum.getArg0()+addNum.getArg1();
08.if (result < 0) {
09.return getFault(result);
10.} else {
11.AddNumbersResponse response = new AddNumbersResponse();
12.response.setReturn(result);
13.JAXBElement<AddNumbersResponse> responseEl = new ObjectFactory().createAddNumbersResponse(response);
14.return new JAXBSource(jc, responseEl);
15.}
16.} catch (JAXBException e) {
17.throw new RuntimeException("Error in provider endpoint", e);
18.}
19.}
 
Note: The biggest advantage of JAX-WS Provider/Dispatcher API is the ability to implement/consume web services even for the cases where wsimport fails (e.g. RPC/Encoded WSDL). See Accessing Google Web Service using JAX-WS. Another option would be to implement the invoke method with SOAPMessage parameter instead of javax.xml.transform.Source. This is more convenient than DOM but requires to work with entire SOAP message rather than with SOAP payload.
 
Axis with ADB (Axis Databinding)
To implement web service using Axis ADB I installed the Axis2 plugin on the top of NetBeans IDE 6.1. To set up NetBeans IDE with Axis2 support see the tutorial: Creating Apache Axis2 Web Services on NetBeans IDE. To create web service from wsdl file I used the Axis Web Service from WSDL wizard from Web Services category. In the wizard, the wsdl file can be selected in Name and Location Panel and ADB databinding stack in Code Generator Options panel -> Databinding Technology combo box. The Axis2 wsdl4j utility is called from the wizard, and the skeleton class for web service is generated to implement. The implementation is fairly simple, intuitive and straightforward. The code completions helps a lot :
view source
print?
01.public class AddNumbersImpl implements AddNumbersServiceSkeletonInterface {
02. 
03.public AddNumbersResponse2 addNumbers(AddNumbers1 addNumbers0) throws AddNumbersFault {
04.int result = addNumbers0.getAddNumbers().getArg0() + addNumbers0.getAddNumbers().getArg1();
05.if (result < 0) {
06.AddNumbersFault fault = new AddNumbersFault();
07.AddNumbersFault0 faultMessage = new AddNumbersFault0();
08.org.example.duke.xsd.AddNumbersFault fDetail = new org.example.duke.xsd.AddNumbersFault();
09.fDetail.setFaultInfo("negative result "+result);fDetail.setMessage("the result is negative");
10.faultMessage.setAddNumbersFault(fDetail);
11.fault.setFaultMessage(faultMessage);
12.throw fault;
13.} else {
14.AddNumbersResponse resp = new AddNumbersResponse();
15.resp.set_return(result);
16.AddNumbersResponse2 response = new AddNumbersResponse2();
17.response.setAddNumbersResponse(resp);
18.return response;
19.}
20.}
21. 
22.public void oneWayInt(org.example.duke.xsd.OneWayInt3 oneWayInt2) {
23.try {
24.OMElement request = oneWayInt2.getOMElement(OneWayInt3.MY_QNAME, OMAbstractFactory.getOMFactory());
25.System.out.println("ADB:oneWayInt request: "+request);
26.} catch (ADBException ex) {
27.ex.printStackTrace();
28.}
29.}
30.}
 
Note: Axis2 doesn't use the Wrapping Style, so the parameter of AddNumbers method, in skeleton class, is AddNumbers1 object instead of 2 int parameters (I didn't find if axis2 enables to set up wrapping style).
 
AXIOM (AxisObject Model)
This is a low level technique, similar to JAX-WS Provider/Dispatcherer API. Working with OM nodes and elemens is more comfortable comparing to DOM but less comparing to ADB or JAXB. I'd compare it to working with SAAJ API. The imlementation is also quite straightforward but requires the knowledge of AXIOM API. The skeleton class can be generated by Axis Service from Wsdl wizard by selecting the AXIOM Databinding Technology.Again, I spent the most of the time by Fault implementation:
view source
print?
01.public class AddNumbersImpl {
02.private static final String SCHEMA_NAMESPACE = "http://duke.example.org/xsd";
03.private OMFactory omFactory = OMAbstractFactory.getOMFactory();
04. 
05.public OMElement addNumbers(OMElement requestElement) throws XMLStreamException {
06. 
07.int value1 = Integer.valueOf(getRequestParam(requestElement, "arg0")).intValue();
08.int value2 = Integer.valueOf(getRequestParam(requestElement, "arg1")).intValue();
09.int result = value1+value2;
10.if (result < 0) {
11. 
12.OMNode text = omFactory.createOMText("negative result");
13.OMNode text1 = omFactory.createOMText("the result is negative");
14. 
15.OMNamespace omNs = omFactory.createOMNamespace(SCHEMA_NAMESPACE, "ns");
16. 
17.OMElement responseChildElement = omFactory.createOMElement("faultInfo", omNs);
18.responseChildElement.addChild(text);
19.OMElement responseChildElement1 = omFactory.createOMElement("message", omNs);
20.responseChildElement1.addChild(text1);
21. 
22.OMElement faultElement = omFactory.createOMElement("AddNumbersFault", omNs);
23.faultElement.addChild(responseChildElement);
24.faultElement.addChild(responseChildElement1);
25. 
26. 
27.SOAPFault fault = OMAbstractFactory.getSOAP11Factory().createSOAPFault();
28.SOAPFaultCode code = OMAbstractFactory.getSOAP11Factory().createSOAPFaultCode();
29.code.setText(fault.getNamespace().getPrefix()+":Server");
30. 
31.SOAPFaultReason faultstring = OMAbstractFactory.getSOAP11Factory().createSOAPFaultReason();
32.faultstring.setText("negative result");
33. 
34.SOAPFaultDetail detail = OMAbstractFactory.getSOAP11Factory().createSOAPFaultDetail();
35.detail.addChild(faultElement);
36. 
37.fault.setCode(code);
38.fault.setReason(faultstring);
39.fault.setDetail(detail);
40. 
41.return fault;
42. 
43.} else {
44.String resultStr = String.valueOf(result);       
45.OMNode response = omFactory.createOMText(resultStr);
46.return createResponse("addNumbersResponse", "return", response);
47.}
48.}
49. 
50.public void oneWayInt(OMElement requestElement) throws XMLStreamException {
51.System.out.println("AXIOM:oneWayInt request: "+requestElement);
52.}
53. 
54.private String getRequestParam(OMElement requestElement, String requestChildName) {
55.OMElement requestChildElement =
56.requestElement.getFirstChildWithName(new QName(SCHEMA_NAMESPACE, requestChildName));
57.return requestChildElement.getText();       
58.}
59. 
60.private OMElement createResponse(String responseElementName, String responseChildName, OMNode response) {
61.OMNamespace omNs = omFactory.createOMNamespace(SCHEMA_NAMESPACE, "ns");
62.OMElement responseElement = omFactory.createOMElement(responseElementName, omNs);
63.OMElement responseChildElement = omFactory.createOMElement(responseChildName, omNs);
64.responseChildElement.addChild(response);
65.responseElement.addChild(responseChildElement);
66.return responseElement;
67.}
68.}
Axis with Xmlbeans
This is the WS Stack supporting also by Axis2 technology. The skeleton class can be generated byAxis Service from Wsdl wizard by selecting the Xmlbeans Databinding Technology.The implementation is quite straightforward similar to Axis2 ADB. The awkwardness of this approach is the number of classes(225) generated by selecting this option. I am not an expert in Xmlbeans so this number may be reduced somehow. This is the implementation class:
view source
print?
01.public class AddNumbersImpl implements AddNumbersServiceSkeletonInterface {
02. 
03.public AddNumbersResponseDocument addNumbers(org.example.duke.xsd.AddNumbersDocument addNumbers0) throws AddNumbersFault {
04.//System.out.println("Xmlbeans: addNumbers request: "+addNumbers0);
05.int result = addNumbers0.getAddNumbers().getArg0() + addNumbers0.getAddNumbers().getArg1();
06.if (result < 0) {
07.AddNumbersFault fault = new AddNumbersFault();
08.AddNumbersFaultDocument faultDoc = AddNumbersFaultDocument.Factory.newInstance();
09.org.example.duke.xsd.AddNumbersFault fDetail = org.example.duke.xsd.AddNumbersFault.Factory.newInstance();
10.fDetail.setFaultInfo("negative result "+result);
11.fDetail.setMessage("the result is negative");
12.faultDoc.setAddNumbersFault(fDetail);
13.fault.setFaultMessage(faultDoc);
14.throw fault;
15.}
16.AddNumbersResponseDocument response = AddNumbersResponseDocument.Factory.newInstance();
17.AddNumbersResponse resp = AddNumbersResponse.Factory.newInstance();
18.resp.setReturn(result);
19.response.setAddNumbersResponse(resp);
20.return response;
21.}
22. 
23.public void oneWayInt(org.example.duke.xsd.OneWayIntDocument oneWayInt2) {
24.//TODO implement this method
25.System.out.println("Xmlbeans: oneWayInt request: "+oneWayInt2);
26.}
27. 
28.}
Summary
I tried to compare 5 different techniques of 2 popular WS Stacks(JAX-WS and Axis2) to implement a simple SOAP web service. The role of all these techniques is to process the XML payload, of the request, and generate the response. The high level techniques like JAX_WS, Axis with ADB or Axis with Xmlbeans look very similar in their essence and protect users from working with low level XML APIs and from knowing the structure of SOAP messages. Both low level implementation techniques documented here are difficult to use and cumbersome in some extent. On other side these techniques can be used even in cases when high level techiques fail.


Soap web services reference ::
http://docs.oracle.com/javaee/5/tutorial/doc/bnayn.html


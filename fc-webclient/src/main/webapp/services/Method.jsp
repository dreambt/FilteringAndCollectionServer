<%@page contentType="text/html;charset=UTF-8"%><HTML>
<HEAD>
<TITLE>Methods</TITLE>
<STYLE TYPE="text/css">
	body {
		font-family: Helvetica, Arial, sans-serif;
		font-size: small
	}
</STYLE>
</HEAD>
<BODY>
<H2>ALE Webclient</H2>
<h3>Filtering and Collection API (ALEServicePort)</h3>
<UL>
<LI><A HREF="Input.jsp?method=2" TARGET="inputs"> getEndpoint()</A></LI>
<LI><A HREF="Input.jsp?method=5" TARGET="inputs"> setEndpoint(String endPointName)</A></LI>
<LI><A HREF="Input.jsp?method=14" TARGET="inputs"> define(String specName, String specFilePath)</A></LI>
<LI><A HREF="Input.jsp?method=50" TARGET="inputs"> undefine(String specName)</A></LI>
<LI><A HREF="Input.jsp?method=57" TARGET="inputs"> getECSpec(String specName)</A></LI>
<LI><A HREF="Input.jsp?method=104" TARGET="inputs"> getECSpecNames()</A></LI>
<LI><A HREF="Input.jsp?method=109" TARGET="inputs"> subscribe(String specName, String notificationUri)</A></LI>
<LI><A HREF="Input.jsp?method=118" TARGET="inputs"> unsubscribe(String specName, String notificationUri)</A></LI>
<LI><A HREF="Input.jsp?method=127" TARGET="inputs"> poll(String specName)</A></LI>
<LI><A HREF="Input.jsp?method=194" TARGET="inputs"> immediate(String specFilePath)</A></LI>
<LI><A HREF="Input.jsp?method=289" TARGET="inputs"> getSubscribers(String specName)</A></LI>
<LI><A HREF="Input.jsp?method=296" TARGET="inputs"> getStandardVersion()</A></LI>
<LI><A HREF="Input.jsp?method=301" TARGET="inputs"> getVendorVersion()</A></LI>
</UL>
<BR/>
<BR/>
<h3>LogicalReader API (ALELRServicePort)</h3>
<UL>
<LI><A HREF="Input.jsp?method=318" TARGET="inputs"> getEndpoint()</A></LI>
<LI><A HREF="Input.jsp?method=317" TARGET="inputs"> setEndpoint(String endPointName)</A></LI>
<LI><A HREF="Input.jsp?method=305" TARGET="inputs"> define(String readerName, LRSpec spec)</A></LI>
<LI><A HREF="Input.jsp?method=306" TARGET="inputs"> undefine(String readerName)</A></LI>
<LI><A HREF="Input.jsp?method=307" TARGET="inputs"> update(String readerName, LRSpec spec)</A></LI>
<LI><A HREF="Input.jsp?method=308" TARGET="inputs"> getLogicalReaderNames()</A></LI>
<LI><A HREF="Input.jsp?method=309" TARGET="inputs"> getLRSpec(String readerName)</A></LI>
<LI><A HREF="Input.jsp?method=310" TARGET="inputs"> addReaders(String readerName, String[] readers)</A></LI>
<LI><A HREF="Input.jsp?method=311" TARGET="inputs"> setReaders(String readerName, String[] readers)</A></LI>
<LI><A HREF="Input.jsp?method=312" TARGET="inputs"> removeReaders(String readerName, String[] readers)</A></LI>
<LI><A HREF="Input.jsp?method=313" TARGET="inputs"> setProperties(String readerName, LRProperty[] properties</A></LI>
<LI><A HREF="Input.jsp?method=314" TARGET="inputs"> getPropertyValue(String readerName, String propertyName)</A></LI>
<LI><A HREF="Input.jsp?method=315" TARGET="inputs"> getStandardVersion()</A></LI>
<LI><A HREF="Input.jsp?method=316" TARGET="inputs"> getVendorVersion()</A></LI>
</UL>
</BODY>
</HTML>
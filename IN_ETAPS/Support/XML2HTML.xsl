<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:exsl="http://exslt.org/common"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns:mml="http://www.w3.org/1998/Math/MathML"
    exclude-result-prefixes="xs"
	xmlns:saxon="http://icl.com/saxon"
    extension-element-prefixes="saxon"
	version="2.0" input-type-annotations="unspecified">   
	
<xsl:include href="decimal-map.xsl"/>

<xsl:output method="xhtml" encoding="utf-8" indent="yes" omit-xml-declaration="no" 
use-character-maps="html-entity-map private-entity-map" 
cdata-section-elements="tex-math"
 saxon:character-representation="decimal" 
saxon:require-well-formed="yes" />

<xsl:preserve-space elements="ref-list ref element-citation"/>

    <xsl:template match="/">
        <html>
		   <head>
			<link rel="stylesheet" type="text/css" href="http://localhost:8080/ETAPS/resources/css/main.css"></link>
			</head>
			<body>                
                <xsl:apply-templates select="article"/>				
            </body>
        </html>
    </xsl:template>

    <xsl:template match="*">
<!--       <xsl:copy>
        <xsl:attribute name='idname'>TRS_ID_<xsl:number level='any' count='*' />
		</xsl:attribute><xsl:copy-of select="@*"/>
      </xsl:copy>
 -->	<xsl:choose>
	 <xsl:when test="count(@*|node()) &lt;= 2">
	 <span class="{local-name()}">
	 			<xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:when>
    <xsl:otherwise>
	  <div class="{local-name()}">
			<xsl:apply-templates select="@*|node()"/>		
      </div>
	</xsl:otherwise>	
  </xsl:choose>		
  </xsl:template>
    <xsl:template match="@*">
        <xsl:if test="name() != 'class'">
		<xsl:copy-of select="."/>            
        </xsl:if>
		
    </xsl:template>	

	<!-- child level rewamb -->


 <!--    <xsl:template match="tex-math">
        <tex-math class="tex-math">
            <xsl:apply-templates select="@*|node()"/>
        </tex-math>
    </xsl:template>	
 -->
<!-- 	<xsl:element name="tex-math">
    <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
    <xsl:apply-templates select="child::node()"/>
    <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
    </xsl:element>
 -->
	
    <xsl:template match="article-title">
        <div class="article-title">
            <xsl:apply-templates select="@*|node()"/>
        </div>
    </xsl:template>	
    <xsl:template match="title">
        <div class="title">
            <xsl:apply-templates select="@*|node()"/>
        </div>
    </xsl:template>
   <xsl:template match="institution">
        <span class="institution">
            <xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:template>
   <xsl:template match="xref">
        <span class="xref">
            <xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:template>
	<xsl:template match="author-comment/p">
        <span class="p">
            <xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:template>
    <xsl:template match="inline-graphic[@xlink:type = 'simple' and @xlink:href]">
        <div class="inline-graphic">
            <xsl:apply-templates select="@*|node()"/>            
            <img src="graphic/{@xlink:href}"><xsl:apply-templates/></img>            
        </div>
    </xsl:template>
   <xsl:template match="graphic[@xlink:type = 'simple' and @xlink:href]">
        <div class="graphic">
            <xsl:apply-templates select="@*|node()"/>            
            <img src="graphic/{@xlink:href}"><xsl:apply-templates/></img>            
        </div>
    </xsl:template>

	<xsl:template match="xref[@rid]">
        <span class="xref">
            <xsl:apply-templates select="@*"/>
			<a href="#{@rid}"><xsl:apply-templates/></a>
        </span>
    </xsl:template>
	
	  <xsl:template match="comment">
        <span class="comment">
            <xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:template>
      <xsl:template match="ext-link">
        <span class="ext-link">
            <xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:template>
	    <xsl:template match="person-group">
        <span class="person-group">
            <xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:template>
	<xsl:template match="surname">
        <span class="surname">
            <xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:template>
	<xsl:template match="given-names">
        <span class="given-names">
            <xsl:apply-templates select="@*|node()"/>
        </span>
    </xsl:template>
   <xsl:template match="p">
        <div class="p">
            <xsl:apply-templates select="@*|node()"/>
        </div>
    </xsl:template>
	
	<!-- Table Replace -->
	
	<xsl:template match="table">
        <table class="table">
            <xsl:apply-templates select="@*|node()"/>
        </table>
    </xsl:template>     
    <xsl:template match="thead">
        <thead class="thead">
            <xsl:apply-templates select="@*|node()"/>
        </thead>
    </xsl:template>     
    <xsl:template match="tbody">
        <tbody class="tbody">
            <xsl:apply-templates select="@*|node()"/>
        </tbody>
    </xsl:template>     
    <xsl:template match="tfoot">
        <tfoot class="tfoot">
            <xsl:apply-templates select="@*|node()"/>
        </tfoot>
    </xsl:template>     
    <xsl:template match="col">
        <col class="col">
            <xsl:apply-templates select="@*|node()"/>
        </col>
    </xsl:template>     
    <xsl:template match="colgroup">
        <colgroup class="colgroup">
            <xsl:apply-templates select="@*|node()"/>
        </colgroup>
    </xsl:template>     
    <xsl:template match="tr">
        <tr class="tr">
            <xsl:apply-templates select="@*|node()"/>
        </tr>
    </xsl:template>     
    <xsl:template match="th">
        <th class="th">
            <xsl:apply-templates select="@*|node()"/>
        </th>
    </xsl:template>     
    <xsl:template match="td">
        <td class="td">
            <xsl:apply-templates select="@*|node()"/>
        </td>
    </xsl:template>    
	<xsl:template match="hr">
        <hr class="hr">
            <xsl:apply-templates select="@*|node()"/>
        </hr>
    </xsl:template>    

	<xsl:template match="text()[normalize-space() = '']"/>

<!-- <xsl:template match="//*[@class='tex-math']">
    <xsl:element name="{local-name()}">
                <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                <xsl:apply-templates select="child::node()"/>
                <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
    </xsl:element>
</xsl:template>
 -->	
	</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"	
	xmlns:saxon="http://saxon.sf.net/"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:exsl="http://exslt.org/common"
    xmlns:mml="http://www.w3.org/1998/Math/MathML"
	xmlns:xlink="http://www.w3.org/1999/xlink" 
    exclude-result-prefixes="xs saxon mml exsl xsi xlink"
	extension-element-prefixes="xlink"
    version="2.0" input-type-annotations="unspecified">   

<xsl:include href="entity-map.xsl"/>


<xsl:output method="xml" 
use-character-maps="html-entity-map private-entity-map" 
saxon:character-representation="decimal" 
encoding="utf-8" indent="yes"
omit-xml-declaration="no" 
saxon:require-well-formed="yes"
cdata-section-elements="tex-math"
doctype-system="H:/Programme/ETAPS/dtd/journalpublishing3.dtd" 
doctype-public="-//NLM//DTD Journal Publishing DTD v3.0 20080202//EN"/>


<xsl:strip-space elements="*"/>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

  <xsl:template match="div|span[contains(@class,'')]">
    <xsl:element name="{@class}">
        <xsl:copy-of select="attribute::node()"/>
		<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>  
	
	<xsl:template match="a">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="img">
		<xsl:apply-templates/>
	</xsl:template>

    <xsl:template match="head"/>    	 

	<xsl:template match="body" priority="1">
        <xsl:apply-templates select="@*|node()"/>
      </xsl:template>    
	  
<!--      <xsl:template match="html" priority="1">
        <xsl:apply-templates select="@*|node()"/>
      </xsl:template>	  
 -->	  
	
	 	<xsl:template match="//*[@class='optbold']">
		<xsl:comment>&lt;optbold-start id="<xsl:apply-templates select="@id"/>" bold-text="<xsl:apply-templates select="node()"/>"&gt;</xsl:comment>
		<bold>
			<xsl:apply-templates select="node()"/>
		</bold>
		<xsl:comment>&lt;/optbold-end&gt;</xsl:comment>
	</xsl:template>

	
	<xsl:template match="//*[@class='optitalic']">
		<xsl:comment>&lt;optitalic-start id="<xsl:apply-templates select="@id"/>" italic-text="<xsl:apply-templates select="node()"/>"&gt;</xsl:comment>
		<italic>
			<xsl:apply-templates select="node()"/>
		</italic>	
		<xsl:comment>&lt;/optitalic-end&gt;</xsl:comment>
	</xsl:template>

	<xsl:template match="//*[@class='optsup']">
		<xsl:comment>&lt;optsup-start id="<xsl:apply-templates select="@id"/>" sup-text="<xsl:apply-templates select="node()"/>"&gt;</xsl:comment>
		<sup>
			<xsl:apply-templates select="node()"/>
		</sup>	
		<xsl:comment>&lt;/optsup-end&gt;</xsl:comment>
	</xsl:template>

	<xsl:template match="//*[@class='optsub']">
		<xsl:comment>&lt;optsub-start id="<xsl:apply-templates select="@id"/>" sub-text="<xsl:apply-templates select="node()"/>"&gt;</xsl:comment>
		<sub>
			<xsl:apply-templates select="node()"/>
		</sub>	
		<xsl:comment>&lt;/optsub-end&gt;</xsl:comment>
	</xsl:template>  

	<xsl:template match="//*[@class='optins']">
		<xsl:comment>&lt;optins-start id="<xsl:apply-templates select="@id"/>" insert-text="<xsl:apply-templates select="node()"/>"&gt;</xsl:comment>	  
		<xsl:apply-templates select="node()"/>
		<xsl:comment>&lt;/optins-end&gt;</xsl:comment>
	</xsl:template> 

	<xsl:template match="//*[@class='optdel']">
		<xsl:comment>&lt;optdel-start id="<xsl:apply-templates select="@id"/>" del-text="<xsl:apply-templates select="node()"/>"&gt;</xsl:comment>
		<xsl:comment>&lt;/optdel-end&gt;</xsl:comment>
	</xsl:template>   

	<xsl:template match="//*[@class='optinstruct']">
		<xsl:comment>&lt;optinstruct-start id="<xsl:apply-templates select="@id"/>" Instruct="<xsl:apply-templates select="node()"/>"&gt;</xsl:comment>	  	  
		<instruct>
			<xsl:apply-templates select="@*|node()"/>
		</instruct>	
		<xsl:comment>&lt;/optinstruct-end&gt;</xsl:comment>
	</xsl:template>

	<xsl:template match="//*[@class='optholder']"/>  

	<xsl:template match="text()" priority="1">
		<xsl:variable name="temp" select="normalize-space(concat('x', ., 'x'))" />
			<xsl:value-of select="substring($temp, 2, string-length($temp) - 2)"/>
	</xsl:template>
	<xsl:template match="*/text()[normalize-space()='']" />
		
	<xsl:template match="@class"/>	
	<xsl:template match="@idname"/>
	<xsl:template match="@name-style"/>		
		
</xsl:stylesheet>
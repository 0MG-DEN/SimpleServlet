<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>

	<xsl:template match="/">
		<html>
			<head>
				<title>Dump Media</title>
				<style>
					body
					{
						background: black;
					}
					img, video
					{
						border: solid 1px white;
						max-width: 100vw;
						max-height: 100vh;
						margin: 0.5rem;
						padding: 0.5rem;
					}
				</style>
			</head>
			<body>
				<xsl:apply-templates select="//img"/>
				<xsl:apply-templates select="//video"/>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="img[@src]">
		<xsl:element name="img">
			<xsl:attribute name="src">
				<xsl:value-of select="@src"/>
			</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<xsl:template match="video[@src]">
		<xsl:element name="video">
			<xsl:attribute name="controls"/>
			<xsl:attribute name="loop"/>
			<xsl:attribute name="src">
				<xsl:value-of select="@src"/>
			</xsl:attribute>
			<xsl:attribute name="onmouseover">
				<xsl:text>this.play()</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="onmouseout">
				<xsl:text>this.pause()</xsl:text>
			</xsl:attribute>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>

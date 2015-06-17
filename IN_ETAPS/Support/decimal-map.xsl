<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE stylesheet [
  <!ENTITY % w3centities-f PUBLIC "-//W3C//ENTITIES Combined Set//EN//XML"
      "http://www.w3.org/2003/entities/2007/w3centities-f.ent">
  %w3centities-f;
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
        
    <xsl:character-map name="html-entity-map">
        <xsl:output-character character="&nbsp;"      string="&amp;#160;" />
        <xsl:output-character character="&iexcl;"     string="&amp;#161;" />
        <xsl:output-character character="&cent;"      string="&amp;#162;" />
        <xsl:output-character character="&pound;"     string="&amp;#163;" />
        <xsl:output-character character="&curren;"    string="&amp;#164;" />
        <xsl:output-character character="&yen;"       string="&amp;#165;" />
        <xsl:output-character character="&brvbar;"    string="&amp;#166;" />
        <xsl:output-character character="&sect;"      string="&amp;#167;"  />
        <xsl:output-character character="&uml;"       string="&amp;#168;" />
        <xsl:output-character character="&copy;"      string="&amp;#169;"  />
        <xsl:output-character character="&ordf;"      string="&amp;#170;"  />
        <xsl:output-character character="&laquo;"     string="&amp;#171;"   />
        <xsl:output-character character="&not;"       string="&amp;#172;"    />
        <xsl:output-character character="&shy;"       string="&amp;#173;"    />
        <xsl:output-character character="&reg;"       string="&amp;#174;"    />
        <xsl:output-character character="&macr;"      string="&amp;#175;"  />
        <xsl:output-character character="&deg;"       string="&amp;#176;"    />
        <xsl:output-character character="&plusmn;"    string="&amp;#177;"    />
        <xsl:output-character character="&sup2;"      string="&amp;#178;"  />
        <xsl:output-character character="&sup3;"      string="&amp;#179;"  />
        <xsl:output-character character="&acute;"     string="&amp;#180;"   />
        <xsl:output-character character="&micro;"     string="&amp;#181;"   />
        <xsl:output-character character="&para;"      string="&amp;#182;"  />
        <xsl:output-character character="&middot;"    string="&amp;#183;"    />
        <xsl:output-character character="&cedil;"     string="&amp;#184;"   />
        <xsl:output-character character="&sup1;"      string="&amp;#185;"  />
        <xsl:output-character character="&ordm;"      string="&amp;#186;"  />
        <xsl:output-character character="&raquo;"     string="&amp;#187;"   />
        <xsl:output-character character="&frac14;"    string="&amp;#188;"    />
        <xsl:output-character character="&frac12;"    string="&amp;#189;"    />
        <xsl:output-character character="&frac34;"    string="&amp;#190;"    />
        <xsl:output-character character="&iquest;"    string="&amp;#191;"    />
        <xsl:output-character character="&Agrave;"    string="&amp;#192;"    />
        <xsl:output-character character="&Aacute;"    string="&amp;#193;"    />
        <xsl:output-character character="&Acirc;"     string="&amp;#194;"   />
        <xsl:output-character character="&Atilde;"    string="&amp;#195;"    />
        <xsl:output-character character="&Auml;"      string="&amp;#196;"  />
        <xsl:output-character character="&Aring;"     string="&amp;#197;"   />
        <xsl:output-character character="&AElig;"     string="&amp;#198;"   />
        <xsl:output-character character="&Ccedil;"    string="&amp;#199;"    />
        <xsl:output-character character="&Egrave;"    string="&amp;#200;"    />
        <xsl:output-character character="&Eacute;"    string="&amp;#201;"    />
        <xsl:output-character character="&Ecirc;"     string="&amp;#202;"   />
        <xsl:output-character character="&Euml;"      string="&amp;#203;"  />
        <xsl:output-character character="&Igrave;"    string="&amp;#204;"    />
        <xsl:output-character character="&Iacute;"    string="&amp;#205;"    />
        <xsl:output-character character="&Icirc;"     string="&amp;#206;"   />
        <xsl:output-character character="&Iuml;"      string="&amp;#207;"  />
        <xsl:output-character character="&ETH;"       string="&amp;#208;"    />
        <xsl:output-character character="&Ntilde;"    string="&amp;#209;"    />
        <xsl:output-character character="&Ograve;"    string="&amp;#210;"    />
        <xsl:output-character character="&Oacute;"    string="&amp;#211;"    />
        <xsl:output-character character="&Ocirc;"     string="&amp;#212;"   />
        <xsl:output-character character="&Otilde;"    string="&amp;#213;"    />
        <xsl:output-character character="&Ouml;"      string="&amp;#214;"  />
        <xsl:output-character character="&times;"     string="&amp;#215;"   />
        <xsl:output-character character="&Oslash;"    string="&amp;#216;"    />
        <xsl:output-character character="&Ugrave;"    string="&amp;#217;"    />
        <xsl:output-character character="&Uacute;"    string="&amp;#218;"    />
        <xsl:output-character character="&Ucirc;"     string="&amp;#219;"   />
        <xsl:output-character character="&Uuml;"      string="&amp;#220;"  />
        <xsl:output-character character="&Yacute;"    string="&amp;#221;"    />
        <xsl:output-character character="&THORN;"     string="&amp;#222;"   />
        <xsl:output-character character="&szlig;"     string="&amp;#223;"   />
        <xsl:output-character character="&agrave;"    string="&amp;#224;"    />
        <xsl:output-character character="&aacute;"    string="&amp;#225;"    />
        <xsl:output-character character="&acirc;"     string="&amp;#226;"   />
        <xsl:output-character character="&atilde;"    string="&amp;#227;"    />
        <xsl:output-character character="&auml;"      string="&amp;#228;"  />
        <xsl:output-character character="&aring;"     string="&amp;#229;"   />
        <xsl:output-character character="&aelig;"     string="&amp;#230;"   />
        <xsl:output-character character="&ccedil;"    string="&amp;#231;"    />
        <xsl:output-character character="&egrave;"    string="&amp;#232;"    />
        <xsl:output-character character="&eacute;"    string="&amp;#233;"    />
        <xsl:output-character character="&ecirc;"     string="&amp;#234;"   />
        <xsl:output-character character="&euml;"      string="&amp;#235;"  />
        <xsl:output-character character="&igrave;"    string="&amp;#236;"    />
        <xsl:output-character character="&iacute;"    string="&amp;#237;"    />
        <xsl:output-character character="&icirc;"     string="&amp;#238;"   />
        <xsl:output-character character="&iuml;"      string="&amp;#239;"  />
        <xsl:output-character character="&eth;"       string="&amp;#240;"    />
        <xsl:output-character character="&ntilde;"    string="&amp;#241;"    />
        <xsl:output-character character="&ograve;"    string="&amp;#242;"    />
        <xsl:output-character character="&oacute;"    string="&amp;#243;"    />
        <xsl:output-character character="&ocirc;"     string="&amp;#244;"   />
        <xsl:output-character character="&otilde;"    string="&amp;#245;"    />
        <xsl:output-character character="&ouml;"      string="&amp;#246;"  />
        <xsl:output-character character="&divide;"    string="&amp;#247;"    />
        <xsl:output-character character="&oslash;"    string="&amp;#248;"    />
        <xsl:output-character character="&ugrave;"    string="&amp;#249;"    />
        <xsl:output-character character="&uacute;"    string="&amp;#250;"    />
        <xsl:output-character character="&ucirc;"     string="&amp;#251;"   />
        <xsl:output-character character="&uuml;"      string="&amp;#252;"  />
        <xsl:output-character character="&yacute;"    string="&amp;#253;"    />
        <xsl:output-character character="&thorn;"     string="&amp;#254;"   />
        <xsl:output-character character="&yuml;"      string="&amp;#255;"  />
        <xsl:output-character character="&OElig;"     string="&amp;#338;"   />
        <xsl:output-character character="&oelig;"     string="&amp;#339;"   />
        <xsl:output-character character="&Scaron;"    string="&amp;#352;"    />
        <xsl:output-character character="&scaron;"    string="&amp;#353;"    />
        <xsl:output-character character="&Yuml;"      string="&amp;#376;"  />
        <xsl:output-character character="&fnof;"      string="&amp;#402;"  />
        <xsl:output-character character="&circ;"      string="&amp;#710;"  />
        <xsl:output-character character="&tilde;"     string="&amp;#732;"   />
        <xsl:output-character character="&Alpha;"     string="&amp;#913;"   />
        <xsl:output-character character="&Beta;"      string="&amp;#914;"  />
        <xsl:output-character character="&Gamma;"     string="&amp;#915;"   />
        <xsl:output-character character="&Delta;"     string="&amp;#916;"   />
        <xsl:output-character character="&Epsilon;"   string="&amp;#917;"  />
        <xsl:output-character character="&Zeta;"      string="&amp;#918;"  />
        <xsl:output-character character="&Eta;"       string="&amp;#919;"    />
        <xsl:output-character character="&Theta;"     string="&amp;#920;"   />
        <xsl:output-character character="&Iota;"      string="&amp;#921;"  />
        <xsl:output-character character="&Kappa;"     string="&amp;#922;"   />
        <xsl:output-character character="&Lambda;"    string="&amp;#923;"    />
        <xsl:output-character character="&Mu;"        string="&amp;#924;"   />
        <xsl:output-character character="&Nu;"        string="&amp;#925;"   />
        <xsl:output-character character="&Xi;"        string="&amp;#926;"   />
        <xsl:output-character character="&Omicron;"   string="&amp;#927;"  />
        <xsl:output-character character="&Pi;"        string="&amp;#928;"   />
        <xsl:output-character character="&Rho;"       string="&amp;#929;"    />
        <xsl:output-character character="&Sigma;"     string="&amp;#931;"   />
        <xsl:output-character character="&Tau;"       string="&amp;#932;"    />
        <xsl:output-character character="&Upsilon;"   string="&amp;#933;"  />
        <xsl:output-character character="&Phi;"       string="&amp;#934;"    />
        <xsl:output-character character="&Chi;"       string="&amp;#935;"    />
        <xsl:output-character character="&Psi;"       string="&amp;#936;"    />
        <xsl:output-character character="&Omega;"     string="&amp;#937;"   />
        <xsl:output-character character="&alpha;"     string="&amp;#945;"   />
        <xsl:output-character character="&beta;"      string="&amp;#946;"  />
        <xsl:output-character character="&gamma;"     string="&amp;#947;"   />
        <xsl:output-character character="&delta;"     string="&amp;#948;"   />
        <xsl:output-character character="&epsilon;"   string="&amp;#949;"  />
        <xsl:output-character character="&zeta;"      string="&amp;#950;"  />
        <xsl:output-character character="&eta;"       string="&amp;#951;"    />
        <xsl:output-character character="&theta;"     string="&amp;#952;"   />
        <xsl:output-character character="&iota;"      string="&amp;#953;"  />
        <xsl:output-character character="&kappa;"     string="&amp;#954;"   />
        <xsl:output-character character="&lambda;"    string="&amp;#955;"    />
        <xsl:output-character character="&mu;"        string="&amp;#956;"   />
        <xsl:output-character character="&nu;"        string="&amp;#957;"   />
        <xsl:output-character character="&xi;"        string="&amp;#958;"   />
        <xsl:output-character character="&omicron;"   string="&amp;#959;"  />
        <xsl:output-character character="&pi;"        string="&amp;#960;"   />
        <xsl:output-character character="&rho;"       string="&amp;#961;"    />
        <xsl:output-character character="&sigmaf;"    string="&amp;#962;"    />
        <xsl:output-character character="&sigma;"     string="&amp;#963;"   />
        <xsl:output-character character="&tau;"       string="&amp;#964;"    />
        <xsl:output-character character="&upsilon;"   string="&amp;#965;"  />
        <xsl:output-character character="&phi;"       string="&amp;#966;"    />
        <xsl:output-character character="&chi;"       string="&amp;#967;"    />
        <xsl:output-character character="&psi;"       string="&amp;#968;"    />
        <xsl:output-character character="&omega;"     string="&amp;#969;"   />
        <xsl:output-character character="&thetasym;"  string="&amp;#977;"  />
        <xsl:output-character character="&upsih;"     string="&amp;#978;"   />
        <xsl:output-character character="&piv;"       string="&amp;#982;"    />
        <xsl:output-character  character="&ensp;"     string="&amp;#8194;"  />
        <xsl:output-character  character="&emsp;"     string="&amp;#8195;"  />
        <xsl:output-character  character="&thinsp;"   string="&amp;#8201;"    />
        <xsl:output-character  character="&zwnj;"     string="&amp;#8204;"  />
        <xsl:output-character  character="&zwj;"      string="&amp;#8205;"    />
        <xsl:output-character  character="&lrm;"      string="&amp;#8206;"    />
        <xsl:output-character  character="&rlm;"      string="&amp;#8207;"    />
        <xsl:output-character  character="&ndash;"    string="&amp;#8211;"   />
        <xsl:output-character  character="&mdash;"    string="&amp;#8212;"   />
        <xsl:output-character  character="&lsquo;"    string="&amp;#8216;"   />
        <xsl:output-character  character="&rsquo;"    string="&amp;#8217;"   />
        <xsl:output-character  character="&sbquo;"    string="&amp;#8218;"   />
        <xsl:output-character  character="&ldquo;"    string="&amp;#8220;"   />
        <xsl:output-character  character="&rdquo;"    string="&amp;#8221;"   />
        <xsl:output-character  character="&bdquo;"    string="&amp;#8222;"   />
        <xsl:output-character  character="&dagger;"   string="&amp;#8224;"    />
        <xsl:output-character  character="&Dagger;"   string="&amp;#8225;"    />
        <xsl:output-character  character="&bull;"     string="&amp;#8226;"  />
        <xsl:output-character  character="&hellip;"   string="&amp;#8230;"    />
        <xsl:output-character  character="&permil;"   string="&amp;#8240;"    />
        <xsl:output-character  character="&prime;"    string="&amp;#8242;"   />
        <xsl:output-character  character="&Prime;"    string="&amp;#8243;"   />
        <xsl:output-character  character="&lsaquo;"   string="&amp;#8249;"    />
        <xsl:output-character  character="&rsaquo;"   string="&amp;#8250;"    />
        <xsl:output-character  character="&oline;"    string="&amp;#8254;"   />
        <xsl:output-character  character="&frasl;"    string="&amp;#8260;"   />
        <xsl:output-character  character="&euro;"     string="&amp;#8364;"  />
        <xsl:output-character  character="&image;"    string="&amp;#8465;"   />
        <xsl:output-character  character="&weierp;"   string="&amp;#8472;"    />
        <xsl:output-character  character="&real;"     string="&amp;#8476;"  />
        <xsl:output-character  character="&trade;"    string="&amp;#8482;"   />
        <xsl:output-character  character="&alefsym;"  string="&amp;#8501;"  />
        <xsl:output-character  character="&larr;"     string="&amp;#8592;"  />
        <xsl:output-character  character="&uarr;"     string="&amp;#8593;"  />
        <xsl:output-character  character="&rarr;"     string="&amp;#8594;"  />
        <xsl:output-character  character="&darr;"     string="&amp;#8595;"  />
        <xsl:output-character  character="&harr;"     string="&amp;#8596;"  />
        <xsl:output-character  character="&crarr;"    string="&amp;#8629;"   />
        <xsl:output-character  character="&lArr;"     string="&amp;#8656;"  />
        <xsl:output-character  character="&uArr;"     string="&amp;#8657;"  />
        <xsl:output-character  character="&rArr;"     string="&amp;#8658;"  />
        <xsl:output-character  character="&dArr;"     string="&amp;#8659;"  />
        <xsl:output-character  character="&hArr;"     string="&amp;#8660;"  />
        <xsl:output-character  character="&forall;"   string="&amp;#8704;"    />
        <xsl:output-character  character="&part;"     string="&amp;#8706;"  />
        <xsl:output-character  character="&exist;"    string="&amp;#8707;"   />
        <xsl:output-character  character="&empty;"    string="&amp;#8709;"   />
        <xsl:output-character  character="&nabla;"    string="&amp;#8711;"   />
        <xsl:output-character  character="&isin;"     string="&amp;#8712;"  />
        <xsl:output-character  character="&notin;"    string="&amp;#8713;"   />
        <xsl:output-character  character="&ni;"       string="&amp;#8715;"   />
        <xsl:output-character  character="&prod;"     string="&amp;#8719;"  />
        <xsl:output-character  character="&sum;"      string="&amp;#8721;"    />
        <xsl:output-character  character="&minus;"    string="&amp;#8722;"   />
        <xsl:output-character  character="&lowast;"   string="&amp;#8727;"    />
        <xsl:output-character  character="&radic;"    string="&amp;#8730;"   />
        <xsl:output-character  character="&prop;"     string="&amp;#8733;"  />
        <xsl:output-character  character="&infin;"    string="&amp;#8734;"   />
        <xsl:output-character  character="&ang;"      string="&amp;#8736;"    />
        <xsl:output-character  character="&and;"      string="&amp;#8743;"    />
        <xsl:output-character  character="&or;"       string="&amp;#8744;"   />
        <xsl:output-character  character="&cap;"      string="&amp;#8745;"    />
        <xsl:output-character  character="&cup;"      string="&amp;#8746;"    />
        <xsl:output-character  character="&int;"      string="&amp;#8747;"    />
        <xsl:output-character  character="&there4;"   string="&amp;#8756;"    />
        <xsl:output-character  character="&sim;"      string="&amp;#8764;"    />
        <xsl:output-character  character="&cong;"     string="&amp;#8773;"  />
        <xsl:output-character  character="&asymp;"    string="&amp;#8776;"   />
        <xsl:output-character  character="&ne;"       string="&amp;#8800;"   />
        <xsl:output-character  character="&equiv;"    string="&amp;#8801;"   />
        <xsl:output-character  character="&le;"       string="&amp;#8804;"   />
        <xsl:output-character  character="&ge;"       string="&amp;#8805;"   />
        <xsl:output-character  character="&sub;"      string="&amp;#8834;"    />
        <xsl:output-character  character="&sup;"      string="&amp;#8835;"    />
        <xsl:output-character  character="&nsub;"     string="&amp;#8836;"  />
        <xsl:output-character  character="&sube;"     string="&amp;#8838;"  />
        <xsl:output-character  character="&supe;"     string="&amp;#8839;"  />
        <xsl:output-character  character="&oplus;"    string="&amp;#8853;"   />
        <xsl:output-character  character="&otimes;"   string="&amp;#8855;"    />
        <xsl:output-character  character="&perp;"     string="&amp;#8869;"  />
        <xsl:output-character  character="&sdot;"     string="&amp;#8901;"  />
        <xsl:output-character  character="&lceil;"    string="&amp;#8968;"   />
        <xsl:output-character  character="&rceil;"    string="&amp;#8969;"   />
        <xsl:output-character  character="&lfloor;"   string="&amp;#8970;"    />
        <xsl:output-character  character="&rfloor;"   string="&amp;#8971;"    />
        <xsl:output-character  character="&lang;"     string="&amp;#9001;"  />
        <xsl:output-character  character="&rang;"     string="&amp;#9002;"  />
        <xsl:output-character  character="&loz;"      string="&amp;#9674;"    />
        <xsl:output-character  character="&spades;"   string="&amp;#9824;"    />
        <xsl:output-character  character="&clubs;"    string="&amp;#9827;"   />
        <xsl:output-character  character="&hearts;"   string="&amp;#9829;"    />
        <xsl:output-character  character="&diams;"    string="&amp;#9830;"  />
		<xsl:output-character character="&hyphen;"    string="&amp;#8208;"  />
    </xsl:character-map>
    
	
    <!-- 
        private use area mappings from 
            private static object ConvertEntities(string text)
        
        Note the 'loz' entity range here, and the '-' fallback:
            if (c == 0xf0b7 ||
                c == 0xf0a7 ||
                c == 0xf076 ||
                c == 0xf0d8 ||
                c == 0xf0a8 ||
                c == 0xf0fc ||
                c == 0xf0e0 ||
                c == 0xf0b2)
                return "bull";
            if (c >= 0xf000)
                return "loz";
            if (c >= 128)
            {
                string entity;
                if (EntityMap.TryGetValue(c, out entity))
                    return entity;
            }
            return "-";
    -->
    <xsl:character-map name="private-entity-map">
        <xsl:output-character character="&#xf0b7;" string="&bull;"    />
        <xsl:output-character character="&#xf0a7;" string="&bull;"    />
        <xsl:output-character character="&#xf076;" string="&bull;"    />
        <xsl:output-character character="&#xf0d8;" string="&bull;"    />
        <xsl:output-character character="&#xf0a8;" string="&bull;"    />
        <xsl:output-character character="&#xf0fc;" string="&bull;"    />
        <xsl:output-character character="&#xf0e0;" string="&bull;"    />
        <xsl:output-character character="&#xf0b2;" string="&bull;"    />
        <xsl:output-character character="&#xf000;" string="&loz;"    />        
    </xsl:character-map>

</xsl:stylesheet>
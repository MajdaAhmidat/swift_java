<?xml version="1.0" encoding="UTF-8" ?><Saa:DataPDU xmlns:Saa="urn:swift:saa:xsd:saa.2.0" xmlns:Sw="urn:swift:snl:ns.Sw" xmlns:SwInt="urn:swift:snl:ns.SwInt" xmlns:SwGbl="urn:swift:snl:ns.SwGbl" xmlns:SwSec="urn:swift:snl:ns.SwSec"><Saa:Revision>2.0.15</Saa:Revision><Saa:Header><Saa:TransmissionReport><Saa:SenderReference>TRS000022758236</Saa:SenderReference><Saa:ReconciliationInfo>swi01006-2025-12-12T10:40:45.35123.13047174Z</Saa:ReconciliationInfo><Saa:NetworkDeliveryStatus>NetworkAcked</Saa:NetworkDeliveryStatus><Saa:OriginalInstanceAddressee><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:OriginalInstanceAddressee><Saa:ReportingApplication>SWIFTNetInterface</Saa:ReportingApplication><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000058</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:45.35123.13047174Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.62915012.001546Z</Saa:SNLRef><Saa:Reference>fc3b3208-d746-41f0-8833-91f2d245bc98</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:45</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:Interventions><Saa:Intervention><Saa:IntvCategory>TransmissionReport</Saa:IntvCategory><Saa:CreationTime>20251212104044</Saa:CreationTime><Saa:OperatorOrigin>SYSTEM</Saa:OperatorOrigin><Saa:Contents><AckNack><PseudoAckNack>{1:F21BMCEMAMCAXXX000029000000058}{4:{177:2512121040}{451:0}{311:ACK}}</PseudoAckNack></AckNack></Saa:Contents></Saa:Intervention></Saa:Interventions><Saa:IsRelatedInstanceOriginal>true</Saa:IsRelatedInstanceOriginal><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:IsMessageModified>false</Saa:IsMessageModified><Saa:MessageFields>HeaderAndBody</Saa:MessageFields><Saa:Message><Saa:SenderReference>TRS000022758236</Saa:SenderReference><Saa:MessageIdentifier>pacs.008.001.08</Saa:MessageIdentifier><Saa:Format>MX</Saa:Format><Saa:SubFormat>Input</Saa:SubFormat><Saa:Sender><Saa:DN>ou=xxx,o=bmcemamc,o=swift</Saa:DN><Saa:FullName><Saa:X1>BMCEMAMCXXX</Saa:X1></Saa:FullName></Saa:Sender><Saa:Receiver><Saa:DN>cn=bamrtgs-prod,o=bkammama,o=swift</Saa:DN><Saa:FullName><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:FullName></Saa:Receiver><Saa:InterfaceInfo><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:MessageContext>Report</Saa:MessageContext><Saa:MessageNature>Financial</Saa:MessageNature><Saa:Sumid>16C40DE0FEE6002D</Saa:Sumid></Saa:InterfaceInfo><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000058</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:45.35123.13047174Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.62915012.001546Z</Saa:SNLRef><Saa:Reference>fc3b3208-d746-41f0-8833-91f2d245bc98</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:45</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:SecurityInfo><Saa:SWIFTNetSecurityInfo><Saa:SignerDN>cn=so1,o=bmcemamc,o=swift</Saa:SignerDN><Saa:SignatureValue><SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>pCFqVpzvnJOGm4dtSF1aTzERls3xMbOEmpJhyHjZpFw=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY
Content-Domain: RFC822
EntrustFile-Version: 2.0
Originator-DN: cn=so1,o=bmcemamc,o=swift
Orig-SN: 1746972102
MIC-Info: SHA256, RSA,
 hlFv7jXDNOW5gpnzSIZ58wf3I7boJJcEn9LjKWQOyWhrPbZAdU9DYPZ+3s8HO3Qm
 q5ZIeuQQOZseossnf76yRDmWAaAfzEvzmKaErZyd8Cckx2O8sWJLvtzjE/BtAgGy
 z21ypEfx6zBzfCq3HzGQ0in3J2LG11zG2aI2gis5HTwvO0r02CiB4uyaskQ+bq+K
 e/+/AM8K/5tujE6BNPRy/PlEKUANU/qj4ppgb0lEV5bORRjyIxAKJWCmMo3Be5ss
 NhZWC/oeylwxHV0iRyaCB+U4dfB3pWVYYVZTzAgXQgpGDtrQhtPXH9pQ2XQdIRVZ
 zTynhXAcwPD/0BnGckhpAw==
</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=so1,o=bmcemamc,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.2</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>Sw.RequestHeader</Sw:DigestRef><Sw:DigestValue>+sUN1ietY2Wb+rGvg6TM5olOvusRTlnbP3kO3GkfIqM=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.RequestPayload</Sw:DigestRef><Sw:DigestValue>A9kUojkm0ZvBJJY1E+k5XIsgDQdAR2LDNZwCF8mx/kI=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.E2S</Sw:DigestRef><Sw:DigestValue>j99VOAwTD1cNKmPBw3G30lTc/WJ4i+2Vvz/HvSx4FGs=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature></Saa:SignatureValue></Saa:SWIFTNetSecurityInfo></Saa:SecurityInfo><Saa:ExpiryDateTime>20260101104032</Saa:ExpiryDateTime></Saa:Message></Saa:TransmissionReport></Saa:Header><Saa:Body>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.02">
    <Fr>
        <FIId>
            <FinInstnId>
                <BICFI>BMCEMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </Fr>
    <To>
        <FIId>
            <FinInstnId>
                <BICFI>CNCAMAMR</BICFI>
            </FinInstnId>
        </FIId>
    </To>
    <BizMsgIdr>TSTNOTIFSAA01</BizMsgIdr>
    <MsgDefIdr>pacs.008.001.08</MsgDefIdr>
    <BizSvc>swift.iap.tia.02</BizSvc>
    <CreDt>2025-12-12T11:20:52+01:00</CreDt>
    <Prty>0020</Prty>
</AppHdr>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08">
    <FIToFICstmrCdtTrf>
        <GrpHdr>
            <MsgId>TSTNOTIFSAA01</MsgId>
            <CreDtTm>2025-12-12T11:20:52+01:00</CreDtTm>
            <NbOfTxs>1</NbOfTxs>
            <SttlmInf>
                <SttlmMtd>CLRG</SttlmMtd>
                <ClrSys>
                    <Cd>RTG</Cd>
                </ClrSys>
            </SttlmInf>
        </GrpHdr>
        <CdtTrfTxInf>
            <PmtId>
                <InstrId>TSTNOTIFSAA01</InstrId>
                <EndToEndId>NOT PROVIDED</EndToEndId>
                <TxId>TSTNOTIFSAA01</TxId>
                <UETR>ba71b952-c419-4168-9304-b74634fe5d88</UETR>
            </PmtId>
            <PmtTpInf>
                <LclInstrm>
                    <Prtry>RTGS-SCCT</Prtry>
                </LclInstrm>
            </PmtTpInf>
            <IntrBkSttlmAmt Ccy="MAD">10000000</IntrBkSttlmAmt>
            <IntrBkSttlmDt>2025-12-12</IntrBkSttlmDt>
            <SttlmPrty>URGT</SttlmPrty>
            <InstdAmt Ccy="MAD">10000000</InstdAmt>
            <ChrgBr>SHAR</ChrgBr>
            <InstgAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </InstgAgt>
            <InstdAgt>
                <FinInstnId>
                    <BICFI>CNCAMAMR</BICFI>
                </FinInstnId>
            </InstdAgt>
            <Dbtr>
                <Nm>STE ROYALE D ENCOUR GEMENT</Nm>
            </Dbtr>
            <DbtrAcct>
                <Id>
                    <Othr>
                        <Id>011810000001210000766581</Id>
                    </Othr>
                </Id>
            </DbtrAcct>
            <DbtrAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </DbtrAgt>
            <CdtrAgt>
                <FinInstnId>
                    <BICFI>CNCAMAMR</BICFI>
                </FinInstnId>
            </CdtrAgt>
            <Cdtr>
                <Nm>SOREC</Nm>
            </Cdtr>
            <CdtrAcct>
                <Id>
                    <Othr>
                        <Id>225810010007251651011065</Id>
                    </Othr>
                </Id>
            </CdtrAcct>
            <InstrForNxtAgt>
                <InstrInf>SRBM SOREC BMCE</InstrInf>
            </InstrForNxtAgt>
            <Purp>
                <Prtry>001</Prtry>
            </Purp>
            <RmtInf>
                <Ustrd>SRBM SOREC BMCE</Ustrd>
            </RmtInf>
        </CdtTrfTxInf>
    </FIToFICstmrCdtTrf>
</Document>
</Saa:Body></Saa:DataPDU><?xml version="1.0" encoding="UTF-8" ?><Saa:DataPDU xmlns:Saa="urn:swift:saa:xsd:saa.2.0" xmlns:Sw="urn:swift:snl:ns.Sw" xmlns:SwInt="urn:swift:snl:ns.SwInt" xmlns:SwGbl="urn:swift:snl:ns.SwGbl" xmlns:SwSec="urn:swift:snl:ns.SwSec"><Saa:Revision>2.0.15</Saa:Revision><Saa:Header><Saa:TransmissionReport><Saa:SenderReference>TRS000022758306</Saa:SenderReference><Saa:ReconciliationInfo>swi01006-2025-12-12T10:40:45.32744.12913266Z</Saa:ReconciliationInfo><Saa:NetworkDeliveryStatus>NetworkAcked</Saa:NetworkDeliveryStatus><Saa:OriginalInstanceAddressee><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:OriginalInstanceAddressee><Saa:ReportingApplication>SWIFTNetInterface</Saa:ReportingApplication><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000059</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:45.32744.12913266Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.16318780.001422Z</Saa:SNLRef><Saa:Reference>fcab3fa8-d746-41f0-8833-88c58fd9f9bf</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:45</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:Interventions><Saa:Intervention><Saa:IntvCategory>TransmissionReport</Saa:IntvCategory><Saa:CreationTime>20251212104044</Saa:CreationTime><Saa:OperatorOrigin>SYSTEM</Saa:OperatorOrigin><Saa:Contents><AckNack><PseudoAckNack>{1:F21BMCEMAMCAXXX000029000000059}{4:{177:2512121040}{451:0}{311:ACK}}</PseudoAckNack></AckNack></Saa:Contents></Saa:Intervention></Saa:Interventions><Saa:IsRelatedInstanceOriginal>true</Saa:IsRelatedInstanceOriginal><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:IsMessageModified>false</Saa:IsMessageModified><Saa:MessageFields>HeaderAndBody</Saa:MessageFields><Saa:Message><Saa:SenderReference>TRS000022758306</Saa:SenderReference><Saa:MessageIdentifier>pacs.008.001.08</Saa:MessageIdentifier><Saa:Format>MX</Saa:Format><Saa:SubFormat>Input</Saa:SubFormat><Saa:Sender><Saa:DN>ou=xxx,o=bmcemamc,o=swift</Saa:DN><Saa:FullName><Saa:X1>BMCEMAMCXXX</Saa:X1></Saa:FullName></Saa:Sender><Saa:Receiver><Saa:DN>cn=bamrtgs-prod,o=bkammama,o=swift</Saa:DN><Saa:FullName><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:FullName></Saa:Receiver><Saa:InterfaceInfo><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:MessageContext>Report</Saa:MessageContext><Saa:MessageNature>Financial</Saa:MessageNature><Saa:Sumid>16C40DDFFEE6002C</Saa:Sumid></Saa:InterfaceInfo><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000059</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:45.32744.12913266Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.16318780.001422Z</Saa:SNLRef><Saa:Reference>fcab3fa8-d746-41f0-8833-88c58fd9f9bf</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:45</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:SecurityInfo><Saa:SWIFTNetSecurityInfo><Saa:SignerDN>cn=so1,o=bmcemamc,o=swift</Saa:SignerDN><Saa:SignatureValue><SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>RA5QatIkiHXgiGlx48/ouqD9wFXWpSknHcfitdoVRj4=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY
Content-Domain: RFC822
EntrustFile-Version: 2.0
Originator-DN: cn=so1,o=bmcemamc,o=swift
Orig-SN: 1746972102
MIC-Info: SHA256, RSA,
 cw7NV4esrJVIpIlkU3icbeJu8LM94XNLs6INcMDSqvRJ7hmDN+a9tldVnFo1gBu5
 SRqd7VSUXDASx5bgyIh2/RJUtuwn1guoULfNyKek9MCiA34Sgf+eVP9kJ0Gr/fBq
 afABhY4wvwUqszetkKif4DoH4XIyaq5iRZ7M8G/FueIAD7rhYuPurqB93CXEoRMx
 zZ2hwckpY72pRsYhbHT3XGmkqSXJMpkRESThvM7zfI5TVXwggEcvbC7jsQC0aPef
 dljSkApkEP34MdoBl+zbRX9C08bxUOWmqQvMuKmCBKKDaRWlIwyB6oJS1NP3BMAT
 6a8wb7clzyfBU6uRbjlR9g==
</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=so1,o=bmcemamc,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.2</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>Sw.RequestHeader</Sw:DigestRef><Sw:DigestValue>+sUN1ietY2Wb+rGvg6TM5olOvusRTlnbP3kO3GkfIqM=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.RequestPayload</Sw:DigestRef><Sw:DigestValue>igaWqWBypxgif14d8MxMGUfQMzrPFpT1v5QMWWGDvaU=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.E2S</Sw:DigestRef><Sw:DigestValue>cB+m3iAsUzBHDrhJLdCgJhueeMI8YR8IV1zqicESqTI=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature></Saa:SignatureValue></Saa:SWIFTNetSecurityInfo></Saa:SecurityInfo><Saa:ExpiryDateTime>20260101104033</Saa:ExpiryDateTime></Saa:Message></Saa:TransmissionReport></Saa:Header><Saa:Body>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.02">
    <Fr>
        <FIId>
            <FinInstnId>
                <BICFI>BMCEMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </Fr>
    <To>
        <FIId>
            <FinInstnId>
                <BICFI>CITIMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </To>
    <BizMsgIdr>TRS000022758306</BizMsgIdr>
    <MsgDefIdr>pacs.008.001.08</MsgDefIdr>
    <BizSvc>swift.iap.tia.02</BizSvc>
    <CreDt>2025-12-12T11:35:31+01:00</CreDt>
    <Prty>0020</Prty>
</AppHdr>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08">
    <FIToFICstmrCdtTrf>
        <GrpHdr>
            <MsgId>TRS000022758306</MsgId>
            <CreDtTm>2025-12-12T11:35:31+01:00</CreDtTm>
            <NbOfTxs>1</NbOfTxs>
            <SttlmInf>
                <SttlmMtd>CLRG</SttlmMtd>
                <ClrSys>
                    <Cd>RTG</Cd>
                </ClrSys>
            </SttlmInf>
        </GrpHdr>
        <CdtTrfTxInf>
            <PmtId>
                <InstrId>TRS000022758306</InstrId>
                <EndToEndId>NOT PROVIDED</EndToEndId>
                <TxId>TRS000022758306</TxId>
                <UETR>08ff3e25-96c1-4ea0-9a54-b1877e7756c3</UETR>
            </PmtId>
            <PmtTpInf>
                <LclInstrm>
                    <Prtry>RTGS-SCCT</Prtry>
                </LclInstrm>
            </PmtTpInf>
            <IntrBkSttlmAmt Ccy="MAD">167787.67</IntrBkSttlmAmt>
            <IntrBkSttlmDt>2025-12-12</IntrBkSttlmDt>
            <SttlmPrty>URGT</SttlmPrty>
            <InstdAmt Ccy="MAD">167787.67</InstdAmt>
            <ChrgBr>SHAR</ChrgBr>
            <InstgAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </InstgAgt>
            <InstdAgt>
                <FinInstnId>
                    <BICFI>CITIMAMC</BICFI>
                </FinInstnId>
            </InstdAgt>
            <Dbtr>
                <Nm>ESPACE TOURISME</Nm>
            </Dbtr>
            <DbtrAcct>
                <Id>
                    <Othr>
                        <Id>011780000054210006036805</Id>
                    </Othr>
                </Id>
            </DbtrAcct>
            <DbtrAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </DbtrAgt>
            <CdtrAgt>
                <FinInstnId>
                    <BICFI>CITIMAMC</BICFI>
                </FinInstnId>
            </CdtrAgt>
            <Cdtr>
                <Nm>bsp maroc</Nm>
            </Cdtr>
            <CdtrAcct>
                <Id>
                    <Othr>
                        <Id>028780421262010017856705</Id>
                    </Othr>
                </Id>
            </CdtrAcct>
            <InstrForNxtAgt>
                <InstrInf>vrt vrt</InstrInf>
            </InstrForNxtAgt>
            <Purp>
                <Prtry>001</Prtry>
            </Purp>
            <RmtInf>
                <Ustrd>vrt vrt</Ustrd>
            </RmtInf>
        </CdtTrfTxInf>
    </FIToFICstmrCdtTrf>
</Document>
</Saa:Body></Saa:DataPDU><?xml version="1.0" encoding="UTF-8" ?><Saa:DataPDU xmlns:Saa="urn:swift:saa:xsd:saa.2.0" xmlns:Sw="urn:swift:snl:ns.Sw" xmlns:SwInt="urn:swift:snl:ns.SwInt" xmlns:SwGbl="urn:swift:snl:ns.SwGbl" xmlns:SwSec="urn:swift:snl:ns.SwSec"><Saa:Revision>2.0.15</Saa:Revision><Saa:Header><Saa:TransmissionReport><Saa:SenderReference>TRS000022758360</Saa:SenderReference><Saa:ReconciliationInfo>swi01006-2025-12-12T10:40:46.35979.5586511Z</Saa:ReconciliationInfo><Saa:NetworkDeliveryStatus>NetworkAcked</Saa:NetworkDeliveryStatus><Saa:OriginalInstanceAddressee><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:OriginalInstanceAddressee><Saa:ReportingApplication>SWIFTNetInterface</Saa:ReportingApplication><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000061</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:46.35979.5586511Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.62915012.001548Z</Saa:SNLRef><Saa:Reference>fd8bf3fe-d746-41f0-8833-0b6c5f0d0061</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:46</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:Interventions><Saa:Intervention><Saa:IntvCategory>TransmissionReport</Saa:IntvCategory><Saa:CreationTime>20251212104044</Saa:CreationTime><Saa:OperatorOrigin>SYSTEM</Saa:OperatorOrigin><Saa:Contents><AckNack><PseudoAckNack>{1:F21BMCEMAMCAXXX000029000000061}{4:{177:2512121040}{451:0}{311:ACK}}</PseudoAckNack></AckNack></Saa:Contents></Saa:Intervention></Saa:Interventions><Saa:IsRelatedInstanceOriginal>true</Saa:IsRelatedInstanceOriginal><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:IsMessageModified>false</Saa:IsMessageModified><Saa:MessageFields>HeaderAndBody</Saa:MessageFields><Saa:Message><Saa:SenderReference>TRS000022758360</Saa:SenderReference><Saa:MessageIdentifier>pacs.008.001.08</Saa:MessageIdentifier><Saa:Format>MX</Saa:Format><Saa:SubFormat>Input</Saa:SubFormat><Saa:Sender><Saa:DN>ou=xxx,o=bmcemamc,o=swift</Saa:DN><Saa:FullName><Saa:X1>BMCEMAMCXXX</Saa:X1></Saa:FullName></Saa:Sender><Saa:Receiver><Saa:DN>cn=bamrtgs-prod,o=bkammama,o=swift</Saa:DN><Saa:FullName><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:FullName></Saa:Receiver><Saa:InterfaceInfo><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:MessageContext>Report</Saa:MessageContext><Saa:MessageNature>Financial</Saa:MessageNature><Saa:Sumid>16C40DDEFEE60029</Saa:Sumid></Saa:InterfaceInfo><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000061</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:46.35979.5586511Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.62915012.001548Z</Saa:SNLRef><Saa:Reference>fd8bf3fe-d746-41f0-8833-0b6c5f0d0061</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:46</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:SecurityInfo><Saa:SWIFTNetSecurityInfo><Saa:SignerDN>cn=so1,o=bmcemamc,o=swift</Saa:SignerDN><Saa:SignatureValue><SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>cbPnhkz/toQUK0Hb5W9og0xsuYnaFueO114dP5CHq3A=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY
Content-Domain: RFC822
EntrustFile-Version: 2.0
Originator-DN: cn=so1,o=bmcemamc,o=swift
Orig-SN: 1746972102
MIC-Info: SHA256, RSA,
 IXUkS6VEm59QhjwstXD/AzmXScFlL0G9rDrbeEndjVcj6pNmj9bu/q/JUt9bxFTu
 pKh2iG9iVgYTmL4PHcjCGL9W9ZYir5ff0sreMGGrp7w0u0wVYYq9kxTeoqzLXV8a
 4sHkFqrtoOvrY8vGJ/pjWrgvz3u7fASQUeoQb+Y+Ybf9oNlSHbMaetV/xec7uYsd
 YXH65jIrtS7IXzvJVdbNh0jXl5gAE5a0PsfknSpv8yxlN78S0RmGm0KNSzjzxdp1
 nzo1uhycg+R+yW0Xm1yJCNEEMB1KHaqoUzf6tDDjBwLEAZANoHEbTzJuDow5sCK+
 NqpHCZ5O8ziVD+LHvtEk2w==
</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=so1,o=bmcemamc,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.2</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>Sw.RequestHeader</Sw:DigestRef><Sw:DigestValue>+sUN1ietY2Wb+rGvg6TM5olOvusRTlnbP3kO3GkfIqM=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.RequestPayload</Sw:DigestRef><Sw:DigestValue>8vMj3ujeFBLR23saAQTvw64ShCEdfu0xwEXJPOilAro=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.E2S</Sw:DigestRef><Sw:DigestValue>c3fRCq/r9NNnbhzYG/TMfqmwhOJ4o+Ls3xSLtjABT1U=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature></Saa:SignatureValue></Saa:SWIFTNetSecurityInfo></Saa:SecurityInfo><Saa:ExpiryDateTime>20260101104034</Saa:ExpiryDateTime></Saa:Message></Saa:TransmissionReport></Saa:Header><Saa:Body>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.02">
    <Fr>
        <FIId>
            <FinInstnId>
                <BICFI>BMCEMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </Fr>
    <To>
        <FIId>
            <FinInstnId>
                <BICFI>BCMAMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </To>
    <BizMsgIdr>TRS000022758360</BizMsgIdr>
    <MsgDefIdr>pacs.008.001.08</MsgDefIdr>
    <BizSvc>swift.iap.tia.02</BizSvc>
    <CreDt>2025-12-12T11:35:41+01:00</CreDt>
    <Prty>0020</Prty>
</AppHdr>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08">
    <FIToFICstmrCdtTrf>
        <GrpHdr>
            <MsgId>TRS000022758360</MsgId>
            <CreDtTm>2025-12-12T11:35:41+01:00</CreDtTm>
            <NbOfTxs>1</NbOfTxs>
            <SttlmInf>
                <SttlmMtd>CLRG</SttlmMtd>
                <ClrSys>
                    <Cd>RTG</Cd>
                </ClrSys>
            </SttlmInf>
        </GrpHdr>
        <CdtTrfTxInf>
            <PmtId>
                <InstrId>TRS000022758360</InstrId>
                <EndToEndId>NOT PROVIDED</EndToEndId>
                <TxId>TRS000022758360</TxId>
                <UETR>b271d216-c6ae-4cea-a0f7-2781aeac75ce</UETR>
            </PmtId>
            <PmtTpInf>
                <LclInstrm>
                    <Prtry>RTGS-SCCT</Prtry>
                </LclInstrm>
            </PmtTpInf>
            <IntrBkSttlmAmt Ccy="MAD">180000</IntrBkSttlmAmt>
            <IntrBkSttlmDt>2025-12-12</IntrBkSttlmDt>
            <SttlmPrty>URGT</SttlmPrty>
            <InstdAmt Ccy="MAD">180000</InstdAmt>
            <ChrgBr>SHAR</ChrgBr>
            <InstgAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </InstgAgt>
            <InstdAgt>
                <FinInstnId>
                    <BICFI>BCMAMAMC</BICFI>
                </FinInstnId>
            </InstdAgt>
            <Dbtr>
                <Nm>EXPERT RETAIL JOBS OUTSOURCING</Nm>
            </Dbtr>
            <DbtrAcct>
                <Id>
                    <Othr>
                        <Id>011793000025210000101765</Id>
                    </Othr>
                </Id>
            </DbtrAcct>
            <DbtrAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </DbtrAgt>
            <CdtrAgt>
                <FinInstnId>
                    <BICFI>BCMAMAMC</BICFI>
                </FinInstnId>
            </CdtrAgt>
            <Cdtr>
                <Nm>EXPERT RETAIL JOBS outsourcing</Nm>
            </Cdtr>
            <CdtrAcct>
                <Id>
                    <Othr>
                        <Id>007780000362500000150039</Id>
                    </Othr>
                </Id>
            </CdtrAcct>
            <InstrForNxtAgt>
                <InstrInf>vrt erj group</InstrInf>
            </InstrForNxtAgt>
            <Purp>
                <Prtry>001</Prtry>
            </Purp>
            <RmtInf>
                <Ustrd>vrt erj group</Ustrd>
            </RmtInf>
        </CdtTrfTxInf>
    </FIToFICstmrCdtTrf>
</Document>
</Saa:Body></Saa:DataPDU><?xml version="1.0" encoding="UTF-8" ?><Saa:DataPDU xmlns:Saa="urn:swift:saa:xsd:saa.2.0" xmlns:Sw="urn:swift:snl:ns.Sw" xmlns:SwInt="urn:swift:snl:ns.SwInt" xmlns:SwGbl="urn:swift:snl:ns.SwGbl" xmlns:SwSec="urn:swift:snl:ns.SwSec"><Saa:Revision>2.0.15</Saa:Revision><Saa:Header><Saa:TransmissionReport><Saa:SenderReference>TRS000022758383</Saa:SenderReference><Saa:ReconciliationInfo>swi01006-2025-12-12T10:40:46.35891.12211849Z</Saa:ReconciliationInfo><Saa:NetworkDeliveryStatus>NetworkAcked</Saa:NetworkDeliveryStatus><Saa:OriginalInstanceAddressee><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:OriginalInstanceAddressee><Saa:ReportingApplication>SWIFTNetInterface</Saa:ReportingApplication><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000062</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:46.35891.12211849Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.16318780.001424Z</Saa:SNLRef><Saa:Reference>fdf6e740-d746-41f0-8833-b62c521273d1</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:46</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:Interventions><Saa:Intervention><Saa:IntvCategory>TransmissionReport</Saa:IntvCategory><Saa:CreationTime>20251212104044</Saa:CreationTime><Saa:OperatorOrigin>SYSTEM</Saa:OperatorOrigin><Saa:Contents><AckNack><PseudoAckNack>{1:F21BMCEMAMCAXXX000029000000062}{4:{177:2512121040}{451:0}{311:ACK}}</PseudoAckNack></AckNack></Saa:Contents></Saa:Intervention></Saa:Interventions><Saa:IsRelatedInstanceOriginal>true</Saa:IsRelatedInstanceOriginal><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:IsMessageModified>false</Saa:IsMessageModified><Saa:MessageFields>HeaderAndBody</Saa:MessageFields><Saa:Message><Saa:SenderReference>TRS000022758383</Saa:SenderReference><Saa:MessageIdentifier>pacs.008.001.08</Saa:MessageIdentifier><Saa:Format>MX</Saa:Format><Saa:SubFormat>Input</Saa:SubFormat><Saa:Sender><Saa:DN>ou=xxx,o=bmcemamc,o=swift</Saa:DN><Saa:FullName><Saa:X1>BMCEMAMCXXX</Saa:X1></Saa:FullName></Saa:Sender><Saa:Receiver><Saa:DN>cn=bamrtgs-prod,o=bkammama,o=swift</Saa:DN><Saa:FullName><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:FullName></Saa:Receiver><Saa:InterfaceInfo><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:MessageContext>Report</Saa:MessageContext><Saa:MessageNature>Financial</Saa:MessageNature><Saa:Sumid>16C40DDDFEE60028</Saa:Sumid></Saa:InterfaceInfo><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000062</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:46.35891.12211849Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.16318780.001424Z</Saa:SNLRef><Saa:Reference>fdf6e740-d746-41f0-8833-b62c521273d1</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:46</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:SecurityInfo><Saa:SWIFTNetSecurityInfo><Saa:SignerDN>cn=so1,o=bmcemamc,o=swift</Saa:SignerDN><Saa:SignatureValue><SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>/4kr/E77mC/JE/AgCKsH40YhqMDxSi3Zzh78aSlwR8g=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY
Content-Domain: RFC822
EntrustFile-Version: 2.0
Originator-DN: cn=so1,o=bmcemamc,o=swift
Orig-SN: 1746972102
MIC-Info: SHA256, RSA,
 4pIuL3jvErN+NnrLBTkiB7t5dZAt4ffG0SCF9Hj5t4HtTZONbfh2UIlVSoqaDORP
 YeMWu2WanBrdqsNtrsMGmh5rjdZnmvGnrhW1VUAaLhZgO/NlXrwnyaxTtXDEMZgH
 0YcDi9W8XMlkRex/Hyf0b5vN2PDULjisgh86OWhUmlDxWjPOfUd3o/0/w8aWKvqs
 XfuRwq5fSZF/o+0L2UbGu5gHijvBTDpfU5g3k30vZE3mhBjPGR91zA3aY82AJmDS
 w6lhSbmgtTZq/T7r5J22Bw2fGV+TodcIxhQKpZIUM9Hx4Mtj4zIQ1g0hZcmutBh+
 h0gBbaID4xLgk/IiOySBZA==
</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=so1,o=bmcemamc,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.2</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>Sw.RequestHeader</Sw:DigestRef><Sw:DigestValue>+sUN1ietY2Wb+rGvg6TM5olOvusRTlnbP3kO3GkfIqM=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.RequestPayload</Sw:DigestRef><Sw:DigestValue>eZovLp2rm8x6YVEFHBJSnzWEpZ10nVC9QzK1buu77sc=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.E2S</Sw:DigestRef><Sw:DigestValue>tvyF0rxwPi/pl57+1ddWi24pcKb+nGlAUK94TkLhwYg=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature></Saa:SignatureValue></Saa:SWIFTNetSecurityInfo></Saa:SecurityInfo><Saa:ExpiryDateTime>20260101104035</Saa:ExpiryDateTime></Saa:Message></Saa:TransmissionReport></Saa:Header><Saa:Body>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.02">
    <Fr>
        <FIId>
            <FinInstnId>
                <BICFI>BMCEMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </Fr>
    <To>
        <FIId>
            <FinInstnId>
                <BICFI>BCMAMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </To>
    <BizMsgIdr>TRS000022758383</BizMsgIdr>
    <MsgDefIdr>pacs.008.001.08</MsgDefIdr>
    <BizSvc>swift.iap.tia.02</BizSvc>
    <CreDt>2025-12-12T11:30:18+01:00</CreDt>
    <Prty>0020</Prty>
</AppHdr>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08">
    <FIToFICstmrCdtTrf>
        <GrpHdr>
            <MsgId>TRS000022758383</MsgId>
            <CreDtTm>2025-12-12T11:30:18+01:00</CreDtTm>
            <NbOfTxs>1</NbOfTxs>
            <SttlmInf>
                <SttlmMtd>CLRG</SttlmMtd>
                <ClrSys>
                    <Cd>RTG</Cd>
                </ClrSys>
            </SttlmInf>
        </GrpHdr>
        <CdtTrfTxInf>
            <PmtId>
                <InstrId>TRS000022758383</InstrId>
                <EndToEndId>NOT PROVIDED</EndToEndId>
                <TxId>TRS000022758383</TxId>
                <UETR>7b292391-b1c0-4ee8-b0f9-017d6e9b4890</UETR>
            </PmtId>
            <PmtTpInf>
                <LclInstrm>
                    <Prtry>RTGS-SCCT</Prtry>
                </LclInstrm>
            </PmtTpInf>
            <IntrBkSttlmAmt Ccy="MAD">180000</IntrBkSttlmAmt>
            <IntrBkSttlmDt>2025-12-12</IntrBkSttlmDt>
            <SttlmPrty>URGT</SttlmPrty>
            <InstdAmt Ccy="MAD">180000</InstdAmt>
            <ChrgBr>SHAR</ChrgBr>
            <InstgAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </InstgAgt>
            <InstdAgt>
                <FinInstnId>
                    <BICFI>BCMAMAMC</BICFI>
                </FinInstnId>
            </InstdAgt>
            <Dbtr>
                <Nm>LE SALON D AMENAGEMENT</Nm>
            </Dbtr>
            <DbtrAcct>
                <Id>
                    <Othr>
                        <Id>011780000073210006256045</Id>
                    </Othr>
                </Id>
            </DbtrAcct>
            <DbtrAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </DbtrAgt>
            <CdtrAgt>
                <FinInstnId>
                    <BICFI>BCMAMAMC</BICFI>
                </FinInstnId>
            </CdtrAgt>
            <Cdtr>
                <Nm>MAZIPROM</Nm>
            </Cdtr>
            <CdtrAcct>
                <Id>
                    <Othr>
                        <Id>007780000118200000062441</Id>
                    </Othr>
                </Id>
            </CdtrAcct>
            <InstrForNxtAgt>
                <InstrInf>VIRT LE SALON D AMENAGEME</InstrInf>
            </InstrForNxtAgt>
            <Purp>
                <Prtry>001</Prtry>
            </Purp>
            <RmtInf>
                <Ustrd>VIRT LE SALON D AMENAGEME</Ustrd>
            </RmtInf>
        </CdtTrfTxInf>
    </FIToFICstmrCdtTrf>
</Document>
</Saa:Body></Saa:DataPDU><?xml version="1.0" encoding="UTF-8" ?><Saa:DataPDU xmlns:Saa="urn:swift:saa:xsd:saa.2.0" xmlns:Sw="urn:swift:snl:ns.Sw" xmlns:SwInt="urn:swift:snl:ns.SwInt" xmlns:SwGbl="urn:swift:snl:ns.SwGbl" xmlns:SwSec="urn:swift:snl:ns.SwSec"><Saa:Revision>2.0.15</Saa:Revision><Saa:Header><Saa:TransmissionReport><Saa:SenderReference>TRS000022758220</Saa:SenderReference><Saa:ReconciliationInfo>swi01006-2025-12-12T10:40:47.35759.5900488Z</Saa:ReconciliationInfo><Saa:NetworkDeliveryStatus>NetworkAcked</Saa:NetworkDeliveryStatus><Saa:OriginalInstanceAddressee><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:OriginalInstanceAddressee><Saa:ReportingApplication>SWIFTNetInterface</Saa:ReportingApplication><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000057</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:47.35759.5900488Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.52691266.001466Z</Saa:SNLRef><Saa:Reference>fbb3034c-d746-41f0-8833-cd63d27d4c92</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:47</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:Interventions><Saa:Intervention><Saa:IntvCategory>TransmissionReport</Saa:IntvCategory><Saa:CreationTime>20251212104044</Saa:CreationTime><Saa:OperatorOrigin>SYSTEM</Saa:OperatorOrigin><Saa:Contents><AckNack><PseudoAckNack>{1:F21BMCEMAMCAXXX000029000000057}{4:{177:2512121040}{451:0}{311:ACK}}</PseudoAckNack></AckNack></Saa:Contents></Saa:Intervention></Saa:Interventions><Saa:IsRelatedInstanceOriginal>true</Saa:IsRelatedInstanceOriginal><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:IsMessageModified>false</Saa:IsMessageModified><Saa:MessageFields>HeaderAndBody</Saa:MessageFields><Saa:Message><Saa:SenderReference>TRS000022758220</Saa:SenderReference><Saa:MessageIdentifier>pacs.008.001.08</Saa:MessageIdentifier><Saa:Format>MX</Saa:Format><Saa:SubFormat>Input</Saa:SubFormat><Saa:Sender><Saa:DN>ou=xxx,o=bmcemamc,o=swift</Saa:DN><Saa:FullName><Saa:X1>BMCEMAMCXXX</Saa:X1></Saa:FullName></Saa:Sender><Saa:Receiver><Saa:DN>cn=bamrtgs-prod,o=bkammama,o=swift</Saa:DN><Saa:FullName><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:FullName></Saa:Receiver><Saa:InterfaceInfo><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:MessageContext>Report</Saa:MessageContext><Saa:MessageNature>Financial</Saa:MessageNature><Saa:Sumid>16C40DE1FEE6002E</Saa:Sumid></Saa:InterfaceInfo><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000057</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:47.35759.5900488Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:44.52691266.001466Z</Saa:SNLRef><Saa:Reference>fbb3034c-d746-41f0-8833-cd63d27d4c92</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:47</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:SecurityInfo><Saa:SWIFTNetSecurityInfo><Saa:SignerDN>cn=so1,o=bmcemamc,o=swift</Saa:SignerDN><Saa:SignatureValue><SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>LH9oTHyScyGwaY0VGCbMj/b4L7GEGoBGcxrC35mycKo=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY
Content-Domain: RFC822
EntrustFile-Version: 2.0
Originator-DN: cn=so1,o=bmcemamc,o=swift
Orig-SN: 1746972102
MIC-Info: SHA256, RSA,
 IY1GlUHMF8elTMu5Fk81vKH10GOiISuODnU8akcnfx+QxgRJQRuLU86HiAVOKpt8
 jDIwQdztn9RPmMe6CEG2NjPXNO3JRVWW6J/q+LZMRBxCQNXUO1hqp+CiRd5I1WBd
 ud2y3GIljZ89S+WZNJf1zPQ3BIO2vg4GXGaqylokIgesjsbHana/CRE3CUEmA7jT
 lQyiedY6mXxHrVCAKbWTZTT8d8IfBGAtX++j24OeeVOIhcJETYWyxbCAdSR+pEOg
 g9hr0XzDo7Q4V+PUOZlA4IxQuptnlbTJHQLZ4910jW+BfZXOITkHJ0EbW9CCZc1k
 cRGRcOW4Fz4rGYATHi880A==
</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=so1,o=bmcemamc,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.2</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>Sw.RequestHeader</Sw:DigestRef><Sw:DigestValue>+sUN1ietY2Wb+rGvg6TM5olOvusRTlnbP3kO3GkfIqM=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.RequestPayload</Sw:DigestRef><Sw:DigestValue>ObAfwa3KL1ZAP9TIiEzcFnlsxj0qCL41SOKVduCvQfc=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.E2S</Sw:DigestRef><Sw:DigestValue>rnP0CrrHgXR0SfAuJsLE0WQKuQsx/g7FPGPK2U5gFWI=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature></Saa:SignatureValue></Saa:SWIFTNetSecurityInfo></Saa:SecurityInfo><Saa:ExpiryDateTime>20260101104031</Saa:ExpiryDateTime></Saa:Message></Saa:TransmissionReport></Saa:Header><Saa:Body>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.02">
    <Fr>
        <FIId>
            <FinInstnId>
                <BICFI>BMCEMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </Fr>
    <To>
        <FIId>
            <FinInstnId>
                <BICFI>BCMAMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </To>
    <BizMsgIdr>TRS000022758220</BizMsgIdr>
    <MsgDefIdr>pacs.008.001.08</MsgDefIdr>
    <BizSvc>swift.iap.tia.02</BizSvc>
    <CreDt>2025-12-12T11:20:52+01:00</CreDt>
    <Prty>0020</Prty>
</AppHdr>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08">
    <FIToFICstmrCdtTrf>
        <GrpHdr>
            <MsgId>TRS000022758220</MsgId>
            <CreDtTm>2025-12-12T11:20:52+01:00</CreDtTm>
            <NbOfTxs>1</NbOfTxs>
            <SttlmInf>
                <SttlmMtd>CLRG</SttlmMtd>
                <ClrSys>
                    <Cd>RTG</Cd>
                </ClrSys>
            </SttlmInf>
        </GrpHdr>
        <CdtTrfTxInf>
            <PmtId>
                <InstrId>TRS000022758220</InstrId>
                <EndToEndId>NOT PROVIDED</EndToEndId>
                <TxId>TRS000022758220</TxId>
                <UETR>6c6793a9-05a5-477f-bc5e-aee77cc5b923</UETR>
            </PmtId>
            <PmtTpInf>
                <LclInstrm>
                    <Prtry>RTGS-SCCT</Prtry>
                </LclInstrm>
            </PmtTpInf>
            <IntrBkSttlmAmt Ccy="MAD">9000000</IntrBkSttlmAmt>
            <IntrBkSttlmDt>2025-12-12</IntrBkSttlmDt>
            <SttlmPrty>URGT</SttlmPrty>
            <InstdAmt Ccy="MAD">9000000</InstdAmt>
            <ChrgBr>SHAR</ChrgBr>
            <InstgAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </InstgAgt>
            <InstdAgt>
                <FinInstnId>
                    <BICFI>BCMAMAMC</BICFI>
                </FinInstnId>
            </InstdAgt>
            <Dbtr>
                <Nm>STE ROYALE D ENCOUR GEMENT</Nm>
            </Dbtr>
            <DbtrAcct>
                <Id>
                    <Othr>
                        <Id>011810000001210000766581</Id>
                    </Othr>
                </Id>
            </DbtrAcct>
            <DbtrAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </DbtrAgt>
            <CdtrAgt>
                <FinInstnId>
                    <BICFI>BCMAMAMC</BICFI>
                </FinInstnId>
            </CdtrAgt>
            <Cdtr>
                <Nm>SOREC</Nm>
            </Cdtr>
            <CdtrAcct>
                <Id>
                    <Othr>
                        <Id>007810000181900000153586</Id>
                    </Othr>
                </Id>
            </CdtrAcct>
            <InstrForNxtAgt>
                <InstrInf>SRBM SOREC BMCE</InstrInf>
            </InstrForNxtAgt>
            <Purp>
                <Prtry>001</Prtry>
            </Purp>
            <RmtInf>
                <Ustrd>SRBM SOREC BMCE</Ustrd>
            </RmtInf>
        </CdtTrfTxInf>
    </FIToFICstmrCdtTrf>
</Document>
</Saa:Body></Saa:DataPDU><?xml version="1.0" encoding="UTF-8" ?><Saa:DataPDU xmlns:Saa="urn:swift:saa:xsd:saa.2.0" xmlns:Sw="urn:swift:snl:ns.Sw" xmlns:SwInt="urn:swift:snl:ns.SwInt" xmlns:SwGbl="urn:swift:snl:ns.SwGbl" xmlns:SwSec="urn:swift:snl:ns.SwSec"><Saa:Revision>2.0.15</Saa:Revision><Saa:Header><Saa:TransmissionReport><Saa:SenderReference>TRS000022758350</Saa:SenderReference><Saa:ReconciliationInfo>swi01006-2025-12-12T10:40:47.38315.7496759Z</Saa:ReconciliationInfo><Saa:NetworkDeliveryStatus>NetworkAcked</Saa:NetworkDeliveryStatus><Saa:OriginalInstanceAddressee><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:OriginalInstanceAddressee><Saa:ReportingApplication>SWIFTNetInterface</Saa:ReportingApplication><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000060</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:47.38315.7496759Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:46.52691266.001468Z</Saa:SNLRef><Saa:Reference>fd1a7760-d746-41f0-8833-3c3ef49bc858</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:47</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:Interventions><Saa:Intervention><Saa:IntvCategory>TransmissionReport</Saa:IntvCategory><Saa:CreationTime>20251212104044</Saa:CreationTime><Saa:OperatorOrigin>SYSTEM</Saa:OperatorOrigin><Saa:Contents><AckNack><PseudoAckNack>{1:F21BMCEMAMCAXXX000029000000060}{4:{177:2512121040}{451:0}{311:ACK}}</PseudoAckNack></AckNack></Saa:Contents></Saa:Intervention></Saa:Interventions><Saa:IsRelatedInstanceOriginal>true</Saa:IsRelatedInstanceOriginal><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:IsMessageModified>false</Saa:IsMessageModified><Saa:MessageFields>HeaderAndBody</Saa:MessageFields><Saa:Message><Saa:SenderReference>TRS000022758350</Saa:SenderReference><Saa:MessageIdentifier>pacs.008.001.08</Saa:MessageIdentifier><Saa:Format>MX</Saa:Format><Saa:SubFormat>Input</Saa:SubFormat><Saa:Sender><Saa:DN>ou=xxx,o=bmcemamc,o=swift</Saa:DN><Saa:FullName><Saa:X1>BMCEMAMCXXX</Saa:X1></Saa:FullName></Saa:Sender><Saa:Receiver><Saa:DN>cn=bamrtgs-prod,o=bkammama,o=swift</Saa:DN><Saa:FullName><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:FullName></Saa:Receiver><Saa:InterfaceInfo><Saa:MessageCreator>ApplicationInterface</Saa:MessageCreator><Saa:MessageContext>Report</Saa:MessageContext><Saa:MessageNature>Financial</Saa:MessageNature><Saa:Sumid>16C40DDEFEE6002A</Saa:Sumid></Saa:InterfaceInfo><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>000029</Saa:SessionNr><Saa:SeqNr>000000060</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.008.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi01006-2025-12-12T10:40:47.38315.7496759Z</Saa:SWIFTRef><Saa:SNLRef>SNL33986-2025-12-12T10:40:46.52691266.001468Z</Saa:SNLRef><Saa:Reference>fd1a7760-d746-41f0-8833-3c3ef49bc858</Saa:Reference><Saa:SnFInputTime>0102:2025-12-12T10:40:47</Saa:SnFInputTime><Saa:ResponsePayloadAttributes><Saa:PayloadAttribute><Saa:Name>type</Saa:Name><Saa:Value>swift.emptyresponse</Saa:Value></Saa:PayloadAttribute></Saa:ResponsePayloadAttributes></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:SecurityInfo><Saa:SWIFTNetSecurityInfo><Saa:SignerDN>cn=so1,o=bmcemamc,o=swift</Saa:SignerDN><Saa:SignatureValue><SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>rwOB1t2FLtQ37DGnO947NHeimTCMynpBeMiYXTQu9Nk=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY
Content-Domain: RFC822
EntrustFile-Version: 2.0
Originator-DN: cn=so1,o=bmcemamc,o=swift
Orig-SN: 1746972102
MIC-Info: SHA256, RSA,
 ks0Q41axaINWs68wD3+IThiNQoe3+TjlvM4VKH1rV98FE8+yqiU+a2nDM9cOhn7Q
 7VtiQqjKxLN4cUPNnwm8vZ0czkNAWyaAUPAZ8T+ROe+cxJeaemR3mcVLb+Eg8TnB
 alOeLDCscs3/e/brStEQnhE6+9MmNeAHD2Zj9i3fYGrm44kOFShQ+s0WlTTKfJZC
 vKX2sPw5ALClGr8uwRZqcbw0rXKZr/3IB3fcrWKH2LYKQNeUGuStzL70H1epFshi
 qKyRtL8rW2DW6eyDU3KPMY+MGXHq3p4ps+EkBr38P4rST0lLLmX1UlY9Q/Hk4Dd/
 vCiFAa7CpM61SUgGrEw2vw==
</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=so1,o=bmcemamc,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.2</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>Sw.RequestHeader</Sw:DigestRef><Sw:DigestValue>+sUN1ietY2Wb+rGvg6TM5olOvusRTlnbP3kO3GkfIqM=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.RequestPayload</Sw:DigestRef><Sw:DigestValue>3nlUa8afxz32qA8OdP7yibPXod/dZAb+KCVS4cvOg3w=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.E2S</Sw:DigestRef><Sw:DigestValue>OwNGq/f/KKhMXBtCQlmTJ2dzFdx4CpkUH7bwxfgl3PU=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature></Saa:SignatureValue></Saa:SWIFTNetSecurityInfo></Saa:SecurityInfo><Saa:ExpiryDateTime>20260101104034</Saa:ExpiryDateTime></Saa:Message></Saa:TransmissionReport></Saa:Header><Saa:Body>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.02">
    <Fr>
        <FIId>
            <FinInstnId>
                <BICFI>BMCEMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </Fr>
    <To>
        <FIId>
            <FinInstnId>
                <BICFI>CIHMMAMC</BICFI>
            </FinInstnId>
        </FIId>
    </To>
    <BizMsgIdr>TRS000022758350</BizMsgIdr>
    <MsgDefIdr>pacs.008.001.08</MsgDefIdr>
    <BizSvc>swift.iap.tia.02</BizSvc>
    <CreDt>2025-12-12T11:26:57+01:00</CreDt>
    <Prty>0020</Prty>
</AppHdr>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08">
    <FIToFICstmrCdtTrf>
        <GrpHdr>
            <MsgId>TRS000022758350</MsgId>
            <CreDtTm>2025-12-12T11:26:57+01:00</CreDtTm>
            <NbOfTxs>1</NbOfTxs>
            <SttlmInf>
                <SttlmMtd>CLRG</SttlmMtd>
                <ClrSys>
                    <Cd>RTG</Cd>
                </ClrSys>
            </SttlmInf>
        </GrpHdr>
        <CdtTrfTxInf>
            <PmtId>
                <InstrId>TRS000022758350</InstrId>
                <EndToEndId>NOT PROVIDED</EndToEndId>
                <TxId>TRS000022758350</TxId>
                <UETR>c6d5f44d-89ce-42ac-86f2-9678fe36396b</UETR>
            </PmtId>
            <PmtTpInf>
                <LclInstrm>
                    <Prtry>RTGS-SCCT</Prtry>
                </LclInstrm>
            </PmtTpInf>
            <IntrBkSttlmAmt Ccy="MAD">103000</IntrBkSttlmAmt>
            <IntrBkSttlmDt>2025-12-12</IntrBkSttlmDt>
            <SttlmPrty>URGT</SttlmPrty>
            <InstdAmt Ccy="MAD">103000</InstdAmt>
            <ChrgBr>SHAR</ChrgBr>
            <InstgAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </InstgAgt>
            <InstdAgt>
                <FinInstnId>
                    <BICFI>CIHMMAMC</BICFI>
                </FinInstnId>
            </InstdAgt>
            <Dbtr>
                <Nm>M Said ANDAM</Nm>
            </Dbtr>
            <DbtrAcct>
                <Id>
                    <Othr>
                        <Id>011550000002200000374754</Id>
                    </Othr>
                </Id>
            </DbtrAcct>
            <DbtrAgt>
                <FinInstnId>
                    <BICFI>BMCEMAMC</BICFI>
                </FinInstnId>
            </DbtrAgt>
            <CdtrAgt>
                <FinInstnId>
                    <BICFI>CIHMMAMC</BICFI>
                </FinInstnId>
            </CdtrAgt>
            <Cdtr>
                <Nm>OULHAJ NAJAT</Nm>
            </Cdtr>
            <CdtrAcct>
                <Id>
                    <Othr>
                        <Id>230550306912621102410146</Id>
                    </Othr>
                </Id>
            </CdtrAcct>
            <InstrForNxtAgt>
                <InstrInf>M Said ANDAM</InstrInf>
            </InstrForNxtAgt>
            <Purp>
                <Prtry>001</Prtry>
            </Purp>
            <RmtInf>
                <Ustrd>M Said ANDAM</Ustrd>
            </RmtInf>
        </CdtTrfTxInf>
    </FIToFICstmrCdtTrf>
</Document>
</Saa:Body></Saa:DataPDU>
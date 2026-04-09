<?xml version="1.0" encoding="UTF-8" ?><Saa:DataPDU xmlns:Saa="urn:swift:saa:xsd:saa.2.0" xmlns:Sw="urn:swift:snl:ns.Sw" xmlns:SwInt="urn:swift:snl:ns.SwInt" xmlns:SwGbl="urn:swift:snl:ns.SwGbl" xmlns:SwSec="urn:swift:snl:ns.SwSec"><Saa:Revision>2.0.15</Saa:Revision><Saa:Header><Saa:Message><Saa:SenderReference>OBKAMMAMAXXX00910743344$251030179600</Saa:SenderReference><Saa:MessageIdentifier>pacs.009.001.08</Saa:MessageIdentifier><Saa:Format>MX</Saa:Format><Saa:SubFormat>Output</Saa:SubFormat><Saa:Sender><Saa:DN>cn=bamrtgs,o=bkammama,o=swift</Saa:DN><Saa:FullName><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:FullName></Saa:Sender><Saa:Receiver><Saa:DN>ou=xxx,o=bmcemamc,o=swift</Saa:DN><Saa:FullName><Saa:X1>BMCEMAMCXXX</Saa:X1><Saa:X2>xxx</Saa:X2></Saa:FullName></Saa:Receiver><Saa:InterfaceInfo><Saa:UserReference>10743344</Saa:UserReference><Saa:MessageCreator>SWIFTNetInterface</Saa:MessageCreator><Saa:MessageContext>Original</Saa:MessageContext><Saa:MessageNature>Financial</Saa:MessageNature><Saa:Sumid>16FCC569FFFD426F</Saa:Sumid></Saa:InterfaceInfo><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm!p</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>001021</Saa:SessionNr><Saa:SeqNr>000006704</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>pacs.009.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.01</Saa:RequestSubtype><Saa:SWIFTRef>swi03003-2025-10-30T10:10:30.32932.404395Z</Saa:SWIFTRef><Saa:SNLRef>SNL36758-2025-10-30T10:09:36.1468156.072935Z</Saa:SNLRef><Saa:Reference>7dc247e4-b578-41f0-9b08-a96ac9e6f705</Saa:Reference><Saa:SnFQueueName>bmcemamc_generic!p</Saa:SnFQueueName><Saa:SnFInputTime>0301:2025-10-30T10:10:30</Saa:SnFInputTime><Saa:SnFDeliveryTime>2025-10-30T10:10:30Z</Saa:SnFDeliveryTime><Saa:ValidationDescriptor>
                <SwInt:ValResult>Success</SwInt:ValResult>
            </Saa:ValidationDescriptor></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:SecurityInfo><Saa:SWIFTNetSecurityInfo><Saa:SignerDN>cn=bamrtgs,o=bkammama,o=swift</Saa:SignerDN><Saa:SignatureResult>Success</Saa:SignatureResult><Saa:SignatureValue><SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>3Bw5zF1aKspCKxK5ZfQGh1zfbVjIcXtAa/mB4MZpu2o=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY
Content-Domain: RFC822
EntrustFile-Version: 2.0
Originator-DN: cn=bamrtgs,o=bkammama,o=swift
Orig-SN: 1752363366
MIC-Info: SHA256, RSA,
 q6ABbljW6+RSg9TY48k0trHZzwCagXv8FpisqtqdrcIPkPHXvmCrh9KwxsoU0Nnh
 gEQ2DgVqAMwQCgAsSeW6yq/P1PoqDCkMpWvQA7qf7slYI3P/mO+sQ8kEykSJ41qQ
 9Hzb26U7WD2duqG9XyokmK8exQkELEHq8EfVlozdZTJ4q8zWDWZA79/70YEAm18+
 KsxsbNdpMLY4l84wq9PLNM8RxUSsgIcN+jlunBhLwP6nHEY/Y1DMf6U18GPcdonz
 sSBwANl6dqHmQBr2WKUAOuS8AjydYYd2T9wVxZ5mS7Q3Bhv8omZHzf6dHEDZ97bp
 PzM+6Rw/700XghEIl1abaA==
</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=bamrtgs,o=bkammama,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.6.10.100.1</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>Sw.RequestHeader</Sw:DigestRef><Sw:DigestValue>g0EBaZiMa47P0snlTj3Ud9tqeMNp7F8110Py8lDBqYo=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.RequestPayload</Sw:DigestRef><Sw:DigestValue>8IBxRrLdN9Wjq0dnz61eqstjsSUcgqV8GP8i+LTeMcg=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.E2S</Sw:DigestRef><Sw:DigestValue>U/CgVidZ9IJe4k7yCk/1ARtwlgcjkoXkruu9oVbgw6g=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature></Saa:SignatureValue></Saa:SWIFTNetSecurityInfo></Saa:SecurityInfo><Saa:ExpiryDateTime>20251119101031</Saa:ExpiryDateTime></Saa:Message></Saa:Header><Saa:Body>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.02"><Fr><FIId><FinInstnId><BICFI>CAFGMAMC</BICFI></FinInstnId></FIId></Fr>
<To><FIId><FinInstnId><BICFI>BMCEMAMC</BICFI></FinInstnId></FIId></To>
<BizMsgIdr>15840798</BizMsgIdr><MsgDefIdr>pacs.009.001.08</MsgDefIdr><BizSvc>swift.iap.tia.01</BizSvc><CreDt>2025-10-30T11:09:42+01:00</CreDt>
<Prty>0020</Prty>
<Sgntr><ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#" Id="_54a6a458-2656-4523-b90c-902483846ba9"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/><ds:SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/><ds:Reference URI="#_ab430183-4e94-42a5-ad6c-2e613feebd54-ki"><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/><ds:DigestValue>bI3FQJMZMpcKVHMtCHEArDwe8Z/YyFolINSyVrq89a0=</ds:DigestValue></ds:Reference><ds:Reference Type="http://uri.etsi.org/01903/v1.3.2#SignedProperties" URI="#_d8f80f5b-afec-4922-a0b0-4ba9b5435dd6-sp"><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/><ds:DigestValue>4WLqgRvhT+NM5nlHn3g+PqAkXGauf8CXI8JTVeu+Z/k=</ds:DigestValue></ds:Reference><ds:Reference><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/><ds:DigestValue>UBSS637QvuCOYNKiwGP98fg0FVdFv4bc9iEK719W7iI=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>l2cegdHRddB4Pp8f9K9yv4aKpFeHT2AwiQx/LuNY59cvO/l0JXhSpoRMPYDI5nmEc/57cWZ52fM+6Ij7PGMBgzQCHKgL9CfZt2/F5vd0gdAp50kbDnkeh/yfWjamIHhMvGi2Pz68QCBma9o0RnK020UyvoREXGDKiwXHQ1wOMLg8D/eNRkB6nuZr+GvsyYE0Yd57lUw1FmU1O0Nzjq1KPqHqtRGF+9KPqYx3Y0B8nUkmCJ2jQ3Yd5/vvBtjwUSMT2ZTDZZByfMofkW6+E2A840OcTVePyM6rAkgxJvZ06jxW9Kg9dhnMwy/horMfBmEYbITuQYbqdb6ZldcDSWiwYQ==</ds:SignatureValue><ds:KeyInfo Id="_ab430183-4e94-42a5-ad6c-2e613feebd54-ki"><ds:X509Data><ds:X509Certificate>MIIFjDCCA3SgAwIBAgIUAOydzSEu6jZSU2jdUFspFG8qwHcwDQYJKoZIhvcNAQELBQAweTELMAkGA1UEBhMCTUExGTAXBgNVBAoTEEJhcmlkIEFsIE1hZ2hyaWIxDjAMBgNVBAsTBTUwNDEzMRMwEQYDVQQLEwpCYXJpZGVzaWduMSowKAYDVQQDEyFURVNUIEJhcmlkZXNpZ24gQ29ycG9yYXRlIFNTTCBCQU0wHhcNMjQxMjMwMTIyNTA4WhcNMjcxMjMwMTIyNTA4WjB0MQswCQYDVQQGEwJNQTEaMBgGA1UECgwRQmFyaWQgQWwgTWFoZ2hyaWIxDjAMBgNVBAsMBTUwNDEzMRMwEQYDVQQLDApCYXJpZGVzaWduMRUwEwYDVQQDDAxCS0FNTUFNMFhJUFMxDTALBgNVBAUMBDEtMzUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC0kQ5tPYsveWTM/Z2bcX/NG+lQhujtCP3FERwsbyvfJ2zyJAKcxmqFHoxOEr+DJ0Qvji0JLcqB1ECp/eUTe1i73bzrjk7Md3JfLN5PZC1gHMVwtvWXgdq/UPGrIOTEPA4xnQ2KA6Kl1LGVbLBH2OHadcw6pMs+r/64sGpjmh11ez5M/DnTr4xiSX9sBNoxR6OUsDhyiWeOqWgmySxhUbwb+AiaZ7sotVZgefVcx4ZqLr6EDD2jbQzluEmo9JgWYYfnjIPiXLwnprdkdqiDT5RY534LTRaHXi9Kr2Ts/392BHM1SDiIdmqJ4wUlG0R4HlDYEeb0AhSootYBXAZdvFUJAgMBAAGjggEPMIIBCzAOBgNVHQ8BAf8EBAMCBkAwSAYIKwYBBQUHAQEEPDA6MDgGCCsGAQUFBzABhixodHRwOi8vc3NsLmJhcmlkZXNpZ24ubWEvQ29ycG9yYXRlL29jc3AvT0NTUDAfBgNVHSMEGDAWgBRho68IwVpDngMLqlgPkhm/bAVRkzAJBgNVHRMEAjAAMBkGA1UdIAQSMBAwDgYMKoN4AQEBAQEBAikBMEkGA1UdHwRCMEAwPqA8oDqGOGh0dHA6Ly9zc2wuYmFyaWRlc2lnbi5tYS9Db3Jwb3JhdGUvY3JsL1NTTF9fQUMtY3JsLTEuY3JsMB0GA1UdDgQWBBQ65Bpu1hgW+lQT+bZR401wn/R2ajANBgkqhkiG9w0BAQsFAAOCAgEACFG0tfg0knEdnCeljUfv6fHkSOPdz6LQeBiXUReBDIaN+4j4uW4P6W/DOrU0AE4uV9s73WKs0ougagCXUN0hKe035NTMRTXhxTUAJP1LnISSc57lsh4r0C+x5BjdjbSP64otiPcN24HNt3pAkyGz6bHGVvlnWIxapLkBHhLCB2Kfiv4l2pGdIZQmtYn2l7Xp1DIqO/bt7JABh7LJIvqLFoWErNx12fb+OZ32gY3N2P3Emlbgmg2yDTQA1ezQaCCqT9GrivauOhdhed6zToGpgvYZM/KL7RB5WbKbybaPIirx502p31v891u7opL0/CA5ucYTmdahU+1WEKsx7W1jrarwQb+ZBi636EHQeMbs3pvWrXI+jgeLlvG9Og2jNR0+Ls0s2LUPDQtOoaJDPxmoDan906BHqBOcfTeDbtp6G98SrhpXluhPKDqq+lCDLcbSNtarEPRPeUC9/RtkhR3N955WKz/D34aLJUKXDWv+7e6H/3M27RJ8ICf7HcYKxIFUGZWbq9JdxwsUv28RaqE6yxp+hRWcbvIiSFj8zjRszdUTt7eUReOWcXrbxSqm62EmZ8jSz6OwF9xBYf/AIy2Dyw3L36QrmJdRzEOjSqC5JvhNwMEfJtXEzgL81dd3snH9h+C3zmctCxlL2qOIXzjfXlDcBlmqRXORelFz3ljoN9Q=</ds:X509Certificate></ds:X509Data></ds:KeyInfo><ds:Object><xades:QualifyingProperties xmlns:xades="http://uri.etsi.org/01903/v1.3.2#" Target="#_54a6a458-2656-4523-b90c-902483846ba9"><xades:SignedProperties Id="_d8f80f5b-afec-4922-a0b0-4ba9b5435dd6-sp"><xades:SignedSignatureProperties><xades:SigningTime>2025-10-30T11:09:41+01:00</xades:SigningTime></xades:SignedSignatureProperties></xades:SignedProperties></xades:QualifyingProperties></ds:Object></ds:Signature></Sgntr></AppHdr>
<q2:Document xmlns:q2="urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08">
      <q2:FICdtTrf>
        <q2:GrpHdr>
          <q2:MsgId>186104686</q2:MsgId>
          <q2:CreDtTm>2025-10-30T00:00:00+01:00</q2:CreDtTm>
          <q2:NbOfTxs>1</q2:NbOfTxs>
          <q2:SttlmInf>
            <q2:SttlmMtd>CLRG</q2:SttlmMtd>
            <q2:ClrSys>
              <q2:Cd>RTG</q2:Cd>
            </q2:ClrSys>
          </q2:SttlmInf>
        </q2:GrpHdr>
        <q2:CdtTrfTxInf>
          <q2:PmtId>
            <q2:InstrId>186104686</q2:InstrId>
            <q2:EndToEndId>NOTPROVIDED</q2:EndToEndId>
            <q2:TxId>186104686</q2:TxId>
            <q2:UETR>a75b97f7-fca4-451e-82f7-7089c7757c4e</q2:UETR>
          </q2:PmtId>
          <q2:PmtTpInf>
            <q2:LclInstrm>
              <q2:Prtry>RTGS-FICT</q2:Prtry>
            </q2:LclInstrm>
          </q2:PmtTpInf>
          <q2:IntrBkSttlmAmt Ccy="MAD">60000</q2:IntrBkSttlmAmt>
          <q2:IntrBkSttlmDt>2025-10-30</q2:IntrBkSttlmDt>
          <q2:InstgAgt>
            <q2:FinInstnId>
              <q2:BICFI>CAFGMAMCXXX</q2:BICFI>
            </q2:FinInstnId>
          </q2:InstgAgt>
          <q2:InstdAgt>
            <q2:FinInstnId>
              <q2:BICFI>BMCEMAMCXXX</q2:BICFI>
            </q2:FinInstnId>
          </q2:InstdAgt>
          <q2:Dbtr>
            <q2:FinInstnId>
              <q2:BICFI>CAFGMAMCXXX</q2:BICFI>
            </q2:FinInstnId>
          </q2:Dbtr>
          <q2:Cdtr>
            <q2:FinInstnId>
              <q2:BICFI>BMCEMAMCXXX</q2:BICFI>
            </q2:FinInstnId>
          </q2:Cdtr>
          <q2:Purp>
            <q2:Prtry>040</q2:Prtry>
          </q2:Purp>
          <q2:RmtInf>
            <q2:Ustrd>/PJJ/3.00</q2:Ustrd>
          </q2:RmtInf>
        </q2:CdtTrfTxInf>
      </q2:FICdtTrf>
    </q2:Document></Saa:Body></Saa:DataPDU><?xml version="1.0" encoding="UTF-8" ?><Saa:DataPDU xmlns:Saa="urn:swift:saa:xsd:saa.2.0" xmlns:Sw="urn:swift:snl:ns.Sw" xmlns:SwInt="urn:swift:snl:ns.SwInt" xmlns:SwGbl="urn:swift:snl:ns.SwGbl" xmlns:SwSec="urn:swift:snl:ns.SwSec"><Saa:Revision>2.0.15</Saa:Revision><Saa:Header><Saa:Message><Saa:SenderReference>OBKAMMAMAXXX05410743346$251030179601</Saa:SenderReference><Saa:MessageIdentifier>camt.054.001.08</Saa:MessageIdentifier><Saa:Format>MX</Saa:Format><Saa:SubFormat>Output</Saa:SubFormat><Saa:Sender><Saa:DN>cn=bamrtgs,o=bkammama,o=swift</Saa:DN><Saa:FullName><Saa:X1>BKAMMAMAXXX</Saa:X1></Saa:FullName></Saa:Sender><Saa:Receiver><Saa:DN>ou=xxx,o=bmcemamc,o=swift</Saa:DN><Saa:FullName><Saa:X1>BMCEMAMCXXX</Saa:X1><Saa:X2>xxx</Saa:X2></Saa:FullName></Saa:Receiver><Saa:InterfaceInfo><Saa:UserReference>10743346</Saa:UserReference><Saa:MessageCreator>SWIFTNetInterface</Saa:MessageCreator><Saa:MessageContext>Original</Saa:MessageContext><Saa:MessageNature>Financial</Saa:MessageNature><Saa:Sumid>16FCC567FFFD426E</Saa:Sumid></Saa:InterfaceInfo><Saa:NetworkInfo><Saa:Priority>Normal</Saa:Priority><Saa:IsPossibleDuplicate>false</Saa:IsPossibleDuplicate><Saa:Service>bkam.srbm!p</Saa:Service><Saa:Network>SWIFTNet</Saa:Network><Saa:SessionNr>001021</Saa:SessionNr><Saa:SeqNr>000006705</Saa:SeqNr><Saa:SWIFTNetNetworkInfo><Saa:RequestType>camt.054.001.08</Saa:RequestType><Saa:RequestSubtype>swift.iap.tia.02</Saa:RequestSubtype><Saa:SWIFTRef>swi03003-2025-10-30T10:10:31.32768.206437Z</Saa:SWIFTRef><Saa:SNLRef>SNL36758-2025-10-30T10:09:37.1467922.073686Z</Saa:SNLRef><Saa:Reference>7dc8a2a6-b578-41f0-9b08-f3dd403aae61</Saa:Reference><Saa:SnFQueueName>bmcemamc_generic!p</Saa:SnFQueueName><Saa:SnFInputTime>0301:2025-10-30T10:10:31</Saa:SnFInputTime><Saa:SnFDeliveryTime>2025-10-30T10:10:32Z</Saa:SnFDeliveryTime><Saa:ValidationDescriptor>
                <SwInt:ValResult>Success</SwInt:ValResult>
            </Saa:ValidationDescriptor></Saa:SWIFTNetNetworkInfo></Saa:NetworkInfo><Saa:SecurityInfo><Saa:SWIFTNetSecurityInfo><Saa:SignerDN>cn=bamrtgs,o=bkammama,o=swift</Saa:SignerDN><Saa:SignatureResult>Success</Saa:SignatureResult><Saa:SignatureValue><SwSec:Signature><SwSec:SignedInfo><Sw:Reference><Sw:DigestValue>N6EhuPdyGQngudiwLg0Li+11n+WJzBhBHHjpY+9M+vw=</Sw:DigestValue></Sw:Reference></SwSec:SignedInfo><SwSec:SignatureValue>PEMF@Proc-Type: 4,MIC-ONLY
Content-Domain: RFC822
EntrustFile-Version: 2.0
Originator-DN: cn=bamrtgs,o=bkammama,o=swift
Orig-SN: 1752363366
MIC-Info: SHA256, RSA,
 oFw2C9em2oYTExDYIesB751T3vm/IYuJYC0g04aGlvBNByniqxCE4YRDv+tKMRSF
 3ZPKCppYcKEhkzEeDFQkebajtUB9pwyHzC1WErwKiWJH6W9URUHrrlAwllzVpq15
 r+srSUiEUULulyIUftD47vqwtcuQAT96K+9l8skCi/jXpQh77Ot1IQGWEZD76UxT
 YNnbbn0Ak7D6VyMRFWFjh0OSENwWyIRCsURTFu3nvXhaRW/U8KYE0KQDZssoKy3M
 6UktjPVx6+vuXA4LUvLEEXiky3PdL/1R8r9u+hLu00YcJ7pOeloUpVLZUVxtDVBi
 KrKgRGwEpvZhmcoG9GXgug==
</SwSec:SignatureValue><SwSec:KeyInfo><SwSec:SignDN>cn=bamrtgs,o=bkammama,o=swift</SwSec:SignDN><SwSec:CertPolicyId>1.3.21.6.6.10.100.1</SwSec:CertPolicyId></SwSec:KeyInfo><SwSec:Manifest><Sw:Reference><Sw:DigestRef>Sw.RequestHeader</Sw:DigestRef><Sw:DigestValue>K/6GhB9eOnU+4A9TQv1Fnoe9DvkNd11khZgMJF7/UaQ=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.RequestPayload</Sw:DigestRef><Sw:DigestValue>YJX7O4banYlh7d2dTPgPbOUaaZ/HYj8kfROJ09xWBeA=</Sw:DigestValue></Sw:Reference><Sw:Reference><Sw:DigestRef>Sw.E2S</Sw:DigestRef><Sw:DigestValue>J9D3zDTzd84GDlsphqkS96eK2kGn+LQFdin5NuCtDU8=</Sw:DigestValue></Sw:Reference></SwSec:Manifest></SwSec:Signature></Saa:SignatureValue></Saa:SWIFTNetSecurityInfo></Saa:SecurityInfo><Saa:ExpiryDateTime>20251119101033</Saa:ExpiryDateTime></Saa:Message></Saa:Header><Saa:Body>
<AppHdr xmlns="urn:iso:std:iso:20022:tech:xsd:head.001.001.02"><Fr><FIId><FinInstnId><BICFI>BKAMMAMA</BICFI></FinInstnId></FIId></Fr>
<To><FIId><FinInstnId><BICFI>BMCEMAMC</BICFI></FinInstnId></FIId></To>
<BizMsgIdr>15840799</BizMsgIdr><MsgDefIdr>camt.054.001.08</MsgDefIdr><BizSvc>swift.iap.tia.02</BizSvc><CreDt>2025-10-30T11:09:42+01:00</CreDt>
<Sgntr><ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#" Id="_50db5070-a0bd-4cc1-9bcc-7cc5f1429ea7"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/><ds:SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/><ds:Reference URI="#_9cc8326c-3df2-4383-b819-3255c04720d9-ki"><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/><ds:DigestValue>+PYUppDZxECQXu2YWgykMvnF0ZUcBQk/6c9tIE53KlI=</ds:DigestValue></ds:Reference><ds:Reference Type="http://uri.etsi.org/01903/v1.3.2#SignedProperties" URI="#_6b05e51a-b15d-4bdd-923c-457277befcb5-sp"><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/><ds:DigestValue>uW43wiZPb7bLPDQ9mzYQhlwyEEjDWKQaEi1M+VMACUw=</ds:DigestValue></ds:Reference><ds:Reference><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/><ds:DigestValue>SbG9YSZmPoSjbGbxMC7MhjTo8kd856dX3fvtU5s3HAc=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>QPP5G3eUrt3NDM1BQbtCOSP33LIanhWfFuZ+52km6anQv7MKUKlwyYzWnTXrU6LjcMdLJ1TDb9UeAJkrbq2CCtuQG9zGuU9xO5ThqZg6MDwUJ693+J7N37FHbg3/RYYU7HhaQzGEdON/CS9aAs0pVmvL6J9QIjrEFNl07JsvmNjcX2pXAkYhJFYWKQMvdr+0E5elWcIu7/pmMF7jo/go0fmslmxV02nsxJQxCAkbbZ3B5nZ+aQUkjQR0ZUMKYWXaOjOJMC4mebYS23CgF48d4b6Iz7UcNjDmQRuYj9dTun50+9hfvl6Hu08Ihj71l+Sv/HqFFHQDG9NMyr4gyvoE4A==</ds:SignatureValue><ds:KeyInfo Id="_9cc8326c-3df2-4383-b819-3255c04720d9-ki"><ds:X509Data><ds:X509Certificate>MIIFjDCCA3SgAwIBAgIUAOydzSEu6jZSU2jdUFspFG8qwHcwDQYJKoZIhvcNAQELBQAweTELMAkGA1UEBhMCTUExGTAXBgNVBAoTEEJhcmlkIEFsIE1hZ2hyaWIxDjAMBgNVBAsTBTUwNDEzMRMwEQYDVQQLEwpCYXJpZGVzaWduMSowKAYDVQQDEyFURVNUIEJhcmlkZXNpZ24gQ29ycG9yYXRlIFNTTCBCQU0wHhcNMjQxMjMwMTIyNTA4WhcNMjcxMjMwMTIyNTA4WjB0MQswCQYDVQQGEwJNQTEaMBgGA1UECgwRQmFyaWQgQWwgTWFoZ2hyaWIxDjAMBgNVBAsMBTUwNDEzMRMwEQYDVQQLDApCYXJpZGVzaWduMRUwEwYDVQQDDAxCS0FNTUFNMFhJUFMxDTALBgNVBAUMBDEtMzUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC0kQ5tPYsveWTM/Z2bcX/NG+lQhujtCP3FERwsbyvfJ2zyJAKcxmqFHoxOEr+DJ0Qvji0JLcqB1ECp/eUTe1i73bzrjk7Md3JfLN5PZC1gHMVwtvWXgdq/UPGrIOTEPA4xnQ2KA6Kl1LGVbLBH2OHadcw6pMs+r/64sGpjmh11ez5M/DnTr4xiSX9sBNoxR6OUsDhyiWeOqWgmySxhUbwb+AiaZ7sotVZgefVcx4ZqLr6EDD2jbQzluEmo9JgWYYfnjIPiXLwnprdkdqiDT5RY534LTRaHXi9Kr2Ts/392BHM1SDiIdmqJ4wUlG0R4HlDYEeb0AhSootYBXAZdvFUJAgMBAAGjggEPMIIBCzAOBgNVHQ8BAf8EBAMCBkAwSAYIKwYBBQUHAQEEPDA6MDgGCCsGAQUFBzABhixodHRwOi8vc3NsLmJhcmlkZXNpZ24ubWEvQ29ycG9yYXRlL29jc3AvT0NTUDAfBgNVHSMEGDAWgBRho68IwVpDngMLqlgPkhm/bAVRkzAJBgNVHRMEAjAAMBkGA1UdIAQSMBAwDgYMKoN4AQEBAQEBAikBMEkGA1UdHwRCMEAwPqA8oDqGOGh0dHA6Ly9zc2wuYmFyaWRlc2lnbi5tYS9Db3Jwb3JhdGUvY3JsL1NTTF9fQUMtY3JsLTEuY3JsMB0GA1UdDgQWBBQ65Bpu1hgW+lQT+bZR401wn/R2ajANBgkqhkiG9w0BAQsFAAOCAgEACFG0tfg0knEdnCeljUfv6fHkSOPdz6LQeBiXUReBDIaN+4j4uW4P6W/DOrU0AE4uV9s73WKs0ougagCXUN0hKe035NTMRTXhxTUAJP1LnISSc57lsh4r0C+x5BjdjbSP64otiPcN24HNt3pAkyGz6bHGVvlnWIxapLkBHhLCB2Kfiv4l2pGdIZQmtYn2l7Xp1DIqO/bt7JABh7LJIvqLFoWErNx12fb+OZ32gY3N2P3Emlbgmg2yDTQA1ezQaCCqT9GrivauOhdhed6zToGpgvYZM/KL7RB5WbKbybaPIirx502p31v891u7opL0/CA5ucYTmdahU+1WEKsx7W1jrarwQb+ZBi636EHQeMbs3pvWrXI+jgeLlvG9Og2jNR0+Ls0s2LUPDQtOoaJDPxmoDan906BHqBOcfTeDbtp6G98SrhpXluhPKDqq+lCDLcbSNtarEPRPeUC9/RtkhR3N955WKz/D34aLJUKXDWv+7e6H/3M27RJ8ICf7HcYKxIFUGZWbq9JdxwsUv28RaqE6yxp+hRWcbvIiSFj8zjRszdUTt7eUReOWcXrbxSqm62EmZ8jSz6OwF9xBYf/AIy2Dyw3L36QrmJdRzEOjSqC5JvhNwMEfJtXEzgL81dd3snH9h+C3zmctCxlL2qOIXzjfXlDcBlmqRXORelFz3ljoN9Q=</ds:X509Certificate></ds:X509Data></ds:KeyInfo><ds:Object><xades:QualifyingProperties xmlns:xades="http://uri.etsi.org/01903/v1.3.2#" Target="#_50db5070-a0bd-4cc1-9bcc-7cc5f1429ea7"><xades:SignedProperties Id="_6b05e51a-b15d-4bdd-923c-457277befcb5-sp"><xades:SignedSignatureProperties><xades:SigningTime>2025-10-30T11:09:41+01:00</xades:SigningTime></xades:SignedSignatureProperties></xades:SignedProperties></xades:QualifyingProperties></ds:Object></ds:Signature></Sgntr><Rltd>
<Fr><FIId><FinInstnId><BICFI>CAFGMAMCXXX</BICFI></FinInstnId></FIId></Fr>
<To><FIId><FinInstnId><BICFI>BMCEMAMCXXX</BICFI></FinInstnId></FIId></To>
<BizMsgIdr>186104686</BizMsgIdr>
<MsgDefIdr>pacs.009.001.08</MsgDefIdr>
<BizSvc>swift.iap.tia.01</BizSvc>
<CreDt>2025-10-30T00:00:00+01:00</CreDt>
<Prty>0020</Prty>
</Rltd>
</AppHdr>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.054.001.08"><BkToCstmrDbtCdtNtfctn><GrpHdr><MsgId>92700639</MsgId><CreDtTm>2025-10-30T11:09:42.284+01:00</CreDtTm></GrpHdr><Ntfctn><Id>92700639</Id><Acct><Id><Othr><Id>0011</Id></Othr></Id><Ccy>MAD</Ccy><Ownr><Id><OrgId><AnyBIC>BMCEMAMC</AnyBIC></OrgId></Id></Ownr></Acct><Ntry><Amt Ccy="MAD">60000.</Amt><CdtDbtInd>CRDT</CdtDbtInd><Sts><Cd>BOOK</Cd></Sts><BookgDt><DtTm>2025-10-30T11:09:42.216+01:00</DtTm></BookgDt><ValDt><Dt>2025-10-30</Dt></ValDt><BkTxCd><Domn><Cd>PMNT</Cd><Fmly><Cd>RCDT</Cd><SubFmlyCd>FICT</SubFmlyCd></Fmly></Domn><Prtry><Cd>pacs.009.001.08</Cd></Prtry></BkTxCd><NtryDtls><TxDtls><Refs><MsgId>186104686</MsgId><InstrId>186104686</InstrId><EndToEndId>NOTPROVIDED</EndToEndId><UETR>a75b97f7-fca4-451e-82f7-7089c7757c4e</UETR><TxId>186104686</TxId><ClrSysRef>20251030CAFGMAMCXXX0000000004487507</ClrSysRef></Refs><Amt Ccy="MAD">60000.</Amt><CdtDbtInd>CRDT</CdtDbtInd><RltdAgts><InstgAgt><FinInstnId><BICFI>CAFGMAMC</BICFI><ClrSysMmbId><ClrSysId><Cd>MARAC</Cd></ClrSysId><MmbId>0050</MmbId></ClrSysMmbId></FinInstnId></InstgAgt><InstdAgt><FinInstnId><BICFI>BMCEMAMC</BICFI><ClrSysMmbId><ClrSysId><Cd>MARAC</Cd></ClrSysId><MmbId>0011</MmbId></ClrSysMmbId></FinInstnId></InstdAgt></RltdAgts><LclInstrm><Prtry>RTGS-FICT</Prtry></LclInstrm><Purp><Prtry>040</Prtry></Purp><RmtInf><Ustrd>/PJJ/3.00</Ustrd></RmtInf></TxDtls></NtryDtls></Ntry></Ntfctn></BkToCstmrDbtCdtNtfctn></Document></Saa:Body></Saa:DataPDU>
package com.stage.swift.helpers;

import com.stage.swift.enums.MessageMX;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gestion des messages MX enveloppés dans SAA (SWIFT).
 * - Détection du type principal via MessageIdentifier (évite de confondre camt.054 avec pacs.008).
 * - Extraction du bloc Document (pacs.008 / pacs.009) depuis Saa:Body pour le parsing Prowide.
 */
public final class SaaEnvelopeHelper {

    private static final Pattern MESSAGE_IDENTIFIER = Pattern.compile(
            "<[^:>]*:?MessageIdentifier[^>]*>([^<]+)</[^:>]*:?MessageIdentifier>",
            Pattern.CASE_INSENSITIVE);

    private SaaEnvelopeHelper() {
    }

    /**
     * Détermine le type MX : parcourt tous les MessageIdentifier (plusieurs PDUs possibles),
     * retient le premier qui est pacs.008 ou pacs.009 ; sinon cherche pacs.008/pacs.009 dans le contenu.
     * Permet d'accepter un pacs.008 même si le corps contient d'abord un camt.054.
     */
    public static MessageMX getMessageTypeFromXml(String xml) {
        if (xml == null) return null;
        MessageMX fromEnvelope = extractFirstPacsTypeFromMessageIdentifiers(xml);
        if (fromEnvelope != null) return fromEnvelope;
        return MessageMX.fromXmlContent(xml);
    }

    /**
     * Retourne le XML à passer à Prowide : si enveloppe SAA, extrait le Document pacs.008/pacs.009 ;
     * sinon retourne le XML tel quel. Utilise le DOM puis, en échec (ex. plusieurs PDUs), une extraction par regex.
     */
    public static String xmlToParse(String xml, MessageMX messageType) {
        if (xml == null || messageType == null) return xml;
        String nsPrefix = messageType == MessageMX.PACS008 ? "pacs.008" : messageType == MessageMX.PACS009 ? "pacs.009" : "camt.054";
        String extracted = extractPacsDocumentFromSaaBody(xml, nsPrefix);
        if (extracted == null)
            extracted = extractPacsDocumentByRegex(xml, nsPrefix);
        return extracted != null ? extracted : xml;
    }

    /**
     * Extrait tous les blocs Document pacs.008 et pacs.009 du XML (un fichier peut contenir plusieurs messages).
     * Ordre conservé. Retourne une liste vide si aucun document PACS trouvé.
     */
    public static List<String> extractAllPacsDocuments(String xml) {
        if (xml == null) return new ArrayList<>();
        List<String> fromDom = extractAllPacsDocumentsFromDom(xml);
        if (!fromDom.isEmpty()) return fromDom;
        return extractAllPacsDocumentsByRegex(xml);
    }

    /** Parcourt tous les MessageIdentifier et retourne PACS008/PACS009 pour le premier qui correspond. */
    static MessageMX extractFirstPacsTypeFromMessageIdentifiers(String xml) {
        if (xml == null) return null;
        Matcher m = MESSAGE_IDENTIFIER.matcher(xml);
        while (m.find()) {
            String id = m.group(1);
            if (id != null) {
                id = id.trim();
                if (id.startsWith("pacs.009")) return MessageMX.PACS009;
                if (id.startsWith("pacs.008")) return MessageMX.PACS008;
                if (id.startsWith("camt.054")) return MessageMX.CAMT054;
            }
        }
        return null;
    }

    static String extractMessageIdentifier(String xml) {
        if (xml == null) return null;
        Matcher m = MESSAGE_IDENTIFIER.matcher(xml);
        return m.find() ? m.group(1) : null;
    }

    /** Retourne le premier MessageIdentifier trouvé (pour message d'erreur). */
    public static String getFirstMessageIdentifier(String xml) {
        String id = extractMessageIdentifier(xml);
        return id != null ? id.trim() : null;
    }

    /** Extrait tous les éléments Document pacs.008 / pacs.009 via DOM (un seul PDU valide). */
    static List<String> extractAllPacsDocumentsFromDom(String xml) {
        List<String> out = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            } catch (Exception ignored) { }
            Document doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            Element root = doc.getDocumentElement();
            if (root == null) return out;
            findAllDocumentElements(root, out);
        } catch (Exception e) {
            return out;
        }
        return out;
    }

    private static void findAllDocumentElements(Node node, List<String> out) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) node;
            String localName = el.getLocalName();
            String ns = el.getNamespaceURI();
            if ("Document".equals(localName) && ns != null && (ns.contains("pacs.008") || ns.contains("pacs.009"))) {
                try {
                    String serialized = nodeToString(node);
                    if (serialized.contains("CdtTrfTxInf")) out.add(serialized);
                } catch (Exception ignored) { }
                return;
            }
        }
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            findAllDocumentElements(children.item(i), out);
        }
    }

    /** Extrait tous les blocs Document pacs.008/pacs.009 par recherche dans la chaîne (plusieurs PDUs). */
    static List<String> extractAllPacsDocumentsByRegex(String xml) {
        List<String> out = new ArrayList<>();
        int fromIndex = 0;
        while (fromIndex < xml.length()) {
            int next008 = xml.indexOf("pacs.008", fromIndex);
            int next009 = xml.indexOf("pacs.009", fromIndex);
            int next = -1;
            String prefix = null;
            if (next008 >= 0 && (next009 < 0 || next008 <= next009)) { next = next008; prefix = "pacs.008"; }
            else if (next009 >= 0) { next = next009; prefix = "pacs.009"; }
            if (next < 0 || prefix == null) break;
            int open = xml.lastIndexOf('<', next);
            if (open < fromIndex) { fromIndex = next + 1; continue; }
            int tagEnd = xml.indexOf('>', open);
            if (tagEnd == -1) break;
            String openTag = xml.substring(open, tagEnd);
            if (!openTag.contains("Document")) { fromIndex = next + 1; continue; }
            String tagName = openTag.substring(1).trim().split("\\s+")[0];
            if (!tagName.endsWith("Document")) { fromIndex = next + 1; continue; }
            String endTag = "</" + tagName + ">";
            int end = xml.indexOf(endTag, open);
            if (end == -1) break;
            String fragment = xml.substring(open, end + endTag.length());
            if (!fragment.contains("CdtTrfTxInf")) {
                fromIndex = next + 1;
                continue;
            }
            out.add(fragment);
            fromIndex = end + endTag.length();
        }
        return out;
    }

    /**
     * Fallback : extrait le premier bloc &lt;...Document...&gt; dont le namespace contient nsPrefix.
     * Cherche la balise fermante par comptage de profondeur, en ignorant commentaires et CDATA.
     */
    static String extractPacsDocumentByRegex(String xml, String nsPrefix) {
        if (xml == null || nsPrefix == null) return null;
        int idx = 0;
        while (true) {
            int start = xml.indexOf(nsPrefix, idx);
            if (start == -1) return null;
            int open = xml.lastIndexOf('<', start);
            if (open == -1) { idx = start + 1; continue; }
            int tagEnd = xml.indexOf('>', open);
            if (tagEnd == -1) return null;
            String openTag = xml.substring(open, tagEnd);
            if (!openTag.contains("Document")) { idx = start + 1; continue; }
            String tagName = openTag.substring(1).trim().split("\\s+")[0];
            if (!tagName.endsWith("Document")) { idx = start + 1; continue; }
            String endTag = "</" + tagName + ">";
            int end = findMatchingEndTagByDepth(xml, open, tagEnd + 1, tagName, endTag);
            if (end == -1) return null;
            return xml.substring(open, end + endTag.length());
        }
    }

    /**
     * Trouve la balise fermante correspondante en comptant la profondeur des balises
     * ouvrantes/fermantes du même nom, et en ignorant commentaires et CDATA.
     */
    private static int findMatchingEndTagByDepth(String xml, int open, int searchStart, String tagName, String endTag) {
        String openStart = "<" + tagName;
        int depth = 1;
        int pos = searchStart;
        while (pos < xml.length() && depth > 0) {
            int nextLt = xml.indexOf('<', pos);
            if (nextLt == -1) return -1;
            pos = nextLt;
            if (isInsideComment(xml, open, pos) || isInsideCdata(xml, open, pos)) {
                pos++;
                continue;
            }
            if (xml.regionMatches(nextLt, endTag, 0, endTag.length())) {
                depth--;
                if (depth == 0) return nextLt;
                pos = nextLt + endTag.length();
                continue;
            }
            if (xml.regionMatches(nextLt, openStart, 0, openStart.length())) {
                int after = nextLt + openStart.length();
                if (after < xml.length()) {
                    char c = xml.charAt(after);
                    if (c == '>' || c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                        depth++;
                    }
                }
                pos = nextLt + 1;
                continue;
            }
            pos++;
        }
        return -1;
    }

    /** true si pos est à l'intérieur d'un commentaire <!-- ... --> (après from). */
    private static boolean isInsideComment(String xml, int from, int pos) {
        int commentStart = xml.lastIndexOf("<!--", pos);
        if (commentStart < from) return false;
        int commentEnd = xml.indexOf("-->", commentStart);
        return commentEnd > 0 && pos >= commentStart && pos < commentEnd + 3;
    }

    /** true si pos est à l'intérieur d'un bloc <![CDATA[ ... ]]> (après from). */
    private static boolean isInsideCdata(String xml, int from, int pos) {
        int cdataStart = xml.lastIndexOf("<![CDATA[", pos);
        if (cdataStart < from) return false;
        int cdataEnd = xml.indexOf("]]>", cdataStart);
        return cdataEnd > 0 && pos >= cdataStart && pos < cdataEnd + 3;
    }

    /**
     * Extrait le premier élément Document dont le namespace contient pacs.008 ou pacs.009.
     */
    static String extractPacsDocumentFromSaaBody(String xml, String nsPrefix) {
        if (xml == null || nsPrefix == null) return null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setIgnoringElementContentWhitespace(false);
            try {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            } catch (Exception ignored) { }
            Document doc = factory.newDocumentBuilder().parse(
                    new InputSource(new StringReader(xml)));
            Element root = doc.getDocumentElement();
            if (root == null) return null;
            Node found = findDocumentElement(root, nsPrefix);
            if (found == null) return null;
            return nodeToString(found);
        } catch (Exception e) {
            return null;
        }
    }

    private static Node findDocumentElement(Node node, String nsPrefix) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node c = findDocumentElement(children.item(i), nsPrefix);
                if (c != null) return c;
            }
            return null;
        }
        Element el = (Element) node;
        String localName = el.getLocalName();
        String ns = el.getNamespaceURI();
        if ("Document".equals(localName) && ns != null && ns.contains(nsPrefix))
            return node;
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node c = findDocumentElement(children.item(i), nsPrefix);
            if (c != null) return c;
        }
        return null;
    }

    private static String nodeToString(Node node) throws Exception {
        StringWriter w = new StringWriter();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "no");
        t.transform(new DOMSource(node), new StreamResult(w));
        return w.toString();
    }
}

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

public class CfdiReader {

    private String uri; // URI del archivo XML

    public CfdiReader(String uri) {
        this.uri = uri;
    }

    public String[] getAttributes() throws Exception {
        // Crea un DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parsea el archivo XML
        Document document = builder.parse(uri);

        // Explora el árbol de nodos y obtén los atributos específicos
        return exploreNode(document.getDocumentElement());
    }

    private String[] exploreNode(Node node) {
        // Lista para almacenar los atributos específicos encontrados
        StringBuilder attributesList = new StringBuilder();

        // Si el nodo actual es un elemento, obtén sus atributos
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            // Obtiene todos los atributos del elemento
            NamedNodeMap attributes = element.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                String attributeName = attribute.getNodeName();
                String attributeValue = attribute.getNodeValue();

                // Filtra los atributos específicos
                if (isValidAttribute(attributeName)) {
                    attributesList.append(attributeName)
                            .append(": ")
                            .append(attributeValue)
                            .append(", ");
                }
            }
        }

        // Explora los hijos de manera recursiva
        var childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            var childNode = childNodes.item(i);
            String[] childAttributes = exploreNode(childNode);

            // Agrega los atributos específicos encontrados en los hijos a la lista
            for (String childAttribute : childAttributes) {
                attributesList.append(childAttribute).append(", ");
            }
        }

        return attributesList.toString().isEmpty() ?
                new String[0] :
                attributesList.toString().split(", ");
    }

    private boolean isValidAttribute(String attributeName) {
        // Lista de atributos válidos
        String[] validAttributes = {"Nombre", "RFC", "Rfc", "Subtotal", "SubTotal", "Total", "Fecha"};

        // Valida si el atributo está en la lista
        for (String validAttribute : validAttributes) {
            if (attributeName.equals(validAttribute)) {
                return true;
            }
        }
        return false;
    }
}

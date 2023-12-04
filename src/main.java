
public class main {

    public static void main(String[] args) {
        // Ruta del archivo XML
        String xmlFilePath = "/home/isaacpc/Downloads/RARZ630709CK2_CFDI_T132299_20231130.xml"; // Reemplaza con la ruta correcta

        // Crea una instancia de XMLAttributeReader
        CfdiReader attributeReader = new CfdiReader(xmlFilePath);

        try {
            // Obtiene los atributos del archivo XML
            String[] attributes = attributeReader.getAttributes();

            // Imprime los atributos obtenidos
            for (String attribute : attributes) {
                System.out.println(attribute);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

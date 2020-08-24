package converter.dom;

import converter.model.Value;
import converter.model.Valute;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DomValute {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;

    @org.springframework.beans.factory.annotation.Value("${URL_CB}")
    private String url;
    @org.springframework.beans.factory.annotation.Value("${URL_CB_BY_DATE}")
    private String urlByDate;

    public DomValute() {
        createBuilder();
    }

    public LocalDate getDateFromXml(){
        Document document = null;
        try {
            document = builder.parse(url);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Node root = document.getDocumentElement();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(root.getAttributes().item(0).getTextContent(), formatter);
        return date;
    }

    public Map<Valute, Value> getAllValuteAndValueFromXml(){
        Map<Valute, Value> valuteValueMap = new HashMap<>();
        Document document = null;
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            document = builder.parse(urlByDate + LocalDate.now().format(formatter));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Node root = document.getDocumentElement();
        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(root.getAttributes().item(0).getTextContent(), formatter);
        NodeList valutes = root.getChildNodes();
        for (int i = 0; i < valutes.getLength(); i++) {
            Node valute = valutes.item(i);
            NodeList valuteProps = valute.getChildNodes();
            String[] paramsForValute = new String[5];
            for (int j = 0; j < valuteProps.getLength(); j++) {
                Node valuteProp = valuteProps.item(j);
                paramsForValute[j] = valuteProp.getTextContent();

            }
            Valute valuteResult = new Valute(paramsForValute[0], paramsForValute[1], Integer.parseInt(paramsForValute[2]), paramsForValute[3]);
            paramsForValute[4] = paramsForValute[4].replaceAll(",",".");
            Value valueResult = new Value(Double.parseDouble(paramsForValute[4]), date);
            valuteValueMap.put(valuteResult, valueResult);
        }
        return valuteValueMap;
    }

    private void createBuilder(){
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {

        }
    }

}


import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Date;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Diary {

    static int id;
    private static void addinfo(Document doc) {
        NodeList diaries = doc.getElementsByTagName("Diary");
        Element lang = (Element) diaries.item(diaries.getLength()-1);
        Scanner in = new Scanner(System.in);
        Date date = new Date();
        Element idElement = doc.createElement("id");
        idElement.appendChild(doc.createTextNode(Integer.toString(id+1)));
        lang.appendChild(idElement);

        Element titleElement = doc.createElement("title");
        System.out.println("Введите название заметки:");
        titleElement.appendChild(doc.createTextNode(in.nextLine()));
        lang.appendChild(titleElement);

        Element dateElement = doc.createElement("date");
        dateElement.appendChild(doc.createTextNode(date.toString()));
        lang.appendChild(dateElement);

        Element contentElement = doc.createElement("content");
        System.out.println("Добавьте описание:");
        contentElement.appendChild(doc.createTextNode(in.nextLine()));
        lang.appendChild(contentElement);
    }

    private static void add(Document doc) {
        NodeList diaries = doc.getElementsByTagName("diaries");
        Element lang = (Element) diaries.item(0);
        Element DairyElement = doc.createElement("Diary");
        lang.appendChild(DairyElement);
        addinfo(doc);
        id++;
        try {
            doc = cleardoc(doc);
            // запишем отредактированный элемент в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Diary.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML успешно изменен!");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static void delete(Document doc, int num) {
        NodeList nodes = doc.getElementsByTagName("Diary");
        int t=0;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element diary = (Element)nodes.item(i);
            // <name>
            Element name = (Element)diary.getElementsByTagName("id").item(0);
            String pName = name.getTextContent();
            if (pName.equals(Integer.toString(num))) {
                diary.getParentNode().removeChild(diary);
                t++;
            }
        }
        if (t==0){System.out.println("Нет такого номера");}
        else
            try {
                doc = cleardoc(doc);
                // запишем отредактированный элемент в файл
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("Diary.xml"));
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
                System.out.println("XML успешно изменен!");

            } catch (Exception exc) {
                exc.printStackTrace();
            }
    }

    private static void show(Document doc, int num) {
        NodeList nodes = doc.getElementsByTagName("Diary");
        int t=0;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element diary = (Element)nodes.item(i);
            // <name>
            Element name = (Element)diary.getElementsByTagName("id").item(0);
            String pName = name.getTextContent();
            if (pName.equals(Integer.toString(num))) {
                name = (Element)diary.getElementsByTagName("title").item(0);
                System.out.println("Название: "+name.getTextContent());

                name = (Element)diary.getElementsByTagName("date").item(0);
                System.out.println("Дата создания: "+name.getTextContent());

                name = (Element)diary.getElementsByTagName("content").item(0);
                System.out.println("Содержание: "+name.getTextContent());
                t++;
            }
        }
        if (t==0){System.out.println("Нет такого номера");}
    }

    private static void showall(Document doc) {
        NodeList nodes = doc.getElementsByTagName("Diary");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element diary = (Element)nodes.item(i);
            Element name;

            name = (Element)diary.getElementsByTagName("id").item(0);
            System.out.println("Номер записи: "+name.getTextContent());

            name = (Element)diary.getElementsByTagName("title").item(0);
            System.out.println("Название: "+name.getTextContent());

            name = (Element)diary.getElementsByTagName("date").item(0);
            System.out.println("Дата создания: "+name.getTextContent()+"\n");
        }
    }

    public static void change(Document doc, int num) {
        NodeList nodes = doc.getElementsByTagName("Diary");
        int t=0;
        Scanner in = new Scanner(System.in);
        Date date = new Date();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element diary = (Element)nodes.item(i);
            // <name>
            Element name = (Element)diary.getElementsByTagName("id").item(0);
            String pName = name.getTextContent();
            if (pName.equals(Integer.toString(num))) {

                name = (Element)diary.getElementsByTagName("title").item(0);
                System.out.println("Введите новое название заметки:");
                name.setTextContent(in.nextLine());

                name = (Element)diary.getElementsByTagName("date").item(0);
                name.setTextContent(date.toString());

                name = (Element)diary.getElementsByTagName("content").item(0);
                System.out.println("Измените содержание:");
                name.setTextContent(in.nextLine());
                t++;
            }
        }
        if (t==0)System.out.println("Нет такого номера");
        else
            try {
                doc = cleardoc(doc);
                // запишем отредактированный элемент в файл
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("Diary.xml"));
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
                System.out.println("XML успешно изменен!");

            } catch (Exception exc) {
                exc.printStackTrace();
            }

    }

    public static void create (){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("diaries");
            doc.appendChild(rootElement);
            doc = cleardoc(doc);
            //создаем объект TransformerFactory для печати в консоль
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // для красивого вывода в консоль

            DOMSource source = new DOMSource(doc);
            //печатаем в консоль или файл
            StreamResult file = new StreamResult(new File("Diary.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, file);
            System.out.println("Создание XML файла закончено");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Document cleardoc(Document doc) {
        String str = toString(doc);
        str = str.replaceAll("\\s+", " ");
        doc = convertStringToDocument(str);
        return doc;
    }

    public static void getid(Document doc){
        try {
            NodeList nodes = doc.getElementsByTagName("Diary");

            Element diary = (Element)nodes.item(nodes.getLength()-1);
            Element name = (Element)diary.getElementsByTagName("id").item(0);
            String pName = name.getTextContent();
            id=Integer.parseInt(pName);
        }
        catch (Exception e){id=0;}
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int p;
        do {
            System.out.println("-------------------------------------");
            System.out.println("1-Создать новый файл\n" + "2-Добавить запись\n"+"3-Просмотреть все записи\n"+"4-Открыть запись\n"+"5-Изменить запись\n"+"6-Удалить запись\n" + "7-Выйти из программы");
            System.out.print("Ввод: ");
            p = in.nextInt();
            System.out.println("-------------------------------------");
            switch (p) {
                case (1):
                    create();
                    break;
                case (2):
                    try {
                        String filePath = "Diary.xml";
                        File xmlFile = new File(filePath);
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(xmlFile);
                        getid(doc);
                        add(doc);

                    } catch (Exception e) {
                        System.out.print("Файл не найден\n");
                    }
                    break;
                case (3):
                    try {
                        String filePath = "Diary.xml";
                        File xmlFile = new File(filePath);
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(xmlFile);
                        getid(doc);
                        if (id==0)System.out.print("Записей нет\n");
                        else showall(doc);

                    } catch (Exception e) {
                        System.out.print("Файл не найден\n");
                    }

                    break;
                case (4):
                    try {

                        String filePath = "Diary.xml";
                        File xmlFile = new File(filePath);
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(xmlFile);
                        System.out.print("Введите номер записи, которую хотите открыть: ");
                        int num = in.nextInt();
                        getid(doc);
                        show(doc,num);

                    } catch (Exception e) {
                        System.out.print("Файл не найден\n");
                    }

                    break;
                case (5):
                    try {

                        String filePath = "Diary.xml";
                        File xmlFile = new File(filePath);
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(xmlFile);
                        System.out.print("Введите номер записи, которую хотите изменить: ");
                        int num = in.nextInt();
                        getid(doc);
                        change(doc,num);

                    } catch (Exception e) {
                        System.out.print("Файл не найден\n");
                    }
                    break;
                case (6):
                    try {

                        String filePath = "Diary.xml";
                        File xmlFile = new File(filePath);
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(xmlFile);
                        System.out.print("Введите номер записи, которую хотите удалить: ");
                        int num = in.nextInt();
                        getid(doc);
                        delete(doc,num);

                    } catch (Exception e) {
                        System.out.print("Файл не найден\n");
                    }
                    break;
            }
        }
        while (p!=7);
    }
}

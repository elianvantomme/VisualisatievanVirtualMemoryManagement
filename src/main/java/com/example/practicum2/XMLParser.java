package com.example.practicum2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLParser {

    private ArrayList<Instruction> instructionLijst;
    private String file;


    public XMLParser(String file) {
        this.instructionLijst = new ArrayList<>();
        this.file = file;
    }

    public ArrayList<Instruction> readProcesses() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(file));

            document.getDocumentElement().normalize();

            NodeList processList = document.getElementsByTagName("instruction");
            for (int i = 0; i < processList.getLength(); i++) {
                Node processNode = processList.item(i);

                if (processNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList processParams = processNode.getChildNodes();

                    Instruction instruction = new Instruction();
                    for (int j = 0; j < processParams.getLength(); j++) {
                        Node param = processParams.item(j);

                        if (param.getNodeType() == Node.ELEMENT_NODE) {
                            Element detailElement = (Element) param;
                            if (detailElement.getNodeName().equals("processID")){
                                instruction.setpId(Integer.parseInt(detailElement.getTextContent()));
                            }else if (detailElement.getNodeName().equals("operation")){
                                instruction.setOperation(detailElement.getTextContent());
                            }else{
                                instruction.setVirtualAddress(Integer.parseInt(detailElement.getTextContent()));
                            }
                        }
                    }
                    instructionLijst.add(instruction);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        return instructionLijst;

    }
}


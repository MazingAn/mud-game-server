package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.messages.PayFinishMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worlddata.db.models.supermodel.BaseObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {


    @PostMapping(value = "/weChatPay/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterByName("符守玑");

    }

    @PostMapping(value = "/weChatPay/weChatPayNotify")
    public void weChatPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("111111111111111111111111111111111111");

        //支付状态
        String code = null;
        //支付描述
        String msg = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        Map<String, String> resultMap = xmlToMap(stringBuffer.toString());
        String playerId = resultMap.get("playerId");
        String status = resultMap.get("status");
        String amount = resultMap.get("amount");
        System.out.println("playerId-----------------：       " + playerId);
        System.out.println("status-----------------：       " + status);
        System.out.println("amount-----------------：       " + amount);
        PlayerCharacter playerCharacter = MongoMapper.playerCharacterRepository.findPlayerCharacterById(playerId);
        if (status.equals("SUCCESS")) {
            System.out.println("SUCCESS");
            PlayerCharacterManager.receiveObjectToBagpack(playerCharacter, "OBJECT_YUANBAO", 1);
           // playerCharacter.msg(new PayFinishMessage(0, Integer.parseInt(amount)));
        } else {
            System.out.println("FAIL");
           //playerCharacter.msg(new PayFinishMessage(1));
        }
        //返回参数
        System.out.println("----------------返回参数-----------------：       " );

        Map<String, String> param = new HashMap();//创建参数
        param.put("return_code", "SUCCESS");
        String xmlStr = mapToXml(param);
        System.out.println("xmlStr-----------------：       " + xmlStr);
        response.getWriter().write(xmlStr);
    }

    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        Iterator var5 = data.keySet().iterator();

        while (var5.hasNext()) {
            String key = (String) var5.next();
            String value = (String) data.get(key);
            if (value == null) {
                value = "";
            }

            value = value.trim();
            Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty("indent", "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();

        try {
            writer.close();
        } catch (Exception var12) {
        }

        return output;
    }

    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        Map<String, String> data = new HashMap();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
        Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == 1) {
                Element element = (Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }

        try {
            stream.close();
        } catch (Exception var10) {
        }

        return data;
    }
}

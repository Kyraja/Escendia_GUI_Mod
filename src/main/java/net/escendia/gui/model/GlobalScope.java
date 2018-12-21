package net.escendia.gui.model;

import net.escendia.ioc.Singleton;

@Singleton
public class GlobalScope {

    public static final String PATH = "mods/Escendia_GUI_MOD/";


    public GlobalScope(){}

    public static final String ESCENDIA_CHANNEL = "ESCENDIACHANNEL";



    public static final int PACKET_CLIENT_CONNECTION_INIT = 10000001;
    public static final int PACKET_CLIENT_CONNECTION_CLOSE = 10000002;
    public static final int PACKET_CLIENT_GUI_CREATE = 10000003;
    public static final int PACKET_CLIENT_GUI_DELETE = 10000004;
    public static final int PACKET_CLIENT_COMPONENT_ADD = 10000005;
    public static final int PACKET_CLIENT_COMPONENT_REMOVE = 10000006;
    public static final int PACKET_CLIENT_COMPONENT_UPDATE = 10000007;
    public static final int PACKET_CLIENT_EVENT_ADD = 10000008;

    public static final int PACKET_CLIENT_EVENT_POST_ONCLICK = 10000100;
    public static final int PACKET_CLIENT_EVENT_POST_ONMOUSELEAVE = 10000101;
    public static final int PACKET_CLIENT_EVENT_POST_ONMOUSEENTER = 10000102;
    public static final int PACKET_CLIENT_EVENT_POST_ONMOUSEBUTTONUP = 10000103;
    public static final int PACKET_CLIENT_EVENT_POST_ONMOUSEBUTTONDOWN = 10000104;
    public static final int PACKET_CLIENT_EVENT_POST_ONDOUBLECLICK = 10000105;
    public static final int PACKET_CLIENT_EVENT_POST_ONKEYPRESSED = 10000106;
    public static final int PACKET_CLIENT_EVENT_POST_ONINPUT = 10000107;
    public static final int PACKET_CLIENT_EVENT_POST_ONPASTE = 10000108;
    public static final int PACKET_CLIENT_EVENT_POST_ONCOPY = 10000109;
    public static final int PACKET_CLIENT_EVENT_POST_ONREMOVE = 10000110;
    public static final int PACKET_CLIENT_EVENT_POST_ONVALUECHANGE = 10000111;
    public static final int PACKET_CLIENT_EVENT_POST_ONFOCUS = 10000112;
    public static final int PACKET_CLIENT_EVENT_POST_ONHOVER =10000113;


    public static final int PACKET_SERVER_CONNECTION_INIT = 50000001;
    public static final int PACKET_SERVER_CONNECTION_CLOSE = 50000002;
    public static final int PACKET_SERVER_GUI_CREATE = 50000003;
    public static final int PACKET_SERVER_GUI_DELETE = 50000004;
    public static final int PACKET_SERVER_COMPONENT_ADD = 50000005;
    public static final int PACKET_SERVER_COMPONENT_REMOVE = 50000006;
    public static final int PACKET_SERVER_COMPONENT_UPDATE = 50000007;
    public static final int PACKET_SERVER_EVENT_ADD = 50000008;
    public static final int PACKET_SERVER_EVENT_POST = 50000009;

    public static final int PACKET_SERVER_EVENT_POST_ONCLICK = 50000100;
    public static final int PACKET_SERVER_EVENT_POST_ONMOUSELEAVE = 50000101;
    public static final int PACKET_SERVER_EVENT_POST_ONMOUSEENTER = 50000102;
    public static final int PACKET_SERVER_EVENT_POST_ONMOUSEBUTTONUP = 50000103;
    public static final int PACKET_SERVER_EVENT_POST_ONMOUSEBUTTONDOWN = 50000104;
    public static final int PACKET_SERVER_EVENT_POST_ONDOUBLECLICK = 50000105;
    public static final int PACKET_SERVER_EVENT_POST_ONKEYPRESSED = 50000106;
    public static final int PACKET_SERVER_EVENT_POST_ONINPUT = 50000107;
    public static final int PACKET_SERVER_EVENT_POST_ONPASTE = 50000108;
    public static final int PACKET_SERVER_EVENT_POST_ONCOPY = 50000109;
    public static final int PACKET_SERVER_EVENT_POST_ONREMOVE = 50000110;
    public static final int PACKET_SERVER_EVENT_POST_ONVALUECHANGE = 50000111;
    public static final int PACKET_SERVER_EVENT_POST_ONFOCUS = 50000112;
    public static final int PACKET_SERVER_EVENT_POST_ONHOVER =50000113;


    public static final String BODY = "body";
    public static final String PACKET_ID = "packetId";
    public static final String UUID = "uuid";

    public static final String GUI = "gui";

    public static final String ELEMENT = "element";
    public static final String ELEMENT_UUID = "elementUUID";
    public static final String EVENT = "event";

    public static final String INITDATA = "initdata";

    public static final String BUTTON = "button";
    public static final String KEY_PRESSED_LIST = "keypressedlist";
    public static final String INPUT = "input";


    public final String R = "r";
    public final String G = "g";
    public final String B = "b";
    public final String A = "a";
    public final String URL = "url";
    public final String NAME = "name";
    public final String FONTS = "fonts";
    public final String IMAGES = "images";
    public final String FONTS_TO_GENERATE = "fontsToGenerate";

    public final String LIST = "list";

}

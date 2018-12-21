package net.escendia.gui.model.factories;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ModelFactoryTest {

//    @Test
//    void elementFromJson() {
//        //String json = "{\"elementUUID\":\"d5141c55-3d70-4e1c-b8a9-3f68d5140d0c\",\"childrenElements\":{},\"visibility\":\"VISIBLE\",\"form\":{\"formStyle\":{\"margin\":{\"top\":0.0,\"left\":0.0,\"right\":0.0,\"bottom\":0.0},\"border\":{\"top\":0.0,\"left\":0.0,\"right\":0.0,\"bottom\":0.0,\"topColor\":{\"value\":0,\"falpha\":0.0},\"leftColor\":{\"value\":0,\"falpha\":0.0},\"rightColor\":{\"value\":0,\"falpha\":0.0},\"bottomColor\":{\"value\":0,\"falpha\":0.0}},\"padding\":{\"top\":0.0,\"left\":0.0,\"right\":0.0,\"bottom\":0.0},\"xPosition\":100.0,\"yPosition\":100.0,\"xOffset\":0.0,\"yOffset\":0.0,\"height\":100.0,\"width\":100.0,\"backgroundColorRed\":255,\"backgroundColorGreen\":255,\"backgroundColorBlue\":0,\"backgroundColorAlpha\":255,\"fontSize\":5,\"fontColorRed\":0,\"fontColorGreen\":0,\"fontColorBlue\":0,\"fontColorAlpha\":0,\"alignment\":\"LEFT\",\"cursorColorRed\":0,\"cursorColorGreen\":0,\"cursorColorBlue\":0,\"cursorColorAlpha\":0},\"formClass\":\"class net.escendia.gui.model.form.style.impl.RectangleStandard\"},\"elementStatus\":\"NORMAL\",\"elementClass\":\"class net.escendia.gui.model.components.impl.Div\",\"mouseOverLastUpdate\":false}";
//        String json ="{\n" +
//                "  \"elementUUID\": \"26118f57-7882-4443-a550-22f2722dd7aa\",\n" +
//                "  \"childrenElements\": {\n" +
//                "    \"959fa682-670c-47ed-9a85-4f50825d64ea\": {\n" +
//                "      \"text\": \"lel\",\n" +
//                "      \"elementUUID\": \"959fa682-670c-47ed-9a85-4f50825d64ea\",\n" +
//                "      \"childrenElements\": {},\n" +
//                "      \"visibility\": \"VISIBLE\",\n" +
//                "      \"form\": {\n" +
//                "        \"borderTopPositions\": [\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ]\n" +
//                "        ],\n" +
//                "        \"borderBottomPositions\": [\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ]\n" +
//                "        ],\n" +
//                "        \"borderRightPositions\": [\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ]\n" +
//                "        ],\n" +
//                "        \"borderLeftPositions\": [\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ],\n" +
//                "          [\n" +
//                "            0.0,\n" +
//                "            0.0\n" +
//                "          ]\n" +
//                "        ],\n" +
//                "        \"formStyle\": {\n" +
//                "          \"margin\": {\n" +
//                "            \"top\": 0.0,\n" +
//                "            \"left\": 0.0,\n" +
//                "            \"right\": 0.0,\n" +
//                "            \"bottom\": 0.0\n" +
//                "          },\n" +
//                "          \"border\": {\n" +
//                "            \"top\": 0.0,\n" +
//                "            \"left\": 0.0,\n" +
//                "            \"right\": 0.0,\n" +
//                "            \"bottom\": 0.0,\n" +
//                "            \"topColor\": {\n" +
//                "              \"value\": 0,\n" +
//                "              \"falpha\": 0.0\n" +
//                "            },\n" +
//                "            \"leftColor\": {\n" +
//                "              \"value\": 0,\n" +
//                "              \"falpha\": 0.0\n" +
//                "            },\n" +
//                "            \"rightColor\": {\n" +
//                "              \"value\": 0,\n" +
//                "              \"falpha\": 0.0\n" +
//                "            },\n" +
//                "            \"bottomColor\": {\n" +
//                "              \"value\": 0,\n" +
//                "              \"falpha\": 0.0\n" +
//                "            }\n" +
//                "          },\n" +
//                "          \"padding\": {\n" +
//                "            \"top\": 0.0,\n" +
//                "            \"left\": 0.0,\n" +
//                "            \"right\": 0.0,\n" +
//                "            \"bottom\": 0.0\n" +
//                "          },\n" +
//                "          \"xPosition\": 100.0,\n" +
//                "          \"yPosition\": 100.0,\n" +
//                "          \"xOffset\": 0.0,\n" +
//                "          \"yOffset\": 0.0,\n" +
//                "          \"height\": 100.0,\n" +
//                "          \"width\": 100.0,\n" +
//                "          \"backgroundColorRed\": 255,\n" +
//                "          \"backgroundColorGreen\": 255,\n" +
//                "          \"backgroundColorBlue\": 255,\n" +
//                "          \"backgroundColorAlpha\": 0,\n" +
//                "          \"fontSize\": 5,\n" +
//                "          \"fontColorRed\": 0,\n" +
//                "          \"fontColorGreen\": 0,\n" +
//                "          \"fontColorBlue\": 0,\n" +
//                "          \"fontColorAlpha\": 0,\n" +
//                "          \"alignment\": \"LEFT\",\n" +
//                "          \"cursorColorRed\": 0,\n" +
//                "          \"cursorColorGreen\": 0,\n" +
//                "          \"cursorColorBlue\": 0,\n" +
//                "          \"cursorColorAlpha\": 0\n" +
//                "        },\n" +
//                "        \"formClass\": \"class net.escendia.gui.model.form.style.impl.RectangleStandard\"\n" +
//                "      },\n" +
//                "      \"elementStatus\": \"NORMAL\",\n" +
//                "      \"cursorType\": \"NORMAL\",\n" +
//                "      \"elementClass\": \"class net.escendia.gui.model.components.impl.Text\",\n" +
//                "      \"mouseOverLastUpdate\": false\n" +
//                "    }\n" +
//                "  },\n" +
//                "  \"visibility\": \"VISIBLE\",\n" +
//                "  \"form\": {\n" +
//                "    \"borderTopPositions\": [\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ]\n" +
//                "    ],\n" +
//                "    \"borderBottomPositions\": [\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ]\n" +
//                "    ],\n" +
//                "    \"borderRightPositions\": [\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ]\n" +
//                "    ],\n" +
//                "    \"borderLeftPositions\": [\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ],\n" +
//                "      [\n" +
//                "        0.0,\n" +
//                "        0.0\n" +
//                "      ]\n" +
//                "    ],\n" +
//                "    \"formStyle\": {\n" +
//                "      \"margin\": {\n" +
//                "        \"top\": 0.0,\n" +
//                "        \"left\": 0.0,\n" +
//                "        \"right\": 0.0,\n" +
//                "        \"bottom\": 0.0\n" +
//                "      },\n" +
//                "      \"border\": {\n" +
//                "        \"top\": 0.0,\n" +
//                "        \"left\": 0.0,\n" +
//                "        \"right\": 0.0,\n" +
//                "        \"bottom\": 0.0,\n" +
//                "        \"topColor\": {\n" +
//                "          \"value\": 0,\n" +
//                "          \"falpha\": 0.0\n" +
//                "        },\n" +
//                "        \"leftColor\": {\n" +
//                "          \"value\": 0,\n" +
//                "          \"falpha\": 0.0\n" +
//                "        },\n" +
//                "        \"rightColor\": {\n" +
//                "          \"value\": 0,\n" +
//                "          \"falpha\": 0.0\n" +
//                "        },\n" +
//                "        \"bottomColor\": {\n" +
//                "          \"value\": 0,\n" +
//                "          \"falpha\": 0.0\n" +
//                "        }\n" +
//                "      },\n" +
//                "      \"padding\": {\n" +
//                "        \"top\": 0.0,\n" +
//                "        \"left\": 0.0,\n" +
//                "        \"right\": 0.0,\n" +
//                "        \"bottom\": 0.0\n" +
//                "      },\n" +
//                "      \"xPosition\": 100.0,\n" +
//                "      \"yPosition\": 100.0,\n" +
//                "      \"xOffset\": 0.0,\n" +
//                "      \"yOffset\": 0.0,\n" +
//                "      \"height\": 100.0,\n" +
//                "      \"width\": 100.0,\n" +
//                "      \"backgroundColorRed\": 255,\n" +
//                "      \"backgroundColorGreen\": 255,\n" +
//                "      \"backgroundColorBlue\": 0,\n" +
//                "      \"backgroundColorAlpha\": 255,\n" +
//                "      \"fontSize\": 5,\n" +
//                "      \"fontColorRed\": 0,\n" +
//                "      \"fontColorGreen\": 0,\n" +
//                "      \"fontColorBlue\": 0,\n" +
//                "      \"fontColorAlpha\": 0,\n" +
//                "      \"alignment\": \"LEFT\",\n" +
//                "      \"cursorColorRed\": 0,\n" +
//                "      \"cursorColorGreen\": 0,\n" +
//                "      \"cursorColorBlue\": 0,\n" +
//                "      \"cursorColorAlpha\": 0\n" +
//                "    },\n" +
//                "    \"formClass\": \"class net.escendia.gui.model.form.style.impl.RectangleStandard\"\n" +
//                "  },\n" +
//                "  \"elementStatus\": \"NORMAL\",\n" +
//                "  \"cursorType\": \"NORMAL\",\n" +
//                "  \"elementClass\": \"class net.escendia.gui.model.components.impl.Div\",\n" +
//                "  \"mouseOverLastUpdate\": false\n" +
//                "}";
//        Element element = new Element().fromJson(json);
//        assertNotEquals(element,null);
//    }

    @Test
    void getFormByClass() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        ClassLoader classLoader = this.getClass().getClassLoader();

        Class loadedMyClass = classLoader.loadClass("com.jcg.MyClass");

        System.out.println("Loaded class name: " + loadedMyClass.getName());

        // Create a new instance from the loaded class

        Constructor constructor = loadedMyClass.getConstructor();

        Object myClassObject = constructor.newInstance();



        // Getting the target method from the loaded class and invoke it using its name

        Method method = loadedMyClass.getMethod("sayHello1");

        System.out.println("Invoked method name: " + method.getName());

        method.invoke(myClassObject);
//        String className = "mypackage.MyClass";
//        String javaCode = "package mypackage;\n" +
//                "public class MyClass implements Runnable {\n" +
//                "    public void run() {\n" +
//                "        System.out.println(\"Hello World\");\n" +
//                "    }\n" +
//                "}\n";
//        Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
//        Runnable runner = (Runnable) aClass.newInstance();
//        runner.run();

    }

    @Test
    void eventFromString() {
    }
}
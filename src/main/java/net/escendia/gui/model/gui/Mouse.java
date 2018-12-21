package net.escendia.gui.model.gui;

import net.escendia.gui.model.GlobalScope;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.Inject;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Cursor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.IntBuffer;

@Singleton
public class Mouse  {

    @Inject
    public EscendiaLogger logger;

    private final Cursor normalCursor;
    private final Cursor grabCursor;
    private final Cursor textCursor;

    private CursorType currentCursorType = CursorType.NORMAL;

    //Current values
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean middlePressed = false;

    private int mouseXPosition = 0;
    private int mouseYPosition = 0;

    private long timeLastClick = Long.MAX_VALUE;

    private final long timeDoubleClick = 255;

    //Art der Clicks
    private boolean click = false;
    private boolean doubleClick = false;

    //Previous Values

    private boolean leftPressedPrevious = false;
    private boolean rightPressedPrevious = false;
    private boolean middlePressedPrevious = false;

    private int mouseXPositionPrevious = 0;
    private int mouseYPositionPrevious = 0;



    public Mouse() {
        logger = InversionOfControl.get().build(EscendiaLogger.class);
        normalCursor = loadCursor("cursor.png", 7, 7);
        grabCursor = loadCursor("drag.png", 7, 7);
        textCursor = loadCursor("typing.png", 7, 7);
    }


    //------- Getter Methods -------


    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isLeftPressedPrevious() {
        return leftPressedPrevious;
    }

    public int getMouseXPosition() {
        return mouseXPosition;
    }

    public int getMouseYPosition() {
        return mouseYPosition;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isRightPressedPrevious() {
        return rightPressedPrevious;
    }

    public boolean isMiddlePressed() {
        return middlePressed;
    }

    public boolean isMiddlePressedPrevious() {
        return middlePressedPrevious;
    }

    public boolean isClick() {
        return click;
    }

    public boolean isDoubleClick() {
        return doubleClick;
    }

    public boolean isGrabbed(){
        return org.lwjgl.input.Mouse.isGrabbed();
    }

    //------- Setter Methods -------
    public void setCurrentCursor(CursorType cursorType){
        try {
            if(currentCursorType!=cursorType){
                switch (cursorType) {
                    case NORMAL:
                        org.lwjgl.input.Mouse.setNativeCursor(normalCursor);
                        break;
                    case HAND:
                        org.lwjgl.input.Mouse.setNativeCursor(grabCursor);
                        break;
                    case TEXT:
                        org.lwjgl.input.Mouse.setNativeCursor(textCursor);
                        break;
                }
                currentCursorType = cursorType;
            }
        }catch (Exception ex){
            logger.error("current cursor could not be set.");
        }
    }

    //----- Methods ------

    public void update(){
        long time = System.currentTimeMillis();

        try {
            Screen screen = InversionOfControl.get().build(Screen.class);
            this.click = false;
            this.doubleClick = false;
            this.leftPressedPrevious = leftPressed;
            this.rightPressedPrevious = rightPressed;
            this.middlePressedPrevious = middlePressed;
            this.leftPressed = org.lwjgl.input.Mouse.isButtonDown(0);
            this.rightPressed = org.lwjgl.input.Mouse.isButtonDown(1);
            this.middlePressed = org.lwjgl.input.Mouse.isButtonDown(2);
            this.mouseXPositionPrevious = mouseXPosition;
            this.mouseYPositionPrevious = mouseYPosition;
            this.mouseXPosition = org.lwjgl.input.Mouse.getX() / screen.getScaleFactor();
            this.mouseYPosition = screen.getHeight() - org.lwjgl.input.Mouse.getY() / screen.getScaleFactor();

            if(time>=timeLastClick){
                timeLastClick = Long.MAX_VALUE;
            }

            if(isLeftPressed() && !isLeftPressedPrevious()){
                long timeWithDoubleClick = time+timeDoubleClick;
                click = true;

                if(timeWithDoubleClick >= timeLastClick){
                    timeLastClick = Long.MAX_VALUE;
                    doubleClick = true;
                }else{
                    timeLastClick = timeWithDoubleClick;
                }
            }
        }catch (Exception ex){
            logger.error("Error while updating Mouse", ex);
        }


        
    }


    //----- load Methods ----


    private org.lwjgl.input.Cursor loadCursor(String name, int xHotSpot, int yHotSpot){
        try {
            File tmpFile = new File(".");
            BufferedImage bufferedImage = ImageIO.read( new FileInputStream(tmpFile.getAbsolutePath().substring(0, tmpFile.getAbsolutePath().length()-1) + GlobalScope.PATH + name));
            IntBuffer ib = BufferUtils.createIntBuffer(bufferedImage.getWidth() * bufferedImage.getHeight());
            int i = 0;

            for(int y = bufferedImage.getHeight()-1; 0 <= y; y--){
                for(int x = 0; x < bufferedImage.getWidth(); x++){
                    ib.put(i, bufferedImage.getRGB(x, y));
                    i++;
                }
            }

            return new org.lwjgl.input.Cursor(bufferedImage.getWidth(), bufferedImage.getHeight(), xHotSpot, yHotSpot, 1, ib, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public enum CursorType{NORMAL,HAND,TEXT}

    public enum Button {LEFT, MIDDLE, RIGHT}
}

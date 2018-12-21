package net.escendia.gui.model.logger;

import net.escendia.gui.controll.FileService;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Date;

@Singleton
public class EscendiaLogger {

    private Level level = Level.INFO;
    private PrintWriter stream = null;
    private String tag = "";
    private boolean fileExport = true;

    public void setLevel(Level l) {
        level = l;
    }

    public void setPrintWriter(PrintWriter f) {
        stream = f;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public EscendiaLogger(){}

    public EscendiaLogger(String tag, Level level){
        this.tag = tag;
        this.level = level;
    }


    public void error(String m) {
        if (Level.ERROR.compareTo(level) <= 0) {
            format("ERROR", m);
        }
    }

    public void error(String m, Throwable e) {
        if (Level.ERROR.compareTo(level) <= 0) {
            StringBuilder sb = new StringBuilder(1024);
            sb.append(m);
            sb.append(ExceptionUtils.getStackTrace(e));

            format("ERROR", sb.toString());
        }
    }

    public void warn(String m) {

        if (Level.WARNING.compareTo(level) <= 0) {
            format("WARNING", m);
        }
    }

    public void warn(String m, Throwable e) {
        if (Level.WARNING.compareTo(level) <= 0) {
            StringBuilder sb = new StringBuilder(1024);
            sb.append(m);
            sb.append(ExceptionUtils.getStackTrace(e));

            format("WARNING", sb.toString());
        }
    }

    public void debug(String m) {
        if (Level.DEBUG.compareTo(level) <= 0) {
            format("DEBUG", m);
        }
    }

    public void info(String m) {
        if (Level.INFO.compareTo(level) <= 0) {
            format("INFO", m);
        }
    }

    public void trace(String m) {
        if (Level.TRACE.compareTo(level) <= 0) {
            format("TRACE", m);
        }
    }

    public void format(String type, String m) {
        StringBuilder sb = new StringBuilder(1024);
        if(tag!=""){
            sb.append("[" + tag + "]");
        }
        sb.append("[");
        sb.append(type);
        sb.append("] ");
        sb.append(new Date().toString());
        sb.append(" - ");
        sb.append(m);

        String line = sb.toString();
        System.out.println(line);
        if(fileExport)InversionOfControl.get().build(FileService.class).writeStringToFile("EscendiaGUI.log", line);
        if (stream != null) {
            stream.println(line);
            stream.flush();
        }
    }
}

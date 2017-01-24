/*==============================================================================

name:       GvLoggerGWT.java

purpose:    iWonder GWT Logger.

            Provides various logging options.

history:    Sat Jul 13, 2013 10:30:00 (Giavaneers - LBM) created.

notes:
                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers, Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2013 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.

==============================================================================*/
                                       // package --------------------------- //
package com.giavaneers.util.gwt;
                                       // imports --------------------------- //
import com.giavaneers.util.logger.GvLoggerKit.ILogger;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
                                       // GvLoggerGWT ========================//
public class GvLoggerGWT implements ILogger
{
                                       // class constants ------------------- //
private static final long   serialVersionUID = 1L;

public static final DateTimeFormat kFORMAT =
   DateTimeFormat.getFormat("dd MMM yyyy hh:mm:ss, SSS");

public static final int    kLOG_REMOTE             = 1;
public static final String kURL_PARAM_LOG_VERBOSE  = "logVerbose";
public static final String kURL_PARAM_REPORT_LOG   = "reportLog";

public static final Map<String,ILogger>
                           kLOGGER_MAP = new HashMap<String,ILogger>();

                                       // class variables ------------------- //
protected static String      report;   // log string                          //
                                       // public instance variables --------- //
                                       // protected instance variables ------ //
public    int                logLevel; // log level                           //
protected String             classname;// logger classname                    //
protected List<ILogListener> listeners;// listeners                           //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       GvLoggerGWT - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public GvLoggerGWT()
{
   logLevel =
      "true".equals (Window.Location.getParameter(
         kURL_PARAM_LOG_VERBOSE)) ? kLOG_LEVEL_INFO : kLOG_LEVEL_ERROR;

   if ("true".equals (Window.Location.getParameter(kURL_PARAM_REPORT_LOG)))
   {
      if (report == null)
      {
         report = "";
      }
   }
}
/*------------------------------------------------------------------------------

@name       GvLoggerGWT - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public GvLoggerGWT(
   String classname) 
{
   this();
   this.classname = classname;
   kLOGGER_MAP.put(classname, this);
}
/*------------------------------------------------------------------------------

@name       addListener - add log listener
                                                                              */
                                                                             /**
            Add log listener.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void addListener(
   ILogListener listener)
{
                                       // remove any previous                 //
   getListeners().clear();
   getListeners().add(listener);
}
/*------------------------------------------------------------------------------

@name       getClassname - get classname
                                                                              */
                                                                             /**
            Get classname.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public String getClassname()
{
   return(classname);
}
/*------------------------------------------------------------------------------

@name       getListeners - get log listeners
                                                                              */
                                                                             /**
            Get log listeners.

@return     listeners

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public List<ILogListener> getListeners()
{
   if (listeners == null)
   {
      listeners = new ArrayList<ILogListener>();
   }
   return(listeners);
}
/*------------------------------------------------------------------------------

@name       getLoggerMap - get logger map
                                                                              */
                                                                             /**
            Get logger map.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Map<String,ILogger> getLoggerMap()
{
   return(kLOGGER_MAP);
}
/*------------------------------------------------------------------------------

@name       getLogLevel - get logLevel
                                                                              */
                                                                             /**
            Get log level.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public int getLogLevel()
{
   return(logLevel);
}
/*------------------------------------------------------------------------------

@name       getReport - get current stack trace
                                                                              */
                                                                             /**
            Get current stack trace. 

@return     current stack trace. 

@history    Mon May 19, 2014 18:00:00 (LBM) created. 

@notes      
                                                                              */
//------------------------------------------------------------------------------
public static String getReport()
{
   return(report);
}
/*------------------------------------------------------------------------------

@name       getStackTrace - get current stack trace
                                                                              */
                                                                             /**
            Get current stack trace.

@return     current stack trace.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public String getStackTrace()
{
   String stackTrace = "";
   try
   {
      throw new Throwable();
   }
   catch (Throwable dummy)
   {
      stackTrace = getStackTrace(dummy);
   }
   return(stackTrace);
}
/*------------------------------------------------------------------------------

@name       getStackTrace - get current stack trace
                                                                              */
                                                                             /**
            Get current stack trace. 

@return     current stack trace. 

@history    Mon May 19, 2014 18:00:00 (LBM) created. 

@notes      
                                                                              */
//------------------------------------------------------------------------------
public String getStackTrace(
   Throwable t)
{
   String stackTrace = t.toString() + "\n";
   StackTraceElement[] elements = t.getStackTrace();
   for (int i = 1; i < elements.length; i++)
   {
      stackTrace += elements[i].toString() + "\n";
   }
   
   Throwable cause = t.getCause();
   if (cause != null)
   {
      stackTrace += "Caused by " + cause.toString() + "\n";
      stackTrace += getStackTrace(cause);
   }
   return(stackTrace);
}
/*------------------------------------------------------------------------------

@name       log - log 
                                                                              */
                                                                             /**
            Log.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void log(
   String msg) 
{
   log(msg, 0);
}
/*------------------------------------------------------------------------------

@name       log - log 
                                                                              */
                                                                             /**
            Log.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
protected void log(
   String msg,
   int    options) 
{
   System.out.println(msg);
   logNative(msg);
   
   if ((kLOG_REMOTE & options) != 0)
   {
      logRemote(msg, options);
   }
   if (report != null)
   {
      report += msg + "\n";
   }
}
/*------------------------------------------------------------------------------

@name       logDebug - log debug
                                                                              */
                                                                             /**
            Log debug.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logDebug(
   String msg) 
{
   if (logLevel >= kLOG_LEVEL_DEBUG)
   {
      log(kFORMAT.format(new Date()) + " DEBUG " + msg);
   }
}
/*------------------------------------------------------------------------------

@name       logDebugDetail - log debug detail
                                                                              */
                                                                             /**
            Log debug detail.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void logDebugDetail(
   String msg)
{
   if (logLevel >= kLOG_LEVEL_DEBUG_DETAIL)
   {
      logDebug(msg);
   }
}
/*------------------------------------------------------------------------------

@name       logDebugFineDetail - log debug fine detail
                                                                              */
                                                                             /**
            Log debug fine detail.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void logDebugFineDetail(
   String msg)
{
   if (logLevel >= kLOG_LEVEL_DEBUG_FINE_DETAIL)
   {
      logDebug(msg);
   }
}
/*------------------------------------------------------------------------------

@name       logError - log info
                                                                              */
                                                                             /**
            Log info.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logError(
   String msg) 
{
   logError(msg, 0);
}
/*------------------------------------------------------------------------------

@name       logError - log info
                                                                              */
                                                                             /**
            Log info.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logError(
   String msg,
   int    options) 
{
   if (logLevel >= kLOG_LEVEL_ERROR)
   {
      log(kFORMAT.format(new Date()) + " ERROR " + msg);
//    sendLogReport(ILog.kSEVERITY_ERROR, true, msg);
//    showErrorDialog(msg);
   }
}
/*------------------------------------------------------------------------------

@name       logError - log error
                                                                              */
                                                                             /**
            Log error.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logError(
   Throwable t) 
{
   logError(t, 0);
}
/*------------------------------------------------------------------------------

@name       logError - log error
                                                                              */
                                                                             /**
            Log error.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void logError(
   Throwable t,
   boolean   bPrintStackTrace)
{
   logError(null, t, bPrintStackTrace, 0);
}
/*------------------------------------------------------------------------------

@name       logError - log error
                                                                              */
                                                                             /**
            Log error.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logError(
   Throwable t,
   int       options) 
{
   logError(t.toString(), options);
}
/*------------------------------------------------------------------------------

@name       logError - log error
                                                                              */
                                                                             /**
            Log error.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logError(
   String    msg,
   Throwable t)
{
   logError(msg, t, false, 0);
}
/*------------------------------------------------------------------------------

@name       logError - log error
                                                                              */
                                                                             /**
            Log error.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void logError(
   String    msg,
   Throwable t,
   boolean   bPrintStackTrace)
{
   logError(msg, t, bPrintStackTrace, 0);
}
/*------------------------------------------------------------------------------

@name       logError - log error
                                                                              */
                                                                             /**
            Log error.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logError(
   String    msg,
   Throwable t,
   boolean   bPrintStackTrace,
   int       options) 
{
   if (msg == null)
   {
      msg = "";
   }
   
   msg += "\n" + t.toString();
   if (bPrintStackTrace)
   {
      msg += "\n" + getStackTrace(t);
   }

   logError(msg, options);
}
/*------------------------------------------------------------------------------

@name       logErrorNative - log error
                                                                              */
                                                                             /**
            Log error.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logErrorNative(
   String    msg,
   Throwable t,
   boolean   bPrintStackTrace) 
{
   if (msg == null)
   {
      msg = "";
   }
   
   msg += "\n" + t.toString();
   if (bPrintStackTrace)
   {
      msg += "\n" + getStackTrace(t);
   }

   logNative(msg);
   
   if (report != null)
   {
      report += msg + "\n";
   }
}
/*------------------------------------------------------------------------------

@name       logInfo - log info
                                                                              */
                                                                             /**
            Log info.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logInfo(
   String msg) 
{
   logInfo(msg, 0);
}
/*------------------------------------------------------------------------------

@name       logInfo - log info
                                                                              */
                                                                             /**
            Log info.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logInfo(
   String msg,
   int    options) 
{
   if (logLevel >= kLOG_LEVEL_INFO)
   {
      log(kFORMAT.format(new Date()) + " INFO  " + msg, options);
   }
}
/*------------------------------------------------------------------------------

@name       logInfo - log info
                                                                              */
                                                                             /**
            Log info.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logWarning(
   String msg) 
{
   logWarning(msg, 0);
}
/*------------------------------------------------------------------------------

@name       logError - log error
                                                                              */
                                                                             /**
            Log error.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void logWarning(
   Throwable t)
{
   logWarning(null, t, false, 0);
}
/*------------------------------------------------------------------------------

@name       logInfo - log info
                                                                              */
                                                                             /**
            Log info.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logWarning(
   String msg,
   int    options) 
{
   if (logLevel >= kLOG_LEVEL_WARNING)
   {
      log(kFORMAT.format(new Date()) + " WARN  " + msg, options);
   }
}
/*------------------------------------------------------------------------------

@name       logWarning - log warning
                                                                              */
                                                                             /**
            Log warning.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void logWarning(
   String    msg,
   Throwable t,
   boolean   bPrintStackTrace,
   int       options)
{
   if (msg == null)
   {
      msg = "";
   }

   msg += "\n" + t.toString();
   if (bPrintStackTrace)
   {
      msg += "\n" + getStackTrace(t);
   }

   logWarning(msg, options);
}
/*------------------------------------------------------------------------------

@name       logNative - log native
                                                                              */
                                                                             /**
            Log native.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public static native void logNative(
   String msg) 
/*-{
   $wnd.console.log(msg);
}-*/;
/*------------------------------------------------------------------------------

@name       logRemote - log remote 
                                                                              */
                                                                             /**
            Log remote. This implementation is null.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logRemote(
   String msg,
   int    options) 
{
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Factory method.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public static GvLoggerGWT newInstance(
   String classname) 
{
   return(new GvLoggerGWT(classname));
}
/*------------------------------------------------------------------------------

@name       newLoggerInstance - factory method
                                                                              */
                                                                             /**
            Factory method.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public ILogger newLoggerInstance(
   String classname)
{
   return(newInstance(classname));
}
/*------------------------------------------------------------------------------

@name       setLogLevel - assign logLevel
                                                                              */
                                                                             /**
            Assign log level.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void setLogLevel(
   int  logLevelNew) 
{
   logLevel = logLevelNew;
}
/*------------------------------------------------------------------------------

@name       setLogLevel - assign logLevel
                                                                              */
                                                                             /**
            Assign log level.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void setLogLevel(
   String classname,
   int    logLevel)
{
   GvLoggerGWT logger = (GvLoggerGWT)getLoggerMap().get(classname);
   if (logger != null)
   {
      logger.setLogLevel(logLevel);
   }
}
/*------------------------------------------------------------------------------

@name       showErrorDialog - shows an error dialog box
                                                                              */
                                                                             /**
            Shows an error dialog box.

@return     error dialog box

@param      errorMessage      error message

@history    Sat March 31, 2012 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public PopupPanel showErrorDialog(
   String    errorMessage)
{
   FlowPanel errorPane  = new FlowPanel();
   errorPane.getElement().getStyle().setBackgroundColor("black");
   errorPane.getElement().getStyle().setColor("white");
   HTMLPanel errorLabel = new HTMLPanel(errorMessage);
   errorPane.add(errorLabel);

   PopupPanel popup = new PopupPanel(true, true);
   popup.setGlassEnabled(true);
   popup.setAnimationEnabled(true);
   popup.setGlassStyleName("midBackground");
// errorPane.addStyleName("popup");
   popup.setWidget(errorPane);
   popup.center();
   
   return (popup);
}
/*------------------------------------------------------------------------------

@name       willLog - test logLevel value
                                                                              */
                                                                             /**
            Returns true if specified testLevel is such that logging will occur.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public boolean willLog(
   int  testLevel) 
{
   return(logLevel >= testLevel);
}
}//====================================// end GvLoggerGWT ====================//

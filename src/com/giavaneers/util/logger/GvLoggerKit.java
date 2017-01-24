/*==============================================================================

name:       GvLoggerKit.java

purpose:    Logger configurable for either GWT or java environments.

            Provides various logging options.

history:    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

notes:
                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers, Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2014 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.

==============================================================================*/
                                       // package --------------------------- //
package com.giavaneers.util.logger;
                                       // imports --------------------------- //
import java.util.Map;
                                       // GvLoggerKit ========================//
public class GvLoggerKit
{
                                       // class constants ------------------- //
private static final long serialVersionUID = 1L;

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
public static ILogger    proxy;        // proxy logger                        //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       GvLoggerKit - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
protected GvLoggerKit() 
{
}
/*------------------------------------------------------------------------------

@name       getLogger - get logger for specified classname
                                                                              */
                                                                             /**
            Get logger for specified classname.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static ILogger getLogger(
   String classname)
{
   return(proxy.getLoggerMap().get(classname));
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
   return(proxy.getLogLevel());
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
public static String getStackTrace()
{
   return(proxy.getStackTrace());
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
public static String getStackTrace(
   Throwable t)
{
   return(proxy.getStackTrace(t));
}
/*------------------------------------------------------------------------------

@name       logDebug - log debug
                                                                              */
                                                                             /**
            Log debug.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logDebug(
   String msg) 
{
   proxy.logDebug(msg);
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
   String msg) 
{
   proxy.logError(msg);
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
   Throwable t) 
{
   proxy.logError(t, false);
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
   proxy.logError(t, bPrintStackTrace);
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
   proxy.logError(msg, t);
}
/*------------------------------------------------------------------------------

@name       logInfo - log info
                                                                              */
                                                                             /**
            Log info.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes      

                                                                              */
//------------------------------------------------------------------------------
public void logInfo(
   String msg) 
{
   proxy.logInfo(msg);
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
   String msg) 
{
   proxy.logWarning(msg);
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
   Throwable t) 
{
   proxy.logWarning(t);
}
/*------------------------------------------------------------------------------

@name       newInstance - new instance
                                                                              */
                                                                             /**
            New instance.

@return     proxy

@return     classname     classname

@history    Sat Jul 09, 2016 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static ILogger newInstance(
   String classname)
{
   ILogger logger = proxy.newLoggerInstance(classname);
   proxy.getLoggerMap().put(classname, logger);

   return(logger);
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
   int  logLevel) 
{
   proxy.setLogLevel(logLevel);
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
public static void setLogLevel(
   String classname,
   int    logLevel) 
{
   proxy.setLogLevel(classname, logLevel);
}
/*------------------------------------------------------------------------------

@name       setProxy - set format proxy
                                                                              */
                                                                             /**
            Assign proxy format, typically either one suitable for GWT or one
            suitable for Java.

@param      format      proxy format

@history    Sat Jul 09, 2016 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void setProxy(
   ILogger proxyNew)
{
   proxy = proxyNew;
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
   return(proxy.willLog(testLevel));
}
/*==============================================================================

name:       ILogger - plugin logger interface

purpose:    Plugin logger interface suitable for both GWT and Java

history:    Sat Jul 09, 2016 10:30:00 (Giavaneers - LBM) created.


notes:

==============================================================================*/
public interface ILogger
{
                                       // constants ------------------------- //
public static final int kLOG_LEVEL_ALL               = 0;
public static final int kLOG_LEVEL_ERROR             = 1;
public static final int kLOG_LEVEL_WARNING           = 10;
public static final int kLOG_LEVEL_INFO              = 100;
public static final int kLOG_LEVEL_DEBUG             = 1000;
public static final int kLOG_LEVEL_DEBUG_DETAIL      = 1010;
public static final int kLOG_LEVEL_DEBUG_FINE_DETAIL = 1020;
public static final int kLOG_LEVEL_NONE              = Integer.MAX_VALUE;
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
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
   ILogListener listener);

/*------------------------------------------------------------------------------

@name       getClassname - get classname
                                                                              */
                                                                             /**
            Get classname.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public String getClassname();

/*------------------------------------------------------------------------------

@name       getLogLevel - get logLevel
                                                                              */
                                                                             /**
            Get log level.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public int getLogLevel();

/*------------------------------------------------------------------------------

@name       getLoggerMap - get logger map
                                                                              */
                                                                             /**
            Get logger map.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public Map<String,ILogger> getLoggerMap();

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
public String getStackTrace();

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
   Throwable t);

/*------------------------------------------------------------------------------

@name       logDebug - log debug
                                                                              */
                                                                             /**
            Log debug.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void logDebug(
   String msg);

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
   String msg);

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
   String msg);

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
   String msg);

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
   Throwable t);

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
   boolean   bPrintStackTrace);

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
   Throwable t);

/*------------------------------------------------------------------------------

@name       logInfo - log info
                                                                              */
                                                                             /**
            Log info.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void logInfo(
   String msg);

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
   String msg);

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
   Throwable t);

/*------------------------------------------------------------------------------

@name       newInstance - new instance for specified classname
                                                                              */
                                                                             /**
            New instance for specified classname

@return     new format instance.

@param      classname     classname

@history    Sat Jul 09, 2016 10:30:00 (Giavaneers - LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public ILogger newLoggerInstance(
   String classname);

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
   int  logLevel);

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
   int    logLevel);

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
   int  testLevel);

/*==============================================================================

name:       ILogListener - log listener interface

purpose:    Plugin logger log listener interface suitable for both GWT and Java

history:    Sat Jul 09, 2016 10:30:00 (Giavaneers - LBM) created.


notes:

==============================================================================*/
public interface ILogListener
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       log - log event handler
                                                                              */
                                                                             /**
            Log event handler.

@history    Thu Jan 30, 2014 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void log(
   ILogger logger,
   String  logLevel,
   String  message);

}//====================================// inner ILogListener =================//
}//====================================// inner ILogger ======================//
}//====================================// end GvLoggerKit ====================//

/*==============================================================================

name:       ChartWorkbench.java

purpose:    Gwt Chart Development Workbench

history:    Fri Jan 13, 20176 13:00:00 (LBM) created.

notes:

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers, Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2017 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.

==============================================================================*/
                                       // package ----------------------------//
package org.marinespacestation.project.client;
                                       // imports ----------------------------//
import com.giavaneers.util.gwt.GvLoggerGWT;
import com.giavaneers.util.logger.GvLoggerKit.ILogger;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.typedarrays.shared.Uint8Array;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import org.marinespacestation.project.client.DragDropGWT.IDropFileListener;
import org.marinespacestation.project.client.MediaGWT.IMediaChangeListener;

                                       // ChartWorkbench =====================//
public class ChartWorkbench
   implements EntryPoint, IDropFileListener, IMediaChangeListener
{
                                       // class constants --------------------//
private static final ILogger kLOGGER =
   GvLoggerGWT.newInstance(ChartWorkbench.class.getName());

                                       // class variables                     //
protected static boolean bEditMode;    // edit mode                           //
                                       // public instance variables ----------//
                                       // (none)                              //
                                       // protected instance variables -------//
protected String         sectionText;  // section text                        //
                                       // private instance variables ---------//
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       ChartWorkbench - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of ChartWorkbench if successful.

@history    Thu Dec 27, 2012 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ChartWorkbench()
{
   this.bEditMode = true;
}
/*------------------------------------------------------------------------------

@name       addChartToSection - add chart to section
                                                                              */
                                                                             /**
            Add chart to section

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes

                                                                              */
//------------------------------------------------------------------------------
public void addChartToSection(
   ChartGWT chartDsc,
   Element  sectionElem)
{
                                       // save original section text          //
   if (sectionText == null)
   {
      sectionText = sectionElem.getInnerText();
   }

   String chartContainerId =
      "chartWidgetSection"
         + chartDsc.url.substring(chartDsc.url.lastIndexOf("/") + 1);

   String chartPlaceholderId =
      chartContainerId.replace("chartWidget","chartPlaceholder");

   String mediaHTML =
      "<div data-brackets-id='67' id='" + chartContainerId
    + "' class='charts-widget top-buffer-20 bottom-buffer-20'>"
    + "   <div class='chartWithOverlay'>"
    + "      <div id='" + chartPlaceholderId + "'></div>"
    + "   </div>"
    + "</div>";

   addMediaToSection(chartDsc, mediaHTML, sectionElem);
}
/*------------------------------------------------------------------------------

@name       addDragDropHandlers - add drag drop handlers
                                                                              */
                                                                             /**
            Add drag drop handlers

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void addDragDropHandlers()
{
   Element[] targets = {Document.get().getElementById("section")};

   DragDropGWT dragDrop = new DragDropGWT();
   dragDrop.addDragDropTargets(targets);
   dragDrop.addDropFileListener(this);
}
/*------------------------------------------------------------------------------

@name       addMediaToSection - add media to section
                                                                              */
                                                                             /**
            Add media to section

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void addMediaToSection(
   MediaGWT mediaGWT,
   String   mediaHTML,
   Element  sectionElem)
{
   mediaGWT.bEditMode = getEditMode();

   String chartContainerId =
      mediaGWT instanceof ChartGWT
         ? "chartWidgetSection"
               + mediaGWT.url.substring(mediaGWT.url.lastIndexOf("/") + 1)
         : null;

   int    idx       = mediaGWT.url.lastIndexOf('.');
   String mediaType = MediaGWT.kMEDIA_TYPES_MAP.get(mediaGWT.url.substring(idx));

   mediaGWT.addMediaToElement(
      mediaType, mediaHTML, sectionElem, chartContainerId, true);

   mediaGWT.addMediaToElement(
      mediaType, mediaHTML, sectionElem, chartContainerId, false);
}
/*------------------------------------------------------------------------------

@name       createMedia - create media
                                                                              */
                                                                             /**
            Create media

@return     void

@param      sectionIdx     section number

@history    Thu Jun 13, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MediaGWT createMedia(
   String     resourceName,
   Uint8Array fileBytes)
   throws     Exception
{
   int idx = resourceName.lastIndexOf('.');
   if (idx < 0)
   {
      throw new IllegalArgumentException(
         "mediaDsc format unknown: " + resourceName);
   }
   String type = MediaGWT.kMEDIA_TYPES_MAP.get(resourceName.substring(idx));
   if (type == null)
   {
      throw new IllegalArgumentException(
         "mediaDsc type unknown: " + resourceName);
   }

   MediaGWT addDsc = null;
   if ("spreadsheet".equals(type))
   {
      addDsc = new ChartGWT(resourceName, fileBytes);
   }
   else
   {
      addDsc     = new MediaGWT();
      addDsc.url = resourceName;
   }
   return(addDsc);
}
/*------------------------------------------------------------------------------

@name       dropFileHandler - drop file handler
                                                                              */
                                                                             /**
            Drop file handler

@return     void

@param      dropDivId      drop div id
@param      filename       drop filename
@param      fileType       drop file type
@param      fileBytes      drop file contents

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void dropFileHandler(
   String           dropDivId,
   String           filename,
   String           fileType,
   final Uint8Array fileBytes)
{
   String mediaType =
      "text/csv".equals(fileType)
         ? "csv" : fileType.substring(0, fileType.indexOf('/'));

   kLOGGER.logInfo("ChartWorkbench.dropFileHandler(): dropped " + mediaType);

   try
   {
      Element  section  = Document.get().getElementById("section");
      MediaGWT mediaDsc = createMedia(filename, fileBytes);
      if (mediaDsc instanceof ChartGWT)
      {
         addChartToSection((ChartGWT)mediaDsc, section);

         if (mediaDsc != null)
         {
            mediaDsc.addMediaChangeListener(ChartWorkbench.this);
         }
      }
      else
      {
         kLOGGER.logWarning(
            "ChartWorkbench.dropFileHandler(): no support for " + mediaType);
      }
   }
   catch(Exception e)
   {
      kLOGGER.logError(e);
   }
}
/*------------------------------------------------------------------------------

@name       getEditMode - get whether is edit mode
                                                                              */
                                                                             /**
            Get whether is edit mode

@return     true iff is edit mode

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean getEditMode()
{
   return(bEditMode);
}
/*------------------------------------------------------------------------------

@name       mediaChange - media change event handler
                                                                              */
                                                                             /**
            Media change event handler.

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes

                                                                              */
//------------------------------------------------------------------------------
public void mediaChange(
   MediaGWT media,
   boolean  bRemove)
{
   if (!bRemove)
   {
      renderSection((ChartGWT)media);
   }
}
/*------------------------------------------------------------------------------

@name       onModuleLoad - entry point method
                                                                              */
                                                                             /**
            Entry point method

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void onModuleLoad()
{
                                       // add clickhandler to edit button     //
   Element btnEditElement = Document.get().getElementById("editModeButton");
   if (btnEditElement != null)
   {
      Event.sinkEvents(btnEditElement, Event.ONCLICK);
      Event.setEventListener(btnEditElement, new ElementClickHandler(btnEditElement));
   }

   addDragDropHandlers();
}
/*------------------------------------------------------------------------------

@name       renderSection - render section
                                                                              */
                                                                             /**
            Render section

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected void renderSection(
   ChartGWT chartDsc)
{
   Element section = Document.get().getElementById("section");
   section.setInnerText(sectionText);
   addChartToSection(chartDsc, section);
}
/*==============================================================================

name:       ElementClickHandler - element click handler

purpose:    element click handler

history:    Fri Dec 02, 2016 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
protected class ElementClickHandler implements EventListener
{
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
protected Element source;              // source element                      //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       ElementClickHandler - constructor
                                                                              */
                                                                             /**
            Constructor

@return     An instance of LoginPanel if successful.

@history    Thu Dec 27, 2012 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ElementClickHandler(
   Element source)
{
   this.source = source;
}
/*------------------------------------------------------------------------------

@name       onBrowserEvent - browser event handler
                                                                              */
                                                                             /**
            Browser event handler.

@return     void

@history    Fri Dec 02, 2016 10:30:00 (Giavaneers - SeanQ) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void onBrowserEvent(
   Event event)
{
   switch(event.getTypeInt())
   {
      case Event.ONCLICK:
      {
         String sourceId = source.getId();

			if ("editModeButton".equals(sourceId))
         {
            bEditMode = !bEditMode;
         }
         break;
      }
   }
}
}//====================================// ElementClickHandler ================//
}//====================================// end class ChartWorkbench -----------//

/*==============================================================================

name:       DragDropGWT.java

purpose:    Gwt DragDrop support

history:    Thu Jan 12, 2017 13:00:00 (LBM) created.

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
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.typedarrays.shared.Uint8Array;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import java.util.List;
                                       // DragDropGWT ========================//
public class DragDropGWT
{
                                       // class constants ------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables ------ //
                                       // drop file listeners                 //
protected List<IDropFileListener> dropFileListeners;
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       addDragDropTargets - add drag drop targets
                                                                              */
                                                                             /**
            Add drag drop targets

@return     void

@param      targets     drop targets

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void addDragDropTargets(
   Element[] targets)
{
   for (Element target : targets)
   {
      target.addClassName("droppable");
      final Label div = Label.wrap(target);

      div.addDragLeaveHandler(new DragLeaveHandler()
      {
         public void onDragLeave(DragLeaveEvent e)
         {
           dragLeaveEventHandler(e, div);
         }
      });
      div.addDragOverHandler(new DragOverHandler()
      {
         public void onDragOver(DragOverEvent e)
         {
           dragOverEventHandler(e, div);
         }
      });

      div.addDropHandler(new DropHandler()
      {
         public void onDrop(DropEvent e)
         {
           dropEventHandler(e, div);
         }
      });
   }
}
/*------------------------------------------------------------------------------

@name       addDropFileListener - add drop file listener
                                                                              */
                                                                             /**
            Add drop file listener

@return     void

@param      listener    drop file listener

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void addDropFileListener(
   IDropFileListener listener)
{
   getDropFileListeners().add(listener);
}
/*------------------------------------------------------------------------------

@name       dragOverEventHandler - dragover event handler
                                                                              */
                                                                             /**
            Dragover event handler

@return     void

@param      e     dragover event

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void dragOverEventHandler(
   DragOverEvent e,
   Label         div)
{
   e.preventDefault();
   div.getElement().addClassName("drop-hover-border");
}
/*------------------------------------------------------------------------------

@name       dragLeaveEventHandler - dragleave event handler
                                                                              */
                                                                             /**
            Dragleave event handler

@return     void

@param      e     dragleave event

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void dragLeaveEventHandler(
   DragLeaveEvent e,
   Label          div)
{
   e.preventDefault();
   div.getElement().removeClassName("drop-hover-border");
}
/*------------------------------------------------------------------------------

@name       dropEventHandler - drop event handler
                                                                              */
                                                                             /**
            Drop event handler

@return     void

@param      e     drop event

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void dropEventHandler(
   DropEvent e,
   Label     div)
{
   dropEventHandlerNative(this, div.getElement().getId(), e.getNativeEvent());
   div.getElement().removeClassName("drop-hover-border");
}
/*------------------------------------------------------------------------------

@name       dropEventHandlerNative - export static methods
                                                                              */
                                                                             /**
            Publish methods to be called by external javascript.

@return     void

@history    Thu July 25, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static native void dropEventHandlerNative(
   DragDropGWT instance,
   String      dropDivId,
   NativeEvent ev)
/*-{
   ev.preventDefault();

   if (dropDivId == null)
   {
      alert("This drop procedure is not supported");
      return;
   }
                                       // process only first File object      //
   var file = ev.dataTransfer.files[0];
   var type = file.type;
   if (type.length == 0)
   {
                                       // windows sometime reports empty type //
      if (file.name.toLowerCase().endsWith(".jpg"))
      {
         type = "image/jpeg";
      }
      else if (file.name.toLowerCase().endsWith(".jpeg"))
      {
         type = "image/jpeg";
      }
      else if (file.name.toLowerCase().endsWith(".png"))
      {
         type = "image/png";
      }
      else if (file.name.toLowerCase().endsWith(".mp3"))
      {
         type = "audio/mp3";
      }
      else if (file.name.toLowerCase().endsWith(".wav"))
      {
         type = "audio/wav";
      }
      else if (file.name.toLowerCase().endsWith(".mp4"))
      {
         type = "video/mp4";
      }
      else if (file.name.toLowerCase().endsWith(".m4v"))
      {
         type = "video/m4v";
      }
      else if (file.name.toLowerCase().endsWith(".csv"))
      {
         type = "text/csv";
      }
      else if (file.name.toLowerCase().endsWith("txt"))
      {
         type = "text/plain";
      }
   }
   if (type.indexOf("image/") < 0
         && type.indexOf("audio/") < 0
         && type.indexOf("video/") < 0
         && type.indexOf("text/")  < 0)
   {
      alert("This drop procedure is not supported");
      return;
   }

   console.log("image drop: " + file.name + " (" + type + ")");

   var reader = new FileReader();
   reader.onload = function(event)
   {
      var filename = file.name;
      var filetype = type;
      var data     = new Uint8Array(reader.result);

      instance.@org.marinespacestation.project.client.DragDropGWT::dropFileHandler(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/google/gwt/typedarrays/shared/Uint8Array;)(
         dropDivId, filename, filetype, data);
   };
   reader.readAsArrayBuffer(file);
}-*/;
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
   String     dropDivId,
   String     filename,
   String     fileType,
   Uint8Array fileBytes)
{
   for (IDropFileListener listener : getDropFileListeners())
   {
      listener.dropFileHandler(dropDivId, filename, fileType, fileBytes);
   }
}
/*------------------------------------------------------------------------------

@name       getDropFileListeners - get drop file listeners
                                                                              */
                                                                             /**
            Get drop file listeners

@return     drop file listeners

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<IDropFileListener> getDropFileListeners()
{
   if (dropFileListeners == null)
   {
      dropFileListeners = new ArrayList<IDropFileListener>();
   }
   return(dropFileListeners);
}
/*==============================================================================

name:       IDropFileListener - media change listener

purpose:    media change listener

history:    Fri Dec 02, 2016 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
protected interface IDropFileListener
{
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
void dropFileHandler(
  String dropDivId,
  String filename,
  String fileType,
  Uint8Array fileBytes);

}//====================================// IDropFileListener ==================//
}//====================================// end class DragDropGWT --------------//

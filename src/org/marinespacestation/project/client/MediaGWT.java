/*==============================================================================

name:       MediaGWT.java

purpose:    Gwt Media Element

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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
                                       // MediaGWT ===========================//
public class MediaGWT
{
                                       // class constants ------------------- //
public static final String kKEY_ALIGNMENT          = "alignment";
public static final String kKEY_CAPTION            = "caption";
public static final String kKEY_URL                = "url";
public static final String kKEY_WIDTH_OVERRIDE     = "widthOverride";
public static final String kKEY_WORD_POSITION      = "wordPosition";

public static final String kMEDIA_CONTANER_CAPTION_DEFAULT    = "Figure-X";
public static final String kMEDIA_CONTANER_WORD_POSITION_DEFAULT = "40";

public static final String kMEDIA_POSTION_UP       = "up";
public static final String kMEDIA_POSTION_DOWN     = "down";
public static final String kMEDIA_POSTION_LEFT     = "left";
public static final String kMEDIA_POSTION_MIDDLE   = "middle";
public static final String kMEDIA_POSTION_NARROWER = "narrower";
public static final String kMEDIA_POSTION_RIGHT    = "right";
public static final String kMEDIA_POSTION_WIDER    = "wider";
                                       // mime type map
                                       //
public static final Map<String,String> kMIME_TYPES_MAP =
   Collections.unmodifiableMap(
      new HashMap<String, String>()
      {
         {
            put("application/pdf", ".pdf");
            put("audio/mpeg",      ".mp3");
            put("audio/wav",       ".wav");
            put("image/gif",       ".gif");
            put("image/png",       ".png");
            put("image/jpeg",      ".jpeg");
            put("image/jpg",       ".jpg");
            put("text/js",         ".js");
            put("text/plain",      ".txt");
            put("video/mp4",       ".mp4");
         }
      });
                                       // mime type map                       //
public static final Map<String,String> kMEDIA_TYPES_MAP =
   Collections.unmodifiableMap(
      new HashMap<String, String>()
      {
         {
            put(".mp3",  "audio");
            put(".wav",  "audio");
            put(".gif",  "image");
            put(".png",  "image");
            put(".jpeg", "image");
            put(".jpg",  "image");
            put(".csv",  "spreadsheet");
            put(".txt",  "text");
            put(".mp4",  "video");
         }
      });

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String  url;                    // url                                 //
public String  widthOverride;          // widthOverride                       //
public String  alignment;              // horizontal alignment                //
public String  wordPosition;           // word position                       //
public String  caption;                // caption                             //
public String  elementId;              // elementId                           //
public Object  userData;               // user data (not persistent)          //
public boolean bEditMode;              // edit mode                           //
public List<IMediaChangeListener>      // change listeners                    //
               changeListeners;
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       MediaGWT - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@return     void

@history    Thu Jun 13, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MediaGWT()
{
}
/*------------------------------------------------------------------------------

@name       MediaGWT - constructor for specified JSONObject
                                                                              */
                                                                             /**
            Constructor for specified JSONObject.

@return     void

@history    Thu Jun 13, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public MediaGWT(
   JSONObject jsonObject)
{
   for (String key : jsonObject.keySet())
   {
      JSONValue value   = jsonObject.get(key);
      JSONString jsValue = value.isString();
      if (jsValue != null)
      {
         String sValue = value.isString().stringValue();

         if (kKEY_URL.equals(key))
         {
            url = sValue;
         }
         else if (kKEY_WIDTH_OVERRIDE.equals(key))
         {
            widthOverride = sValue;
         }
         else if (kKEY_ALIGNMENT.equals(key))
         {
            alignment = sValue;
         }
         else if (kKEY_WORD_POSITION.equals(key))
         {
            wordPosition = sValue;
         }
         else if (kKEY_CAPTION.equals(key))
         {
            caption = sValue;
         }
      }
   }
}
/*------------------------------------------------------------------------------

@name       addMediaMenuHandler - add media menu handler
                                                                              */
                                                                             /**
            Add media menu handler

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void addMediaMenuHandler(
   String mediaPanelId)
{
   final Element mediaElem = Document.get().getElementById(mediaPanelId);
   Element mediaMenu = Document.get().createDivElement();
   mediaMenu.addClassName("dropdown media-menu");
   mediaElem.appendChild(mediaMenu);

   Element mediaDropdownBtn = Document.get().createPushButtonElement();
   mediaDropdownBtn.addClassName("btn btn-primary dropdown-toggle");
   mediaDropdownBtn.setPropertyString("type", "button");
   mediaDropdownBtn.setPropertyString("data-toggle", "dropdown");
   mediaMenu.appendChild(mediaDropdownBtn);

   Element span = Document.get().createSpanElement();
   span.addClassName("caret");
   mediaDropdownBtn.appendChild(span);

   Element mediaDropdownMenu = Document.get().createULElement();

                                       // set margin top zero so dropdown does//
                                       // not disappear on mouse transition   //
                                       // from button to menu                 //
   mediaDropdownMenu.addClassName("dropdown-menu");
   mediaDropdownMenu.setId(mediaPanelId + "dropdown-menu");
   mediaDropdownMenu.getStyle().setMarginTop(0, Unit.PX);
   mediaMenu.appendChild(mediaDropdownMenu);

                                       // up -------------------------------- //
   Element mediaItemPositionUp = Document.get().createLIElement();
   mediaItemPositionUp.setId("mediaItemPositionUp");
   Element mediaItemPositionUpAnchor = Document.get().createAnchorElement();
   mediaItemPositionUpAnchor.setInnerText("Move Up");
   mediaItemPositionUp.appendChild(mediaItemPositionUpAnchor);
   Event.sinkEvents(mediaItemPositionUp, Event.ONCLICK);
   DOM.setEventListener(mediaItemPositionUp, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignMediaPosition(kMEDIA_POSTION_UP);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemPositionUp);

                                       // down ------------------------------ //
   Element mediaItemPositionDown = Document.get().createLIElement();
   mediaItemPositionDown.setId("mediaItemPositionDown");
   Element mediaItemPositionDownAnchor = Document.get().createAnchorElement();
   mediaItemPositionDownAnchor.setInnerText("Move Down");
   mediaItemPositionDown.appendChild(mediaItemPositionDownAnchor);
   Event.sinkEvents(mediaItemPositionDown, Event.ONCLICK);
   DOM.setEventListener(mediaItemPositionDown, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignMediaPosition(kMEDIA_POSTION_DOWN);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemPositionDown);

                                       // left ------------------------------ //
   Element mediaItemPositionLeft = Document.get().createLIElement();
   mediaItemPositionLeft.setId("mediaItemPositionLeft");
   Element mediaItemPositionLeftAnchor = Document.get().createAnchorElement();
   mediaItemPositionLeftAnchor.setInnerText("Position Left");
   mediaItemPositionLeft.appendChild(mediaItemPositionLeftAnchor);
   Event.sinkEvents(mediaItemPositionLeft, Event.ONCLICK);
   DOM.setEventListener(mediaItemPositionLeft, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignMediaPosition(kMEDIA_POSTION_LEFT);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemPositionLeft);

                                       // middle ---------------------------- //
   Element mediaItemPositionMiddle = Document.get().createLIElement();
   mediaItemPositionMiddle.setId("mediaItemPositionMiddle");
   Element mediaItemPositionMiddleAnchor = Document.get().createAnchorElement();
   mediaItemPositionMiddleAnchor.setInnerText("Position Middle");
   mediaItemPositionMiddle.appendChild(mediaItemPositionMiddleAnchor);
   Event.sinkEvents(mediaItemPositionMiddle, Event.ONCLICK);
   DOM.setEventListener(mediaItemPositionMiddle, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignMediaPosition(kMEDIA_POSTION_MIDDLE);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemPositionMiddle);

                                       // right ----------------------------- //
   Element mediaItemPositionRight = Document.get().createLIElement();
   mediaItemPositionRight.setId("mediaItemPositionRight");
   Element mediaItemPositionRightAnchor = Document.get().createAnchorElement();
   mediaItemPositionRightAnchor.setInnerText("Position Right");
   mediaItemPositionRight.appendChild(mediaItemPositionRightAnchor);
   Event.sinkEvents(mediaItemPositionRight, Event.ONCLICK);
   DOM.setEventListener(mediaItemPositionRight, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignMediaPosition(kMEDIA_POSTION_RIGHT);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemPositionRight);

                                       // divider --------------------------- //
   Element divider = Document.get().createLIElement();
   divider.addClassName("divider");
   mediaDropdownMenu.appendChild(divider);

                                       // narrower -------------------------- //
   Element mediaItemNarrower = Document.get().createLIElement();
   mediaItemNarrower.setId("mediaItemNarrower");
   Element mediaItemNarrowerAnchor = Document.get().createAnchorElement();
   mediaItemNarrowerAnchor.setInnerText("Narrower");
   mediaItemNarrower.appendChild(mediaItemNarrowerAnchor);
   Event.sinkEvents(mediaItemNarrower, Event.ONCLICK);
   DOM.setEventListener(mediaItemNarrower, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignMediaPosition(kMEDIA_POSTION_NARROWER);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemNarrower);

                                       // wider ----------------------------- //
   Element mediaItemWider = Document.get().createLIElement();
   mediaItemWider.setId("mediaItemWider");
   Element mediaItemWiderAnchor = Document.get().createAnchorElement();
   mediaItemWiderAnchor.setInnerText("Wider");
   mediaItemWider.appendChild(mediaItemWiderAnchor);
   Event.sinkEvents(mediaItemWider, Event.ONCLICK);
   DOM.setEventListener(mediaItemWider, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignMediaPosition(kMEDIA_POSTION_WIDER);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemWider);

                                       // default size ---------------------- //
   Element mediaItemReset = Document.get().createLIElement();
   mediaItemReset.setId("mediaItemReset");
   Element mediaItemResetAnchor = Document.get().createAnchorElement();
   mediaItemResetAnchor.setInnerText("Default Size");
   mediaItemReset.appendChild(mediaItemResetAnchor);
   Event.sinkEvents(mediaItemReset, Event.ONCLICK);
   DOM.setEventListener(mediaItemReset, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            if (kMEDIA_POSTION_LEFT.equals(alignment)|| kMEDIA_POSTION_RIGHT.equals(alignment)){
               widthOverride = "40%";
            }
            else if (kMEDIA_POSTION_MIDDLE.equals(alignment)){
               widthOverride = "80%";
            }
         }
         notifyChangeListeners(false);
      }
   });
   mediaDropdownMenu.appendChild(mediaItemReset);

                                       // divider --------------------------- //
   divider = Document.get().createLIElement();
   divider.addClassName("divider");
   mediaDropdownMenu.appendChild(divider);

                                       // delete ---------------------------- //
   Element mediaItemDelete = Document.get().createLIElement();
   mediaItemDelete.setId("mediaItemNewSheet");
   Element mediaItemDeleteAnchor = Document.get().createAnchorElement();
   mediaItemDeleteAnchor.setInnerText("Delete");
   mediaItemDelete.appendChild(mediaItemDeleteAnchor);
   Event.sinkEvents(mediaItemDelete, Event.ONCLICK);
   DOM.setEventListener(mediaItemDelete, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            removeMediaFromParent(mediaElem);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemDelete);
}
/*------------------------------------------------------------------------------

@name       assignMediaPosition - assign media position
                                                                              */
                                                                             /**
            Assign media position

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void assignMediaPosition(
   String  direction)
{
   boolean  bChanged = false;

   if (kMEDIA_POSTION_UP.equals(direction))
   {
      int mediaPos = Integer.parseInt(wordPosition);
      if (mediaPos > 0)
      {
         mediaPos = Math.max(0, mediaPos - 10);
         wordPosition = Integer.toString(mediaPos);
         bChanged = true;
      }
   }
   else if (kMEDIA_POSTION_DOWN.equals(direction))
   {
      String innerText =
         Document.get().getElementById(
            elementId).getParentElement().getInnerText();

      int mediaPos = Integer.parseInt(wordPosition);
      int numWords = numWords(innerText);

      if (mediaPos < numWords)
      {
         mediaPos     = Math.min(numWords, mediaPos + 10);
         wordPosition = Integer.toString(mediaPos);
         bChanged     = true;
      }
   }
   else if (kMEDIA_POSTION_LEFT.equals(direction))
   {
      if (!kMEDIA_POSTION_LEFT.equals(alignment))
      {
         alignment     = kMEDIA_POSTION_LEFT;
         widthOverride = "40%";
         bChanged      = true;
      }
   }
   else if (kMEDIA_POSTION_MIDDLE.equals(direction))
   {
      if (!kMEDIA_POSTION_MIDDLE.equals(alignment))
      {
         alignment     = kMEDIA_POSTION_MIDDLE;
         widthOverride = "80%";
         bChanged      = true;
      }
   }
   else if (kMEDIA_POSTION_NARROWER.equals(direction))
   {
      widthOverride =
         Double.toString((Double.parseDouble(
            widthOverride.substring(
               0, widthOverride.length() - 1)) * 0.9)) + "%";
      bChanged = true;
   }
   else if (kMEDIA_POSTION_RIGHT.equals(direction))
   {
      if (!kMEDIA_POSTION_RIGHT.equals(alignment))
      {
         alignment     = kMEDIA_POSTION_RIGHT;
         widthOverride = "40%";
         bChanged      = true;
      }
   }
   else if (kMEDIA_POSTION_WIDER.equals(direction))
   {
      widthOverride =
         Double.toString((Double.parseDouble(
            widthOverride.substring(
               0, widthOverride.length() - 1)) * 1.1)) + "%";

      bChanged = true;
   }
   else
   {
      throw new IllegalArgumentException("Unknown assigment = " + direction);
   }
   if (bChanged)
   {
      notifyChangeListeners(false);
   }
}
/*------------------------------------------------------------------------------

@name       addMediaChangeListener - add media change listener
                                                                              */
                                                                             /**
            Add media change listener

@return     void

@param      listener    media change listener

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void addMediaChangeListener(
   IMediaChangeListener listener)
{
   getChangeListeners().add(listener);
}
/*------------------------------------------------------------------------------

@name       addMediaToElement - add media to element
                                                                              */
                                                                             /**
            Add media to element

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void addMediaToElement(
   String  mediaType,
   String  mediaHTML,
   Element textElem,
   String  chartContainerId,
   boolean bAddPlaceholder)
{
   String text = textElem.getInnerText();
   elementId   = messageDigestSHA256Native(mediaType + url);

   if (wordPosition == null)
   {
      wordPosition = kMEDIA_CONTANER_WORD_POSITION_DEFAULT;
   }

   int wordPos = Integer.parseInt(wordPosition);
   int idx     = wordPos > 0 ? ordinalIndexOf(text, " ", wordPos) : 0;

   if (bAddPlaceholder)
   {
      text = text.substring(0, idx) + elementId + text.substring(idx);
      textElem.setInnerHTML(text);
   }
   else
   {
      if (alignment == null)
      {
         alignment     = kMEDIA_POSTION_MIDDLE;
         widthOverride = "80%";
      }

      String classname = "media-container-" + alignment;

      if (caption == null)
      {
         caption = kMEDIA_CONTANER_CAPTION_DEFAULT;
      }

      String widthOverride =
         this.widthOverride != null
            ? "style='width:" + this.widthOverride + "'" : "";

      String textInputEdit =
         "   <input type='text' id='" + ("textBox" + elementId)
       + "' class='gwt-TextBox mediaCaption' value='" + caption + "'>";

      String textInputNoEdit =
         "   <input type='text' id='" + ("textBox" + elementId)
       + "' class='gwt-TextBox gwt-TextBox-readonly mediaCaption' "
       + "readonly value='" + caption + "'>";

      String textInput = bEditMode ? textInputEdit : textInputNoEdit;

      String mediaString =
         "<div class='" + classname + "' id='" + elementId + "' "
       + widthOverride + ">\n"
       + mediaHTML + "\n"
       + textInput
       + "</div>\n";

      text = text.replace(elementId, mediaString);
      textElem.setInnerHTML(text);

      if (bEditMode)
      {
         addTextChangeHandlerNative(
            this, DOM.getElementById("textBox" + elementId));

         addMediaMenuHandler(elementId);
      }
   }
}
/*------------------------------------------------------------------------------

@name       addTextChangeHandlerNative - add native text change handler
                                                                              */
                                                                             /**
            Add native text change handler.

@return     void

@history    Thu July 25, 2016 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static native void addTextChangeHandlerNative(
   MediaGWT mediaGWT,
   Element  element)
/*-{
   element.onchange = function()
   {
      mediaGWT.@org.marinespacestation.project.client.MediaGWT::textChangeHandler(Ljava/lang/String;)(element.id);
   };
}-*/;
/*------------------------------------------------------------------------------

@name       getChangeListeners - get change handlers
                                                                              */
                                                                             /**
            Get change handlers

@return     change handlers

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected List<IMediaChangeListener> getChangeListeners()
{
   if (changeListeners == null)
   {
      changeListeners = new ArrayList<IMediaChangeListener>();
   }
   return(changeListeners);
}
/*------------------------------------------------------------------------------

@name       messageDigestSHA256Native - generate message digest
                                                                              */
                                                                             /**
            Generate message digest

@return     void

@history    Sun Oct 16, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native String messageDigestSHA256Native(
   String message)
/*-{
   var md = new $wnd.KJUR.crypto.MessageDigest({alg: "sha256", prov: "sjcl"});
   md.updateString(message);
   var mdHex = md.digest();
   return(mdHex);
}-*/;
/*------------------------------------------------------------------------------

@name       notifyChangeListeners - notify change listeners
                                                                              */
                                                                             /**
            Notify change listeners

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void notifyChangeListeners(
   boolean bRemove)
{
   for (IMediaChangeListener listener : getChangeListeners())
   {
      listener.mediaChange(this, bRemove);
   }
}
/*------------------------------------------------------------------------------

@name       numWords - find number or words in string
                                                                              */
                                                                             /**
            Find number or words in string

@return     number or words in string

@history    Sun Oct 16, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static int numWords(
   String text)
{
   return(ordinalIndexOf(text, " ", Integer.MAX_VALUE));
}
/*------------------------------------------------------------------------------

@name       ordinalIndexOf - index of nth occurrence of substring
                                                                              */
                                                                             /**
            Index of nth occurrence of substring

@return     index of nth occurrence of substring

@param      str      original string
@param      sub      substring
@param      n        occurrence

@history    Sun Oct 16, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static int ordinalIndexOf(
   String str,
   String sub,
   int    n)
{
   int idx = 0;
   for (int i = 0; i < n; i++)
   {
      int idxNext = str.indexOf(sub, idx + 1);
      if (idxNext < 0)
      {
         break;
      }
      idx = idxNext;
   }
   return(idx);
}
/*------------------------------------------------------------------------------

@name       removeMediaFromParent - remove media from specified section
                                                                              */
                                                                             /**
            Remove media from specified section.

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void removeMediaFromParent(
   Element mediaPanelElement)
{
   mediaPanelElement.removeFromParent();
   notifyChangeListeners(true);
};
/*------------------------------------------------------------------------------

@name       textChangeHandler - text change handler
                                                                              */
                                                                             /**
            Text change handler

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void textChangeHandler(
   String elementId)
{
   String text =
      Document.get().getElementById(elementId).getPropertyString("value");

   if (!text.equals(caption))
   {
      caption = text;
      notifyChangeListeners(false);
   }
}
/*------------------------------------------------------------------------------

@name       toJSON - to json
                                                                              */
                                                                             /**
            Convert to JSONObject.

@return     void

@history    Thu Jun 13, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public JSONObject toJSON()
{
   JSONObject jsonObject = new JSONObject();
   jsonObject.put("url",  new JSONString(url));
   if (widthOverride != null)
   {
      jsonObject.put(kKEY_WIDTH_OVERRIDE, new JSONString(widthOverride));
   }
   if (alignment != null)
   {
      jsonObject.put(kKEY_ALIGNMENT, new JSONString(alignment));
   }
   if (wordPosition != null)
   {
      jsonObject.put(kKEY_WORD_POSITION, new JSONString(wordPosition));
   }
   if (caption != null)
   {
      jsonObject.put(kKEY_CAPTION, new JSONString(caption));
   }

   return(jsonObject);
}
/*==============================================================================

name:       IMediaChangeListener - media change listener

purpose:    media change listener

history:    Fri Dec 02, 2016 10:30:00 (Giavaneers - LBM) created

notes:

==============================================================================*/
protected interface IMediaChangeListener
{
/*------------------------------------------------------------------------------

@name       mediaChange - media change event handler
                                                                              */
                                                                             /**
            Media change event handler.

@return     void

@history    Fri Dec 02, 2016 10:30:00 (Giavaneers - SeanQ) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void mediaChange(
   MediaGWT media,
   boolean  bRemove);

}//====================================// IMediaChangeHandler ================//
}//====================================// end class MediaGWT -----------------//


/*==============================================================================

name:       ChartGWT.java

purpose:    Gwt Chart

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
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.typedarrays.shared.Uint8Array;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
                                       // ChartGWT ===========================//
public class ChartGWT extends MediaGWT
{
                                       // class constants ------------------- //
public static final String kCHART_COLUMN_CHART          = "ColumnChart";
public static final String kCHART_LINE_CHART            = "LineChart";
public static final String kCHART_PIE_CHART             = "PieChart";
public static final String kCHART_SCATTER_CHART         = "ScatterChart";
public static final String kCHART_SURFACE_CHART         = "SurfaceChart";

public static final String kKEY_OPTIONS                 = "options";
public static final String kKEY_TYPE                    = "type";

public static final String kOPTION_COLOR_THEME          = "colorTheme";
public static final String kOPTION_HEIGHT               = "height";
public static final String kOPTION_TITLE                = "title";
public static final String kOPTION_TITLE_TEXT_COLOR     = "titleTextColor";
public static final String kOPTION_TITLE_TEXT_FONT_SIZE = "titleTextFontSize";
public static final String kOPTION_TITLE_TEXT_STYLE     = "titleTextStyle";
public static final String kOPTION_WIDTH                = "width";
public static final String kOPTION_X_LABEL              = "xLabel";
public static final String kOPTION_Y_LABEL              = "yLabel";
public static final String kOPTION_Z_LABEL              = "zLabel";

public static final Map<String,String> kDEFAULT_OPTIONS =
   Collections.unmodifiableMap(
      new HashMap<String, String>()
      {
         {
            put(kOPTION_WIDTH, "'100%'");
            put(kOPTION_HEIGHT, "360");
            put(kOPTION_TITLE_TEXT_STYLE, "{color:'grey',fontSize:20}");
         }
      });
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
public String             type;        // chart type                          //
public String             data;        // chart data (not persistent)         //
public Map<String,String> options;     // options                             //
                                       // chart container id                  //
public String             chartContainerId;
                                       // protected instance variables ------ //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       ChartGWT - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@return     void

@history    Thu Jun 13, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ChartGWT()
{
   this.type    = kCHART_LINE_CHART;
   this.options = kDEFAULT_OPTIONS;
}
/*------------------------------------------------------------------------------

@name       ChartGWT - default constructor
                                                                              */
                                                                             /**
            Default constructor.

@return     void

@history    Thu Jun 13, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ChartGWT(
   String     url,
   Uint8Array fileBytes)
   throws     Exception
{
   this();
   this.url = url;

   byte[]   bytes = uint8ArrayToBytes(fileBytes);
            data  = new String(bytes, "UTF-8");
   String   row   = data.substring(0, data.indexOf('\n'));
   String[] cols  = row.split(",");

   this.type =
      cols.length < 3
         ? ChartGWT.kCHART_LINE_CHART : ChartGWT.kCHART_SURFACE_CHART;
}
/*------------------------------------------------------------------------------

@name       ChartGWT - constructor for specified JSONObject
                                                                              */
                                                                             /**
            Constructor for specified JSONObject.

@return     void

@history    Thu Jun 13, 2013 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public ChartGWT(
   JSONObject jsonObject)
{
   super(jsonObject);
   for (String key : jsonObject.keySet())
   {
      JSONValue value   = jsonObject.get(key);
      JSONString jsValue = value.isString();
      if (jsValue != null)
      {
         String sValue = value.isString().stringValue();

         if (kKEY_TYPE.equals(key))
         {
            type = sValue;
         }
      }
      else if (kKEY_OPTIONS.equals(key))
      {
         this.options = new HashMap<String,String>();

         JSONObject jOptions = (JSONObject)value;
         for (String keyOptions : jOptions.keySet())
         {
            this.options.put(
               keyOptions, jOptions.get(keyOptions).isString().stringValue());
         }
      }
   }
}
/*------------------------------------------------------------------------------

@name       addChartMenuHandler - add chart menu handler
                                                                              */
                                                                             /**
            Add chart menu handler

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void addChartMenuHandler(
   Element mediaDropdownMenu)
{
                                       // divider --------------------------- //
   Element divider = Document.get().createLIElement();
   divider.addClassName("divider");
   mediaDropdownMenu.appendChild(divider);
                                       // line chart ------------------------ //
   Element mediaItemLineChart = Document.get().createLIElement();
   mediaItemLineChart.setId("mediaItemLineChart");
   Element mediaItemLineChartAnchor = Document.get().createAnchorElement();
   mediaItemLineChartAnchor.setInnerText("Line Chart");
   mediaItemLineChart.appendChild(mediaItemLineChartAnchor);
   Event.sinkEvents(mediaItemLineChart, Event.ONCLICK);
   DOM.setEventListener(mediaItemLineChart, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignChartOption(kCHART_LINE_CHART);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemLineChart);

                                       // scatter chart --------------------- //
   Element mediaItemScatterChart = Document.get().createLIElement();
   mediaItemScatterChart.setId("mediaItemScatterChart");
   Element mediaItemScatterChartAnchor = Document.get().createAnchorElement();
   mediaItemScatterChartAnchor.setInnerText("Scatter Chart");
   mediaItemScatterChart.appendChild(mediaItemScatterChartAnchor);
   Event.sinkEvents(mediaItemScatterChart, Event.ONCLICK);
   DOM.setEventListener(mediaItemScatterChart, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignChartOption(kCHART_SCATTER_CHART);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemScatterChart);

                                       // column chart ---------------------- //
   Element mediaItemColumnChart = Document.get().createLIElement();
   mediaItemColumnChart.setId("mediaItemColumnChart");
   Element mediaItemColumnChartAnchor = Document.get().createAnchorElement();
   mediaItemColumnChartAnchor.setInnerText("Column Chart");
   mediaItemColumnChart.appendChild(mediaItemColumnChartAnchor);
   Event.sinkEvents(mediaItemColumnChart, Event.ONCLICK);
   DOM.setEventListener(mediaItemColumnChart, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignChartOption(kCHART_COLUMN_CHART);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemColumnChart);

                                       // pie chart ------------------------- //
   Element mediaItemPieChart = Document.get().createLIElement();
   mediaItemPieChart.setId("mediaItemPieChart");
   Element mediaItemPieChartAnchor = Document.get().createAnchorElement();
   mediaItemPieChartAnchor.setInnerText("Pie Chart");
   mediaItemPieChart.appendChild(mediaItemPieChartAnchor);
   Event.sinkEvents(mediaItemPieChart, Event.ONCLICK);
   DOM.setEventListener(mediaItemPieChart, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignChartOption(kCHART_PIE_CHART);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemPieChart);

                                       // surface chart --------------------- //
   Element mediaItemSurfaceChart = Document.get().createLIElement();
   mediaItemSurfaceChart.setId("mediaItemSurfaceChart");
   Element mediaItemSurfaceChartAnchor = Document.get().createAnchorElement();
   mediaItemSurfaceChartAnchor.setInnerText("Surface Chart");
   mediaItemSurfaceChart.appendChild(mediaItemSurfaceChartAnchor);
   Event.sinkEvents(mediaItemSurfaceChart, Event.ONCLICK);
   DOM.setEventListener(mediaItemSurfaceChart, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignChartOption(kCHART_SURFACE_CHART);
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemSurfaceChart);

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
   super.addMediaMenuHandler(mediaPanelId);

   Element mediaDropdownMenu =
      Document.get().getElementById(mediaPanelId + "dropdown-menu");

   addChartMenuHandler(mediaDropdownMenu);
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
   super.addMediaToElement(
      mediaType, mediaHTML, textElem, chartContainerId, bAddPlaceholder);

   if (bAddPlaceholder)
   {
      this.chartContainerId = chartContainerId;
   }
   else
   {
      render();
   }
}
/*------------------------------------------------------------------------------

@name       assignChartOption - assign chart option
                                                                              */
                                                                             /**
            Assign chart option

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void assignChartOption(
   String  option)
{
   boolean bChanged = false;

   if (kCHART_LINE_CHART.equals(option))
   {
      if (!kCHART_LINE_CHART.equals(type))
      {
         type     = kCHART_LINE_CHART;
         bChanged = true;
      }
   }
   else if (kCHART_SCATTER_CHART.equals(option))
   {
      if (!kCHART_SCATTER_CHART.equals(type))
      {
         type     = kCHART_SCATTER_CHART;
         bChanged = true;
      }
   }
   else if (kCHART_COLUMN_CHART.equals(option))
   {
      if (!kCHART_COLUMN_CHART.equals(type))
      {
         type     = kCHART_COLUMN_CHART;
         bChanged = true;
      }
   }
   else if (kCHART_PIE_CHART.equals(option))
   {
      if (!kCHART_PIE_CHART.equals(type))
      {
         type      = kCHART_PIE_CHART;
         bChanged = true;
      }
   }
   else if (kCHART_SURFACE_CHART.equals(option))
   {
      if (!kCHART_SURFACE_CHART.equals(type))
      {
         type     = kCHART_SURFACE_CHART;
         bChanged = true;
      }
   }
   if (bChanged)
   {
      notifyChangeListeners(false);
      render();
   }
}
/*------------------------------------------------------------------------------

@name       createNativeOptions - create chart options
                                                                              */
                                                                             /**
            Create chart options.

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public JavaScriptObject createNativeOptions()
{
   JavaScriptObject nativeOptions = null;
   if (options != null)
   {
      String title             = options.get(kOPTION_TITLE);
      String titleTextColor    = options.get(kOPTION_TITLE_TEXT_COLOR);
      String titleTextFontSize = options.get(kOPTION_TITLE_TEXT_FONT_SIZE);
      String hAxisTitle        = options.get(kOPTION_X_LABEL);
      String vAxisTitle        = options.get(kOPTION_Y_LABEL);
      String colorTheme        = options.get(kOPTION_COLOR_THEME);

      nativeOptions =
         createNativeOptions(
            title,
            titleTextColor != null ? titleTextColor : "grey",
            titleTextFontSize != null ? Integer.parseInt(titleTextFontSize) : 20,
            hAxisTitle,
            vAxisTitle);
   }
   return(nativeOptions);
}
/*------------------------------------------------------------------------------

@name       createNativeOptions - create chart options
                                                                              */
                                                                             /**
            Create chart options.

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static native JavaScriptObject createNativeOptions(
   String title,
   String titleTextColor,
   int    titleTextFontSize,
   String hAxisTitle,
   String vAxisTitle)
/*-{
   var options = {};
   options.width  = '100%';
   options.height = 360;
   options.title  = title;
   options.colors = null;
   options.titleTextStyle = {color:titleTextColor, fontSize:titleTextFontSize};
   options.hAxis = {};
   options.hAxis.title = hAxisTitle;
   options.vAxis = {};
   options.vAxis.title = vAxisTitle;

   return(options);
}-*/;
/*------------------------------------------------------------------------------

@name       draw2DChartNative - render the specified 2D chart
                                                                              */
                                                                             /**
            Render the specified 2D chart.

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
protected static native void draw2DChartNative(
   String           chartContainerId,
   String           localChartData,
   String           url,
   String           chartType,
   JavaScriptObject options,
   boolean          bEditMode)
/*-{
   function drawChart2D(chartData)
   {
      var arrayData    = $wnd.$.csv.toArrays(chartData, {onParseValue: $wnd.$.csv.hooks.castToScalar});
      var data         = new $wnd.google.visualization.arrayToDataTable(arrayData);
      var chartWrapper =
         new $wnd.google.visualization.ChartWrapper(
         {
            chartType:   chartType,
            containerId: chartContainerId,
            dataTable:   data,
            options:     options
         });

      chartWrapper.draw();
   }
   if (localChartData == null)
   {
      $wnd.$.get(url, function(chartData)
      {
         drawChart2D(chartData);
      });
   }
   else
   {
      drawChart2D(localChartData);
   }
}-*/;
/*------------------------------------------------------------------------------

@name       draw3DChartNative - render the specified 3D chart
                                                                              */
                                                                             /**
            Render the specified 3D chart.

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes      VIS.JS 3D SURFACE CHART
                                                                              */
//------------------------------------------------------------------------------
protected static native void draw3DChartNative(
   String           chartContainerId,
   String           localChartData,
   String           url,
   String           chartType,
   JavaScriptObject options,
   boolean          bEditMode)
/*-{
   function convertArrayToVizDataset(csvArray)
   {
      var dataset = new $wnd.vis.DataSet();

                                       // read all data                       //
      for (var row = 1; row < csvArray.length; row++)
      {
         var data;

         if (csvArray[row].length == 4 && skipValue == true)
         {
                                       // vis.js will test for instanceof     //
                                       // Object, but that must be Object     //
                                       // loaded in same window as vis.js...  //
            data        = new $wnd.Object();
            data.x      = parseFloat(csvArray[row][0]);
            data.y      = parseFloat(csvArray[row][1]);
            data.z      = parseFloat(csvArray[row][2]);
            data.filter = parseFloat(csvArray[row][3]);
            dataset.add(data);
         }
         else if (csvArray[row].length == 5)
         {
            data        = new $wnd.Object();
            data.x      = parseFloat(csvArray[row][0]);
            data.y      = parseFloat(csvArray[row][1]);
            data.z      = parseFloat(csvArray[row][2]);
            data.style  = parseFloat(csvArray[row][3]);
            data.filter = parseFloat(csvArray[row][4]);
            dataset.add(data);
         }
         else
         {
            data       = new $wnd.Object();
            data.x     = parseFloat(csvArray[row][0]);
            data.y     = parseFloat(csvArray[row][1]);
            data.z     = parseFloat(csvArray[row][2]);
            data.style = parseFloat(csvArray[row][3]);
            dataset.add(data);
         }
      }

      return dataset;
   }

   function graph3dNullMouseWheelHandler(event)
   {
   }

   function drawChart3D(chartData)
   {
      var arrayData =
         $wnd.$.csv.toArrays(
            chartData, {onParseValue: $wnd.$.csv.hooks.castToScalar});

      if (arrayData[0].length < 3)
      {
         console.log("creating dummy data");

                                       // create dummy data                   //
         var dataset  = new $wnd.vis.DataSet();
         var counter  = 0;
         var steps    = 50;            // num datapoints will be steps*steps  //
         var axisMax  = 314;
         var axisStep = axisMax / steps;

         for (var x = 0; x < axisMax; x+=axisStep)
         {
            for (var y = 0; y < axisMax; y+=axisStep)
            {
               var value     = (Math.sin(x/50) * Math.cos(y/50) * 50 + 50);

                                       // vis.js will test for instanceof     //
                                       // Object, but that must be Object     //
                                       // loaded in same window as vis.js...  //
               var data   = new $wnd.Object();
               data.id    = counter++;
               data.x     = x;
               data.y     = y;
               data.z     = value;
               data.style = value;

               dataset.add(data);
            }
         }
      }
      else
      {
         var dataset = convertArrayToVizDataset(arrayData);
      }
                                       // specify dummy options               //
      options =
      {
         width:           '100%',
         height:          '360px',
         style:           'surface',
         showPerspective: true,
         showGrid:        true,
         showShadow:      false,
         keepAspectRatio: false,
         verticalRatio:   0.4,
         xLabel:          'xAxisLabel',
         yLabel:          'yAxisLabel',
         zLabel:          'zAxisLabel',
         cameraPosition:
         {
            horizontal: .8,
            vertical: 0.3,
            distance: 2.2
         }
      };
                                        // instantiate the graph object       //
      var container = $doc.getElementById(chartContainerId);
      var graph3d   = new $wnd.vis.Graph3d(container, dataset, options);

      if (!bEditMode)
      {
         graph3d._onWheel = graph3dNullMouseWheelHandler;
      }
   };

   if (localChartData == null)
   {
      $wnd.$.get(url, function(chartData)
      {
         drawChart3D(chartData);
      });
   }
   else
   {
      drawChart3D(localChartData);
   }

}-*/;
/*------------------------------------------------------------------------------

@name       render - render the specified chart
                                                                              */
                                                                             /**
            Render the specified chart.

@return     void

@history    Mon Aug 29, 2016 13:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public void render()
{
   JavaScriptObject nativeOptions = createNativeOptions();

   int    idx    = url.indexOf(";");
   String srcURL = idx >= 0 ? url.substring(0, idx) : url;

   if (kCHART_SURFACE_CHART.equals(type))
   {
      draw3DChartNative(
         chartContainerId, data, srcURL, type, nativeOptions, bEditMode);
   }
   else
   {
      draw2DChartNative(
         chartContainerId, data, srcURL, type, nativeOptions, bEditMode);
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
   JSONObject jsonObject = super.toJSON();
   if (type != null)
   {
      jsonObject.put(kKEY_TYPE, new JSONString(type));
   }
   if (options != null)
   {
      JSONObject jsonOptions = new JSONObject();
      for (String key : options.keySet())
      {
         jsonOptions.put(key, new JSONString(options.get(key)));
      }
      jsonObject.put(kKEY_OPTIONS, jsonOptions);
   }

   return(jsonObject);
}
/*------------------------------------------------------------------------------

@name       uint8ArrayToBytes - get byte array from Uint8Array
                                                                              */
                                                                             /**
            Get byte array from Uint8Array.

@return     byte array from Uint8Array

@param      aBytes      Uint8Array

@history    Mon May 09, 2016 08:46:23 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static byte[] uint8ArrayToBytes(
   Uint8Array aBytes)
{
   byte[] bytes = new byte[aBytes.length()];
   for (int i = 0, iMax = bytes.length; i < iMax; i++)
   {
      bytes[i] = (byte)aBytes.get(i);
   }
   return(bytes);
}
}//====================================// end class ChartGWT -----------------//
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
import com.google.gwt.dom.client.Style.Unit;
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
public static final String kCHART_GRAPHICAL_CHART       = "GraphicalChart";

public static final String kGRAPHICAL_CORRELATION       = "Correlation";
public static final String kGRAPHICAL_FUNCTIONS         = "Functions";
public static final String kGRAPHICAL_CROSS_SECTIONS    = "Cross Sections";
public static final String kGRAPHICAL_UNDO              = "Undo";

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

public static final Map<String,String> kDEFAULT_OPTIONS = Collections.unmodifiableMap(
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
public GraphicalFunctions functions;
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
   functions = new GraphicalFunctions(this);
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

@name       setData - sets the class variable data
                                                                              */
   /**
    Sets the class variable data to the input string

    @return     void

    @history    Mon May 23, 2017 created

    @notes
    */
//------------------------------------------------------------------------------
public void setData(String newData){
   data = newData;
}
/*------------------------------------------------------------------------------

@name       setData - converts the a string array of data to a string in csv form
                                                                              */
   /**
    Converts the a string array of data to a string in csv form

    @return     void

    @history    Mon May 23, 2017 created

    @notes
    */
//------------------------------------------------------------------------------
public void setData(String [][] newData){
   String dataStr = "";
   for (int i=0;i<newData[0].length;i++){
      for (int j=0;j<newData.length;j++){
         dataStr += newData[j][i]+",";
      }
      dataStr = dataStr.substring(0, dataStr.length()-1) +"\n";
   }
   dataStr = dataStr.substring(0,dataStr.length()-1); //remove extra symbols created by loop
   data = dataStr;
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

                                       // Graphical chart ------------------- //
   Element mediaItemGraphicalChart = Document.get().createLIElement();
   mediaItemGraphicalChart.setId("mediaItemGraphicalChart");
   Element mediaItemGraphicalChartAnchor = Document.get().createAnchorElement();
   mediaItemGraphicalChartAnchor.setInnerText("Graphical Chart");
   mediaItemGraphicalChart.appendChild(mediaItemGraphicalChartAnchor);
   Event.sinkEvents(mediaItemGraphicalChart, Event.ONCLICK);
   DOM.setEventListener(mediaItemGraphicalChart, new EventListener()
   {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click"))
         {
            assignChartOption(kCHART_GRAPHICAL_CHART);//for now
         }
      }
   });
   mediaDropdownMenu.appendChild(mediaItemGraphicalChart);

   //graphicalMenuHandler(elementId);
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
         type     = kCHART_PIE_CHART;
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
   else if (kCHART_GRAPHICAL_CHART.equals(option))
   {
      if (!kCHART_GRAPHICAL_CHART.equals(type))
      {
         type     = kCHART_GRAPHICAL_CHART;
         bChanged = true;

      }
   }
   if (bChanged)
   {
      if (!kCHART_GRAPHICAL_CHART.equals(type)){
         removeGraphicalMenuHandler();
      }
      notifyChangeListeners(false);
      render();
   }
}
/*------------------------------------------------------------------------------

@name       graphicalMenuHandler - graphical menu handler
                                                                              */
   /**
    Create the Graphical Options Menu

    @return     void

    @history

    @notes      Called when the Graphical chart type is selected. Removed when it is switched away.
                                                                              */
//------------------------------------------------------------------------------
public void graphicalMenuHandler(String mediaPanelId) {

   final Element mediaElem = Document.get().getElementById(mediaPanelId);

   Element graphicalMenu = Document.get().createDivElement();
   graphicalMenu.addClassName("dropdown graphical-menu");
   graphicalMenu.setId("graphical menu");
   mediaElem.appendChild(graphicalMenu);

   Element menuButton = Document.get().createPushButtonElement();
   menuButton.addClassName("btn btn-secondary dropdown-toggle");
   menuButton.setPropertyString("type", "button");
   menuButton.setPropertyString("data-toggle", "dropdown");
   menuButton.setInnerText("Graphical ");
   graphicalMenu.appendChild(menuButton);

   Element span = Document.get().createSpanElement();
   span.addClassName("caret");
   menuButton.appendChild(span);

   Element mediaDropdownMenu = Document.get().createULElement();
   mediaDropdownMenu.addClassName("dropdown-menu");
   mediaDropdownMenu.setId(mediaPanelId + "dropdown-menu");
   mediaDropdownMenu.getStyle().setMarginTop(0, Unit.PX);
   graphicalMenu.appendChild(mediaDropdownMenu);

                                       //-----------Correlations-------------------//
   Element graphicalItemCorrelation = Document.get().createLIElement();
   graphicalItemCorrelation.setId("graphicalItemCorrelation");
   Element graphicalItemCorrelationAnchor = Document.get().createAnchorElement();
   graphicalItemCorrelationAnchor.setInnerText("Correlation");
   graphicalItemCorrelation.appendChild(graphicalItemCorrelationAnchor);
   Event.sinkEvents(graphicalItemCorrelation, Event.ONCLICK);
   DOM.setEventListener(graphicalItemCorrelation, new EventListener() {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click")) {
            graphicalFunctions(kGRAPHICAL_CORRELATION);
         }
      }
   });
   mediaDropdownMenu.appendChild(graphicalItemCorrelation);

                                       //------------Cross Sections----------------//
   Element graphicalItemCrossSections = Document.get().createLIElement();
   graphicalItemCrossSections.setId("graphicalItemCrossSections");
   Element graphicalItemCrossSectionsAnchor = Document.get().createAnchorElement();
   graphicalItemCrossSectionsAnchor.setInnerText("Cross Sections");
   graphicalItemCrossSections.appendChild(graphicalItemCrossSectionsAnchor);
   Event.sinkEvents(graphicalItemCrossSections, Event.ONCLICK);
   DOM.setEventListener(graphicalItemCrossSections, new EventListener() {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click")) {
            graphicalFunctions(kGRAPHICAL_CROSS_SECTIONS);
         }
      }
   });
   mediaDropdownMenu.appendChild(graphicalItemCrossSections);

                                       //------------Functions---------------------//
   Element graphicalItemFunctions = Document.get().createLIElement();
   graphicalItemFunctions.setId("graphicalItemFunctions");
   Element graphicalItemFunctionsAnchor = Document.get().createAnchorElement();
   graphicalItemFunctionsAnchor.setInnerText("Functions");
   graphicalItemFunctions.appendChild(graphicalItemFunctionsAnchor);
   Event.sinkEvents(graphicalItemFunctions, Event.ONCLICK);
   DOM.setEventListener(graphicalItemFunctions, new EventListener() {
      public void onBrowserEvent(Event event) {

         if (event.getType().equalsIgnoreCase("click")) {
            graphicalFunctions(kGRAPHICAL_FUNCTIONS);
         }
      }
   });
   mediaDropdownMenu.appendChild(graphicalItemFunctions);

                                       //---------------Undo-----------------------//
   Element graphicalItemUndo = Document.get().createLIElement();
   graphicalItemUndo.setId("graphicalItemUndo");
   Element graphicalItemUndoAnchor = Document.get().createAnchorElement();
   graphicalItemUndoAnchor.setInnerText("Undo");
   graphicalItemUndo.appendChild(graphicalItemUndoAnchor);
   Event.sinkEvents(graphicalItemUndo, Event.ONCLICK);
   DOM.setEventListener(graphicalItemUndo, new EventListener() {
      public void onBrowserEvent(Event event) {
         if (event.getType().equalsIgnoreCase("click")) {
            graphicalFunctions(kGRAPHICAL_UNDO);
         }
      }
   });
   mediaDropdownMenu.appendChild(graphicalItemUndo);
}
/*------------------------------------------------------------------------------

@name       removeGraphicalMenuHandler - removes the graphical menu handler
                                                                              */
   /**
    Deletes the graphical menu

    @return     void

    @history

    @notes
                                                                              */
//------------------------------------------------------------------------------
public void removeGraphicalMenuHandler(){
   if (!(Document.get().getElementById("graphical menu") == null)) {
      Document.get().getElementById("graphical menu").removeFromParent();
   }
}
/*------------------------------------------------------------------------------

@name       graphicalFunctions - graphical functions
                                                                              */
   /**
    Contains all the functionality for the Graphical Options

    @return     void

    @history

    @notes
                                                                              */
//------------------------------------------------------------------------------
public void graphicalFunctions(String option) {

   boolean changed = false;

   if (option.equals(kGRAPHICAL_CORRELATION)) {//if selected while active, should be able to remove previous entries
      if(functions.correlationActive){
         functions.removeCorrelations();
      }
      functions.createCorrelationDialog();
      changed = true;
   }
   else if (option.equals(kGRAPHICAL_CROSS_SECTIONS)) {//if selected while active, should turn off
      functions.createCrossSectionHandler();
      changed = true;
   }
   else if (option.equals(kGRAPHICAL_FUNCTIONS)) {//if selected while active, should be able to remove previous entries
      functions.createFunctionsDialog();
      changed = true;
   }
   else if (option.equals(kGRAPHICAL_UNDO)){
      functions.undo();
      changed = true;
   }
   if (changed){
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

    protected static native void drawGraphicalChartNative(
            String           chartContainerId,
            String           localChartData,
            String           url,
            String           chartType,
            JavaScriptObject options,
            boolean          bEditMode)
/*-{
    function drawGraphicalChart(chartData)
    {
        options = {
            hAxis: {title: "X-Axis"},
            vAxis: {title: "Y-Axis"},
            pointSize: 3,
            dataOpacity: .4,
            pointShape: "circle",
            legend: "none"
        };

        var arrayData    = $wnd.$.csv.toArrays(chartData, {onParseValue: $wnd.$.csv.hooks.castToScalar});

        var rawArray = arrayData;

        // Code to get rid of non-numerical characters and convert blank strings to 0

        if (arrayData[0].length > 0) {
            for (var num = 0; num < arrayData.length; num++) {
                for (var index = 0; index < arrayData[0].length; index++) {
                    if (arrayData[num][index] === arrayData[num][index].toString()) {
                        var strHolder = arrayData[num][index].replace(/[^\d.-]/g, '');
                        strHolder = "0" + strHolder;
                        arrayData[num][index] = parseInt(strHolder);
                    }
                }
            }
        }

        // Takes arrayData and spaces out the y values so each point has its own column
        // This allows Google Charts to apply options to each point individually

        var newArray = new Array(arrayData.length + 1);
        for (var i = 0; i < newArray.length; i++) {
            newArray[i] = new Array(arrayData.length + 1);
            if (i === 0) {
                for (var m = 0; m < newArray[0].length; m++) {
                    newArray[0][m] = "";
                }
            }
            else {
                for (var h = 0; h < newArray[i].length; h++) {
                    newArray[i][h] = null;
                }
            }
        }
        for (var k = 0; k < arrayData.length; k++) {
            newArray[k + 1][0] = arrayData[k][0];
            for (var n = 0; n < arrayData[0].length; n++) {
                newArray[k + 1][k+1] = arrayData[k][1];
            }
        }

        // Converts newArray to a DataTable (duh)

        var data         = new $wnd.google.visualization.arrayToDataTable(newArray);

        // Adds a column after every y-value that has the tooltip role
        // Allows every point to have a mouseover displaying each dimension, not just 1st & 2nd

        var tempTooltip;
        for (var r = 0; r < rawArray.length; r++) {
            tempTooltip = "";
            for (var g = 0; g < rawArray[0].length; g++) {
                tempTooltip = tempTooltip + rawArray[r][g] + ", ";
            }
            data.insertColumn(r * 2 + 2, "string", null);
            data.setValue(r, r * 2 + 2, tempTooltip.substring(0, tempTooltip.length - 2));
            data.setColumnProperty(r * 2 + 2, "role", "tooltip");
        }

        if (arrayData[0].length > 2) {

            // Applies the third dimension as a variation of color from red to blue

            var thirdDim = new Array(arrayData.length);
            var tempArray = new Array(arrayData.length);
            for (var t = 0; t < thirdDim.length; t++) {
                thirdDim[t] = arrayData[t][2];
                tempArray[t] = arrayData[t][2];
            }
            var maxValue = tempArray.sort(function(a, b){return a-b})[thirdDim.length - 1];
            var minValue = tempArray.sort(function(a, b){return a-b})[0];
            if (maxValue > minValue) {
                var colorArray = new Array(newArray.length - 1);
                for (var c = 0; c < colorArray.length; c++) {
                    var colorNum = Math.trunc(((thirdDim[c] - minValue) / (maxValue - minValue)) * 255);
                    var red = (255 - colorNum).toString(16);
                    if (red.length === 1) {
                        red = "0" + red;
                    }
                    var green = "00";
                    var blue = (colorNum).toString(16);
                    if (blue.length === 1) {
                        blue = "0" + blue;
                    }
                    colorArray[c] = '#' + red + green + blue;
                }
                options.colors = colorArray;
            }

            // Applies fourth dimension as a variation of point size

            if (arrayData[0].length > 3) {
                var tempNum;
                var fourthDim = new Array(arrayData.length);
                var temp2 = new Array(arrayData.length);
                for (var e = 0; e < fourthDim.length; e++) {
                    fourthDim[e] = arrayData[e][3];
                    temp2[e] = arrayData[e][3];
                }
                var max = temp2.sort(function(a, b){return a-b})[fourthDim.length - 1];
                var min = temp2.sort(function(a, b){return a-b})[0];
                options.series = {};
                for (var l = 0; l < fourthDim.length; l++) {
                    options["series"][l + ""] = {};
                    if (max > min) {
                        tempNum = ((fourthDim[l] - min) / (max - min)) * 30 + 1;
                        options["series"][l + ""] = {pointSize: tempNum};
                    }
                }
            }

            // Applies a fifth dimension as a variation of opacity

            if (arrayData[0].length > 4) {
                var tempNum2;
                var fifthDim = new Array(arrayData.length);
                var temp3 = new Array(arrayData.length);
                for (var f = 0; f < fifthDim.length; f++) {
                    fifthDim[f] = arrayData[f][4];
                    temp3[f] = arrayData[f][4];
                }
                var max2 = temp3.sort(function(a, b){return a-b})[fifthDim.length - 1];
                var min2 = temp3.sort(function(a, b){return a-b})[0];
                if (max2 > min2) {
                    for (var b = 0; b < fifthDim.length; b++) {
                        tempNum2 = ((fifthDim[b] - min2) / (max2 - min2)) * .8 + .1;
                        options["series"][b + ""]["dataOpacity"] = tempNum2;
                    }
                }
            }
        }

        var chartWrapper =
            new $wnd.google.visualization.ChartWrapper(
                {
                    chartType:   "ScatterChart",
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
            drawGraphicalChart(chartData);
        });
    }
    else
    {
        drawGraphicalChart(localChartData);
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
   else if (kCHART_GRAPHICAL_CHART.equals(type)) {
      graphicalMenuHandler(elementId);
      functions.updateGraphicalElements(elementId);
      drawGraphicalChartNative(chartContainerId, data, srcURL, type, nativeOptions, bEditMode);
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

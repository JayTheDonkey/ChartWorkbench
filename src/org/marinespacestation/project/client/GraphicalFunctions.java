package org.marinespacestation.project.client;



import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.i18n.client.NumberFormat;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

import com.google.gwt.user.client.ui.*;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class GraphicalFunctions {

    public boolean bestFitActive, crossSectionsActive, correlationActive;

    public String [][] data;
    public String elementID;
    protected int scale = 50;

    public double correlation;

    public ChartGWT chartGWT;

/*------------------------------------------------------------------------------

@name       GraphicalFunctions - Graphical Functions default constructor
                                                                              */
    /**
     Default constructor

     @return     void

     @history    2017 ish created

     @notes
     */
//------------------------------------------------------------------------------

    public GraphicalFunctions(ChartGWT tempChartGWT){
        chartGWT = tempChartGWT;
        String dataStr = chartGWT.data;
        //TODO this does not handle commas in terms eg. "1,324"
        String   row   = dataStr.substring(0, dataStr.indexOf('\n'));
        String[] cols  = row.split(",");
        dataStr = dataStr.replace("\"", "");
        dataStr = dataStr.replace("\r", "");//removes windows carriage returns
        dataStr = dataStr.replace("\n",",");
        String[] values = dataStr.split(",");
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        for (int i=0;i<cols.length;i++){
            ArrayList<String> axes = new ArrayList<>();
            int index = i;
            while (index < values.length){
                axes.add(values[index]);
                index += cols.length;
            }
            dataList.add(axes);
        }
        String[][] tempData = new String [dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> rows = dataList.get(i);
            tempData[i] = rows.toArray(new String[rows.size()]);
        }
        data = tempData;
        bestFitActive = false;
        crossSectionsActive = false;
        correlationActive = false;
    }
/*------------------------------------------------------------------------------

@name       createdLineOfBestFitDialog - creates a dialog for Line of Best Fit creation
                                                                              */
    /**
     creates a DialogBox for the Line of Best Fix creation process

     @return     void

     @history    2017 ish created

     @notes
     */
//------------------------------------------------------------------------------
    public void createLineOfBestFitDialog(){

        final DialogBox dialog = new DialogBox(false);
        dialog.setPopupPosition(400, 300);
        dialog.setText("Line of Best Fit");
        dialog.setAnimationEnabled(true);
        dialog.setGlassEnabled(true);

        Label label = new Label("Choose two axes");
        Label axis1 = new Label("1st axis:");
        Label axis2 = new Label("2nd axis:");

        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("verticalPanel");
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);

        final ListBox axesBox1 = new ListBox();//this should actually have an entry for each axis of the data
        axesBox1.addItem("X Axis");
        axesBox1.addItem("Y Axis");

        final ListBox axesBox2 = new ListBox();
        axesBox2.addItem("X Axis");
        axesBox2.addItem("Y Axis");

        if (data.length >= 3){
            axesBox1.addItem("Z Axis");
            axesBox2.addItem("Z Axis");
        }
        if (data.length >= 4){
            axesBox1.addItem("W Axis");
            axesBox2.addItem("W Axis");
        }
        if (data.length == 5){
            axesBox1.addItem("V Axis");
            axesBox2.addItem("V Axis");
        }

        HorizontalPanel listBoxLayer1 = new HorizontalPanel();
        listBoxLayer1.setHeight("100");
        listBoxLayer1.setWidth("300");
        listBoxLayer1.setStyleName("listBoxPanel");
        listBoxLayer1.add(axis1);
        listBoxLayer1.add(axesBox1);

        panel.add(listBoxLayer1);

        HorizontalPanel listBoxLayer2 = new HorizontalPanel();
        listBoxLayer2.setHeight("100");
        listBoxLayer2.setWidth("300");
        listBoxLayer2.setStyleName("listBoxPanel");
        listBoxLayer2.add(axis2);
        listBoxLayer2.add(axesBox2);

        panel.add(listBoxLayer2);

        Button ok = new Button("Enter");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int axis1 = axesBox1.getSelectedIndex();
                int axis2 = axesBox2.getSelectedIndex();
                generateLineOfBestFit(axis1, axis2);
                dialog.hide();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        HorizontalPanel buttonLayer = new HorizontalPanel();
        buttonLayer.setHeight("100");
        buttonLayer.setWidth("300");
        buttonLayer.addStyleName("buttonPanel");
        buttonLayer.add(ok);
        buttonLayer.add(cancel);

        panel.add(buttonLayer);

        dialog.setWidget(panel);
        dialog.center();
    }

    public void generateLineOfBestFit(int a, int b){
        //TODO Chase's program goes here
        /* how the fuck are we going to display the line
            Options:
                1: add a layer on top of the chart
                    (pros: easy to remove, cons: hard to generate and have it look good)
                2: modify the data set
                    (pros: easy to generate and make look good, cons: hard to remove)
                                                                                        */
        bestFitActive = true;
    }

    public void removeLineOfBestFit(){
        bestFitActive = false;
    }

    public void createCorrelationDialog(){

        final DialogBox dialog = new DialogBox(false);
        dialog.setPopupPosition(400, 300);
        dialog.setText("Correlation");
        dialog.setAnimationEnabled(true);
        dialog.setGlassEnabled(true);

        Label label = new Label("Choose two axes to find the coefficient");
        Label label2 = new Label("of correlation between their data");
        Label axis1 = new Label("1st axis:");
        Label axis2 = new Label("2nd axis:");

        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("verticalPanel");
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);
        panel.add(label2);

        final ListBox axesBox1 = new ListBox();//this should actually have an entry for each axis of the data
        axesBox1.addItem("X Axis");
        axesBox1.addItem("Y Axis");

        final ListBox axesBox2 = new ListBox();
        axesBox2.addItem("X Axis");
        axesBox2.addItem("Y Axis");

        if (data.length >= 3){
            axesBox1.addItem("Z Axis");
            axesBox2.addItem("Z Axis");
        }
        if (data.length >= 4){
            axesBox1.addItem("W Axis");
            axesBox2.addItem("W Axis");
        }
        if (data.length == 5){
            axesBox1.addItem("V Axis");
            axesBox2.addItem("V Axis");
        }

        HorizontalPanel listBoxLayer1 = new HorizontalPanel();
        listBoxLayer1.setHeight("100");
        listBoxLayer1.setWidth("300");
        listBoxLayer1.setStyleName("listBoxPanel");
        listBoxLayer1.add(axis1);
        listBoxLayer1.add(axesBox1);

        panel.add(listBoxLayer1);

        HorizontalPanel listBoxLayer2 = new HorizontalPanel();
        listBoxLayer2.setHeight("100");
        listBoxLayer2.setWidth("300");
        listBoxLayer2.setStyleName("listBoxPanel");
        listBoxLayer2.add(axis2);
        listBoxLayer2.add(axesBox2);

        panel.add(listBoxLayer2);

        Button ok = new Button("Calculate");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int axis1 = axesBox1.getSelectedIndex();
                int axis2 = axesBox2.getSelectedIndex();
                calculateCorrelation(axis1, axis2);
                dialog.hide();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        HorizontalPanel buttonLayer = new HorizontalPanel();
        buttonLayer.setHeight("100");
        buttonLayer.setWidth("300");
        buttonLayer.addStyleName("buttonPanel");
        buttonLayer.add(ok);
        buttonLayer.add(cancel);

        panel.add(buttonLayer);

        dialog.setWidget(panel);
        dialog.center();
    }
    public void calculateCorrelation(int a, int b){//both data sets should have same length
        /* Array Formatting */
        boolean removeFirst = false;
        LinkedList<String> tempA = new LinkedList<>(Arrays.asList(data[a]));
        LinkedList<String> tempB = new LinkedList<>(Arrays.asList(data[b]));
        try {//the logic here is that the axes might have titles. this checks for those and removes them
            Integer.parseInt(tempA.get(0));
        }
        catch (NumberFormatException e){
            removeFirst = true;
        }
        if (removeFirst){
            tempA.remove(0);
            tempB.remove(0);
        }
        Double[] axisA = new Double[tempA.size()];
        Double[] axisB = new Double[tempB.size()];
        try {
            for (int i = 0; i < axisA.length; i++) { //parse axes into ints
                axisA[i] = Double.parseDouble(tempA.get(i));
                axisB[i] = Double.parseDouble(tempB.get(i));
            }
            /* Calculate Correlation */
            //calculate means of the data sets
            double meanA = 0;
            double meanB = 0;
            for (int i = 0; i < axisA.length; i++) {
                meanA += axisA[i];
                meanB += axisB[i];
            }
            meanA = meanA / axisA.length;
            meanB = meanB / axisB.length;
            //calculate the top half of the correlation equation
            double topValue = 0;
            double bottomA = 0;
            double bottomB = 0;
            for (int i = 0; i < axisA.length; i++) {
                topValue += (axisA[i] - meanA) * (axisB[i] - meanB);
                bottomA += Math.pow((axisA[i] - meanA), 2);
                bottomB += Math.pow((axisB[i] - meanB), 2);
            }
            //calculate the bottom half
            bottomA = Math.pow(bottomA, .5);
            bottomB = Math.pow(bottomB, .5);
            //put it all together
            correlation = topValue / (bottomA * bottomB);
            correlationActive = true;
            chartGWT.render();
        }
        catch (NumberFormatException e){
            createErrorMessage("One or more of the axes is a data set of strings");
        }
    }

    public void removeCorrelations(){
        correlationActive = false;
        Document.get().getElementById("correlationDiv").removeFromParent();
    }

    public void createCrossSectionHandler(){
        final DialogBox dialogCS = new DialogBox(false, true);
        dialogCS.setPopupPosition(700, 300);
        dialogCS.setText("Cross Sections");
        dialogCS.setAnimationEnabled(true);
        dialogCS.setGlassEnabled(true);

        Label labelCS = new Label("Generate cross sections");

        Label labelDir = new Label("Constant:");
        labelDir.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        final ListBox directionBox = new ListBox();
        directionBox.addItem("X=C");
        directionBox.addItem("Y=C");
        directionBox.addItem("Z=C");


        Label labelAx1 = new Label("Axis 1:");
        labelAx1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        final ListBox axesBox1 = new ListBox();
        axesBox1.addItem("X Axis");
        axesBox1.addItem("Y Axis");
        axesBox1.addItem("Z Axis");


        Label labelAx2 = new Label("Axis 2:");
        labelAx2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        final ListBox axesBox2 = new ListBox();
        axesBox2.addItem("X Axis");
        axesBox2.addItem("Y Axis");
        axesBox2.addItem("Z Axis");

        Button genCS = new Button("Enter");


        Button closeCS = new Button("Cancel");
        closeCS.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogCS.hide();
            }
        });

        //Slider
        final InputElement sliderElem = InputElement.as(DOM.createElement("input"));
        sliderElem.setPropertyString("type", "range");
        sliderElem.setAttribute("min",  "0");
        sliderElem.setAttribute("max",  "100");
        sliderElem.setAttribute("step", "10");
        sliderElem.setValue(Integer.toString(scale));

        Event.sinkEvents(sliderElem, Event.ONCHANGE);
        Event.setEventListener(sliderElem, new EventListener()
{
        public void onBrowserEvent(Event event)
        {
            if (event.getType().equalsIgnoreCase("change"))
            {
                scale = Integer.parseInt(sliderElem.getValue());
             }
        }
        });

        HorizontalPanel buttonCS = new HorizontalPanel();
        buttonCS.setWidth("500");
        buttonCS.setHeight("200");
        buttonCS.add(genCS);
        buttonCS.add(closeCS);

        genCS.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int chosen = directionBox.getSelectedIndex();
                int a1 = axesBox1.getSelectedIndex();
                int a2 = axesBox2.getSelectedIndex();
                createCrossSections(chosen,a1,a2, scale);
                dialogCS.hide();
            }
        });
        //Adding elements to panel
        VerticalPanel panelCS = new VerticalPanel();
        panelCS.setWidth("500");
        panelCS.setHeight("400");
        panelCS.setHorizontalAlignment(HasAutoHorizontalAlignment.ALIGN_CENTER);
        panelCS.add(labelCS);
        panelCS.add(labelDir);
        panelCS.add(directionBox);
        panelCS.add(labelAx1);
        panelCS.add(axesBox1);
        panelCS.add(labelAx2);
        panelCS.add(axesBox2);
        panelCS.getElement().appendChild(sliderElem);
        panelCS.add(buttonCS);

        dialogCS.setWidget(panelCS);
        dialogCS.show();
    }

    public void createCrossSections(int constant, int a1,int a2, int value){
        boolean removeFirst = false;
        LinkedList<String> axis1 = new LinkedList<>(Arrays.asList(data[a1]));
        LinkedList<String> axis2 = new LinkedList<>(Arrays.asList(data[a2]));
        LinkedList<String> axisC = new LinkedList<>(Arrays.asList(data[constant]));
        try {//the logic here is that the axes might have titles. this checks for those and removes them
            Integer.parseInt(axis1.get(0));
        }
        catch (NumberFormatException e){
            removeFirst = true;
        }
        if (removeFirst){
            axis1.remove(0);
            axis2.remove(0);
            axisC.remove(0);
        }
        int[] axiss1 = new int[axis1.size()];
        int[] axiss2 = new int[axis2.size()];
        int[] axissC = new int[axisC.size()];
        try {
            for (int i = 0; i < axiss1.length; i++) { //parse axes into ints
                axiss1[i] = Integer.parseInt(axis1.get(i));
                axiss2[i] = Integer.parseInt(axis2.get(i));
                axissC[i] = Integer.parseInt(axisC.get(i));
            }
        /* Calculate Cross Sections */
        for(int i = 0; i<axissC.length; i++){
            axissC[i] = value;
        }
        chartGWT.render();
        }
        catch (NumberFormatException e){
            createErrorMessage("One or more of the axes is a data set of strings");
        }
    }

    public void removeCrossSections(){
        crossSectionsActive = false;
    }

    public void createFunctionsDialog(){
        final DialogBox dialog = new DialogBox(false);
        dialog.setPopupPosition(400, 300);
        dialog.setText("Functions");
        dialog.setAnimationEnabled(true);
        dialog.setGlassEnabled(true);

        Label label1 = new Label("Choose an axis you would like to modify");
        Label listboxLabel = new Label("Modify");
        Label label2 = new Label("Now choose the function you would like to");
        Label label2cont = new Label("modify the selected axis by");
        Label textboxLabel = new Label("Enter Function: ");

        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("verticalPanel");
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label1);

        final ListBox axesBox = new ListBox();//this should actually have an entry for each axis of the data
        axesBox.addItem("X Axis");
        axesBox.addItem("Y Axis");
        if (data.length >= 3){
            axesBox.addItem("Z Axis");
        }
        if (data.length >= 4){
            axesBox.addItem("W Axis");
        }
        if (data.length == 5){
            axesBox.addItem("V Axis");
        }
        HorizontalPanel listBoxLayer = new HorizontalPanel();
        listBoxLayer.setHeight("100");
        listBoxLayer.setWidth("300");
        listBoxLayer.setStyleName("listBoxPanel");
        listBoxLayer.add(listboxLabel);
        listBoxLayer.add(axesBox);

        panel.add(listBoxLayer);
        panel.add(label2);
        panel.add(label2cont);

        final ListBox functionBox = new ListBox();
        functionBox.addItem("Add a Number to Axis");
        functionBox.addItem("Multiply Axis by a Number");
        functionBox.addItem("Raise Axis to a Power");

        HorizontalPanel functionBoxLayer = new HorizontalPanel();
        functionBoxLayer.setHeight("100");
        functionBoxLayer.setWidth("300");
        functionBoxLayer.setStyleName("listBoxPanel");
        functionBoxLayer.add(textboxLabel);
        functionBoxLayer.add(functionBox);

        panel.add(functionBoxLayer);

        Button ok = new Button("Enter");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int axis = axesBox.getSelectedIndex();
                String function = functionBox.getSelectedItemText();
                createSecondaryFunctionDialog(axis, function);
                dialog.hide();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        HorizontalPanel buttonLayer = new HorizontalPanel();
        buttonLayer.setHeight("100");
        buttonLayer.setWidth("300");
        buttonLayer.addStyleName("buttonPanel");
        buttonLayer.add(ok);
        buttonLayer.add(cancel);

        panel.add(buttonLayer);

        dialog.setWidget(panel);
        dialog.center();

    }

    public void createSecondaryFunctionDialog(final int axis, final String functionType){
        final DialogBox dialog = new DialogBox(false);
        dialog.setPopupPosition(400, 300);
        dialog.setText("Input Value");
        dialog.setAnimationEnabled(true);
        dialog.setGlassEnabled(true);

        Label label = new Label(functionType);
        Label textBoxLabel = new Label("enter a number");

        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("verticalPanel");
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);

        final TextBox textBox= new TextBox();

        HorizontalPanel textBoxLayer = new HorizontalPanel();
        textBoxLayer.setHeight("100");
        textBoxLayer.setWidth("300");
        textBoxLayer.setStyleName("textBoxPanel");
        textBoxLayer.add(textBoxLabel);
        textBoxLayer.add(textBox);

        panel.add(textBoxLayer);

        Button ok = new Button("Enter");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createFunctions(axis, functionType, Double.parseDouble(textBox.getText()));
                dialog.hide();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        HorizontalPanel buttonLayer = new HorizontalPanel();
        buttonLayer.setHeight("100");
        buttonLayer.setWidth("300");
        buttonLayer.addStyleName("buttonPanel");
        buttonLayer.add(ok);
        buttonLayer.add(cancel);

        panel.add(buttonLayer);

        dialog.setWidget(panel);
        dialog.center();
    }

    public void createFunctions(int axis, String functionType, Double value) {
        for (int i=0;i<data[axis].length;i++){
            try {
                if (functionType.equals("Add a Number to Axis")) {
                    data[axis][i] = (Double.parseDouble(data[axis][i]) + value) + "";
                }
                else if (functionType.equals("Multiply Axis by a Number")) {
                    data[axis][i] = (Double.parseDouble(data[axis][i]) * value) + "";
                }
                else {
                    data[axis][i] = (Math.pow(Double.parseDouble(data[axis][i]), value)) + "";
                }
            }
            catch (NumberFormatException e){

            }
        }
        chartGWT.setData(data);
        chartGWT.render();
    }

    public void createTransformationsHandler(){

    }

    public void updateGraphicalElements(String mediaPanelID){
        elementID = mediaPanelID;
        final Element mediaPanel = Document.get().getElementById(elementID);
        //display correlation
        if (correlationActive){
            Element correlationDiv = Document.get().createDivElement();
            correlationDiv.addClassName("correlationDiv");
            correlationDiv.setId("correlationDiv");
            mediaPanel.appendChild(correlationDiv);

            Text correlationText = Document.get().createTextNode("Correlation: "+ Double.toString(correlation));
            correlationDiv.appendChild(correlationText);
        }
    }

    public void createErrorMessage(String message){
        final DialogBox dialog = new DialogBox(false);
        dialog.setPopupPosition(400, 300);
        dialog.setText("Error");
        dialog.setAnimationEnabled(true);
        dialog.setGlassEnabled(true);

        Label label = new Label(message);

        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("verticalPanel");
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);

        Button ok = new Button("Ok");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        HorizontalPanel buttonLayer = new HorizontalPanel();
        buttonLayer.setHeight("100");
        buttonLayer.setWidth("300");
        buttonLayer.addStyleName("buttonPanel");
        buttonLayer.add(ok);
        buttonLayer.add(cancel);

        panel.add(buttonLayer);

        dialog.setWidget(panel);
        dialog.center();

    }
}

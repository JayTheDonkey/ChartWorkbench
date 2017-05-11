package org.marinespacestation.project.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class GraphicalFunctions {

    public boolean bestFitActive, crossSectionsActive;

    public String [][] data;
    public String elementID;

    public GraphicalFunctions(String dataStr){ //fixme: this causes the chart not to load

        String   row   = dataStr.substring(0, dataStr.indexOf('\n'));
        String[] cols  = row.split(",");
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
        data = new String [dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> rows = dataList.get(i);
            data[i] = rows.toArray(new String[rows.size()]);
        }
    }

    public void updateElementID(String mediaPanelID){
        elementID = mediaPanelID;
    }

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
        int[] axisA = new int[tempA.size()];
        int[] axisB = new int[tempB.size()];
        try {
            for (int i = 0; i < axisA.length; i++) { //parse axes into ints
                axisA[i] = Integer.parseInt(tempA.get(i));
                axisB[i] = Integer.parseInt(tempB.get(i));
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
            for (int i = 0; i < axisA.length; i++) {
                topValue += (axisA[i] - meanA) * (axisB[i] - meanB);
            }
            //calculate the bottom half
            double bottomA = 0;
            double bottomB = 0;
            for (int i = 0; i < axisA.length; i++) {
                bottomA += Math.pow((axisA[i] - meanA), 2);
                bottomB += Math.pow((axisB[i] - meanB), 2);
            }
            bottomA = Math.pow(bottomA, .5);
            bottomB = Math.pow(bottomB, .5);
            //put it all together
            double correlation = topValue / (bottomA * bottomB);
            createErrorMessage(Double.toString(correlation));
        }
        catch (NumberFormatException e){
            createErrorMessage("One or more of the axes is a data set of strings");
        }
    }

    public void createCrossSectionHandler(){
        crossSectionsActive = true;
    }

    public void removeCrossSections(){
        crossSectionsActive = false;
    }

    public void createFunctions(){

    }

    public void createTransformationsHandler(){

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

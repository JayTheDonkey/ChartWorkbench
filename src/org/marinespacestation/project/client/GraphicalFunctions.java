package org.marinespacestation.project.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class GraphicalFunctions {

    public boolean bestFitActive, crossSectionsActive;

    public void createLineOfBestFitDialog(){

        final DialogBox dialog = new DialogBox(false);
        dialog.setPopupPosition(400, 300);
        dialog.setText("Line of Best Fit");
        dialog.setAnimationEnabled(true);
        dialog.setGlassEnabled(true);

        Label label = new Label("Choose two axes");
        Label axis1 = new Label("1st axis:");
        final Label axis2 = new Label("2nd axis:");

        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("verticalPanel");
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);

        final ListBox axesBox1 = new ListBox();
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

    public void calculateCorrelation(){

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
}

package org.marinespacestation.project.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class GraphicalFunctions {

    public boolean bestFitActive, crossSectionsActive;

    public void createLineOfBestFit(){

        final DialogBox dialog = new DialogBox(true);
        dialog.setPopupPosition(400, 300);
        dialog.setText("Test BOI");
        dialog.setAnimationEnabled(true);
        dialog.setGlassEnabled(true);

        Button ok = new Button("OK");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        Button ko = new Button("KO!");
        ko.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        Label label = new Label("Some sample text");

        VerticalPanel panel = new VerticalPanel();
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setSpacing(10);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);

        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setHeight("100");
        horizontalPanel.setWidth("300");
        horizontalPanel.setSpacing(50);
        horizontalPanel.addStyleName("horizontalPanel");
        horizontalPanel.add(ok);
        horizontalPanel.add(ko);
        panel.add(horizontalPanel);

        dialog.setWidget(panel);
        dialog.center();
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

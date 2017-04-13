package org.marinespacestation.project.client;


import com.google.gwt.user.client.ui.DialogBox;

public class GraphicalFunctions {

    public boolean bestFitActive, crossSectionsActive;

    public void createLineOfBestFit(){

        DialogBox dialog = new DialogBox(true);
        dialog.setSize("100","50");
        dialog.setPopupPosition(400, 500);
        dialog.setText("Test BOI");
        dialog.show();
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

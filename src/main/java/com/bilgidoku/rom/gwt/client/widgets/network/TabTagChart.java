package com.bilgidoku.rom.gwt.client.widgets.network;

import com.bilgidoku.rom.gwt.client.widgets.network.Network;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.visualization.client.DataTable;

public class TabTagChart extends Composite {
	private Network network = null;
	
	private Network.Options options = Network.Options.create();
	
	public TabTagChart() {
		// Create nodes table with some data
        final DataTable nodes = DataTable.create();
        nodes.addColumn(DataTable.ColumnType.NUMBER, "id");
        nodes.addColumn(DataTable.ColumnType.STRING, "text");
        
        nodes.addRow();        
        int i = 0;
        nodes.setValue(i, 0, 1);
        nodes.setValue(i, 1, "Node 1");
        
        nodes.addRow(); 
        i++;
        nodes.setValue(i, 0, 2);
        nodes.setValue(i, 1, "Node 2");
        
        nodes.addRow(); 
        i++;
        nodes.setValue(i, 0, 3);
        nodes.setValue(i, 1, "Node 3");

        // Create links table with some data
        final DataTable links = DataTable.create();
        links.addColumn(DataTable.ColumnType.NUMBER, "from");
        links.addColumn(DataTable.ColumnType.NUMBER, "to");
        
        links.addRow(); 
        i = 0;
        links.setValue(i, 0, 1);
        links.setValue(i, 1, 2);
        
        links.addRow(); 
        i++;
        links.setValue(i, 0, 1);
        links.setValue(i, 1, 3);
        
        links.addRow(); 
        i++;
        links.setValue(i, 0, 2);
        links.setValue(i, 1, 3);

        // Create options
        
        options.setWidth("300px");
        options.setHeight("300px");
        
        // create the visualization, with data and options
        network = new Network(nodes, links, options);
        
		initWidget(network);
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				network.draw(nodes, links, options);
				
			}
		});
		

	}

}

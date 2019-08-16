package com.baeldung;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("div")
@StyleSheet("frontend://src/styles/div-layout-styles.css")
public class DivLayoutView extends Div {

	private static final long serialVersionUID = -2468700038262515731L;

	public DivLayoutView() {
	    // Instantiate layouts
	    HorizontalLayout header = new HorizontalLayout();
	    VerticalLayout navBar = new VerticalLayout();
	    VerticalLayout content = new VerticalLayout();
	    Div center = new Div();
	    HorizontalLayout footer = new HorizontalLayout();
	
	    // Configure layouts
	    setSizeFull();
	    header.setPadding(true);
	    footer.setPadding(true);
	    addClassName("main-view");
	    header.addClassName("header");
	    navBar.addClassName("navbar");
	    center.addClassName("center");
	    
	    content.addClassName("content");
	    content.add(new Label("..................."));
	    
	    footer.addClassName("footer");
	
	    // Compose layout
	    center.add(navBar, content);
	    add(header, center, footer);
	  }
}
package com.baeldung;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("test")
public class TestView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public TestView() {
		super();
		
		Button button = new Button("simple");
		
		button.addClickListener(e->{
			Notification.show("Thanks for clicking!");
			button.getUI().ifPresent(ui -> ui.navigate("div"));
		});
		
		button.getElement().addEventListener("mouseover", e -> {
			  System.out.println("'mouseover' event happened");
			});
		
		button.setIcon(VaadinIcon.ADOBE_FLASH.create());
		
		add(button);
		
	   // Instantiate layouts
	   HorizontalLayout header = new HorizontalLayout();
	   VerticalLayout navBar = new VerticalLayout();
	   VerticalLayout content = new VerticalLayout();
	   HorizontalLayout center = new HorizontalLayout();
	   HorizontalLayout footer = new HorizontalLayout();
	
	   // Configure layouts
	   setSizeFull();
	   setPadding(false);
	   setSpacing(false);
	   
	   header.setWidth("100%");
	   header.setPadding(true);
	   header.add(new Label("Header..."));
	   
	   center.setWidth("100%");
	   navBar.setWidth("200px");
	   content.setWidth("100%");	
	   
	   footer.setWidth("100%");
	   footer.setPadding(true);
	
	   // Compose layout
	   center.add(navBar, content);
	   center.setFlexGrow(1, navBar);
	   add(header, center, footer);
	   expand(center);
		
	}

	
	
}

package com.example.application.views.list;

import com.example.application.data.entities.Notification;
import com.example.application.services.NotificationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import java.util.Map;


@Route(value = "/notifications", layout = MainLayout.class)
@PageTitle("Notifications")
public class NotificationView extends VerticalLayout {
    Grid<Notification> grid = new Grid<>(Notification.class);
    TextField filterText = new TextField();
    NotificationForm form;
    Button assignUserButton;
    NotificationService notificationService;
    Notification selectedNotification;

    public NotificationView(NotificationService notificationService) {
        this.notificationService = notificationService;

        addClassName("notification-view");
        setSizeFull();
        configureGrid();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new NotificationForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveNotification);
        form.addDeleteListener(this::deleteNotification);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveNotification(NotificationForm.SaveEvent event) {
        notificationService.saveNotification(event.getNotification());
        updateList();
        closeEditor();
    }

    private void deleteNotification(NotificationForm.DeleteEvent event) {
        notificationService.deleteNotification(event.getNotification());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("notification-grid");
        grid.setSizeFull();


        grid.setColumns("notificationId", "title"
                , "content");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                assignUserButton.setEnabled(false);
            } else {
                assignUserButton.setEnabled(true);
            }
            editNotification(event.getValue());

        });

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addNotificationButton = new Button("Add notification");
        addNotificationButton.addClickListener(click -> addNotification());

        //assignUserButton = new Button("Assign User");
        //assignUserButton.addClickListener(click -> assignUser());
        assignUserButton = new Button("Assign", event -> UI.getCurrent().navigate(AssignUserView.class, new RouteParameters("notificationId", Long.toString(selectedNotification.getNotificationId()))));
        assignUserButton.setEnabled(false);
        var toolbar = new HorizontalLayout(filterText, addNotificationButton, assignUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editNotification(Notification notification) {
        selectedNotification = notification;
        if (notification == null) {
            closeEditor();
        } else {
            form.setNotification(notification);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setNotification(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addNotification() {
        grid.asSingleSelect().clear();
        editNotification(new Notification());
    }

    private void assignUser() {
        System.out.println("ASSIGN USER BUTTON CLICKED");
        Notification selectedNotification = grid.asSingleSelect().getValue();
        if (selectedNotification != null) {
            System.out.println("Notification Object is not null");
            // Get the notificationId
            Long notificationId = selectedNotification.getNotificationId();
            QueryParameters queryParameters =
                    QueryParameters.simple(Map.of("notificationId", notificationId.toString()));


            // Navigate to AssignUser view with the notificationId as a parameter
            //assignUserButton.getUI().ifPresent(ui -> ui.navigate(AssignUserView.class, queryParameters));
            UI.getCurrent().navigate(AssignUserView.class, queryParameters);
        }
    }

    private void updateList() {
        grid.setItems(notificationService.findAllNotifications(filterText.getValue()));
    }
}
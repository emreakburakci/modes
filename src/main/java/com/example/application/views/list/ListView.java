package com.example.application.views.list;

import com.example.application.data.entities.User;
import com.example.application.services.NotificationService;
import com.example.application.services.UserNotificationService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Kullanıcılar")
public class ListView extends VerticalLayout {
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    UserForm form;
    UserService service;
    NotificationService notificationService;
    UserNotificationService userNotificationService;

    public ListView(UserService service, NotificationService notificationService, UserNotificationService userNotificationService) {
        this.service = service;
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;

        addClassName("list-view");
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
        form = new UserForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveUser);
        form.addDeleteListener(this::deleteUser);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveUser(UserForm.SaveEvent event) {
        service.saveUser(event.getUser());
        updateList();
        closeEditor();
    }

    private void deleteUser(UserForm.DeleteEvent event) {
        service.deleteUser(event.getUser());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setSizeFull();


        grid.setColumns("identityNumber", "firstName", "lastName"
                , "password", "email", "country", "region", "subregion", "district", "postalCode", "lastConfirmationDateTime"
                , "lastConfirmationStatus", "GSMConfirmationCode", "phoneNumber");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.getColumnByKey("identityNumber").setHeader("Kimlik Numarası");
        grid.getColumnByKey("firstName").setHeader("İsim");
        grid.getColumnByKey("lastName").setHeader("Soyisim");
        grid.getColumnByKey("password").setHeader("Parola");
        grid.getColumnByKey("email").setHeader("E-Posta");
        grid.getColumnByKey("country").setHeader("Ülke");
        grid.getColumnByKey("region").setHeader("Şehir");
        grid.getColumnByKey("subregion").setHeader("İlçe");
        grid.getColumnByKey("district").setHeader("Mahalle");
        grid.getColumnByKey("postalCode").setHeader("Posta Kodu");
        grid.getColumnByKey("lastConfirmationDateTime").setHeader("Son Doğrulama Zamanı");
        grid.getColumnByKey("lastConfirmationStatus").setHeader("Son Doğrulama Durumu");
        grid.getColumnByKey("GSMConfirmationCode").setHeader("SMS Doğrulama Kodu");
        grid.getColumnByKey("phoneNumber").setHeader("Telefon Numarası");


        Grid.Column front = grid.addComponentColumn(user -> {
            if (user.getPictureFront() != null && user.getPictureFront().length > 0) {
                // Create a stream resource for the user's photo
                StreamResource resource = new StreamResource("user-photo.jpg", (InputStreamFactory) () ->
                        new ByteArrayInputStream(user.getPictureFront()));

                // Create an image component to display the photo
                Image image = new Image(resource, "User Photo");

                // Set size and other properties as needed
                image.setWidth("50px");
                image.setHeight("50px");

                return new HorizontalLayout(image);
            } else {
                // If user has no photo, display a placeholder icon
                return new Icon(VaadinIcon.USER);
            }
        }).setHeader("Ön Fotoğraf");

        Grid.Column right = grid.addComponentColumn(user -> {
            if (user.getPictureRight() != null && user.getPictureRight().length > 0) {
                // Create a stream resource for the user's photo
                StreamResource resource = new StreamResource("user-photo.jpg", (InputStreamFactory) () ->
                        new ByteArrayInputStream(user.getPictureRight()));

                // Create an image component to display the photo
                Image image = new Image(resource, "User Photo");

                // Set size and other properties as needed
                image.setWidth("50px");
                image.setHeight("50px");

                return new HorizontalLayout(image);
            } else {
                // If user has no photo, display a placeholder icon
                return new Icon(VaadinIcon.USER);
            }
        }).setHeader("Sağ Profil");

        Grid.Column left = grid.addComponentColumn(user -> {
            if (user.getPictureLeft() != null && user.getPictureLeft().length > 0) {
                // Create a stream resource for the user's photo
                StreamResource resource = new StreamResource("user-photo.jpg", (InputStreamFactory) () ->
                        new ByteArrayInputStream(user.getPictureLeft()));

                // Create an image component to display the photo
                Image image = new Image(resource, "User Photo");

                // Set size and other properties as needed
                image.setWidth("50px");
                image.setHeight("50px");

                return new HorizontalLayout(image);
            } else {
                // If user has no photo, display a placeholder icon
                return new Icon(VaadinIcon.USER);
            }
        }).setHeader("Sol Profil");

        grid.setColumnOrder(front, left, right, grid.getColumnByKey("identityNumber"), grid.getColumnByKey("firstName"), grid.getColumnByKey("lastName")
                , grid.getColumnByKey("password"), grid.getColumnByKey("email"), grid.getColumnByKey("country"), grid.getColumnByKey("region"), grid.getColumnByKey("subregion"), grid.getColumnByKey("district"), grid.getColumnByKey("postalCode"), grid.getColumnByKey("lastConfirmationDateTime")
                , grid.getColumnByKey("lastConfirmationStatus"), grid.getColumnByKey("GSMConfirmationCode"), grid.getColumnByKey("phoneNumber"));
        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue(), false));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("İsme göre filtrele");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addUserButton = new Button("Kullanıcı Ekle");
        addUserButton.addClickListener(click -> addUser());

        var toolbar = new HorizontalLayout(filterText, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editUser(User user, boolean isAddUser) {

        System.out.println("IS ADD USER:" + isAddUser);
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            form.setIsAddUserClicked(isAddUser);

            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User(), true);
    }

    private void updateList() {
        grid.setItems(service.findAllUsers(filterText.getValue()));
    }
}
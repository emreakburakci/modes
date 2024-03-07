package com.example.application.views.list;

import com.example.application.data.entities.Notification;
import com.example.application.data.entities.User;
import com.example.application.data.entities.UserNotification;
import com.example.application.services.NotificationService;
import com.example.application.services.UserNotificationService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route(value = "/assignUser/:notificationId", layout = MainLayout.class)
@PageTitle("Bildirim Atama")

public class AssignUserView extends VerticalLayout implements HasUrlParameter<String> {

    UserService userService;
    NotificationService notificationService;

    UserNotificationService userNotificationService;

    Grid<User> notAssignedUsers = new Grid<>(User.class);
    Grid<User> assignedUsers = new Grid<>(User.class);
    FormLayout notificationInfo;
    FormLayout gridLabels;
    Notification notification;

    Button assignAllButton = new Button("Hepsine Ata");

    Button removeAllButton = new Button("Hepsini çıkar");

    //ResourceBundleUtil rb;

    public AssignUserView(UserService userService, NotificationService notificationService, UserNotificationService userNotificationService) {

        this.userService = userService;
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        //rb = new ResourceBundleUtil((VaadinSession.getCurrent().getAttribute("language").toString()));

        addClassName("hasta-personel");
        setSizeFull();
        notificationInfo = new FormLayout();

        H3 related = new H3("Bildirim Atanmış Kullanıcılar");
        H3 notRelated = new H3("Bildirim Atanmamış Kullanıcılar");

        configureButtons();

        gridLabels = new FormLayout(related, notRelated, removeAllButton, assignAllButton);

        add(notificationInfo, gridLabels, getContent());
    }

    private void configureButtons() {
        removeAllButton.addClickListener(click -> {
            unrelateAll();
        });
        assignAllButton.addClickListener(click -> {
            relateAll();
        });
    }

    private void configureNotificationInfo() {


        TextField notificationId = new TextField("ID");
        notificationId.setValue(Long.toString(notification.getNotificationId()));
        notificationId.setReadOnly(true);

        TextField title = new TextField("Başlık");
        title.setValue(notification.getTitle());
        title.setReadOnly(true);

        TextArea content = new TextArea("İçerik");
        content.setValue(notification.getContent());
        content.setReadOnly(true);


        notificationInfo.add(notificationId, title, content);

        notificationInfo.setColspan(content, 2);


    }


    private HorizontalLayout getContent() {

        configureRelatedPatienceGrid();
        configureNotRelatedPatienceGrid();

        HorizontalLayout content = new HorizontalLayout(assignedUsers, notAssignedUsers);

        content.setFlexGrow(2, assignedUsers);

        content.setFlexGrow(2, notAssignedUsers);

        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    private void configureNotRelatedPatienceGrid() {

        notAssignedUsers.addClassNames("user-grid");
        notAssignedUsers.setSizeFull();
        notAssignedUsers.setColumns("identityNumber", "firstName", "lastName");

        notAssignedUsers.getColumnByKey("identityNumber").setHeader("Kimlik Numarası");
        notAssignedUsers.getColumnByKey("firstName").setHeader("Ad");
        notAssignedUsers.getColumnByKey("lastName").setHeader("Soyad");

        notAssignedUsers.getColumns().forEach(col -> col.setAutoWidth(true));
        notAssignedUsers.getColumns().forEach(col -> col.setAutoWidth(true));
        notAssignedUsers.getColumns().forEach(col -> col.setAutoWidth(true));


        notAssignedUsers.addComponentColumn(user -> {
            Button button = new Button("", VaadinIcon.ARROW_LEFT.create());

            button.addClickListener(e -> {

                relate(user);
            });
            return button;
        });


        notAssignedUsers.asSingleSelect().addValueChangeListener(event -> {
        });
    }

    private void configureRelatedPatienceGrid() {

        assignedUsers.addClassNames("user-grid");
        assignedUsers.setSizeFull();
        assignedUsers.setColumns("identityNumber", "firstName", "lastName");
        assignedUsers.addColumn(user -> user.getNotificationStatus(Long.toString(notification.getNotificationId()))).setHeader("Durum");

        assignedUsers.getColumns().forEach(col -> col.setAutoWidth(true));

        assignedUsers.addComponentColumn(user -> {
            Button button = new Button("", VaadinIcon.ARROW_RIGHT.create());
            button.addClickListener(e -> {

                unRelate(user);
            });
            return button;
        });

        assignedUsers.getColumnByKey("identityNumber").setHeader("Kimlik Numarası");
        assignedUsers.getColumnByKey("firstName").setHeader("Ad");
        assignedUsers.getColumnByKey("lastName").setHeader("Soyad");

        assignedUsers.asSingleSelect().addValueChangeListener(event -> {
        });
    }

    private void unRelate(User user) {
        System.out.println("UNRELATE RUNNED");
        //user.getNotifications().remove(notification);
        UserNotification userNotification = userNotificationService.findById(user, notification);

        user.getUserNotifications().remove(userNotification);
        userNotificationService.delete(userNotification);

        updateAssignedUserGrid();
        updateNotAssignedUserGrid();

        UI.getCurrent().getPage().reload();

        System.out.println("UNRELATE FINISHED");

    }


    private void unrelateAll() {
        System.out.println("UNRELATE ALL RUNNED");
        ListDataProvider<User> dataProvider = (ListDataProvider<User>) assignedUsers.getDataProvider();

        List<User> allUsers = new ArrayList(dataProvider.getItems());

        List<UserNotification> notificationsToRemove = new ArrayList<>();

        for (User user : allUsers) {

            UserNotification userNotification = new UserNotification(user, notification);

            user.getUserNotifications().remove(userNotification);

            notificationsToRemove.add(userNotification);

        }

        userNotificationService.deleteAll(notificationsToRemove);

        updateAssignedUserGrid();
        updateNotAssignedUserGrid();

        UI.getCurrent().getPage().reload();

        System.out.println("UNRELATE ALL FINISHED");

    }

    private void relate(User user) {
        System.out.println("RELATE RUNNED");
        UserNotification userNotification = new UserNotification(user, notification);

        user.getUserNotifications().add(userNotification);

        userNotificationService.saveUserNotification(userNotification);

        updateAssignedUserGrid();
        updateNotAssignedUserGrid();
        UI.getCurrent().getPage().reload();
        System.out.println("RELATE FINISHED");

    }

    private void relateAll() {
        System.out.println("RELATE ALL RUNNED");
        ListDataProvider<User> dataProvider = (ListDataProvider<User>) notAssignedUsers.getDataProvider();

        List<User> allUsers = new ArrayList(dataProvider.getItems());// users in the notAssignedUsers

        List<UserNotification> notificationsToSave = new ArrayList<>();

        for (User user : allUsers) {

            UserNotification userNotification = new UserNotification(user, notification);

            user.getUserNotifications().add(userNotification);

            notificationsToSave.add(userNotification);

        }

        userNotificationService.saveUserNotifications(notificationsToSave);

        updateAssignedUserGrid();
        updateNotAssignedUserGrid();
        UI.getCurrent().getPage().reload();
        System.out.println("RELATE FINISHED");

    }

    private void updateAssignedUserGrid() {
        System.out.println("updateAssignedUserGrid RUNNED");
        Set<UserNotification> userNotifications = notification.getUserNotifications();
        List<User> users = new ArrayList<>();

        for (UserNotification un : userNotifications) {
            users.add(un.getUser());
            System.out.println(un.getUser().getIdentityNumber());
        }

        assignedUsers.setItems(users);

        System.out.println("updateAssignedUserGrid FINISHED");

    }

    private void updateNotAssignedUserGrid() {

        System.out.println("updateNotAssignedUserGrid RUNNED");

        List<User> all = userService.findAllUsers("");

        Set<UserNotification> userNotifications = notification.getUserNotifications();
        List<User> assignedUsers = new ArrayList<>();

        userNotifications.stream().forEach(e -> assignedUsers.add(e.getUser()));


        all.removeIf(p -> {
            for (User setU : assignedUsers) {
                if (p.getIdentityNumber().equals(setU.getIdentityNumber())) {
                    return true;
                }
            }
            return false;
        });

        notAssignedUsers.setItems(all);
        System.out.println("updateNotAssignedUserGrid FINISHED");

    }


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {

        RouteParameters rp = event.getRouteParameters();

        notification = notificationService.findById(Long.parseLong(rp.get("notificationId").get()));

        configureNotificationInfo();
        updateAssignedUserGrid();
        updateNotAssignedUserGrid();
    }

}
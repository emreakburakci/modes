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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route(value = "/assignUser/:notificationId", layout = MainLayout.class)
@PageTitle("Notifications")

public class AssignUserView extends VerticalLayout implements HasUrlParameter<String> {

    UserService userService;
    NotificationService notificationService;

    UserNotificationService userNotificationService;

    Grid<User> notAssignedUsers = new Grid<>(User.class);
    Grid<User> assignedUsers = new Grid<>(User.class);
    FormLayout notificationInfo;
    FormLayout gridLabels;
    Notification notification;

    //ResourceBundleUtil rb;

    public AssignUserView(UserService userService, NotificationService notificationService, UserNotificationService userNotificationService) {

        this.userService = userService;
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        //rb = new ResourceBundleUtil((VaadinSession.getCurrent().getAttribute("language").toString()));

        addClassName("hasta-personel");
        setSizeFull();
        notificationInfo = new FormLayout();

        H3 related = new H3("Assigned Users");
        H3 notRelated = new H3("Not Assigned Users");

        gridLabels = new FormLayout(related, notRelated);

        add(notificationInfo, gridLabels, getContent());
    }

    private void configureNotificationInfo() {


        TextField notificationId = new TextField("Notification Id");
        notificationId.setValue(Long.toString(notification.getNotificationId()));
        notificationId.setReadOnly(true);

        TextField title = new TextField("Title");
        title.setValue(notification.getTitle());
        title.setReadOnly(true);

        TextField content = new TextField("Content");
        content.setValue(notification.getContent());
        content.setReadOnly(true);


        notificationInfo.add(notificationId, title, content);


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
        notAssignedUsers.getColumns().forEach(col -> col.setAutoWidth(true));

        notAssignedUsers.addComponentColumn(user -> {
            Button button = new Button("", VaadinIcon.ARROW_LEFT.create());

            button.addClickListener(e -> {

                relate(user);
            });
            return button;
        });

        notAssignedUsers.getColumnByKey("identityNumber").setHeader("identityNumber");
        notAssignedUsers.getColumnByKey("firstName").setHeader("firstName");
        notAssignedUsers.getColumnByKey("lastName").setHeader("lastName");


        notAssignedUsers.asSingleSelect().addValueChangeListener(event -> {
        });
    }

    private void configureRelatedPatienceGrid() {

        assignedUsers.addClassNames("user-grid");
        assignedUsers.setSizeFull();
        assignedUsers.setColumns("identityNumber", "firstName", "lastName");
        assignedUsers.getColumns().forEach(col -> col.setAutoWidth(true));

        assignedUsers.addComponentColumn(user -> {
            Button button = new Button("", VaadinIcon.ARROW_RIGHT.create());
            button.addClickListener(e -> {

                unRelate(user);
            });
            return button;
        });

        assignedUsers.getColumnByKey("identityNumber").setHeader("identityNumber");
        assignedUsers.getColumnByKey("firstName").setHeader("firstName");
        assignedUsers.getColumnByKey("lastName").setHeader("lastName");


        assignedUsers.asSingleSelect().addValueChangeListener(event -> {
        });
    }

    private void unRelate(User user) {
        System.out.println("UNRELATE RUNNED");
        //user.getNotifications().remove(notification);
        UserNotification userNotification = userNotificationService.findById(user, notification);


        user.getUserNotifications().remove(userNotification);
        userNotificationService.delete(userNotification);

        //user = userService.saveAndFlush(user);
        //notification = notificationService.findById(notification.getNotificationId());
        //user = userService.findById(user.getIdentityNumber());


        updateAssignedUserGrid();
        updateNotAssignedUserGrid();

        UI.getCurrent().getPage().reload();

        System.out.println("UNRELATE FINISHED");

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